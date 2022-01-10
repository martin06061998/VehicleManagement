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
		regexMap.put("color", "\\d{1,3}");

	}

	abstract T Create_Instance();

	public abstract T Create_Instance(JsonNode obj);

	abstract boolean reforge(T vehicle, JsonNode obj);

	final boolean checkPattern(JsonNode obj) {
		boolean isValidFormat = true;
		int numberOfKeys = 0;
		String regex, target, key;
		Iterator<String> keys = obj.fieldNames();
		while (keys.hasNext()) {
			key = keys.next();
			if (!regexMap.containsKey(key)) {
				isValidFormat = false;
				break;
			}
			regex = regexMap.get(key);
			target = obj.get(key).asText();
			if (!target.matches(regex)) {
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
