package client;
import messaging.Message;

import java.util.Scanner;
public class Driver {
    public static void main(String[] args) {
        
	    System.out.println("Hello world from client");
	    
	    //init client
	    Client client = Client.clientInit("localhost", 8080, "qball");
	    //send a test message
	    client.login();
	    client.sendMessage(new Message("msg", "poop"));
	    
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
	    	Scanner sc = new Scanner(System.in);
	    	System.out.println("Enter something");
	    	String inputLine;
	    	while(sc.hasNextLine()) {
	    	  inputLine = sc.nextLine();
	    	  client.sendMessage(new Message("msg", inputLine));
	    	  System.out.println("Enter something");
	    	}
	    	sc.close();
	    	
	    }
}
