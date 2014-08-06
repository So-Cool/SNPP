package engine;

import java.io.IOException;

public class Killer implements Runnable {

	// close file writing on exit
	private GeneratorCSV[] writer;
	private String[] name;
	
	public Killer( GeneratorCSV[] wrt, String [] nam ) {
		this.writer = wrt;
		this.name = nam;
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
			for (GeneratorCSV element : writer)
			    element.close();
			
			// Do WEKA comparison
			System.out.println("Doing WEKA comparison...");
			for (String element : name)
				System.out.println(element + ".CSV");
			
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
