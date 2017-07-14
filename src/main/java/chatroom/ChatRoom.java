package chatroom;

import java.util.HashMap;

public class ChatRoom {
	public String name;
	
	private HashMap <String,String> members;
	
	public ChatRoom(String name, String[] initMembers) {
		this.name = name;
		this.members = new HashMap<String,String>();
		
		for(int i = 0; i < initMembers.length; i++) {
			this.members.put(initMembers[i], initMembers[i]);
		}
	}
	
	public boolean addMember(String name) {
		boolean ret = true;
		synchronized(members){
			String exists = members.get(name);
			if(exists == null) {
				members.put(name, name);
			}
			else {
				ret = false;
			}
		}
		return ret;
	}
	
	public boolean removeMember(String name) {
		boolean ret = true;
		synchronized(members){
			String exists = members.get(name);
			if(exists != null) {
				members.remove(name);
			}
			else {
				ret = false;
			}
		}
		return ret;
	}
	
	public int getCount() {
		int ret;
		synchronized(members) {
			ret = members.size();
		}
		return ret;
	}
	
	public String[] getAllMembers() {
		String[] ret;
		synchronized(members) {
			ret = members.keySet().toArray(new String[members.size()]);
		}
		return ret;
	}
}
