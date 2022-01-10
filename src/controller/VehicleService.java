/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 *
 * @author marti
 */
public interface VehicleService {

	void loadDataFromFile();

	void saveDataToFile();

	String add(ObjectNode obj);

	JsonNode update(JsonNode obj);

	JsonNode searchById(int d);

	JsonNode searchByName(String name);

	JsonNode delete(int id);

	void showAll();

	void showAllOrderedByPrice();
}
