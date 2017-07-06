import java.util.Scanner;

import client.Client;
import messaging.Message;
import messaging.Message.Type;
import server.Server;

public class ChatManSam {
	public static void main(String[] args) {
		if(args.length != 0 && args[0].equals("-s")) {
			startServer();
		}else {
			StartClient();
		}
	}
	
	public static void startServer() {
		System.out.println(" Starting in server mode...  " );
  		 
  		 Server server = new Server(8080);
	}
	public static void StartClient() {
		System.out.println("Starting in server Mode");
	    Scanner sc = new Scanner(System.in);
	    System.out.println("Enter your username");
	    String nameStr = sc.nextLine();
	    
	    System.out.println("your name: " + nameStr);
	    System.out.println("Enter your target userName");
	    String target = sc.nextLine();
	    
	    System.out.println("your target is: " + target);
	    
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
	    
		System.out.println("Enter a message to " + target);
	    	String inputLine;
	    	while(sc.hasNextLine()) {
	    	  inputLine = sc.nextLine();
	    	  System.out.println(inputLine);
	    	  client.sendMessage(new Message(Type.ChatMessage, inputLine, new String[] {target}));
	    	  System.out.println("Enter something");
	    	}
	    	sc.close();
	    
	}
}
