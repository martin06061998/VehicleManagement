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
		return "(?=^\\{\"[a-z]{2,10}\":((\\d{1,8}|\\d{1,8}\\.\\d{1,8})|(\"[A-Za-z\\s]{1,33}\"))(,\"[a-z]{2,10}\":((\\d{1,8}|\\d{1,8}\\.\\d{1,8})|(\"[A-Za-z\\s]{1,33}\"))){6}\\}$)(?=.*[,{]\"class\":\"car\"[,}])(?=.*[,{]\"color\":\\d{1,3}[,}])(?=.*[,{]\"type\":\"[A-Za-z\\s]{1,33}\"[,}])(?=.*[,{]\"name\":\"[A-Za-z\\s]{1,33}\"[,}])(?=.*[,{]\"price\":\\d{1,8}[,}])(?=.*[,{]\"year\":\\d{4}[,}])(?=.*[,{]\"id\":\\d{1,8}[,}]).*";
	}

	@Override
	Car Create_Instance(ObjectNode obj) {
		Car newCar = Create_Instance();
		String name = obj.get("name").asText();
		int id = obj.get("id").asInt();
		int price = obj.get("price").asInt();
		String type = obj.get("type").asText();
		short yearOfManufactured = obj.get("year").shortValue();
		int color = obj.get("color").asInt();
		
		newCar.setId(id);
		newCar.setName(name);
		newCar.setPrice(price);
		newCar.setType(type);
		newCar.setYearOfManufactured(yearOfManufactured);
		newCar.setColor(Color.valueOf(color));
		return newCar;
	}

}
