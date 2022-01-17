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
class CarFactory extends I_Vehicle_Factory<Car> {

	CarFactory() {
		super();
		regexMap.put("type", "[A-Za-z\\s]{1,33}");
		regexMap.put("year", "\\d{4}");
		regexMap.put("class", "(?i)car");
	}

	@Override
	Car createInstance() {
		return new Car();
	}

	@Override
	Car createInstance(JsonNode target) throws IllegalArgumentException {
		boolean isValidFormat = checkPattern(target);
		if (isValidFormat) {
			Car newCar = createInstance();
			String id = target.get("id").asText();
			String name = target.get("name").asText();
			String color = target.get("color").asText();
			String price = target.get("price").asText();
			String brand = target.get("brand").asText();
			String type = target.get("type").asText();
			String year = target.get("year").asText();

			newCar.setId(Integer.parseInt(id));
			newCar.setName(name);
			newCar.setColor(color);
			newCar.setPrice(Long.parseLong(price));
			newCar.setBrand(brand);
			newCar.setType(type);
			newCar.setYearOfManufactured(Integer.parseInt(year));
			return newCar;
		} else {
			throw new IllegalArgumentException("Format is invalid");
		}
	}

	@Override
	Car reforge(JsonNode request) throws NullPointerException, IllegalArgumentException {
		boolean isValidFormat = checkPattern(request);
		if (isValidFormat) {
			Car reforgedCar = createInstance();
			reforgedCar.setId(Integer.parseInt(request.get("id").asText()));
			reforgedCar.setName(request.get("name").asText());
			reforgedCar.setColor((request.get("color").asText()));		
			reforgedCar.setPrice(Long.parseLong(request.get("price").asText()));
			reforgedCar.setBrand(request.get("brand").asText());
			reforgedCar.setType(request.get("type").asText());
			reforgedCar.setYearOfManufactured(Integer.parseInt(request.get("year").asText()));
			return reforgedCar;
		} else {
			throw new IllegalArgumentException("invalid format");
		}

	}
}
