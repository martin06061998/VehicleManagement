/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 *
 * @author marti
 */
class Motorbike extends Vehicle {

	float speed;
	boolean licenseRequire;

	Motorbike() {
	}

	Motorbike(int id, String name, String color, int price, String brand, float speed, boolean licenseRequire) throws IllegalArgumentException, NullPointerException {
		super(id, name, color, price, brand);
		if (speed <= 0) {
			throw new IllegalArgumentException("speed should not positive");
		}
		this.licenseRequire = licenseRequire;
		this.speed = speed;
	}

	void setLicenseRequire(boolean licenseRequire) {
		this.licenseRequire = licenseRequire;
	}

	void setSpeed(float speed) throws IllegalArgumentException {
		if (speed <= 0) {
			throw new IllegalArgumentException("speed should not positive");
		}
		this.speed = speed;
	}

	public void makeSound() {
		System.out.println("tin tin tin");
	}

	public float getSpeed() {
		return speed;
	}

	public boolean isLicenseRequire() {
		return licenseRequire;
	}

	@Override
	public JsonNode serialize() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode response = mapper.createObjectNode();
		response.put("class", "motorbike");
		response.put("id", String.valueOf(id));
		response.put("name", name);
		response.put("price", String.valueOf(price));
		response.put("color", color);
		response.put("brand", brand);
		response.put("license", String.valueOf(licenseRequire));
		response.put("speed", String.valueOf(speed));
		return response;
	}

	@Override
	public String toString() {
		return "Class: Motorbike: tin tin tin " + "+\n" + super.toString() + "\n" + "Speed: " + speed + "\nLicense require: " + licenseRequire;
	}

}
