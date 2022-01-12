/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author marti
 */
abstract class I_Vehicle_Factory<T extends Vehicle> {

	HashMap<String, String> regexMap;

	I_Vehicle_Factory() {
		regexMap = new HashMap<>();
		regexMap.put("id", "\\d{1,9}");
		regexMap.put("name", "[A-Za-z\\s]{1,33}");
		regexMap.put("price", "\\d{1,8}");
		regexMap.put("brand", "[A-Za-z\\s]{1,33}");
		regexMap.put("color", "(?i)(red)|(green)|(blue)");

	}

	abstract T createInstance();

	abstract T createInstance(JsonNode target);

	abstract T reforge(JsonNode request);
	
	
	
	final boolean checkPattern(JsonNode data) {
		boolean isValidFormat = true;
		int numberOfKeys = 0;
		String regex, value, key;
		Iterator<String> keys = data.fieldNames();
		while (keys.hasNext()) {
			key = keys.next();
			if (!regexMap.containsKey(key)) {
				isValidFormat = false;
				break;
			}
			regex = regexMap.get(key);
			value = data.get(key).asText();
			if (!value.matches(regex)) {
				isValidFormat = false;
				break;
			}
			numberOfKeys++;
		}
		if (numberOfKeys < regexMap.size()) {
			isValidFormat = false;
		}
		return isValidFormat;
	}
}
