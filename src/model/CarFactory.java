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
		regexMap.put("class", "car");
	}

	@Override
	Car Create_Instance() {
		return new Car();
	}

	@Override
	public Car Create_Instance(JsonNode obj) throws IllegalArgumentException {
		boolean isValidFormat = checkPattern(obj);
		if (isValidFormat) {
			Car newCar = Create_Instance();
			String id = obj.get("id").asText();
			String name = obj.get("name").asText();
			String color = obj.get("color").asText();
			String price = obj.get("price").asText();
			String brand = obj.get("brand").asText();
			String type = obj.get("type").asText();
			String year = obj.get("year").asText();

			newCar.setId(Integer.parseInt(id));
			newCar.setName(name);
			newCar.setColor(Color.valueOf(Integer.parseInt(color)));
			newCar.setPrice(Integer.parseInt(price));
			newCar.setBrand(brand);
			newCar.setType(type);
			newCar.setYearOfManufactured(Integer.parseInt(year));
			return newCar;
		} else {
			throw new IllegalArgumentException("Format is invalid");
		}
	}

	@Override
	boolean reforge(Car c, JsonNode obj) throws NullPointerException, IllegalArgumentException {
		return true;
	}
}
