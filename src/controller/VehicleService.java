/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.fasterxml.jackson.databind.JsonNode;

/**
 *
 * @author marti
 */
public interface VehicleService {

	void loadDataFromFile();

	void saveDataToFile();

	JsonNode add(JsonNode data);

	JsonNode update(JsonNode data);

	JsonNode searchById(int id);

	JsonNode searchByName(String name);

	JsonNode delete(int id);

	void showAll();

	void showAllOrderedByPrice();
}
