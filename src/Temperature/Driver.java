package Temperature;

/*
 * ToDo:
 * run it as a process
 * send the weather conditions randomly according to some distribution
 * 
 */

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class Driver {

	private static String t1URL = "http://137.138.196.84/tme.xml";
	
	private static String prevessin = "618067";
	private static String units     = "c";
	
	public static void main(String[] args) {
		int oldTemp1;
		int newTemp1;
		int oldTemp2;
		int newTemp2;
		
		try {
			CERNtermometer t1 = new CERNtermometer( t1URL );
			WeatherForecast prev = new WeatherForecast( prevessin, units );
			while (true) {
				oldTemp1 = t1.getTemperature();
				t1.updateTemperature();
				newTemp1 = t1.getTemperature();
				
				oldTemp2 = prev.getTemperature();
				prev.updateTemperature();
				newTemp2 = prev.getTemperature();
				
				if (oldTemp2 != newTemp2 || oldTemp1 != newTemp1) {
					System.out.println( t1.toString() );
					System.out.println( prev.toString() );
				}
					
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
