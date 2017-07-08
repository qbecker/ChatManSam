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
		} else {
			//StartClient();
			Thread insertStuff = new Thread() {
				public void run() {
					boolean test = DAO.insertUser("Shelby", "Largemelons1", "Online");


					StartClient();
				}
			};
			insertStuff.start();

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

	public static void startServer() {
		Log.debug(" Starting in server mode...  " );
  	Server server = new Server(8080);
	}

	public static void StartClient() {
		Log.debug("Client starting.");
    Scanner sc = new Scanner(System.in);

    Log.debug("Enter your username:");
    String nameStr = sc.nextLine();

		if(DAO.getUserName(nameStr) == null) {
			Log.debug("Username does not exist.");

			boolean looping = true;
			while(looping) {

				Log.debug("Would you like to make an account? [Y/N]");
				Scanner usc = new Scanner(System.in);
				String str = usc.nextLine();

				Log.debug(str);

				if(str.equals("Y") || str.equals("y")) {
					Log.debug("Creating account... ");
					// TODO: CREATE ACCOUNT METHOD
					looping = false;

				} else if(str.equals("N") || str.equals("n")) {
					Log.debug(nameStr + " will not be created.");
					// TODO: Loop it back into client input loop.
					looping = false;

				} else {
					Log.debug("Command not recognized, please try again. ");

				}
			}
		} else {
			Log.debug("Username exists!");
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
}
