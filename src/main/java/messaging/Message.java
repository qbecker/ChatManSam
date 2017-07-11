package messaging;

import java.io.Serializable;
import java.util.Arrays;

import utils.Logger.Log;


public class Message implements Serializable{
	Type type;
	String message;
	String message2;
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
	public Message(Type type, String message, String message2) {
		this.type = type;
		this.message = message;
		this.message2 = message2;
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

	public String getMessage2() {
		return message2;
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
			if(this.getType().toString().equals("Login")) {
				sb.append("Message: " + this.getMessage() + ", " + this.getMessage2());
			}
			if(this.getType().toString().equals("ChatMessage")) {
				sb.append("Recipients: " + Arrays.toString(this.recipients) + "\n");
			}
			if(this.getType().toString().equals("CreateAccount")) {
				sb.append("Message: " + this.getMessage());
			}
		}catch(Exception e) {
			Log.debug("Something went wrong in messaging tostring");
		}
		return sb.toString();
	}

	public enum Type {
		ChatMessage, Login, CreateAccount
	}

}
