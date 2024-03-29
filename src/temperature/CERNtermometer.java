package temperature;

import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.distribution.PoissonDistribution;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.espertech.esper.client.EPServiceProvider;

/* once you call this object with property: locaton of xml file
 * it creates and object connected to this xml file and when you call
 * the getter returns current temperature
 */

/*
 * http://137.138.196.84/tme.xml
 *
 *	<thermometer>
 *		<title>
 *			Ethernet thermometer TME designed by Papouch s.r.o. - www.papouch.com
 *		</title>
 *		<description>864,1-A01</description>
 *		<temperature>238</temperature>
 *		<mintemperature>"N"</mintemperature>
 *		<maxtemperature>"N"</maxtemperature>
 *	</thermometer>
 */

public class CERNtermometer implements Runnable {
	// Define location of thermometer
	private String location;
	// Define current temperature
	private int temperature;
	// Define current time --- system date or some internal clock
	private Date time;
	// define reader of xml file
	private URL url;
	private File tempFile;
	private Document document;
	
	// ESPER service provider
	private EPServiceProvider myService;
	
	// class definition
	public CERNtermometer(String xml, EPServiceProvider service) throws ParserConfigurationException, SAXException, IOException {
		
		this.url = new URL(xml);
		String tDir = System.getProperty("java.io.tmpdir");
		this.tempFile = new File(tDir+"/temperatureTerm.tmp");
		FileUtils.copyURLToFile(url, this.tempFile);
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		this.document = db.parse(this.tempFile);
        this.location = this.document.getElementsByTagName("description").item(0).getTextContent();
        String tempTemp = this.document.getElementsByTagName("temperature").item(0).getTextContent();
        this.temperature = Integer.parseInt(tempTemp);
        this.time = new Date( System.currentTimeMillis() );
        
        // Initialize my service provider
        this.myService = service;
        
        System.out.println(toString());
	}
	
	// make a copy
	public CERNtermometer( CERNtermometer another ) {
		this.location = another.location;
		this.temperature = another.temperature;
		this.time = another.time;
		
		this.url = another.url;
		this.tempFile = another.tempFile;
		this.document = another.document;
		
		this.myService = another.myService;
	}
	
	// Define what to do in the thread
	public void run() {
//		String t1URL = "http://137.138.196.84/tme.xml";
		int oldTemp;
		int newTemp;
		long timeToWait = 1;
		
		// generate timeToWait according to Poisson distribution
		PoissonDistribution gen = new PoissonDistribution( 47 );
		
		try {
			
//			CERNtermometer t1 = new CERNtermometer( t1URL );
			while (true) {
				oldTemp = this.getTemperature();
				this.updateTemperature();
				
				// once updated send
				myService.getEPRuntime().sendEvent( new CERNtermometer(this) );
				
				newTemp = this.getTemperature();
				
				if (oldTemp != newTemp) {
					System.out.println( this.toString() );
					// send message --- not because it is the same
				}
				
				timeToWait = gen.sample();
				
				//Pause for timeToWait seconds
				Thread.sleep( timeToWait * 1000);
				System.out.println( "Tick term!" );
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
		
	// Define *getters*
	public String getLocation() {return location;}
	public Date getTime() {return time;}
	public int getTemperature() {return temperature;}
	
	// Read the temperature from the sensors
	public void updateTemperature( ) throws IOException, ParserConfigurationException, SAXException {
		FileUtils.copyURLToFile(url, this.tempFile);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		this.document = db.parse(this.tempFile);
		String tempTemp = this.document.getElementsByTagName("temperature").item(0).getTextContent();
		int tempTempInt = Integer.parseInt(tempTemp);
		
		if (temperature != tempTempInt)
			System.out.println("Temperature update: " + readableTemp(temperature) + " --> " + readableTemp(tempTempInt) );
		
		this.temperature = Integer.parseInt(tempTemp);
		this.time.setTime( System.currentTimeMillis() );
	}
	
	// Create readable temperature
	public String readableTemp( int tmp ) {
		int rest = tmp % 10;
		int whole = (tmp - rest) / 10 ;
		return whole + "." + rest + "oC";
	}
	
	// Return string
    @Override
    public String toString() {
        return "Location: " + location + " | Temperature: " + readableTemp(temperature) + " | Time: " + time;
    }
}
