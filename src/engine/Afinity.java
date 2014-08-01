package engine;

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
	private List<Double> printable = new ArrayList<Double>();
	
	// Remember the current cluster count
	private int clusters = 1;
	
	// Maximal size of reservoir
	private int resSize;
	
	// values for statistical tests
	private double p;
	
	// ESPER engine
	private EPServiceProvider EsperService;
	
	
/////////////////////////////////////////////////////////////////////////////////////
	// functions //
	
	// Initialize
	public Afinity( int reservoirSize, double pValue, EPServiceProvider EPSP ) {
		this.resSize = reservoirSize;
		this.EsperService = EPSP;
		this.p = pValue;
	}
	
	// Append new point
	public void addNewPoint( double point ) {
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
	
	// Rebuild the model
	private void rebuild() {
		/* FILL WITH CODE */
	}
	
	// Get the number of clusters
	public int getClusters() { return clusters; }
	// Get the clustering in readable format
	public List<Double> getPrintable() { return printable; }
}
