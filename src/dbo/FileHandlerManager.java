/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/**
 *
 * @author marti
 */
final public class FileHandlerManager {

	private HashMap<String, FileHandler> handlerList;
	private static FileHandlerManager manager;

	private FileHandlerManager() {
	}

	public static FileHandlerManager getInstance() {
		if (Objects.isNull(manager)) {
			manager = new FileHandlerManager();
			manager.handlerList = new HashMap<>();
			manager.handlerList.put("TextFileHandler", new TextFileHandler());
		}
		return manager;
	}

	public StringBuffer readText(String filePath) throws IOException {
		StringBuffer buffer = (StringBuffer) manager.handlerList.get("TextFileHandler").read(filePath);
		return buffer;
	}

	public void writeText(StringBuffer data, String filePath) throws IOException {
		manager.handlerList.get("TextFileHandler").write(data, filePath);
	}
}
