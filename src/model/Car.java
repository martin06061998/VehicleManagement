/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Objects;

/**
 *
 * @author marti
 */
public class Car extends Vehicle {

	private String type;
	private short yearOfManufactured;

	Car() {
	}

	;

	public Car(String type, short yearOfManufactured, int id, String name, Color color, int price) throws IllegalArgumentException, NullPointerException {
		super(id, name, color, price);
		Objects.requireNonNull(type, "arugument \"type\" should not be null");
		if (type.length() < 4) {
			throw new IllegalArgumentException("argument \"type\" should be at least 4 characters");
		}
		int year = Calendar.getInstance().get(Calendar.YEAR);
		if (yearOfManufactured <= 1960 || yearOfManufactured > year) {
			throw new IllegalArgumentException("argument \"year\" is invalid");
		}
		this.type = type;
		this.yearOfManufactured = yearOfManufactured;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) throws IllegalArgumentException, NullPointerException {
		Objects.requireNonNull(type, "arugument \"type\" should not be null");
		if (type.length() < 4) {
			throw new IllegalArgumentException("argument \"type\" should be at least 4 characters");
		}
		this.type = type;
	}

	public short getYearOfManufactured() {
		return yearOfManufactured;
	}

	public void setYearOfManufactured(short yearOfManufactured) throws IllegalArgumentException {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		if (yearOfManufactured <= 1960 || yearOfManufactured > year) {
			throw new IllegalArgumentException("argument \"year\" is invalid");
		}
		this.yearOfManufactured = yearOfManufactured;
	}

	@Override
	public String serialize() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode ret = mapper.createObjectNode();
		ret.put("type", type);
		ret.put("class", "car");
		ret.put("id", id);
		ret.put("name", name);
		ret.put("price", price);
		ret.put("year", yearOfManufactured);
		ret.put("color", color.getValue());
		return ret.toString();
	}

	@Override
	public String toString() {
		return "Class: Car\n" + super.toString() + "\ntype: " + type + "\n" + "Year of manufactured: " + yearOfManufactured;
	}

}
