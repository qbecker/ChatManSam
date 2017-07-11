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

	public void clientLoop() {
		signUpLogIn();
		Scanner sc = new Scanner(System.in);
		Log.debug("Enter something: ");
		String inputLine;
		while(sc.hasNextLine()) {
			inputLine = sc.nextLine();
			Log.debug(this.userName + ": " + inputLine);
			Log.debug("Enter something: ");
		}
	}

	//over writen method from socketManager for behavior on incoming messages
	@Override
	public void readMessage(Message message) {
		Log.debug("Received Message: ");
		Log.debug(message.messageToString());

		if(message.getType() == Type.Login) {
			if(message.getMessage().equals("Success")) {
				// The log in was successful.
				this.loggedIn = true;
				this.userName = message.getMessage2();
			} else if(message.getMessage().equals("Failed")) {
				Log.debug("Username or password incorrect.");
			} else {
				Log.debug("Login received neither success nor failed.");
			}
		}

		if(message.getType() == Type.CreateAccount) {
			if(message.getMessage().equals("Success")) {
				Log.debug("Account Creation Sucessful");
			}	if(message.getMessage().equals("Failed")) {
				Log.debug("Account Creation Failed");
			}	else {
				Log.debug("Account creation received neither success nor failed.");
			}
		}
	}

	@Override
	public void disconnect() {

	}


	public void signUpLogIn() {

		Scanner sc = new Scanner(System.in);
		String inputLine;

		Log.debug("Would you like to [L]og in, [C]reate an account, or [Q]uit?");
		while(sc.hasNextLine()) {
			inputLine = sc.nextLine();

			if (inputLine.toLowerCase().equals("l")) {
				Log.debug("You wish to log in!");
				logInRequest();

			} else if (inputLine.toLowerCase().equals("c")) {
				Log.debug("You wish to create an account!");
				createAccountRequest();

			} else if (inputLine.toLowerCase().equals("q")) {
				Log.debug("You wish to quit!");
				System.exit(0);

			} else {
				Log.debug("Command not recognized. Please try again.");
				Log.debug("Would you like to [L]og in, [C]reate an account, or [Q]uit?");
			}
		}
	}

	public void logInRequest() {
		//Log.debug("Logging in with User: Shelby | Pass: Largemelons1");
		//sendMessage(new Message(Type.Login, "Shelby", "Largemelons1"));
	}

	public void createAccountRequest() {

	}

	String properCase (String inputVal) {
		// Shamelessly stolen from the user paxdiablo on SE.
		// https://stackoverflow.com/questions/2375649/converting-to-upper-and-lower-case-in-java

		//String inputval="ABCb" OR "a123BC_DET" or "aBcd"
		//String outputval="Abcb" or "A123bc_det" or "Abcd"

    // Empty strings should be returned as-is.
    if (inputVal.length() == 0) return "";
    // Strings with only one character uppercased.
    if (inputVal.length() == 1) return inputVal.toUpperCase();
    // Otherwise uppercase first letter, lowercase the rest.
    return inputVal.substring(0,1).toUpperCase()
        + inputVal.substring(1).toLowerCase();
}


}
