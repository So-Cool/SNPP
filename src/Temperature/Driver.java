package Temperature;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Driver {

	public static void main(String[] args) {
		String t1URL = "http://137.138.196.84/tme.xml";
		
		try {
			
			
			CERNtermometer t1 = new CERNtermometer( t1URL );
			while (true) {
				t1.updateTemperature();
				System.out.println( t1.toString() );
				//Pause for 10 seconds
				Thread.sleep(10000);
			}
			
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
