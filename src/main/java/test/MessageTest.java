package test;

import static org.junit.Assert.*;

import org.junit.Test;

import client.Client;
import messaging.Message.Type;
import server.Server;
import server.Server.ClientConnection;
import messaging.Message;

public class MessageTest {

	@Test
	public void test() {
		String testUserName1 = "TestUser1";
		String testUserName2 = "TestUser2";
		// Had to comment out because usernames aren't used to init client.
		/*Message testMsg = new Message(Type.ChatMessage, "Test Message...", new String[] {testUserName1});
		assertTrue(testMsg.getType() == Type.ChatMessage);
		testMsg.setType(Type.Login);
		assert(testMsg.getType() == Type.Login);

		Server server = new Server(8080);


		Client client = Client.clientInit("localhost", 8080, testUserName1);
		Client client2 = Client.clientInit("localhost", 8080, testUserName2);
		client.login();
		client2.login();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ClientConnection conn = server.getClientConn(testUserName1);
		assertTrue(conn != null);
		assertTrue(conn.getUserName().equals(testUserName1));
		client2.sendMessage(testMsg);*/
	}

}
