package messaging;

import org.json.JSONObject;
import org.json.JSONArray;


public class Message{
	String type;
	String message;
	String[] recipients;
	

	public Message(String type, String message) {
		this.type = type;
		this.message = message;
	}
	public Message(String type, String message, String[] recipients) {
		this.type = type;
		this.message = message;
	}

	public Message(String json) {
		try {
			JSONObject jsnObj = new JSONObject(json);
			this.type = jsnObj.getString("Type");
			this.message =  jsnObj.getString("Message");
			if(this.type.equals("ChatMessage")) {
				JSONArray  tmp = jsnObj.getJSONArray("Recipients");
				this.recipients = new String[tmp.length()];
				for(int i = 0; i < tmp.length(); i++) {
					this.recipients[i] = tmp.getString(i);
				}
			}

		}catch(Exception e) {
			this.type = "Error";
			this.message = "Error";
		}
		
		
	}


	public String getType() {
		return type;
	}



	public void setType(String type) {
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

	public String toJsonString() {
		String jsn = null;
		try{
			JSONObject jsnObj = new JSONObject();
			jsnObj.put("Type", this.getType());
			jsnObj.put("Message", this.getMessage());
			jsn = jsnObj.toString();
			
		}catch(Exception e) {
			System.out.println("Something went wrong in messageing tostring");
		}
		return jsn;
	}
}
