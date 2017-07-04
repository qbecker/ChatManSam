package client;
import messaging.Message;
import messaging.Message.Type;

import java.util.Scanner;
public class Driver {
    public static void main(String[] args) {
        
	    System.out.println("Hello world from client");
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
	    
	    //take input and keep send it.
	    	
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
