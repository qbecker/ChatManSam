package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import messaging.Message;
import socket.SocketManager;
import utils.Logger.Log;
import messaging.Message.Type;

public class Server implements Runnable{
	private int port;
	private ServerSocket servSoc;
	
	private  HashMap<String, ClientConnection> connectedClients;
	
	public Server(int port) {
		this.connectedClients = new HashMap<String, ClientConnection>();
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
	public ClientConnection getClientConn(String userName) {
		ClientConnection ret = null;
		synchronized(connectedClients) {
			try {
				ret = connectedClients.get(userName);
			}catch(Exception e) {
				Log.debug("Something went wrong in getCLientConn");
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
			if(message.getType()== Type.Login) {
				ClientConnection con = getClientConn(message.getMessage());
				if(con == null) {
					this.setUserName(message.getMessage());
					putClientConn(this);
					Log.debug(this.getUserName());
					sendMessage(new Message(Type.Login, "Success"));
				}else {
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
		}

		@Override
		public void disconnect() {
		}

	}



  
}
