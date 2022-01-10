/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author marti
 */
public class Motorbike extends Vehicle {

	private String brand;
	private float speed;

	Motorbike() {
	}

	Motorbike(String brand, float speed, int id, String name, Color color, int price) throws IllegalArgumentException, NullPointerException {
		super(id, name, color, price);
		Objects.requireNonNull(brand, "brand should not be null");
		if (brand.length() < 4) {
			throw new IllegalArgumentException("brand should be at least 4 characters");
		}
		if (speed <= 0) {
			throw new IllegalArgumentException("speed should not positive");
		}
		this.brand = brand;
		this.speed = speed;
	}

	public void setBrand(String brand) throws IllegalArgumentException, NullPointerException {
		Objects.requireNonNull(brand, "brand should not be null");
		if (brand.length() < 4) {
			throw new IllegalArgumentException("brand should be at least 4 characters");
		}
		this.brand = brand;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) throws IllegalArgumentException {
		if (speed <= 0) {
			throw new IllegalArgumentException("speed should not positive");
		}
		this.speed = speed;
	}

	public void makeSound() {
		System.out.println("tin tin tin");
	}

	@Override
	public String serialize() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode ret = mapper.createObjectNode();
		ret.put("price", price);
		ret.put("color", color.getValue());
		ret.put("name", name);
		ret.put("class", "motorbike");
		ret.put("id", id);
		ret.put("brand", brand);
		ret.put("speed", speed);
		return ret.toString();
	}

	@Override
	public String toString() {
		return "Class: Motorbike\n" + super.toString() + "\nbrand: " + brand + "\n" + "Speed: " + speed;
	}

}
