/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.fasterxml.jackson.databind.JsonNode;

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
		String regex =  "(?=^\\{\"[a-z]{2,10}\":((\\d{1,8}|\\d{1,8}\\.\\d{1,8})|(\"[A-Za-z\\s]{1,33}\"))(,\"[a-z]{2,10}\":((\\d{1,8}|\\d{1,8}\\.\\d{1,8})|(\"[A-Za-z\\s]{1,33}\"))){6}\\}$)(?=.*[,{]\"class\":\"motorbike\"[,}])(?=.*[,{]\"color\":\\d{1,3}[,}])(?=.*[,{]\"brand\":\"[A-Za-z\\s]{1,33}\"[,}])(?=.*[,{]\"name\":\"[A-Za-z\\s]{1,33}\"[,}])(?=.*[,{]\"price\":\\d{1,8}[,}])(?=.*[,{]\"speed\":\\d{1,8}|(\\d{1,8}\\.\\d{1,8})[,}])(?=.*[,{]\"id\":\\d{1,8}[,}]).*";
		return regex;
	}

	@Override
	Motorbike Create_Instance(JsonNode obj) {
		String name = obj.get("name").asText();
		int price =  obj.get("price").asInt();
		String brand = obj.get("brand").asText();
		float speed = obj.get("speed").floatValue();
		int color = obj.get("color").asInt();
		int id = obj.get("id").asInt();
		Motorbike newMotor = Create_Instance();
		newMotor.setId(id);
		newMotor.setName(name);
		newMotor.setPrice(price);
		newMotor.setBrand(brand);
		newMotor.setSpeed(speed);
		newMotor.setColor(Color.valueOf(color));
		return newMotor;
	}

}
