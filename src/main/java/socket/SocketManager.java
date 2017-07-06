package socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import messaging.Message;
import utils.Logger.Log;
/*
 *	A class to manage sockets 
 * 
 */


public abstract class SocketManager implements Runnable {

	private Socket socket;
	private ObjectInputStream inputRead;
    private ObjectOutputStream outputWriter;
	
	public SocketManager(Socket socket) {
		this.socket = socket;
		try {
			outputWriter = new ObjectOutputStream(socket.getOutputStream());
			inputRead = new ObjectInputStream(socket.getInputStream());
			new Thread(this).start();
		}catch(Exception e) {
			//todo fail gracefully
			Log.debug("socket creation failed");
		}
		
	}
	
	//over ridden from Runnable(Our threads)
	//loops while the socket is open and converts buffer data to a Message
	@Override
	public void run() {
		try {
			while(!socket.isClosed()) {
				Message msg = (Message) inputRead.readObject();
				readMessage(msg);
			}
		}catch(Exception e) {
			//todo: fail gracefully and clean up resources
			Log.debug("Disconnected");
			e.printStackTrace();
			closeConnection();
			Log.debug("socket closed");
		}
		
	}
	
	
	public void sendMessage(Message message) {
		try {
			outputWriter.writeObject(message);
			outputWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void closeConnection() {
		try {inputRead.close();}catch(Exception e) {e.printStackTrace();}
		try {outputWriter.close();}catch(Exception e) {e.printStackTrace();}
		try {socket.close();}catch(Exception e) {e.printStackTrace();}

	}
	
	
	public abstract void readMessage(Message message);
	public abstract void disconnect();
	
}