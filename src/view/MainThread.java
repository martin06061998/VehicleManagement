/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.VehicleManager;
import java.io.IOException;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import static view.Menu.getIntChoice;

/**
 *
 * @author marti
 */
public class MainThread {

	public static final Logger logger = Logger.getLogger("VehicleManager");

	static {
		try {
			//LogManager.getLogManager().reset();
			Logger globalLogger = Logger.getLogger("global");
			Handler[] handlers = globalLogger.getHandlers();
			for (Handler handler : handlers) {
				globalLogger.removeHandler(handler);
			}
			logger.setLevel(Level.ALL);
			FileHandler fh = new java.util.logging.FileHandler("java.log", true);
			fh.setFormatter(new SimpleFormatter());
			fh.setLevel(Level.FINER);
			logger.addHandler(fh);
		} catch (IOException | SecurityException ex) {
			Logger.getLogger(VehicleManager.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		ArrayList<String> mainMenu = Menu.loadMainMenu();
		while (true) {
			try {
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
			} catch (RuntimeException ex) {
				logger.throwing("MainThread", "main", ex);
			}
		}

	}
}
