/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import utilities.StringUtilities;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;

/**
 *
 * @author marti
 */
final public class VehicleFactory {

	private HashMap<Class<? extends Vehicle>, I_Vehicle_Factory> factoryList;
	private static VehicleFactory factory;

	private VehicleFactory() {

	}

	public static VehicleFactory getInstane() {
		if (factory == null) {
			factory = new VehicleFactory();
			factory.factoryList = new HashMap<>();
			factory.factoryList.put(Car.class, new CarFactory());
			factory.factoryList.put(Motorbike.class, new MotorbikeFactory());
		}
		return factory;
	}

	@SuppressWarnings("element-type-mismatch")
	public Vehicle createVehicle(JsonNode data) throws IllegalArgumentException, NullPointerException, ClassNotFoundException {
		boolean errorFree = true;
		Vehicle response = null;
		if (data.has("class") && data.get("class").isTextual()) {
			String key = data.get("class").asText();
			Class<?> clazz = Class.forName("model." + StringUtilities.toCamelCase(key));
			if (factoryList.containsKey(clazz)) 
				response = factoryList.get(clazz).createInstance(data);
			else 
				errorFree = false;

		} 
		else 
			errorFree = false;
		if(errorFree == false)
			throw new IllegalArgumentException("invalid format");
		return response;
	}

	public <T extends Vehicle> Vehicle reforge(T oldVehicle,JsonNode request) throws IllegalArgumentException, NullPointerException {
		return factory.factoryList.get(oldVehicle.getClass()).reforge(request);
	} 
}
