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
class MotorbikeFactory extends I_Vehicle_Factory<Motorbike> {

	MotorbikeFactory() {
		super();
		regexMap.put("class", "motorbike");
		regexMap.put("license", "([tT][rR][uU][eE])|([fF][aA][lL][sS][eE])");
		regexMap.put("speed", "\\d{1,8}|(\\d{1,8}\\.\\d{1,8})");
	}

	@Override
	Motorbike Create_Instance() {
		return new Motorbike();
	}

	@Override
	public Motorbike Create_Instance(JsonNode obj) {
		boolean isValidFormat = checkPattern(obj);
		if (isValidFormat) {
			Motorbike newMotor = Create_Instance();
			String id = obj.get("id").asText();
			String name = obj.get("name").asText();
			String color = obj.get("color").asText();
			String price = obj.get("price").asText();
			String brand = obj.get("brand").asText();
			String speed = obj.get("speed").asText();
			String license = obj.get("license").asText();

			newMotor.setId(Integer.parseInt(id));
			newMotor.setName(name);
			newMotor.setColor(Color.valueOf(Integer.parseInt(color)));
			newMotor.setPrice(Integer.parseInt(price));
			newMotor.setBrand(brand);
			newMotor.setSpeed(Float.parseFloat(speed));
			newMotor.setLicenseRequire(Boolean.parseBoolean(license));
			return newMotor;
		} else {
			throw new IllegalArgumentException("Format is invalid");
		}
	}

	@Override
	boolean reforge(Motorbike m, JsonNode obj) {
		return true;
	}
}
