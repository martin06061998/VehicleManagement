/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import Utilities.Inputter;
import Utilities.StringUtilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controller.VehicleService;
import controller.VehicleServiceProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
		String name = Inputter.inputNotBlankStr("Please enter name");
		int price = Inputter.inputInteger("Please enter price");
		List<Color> colors = Arrays.asList(model.Color.values());
		int color = int_getChoice(colors);
		String type = Inputter.inputNotBlankStr("Please enter type");
		Short yearOfManufactured = Inputter.inputShort("Please enter year of manufactured");
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("class", "car");
		node.put("name", name);
		node.put("price", price);
		node.put("color", color);
		node.put("type", type);
		node.put("year", yearOfManufactured);
		String reply = service.add(node);
		System.out.println(reply);

	}

	private static void Load_Add_SubForm2() {
		System.out.println("Welcome To Motorbike Add Form");
		String name = Inputter.inputNotBlankStr("Please enter name");
		int price = Inputter.inputInteger("Please enter price");
		List<Color> colors = Arrays.asList(model.Color.values());
		int color = int_getChoice(colors);
		String brand = Inputter.inputNotBlankStr("Please enter brand");
		float speed = Inputter.inputFloat("Please enter speed");
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put("class", "motorbike");
		node.put("name", name);
		node.put("price", price);
		node.put("color", color);
		node.put("brand", brand);
		node.put("speed", speed);
		String reply = service.add(node);
		System.out.println(reply);
	}

	public static void Load_Search_Menu() {
		System.out.println("Welcome To Search Page");;
		ArrayList<String> options = new ArrayList<>();
		options.add("Search by id");
		options.add("Search by name");
		options.add("Exit");
		while (true) {
			int choice = int_getChoice(options);
			switch (choice) {
				case 1:
					Load_Search_SubMenu1();
					break;
				case 2:
					Load_Search_SubMenu2();
					break;
				case 3:
					return;
			}
		}
	}

	private static void Load_Search_SubMenu1() {

	}

	private static void Load_Search_SubMenu2() {

	}
	
	public static void Load_Save_Menu(){
		service.saveDataToFile();
	}
	
	public static void Load_Load_Menu(){
		service.loadDataFromFile();
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
