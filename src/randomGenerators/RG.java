package randomGenerators;

public class RG implements Runnable {

	protected Boolean running = true;
	protected Boolean threadDone = false;
	
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
	
	// Return string
    public String toString() {
        return "Tick! -> Generator: " + genName + " | Sample: " + current + " | Time: " + timer;
    }

	@Override
	public void run() {
		// Override runnable in particular generator.	
	}
	
}
