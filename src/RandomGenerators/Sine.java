package RandomGenerators;

import java.util.Date;

// Math.PI

public class Sine {

	private double current = 0;
	private Date timer;
	private String genName = "Sine";
	
	public Sine() {
		this.timer = new Date( System.currentTimeMillis() );
	}
	
	public void sinGen() {
	
		while( true ) {
			
			double radians = 0;
			
			for( double degrees = 0; degrees < 360; degrees++ ) {
				radians = Math.toRadians(degrees);
				current = Math.sin( radians );
			}
		}
	}
		
}
