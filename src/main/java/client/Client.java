package client;

import java.net.Socket;

import messaging.Message;
import messaging.Message.Type;
import socket.SocketManager;
import utils.Logger.*;
import java.util.Scanner;


public class Client extends SocketManager{
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
		SignUpLogIn();
		// Get username from server, buuuut having issues with this.
		// Not sure how to set the connectedClient username on the server.
		// I tried returning a string from SignUpLogIn()
		// but I ended up with several back and forth sendMessages.
		// another option would be to have a name generated when the client starts.
		// We would need to

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
			} else {
				Log.debug("Username or password incorrect.");
			}
		}
	}

	@Override
	public void disconnect() {
	}

	public void SignUpLogIn() {
		Log.debug("Logging in with User: Shelby | Pass: Largemelons1");
		sendMessage(new Message(Type.Login, "Shelby Largemelons1"));
	}

	public enum SignIn {
		Existing, NewAcc, Quit
	}
}
