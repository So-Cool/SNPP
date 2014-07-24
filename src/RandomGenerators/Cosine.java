package RandomGenerators;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.distribution.PoissonDistribution;

// Math.PI

public class Cosine implements Runnable {

	private double current = 0;
	private double x = 1;
	private double y = 1;
	private PoissonDistribution gen;
	private Date timer;
	private String genName = "Cosine";
	
	public Cosine( double xScale, double yScale, int jump ) {
		this.timer = new Date( System.currentTimeMillis() );
		this.gen = new PoissonDistribution( jump );
		this.x = xScale;
		this.y = yScale;
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
		    this.timer.setTime( System.currentTimeMillis()-1000 );
		    if( degrees > 360000 )
		    	degrees = degrees % 360;
		    
			// Print and send tick
			System.out.println( toString() );
			//send
		}			
	}
	
	// Return string
    @Override
    public String toString() {
        return "Tick! -> Generator: " + genName + " | Sample: " + current + " | Time: " + timer;
    }
		
}
