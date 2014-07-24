package RandomGenerators;

import java.util.Date;

import org.apache.commons.math3.distribution.PoissonDistribution;

// Math.PI

public class Sine implements Runnable {

	private double current = 0;
	private PoissonDistribution gen;
	private Date timer;
	private String genName = "Sine";
	
	public Sine( int jump ) {
		this.timer = new Date( System.currentTimeMillis() );
		this.gen = new PoissonDistribution( jump );
	}
	
	public void run() {
		int timeToWait = gen.sample();
		while( true ) {

			
			
			for( double degrees = 0; degrees < 360; degrees++ ) {
				current = Math.sin( Math.toRadians(degrees) );
				// Print and send tick
				System.out.println( toString() );
				//send
			}
			try {
				Thread.sleep( timeToWait * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
	// Return string
    @Override
    public String toString() {
        return "Tick! -> Generator: " + genName + " | Sample: " + current + " | Time: " + timer;
    }
		
}
