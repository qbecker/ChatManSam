package messaging;

import org.json.JSONObject;


public class Message{
	String type;
	String message;

	public Message(String type, String message) {
		this.type = type;
		this.message = message;
	}

	public Message(String json) {
		try {
			JSONObject jsnObj = new JSONObject(json);
			this.type = jsnObj.getString("Type");
			this.message =  jsnObj.getString("Message");
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
