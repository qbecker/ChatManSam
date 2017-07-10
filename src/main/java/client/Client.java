package client;

import java.net.Socket;
import socket.SocketManager;

import java.util.Scanner;
import messaging.Message;
import messaging.Message.Type;

import utils.Logger.*;


public class Client extends SocketManager {
	//private SocketManager socMan;
	public String userName;
	public boolean loggedIn;

	//create a new client
	public static Client clientInit(String address, int port) {

		Client client = null;
		try {
			//create connection to server
			Socket socket = new Socket(address, port);
			//create new client object for use of socketManager
			client = new Client(socket);

		}catch(Exception e) {
			Log.debug("Error in creating client");
		}
		return client;
	}
	//super constructor
	public Client(Socket socket) {
		super(socket);
		this.loggedIn = false;
		
	}
	
	public void createAccount() {
		
	}

	//over writen method from socketManager for behavior on incoming messages
	@Override
	public void readMessage(Message message) {
		Log.debug("Received Message: ");
		Log.debug(message.messageToString());
		
		if(message.getType() == Type.Login) {
			if(message.getMessage().equals("Success")) {
				Log.debug("The log in was successful " + userName);
				this.loggedIn = true;
			}else if(message.getMessage().equals("Failed")) {
				Log.debug("Username or password incorrect.");
			} else {
				Log.debug("Login received neither success nor failed.");
			}
		}
		
		if(message.getType() == Type.CreateAccount) {
			if(message.getMessage().equals("Success")) {
				Log.debug("Account Creation Sucessful");
			}
			if(message.getMessage().equals("Failed")) {
				Log.debug("Account Creation Failed");
			}
			else {
				Log.debug("Account creation received neither success nor failed.");
			}
		}
	}

	@Override
	public void disconnect() {
		
	}

	public void signUpLogIn() {
		Log.debug("Logging in with User: Shelby | Pass: Largemelons1");
		sendMessage(new Message(Type.Login, "Shelby", "Largemelons1"));

	}
	
	
}
