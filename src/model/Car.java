/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import utilities.StringUtilities;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Calendar;
import java.util.Objects;

/**
 *
 * @author marti
 */
class Car extends Vehicle {

	String type;
	int yearOfManufactured;

	Car() {
	}

	;

	Car(int id, String name, String color, int price, String brand, String type, int yearOfManufactured) throws IllegalArgumentException, NullPointerException {
		super(id, name, color, price, brand);
		Objects.requireNonNull(type, "arugument \"type\" should not be null");
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		if (yearOfManufactured <= 1980 || yearOfManufactured > currentYear) {
			throw new IllegalArgumentException("argument \"year\" is invalid");
		}
		this.type = StringUtilities.toCamelCase(type);
		this.yearOfManufactured = yearOfManufactured;
	}

	void setType(String type) throws NullPointerException {
		Objects.requireNonNull(type, "arugument \"type\" should not be null");
		this.type = StringUtilities.toCamelCase(type);
	}

	void setYearOfManufactured(int yearOfManufactured) throws IllegalArgumentException {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		if (yearOfManufactured <= 1960 || yearOfManufactured > currentYear) {
			throw new IllegalArgumentException("argument \"year\" is invalid");
		}
		this.yearOfManufactured = yearOfManufactured;
	}

	public String getType() {
		return type;
	}

	public int getYearOfManufactured() {
		return yearOfManufactured;
	}

	@Override
	public JsonNode serialize() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode response = mapper.createObjectNode();
		response.put("class", "car");
		response.put("id", String.valueOf(id));
		response.put("name", name);
		response.put("price", String.valueOf(price));
		response.put("color", color);
		response.put("brand", brand);
		response.put("type", type);
		response.put("year", String.valueOf(yearOfManufactured));
		return response;
	}

	@Override
	public String toString() {
		return "Class: Car\n" + super.toString() + "\ntype: " + type + "\n" + "Year of manufactured: " + yearOfManufactured;
	}

}
