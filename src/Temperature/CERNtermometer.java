package Temperature;

import java.util.Date;

/* once you call this object with property: locaton of xml file
 * it creates and object connected to this xml file and when you call
 * the getter returns current temperature
 */

public class CERNtermometer {
	// Define location of thermometer
	private String location;
	// Define current temperature
	private int temperatue;
	// Define current time --- system date or some internal clock
	private Date time;
	
	// class definition
	public CERNtermometer(String xml, long tStamp) {
        this.location = "lol";
        this.temperatue = 22;
        this.time = new Date(tStamp);
    }
	
	// Define *getters*
	// http://137.138.196.84/tme.xml
	/*
	 * <thermometer>
		<title>
			Ethernet thermometer TME designed by Papouch s.r.o. - www.papouch.com
		</title>
		<description>864,1-A01</description>
		<temperature>238</temperature>
		<mintemperature>"N"</mintemperature>
		<maxtemperature>"N"</maxtemperature>
	</thermometer>
	*/

	// Read the temperature from the sensors
	
}
