/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Utilities.StringUtilities;
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
	public Vehicle New_Vehicle(JsonNode obj) throws IllegalArgumentException, NullPointerException, ClassNotFoundException {
		boolean errorFree = true;
		Vehicle ret = null;
		if (obj.has("class") && obj.get("class").isTextual()) {
			String key = obj.get("class").asText();
			Class<?> clazz = Class.forName("model." + StringUtilities.Standard_CamelCase_Str(key, "\\s"));
			if (factoryList.containsKey(clazz)) 
				ret = factoryList.get(clazz).Create_Instance(obj);
			else 
				errorFree = false;

		} 
		else 
			errorFree = false;
		if(errorFree == false)
			throw new IllegalArgumentException("invalid format");
		return ret;
	}

	public <T extends Vehicle> boolean reforge(T vehicle, JsonNode obj) {
		factory.factoryList.get(vehicle.getClass()).reforge(vehicle, obj);
		return true;
	}
}
