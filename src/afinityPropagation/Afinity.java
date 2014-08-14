package afinityPropagation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.queue.CircularFifoQueue;

public class Afinity {
/////////////////////////////////////////////////////////////////////////////////////
	// class properties //
	
	// Maximal size of reservoir
	private int resSize;
	
	// number of data for initialization
	private int initSize;
	
	// Fit threshold epsilon
	private double epsilon;
	
	// Boolean flag for initial build---check whether collecting points for initial build
	Boolean initialBuild = true;
	
	// Incoming points counter
	private int incomingPoints = 0;
	
	// Expandable array for the RESERVOIR
	private List<double[]> reservoir = new ArrayList<double[]>();
	
	// Change point detection of input stream status
	private Boolean CPD = false;
	
	// CPD queue
	CircularFifoQueue<Double> CPDqu;
	CircularFifoQueue<Long> CPDqt;
	CircularFifoQueue<Double> CPDqp;
	
	// PH test maximum
	private double Mt = 0;
	
	// PH test threshold
	private double lambda = 0;
	
	// Lambda factor
	private double lFactor;
	
	// CPC queue size
	private int CPCsize;
	
	//MODEL????
	
/////////////////////////////////////////////////////////////////////////////////////
	// functions //
	
	// Initialize
	public Afinity( int reservoirSize, int initialSize, double eps, double f, int cpcs ) {
		this.initSize = initialSize;
		this.resSize = reservoirSize;
		this.epsilon = eps;
		this.CPCsize = cpcs;
		this.lFactor = f;
		this.CPDqu = new CircularFifoQueue<Double>(CPCsize);
		this.CPDqt = new CircularFifoQueue<Long>(CPCsize);
		this.CPDqp = new CircularFifoQueue<Double>(CPCsize);
	}
	
	// Receive new datum point
	public void getPoint(double[] p, long timestamp) {
		/*for (double element : p) {
		 *	System.out.println("O: " + element);
		 *}
		**/
		
		if(initialBuild){
			++incomingPoints;
			reservoir.add(p);
		} else {
			processPoint(p, timestamp);
			return;
		}	
		
		if(initialBuild && incomingPoints > initSize){
			initialBuild = false;
			// build first model
			initializeModel();
		}
	}
	
	// initialize model
	private void initializeModel(){
		// empty the reservoir
		reservoir.clear();
		
		//CREATE MODEL
	}
	
	// update model
	private void updateModel(){
		//UPDATE MODEL
	}
	
	// rebuild model
	private void rebuildModel(){
		//REBUILD MODEL
	}
	
	// process the new arriving point
	private void processPoint(double[] p, long ts) {
		//COMPUTE E_I
		double ei = 0;
		// get matrix distance
		double d = 0 * ei;// d( p, e_i )
		
		if(d < epsilon){
			updateModel();
		} else {
			checkStationarity(d, ts);
			reservoir.add(p);
		}
		
		// check rebuild criteria
		if(reservoiFull() || CPD){
			rebuildModel();
			reservoir.clear();
			CPD = false;
		}
		
	}
	
	private void checkStationarity(double pt, long ts){
		
		CPDqu.add(pt);
		CPDqt.add(ts);
		//CHECK WHETHER ARRIVING POINTS ARE STATIONRY
		int l = CPDqu.size(); System.out.println("Side: " + l);
		double sum = 0;
		
		double c2 = 0;
		for(int j = 1; j < l; ++j) {
			c2 += CPDqu.get(j);
		}
		
		for (int i = 1; i < l; ++i){
			double c1 = 1 + Math.log( CPDqt.get(i) - CPDqt.get(i-1) );
			double c3 = CPDqu.get(i) - Math.pow( ( c2 / (l-1) ), 2 );
			sum += c1*c3;
		}
		
		double p = Math.sqrt(sum / (l-1));
		CPDqp.add(p); System.out.println("p-value: " + p);
		
		
		// Get statistics
		double pBar = 0;
		for (double pp : CPDqp) {
			pBar += pp;
		}
		pBar /= CPDqp.size();
		
		double m = 0;
		for (double pp : CPDqp) {
			m += (pp - pBar);
		}
		Mt = Math.max(Mt, m);
		
		// PHt value
		double PHt = Mt - m;
		
		
		// Calibrate lambda
		lambda = (PHt == 0) ? 0.0 : lFactor * pBar;
		
		
		// Page-Hinkley test		
		if( PHt > lambda )
			CPD = true;
	}
	
	private Boolean reservoiFull(){
		return (reservoir.size() > resSize) ? true : false;
	}

}
