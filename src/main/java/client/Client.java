package client;

import java.net.Socket;

import messaging.Message;
import messaging.Message.Type;
import socket.SocketManager;
public class Client extends SocketManager{

	//private SocketManager socMan;
	public String userName;
	
	public boolean loggedIn;
	
	
	//create a new client
	public static Client clientInit(String address, int port, String userName) {
		
		Client client = null;
		try {
			//create connection to server
			Socket socket = new Socket(address, port);
			//create new client object for use of socketManager
		    client = new Client(socket, userName);
		    
		}catch(Exception e) {
			System.out.println("Error in creating client");
		}
		return client;
	}
	//super constructor
	public Client(Socket socket, String userName) {
		super(socket);
		this.userName = userName;
		this.loggedIn = false;
		
	}
	
	public void login() {
		sendMessage(new Message(Type.Login, userName));
	}

	//over writen method from socketManager for behavior on incoming messages
	@Override
	public void readMessage(Message message) {
			System.out.println("Received Message: ");
			System.out.println(message.messageToString());
			if(message.getType() == Type.Login) {
				if(message.getMessage().equals("Success")) {
					this.loggedIn = true;
				}else {
					System.out.println("Login failed");
				}
			}
	}
	@Override
	public void disconnect() {
		
	}
	
}