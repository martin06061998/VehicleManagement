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
public class Car extends Vehicle {
	private String type;
	private short yearOfManufactured ;

	public Car() {};

	public Car(String type, short yearOfManufactured) {
		this.type = type;
		this.yearOfManufactured = yearOfManufactured;
	}

	public Car(String type, short yearOfManufactured, int id, String name, Color color, float price) {
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

	@Override
	public String toString() {
		return super.toString() + "Car{" + "type=" + type + ", yearOfManufactured=" + yearOfManufactured + '}';
	}
	
}
