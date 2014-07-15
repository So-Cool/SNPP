package Temperature;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Driver {

	public static void main(String[] args) {
		String t1URL = "http://137.138.196.84/tme.xml";
		long timeStamp = System.currentTimeMillis();
		
		try {
			
			
			CERNtermometer t1 = new CERNtermometer( t1URL, timeStamp );
			while (true) {
				t1.updateTemperature();
				System.out.println( t1.toString() );
				//Pause for 4 seconds
				Thread.sleep(4000);
			
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
