/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dbo.FileHandlerManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import model.Vehicle;
import model.VehicleFactory;
import static view.MainThread.logger;

/**
 *
 * @author martin
 */
final public class VehicleManager implements VehicleService, Serializable {

	private static final long serialVersionUID = 2022685098267757690L;
	private List<Vehicle> vehicleList;
	private static transient final int CAPACITY = 50;
	private static transient VehicleManager manager;
	private final transient int MAXIMUM_NUMBER_OF_VEHICLES = Integer.MAX_VALUE;
	private final VehicleManager.IDAllocator idAllocator = new VehicleManager.IDAllocator();

	private VehicleManager() {
	}

	public static VehicleManager getManager() {
		if (Objects.isNull(manager)) {
			manager = new VehicleManager();
			manager.vehicleList = new ArrayList<>(CAPACITY);
		}
		return manager;
	}

	@Override
	public void loadDataFromFile() {
		try {
			List<String> data = FileHandlerManager.getInstance().readText();
			ObjectMapper mapper = new ObjectMapper();
			for (String record : data) {
				JsonNode node = mapper.readTree(record);
				int id = Integer.parseInt(node.get("id").asText());
				boolean isAllocated = idAllocator.isAllocated(id);
				if (isAllocated) {
					continue;
				}
				Vehicle newVehicle = VehicleFactory.getInstane().createVehicle(node);
				vehicleList.add(newVehicle);
				idAllocator.add(id);

			}
		} catch (Exception ex) {
			logger.throwing("VehicleManager", "VehicleManager.loadDataFromFile", ex);
		}

	}

	@Override
	public void saveDataToFile() {
		String message = null;
		try {
			List<String> data = prepareDataForSaving();
			Objects.requireNonNull(data);
			FileHandlerManager.getInstance().writeText(data);
			message = "save successfully";
		} catch (Exception ex) {
			message = "cannot save data to file";
			logger.throwing("VehicleManager", "VehicleManager.saveDataToFile", ex);
		} finally {
			System.out.println(message);
		}

	}

