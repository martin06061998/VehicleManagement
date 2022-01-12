/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static java.lang.System.exit;
import java.util.ArrayList;
import static view.Menu.getIntChoice;

/**
 *
 * @author marti
 */
public class MainThread {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		ArrayList<String> mainMenu = Menu.loadMainMenu();
		while (true) {
			//Menu.clearScreen();
			int choice = getIntChoice(mainMenu);
			switch (choice) {
				case 1:
					Menu.loadLoadDataMenu();
					break;
				case 2:
					Menu.loadAddForm();
					break;
				case 3:
					Menu.loadUpdateMenu();
					break;
				case 4:
					Menu.loadDeleteMenu();
					break;
				case 5:
					Menu.loadSearchMenu();
					break;
				case 6:
					Menu.loadShowMenu();
					break;
				case 7:
					Menu.loadSaveMenu();
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
