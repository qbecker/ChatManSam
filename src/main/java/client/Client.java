package client;

import java.net.Socket;

import messaging.Message;
import socket.SocketManager;
public class Client extends SocketManager{

	//private SocketManager socMan;
	public String userName;
	
	
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
		
	}
	
	public void login() {
		sendMessage(new Message("Login", userName));
	}

	//over writen method from socketManager for behavior on incoming messages
	@Override
	public void readMessage(Message message) {
			System.out.println("Received Message: ");
			System.out.println(message.toJsonString());
	}
	
}