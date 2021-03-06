/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import utilities.Inputter;
import utilities.StringUtilities;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controller.VehicleService;
import controller.VehicleServiceProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author marti
 */
class Menu {

	private static final VehicleService service = VehicleServiceProvider.getProvider().getService();
	private static final int BLOCK_SIZE = 14;

	static ArrayList<String> loadMainMenu() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Load data from file");
		options.add("Add new vehicle");
		options.add("Update vehicle");
		options.add("Delete vehicle");
		options.add("Search vehicle");
		options.add("Show vehicle list");
		options.add("Save data to file");
		options.add("Exit");
		return options;
	}

	static void loadDeleteMenu() {
		int id = Inputter.inputInteger("Please the id of the vehicle you want to delete");
		JsonNode searchResult = service.searchById(id);
		String status = getStatus(searchResult);
		if (status.equals("success")) {
			printVehicle(searchResult);
			String clazz = searchResult.get("data").get(0).get("class").asText();
			String choice = Inputter.inputPatternStr("Do you you want to delete this " + clazz + " [y/n]", "[yYnN]");
			if (choice.equalsIgnoreCase("y")) {
				JsonNode deleteResult = service.delete(id);
				printMessage(deleteResult);
			} else {
				System.out.println("Cancle request, nothing is changed");
			}
		} else {
			printMessage(searchResult);
		}
	}

	static void loadAddForm() {
		ArrayList<String> options = new ArrayList<>();
		System.out.println("Welcome To Add Form");
		options.add("Add a car");
		options.add("Add a motorbike");
		options.add("Exit");
		while (true) {
			//clearScreen();
			int choice = getIntChoice(options);
			switch (choice) {
				case 1:
					loadAddCarForm();
					break;
				case 2:
					loadAddMotorbikeForm();
					break;
				case 3:
					return;
			}
		}
	}

	private static void loadAddCarForm() {
		System.out.println("Welcome To Car Add Form");
		String name = Inputter.inputNotBlankStr("Please enter name");
		String color = Inputter.inputNotBlankStr("Please enter color");
		long price = Inputter.inputLong("Please enter price");
		String brand = Inputter.inputNotBlankStr("Please enter brand");
		String type = Inputter.inputNotBlankStr("Please enter type");
		int yearOfManufactured = Inputter.inputInteger("Please enter year of manufactured");

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode item = mapper.createObjectNode();
		item.put("class", "car");
		item.put("name", name);
		item.put("price", String.valueOf(price));
		item.put("color", color);
		item.put("type", type);
		item.put("brand", brand);
		item.put("year", String.valueOf(yearOfManufactured));
		JsonNode reply = service.add(item);
		printMessage(reply);

	}

	private static void loadAddMotorbikeForm() {
		System.out.println("Welcome To Motorbike Add Form");
		String name = Inputter.inputNotBlankStr("Please enter name");
		String color = Inputter.inputNotBlankStr("Please enter color");
		long price = Inputter.inputLong("Please enter price");
		String brand = Inputter.inputNotBlankStr("Please enter brand");
		double speed = Inputter.inputDouble("Please enter speed");
		boolean license = Inputter.inputBoolean("Is the motorbike require license:");

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode item = mapper.createObjectNode();
		item.put("class", "motorbike");
		item.put("name", name);
		item.put("price", String.valueOf(price));
		item.put("color", color);
		item.put("brand", brand);
		item.put("speed", String.valueOf(speed));
		item.put("license", String.valueOf(license));
		JsonNode reply = service.add(item);
		printMessage(reply);
	}

	static void loadSearchMenu() {
		System.out.println("Welcome To Search Memu");
		ArrayList<String> options = new ArrayList<>();
		options.add("Search by id");
		options.add("Search by name");
		int choice = getIntChoice(options);
		switch (choice) {
			case 1:
				loadSearchByIdMenu();
				break;
			case 2:
				loadSearchByNameMenu();
				break;
			default:
				break;
		}
	}

	private static void loadSearchByIdMenu() {
		System.out.println("Search By Id");
		int choice = Inputter.inputInteger("Please enter the id of the vehicle you want to search");
		JsonNode response = service.searchById(choice);
		printMessage(response);
		printVehicle(response);
	}

	private static void loadSearchByNameMenu() {
		System.out.println("Search By Name");
		String name = Inputter.inputNotBlankStr("Please enter the name of the vehicle");
		JsonNode response = service.searchByName(name);
		printMessage(response);
		printVehicle(response);
	}

	static void loadSaveMenu() {
		System.out.println("Saving.....");
		service.saveDataToFile();
	}

	static void loadLoadDataMenu() {
		System.out.println("Loading data.....");
		service.loadDataFromFile();
	}

	static void loadShowMenu() {
		System.out.println("Welcome To Showw Menu");;
		ArrayList<String> options = new ArrayList<>();
		options.add("Show All");
		options.add("Show All Ordered By Descending Order");
		int choice = getIntChoice(options);
		switch (choice) {
			case 1:
				JsonNode reply1 = service.showAll();
				printVehicle(reply1);
				break;
			case 2:
				JsonNode reply2 = service.showAllOrderedByPrice();
				printVehicle(reply2);
				break;
			default:
				break;
		}
	}

	static void loadUpdateMenu() {
		int id = Inputter.inputInteger("Please enter the id of the vehicle you want to update");
		ObjectNode response = (ObjectNode) service.searchById(id);
		if (getStatus(response).equals("fail")) {
			printMessage(response);
		} else {
			ObjectNode vehicle = (ObjectNode) response.get("data").get(0);
			Iterator<String> keys = vehicle.fieldNames();
			String key;
			while (keys.hasNext()) {
				key = keys.next();
				if (key.equals("id") || key.equals("class")) {
					continue;
				}
				String value = vehicle.get(key).asText();
				if (!key.equals("year")) {
					value = StringUtilities.toPretty(value);
				}
				String choice = Inputter.inputPatternStr("Do you you want to change " + key + " ( Old value: " + value + " ) [y/n]", "[ynYN]");
				if (choice.equalsIgnoreCase("y")) {
					String newValue = Inputter.inputNotBlankStr("Please enter new " + key);
					vehicle.put(key, newValue);
				}
			}
			JsonNode updateResponse = service.update(vehicle);
			printMessage(updateResponse);
			if (getStatus(updateResponse).equals("success")) {
				System.out.println("Update Result");
				printVehicle(response);
			}
		}
	}

	private static void printMessage(JsonNode node) {
		System.out.println(node.get("message").asText());
	}

	private static String getStatus(JsonNode node) {
		return node.get("status").asText();
	}

	private static void printVehicle(JsonNode node) {
		LinkedHashMap<String, Integer> keyMap = new LinkedHashMap<>();
		HashMap<String, LinkedHashMap<String, Integer>> keyMapTable = new HashMap<>();
		JsonNode dataElement = node.get("data");
		if (dataElement.isArray()) {
			String oldClass = null;
			if (dataElement.size() == 0) {
				System.out.println("Nothing to show");
			} else {
				for (JsonNode element : dataElement) {
					if (Objects.isNull(oldClass)) {
						oldClass = (element.get("class")).asText();
						keyMap = calculateSize(oldClass, node);
						keyMapTable.put(oldClass, keyMap);
						printHeader(keyMap);
					} else {
						String newClass = element.get("class").asText();
						if (!oldClass.equals(newClass)) {
							System.out.println("");
							if (keyMapTable.containsKey(newClass)) {
								keyMap = keyMapTable.get(newClass);
							} else {
								keyMap = calculateSize(newClass, node);
							}
							printHeader(keyMap);
						}
						oldClass = newClass;
					}
					printElement(element, keyMap);
				}
			}
		}

	}

	private static void printElement(JsonNode element, LinkedHashMap<String, Integer> keyMap) {
		Iterator<String> fieldNames = element.fieldNames();
		while (fieldNames.hasNext()) {
			String field = fieldNames.next();
			int numberOfCharacter = keyMap.get(field);
			String value = element.get(field).asText();
			if (!field.contains("id") && !field.contains("year")) {
				value = StringUtilities.toPretty(value);
			}
			int padding = (numberOfCharacter - value.length()) / 2;
			String leftAlign = StringUtilities.generateRepeatedString(" ", padding);
			String rightAlign = StringUtilities.generateRepeatedString(" ", numberOfCharacter - padding - value.length());
			String outputString = "|" + leftAlign + value + rightAlign + "|";
			System.out.print(outputString);
		}
		System.out.println();
	}

	private static void printHeader(LinkedHashMap<String, Integer> keyMap) {
		for (String key : keyMap.keySet()) {
			int numberOfCharacter = keyMap.get(key);
			int padding = (numberOfCharacter - key.length()) / 2;
			String leftAlign = StringUtilities.generateRepeatedString(" ", padding);
			String rightAlign = StringUtilities.generateRepeatedString(" ", numberOfCharacter - padding - key.length());
			String outputString = "|" + leftAlign + key.toUpperCase() + rightAlign + "|";
			System.out.print(outputString);
		}
		System.out.println();
	}

	private static LinkedHashMap<String, Integer> calculateSize(String clazz, JsonNode node) {
		JsonNode dataElement = node.get("data");
		LinkedHashMap<String, Integer> keyMap = new LinkedHashMap<>();
		Iterator<JsonNode> iterator = dataElement.iterator();
		while (iterator.hasNext()) {
			JsonNode element = iterator.next();
			Iterator<String> fieldNames = element.fieldNames();
			if (!(element.get("class").asText()).equals(clazz)) {
				continue;
			}
			while (fieldNames.hasNext()) {
				String key = fieldNames.next();
				String value = element.get(key).asText();
				if (keyMap.containsKey(key)) {
					int newLength = (int) Math.ceil(value.length() / BLOCK_SIZE) * BLOCK_SIZE + BLOCK_SIZE;
					int oldLength = keyMap.get(key);
					if (newLength > oldLength) {
						keyMap.put(key, newLength);
					}
				} else {
					int newLength = (int) Math.ceil(value.length() / BLOCK_SIZE) * BLOCK_SIZE + BLOCK_SIZE;
					keyMap.put(key, newLength);
				}
			}
		}
		return keyMap;
	}

	static <T> T getRefChoice(List<T> options) {
		int response;
		do {
			response = getIntChoice(options);
		} while (response < 0 || response > options.size());
		return options.get(response - 1);

	}

	static int getIntChoice(List<?> options) {
		System.out.println(StringUtilities.generateRepeatedString("*", 65));
		for (int i = 0; i < options.size(); i++) {
			System.out.printf("%-3d.  %s\n", i + 1, options.get(i));
		}
		System.out.println(StringUtilities.generateRepeatedString("*", 65));
		int response = 0;
		do {
			response = Inputter.inputInteger("**(Note: Your options from 1 - " + options.size() + ")");
		} while (response < 0 || response > options.size());
		return response;
	}
}
