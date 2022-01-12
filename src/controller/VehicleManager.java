/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dbo.FileHandlerManager;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import model.Vehicle;
import model.VehicleFactory;

/**
 *
 * @author martin
 */
final public class VehicleManager implements VehicleService {

	private final transient String filePath = "src\\dbo\\vehicle.txt";
	private List<Vehicle> vehicleList;
	private static final int CAPACITY = 50;
	private static transient VehicleManager manager;
	private final transient int MAXIMUM_NUMBER_OF_VEHICLES = Integer.MAX_VALUE;
	private final transient VehicleManager.IDAllocator idAllocator = new VehicleManager.IDAllocator();

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
			List<String> data = FileHandlerManager.getInstance().readText(filePath);
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
		} catch (IOException | RuntimeException  | ClassNotFoundException exception) {
			//System.out.println(exception.getMessage());
		}
	}

	@Override
	public void saveDataToFile() {
		String message = null;
		try {
			List<String> data = prepareDataForSaving();
			Objects.requireNonNull(data);
			FileHandlerManager.getInstance().writeText(data, filePath);
			message = "save successfully";
		} catch (IOException | RuntimeException ex) {
			message = "cannot save data to file";
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
			System.out.println(data.asText());
			ObjectNode vehicleData = (ObjectNode) mapper.readTree(data.toString());
			int id = idAllocator.allocate();
			vehicleData.put("id", id);
			Vehicle newVehicle = VehicleFactory.getInstane().createVehicle(vehicleData);
			vehicleList.add(newVehicle);
			idAllocator.add(id);
			status = "success";
			message = "add successfully";
		} catch (IllegalArgumentException ex) {
			status = "fail";
			message = "add failed due to " + ex.getMessage();
		} catch (RuntimeException | JacksonException | ClassNotFoundException ex) {
			status = "status";
			message = "bad request";
		}
		reply.put("status", status);
		reply.put("message", message);
		return reply;
	}

	@Override
	public JsonNode update(JsonNode request
	) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode reply = mapper.createObjectNode();
		String message, status;
		try {
			Objects.requireNonNull(request);
			final int id = Integer.parseInt(request.get("id").asText());
			boolean isAllocated = idAllocator.isAllocated(id);
			if (!isAllocated) {
				throw new IllegalArgumentException("Vehicle does not exist”");
			}
			Vehicle oldVehicle = findById(id);
			Vehicle reforgedVehicle = VehicleFactory.getInstane().reforge(oldVehicle, request);
			int index = vehicleList.indexOf(oldVehicle);
			vehicleList.set(index, reforgedVehicle); // old vehicle only change when there is no exceptions occur
			status = "success";
			message = "sucessfully updated";
		} catch (IllegalArgumentException ex) {
			status = "fail";
			message = "failed due to " + ex.getMessage();
		} catch (RuntimeException e) {
			status = "fail";
			message = "bad request";
		}
		reply.put("status", status);
		reply.put("message", message);
		return reply;
	}

	@Override
	public JsonNode searchById(int id
	) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode reply = mapper.createObjectNode();
		boolean isValidId = idAllocator.isAllocated(id);
		if (!isValidId) {
			reply.put("status", "fail");
			reply.put("message", "vehicle does not exist");
			reply.set("data", mapper.createObjectNode());
			return reply;
		}
		Vehicle vehicle = findById(id);
		ObjectNode data = (ObjectNode) vehicle.serialize();
		data.put("class", vehicle.getClass().getSimpleName());
		reply.put("status", "success");
		reply.put("message", "a vehicle has been found");
		reply.set("data", data);

		return reply;
	}

	@Override
	public JsonNode searchByName(String name) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode reply = mapper.createObjectNode();
		try {
			Objects.requireNonNull(name);
			final String standardName = name.toLowerCase();
			List<Vehicle> result = findAll((n) -> standardName.contains(n.getName().toLowerCase()));
			Objects.requireNonNull(result);
			result.sort((vehicle1, vehicle2) -> vehicle2.getName().compareTo(vehicle1.getName()));
			reply.put("status", "success");
			reply.put("message", "found");
			ArrayNode data = mapper.valueToTree(result);
			reply.set("data", data);
		} catch (RuntimeException exception) {
			reply.put("status", "fail");
			reply.put("message", "invalid input");
			ArrayNode data = mapper.createArrayNode();
			reply.set("data", data);
		}
		return reply;
	}

	@Override
	public JsonNode delete(int id
	) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode reply = mapper.createObjectNode();
		boolean isIdAllocated = idAllocator.isAllocated(id);
		if (!isIdAllocated) {
			reply.put("status", "fail");
			reply.put("message", "vehicle does not exist");
			return reply;
		}
		Vehicle vehicle = findById(id);
		vehicleList.remove(vehicle);
		idAllocator.delete(id);
		reply.put("status", "success");
		reply.put("message", "successfully deleted");
		return reply;
	}

	private void show(List<Vehicle> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i) + "\n");
		}
	}

	@Override
	public void showAll() {
		show(vehicleList);
	}

	@Override
	public void showAllOrderedByPrice() {
		List<Vehicle> result = sort((v1, v2) -> v2.getPrice() - v1.getPrice());
		if (Objects.isNull(result)) {
			System.out.println("Nothing to show");
		} else {
			show(result);
		}
	}

	private Vehicle findById(int id) {
		return findUnique((v) -> v.getId() == id);
	}

	private Vehicle findUnique(Predicate<Vehicle> predicate) {
		Vehicle response = vehicleList.stream().filter(predicate).findFirst().orElse(null);
		return response;
	}

	private List<Vehicle> findAll(Predicate<Vehicle> predicate) {
		List<Vehicle> response = vehicleList.stream().filter(predicate).collect(Collectors.toList());
		return response.isEmpty() ? null : response;
	}

	public int size() {
		return vehicleList.size();
	}

	private List<String> prepareDataForSaving() {
		int size = vehicleList.size();
		List<String> response = null;
		if (size != 0) {
			Function<Vehicle, String> function = (vehicle) -> vehicle.serialize().toString();
			response = vehicleList.stream().map(function).collect(Collectors.toList());
		}
		return response;
	}

	private List<Vehicle> sort(Comparator<Vehicle> comparator) {
		if (vehicleList.isEmpty()) {
			return null;
		}
		List<Vehicle> response = new ArrayList<>(vehicleList);
		response.sort(comparator);
		return response;
	}

	private class IDAllocator {

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
