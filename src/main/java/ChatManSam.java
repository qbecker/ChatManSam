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
			boolean test = DAO.insertUser("Shelby", "Largemelons1", "Online");
			StartServer();
		} else {
			StartClient();

			/*Thread insertStuff= new Thread() {
				public void run() {
					for(int i =0; i < 100; i++) {
						boolean test = DAO.insertUser("Quinten", "Hunter2", "Online");
						if(test) {
							Log.debug("worked");
							Log.debug(DAO.getPassWord("Quinten"));
						}
					}
				}
			};*/
		}
	}

	public static void StartServer() {
		Log.debug(" Starting in server mode...  ");
		Server server = new Server(8080);
	}

	public static void StartClient() {
		System.out.println(" Starting in client mode... ");
		Client client = Client.clientInit("localhost", 8080);
		Thread t = new Thread() {
			public void run() {
				client.clientLoop();
			}
		};
		t.start();
	}











    /*Log.debug("your name: " + nameStr);
    Log.debug("Enter your target userName");
    String target = sc.nextLine();

    Log.debug("your target is: " + target);

    //init client
    Client client = Client.clientInit("localhost", 8080, nameStr);
    //send a test message
    client.login();
    // Message mes = new Message("ChatMessage", "poop", new String[] {"test"});
    // client.sendMessage(mes);


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


    Log.debug("Enter a message to " + target);
    	String inputLine;
    	while(sc.hasNextLine()) {
    	  inputLine = sc.nextLine();
    	  Log.debug(inputLine);
    	  client.sendMessage(new Message(Type.ChatMessage, inputLine, new String[] {target}));
    	  Log.debug("Enter something");
    	}
    	sc.close();*/


}
