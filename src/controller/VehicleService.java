/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import model.Vehicle;

/**
 *
 * @author marti
 */
public interface VehicleService {
	void loadDataFromFile()		;
	void saveDataToFile();
	int add();
	boolean update(int id);
	Vehicle searchById(int d);
	List<Vehicle> searchByName(String name);
	boolean delete(int id);
	void showAll(List<Vehicle> list);
	void showAllOrderedByPrice(List<Vehicle> list);
}
