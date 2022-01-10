/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;

/**
 *
 * @author marti
 */
final public class VehicleFactory {

	private HashMap<String, I_Vehicle_Factory> factoryList;
	private static VehicleFactory factory;

	private VehicleFactory() {

	}

	public static VehicleFactory getInstane() {
		if (factory == null) {
			factory = new VehicleFactory();
			factory.factoryList = new HashMap<>();
			factory.factoryList.put("car", new CarFactory());
			factory.factoryList.put("motorbike", new MotorbikeFactory());
		}
		return factory;
	}

	public Vehicle New_Vehicle(JsonNode obj) throws IllegalArgumentException {
		Vehicle ret = null;
		if (obj.has("class") && obj.get("class").isTextual()) {
			String key = obj.get("class").asText();
			if (factory.factoryList.containsKey(key)) {
				String regex = factory.factoryList.get(key).getRegex();
				String target = obj.toString();
				if (target.matches(regex)) {
					return factory.factoryList.get(key).Create_Instance(obj);
				}
				else
					throw new IllegalArgumentException("invalid format");
			}else
				throw new IllegalArgumentException("type of vehicle not found");

		} else {
			throw new IllegalArgumentException("invalid format");
		}
	}
}
