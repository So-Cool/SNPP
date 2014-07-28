package randomGenerators;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.distribution.PoissonDistribution;

import com.espertech.esper.client.EPServiceProvider;

// Math.PI

public class Sine implements Runnable {

	private double current = 0;
	private double x = 1;
	private double y = 1;
	private PoissonDistribution gen;
	private Date timer;
	private String genName = "Sine";
	
	// ESPER service provider
	private EPServiceProvider myService;
	
	public Sine( double xScale, double yScale, int jump, EPServiceProvider service ) {
		this.timer = new Date( System.currentTimeMillis() );
		this.gen = new PoissonDistribution( jump );
		this.x = xScale;
		this.y = yScale;
		
		// Initialize my service provider
        this.myService = service;
	}
	
	public void run() {
		int timeToWait = gen.sample();
		double degrees = 0;
		
		while( true ) {
		    for (long stop=System.nanoTime()+TimeUnit.SECONDS.toNanos(timeToWait); stop>System.nanoTime(); degrees++ ) {
				current = y* Math.sin( x* Math.toRadians(degrees) );
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		    this.timer.setTime( System.currentTimeMillis() );
		    if( degrees > 360000 )
		    	degrees = degrees % 360;
		    
			// Print and send tick
			System.out.println( toString() );
			// once updated send
			myService.getEPRuntime().sendEvent(this);
		}			
	}
	
	// Return string
    @Override
    public String toString() {
        return "Tick! -> Generator: " + genName + " | Sample: " + current + " | Time: " + timer;
    }
		
}
