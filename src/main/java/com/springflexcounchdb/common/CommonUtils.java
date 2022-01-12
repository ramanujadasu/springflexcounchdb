package com.springflexcounchdb.common;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CommonUtils {

	public static final String CREATED_BY = getUsername();
	public static final String LAST_MODIFIED_BY = getUsername();
	public static String CREATED_DATE = Instant.now().toString();
	public static String LAST_MODIFIED_DATE = Instant.now().toString();

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
			newObjectNode = (ObjectNode) newBody;
			newObjectNode.put("name", objectUpdateBodyNode.get("name"));
			newObjectNode.put("id", objectUpdateBodyNode.get("id"));
			newObjectNode.put("age", objectUpdateBodyNode.get("age"));
			//TODO
			ArrayNode nodeAddress = mapper.createArrayNode();
			for(JsonNode node : objectUpdateBodyNode.get("address")) {
				//JsonNode addNode = mapper.createObjectNode();
				//addNode = node.get("addressId");
				ObjectNode obj = mapper.createObjectNode();
				obj.put("addressId", node.get("addressId"));
				nodeAddress.add(obj);
			}
			newObjectNode.put("address", nodeAddress);
		} catch (IOException e) {
			System.err.println("Exception: "+e.getMessage());
		} 
		return convertEntityToJsonObject(newObjectNode);
	}
	
}
