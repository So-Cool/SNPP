package engine;

import java.io.FileWriter;
import java.io.IOException;

public class GeneratorCSV {

	private static FileWriter writer;
	
	public GeneratorCSV( String fileName ) {
		try {
			writer = new FileWriter(fileName+".csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void newLine() {
		try {
			writer.append('\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void comma() {
		try {
			writer.append(',');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void element( String elem ) {
		try {
			writer.append(elem);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			writer.append('\n');
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void header(String head) {
		try {
			writer.append(head);
			writer.append('\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
