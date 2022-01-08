/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.fasterxml.jackson.databind.node.ObjectNode;
import controller.VehicleManager;

/**
 *
 * @author marti
 */
class CarFactory extends I_Vehicle_Factory {

	CarFactory() {
		super();
	}

	@Override
	Car Create_Instance() {
		return new Car();
	}

	@Override
	String buildRegex() {
		return "\\{\"class\":\"car\",\"name\":\"[A-Za-z]+(\\s[A-Za-z]+)*\",\"price\":(\\d+\\.\\d+|\\d+),\"color\":[1-3],\"type\":\"[A-Za-z]+(\\s[A-Za-z]+)*\",\"year\":\\d{4}\\}";
	}

	@Override
	Car Create_Instance(ObjectNode obj) {
		Car newCar = Create_Instance();
		String name = obj.get("name").asText();
		float price = obj.get("price").shortValue();
		String type = obj.get("type").asText();
		short yearOfManufactured = obj.get("year").shortValue();
		int color = obj.get("color").asInt();
		
		newCar.setId(VehicleManager.getManager().size());
		newCar.setName(name);
		newCar.setPrice(price);
		newCar.setType(type);
		newCar.setYearOfManufactured(yearOfManufactured);
		newCar.setColor(Color.valueOf(color));
		return newCar;
	}

}
