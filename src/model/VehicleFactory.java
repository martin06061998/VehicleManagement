/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.HashMap;
import java.util.Objects;
import model.Vehicle;

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

	public Vehicle New_Vehicle(ObjectNode obj) {
		String type = checkType(obj.toString());
		Vehicle ret = null;
		if (Objects.isNull(type)) {
			return ret;
		}
		ret = factory.factoryList.get(type).Create_Instance(obj);
		return ret;
	}

	private String checkType(String target) {
		for (String key : factory.factoryList.keySet()) {
			String regex = factory.factoryList.get(key).getRegex();
			if (target.matches(regex)) {
				return key;
			}
		}
		return null;
	}

}
