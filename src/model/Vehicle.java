/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.fasterxml.jackson.databind.node.ObjectNode;
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

	Vehicle() {
	}

	Vehicle(int id, String name, Color color, int price) throws IllegalArgumentException,NullPointerException {
		Objects.requireNonNull(name, "arugument \"name\" should not be null");
		if (name.length() < 4) {
			throw new IllegalArgumentException("argument \"name\" should be at least 4 characters");
		}
		Objects.requireNonNull(color, "argument \"color\" should not be null");
		if (price <= 0) {
			throw new IllegalArgumentException("Price should not be negative or zero");
		}
		this.id = id;
		this.name = name;
		this.color = color;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws IllegalArgumentException, NullPointerException {
		Objects.requireNonNull(name, "arugument \"name\" should not be null");
		if (name.length() < 4) {
			throw new IllegalArgumentException("argument \"name\" should be at least 4 characters");
		}
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) throws NullPointerException {
		Objects.requireNonNull(color, "argument \"color\" should not be null");
		this.color = color;
	}

	public int getPrice() {
		return price;
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

	public abstract String serialize();

	@Override
	public String toString() {
		return "id :" + id + "\n" + "name: " + name + "\n" + "Color: " + color + "\n" + "Price: " + price;
	}

}
