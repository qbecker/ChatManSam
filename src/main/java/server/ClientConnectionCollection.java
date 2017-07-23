package server;

import java.util.HashMap;

import server.Server.ClientConnection;
import utils.Logger.Log;

public class ClientConnectionCollection {
	private static  HashMap<String, ClientConnection> connectedClients;
	
	private static ClientConnectionCollection instance;
	
	public static synchronized ClientConnectionCollection getInstance() {
		
		if(instance == null) {
			instance = new ClientConnectionCollection();
		}
		
		return instance;
	}
	
	private ClientConnectionCollection() {
		this.connectedClients = new HashMap<String, ClientConnection>();
	}
	
	public boolean putClientConn(ClientConnection conn) {
		boolean ret = true;
		synchronized(connectedClients) {
			try {
				connectedClients.put(conn.getUserName(), conn);
			}catch(Exception e) {
				ret = false;
			}
		}
		return ret;
	}
	
	public ClientConnection getClientConn(String userName) {
		ClientConnection ret = null;
		synchronized(connectedClients) {
			try {
				ret = connectedClients.get(userName);
			}catch(Exception e) {
				Log.debug("Something went wrong in getClientConn");
			}
		}
		return ret;
	}
	
	public String[] getAllClientNames() {
		String[] ret;
		synchronized(connectedClients) {
			ret = connectedClients.keySet().toArray(new String[connectedClients.size()]);
		}
		return ret;
	}
	
	public boolean removeClientConn(String userName) {
		boolean ret = true;
		synchronized(connectedClients) {
			try {
				connectedClients.remove(userName);
			}catch(Exception e) {
				ret = false;
			}
		}
		return ret;
	}
	
}
