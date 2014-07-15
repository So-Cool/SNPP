package Temperature;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

// get the weather forecast form Yahoo!
public class WeatherForecast {
	
	private static String t0 = "http://weather.yahooapis.com/forecastrss?w=";
	private static String t1 = "&u=";
	private URL feed;
	private File tempFile;
	
	private String location;
	private Date time;
	private String yahooDate;
	private int temperature;
	
	public WeatherForecast( String where, String units ) throws IOException, ParserConfigurationException, SAXException {
		
		this.feed = new URL(t0 + where + t1 + units);
		String tDir = System.getProperty("java.io.tmpdir");
		this.tempFile = new File(tDir+"/temperatureYahoo.tmp");
		FileUtils.copyURLToFile(feed, this.tempFile);


		// Convert response to a w3c.Document object.
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document weatherDocument = builder.parse(tempFile);

		// Extract the attribute values for temp and text within the yweather:condition element.
		NodeList places     = weatherDocument.getElementsByTagName("yweather:location");
		NodeList conditions = weatherDocument.getElementsByTagName("yweather:condition");
		
		Element condition = (Element) conditions.item(0);
		Element place = (Element) places.item(0);
		
		String temp = condition.getAttribute("temp");
		String loc1 = place.getAttribute("city");
        // String loc2 = place.getAttribute("region");
		String loc3 = place.getAttribute("country");
		this.yahooDate = condition.getAttribute("date");
		
		this.temperature = Integer.parseInt(temp) * 10;
		this.time = new Date( System.currentTimeMillis() );
		this.location = loc1 + ", " + loc3;

		System.out.println(toString());
	}
	
	// Read the temperature from the forecast
	public void updateTemperature( ) throws IOException, ParserConfigurationException, SAXException {
		FileUtils.copyURLToFile(this.feed, this.tempFile);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document weatherDocument = builder.parse(tempFile);
		
		NodeList conditions = weatherDocument.getElementsByTagName("yweather:condition");
		Element condition = (Element) conditions.item(0);
		
		String temp = condition.getAttribute("temp");
		this.yahooDate = condition.getAttribute("date");
		
		if (temperature != Integer.parseInt(temp) *10)
			System.out.println("Temperature update: " + readableTemp(temperature) + " --> " + readableTemp(Integer.parseInt(temp) *10) );
		
		this.temperature = Integer.parseInt(temp) * 10;
		this.time.setTime( System.currentTimeMillis() );
	}
	
	// Define *getters*
	public String getLocation() {return location;}
	public Date getTime() {return time;}
	public int getTemperature() {return temperature;}
	public String getYahooDate() {return yahooDate;}
	
	// Create readable temperature
	public String readableTemp( int tmp ) {
		int rest = tmp % 10;
		int whole = (tmp - rest) / 10 ;
		return whole + "." + rest + "oC";
	}
	
	// Return string
    @Override
    public String toString() {
        return "Location: " + location + " | Temperature: " + readableTemp(temperature) + " | Time: " + time + " | Yahoo! time: " + yahooDate;
    }
}
