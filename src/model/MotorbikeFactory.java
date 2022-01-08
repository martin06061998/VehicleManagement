/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.fasterxml.jackson.databind.node.ObjectNode;
import controller.VehicleManager;
import model.Color;
import model.Motorbike;

/**
 *
 * @author marti
 */
class MotorbikeFactory extends I_Vehicle_Factory {

	MotorbikeFactory() {
		super();
	}

	@Override
	Motorbike Create_Instance() {
		return new Motorbike();
	}

	@Override
	String buildRegex() {
		return "\\{\"class\":\"motorbike\",\"name\":\"[A-Za-z]+(\\s[A-Za-z]+)*\".\"price\":(\\d+\\.\\d+|\\d+),\"color\":[1-3],\"brand\":\"[A-Za-z]+(\\s[A-Za-z]+)*\",\"speed\":(\\d+|\\d+\\.\\d+)\\}";
	}

	@Override
	Motorbike Create_Instance(ObjectNode obj) {
		String name = obj.get("name").asText();
		float price = obj.get("price").shortValue();
		String brand = obj.get("brand").asText();
		float speed = obj.get("speed").floatValue();
		int color = obj.get("color").asInt();
		Motorbike newMotor = Create_Instance();
		newMotor.setId(VehicleManager.getManager().size());
		newMotor.setName(name);
		newMotor.setPrice(price);
		newMotor.setBrand(brand);
		newMotor.setSpeed(speed);
		newMotor.setColor(Color.valueOf(color));
		return newMotor;
	}

}
