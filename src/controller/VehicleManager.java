/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import java.util.Objects;
import model.Vehicle;

/**
 *
 * @author marti
 */
final public class VehicleManager implements VehicleService {

	private final String filePath = "";
	private List<Vehicle> vehicleList;
	private static VehicleManager manager;

	private VehicleManager() {
	}

	public static VehicleManager getManager() {
		if (Objects.isNull(manager)) {
			manager = new VehicleManager();
		}
		return manager;
	}

	@Override
	public void loadDataFromFile() {
		System.out.println("File loaded successfully");
	}

	@Override
	public void saveDataToFile() {
		System.out.println("File save successfully");
	}

	@Override
	public int add() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean update(int id) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Vehicle searchById(int d) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public List<Vehicle> searchByName(String name) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean delete(int id) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void showAll(List<Vehicle> list) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void showAllOrderedByPrice(List<Vehicle> list) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
