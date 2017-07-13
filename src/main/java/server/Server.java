package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import chatroom.ChatRoom;
import database.DAO;

import messaging.Message;
import socket.SocketManager;
import utils.Logger.Log;
import messaging.Message.Type;


public class Server implements Runnable{
	private int port;
	private ServerSocket servSoc;

	private HashMap<String, ClientConnection> connectedClients;
	private HashMap<String, ChatRoom> currentChatRooms;

	public Server(int port) {
		this.connectedClients = new HashMap<String, ClientConnection>();
		this.currentChatRooms = new HashMap<String, ChatRoom>();
		this.port = port;
		Thread thread = new Thread(this);
		thread.start();
	}

	public boolean putClientConn(ClientConnection conn) {
		boolean ret = true;
		synchronized(connectedClients) {
			try {
				connectedClients.put(conn.getUserName(), conn);
			}catch(Exception e) {
				ret = false;
			}
		}
		return ret;
	}
	
	public boolean removeChatRoom(String name) {
		boolean ret = true;
		synchronized(currentChatRooms) {
			ChatRoom exists = currentChatRooms.get(name);
			if(exists != null) {
				currentChatRooms.remove(name);
			}else {
				ret = false;
			}
		}
		return ret;
	}
	
	public ChatRoom getChatRoom(String name) {
		ChatRoom ret = null;
		synchronized(currentChatRooms){
			ret = currentChatRooms.get(name);
		}
		return ret;
	}
	
	public boolean putChatRoom(ChatRoom room) {
		boolean ret = true;
		synchronized(currentChatRooms) {
			ChatRoom exists = currentChatRooms.get(room.name);
			if(exists == null) {
				currentChatRooms.put(room.name, room);
			}else {
				ret = false;
			}
		}
		return ret;
	}

	public ClientConnection getClientConn(String userName) {
		ClientConnection ret = null;
		synchronized(connectedClients) {
			try {
				ret = connectedClients.get(userName);
			}catch(Exception e) {
				Log.debug("Something went wrong in getClientConn");
			}
		}
		return ret;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			servSoc = new ServerSocket(port);
			while(true) {
				Socket soc = servSoc.accept();
				ClientConnection client = new ClientConnection(soc);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class ClientConnection extends SocketManager {

		public String userName;

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public ClientConnection(Socket socket) {
			super(socket);
		}

		@Override
		public void readMessage(Message message) {
			// all incoming messages from connected clients come through here
			// Here is where we can redirect messages to other client.
			
			Log.debug(message.messageToString());
			if(message.getType() == Type.Login) {
				processLogIn(message);
			} 
			if(message.getType() == Type.CreateAccount) {
				processAccCreation(message);
			} 
			if(message.getType() == Type.CreateChatRoom) {
				createChatRoom(message);
			} 
			if(message.getType() == Type.ChatMessage) {
				processChatMessage(message);
			} 
			if(message.getType() == Type.ChatRoomMessage) {
				processChatRoomMessage(message);
			}
		}
		
		public void processLogIn(Message message) {
			//message.getMessage() = username.
			//message.getMessage2() = password.
			// check server if name exists, check password.
			String checkPW = DAO.getPassWord(message.getMessage());
			String sentPW = message.getMessage2();
			if(checkPW == null) {
				Log.debug("checkPW failed!");
				sendMessage(new Message(Type.Login, "Failed", "Password check failed."));
			}

			if(checkPW.equals(sentPW)) {
				Log.debug("checkPW succeeded!");
				this.setUserName(message.getMessage());
				ClientConnection con = getClientConn(this.userName);
				if(con == null) {
					putClientConn(this);
					sendMessage(new Message(Type.Login, "Success", this.userName));
				}else {
					sendMessage(new Message(Type.Login, "Failed", "You have NOT been logged in."));
				}
			}
			else{
				sendMessage(new Message(Type.Login, "Failed"));
			}
		}

		public void processAccCreation(Message message) {
			String reqUser = message.getMessage();
			String reqPass = message.getMessage2();
			boolean success = DAO.insertUser(reqUser, reqPass, "Online");
			if(success) {
				sendMessage(new Message(Type.CreateAccount, "Success"));
			} else {
				sendMessage(new Message(Type.CreateAccount, "Failed"));
			}
		}
		
		public void createChatRoom(Message message) { 
			Log.debug("Entering create chatx");
			ChatRoom chat = new ChatRoom(message.getMessage(), message.getRecipients());
			boolean created = putChatRoom(chat);
			if(created) {
				Log.debug("Chat room created: " + chat.name);
				sendMessage(new Message(Type.CreateChatRoom, "Success"));
			}else {
				Log.debug("Chat room already exists: " + chat.name);
				sendMessage(new Message(Type.ChatRoomMessage, "Failed"));
			}
		}
		
		public void processChatMessage(Message message) {
			String[] recips = message.getRecipients();
			for(int i = 0; i < recips.length; i++) {
				ClientConnection con = null;
				con = getClientConn(recips[i]);
				if(con != null) {
					con.sendMessage(new Message(Type.ChatMessage, message.getMessage(), new String[] {this.userName}));
					Log.debug("Message From: " + this.userName +" to: "+ recips[i] + " Containing: " + message.getMessage());
				}
			}
		}
		
		public void processChatRoomMessage(Message message) {
			ChatRoom chat = getChatRoom(message.getRecipients()[0]);
			if(chat != null) {
				String[] members = chat.getAllMembers();
				for(int i = 0; i < members.length; i++) {
					ClientConnection con = null;
					con = getClientConn(members[i]);
					if(con != null) {
						con.sendMessage(new Message(Type.ChatRoomMessage, message.getMessage(), new String[] {message.getRecipients()[0]}, this.getUserName()));
						Log.debug("Group Message From: " + this.userName +" to: "+ members[i] + " Containing: " + message.getMessage());
					}
				}
			}
		}
		
		
		@Override
		public void disconnect() {
		}
		
		
	}
}
