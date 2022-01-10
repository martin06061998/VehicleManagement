/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import Utilities.Inputter;
import Utilities.StringUtilities;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controller.VehicleService;
import controller.VehicleServiceProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import model.Color;

/**
 *
 * @author marti
 */
public class Menu {

	static final VehicleService service = VehicleServiceProvider.getProvider().getService();

	public static ArrayList<String> Load_Main_Menu() {
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

	public static void Load_Delete_Form() {
		int id = Inputter.inputInteger("Please the id of the car you want to delete");
		JsonNode searchResult = service.searchById(id);
		String status = searchResult.get("status").asText();
		if (status.equals("success")) {
			System.out.println(searchResult.get("data").toPrettyString());
			String choice = Inputter.inputPatternStr("Do you you want to delete this car [y/n]", "[ynYn]");
			if (choice.equalsIgnoreCase("y")) {
				JsonNode deleteResponse = service.delete(id);
				System.out.println(deleteResponse.get("message").asText());
			} else {
				System.out.println("Cancle request, nothing is changed");
			}
		} else {
			System.out.println("id not found, nothing is changed");
		}
	}

	public static void Load_Add_Form() {
		ArrayList<String> options = new ArrayList<>();
		System.out.println("Welcome To Add Form");
		options.add("Add a car");
		options.add("Add a motorbike");
		options.add("Exit");
		while (true) {
			clearScreen();
			int choice = int_getChoice(options);
			switch (choice) {
				case 1:
					Load_Add_SubForm1();
					break;
				case 2:
					Load_Add_SubForm2();
					break;
				case 3:
					return;
			}
		}
	}

	private static void Load_Add_SubForm1() {
		System.out.println("Welcome To Car Add Form");
		List<Color> colors = Arrays.asList(model.Color.values());

		String name = Inputter.inputNotBlankStr("Please enter name");
		int color = int_getChoice(colors);
		int price = Inputter.inputInteger("Please enter price");
		String brand = Inputter.inputNotBlankStr("Please enter brand");
		String type = Inputter.inputNotBlankStr("Please enter type");
		int yearOfManufactured = Inputter.inputInteger("Please enter year of manufactured");

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode item = mapper.createObjectNode();
		item.put("class", "car");
		item.put("name", name);
		item.put("price", String.valueOf(price));
		item.put("color", String.valueOf(color));
		item.put("type", type);
		item.put("brand", brand);
		item.put("year", String.valueOf(yearOfManufactured));
		String reply = service.add(item);
		System.out.println(reply);

	}

	private static void Load_Add_SubForm2() {
		System.out.println("Welcome To Motorbike Add Form");
		List<Color> colors = Arrays.asList(model.Color.values());

		String name = Inputter.inputNotBlankStr("Please enter name");
		int color = int_getChoice(colors);
		int price = Inputter.inputInteger("Please enter price");
		String brand = Inputter.inputNotBlankStr("Please enter brand");
		float speed = Inputter.inputFloat("Please enter speed");
		boolean license = Inputter.inputBoolean("Is the motorbike require license:");

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode item = mapper.createObjectNode();
		item.put("class", "motorbike");
		item.put("name", name);
		item.put("price", String.valueOf(price));
		item.put("color", String.valueOf(color));
		item.put("brand", brand);
		item.put("speed", String.valueOf(speed));
		item.put("license", String.valueOf(license));
		String reply = service.add(item);
		System.out.println(reply);
	}

	public static void Load_Search_Menu() {
		System.out.println("Welcome To Search Memu");
		ArrayList<String> options = new ArrayList<>();
		options.add("Search by id");
		options.add("Search by name");

		int choice = int_getChoice(options);
		switch (choice) {
			case 1:
				Load_Search_SubMenu1();
				break;
			case 2:
				Load_Search_SubMenu2();
				break;
			default:
				System.out.println("");
				break;
		}

	}

	private static void Load_Search_SubMenu1() {
		System.out.println("Search By Id");
		int choice = Inputter.inputInteger("Please enter the id of the car you want to search");
		JsonNode response = service.searchById(0);
		System.out.println(response.toPrettyString());
	}

	private static void Load_Search_SubMenu2() {
		System.out.println("Search By Name");
		String name = Inputter.inputNotBlankStr("Please enter the name of the car");
		JsonNode response = service.searchByName(name);
		System.out.println(response.toPrettyString());
	}

	public static void Load_Save_Menu() {
		System.out.println("Saving.....");
		service.saveDataToFile();
	}

	public static void Load_Load_Menu() {
		System.out.println("Loading data.....");
		service.loadDataFromFile();
	}

	public static void Load_Show_Menu() {
		System.out.println("Welcome To Showw Menu");;
		ArrayList<String> options = new ArrayList<>();
		options.add("Show All");
		options.add("Show All Ordered By Descending Order");
		int choice = int_getChoice(options);
		switch (choice) {
			case 1:
				service.showAll();
				break;
			case 2:
				service.showAllOrderedByPrice();
				break;
			default:
				break;
		}

	}

	public static void Load_Update_Menu() {
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<String> options = new ArrayList<>();
		options.add("1 Yes");
		options.add("2 All No");
		Scanner sc = new Scanner(System.in);
	}

	public static <T> T ref_getChoice(List<T> options) {
		int response;
		do {
			response = int_getChoice(options);
		} while (response < 0 || response > options.size());
		return options.get(response - 1);

	}

	public static int int_getChoice(List<?> options) {
		System.out.println(StringUtilities.Generate_Repeated_String("*", 65));
		for (int i = 0; i < options.size(); i++) {
			System.out.printf("%-3d.  %s\n", i + 1, options.get(i));
		}
		System.out.println(StringUtilities.Generate_Repeated_String("*", 65));
		int response = 0;
		do {
			response = Inputter.inputInteger("**(Note: Your options from 1 - " + options.size() + ")");
		} while (response < 0 || response > options.size());
		return response;
	}

	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}
