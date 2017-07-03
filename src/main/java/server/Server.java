package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import messaging.Message;
import socket.SocketManager;

public class Server implements Runnable{
	private int port;
	private ServerSocket servSoc;
	
	public Server(int port) {
		this.port = port;
		Thread thread = new Thread(this);
		thread.start();
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


  
}
