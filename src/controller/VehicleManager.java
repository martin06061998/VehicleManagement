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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import model.Vehicle;
import model.VehicleFactory;

/**
 *
 * @author marti
 */
final public class VehicleManager implements VehicleService {

	transient private String filePath = "C:\\Users\\marti\\Documents\\NetBeansProjects\\VehicleManagement\\src\\dbo\\vehicle.txt";
	private List<Vehicle> vehicleList;
	transient private static VehicleManager manager;

	private VehicleManager() {
		vehicleList = new ArrayList<>();
	}

	public static VehicleManager getManager() {
		if (Objects.isNull(manager)) {
			manager = new VehicleManager();
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
		} catch (IOException | IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public void saveDataToFile() {
		try {
			List<String> data = prepareDataForSaving();
			if(Objects.isNull(data)){
				System.out.println("Nothing to save");
				return;
			}
			FileHandlerManager.getInstance().writeText(data, filePath);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public String add(ObjectNode obj) {
		String message;
		if (Objects.isNull(obj)) {
			message = "Add failed";
		} else {
			try {
				obj.put("id", vehicleList.size());
				Vehicle newVehicle = VehicleFactory.getInstane().New_Vehicle(obj);
				vehicleList.add(newVehicle);
				message = "successfully added";
			} catch (IllegalArgumentException | NullPointerException e) {
				message = "Add failed due to " + e.getMessage();
			}
		}
		return message;
	}

	@Override
	public boolean update(int id) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public ObjectNode searchById(int id) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode reply = mapper.createObjectNode();
		if (id < 0) {
			reply.put("status", "error");
			reply.put("message", "id is invalid");
			reply.set("data", mapper.createObjectNode());
			return reply;
		}
		Vehicle ret = findUnique((n) -> n.getId() == id);
		if (Objects.isNull(ret)) {
			reply.put("status", "accepted");
			reply.put("message", "not found");
			reply.set("data", mapper.createObjectNode());
		} else {
			//JsonNode node = mapper.valueToTree(ret);
			ObjectNode vehicle = mapper.convertValue(ret, ObjectNode.class);
			reply.put("status", "accepted");
			reply.put("message", "found");
			reply.set("data", vehicle);
		}
		return reply;
	}

	@Override
	public ObjectNode searchByName(String name) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode reply = mapper.createObjectNode();
		if (Objects.isNull(name)) {
			reply.put("status", "error");
			reply.put("message", "argument \"name\" should not be null");
			ArrayNode data = mapper.createArrayNode();
			reply.set("data", data);
			return reply;
		}
		List<Vehicle> ret = findAll((n) -> n.getName().equalsIgnoreCase(name));
		if (ret.isEmpty()) {
			reply.put("status", "accepted");
			reply.put("message", "not found any vehicle");
			ArrayNode data = mapper.createArrayNode();
			reply.set("data", data);
		} else {
			reply.put("status", "accepted");
			reply.put("message", "found");
			ArrayNode data = mapper.valueToTree(ret);
			reply.set("data", data);
		}
		return reply;
	}

	@Override
	public boolean delete(int id) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private void show(List<Vehicle> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

	@Override
	public void showAll() {
		show(vehicleList);
	}

	@Override
	public void showAllOrderedByPrice() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
			Function<Vehicle, String> fun = (n) -> n.serialize();
			ret = vehicleList.stream().map(fun).collect(Collectors.toList());
		}
		return ret;
	}
}
