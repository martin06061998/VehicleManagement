/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Utilities.StringUtilities;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dbo.FileHandlerManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import model.Motorbike;
import model.Vehicle;
import model.VehicleFactory;

/**
 *
 * @author marti
 */
final public class VehicleManager implements VehicleService {

	private final transient String filePath = "src\\dbo\\vehicle.txt";
	private List<Vehicle> vehicleList;
	transient private static VehicleManager manager;

	private VehicleManager() {
	}

	public static VehicleManager getManager() {
		if (Objects.isNull(manager)) {
			manager = new VehicleManager();
			manager.vehicleList = new ArrayList<>();
		}
		return manager;
	}

	@Override
	public void loadDataFromFile() {
		try {
			List<String> data = FileHandlerManager.getInstance().readText(filePath);
			ObjectMapper mapper = new ObjectMapper();
			for (String s : data) {
				JsonNode node = mapper.readTree(s);
				Vehicle newVehicle = VehicleFactory.getInstane().New_Vehicle(node);
				vehicleList.add(newVehicle);

			}
		} catch (IOException | IllegalArgumentException | NullPointerException | ClassNotFoundException ex) {
			System.out.println(ex.getMessage());
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
		} catch (IOException | NullPointerException ex) {
			message = "cannot save data to file";
		}
	}

	@Override
	public JsonNode add(ObjectNode obj) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode reply = mapper.createObjectNode();
		try {
			Objects.requireNonNull(obj);
			obj.put("id", vehicleList.size());
			Vehicle newVehicle = VehicleFactory.getInstane().New_Vehicle(obj);
			vehicleList.add(newVehicle);
			reply.put("status", "success");
			reply.put("message", "successfully added");
		} catch (IllegalArgumentException | ClassNotFoundException | NullPointerException e) {
			reply.put("status", "success");
			reply.put("message", "Add to failed due to invalid data type");

		}
		return reply;
	}

	@Override
	public JsonNode update(JsonNode obj) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode reply = mapper.createObjectNode();
		try {
			String id = obj.get("id").asText();
			Vehicle v = findUnique((n) -> n.getId() == Integer.parseInt(id));
			Objects.requireNonNull(v);
			VehicleFactory.getInstane().reforge(v, obj);
			reply.put("status", "success");
			reply.put("message", "sucessfully update");
		} catch (IllegalArgumentException | NullPointerException e) {
			reply.put("status", "fail");
			reply.put("message", "something is wrong, no change is made");
		}

		return reply;
	}

	@Override
	public JsonNode searchById(int id) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode reply = mapper.createObjectNode();
		if (id < 0) {
			reply.put("status", "fail");
			reply.put("message", "id is invalid");
			reply.set("data", mapper.createObjectNode());
			return reply;
		}
		Vehicle ret = findUnique((n) -> n.getId() == id);
		if (Objects.isNull(ret)) {
			reply.put("status", "fail");
			reply.put("message", "no vehicle have such an id");
			reply.set("data", mapper.createObjectNode());
		} else {
			ObjectNode vehicle = (ObjectNode) ret.serialize();
			vehicle.put("class",   ret.getClass().getSimpleName());
			reply.put("status", "success");
			reply.put("message", "a vehicle has been found");
			reply.set("data", vehicle);
		}
		return reply;
	}

	@Override
	public JsonNode searchByName(String name) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode reply = mapper.createObjectNode();
		try {
			Objects.requireNonNull(name);
			List<Vehicle> ret = findAll((n) -> StringUtilities.Standard_Lowercase_Str(n.getName()).contains(StringUtilities.Standard_Lowercase_Str(name)));
			Objects.requireNonNull(ret);
			ret.sort((v1, v2) -> v2.getName().compareTo(v1.getName()));
			reply.put("status", "success");
			reply.put("message", "found");
			ArrayNode data = mapper.valueToTree(ret);
			reply.set("data", data);
		} catch (IllegalArgumentException | NullPointerException e) {
			reply.put("status", "fail");
			reply.put("message", "invalid inputl");
			ArrayNode data = mapper.createArrayNode();
			reply.set("data", data);
		}
		return reply;
	}

	@Override
	public JsonNode delete(int id) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode reply = mapper.createObjectNode();
		if (id < 0) {
			reply.put("status", "fail");
			reply.put("message", "id is invalid, no change is made");
			return reply;
		}
		Vehicle ret = findUnique((n) -> n.getId() == id);
		if (Objects.isNull(ret)) {
			reply.put("status", "fail");
			reply.put("message", "id not found, no change is made");
		} else {
			//JsonNode node = mapper.valueToTree(ret);
			vehicleList.remove(ret);
			reply.put("status", "success");
			reply.put("message", "successfully deleted");
		}
		return reply;
	}

	private void show(List<Vehicle> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
			if (list.get(i) instanceof Motorbike) {
				((Motorbike) list.get(i)).makeSound();
			}
		}
	}

	@Override
	public void showAll() {
		show(vehicleList);
	}

	@Override
	public void showAllOrderedByPrice() {
		List<Vehicle> lst = sort((v1, v2) -> v2.getPrice() - v1.getPrice());
		if (Objects.isNull(lst)) {
			System.out.println("Nothing to show");
		} else {
			show(lst);
		}
	}

	private Vehicle findUnique(Predicate<Vehicle> predicate) {
		Vehicle ret = vehicleList.stream().filter(predicate).findFirst().orElse(null);
		return ret;
	}

	private List<Vehicle> findAll(Predicate<Vehicle> predicate) {
		List<Vehicle> ret = vehicleList.stream().filter(predicate).collect(Collectors.toList());
		return ret.isEmpty() ? null : ret;
	}

	public int size() {
		return vehicleList.size();
	}

	private List<String> prepareDataForSaving() {
		int size = vehicleList.size();
		List<String> ret = null;
		if (size != 0) {
			Function<Vehicle, String> fun = (n) -> n.serialize().toString();
			ret = vehicleList.stream().map(fun).collect(Collectors.toList());
		}
		return ret;
	}

	private List<Vehicle> sort(Comparator<Vehicle> comparator) {
		if (vehicleList.isEmpty()) {
			return null;
		}
		List<Vehicle> ret = new ArrayList<>(vehicleList);
		ret.sort(comparator);
		return ret;
	}
}
