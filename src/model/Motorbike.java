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
public class Motorbike extends Vehicle {

	private String brand;
	private float speed;

	public Motorbike() {
	}

	;

	public Motorbike(String brand, float speed) {
		this.brand = brand;
		this.speed = speed;
	}

	public Motorbike(String brand, float speed, int id, String name, Color color, float price) {
		super(id, name, color, price);
		this.brand = brand;
		this.speed = speed;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void makeSound() {
		System.out.println("tin tin tin");
	}

	@Override
	String serialize() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
