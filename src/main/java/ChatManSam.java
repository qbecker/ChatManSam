import java.util.Scanner;

import client.Client;
import database.DAO;
import messaging.Message;
import messaging.Message.Type;
import server.Server;
import utils.Logger.Log;

public class ChatManSam {
	public static void main(String[] args) {
		if(args.length != 0 && args[0].equals("-s")) {
			startServer();
		}else {
			//StartClient();
			Thread insert = new Thread(){
				public void run() {
					for(int i = 0; i < 200; i++) {
						DAO.insertUser("Quinten", "passssss", "online");
					}
				}
			};
			Thread get = new Thread(){
				public void run() {
					for(int j = 0; j < 200; j++) {
						Log.debug(DAO.getPassWord("Quinten"));
					}
				}
			};
			
			insert.start();
			get.start();
			
			
			Log.debug(DAO.getPassWord("Quinten"));
		}
	}
	
	public static void startServer() {
		Log.debug(" Starting in server mode...  " );
  		 
  		 Server server = new Server(8080);
	}
	public static void StartClient() {
		Log.debug("Starting in server Mode");
	    Scanner sc = new Scanner(System.in);
	    Log.debug("Enter your username");
	    String nameStr = sc.nextLine();
	    
	    Log.debug("your name: " + nameStr);
	    Log.debug("Enter your target userName");
	    String target = sc.nextLine();
	    
	    Log.debug("your target is: " + target);
	    
	    //init client
	    Client client = Client.clientInit("localhost", 8080, nameStr);
	    //send a test message
	    client.login();
	   // Message mes = new Message("ChatMessage", "poop", new String[] {"test"});
	   // client.sendMessage(mes);
	    
	    /*
	    	EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						ChatWindow frame = new ChatWindow();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
	    	*/
	    
	    Log.debug("Enter a message to " + target);
	    	String inputLine;
	    	while(sc.hasNextLine()) {
	    	  inputLine = sc.nextLine();
	    	  Log.debug(inputLine);
	    	  client.sendMessage(new Message(Type.ChatMessage, inputLine, new String[] {target}));
	    	  Log.debug("Enter something");
	    	}
	    	sc.close();
	    
	}
}
