package randomGenerators;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;

import com.espertech.esper.client.EPServiceProvider;

// Math.PI

public class Sine extends RG{

	private double x = 1;
	private double y = 1;
	private NormalDistribution noise;
	
	public Sine( double xScale, double yScale, int jump, double noise, EPServiceProvider service, Boolean print ) {
		genName = "Sine";
		this.timer = new Date( System.currentTimeMillis() );
		this.elaps = new PoissonDistribution( jump );
		this.x = xScale;
		this.y = yScale;
		this.noise =  new NormalDistribution( 0, noise );
		this.printOut = print;
		
		// Initialize my service provider
        this.myService = service;
	}
	
	// get copy of object
	public Sine(Sine another) {
	   this.elaps = another.elaps;
	   this.timer = another.timer;
	   this.current = another.current;
	   this.x = another.x;
	   this.y = another.y;
	   this.myService = another.myService;
	   this.printOut = another.printOut;
	}
	
	public double gen( int timeToWait, double degrees ) {
		for (long stop=System.nanoTime()+TimeUnit.MILLISECONDS.toNanos(timeToWait *100); stop>System.nanoTime(); degrees++ ) {
			current = y* Math.sin( x* Math.toRadians(degrees) );
			if( noise.getStandardDeviation() != 0 )
				current += noise.sample();
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

	@Override
	public void run() {
		int timeToWait = elaps.sample();
		double degrees = 0;
		
		while( running ) {
			degrees = this.gen( timeToWait, degrees );
		    timeToWait = this.waita();
		   
		    if( degrees > 360000 )
		    	degrees = degrees % 360;
		    
			// Print and send tick
		    if( printOut )
		    	System.out.println( toString() );
			// once updated send
			myService.getEPRuntime().sendEvent( new Sine(this) );
		}
		closer();
	}
		
}
