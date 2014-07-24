package RandomGenerators;

import java.util.Date;

import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;

public class Normal implements Runnable {

	// generate timeToWait according to Poisson distribution
	private NormalDistribution gen;
	private Date timer;
	private PoissonDistribution elaps;
	private double current;
	private String genName = "Normal";
	
	public Normal( long mean, long variance, int inter ) {
		this.gen = new NormalDistribution( mean, variance );
		this.elaps = new PoissonDistribution(inter);
		this.timer = new Date( System.currentTimeMillis() );;
	}
	
	public Date getTimer() { return this.timer; }
	public double getCurrent() { return this.current; }
	
	// Define what to do in the thread
	public void run() {
		long timeToWait = 0;
		
		while(true) {
			timeToWait = elaps.sample();
			
			this.timer.setTime( System.currentTimeMillis() );
			this.current = gen.sample();
			
			// Print and send tick
			System.out.println( toString() );
			//send
			
			//Pause for timeToWait seconds
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
