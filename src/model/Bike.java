/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author marti
 */
public class Bike extends Vehicle {
	private String type;
	private short yearOfManufactured ;

	public Bike()	{};

	public Bike(String type, short yearOfManufactured) {
		this.type = type;
		this.yearOfManufactured = yearOfManufactured;
	}

	public Bike(String type, short yearOfManufactured, int id, String name, Color color, float price) {
		super(id, name, color, price);
		this.type = type;
		this.yearOfManufactured = yearOfManufactured;
	}

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public short getYearOfManufactured() {
		return yearOfManufactured;
	}

	public void setYearOfManufactured(short yearOfManufactured) {
		this.yearOfManufactured = yearOfManufactured;
	}
	
	@Override
	String serialize() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
