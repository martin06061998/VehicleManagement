/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.VehicleService;
import controller.VehicleServiceProvider;

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
		service.loadDataFromFile();
	}

}