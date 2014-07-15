package Temperature;

import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

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

public class CERNtermometer {
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
	
	// class definition
	public CERNtermometer(String xml, long tStamp) throws ParserConfigurationException, SAXException, IOException {
		
		this.url = new URL(xml);
		String tDir = System.getProperty("java.io.tmpdir");
		this.tempFile = new File(tDir+"/temperature.tmp");
		FileUtils.copyURLToFile(url, this.tempFile);
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		this.document = db.parse(this.tempFile);
        this.location = this.document.getElementsByTagName("description").item(0).getTextContent();
        String tempTemp = this.document.getElementsByTagName("temperature").item(0).getTextContent();
        this.temperature = Integer.parseInt(tempTemp);
        this.time = new Date(tStamp);
        
        System.out.println("Location: " + location);
        System.out.println("Temperature: " + temperature);
        System.out.println("Time: " + time);
	}
	
	// Define *getters*
	public String getLocation() {return location;}
	public Date getTime() {return time;}
	public int getTemperature() {return temperature;}
	
	// Read the temperature from the sensors
	public void updateTemperature() throws IOException, ParserConfigurationException, SAXException {
		FileUtils.copyURLToFile(url, this.tempFile);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		this.document = db.parse(this.tempFile);
		String tempTemp = this.document.getElementsByTagName("temperature").item(0).getTextContent();
		
		System.out.println("Temperature update: " + this.temperature + " --> " + tempTemp);
		
		this.temperature = Integer.parseInt(tempTemp);
	}
	
	// Return string
    @Override
    public String toString() {
        return "Location: " + location + " | Temperature: " + temperature + " | Time: " + time;
    }
}
