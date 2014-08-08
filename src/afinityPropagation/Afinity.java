package afinityPropagation;

import java.util.ArrayList;
import java.util.List;

import com.espertech.esper.client.EPServiceProvider;

public class Afinity {
/////////////////////////////////////////////////////////////////////////////////////
	// class properties //
	
	// Expandable array for the POOL --- Java ArryaList
	private List<Double> pool = new ArrayList<Double>();
	
	// Expandable array for memorized data = i.e. = exemplars
	private List<Double> exemplars = new ArrayList<Double>();
	
	// Data for printing ??????
		// Do separate class for printing and sending to esper engine
		//-> which would just copy the necessary elements and send it as "new XYZ(x, y, z)"
		//
		// #clusters | mean values of each cluster | print to which class each newcomming point belong
	//
	private List<Double> printable = new ArrayList<Double>();
	
	// Remember the current cluster count
	private int clusters = 1;
	
	// Maximal size of reservoir
	private int resSize;
	
	// number of data for initialization
	private int initSize;
	
	// number of datapoints reseived
	private int received = 0;
	
	// values for statistical tests
	private double p;
	
	// Boolean flag for initial build
	Boolean initialBuild = true;
	
	// ESPER engine
	private EPServiceProvider EsperService;
	
	// FEATURES??????? - feature functions for signal, etc
	// HOW TO HANDLE??????????????????????
	
	// Maximum number of points that can be remembered
	// ????????????????????????
	
/////////////////////////////////////////////////////////////////////////////////////
	// functions //
	
	// Initialize
	public Afinity( int reservoirSize, int initialSize, double pValue, EPServiceProvider EPSP ) {
		this.initSize = initialSize;
		this.resSize = reservoirSize;
		this.EsperService = EPSP;
		this.p = pValue;
	}
	
	// Append new point
	public void addNewPoint( double point ) {
		// increment the counter of received points
		received++;
		
		if ( received < initSize && initialBuild ) {
			// append point for initial build
			pool.add( point );
			return;
		} else if ( initialBuild ) {
			// append point for initial build
			pool.add( point );
			// build initial model
			build();
			return;
		}
		
		// check similarity to what we already have
		Boolean sim = simmilarity( point );
		
		// if similar to what we already seen add to clustering
		if( sim ) {
			/* FILL WITH CODE */
		} // else add to reservoir
		else {
			/* FILL WITH CODE */
		}
		
		// check whether model need a rebuild
		Boolean rbld = needRebuild();
		
		// if rebuild needed: Just do it!
		if (rbld)
			rebuild();
	}

	// Check similarity to what we already have
	private Boolean simmilarity( double newPoint ) {
		/* FILL WITH CODE */
		return true;
	}
	
	// Check whether to rebuild
	private Boolean needRebuild() {
		/* FILL WITH CODE */
		return true;
	}

	// Build the model
	private void build() {
		/* FILL WITH CODE */
		initialBuild = false;
	}
	
	// Rebuild the model
	private void rebuild() {
		/* FILL WITH CODE */
	}
	
	// Get the number of clusters
	public int getClusters() { return clusters; }
	// Get the clustering in readable format
	public List<Double> getPrintable() { return printable; }
	
	
	
	public void getSome(Object o) {
		if( o instanceof double[] ) {}
//			System.out.println( "Data received!" );
		if( o instanceof String ) {}
//			System.out.println( "TimeStamp received!" );
	}
}
