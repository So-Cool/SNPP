package temperature;

import java.io.IOException;

import javax.naming.NamingException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import testRun.CEPListener;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class DriveTemp {

	private static String t1URL = "http://137.138.196.84/tme.xml";
	
	private static String prevessin = "618067";
	private static String units     = "c";
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, NamingException {
		
		// Initialize ESPER server
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider();
		String expression = "select temperature from temperature.CERNtermometer.win:time(5 sec) having avg(temperature) > 5.0";
		EPStatement statement = epService.getEPAdministrator().createEPL(expression);
		
//		CERNListener listener = new CERNListener();
//		statement.addListener(listener);
		statement.addListener(new CEPListener());
		
		
		
		( new Thread( new CERNtermometer(t1URL, epService) ) ).start();
		( new Thread( new WeatherForecast( prevessin, units, epService ) ) ).start();
		
		
	}
	
}
