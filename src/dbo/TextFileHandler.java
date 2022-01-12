/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author marti
 */
class TextFileHandler implements FileHandler<List<String>> {

	@Override
	public List<String> read(String filePath) throws IOException {
		File handler = new File(filePath);
		if(!handler.exists() || !handler.canRead()){
			throw new IOException("File not found or not readable");
		}
		String absolutePath = handler.getAbsolutePath();
		Path path = Paths.get(absolutePath);
		List<String> response = Files.readAllLines(path);
		return response;
	}

	@Override
	public void write(List<String> data, String filePath) throws IOException {
		File handler = new File(filePath);
		if(!handler.exists() || !handler.canWrite()){
			throw new IOException("File not found or not readable");
		}
		String absolutePath = handler.getAbsolutePath();
		PrintWriter printer = new PrintWriter(absolutePath);
		for(String record : data){
			printer.print(record + "\n");
		}
		printer.flush();
		printer.close();
	}
	
}
