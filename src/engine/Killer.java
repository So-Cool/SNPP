package engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import randomGenerators.RG;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class Killer implements Runnable {

	// close file writing on exit
	private GeneratorCSV[] writer;
	private String[] name;
	private List<RG> threads;
	
	public Killer( GeneratorCSV[] wrt, String [] nam, List<RG> ths ) {
		this.writer = wrt;
		this.name = nam;
		this.threads = ths;
	}
	
	public void run() {
		System.out.println( "Press *enter* to exit at anytime." );
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Stop threads
			for (RG o : threads) {
				System.out.println( "Waiting for " + o.getClass() + " to shut down..." );
			    o.stop();
			    synchronized ( o ) {
			    	if (!o.done()) {
			    		try {
			    			o.wait();
			    		} catch (InterruptedException e) {
			    			// TODO Auto-generated catch block
			    			e.printStackTrace();
			    		}
			    	}
				}
			    System.out.println( o.getClass() + " closed." );
			}
			
			System.out.println("Closing file writer.");
			for (GeneratorCSV element : writer)
			    element.close();
			
			// Do WEKA comparison
			System.out.println("Doing WEKA comparison...");
			List<Instances> sets = new ArrayList<Instances>();
			Instances set;
			DataSource source;

			for (String element : name) {
				try {
					System.out.println(element + ".CSV");
					
					source = new DataSource( element + ".csv");
					set = source.getDataSet();

					// setting class attribute
					if (set.classIndex() == -1)
						set.setClassIndex(set.numAttributes() - 1);


					int attributesAll = set.numAttributes();
					int attributes = (int) set.attributeToDoubleArray( set.numAttributes() - 2 )[0];
					String[] options = new String[2];
					// "range"
					options[0] = "-R";
					// first attribute
					options[1] = Integer.toString( attributes+1 ) + "-" + Integer.toString(attributesAll);
					// new instance of filter
					Remove remove = new Remove();
					// set options
					remove.setOptions( options );
					// inform filter about data-set **AFTER** setting options
					remove.setInputFormat( set );
					// apply filter
					set = Filter.useFilter( set, remove );
					
					sets.add( set );

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
//			for (Instances setElement : sets) {
//				DBSCAN
				// weka.clusterers.SimpleKMeans -N 2 -A "weka.core.EuclideanDistance -R first-last" -I 500 -S 10
//			}
			
			// Clusteriong evaluation!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			
			// call Python script for visualization
			try {
				System.out.println( "Visualizing results..." );
				String fNames = " ";
				for (String element : name)
					fNames += ( element + ".csv " );
					Runtime.getRuntime().exec("./visualize.py nowis" + fNames).waitFor();
				} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
