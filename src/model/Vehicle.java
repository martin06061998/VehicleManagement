/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Objects;

/**
 *
 * @author marti
 */
public abstract class Vehicle {

	int id;
	String name;
	String color;
	long price;
	String brand;

	Vehicle() {
	}

	Vehicle(int id, String name, String color, long price, String brand) throws IllegalArgumentException, NullPointerException {
		Objects.requireNonNull(name, "arugument \"name\" should not be null");
		Objects.requireNonNull(brand, "arugument \"brand\" should not be null");
		Objects.requireNonNull(color, "argument \"color\" should not be null");
		if (name.length() < 4) {
			throw new IllegalArgumentException("argument \"name\" should be at least 4 characters");
		}
		if (price <= 0) {
			throw new IllegalArgumentException("Price should not be negative or zero");
		}
		this.id = id;
		this.name = name.toLowerCase();
		this.color = color.toLowerCase();
		this.price = price;
		this.brand = brand.toLowerCase();
	}

	void setId(int id) {
		this.id = id;
	}

	void setName(String name) throws IllegalArgumentException, NullPointerException {
		Objects.requireNonNull(name, "arugument \"name\" should not be null");
		if (name.length() < 4) {
			throw new IllegalArgumentException("argument \"name\" should be at least 4 characters");
		}
		this.name = name.toLowerCase();
	}

	void setColor(String color) throws NullPointerException {
		Objects.requireNonNull(color, "argument \"color\" should not be null");
		this.color = color.toLowerCase();
	}

	void setBrand(String brand) throws NullPointerException {
		Objects.requireNonNull(brand, "arugument \"brand\" should not be null");
		this.brand = brand.toLowerCase();
	}

	/**
	 *
	 * @param price
	 * @throws IllegalArgumentException if price is non-positive
	 */
	void setPrice(long price) throws IllegalArgumentException {
		if (price <= 0) {
			throw new IllegalArgumentException("Price should not be zero or negative");
		}
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public long getPrice() {
		return price;
	}

	public String getBrand() {
		return brand;
	}

	public abstract JsonNode serialize();
	

	
	
	@Override
	public String toString() {
		return "Id :" + id + "\n" + "Name: " + name + "\n" + "Color: " + color + "\n" + "Price: " + price + "\nBrand: " + brand;
	}

}
