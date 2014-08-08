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
	private static String genName = "Sine";
	private Boolean printOut;
	
	// ESPER service provider
	private EPServiceProvider myService;
	
	public Sine( double xScale, double yScale, int jump, EPServiceProvider service, Boolean print ) {
		this.timer = new Date( System.currentTimeMillis() );
		this.gen = new PoissonDistribution( jump );
		this.x = xScale;
		this.y = yScale;
		this.printOut = print;
		
		// Initialize my service provider
        this.myService = service;
	}
	
	// get copy of object
	public Sine(Sine another) {
	   this.gen = another.gen;
	   this.timer = another.timer;
	   this.current = another.current;
	   this.x = another.x;
	   this.y = another.y;
	   this.myService = another.myService;
	   this.printOut = another.printOut;
	}
	
	public double gen( int timeToWait, double degrees ) {
		for (long stop=System.nanoTime()+TimeUnit.SECONDS.toNanos(timeToWait); stop>System.nanoTime(); degrees++ ) {
			current = y* Math.sin( x* Math.toRadians(degrees) );
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    this.timer.setTime( System.currentTimeMillis()-50 );
		
	    return degrees;
	}
	public int waita() {
		return this.gen.sample();
	}
	
	public void run() {
		int timeToWait = gen.sample();
		double degrees = 0;
		
		while( true ) {
			degrees = this.gen( timeToWait, degrees );
		    timeToWait = this.waita();
		   
		    if( degrees > 360000 )
		    	degrees = degrees % 360;
		    
			// Print and send tick
		    if( printOut )
		    	System.out.println( toString() );
			// once updated send
			myService.getEPRuntime().sendEvent(this);
		}			
	}
	
	// Current getter
	public double getCurrent() { return this.current; }
	public Date getTimer() { return this.timer; }
	
	// Return string
    @Override
    public String toString() {
        return "Tick! -> Generator: " + genName + " | Sample: " + current + " | Time: " + timer;
    }
		
}
