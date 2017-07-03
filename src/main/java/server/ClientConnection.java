package server;

import java.net.Socket;

import messaging.Message;
import socket.SocketManager;


//class for use by server to create and manage diffrent connections to the clients
public class ClientConnection extends SocketManager {
	
	public String userName;
	
	public ClientConnection(Socket socket) {
		super(socket);
		
	}

	@Override
	public void readMessage(Message message) {
		// all incoming messages from connected clients come through here
		// Here is where we can redirect messages to other client.
		System.out.println("read message: ");
		System.out.println(userName);
		System.out.println(message.toJsonString());
		sendMessage(message);
	}

}
