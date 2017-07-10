package test;

import static org.junit.Assert.*;

import org.junit.Test;

import client.Client;
import messaging.Message;
import messaging.Message.Type;
import server.Server;
import server.Server.ClientConnection;
import utils.Logger.Log;

public class ChatRoomTest {

	@Test
	public void test() {
		String testUserName1 = "TestUser1";
		String testUserName2 = "TestUser2";

		Server server = new Server(8080);
		Client client = Client.clientInit("localhost", 8080);
		client.userName = testUserName1;
		Client client2 = Client.clientInit("localhost", 8080);
		client2.userName = testUserName2;
		client.login();
		client2.login();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client2.sendMessage(new Message(Type.CreateChatRoom, "TestChatRoom", new String[] {client.userName,testUserName2 }));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.sendMessage(new Message(Type.ChatRoomMessage, "TestingChatRoom", new String[] {client.userName,testUserName2 }, client.userName));
		Log.debug("hmmm");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
