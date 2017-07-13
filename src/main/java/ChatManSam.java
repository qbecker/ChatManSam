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

			/*
			Thread insertStuff= new Thread() {
				public void run() {
					for(int i =0; i < 100; i++) {
						boolean test = DAO.insertUser("Quinten", "Hunter2", "Online");
						if(test) {
							Log.debug("worked");
							Log.debug(DAO.getPassWord("Quinten"));
						}
					}
				}
			};
			*/
			
		}
	}
 
	public static void StartServer() {
		Log.debug(" Starting in server mode...  ");
		Server server = new Server(8080);
	}

	public static void StartClient() {
		Log.debug(" Starting in client mode... ");
		Client client = Client.clientInit("localhost", 8080);
		Thread t = new Thread() {
			public void run() {
				client.clientLoop();
			}
		};
		t.start();
	}
}
