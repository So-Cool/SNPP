package randomGenerators;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.distribution.PoissonDistribution;

import com.espertech.esper.client.EPServiceProvider;

// Math.PI

public class Cosine implements Runnable {

	private double current = 0;
	private double x = 1;
	private double y = 1;
	private PoissonDistribution gen;
	private Date timer;
	private String genName = "Cosine";
	
	// ESPER service provider
	private EPServiceProvider myService;
	
	public Cosine( double xScale, double yScale, int jump, EPServiceProvider service ) {
		this.timer = new Date( System.currentTimeMillis() );
		this.gen = new PoissonDistribution( jump );
		this.x = xScale;
		this.y = yScale;
		
		// Initialize my service provider
        this.myService = service;
	}
	
	// get copy of object
	public Cosine(Cosine another) {
	   this.gen = another.gen;
	   this.timer = another.timer;
	   this.current = another.current;
	   this.x = another.x;
	   this.y = another.y;
	   this.genName = another.genName;
	}
	
	public double gen( int timeToWait, double degrees ) {
		for (long stop=System.nanoTime()+TimeUnit.SECONDS.toNanos(timeToWait); stop>System.nanoTime(); degrees++ ) {
			current = y* Math.cos( x* Math.toRadians(degrees) );
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    this.timer.setTime( System.currentTimeMillis()-1000 );
		
	    return degrees;
	}
	public int waita() {
		return this.gen.sample();
	}
	
	public void run() {
		int timeToWait = this.waita();
		double degrees = 0;
		
		while( true ) {
		    degrees = this.gen( timeToWait, degrees );
		    timeToWait = this.waita();
		    
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
