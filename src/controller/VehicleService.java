/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 *
 * @author marti
 */
public interface VehicleService {

	void loadDataFromFile();

	void saveDataToFile();

	String add(ObjectNode obj);

	boolean update(int id);

	ObjectNode searchById(int d);

	ObjectNode searchByName(String name);

	boolean delete(int id);

	void showAll();

	void showAllOrderedByPrice();
}
