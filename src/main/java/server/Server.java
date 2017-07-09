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

	private  HashMap<String, ClientConnection> connectedClients;
	private 	 HashMap<String, ChatRoom> currentChatRooms;

	public Server(int port) {
		this.connectedClients = new HashMap<String, ClientConnection>();
		this.currentChatRooms = new HashMap<String, ChatRoom>();
		this.port = port;
		Thread thread = new Thread(this);
		thread.start();
		//boolean test = DAO.insertUser("Shelby", "Largemelons1", "Online");
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
				//splitMsg[0] is username, splitMsg[1] is password
				/*String[] splitMsg = message.getMessage().toString().split(" ");
				String checkPW = DAO.getPassWord(splitMsg[0]);
				if(checkPW != splitMsg[1])
				{
					Log.debug("Log in successful.");
					sendMessage(new Message(Type.Login, "Success"));
				} else {
					sendMessage(new Message(Type.Login, "Failed"));
				}*/
				putClientConn(this);
				sendMessage(new Message(Type.Login, "Success"));
			}
			if(message.getType() == Type.CreateAccount) {
				String[] splitMsg = message.getMessage().toString().split(" ");
				boolean created = DAO.insertUser(splitMsg[0], splitMsg[1], "New User");
				if(created) {
					Log.debug("Account Creation successful.");
					sendMessage(new Message(Type.CreateAccount, "Success"));
				}else {
					Log.debug("Account Creation Failed.");
					sendMessage(new Message(Type.CreateAccount, "Failed"));
				}
			}
			if(message.getType() == Type.CreateChatRoom) {
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
			if(message.getType()== Type.Login) {
				ClientConnection con = getClientConn(message.getMessage());
				if(con == null) {
					this.setUserName(message.getMessage());
					putClientConn(this);
					Log.debug(this.getUserName());
					sendMessage(new Message(Type.Login, "Success"));
				} else {
					sendMessage(new Message(Type.Login, "Failed"));
				}
			}
			if(message.getType() == Type.ChatMessage) {
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
			if(message.getType() == Type.ChatRoomMessage) {
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
		}

		@Override
		public void disconnect() {
		}
	}
}
