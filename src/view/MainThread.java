/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.VehicleService;
import controller.VehicleServiceProvider;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 *
 * @author marti
 */
public class MainThread {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		VehicleService service = VehicleServiceProvider.getProvider().getService();
		JsonObject vehicle1 = Json.createObjectBuilder()
			.add("class", "car")
			.add("id", "12")
			.add("name", "Marin Model X")
			.add("color", "red")
			.add("price", "sdfsdf")
			.add("type", "electric")
			.add("yearOfManufactured", "23")
			.build();
		JsonObject vehicle2 = Json.createObjectBuilder()
			.add("class", "motorbike")
			.add("id", "89")
			.add("name", "Jack Model XYZ")
			.add("color", "blue")
			.add("price", "68000")
			.add("brand", "Martin")
			.add("speed", "230")
			.build();
		JsonArray arr = Json.createArrayBuilder()
			.add(vehicle1)
			.add(vehicle2)
			.build();
		int choice = 2;
		switch (choice) {
			case 1:
				service.loadDataFromFile();
				break;
			case 2:
				service.add(arr);
				service.showAll();
				break;
			case 3:
				service.update(0);
				break;
			case 4:
				service.delete(0);
				break;
			case 5:
				service.searchById(12);
				break;
			case 6:
				service.showAll();

			case 7:
				service.saveDataToFile();
				break;
			default:
				break;
		}
	}

}
