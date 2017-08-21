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

	private 	 HashMap<String, ChatRoom> currentChatRooms;

	public Server(int port) {
		this.currentChatRooms = new HashMap<String, ChatRoom>();
		this.port = port;
		Thread thread = new Thread(this);
		thread.start();
		//boolean test = DAO.insertUser("Shelby", "Largemelons1", "Online");
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

	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			servSoc = new ServerSocket(port);
			while(true) {
				Socket soc = servSoc.accept();
				Log.debug("New socket");
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
				// check server if name exists, check password.
				Log.debug(message.getMessage());
				Log.debug(message.getMessage2());
				String checkPW = DAO.getPassWord(message.getMessage());
				String sentPW = message.getMessage2();
				if(checkPW == null) {
					Log.debug("checkPW failed!");
					
					sendMessage(new Message(Type.Login, "Failed", "Password check failed."));
				}
				
				if(checkPW != null) {
					if(checkPW.equals(sentPW)) {
						Log.debug("checkPW succeeded!");
						this.setUserName(message.getMessage());
						ClientConnection con = ClientConnectionCollection.getInstance().getClientConn(this.userName);
						if(con == null) {
							ClientConnectionCollection.getInstance().putClientConn(this);
							sendMessage(new Message(Type.Login, "Success", this.userName));
						}else {
							sendMessage(new Message(Type.Login, "Failed", "You have NOT been logged in."));
						}
					}
					else{
						sendMessage(new Message(Type.Login, "Failed"));
					}
				}
				

			} else if(message.getType() == Type.CreateAccount) {
				String reqUser = message.getMessage();
				String reqPass = message.getMessage2();
				boolean success = DAO.insertUser(reqUser, reqPass, "Online");
				if(success) {
					sendMessage(new Message(Type.CreateAccount, "Success"));
				} else {
					sendMessage(new Message(Type.CreateAccount, "Failed"));
				}
			}


			if(message.getType() == Type.CreateChatRoom) {
				Log.debug("Entering create chat");
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
			
			if(message.getType() == Type.ChatMessage) {
				
				/*
				 * temporarily taking out for testing so the message goes to all
				String[] recips = message.getRecipients();
				for(int i = 0; i < recips.length; i++) {
					ClientConnection con = null;
					con = ClientConnectionCollection.getInstance().getClientConn(recips[i]);
					if(con != null) {
						con.sendMessage(new Message(Type.ChatMessage, message.getMessage(), new String[] {this.userName}));
						Log.debug("Message From: " + this.userName +" to: "+ recips[i] + " Containing: " + message.getMessage());
					}
				}
				*/
				String[] members = ClientConnectionCollection.getInstance().getAllClientNames();
				for(int i = 0; i < members.length; i++) {
					ClientConnection con = null;
					con = ClientConnectionCollection.getInstance().getClientConn(members[i]);
					if(con != null) {
						Message msg = new Message(Type.ChatMessage, message.getMessage(), new String[] {this.userName});
						msg.setSender(this.userName);
						con.sendMessage(msg);
					}
				}
			}
			if(message.getType() == Type.ChatRoomMessage) {
				ChatRoom chat = getChatRoom(message.getRecipients()[0]);
				if(chat != null) {
					String[] members = chat.getAllMembers();
					for(int i = 0; i < members.length; i++) {
						ClientConnection con = null;
						con = ClientConnectionCollection.getInstance().getClientConn(members[i]);
						if(con != null) {
							con.sendMessage(new Message(Type.ChatRoomMessage, message.getMessage(), new String[] {message.getRecipients()[0]}, this.getUserName()));
							Log.debug("Group Message From: " + this.userName +" to: "+ members[i] + " Containing: " + message.getMessage());
						}
					}
				}
			}
			if(message.getType() == Type.DisconnectMessage) {
				disconnect();
			}
		}

		@Override
		public void disconnect() {
			this.setConnected(false);
			ClientConnectionCollection.getInstance().removeClientConn(this.userName);
		}
	}
}