	@Override
	public JsonNode add(JsonNode data) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode reply = mapper.createObjectNode();
		String message = "";
		String status = "";
		try {
			Objects.requireNonNull(data);
			ObjectNode vehicleData = (ObjectNode) mapper.readTree(data.toString());
			int id = manager.idAllocator.allocate();
			vehicleData.put("id", id);
			Vehicle newVehicle = VehicleFactory.getInstane().createVehicle(vehicleData);
			manager.vehicleList.add(newVehicle);
			manager.idAllocator.add(id);
			status = "success";
			message = "add successfully";
		} catch (IllegalArgumentException ex) {
			status = "fail";
			message = "add failed due to " + ex.getMessage();
		} catch (Exception ex) {
			status = "status";
			message = "bad request";
			logger.throwing("VehicleManager", "VehicleManager.add", ex);
		}
		reply.put("status", status);
		reply.put("message", message);
		return reply;
	}

	@Override
	public JsonNode update(JsonNode request) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode reply = mapper.createObjectNode();
		String message, status;
		try {
			Objects.requireNonNull(request);
			final int id = Integer.parseInt(request.get("id").asText());
			boolean isAllocated = manager.idAllocator.isAllocated(id);
			if (!isAllocated) {
				throw new IllegalArgumentException("Vehicle does not exist”");
			}
			Vehicle oldVehicle = findById(id);
			Vehicle reforgedVehicle = VehicleFactory.getInstane().reforge(oldVehicle, request);
			int index = manager.vehicleList.indexOf(oldVehicle);
			manager.vehicleList.set(index, reforgedVehicle); // old vehicle only change when there is no exceptions occur
			status = "success";
			message = "sucessfully updated";
		} catch (IllegalArgumentException ex) {
			status = "fail";
			message = "failed due to " + ex.getMessage();
		} catch (RuntimeException ex) {
			status = "fail";
			message = "bad request";
			logger.throwing("VehicleManager", "VehicleManager.update", ex);
		}
		reply.put("status", status);
		reply.put("message", message);
		return reply;
	}

	@Override
	public JsonNode searchById(int id) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode reply = mapper.createObjectNode();
		ArrayNode data = mapper.createArrayNode();
		boolean isValidId = manager.idAllocator.isAllocated(id);
		if (!isValidId) {
			reply.put("status", "fail");
			reply.put("message", "vehicle does not exist");
			reply.set("data", data);
			return reply;
		}
		Vehicle vehicle = findById(id);
		data.add(vehicle.serialize());
		reply.put("status", "success");
		reply.put("message", "a vehicle has been found");
		reply.set("data", data);
		return reply;
	}

	@Override
	public JsonNode searchByName(String name) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode reply = mapper.createObjectNode();
		ArrayNode data = mapper.createArrayNode();
		String message = "";
		String status = "";
		try {
			Objects.requireNonNull(name);
			final String standardName = name.toLowerCase();
			List<Vehicle> result = findAll((n) -> (n.getName().toLowerCase()).contains(standardName));
			if (result.isEmpty()) {
				status = "success";
				message = "not found any vehicles";
			} else {
				result.sort((vehicle1, vehicle2) -> vehicle2.getName().compareTo(vehicle1.getName()));
				status = "success";
				message = "found some vehicles";
				for (Vehicle v : result) {
					data.add(v.serialize());
				}
			}

		} catch (RuntimeException ex) {
			logger.throwing("VehicleManager", "VehicleManager.searchByName", ex);
			status = "fail";
			message = "bad request";
		} finally {
			reply.put("status", status);
			reply.put("message", message);
			reply.set("data", data);
		}
		return reply;
	}

	@Override
	public JsonNode delete(int id) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode reply = mapper.createObjectNode();
		boolean isIdAllocated = manager.idAllocator.isAllocated(id);
		if (!isIdAllocated) {
			reply.put("status", "fail");
			reply.put("message", "vehicle does not exist");
			return reply;
		}
		Vehicle vehicle = findById(id);
		manager.vehicleList.remove(vehicle);
		manager.idAllocator.delete(id);
		reply.put("status", "success");
		reply.put("message", "successfully deleted");
		return reply;
	}

	private JsonNode show(List<Vehicle> list) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode reply = mapper.createObjectNode();
		ArrayNode data = mapper.createArrayNode();
		for (Vehicle v : list) {
			data.add(v.serialize());
		}
		reply.set("data", data);
		return reply;
	}

	@Override
	public JsonNode showAll() {
		return show(manager.vehicleList);
	}

	@Override
	public JsonNode showAllOrderedByPrice() {
		List<Vehicle> result = sort((v1, v2) -> Long.compare(v2.getPrice(), v1.getPrice()));
		return show(result);
	}

	private Vehicle findById(int id) {
		return findUnique((v) -> v.getId() == id);
	}

	private Vehicle findUnique(Predicate<Vehicle> predicate) {
		Vehicle response = manager.vehicleList.stream().filter(predicate).findFirst().orElse(null);
		return response;
	}

	private List<Vehicle> findAll(Predicate<Vehicle> predicate) {
		List<Vehicle> response = manager.vehicleList.stream().filter(predicate).collect(Collectors.toList());
		return response;
	}

	public int size() {
		return manager.vehicleList.size();
	}

	private List<String> prepareDataForSaving() {
		int size = manager.vehicleList.size();
		List<String> response = null;
		if (size != 0) {
			Function<Vehicle, String> function = (vehicle) -> vehicle.serialize().toString();
			response = manager.vehicleList.stream().map(function).collect(Collectors.toList());
		}
		return response;
	}

	private List<Vehicle> sort(Comparator<Vehicle> comparator) {
		if (manager.vehicleList.isEmpty()) {
			return manager.vehicleList;
		}
		List<Vehicle> response = new ArrayList<>(manager.vehicleList);
		response.sort(comparator);
		return response;
	}

	private class IDAllocator implements Serializable {

		private static final long serialVersionUID = 2022685098267757691L;
		private final HashSet<Integer> allocatedId;

		private IDAllocator() {
			allocatedId = new HashSet<>();
		}

		private boolean isAllocated(int id) {
			return allocatedId.contains(id);
		}

		@SuppressWarnings("empty-statement")
		private int allocate() {
			int size = vehicleList.size();
			if (size == MAXIMUM_NUMBER_OF_VEHICLES) {
				return -1;
			}
			int id = vehicleList.size();
			while (allocatedId.contains(id)) {
				id++;
			}
			return id;
		}

		private boolean add(int id) {
			allocatedId.add(id);
			return true;
		}

		private boolean delete(int id) {
			allocatedId.remove(id);
			return true;
		}

	}
}
