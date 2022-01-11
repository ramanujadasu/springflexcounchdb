package com.springflexcounchdb.common;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CommonUtils {

	public static final String CREATED_BY = getUsername();

	public static final String LAST_MODIFIED_BY = getUsername();

	public static String getLocalTime() {
		LocalDateTime datetime1 = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formatDateTime = datetime1.format(format);
		System.out.println(formatDateTime);
		return formatDateTime;
	}

	public static String convertEntityToJsonObject(Object obj) {

		ObjectMapper mapper = new ObjectMapper();
		String jsonObj = "";
		try {
			jsonObj = mapper.writeValueAsString(obj);
			System.out.println("ResultingJSONstring = " + jsonObj);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonObj;
	}

	public static String getUsername() {

		return "System";
	}
	
	public static String updateValues(String updateBody, JsonNode newBody) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode newObjectNode = null;
		try {
			JsonNode updateBodyNode = mapper.readTree(updateBody); 
			ObjectNode objectUpdateBodyNode = (ObjectNode) updateBodyNode;
			//JsonNode newBodyNode = mapper.readTree(newBody);
			 newObjectNode = (ObjectNode) newBody;
			newObjectNode.put("name", objectUpdateBodyNode.get("name"));
			newObjectNode.put("id", objectUpdateBodyNode.get("id"));
			newObjectNode.put("age", objectUpdateBodyNode.get("age"));
			//TODO
			ArrayNode nodeAddress = (ArrayNode) objectUpdateBodyNode.get("address");
			//((ObjectNode)jsonNode).putArray("address").add(object.ge‌​tValue());
			//ArrayNode objectNodeAddress = (ArrayNode) nodeAddress;
			//newObjectNode.put("address", objectNodeAddress);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Exception: "+e.getMessage());
		} 
		//newObjectNode.put("", objectUpdateBodyNode.get(""));
//		JsonNode nodeParent = someNode.get("NodeA")
//                .get("Node1");
//
//		// Manually modify value of 'subfield', can only be done using the parent.
//		((ObjectNode) nodeParent).put('subfield', "my-new-value-here");
		//REF: https://stackoverflow.com/questions/30997362/how-to-modify-jsonnode-in-java
		return convertEntityToJsonObject(newObjectNode);
	}
}
