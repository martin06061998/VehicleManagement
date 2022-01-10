/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import Utilities.StringUtilities;
import java.util.Objects;

/**
 *
 * @author marti
 */
public abstract class Vehicle {

	int id;
	String name;
	Color color;
	int price;
	String brand;

	Vehicle() {
	}

	Vehicle(int id, String name, Color color, int price, String brand) throws IllegalArgumentException, NullPointerException {
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
		this.name = StringUtilities.Standard_Lowercase_Str(name);
		this.color = color;
		this.price = price;
		this.brand = StringUtilities.Standard_Lowercase_Str(brand);
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) throws IllegalArgumentException, NullPointerException {
		Objects.requireNonNull(name, "arugument \"name\" should not be null");
		if (name.length() < 4) {
			throw new IllegalArgumentException("argument \"name\" should be at least 4 characters");
		}
		this.name =StringUtilities.Standard_Lowercase_Str(name);
	}

	public void setColor(Color color) throws NullPointerException {
		Objects.requireNonNull(color, "argument \"color\" should not be null");
		this.color = color;
	}

	public void setBrand(String brand) throws NullPointerException {
		Objects.requireNonNull(brand, "arugument \"brand\" should not be null");
		this.brand = StringUtilities.Standard_Lowercase_Str(brand);
	}

	/**
	 *
	 * @param price
	 * @throws IllegalArgumentException if price is non-positive
	 */
	public void setPrice(int price) throws IllegalArgumentException {
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

	public Color getColor() {
		return color;
	}

	public int getPrice() {
		return price;
	}

	public String getBrand() {
		return brand;
	}

	public abstract String serialize();

	@Override
	public String toString() {
		return "Id :" + id + "\n" + "Name: " + name + "\n" + "Color: " + color + "\n" + "Price: " + price + "\nBrand"+ brand;
	}

}
