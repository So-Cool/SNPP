package randomGenerators;

import java.util.Date;

import org.apache.commons.math3.distribution.PoissonDistribution;

import com.espertech.esper.client.EPServiceProvider;

public class RG implements Runnable {

	protected Boolean running = true;
	protected Boolean threadDone = false;
	
	protected double current = 0;
	
	protected PoissonDistribution elaps;
	protected static String genName = "empty";
	protected Date timer;
	protected Boolean printOut;
	
	// ESPER service provider
	protected EPServiceProvider myService;
	
	
	
	
	// Stop the thread
	public void stop() { this.running = false; }
	
	// check done
	public Boolean done() { return this.threadDone; }
	
	// thread closer
	protected void closer() {
		synchronized (this) {
			this.threadDone = true;
			notifyAll();
		}
	}
	
	// Current / timer getter
	public double getCurrent() { return this.current; }
	public Date getTimer() { return this.timer; }
	
	// Return string
    public String toString() {
        return "Tick! -> Generator: " + genName + " | Sample: " + current + " | Time: " + timer;
    }
    
    // Give back waiting time
	int waita() {
		return this.elaps.sample();
	}

	// Thread method
	@Override
	public void run() {
		// Override runnable in particular generator.	
	}
	
}
