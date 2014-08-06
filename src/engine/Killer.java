package engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

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
			List<Instances> sets = new ArrayList<Instances>();
			Instances set;
			DataSource source;
			int attributesAll = -1;
			int attributes = -1;
			for (String element : name) {
				try {
					System.out.println(element + ".CSV");
					
					source = new DataSource( element + ".csv");
					set = source.getDataSet();

					// setting class attribute
					if (set.classIndex() == -1) {
						attributesAll = set.numAttributes();
						attributes = (int) ( set.attributeToDoubleArray( set.numAttributes() - 2 ) )[0];
						
						for( int i = attributesAll; i > attributes; --i ) {
							set.deleteAttributeAt( i );
						}
						System.out.println( "Lo: " + set.numAttributes() );

						set.setClassIndex( attributes );
					}
					
					
					sets.add( set );
						
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// Ask which features use for classification, or better use all defined features


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
