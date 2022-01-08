/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import model.Vehicle;
import model.VehicleFactory;

/**
 *
 * @author marti
 */
final public class VehicleManager implements VehicleService {

	transient private String filePath = "";
	private List<Vehicle> vehicleList;
	transient private static VehicleManager manager;

	private VehicleManager() {
		vehicleList = new ArrayList<>();
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
	public int add(ObjectNode obj) {
		if (Objects.isNull(obj)) {

		} else {
			Vehicle newVehicle = VehicleFactory.getInstane().New_Vehicle(obj);
			if (Objects.isNull(newVehicle)) {

			} else {
				vehicleList.add(newVehicle);

			}
		}
		return 0;

	}

	@Override
	public boolean update(int id) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Vehicle searchById(int id) {
		if (id < 0) {
			throw new IllegalArgumentException("Id is negative ( must be positive instead)");
		}
		Vehicle ret = findUnique((n) -> n.getId() == id);
		return ret;
	}

	@Override
	public List<Vehicle> searchByName(String name) {
		if (Objects.isNull(name)) {
			throw new IllegalArgumentException("argument (name) is null");
		}
		List<Vehicle> ret = findAll((n) -> n.getName().equalsIgnoreCase(name));
		return ret;
	}

	@Override
	public boolean delete(int id) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private void show(List<Vehicle> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

	@Override
	public void showAll() {
		show(vehicleList);
	}

	@Override
	public void showAllOrderedByPrice() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private Vehicle findUnique(Predicate<Vehicle> predicate) {
		Vehicle ret = vehicleList.stream().filter(predicate).findFirst().orElse(null);
		return ret;
	}

	private List<Vehicle> findAll(Predicate<Vehicle> predicate) {
		List<Vehicle> ret = vehicleList.stream().filter(predicate).collect(Collectors.toList());
		return ret.isEmpty() ? null : ret;
	}

	public int size() {
		return vehicleList.size();
	}
}
