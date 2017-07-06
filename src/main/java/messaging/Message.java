package messaging;

import java.io.Serializable;
import java.util.Arrays;

import utils.Logger.Log;


public class Message implements Serializable{
	Type type;
	String message;
	String[] recipients;
	

	public Message(Type type, String message) {
		this.type = type;
		this.message = message;
	}
	public Message(Type type, String message, String[] recipients) {
		this.type = type;
		this.message = message;
		this.recipients = recipients;
	}



	public Type getType() {
		return type;
	}



	public void setType(Type type) {
		this.type = type;
	}



	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	
	public String[] getRecipients() {
		return recipients;
	}

	public void setRecipients(String[] recipients) {
		this.recipients = recipients;
	}

	public String messageToString() {
		StringBuilder sb = new StringBuilder();
		try{
			sb.append("Type: " + this.getType().toString() + "\n");
			sb.append("Message: " +  this.getMessage() + "\n");
			if(this.getType().toString().equals("ChatMessage")) {
				sb.append("Recipients: " + Arrays.toString(this.recipients) + "\n");
			}
		}catch(Exception e) {
			Log.debug("Something went wrong in messageing tostring");
		}
		return sb.toString();
	}
	
	public enum Type{
		ChatMessage, Login
	}
	
}
