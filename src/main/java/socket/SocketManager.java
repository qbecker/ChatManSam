package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import messaging.Message;

/*
 *	A class to manage sockets 
 * 
 */

public abstract class SocketManager implements Runnable {

	private Socket socket;
	private BufferedReader inputRead;
    private PrintWriter outputWriter;
	
	public SocketManager(Socket socket) {
		this.socket = socket;
		try {
			inputRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputWriter = new PrintWriter(socket.getOutputStream());
			new Thread(this).start();
		}catch(Exception e) {
			//todo fail gracefully
			System.out.println("socket creation failed");
		}
		
	}
	
	//over ridden from Runnable(Our threads)
	//loops while the socket is open and converts buffer data to a Message
	@Override
	public void run() {
		try {
			while(!socket.isClosed()) {
				readMessage(new Message(inputRead.readLine().trim()));
			}
		}catch(Exception e) {
			//todo: fail gracefully and clean up resources
			System.out.println("");
			System.out.println("Disconnected");
		}
		
	}
	
	public void sendMessage(Message message) {
		outputWriter.println(message.toJsonString());
		outputWriter.flush();
	}
	
	
	public abstract void readMessage(Message message);
	
}