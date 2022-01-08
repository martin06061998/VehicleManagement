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
public abstract class Vehicle {

	int id;
	String name;
	Color color;
	float price;

	Vehicle() {
	}

	Vehicle(int id, String name, Color color, float price) {
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

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	abstract String serialize();

	@Override
	public String toString() {
		return "Vehicle{" + "id=" + id + ", name=" + name + ", color=" + color + ", price=" + price + '}';
	}
	
}
