/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * A file handler that can read and write file in binary or text format
 * <p>
 * This class implements Singleton design pattern. Therefore, the only way to
 * use this class is through the getInstance() method
 * <p>
 * It can be used to get a list/array of object with the given runtime type
 * 
 * @author martin
 * @since 1.0
 */
final public class FileHandler implements java.io.Serializable {
	private static FileHandler handler = null;
	private static final long serialVersionUID = 8683452576132892189L;
	private static final int NUMBER_OF_REVERSED_BYTE = 64;

	private FileHandler() {
	}

	/**
	 * Get an instance of this class
	 * 
	 * @return {@ handler}
	 */
	public static FileHandler getInstance() {
		if (handler == null)
			handler = new FileHandler();
		return handler;
	}

	public List<String> ReadTXT(String filePath) throws IOException {
		Objects.requireNonNull(filePath);

		ArrayList<String> data = null;
		String thisLine;
		BufferedReader myInput;

		File fileHandler = new File(filePath);
		if (!fileHandler.isFile())
			throw new IOException("The file " + filePath + " is not a valid file");
		if (!fileHandler.exists() || !fileHandler.canRead()) {
			throw new IOException("File " + filePath + " is not a valid file or not readable");
		}
		if (fileHandler.length() == 0)
			throw new IOException("The file " + filePath + " is Empty");

		String fullPath = fileHandler.getAbsolutePath();
		FileInputStream file = new FileInputStream(fullPath);
		myInput = new BufferedReader(new InputStreamReader(file));
		while ((thisLine = myInput.readLine()) != null) {
			if (!thisLine.isEmpty()) {
				if (data == null) {
					data = new ArrayList<>();
				}
				data.add(thisLine);
			}
		}
		myInput.close();
		return data;
	}

	private class END_OF_FILE implements java.io.Serializable {
		private final END_OF_FILE EOF = FileHandler.getInstance().new END_OF_FILE();
		private END_OF_FILE() {
		};
	}

	/**
	 * <p> Write a list of object to a file in binary format
	 * <p>
	 * The class of element must implements java.io.Serializable
	 * 
	 * @param <T>          - the runtime class of each elements in the list
	 * @param listOfObject - the list of objects to be written
	 * @param filePath     - the path to the file
	 * @throws IOException              if file can not be written correctly
	 * @throws NullPointerException     if any arguments is null
	 * @throws IllegalArgumentException if the number of object is zero or file is
	 *                                  not writeable
	 */
	public <T extends java.io.Serializable> void Write_Binary(List<T> listOfObject, String filePath)
			throws IOException, NullPointerException, IllegalArgumentException {
		if (Objects.isNull(listOfObject))
			throw new NullPointerException("argument (data) is null");

		if (Objects.isNull(filePath))
			throw new NullPointerException("Argument (filePath) is null");

		int size = listOfObject.size();
		if (size == 0)
			throw new IllegalArgumentException("argument(data) is empty");

		File fh = new File(filePath);
		if (!fh.canWrite() && fh.exists()) {
			throw new IllegalArgumentException("File not writeable");
		}
		int counter, numOfElements;
		counter = numOfElements = 0;
		FileOutputStream fos = new FileOutputStream(fh);
		DataOutputStream dos = new DataOutputStream(fos);
		ObjectOutputStream oos = new ObjectOutputStream(dos);

		long startPos = fos.getChannel().position();
		dos.writeLong(serialVersionUID);
		fos.getChannel().position(startPos + 8 + 4 + NUMBER_OF_REVERSED_BYTE);

		while (counter < size) {
			if (!Objects.isNull(listOfObject.get(counter))) {
				T element = listOfObject.get(counter);
				oos.writeObject(element);
				numOfElements++;
			}
			counter++;
		}

		fos.getChannel().position(startPos + 8);
		dos.writeInt(numOfElements);
		oos.close();
	}

