/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author marti
 */
class TextFileHandler implements FileHandler<StringBuffer> {
	@Override
	public StringBuffer read(String filePath) throws IOException {
		StringBuffer ret = new StringBuffer();
		Path path = Paths.get(filePath);
		BufferedReader bufferedReader = Files.newBufferedReader(path);
		String curLine;
		while ((curLine = bufferedReader.readLine()) != null) {
			ret.append(curLine);
		}
		return ret;
	}

	@Override
	public void write(StringBuffer data, String filePath) throws IOException {
		PrintWriter printer = new PrintWriter(filePath, "UTF-8");
		printer.print(data.toString());
		printer.flush();
		printer.close();
	}
}
