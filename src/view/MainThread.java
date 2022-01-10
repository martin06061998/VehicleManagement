/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

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
					Menu.Load_Load_Menu();
					break;
				case 2:
					Menu.Load_Add_Form();
					break;
				case 3:
					Menu.Load_Update_Menu();
					break;
				case 4:
					Menu.Load_Delete_Form();
					break;
				case 5:
					Menu.Load_Search_Menu();
					break;
				case 6:
					Menu.Load_Show_Menu();
					break;
				case 7:
					Menu.Load_Save_Menu();
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