	/**
	 * Read and return a list of object with the given element type from a binary file
	 * @param <T>      - the component type of the list to be returned
	 * @param componentType   - the runtime class of object in the file
	 * @param filePath - path to the file
	 * @return
	 * @throws IOException              - if the file`s format is invalid
	 * @throws IllegalArgumentException - if file is not exists or not readable""
	 * @throws ClassNotFoundException   due to mismatch between the desired
	 *                                  component type and the actual runtime class
	 *                                  of the object in the file
	 * @throws NullPointerException     - if any arguments is null
	 */
	public <T extends java.io.Serializable> List<T> Read_Binary_File_toList(Class<T> componentType, String filePath)
			throws IOException, IllegalArgumentException, ClassNotFoundException, NullPointerException {
		if (Objects.isNull(componentType))
			throw new NullPointerException("Argument(type) is null");
		if (Objects.isNull(filePath))
			throw new NullPointerException("Argument(filePath) is null");
		File fh = new File(filePath);
		if (!fh.exists() || !fh.canRead()) {
			throw new IllegalArgumentException("File not found or not readable");
		}
		if (fh.length() == 0)
			throw new IllegalArgumentException("File is empty");

		List<T> ret = new ArrayList<T>();
		FileInputStream fis = new FileInputStream(filePath);
		DataInputStream dis = new DataInputStream(fis);
		ObjectInputStream ois = new ObjectInputStream(dis);

		long fileSerialVersionUID = 0l;
		long startPos = fis.getChannel().position();
		fileSerialVersionUID = dis.readLong();

		if (Long.compare(fileSerialVersionUID, serialVersionUID) != 0) {
			ois.close();
			throw new IOException("invalid format");
		}

		int length = dis.readInt(); // number of objects in the file

		int counter = 0;
		fis.getChannel().position(startPos + 12 + NUMBER_OF_REVERSED_BYTE);
		while (counter < length) {
			Object t = ois.readObject();
			if (componentType.isAssignableFrom(t.getClass())) {
				ret.add((T) t);
				counter = counter + 1;
			} else {
				ois.close();
				throw new ClassNotFoundException(
						"mismatch between the desired component type and the actual runtime class");
			}
		}
		ois.close();
		return ret;
	}

	/**
	 * Read and return a array of object with the desired element type from a binary file
	 * @param <T>      - the desired component type of the array to be returned
	 * @param type     - the class of the object in the binary file
	 * @param filePath - path to the file
	 * @return - an array of object
	 * @throws IOException              if file`format is invalid
	 * @throws IllegalArgumentException if file not found or not readable
	 * @throws NullPointerException     if any arguments is null
	 * @throws ClassNotFoundException
	 */
	public <T extends java.io.Serializable> T[] Read_Binary_File_toArray(Class<T> componentType, String filePath)
			throws IOException, IllegalArgumentException, NullPointerException, ClassNotFoundException {
		if (Objects.isNull(componentType))
			throw new NullPointerException("Argument(type) is null");
		if (Objects.isNull(filePath))
			throw new NullPointerException("Argument(filePath) is null");

		File fh = new File(filePath);
		if (!fh.exists() || !fh.canRead()) {
			throw new IllegalArgumentException("File not found or not readable");
		}
		if (fh.length() == 0)
			throw new IOException("File is empty");

		FileInputStream fis = new FileInputStream(filePath);
		DataInputStream dis = new DataInputStream(fis);
		ObjectInputStream ois = new ObjectInputStream(dis);

		long fileSerialVersionUID = 0l;
		long startPos = fis.getChannel().position();
		fileSerialVersionUID = dis.readLong();

		if (Long.compare(fileSerialVersionUID, serialVersionUID) != 0) {
			ois.close();
			throw new IOException("invalid format");
		}

		int length = dis.readInt(); // number of objects in the file

		int counter = 0;

		T[] ret = (T[]) Array.newInstance(componentType, length);
		fis.getChannel().position(startPos + 12 + NUMBER_OF_REVERSED_BYTE);
		while (counter < length) {
			Object t = ois.readObject();
			if (componentType.isAssignableFrom(t.getClass())) {
				ret[counter++] = (T) t;
				counter = counter + 1;
			} else {
				ois.close();
				throw new ClassNotFoundException(
						"mismatch between the desired component type and the actual runtime class");
			}
		}
		ois.close();
		return Arrays.copyOf(ret, counter);
	}

}
