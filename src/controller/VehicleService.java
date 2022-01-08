/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import model.Vehicle;

/**
 *
 * @author marti
 */
public interface VehicleService {

	void loadDataFromFile();

	void saveDataToFile();

	int add(ObjectNode obj);

	boolean update(int id);

	Vehicle searchById(int d);

	List<Vehicle> searchByName(String name);

	boolean delete(int id);

	void showAll();

	void showAllOrderedByPrice();
}
