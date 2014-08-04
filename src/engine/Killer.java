package engine;

import java.io.IOException;

public class Killer implements Runnable {

	// close file writing on exit
	private GeneratorCSV writer;
	
	public Killer( GeneratorCSV wrt ) {
		this.writer = wrt;
	}
	
	public void run() {
		System.out.println( "Press *enter* to exit at anytime." );
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Closing file writer.");
			writer.close();
			
			// Do WEKA comparison
			System.out.println("Doing WEKA comparison...not yet.");
			
			System.out.println("Exiting.");
			System.exit(0);
			Runtime.getRuntime().halt(0);
	}

/*
	Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	    public void run() {
	    	// Do something
	    }
	}));
*/

}
