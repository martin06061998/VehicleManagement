/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Utilities.StringUtilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Calendar;
import java.util.Objects;

/**
 *
 * @author marti
 */
public class Car extends Vehicle {

	String type;
	int yearOfManufactured;

	Car() {
	}

	;

	public Car(int id, String name, Color color, int price, String brand, String type, int yearOfManufactured) throws IllegalArgumentException, NullPointerException {
		super(id, name, color, price, brand);
		Objects.requireNonNull(type, "arugument \"type\" should not be null");
		int year = Calendar.getInstance().get(Calendar.YEAR);
		if (yearOfManufactured <= 1980 || yearOfManufactured > year) {
			throw new IllegalArgumentException("argument \"year\" is invalid");
		}
		this.type = StringUtilities.Standard_Lowercase_Str(type);
		this.yearOfManufactured = yearOfManufactured;
	}

	public void setType(String type) throws NullPointerException {
		Objects.requireNonNull(type, "arugument \"type\" should not be null");
		this.type = StringUtilities.Standard_Lowercase_Str(type);
	}

	public void setYearOfManufactured(int yearOfManufactured) throws IllegalArgumentException {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		if (yearOfManufactured <= 1960 || yearOfManufactured > year) {
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
	public String serialize() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode ret = mapper.createObjectNode();
		ret.put("class", "car");
		ret.put("id", String.valueOf(id));
		ret.put("name", name);
		ret.put("price", String.valueOf(price));
		ret.put("color", String.valueOf(color.getValue()));
		ret.put("brand", brand);
		ret.put("type", type);
		ret.put("year", String.valueOf(yearOfManufactured));
		return ret.toString();
	}

	@Override
	public String toString() {
		return "Class: Car\n" + super.toString() + "\ntype: " + type + "\n" + "Year of manufactured: " + yearOfManufactured;
	}

}
