/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.fasterxml.jackson.databind.JsonNode;
import model.Vehicle;

/**
 *
 * @author marti
 */
abstract class I_Vehicle_Factory<T extends Vehicle> {

	String regex;

	I_Vehicle_Factory() {
		regex = buildRegex();
	}
	abstract T Create_Instance();
	abstract T Create_Instance(JsonNode obj);
	abstract String buildRegex();
	String getRegex() {
		return regex;
	}
}
