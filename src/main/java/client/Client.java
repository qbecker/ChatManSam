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
		// Get username from server, buuuut having issues with this.
		// Not sure how to set the connectedClient username on the server.
		// I tried returning a string from signUpLogIn()
		// but I ended up with several back and forth sendMessages.
		// another option would be to have a name generated when the client starts.
		// We would need to

	}
	
	public void checkName(AccountObject accObject) {
		sendMessage(new Message(Type.EchoName, accObject));
	}

	public void login() {
		sendMessage(new Message(Type.Login, userName));
		
	}

	//over writen method from socketManager for behavior on incoming messages
	@Override
	public void readMessage(Message message) {
		Log.debug("Received Message: ");
		Log.debug(message.messageToString());
		
		if(message.getType() == Type.Login) {
			if(message.getMessage().equals("Success")) {
				this.loggedIn = true;
			} if(message.getMessage().equals("Failed")) {
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
		//Log.debug("Logging in with User: Shelby | Pass: Largemelons1");
		//sendMessage(new Message(Type.CreateAccount, "Shelby Largemelons1"));
		AccountObject account = new AccountObject("Shelby", "Largemelons1");
		Log.debug("Account Name: " + account.accName);
		Log.debug("Account Pass: " + account.accPass);
		
		// Here is the where the account object is sent.
		sendMessage(new Message(Type.EchoName, account));
		
	}
	
	public class AccountObject {
		// This is the log in object that stores the temp username/password for log in.
		// Like the username/password field before logging into an application.
		public String accName;
		public String accPass;
		
		// Here is the contstructor: 
		public AccountObject(String accName, String accPass) {
			this.accName = accName;
			this.accPass = accPass;
		}
		/*
		public String getAccName() {
			return this.accName;
		}
		
		public String getAccPass() {
			return this.accPass;
		}
		*/
	}
}
