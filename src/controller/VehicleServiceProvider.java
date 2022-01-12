/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.HashMap;
import java.util.Objects;

/**
 *
 * @author marti
 */
final public class VehicleServiceProvider {

	private HashMap<String, VehicleManager> managerList;
	private static VehicleServiceProvider provider;

	private VehicleServiceProvider() {
	}

	;

	public static VehicleServiceProvider getProvider() {
		if (Objects.isNull(provider)) {
			provider = new VehicleServiceProvider();
			provider.managerList = new HashMap<>();
			provider.managerList.put("default", VehicleManager.getManager());
		}
		return provider;
	}

	public VehicleService getService() {
		return managerList.get("default");
	}

	/*public VehicleService getServer(String type) {
		return managerList.get(type);
	}*/
}
