package com.springflexcounchdb.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {

	/*
	 * { "selector": { "year": {"$gt": 2010} }, "fields": ["_id", "_rev", "year",
	 * "title"], "sort": [{"year": "asc"}], "limit": 2, "skip": 0,
	 * "execution_stats": true }
	 */

	Map<String, Object> selector;
	List<String> fields;
	List<Map<String, String>> sort;
	Integer limit;
	Boolean execution_stats;
	
	public Map<String, Object> getProperties() {
		 Map<String, Object>  map = new HashMap<>();
		 map.put("selector", this.selector);
		 map.put("fields", this.fields);
		 map.put("sort", this.sort);
		 map.put("limit", this.limit);
		 map.put("execution_stats", execution_stats);
		return map;
	}

}
