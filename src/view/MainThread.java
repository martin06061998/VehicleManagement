/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controller.VehicleService;
import controller.VehicleServiceProvider;
import static java.lang.System.exit;
import java.util.ArrayList;
import static view.Menu.int_getChoice;

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
		ArrayList<String> mainMenu = Menu.Load_Main_Menu();
		while (true) {
			//Menu.clearScreen();
			int choice = int_getChoice(mainMenu);
			switch (choice) {
				case 1:
					VehicleServiceProvider.getProvider().getService().loadDataFromFile();
					break;
				case 2:
					Menu.Navigate_To_Add_Menu();
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
					break;
				case 7:
					VehicleServiceProvider.getProvider().getService().saveDataToFile();
					break;
				case 8:
					System.out.println("good bye");
					exit(0);
				default:
					break;
			}
		}
	}

}
