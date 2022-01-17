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
		regexMap.put("class", "(?i)motorbike");
		regexMap.put("license", "([tT][rR][uU][eE])|([fF][aA][lL][sS][eE])");
		regexMap.put("speed", "\\d{1,15}|(\\d{1,15}\\.\\d{1,15})");
	}

	@Override
	Motorbike createInstance() {
		return new Motorbike();
	}

	@Override
	public Motorbike createInstance(JsonNode data) {
		boolean isValidFormat = checkPattern(data);
		if (isValidFormat) {
			Motorbike newMotor = createInstance();
			String id = data.get("id").asText();
			String name = data.get("name").asText();
			String color = data.get("color").asText();
			String price = data.get("price").asText();
			String brand = data.get("brand").asText();
			String speed = data.get("speed").asText();
			String license = data.get("license").asText();

			newMotor.setId(Integer.parseInt(id));
			newMotor.setName(name);
			newMotor.setColor(color);
			newMotor.setPrice(Long.parseLong(price));
			newMotor.setBrand(brand);
			newMotor.setSpeed(Float.parseFloat(speed));
			newMotor.setLicenseRequire(Boolean.parseBoolean(license));
			return newMotor;
		} else {
			throw new IllegalArgumentException("Format is invalid");
		}
	}

	@Override
	Motorbike reforge(JsonNode request) {
		boolean isValidFormat = checkPattern(request);
		if (isValidFormat) {
			Motorbike reforgedMotor = createInstance();
			reforgedMotor.setId(Integer.parseInt(request.get("id").asText()));
			reforgedMotor.setName(request.get("name").asText());
			reforgedMotor.setColor(request.get("color").asText());
			reforgedMotor.setPrice(Integer.parseInt(request.get("price").asText()));
			reforgedMotor.setBrand(request.get("brand").asText());
			reforgedMotor.setSpeed(Double.parseDouble(request.get("speed").asText()));
			reforgedMotor.setLicenseRequire(Boolean.parseBoolean(request.get("license").asText()));
			return reforgedMotor;
		} else {
			throw new IllegalArgumentException("invalid format");
		}
	}

}
