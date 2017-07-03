package server;

import java.net.Socket;

import messaging.Message;
import socket.SocketManager;


//class for use by server to create and manage diffrent connections to the clients
public class ClientConnection extends SocketManager {


	public ClientConnection(Socket socket) {
		super(socket);
		
	}

	@Override
	public void readMessage(Message message) {
		System.out.println("read message: ");
		System.out.println(message.toJsonString());
		sendMessage(message);
		
	}

}
