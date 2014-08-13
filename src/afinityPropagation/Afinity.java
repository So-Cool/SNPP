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
	
	// CPC queue size
	private int CPCsize;
	
	//MODEL????
	
/////////////////////////////////////////////////////////////////////////////////////
	// functions //
	
	// Initialize
	public Afinity( int reservoirSize, int initialSize, double eps, int cpcs ) {
		this.initSize = initialSize;
		this.resSize = reservoirSize;
		this.epsilon = eps;
		this.CPCsize = cpcs;
	}
	
	// Receive new datum point
	public void getPoint(double[] p, long timestamp) {
		/*for (double element : p) {
		 *	System.out.println("O: " + element);
		 *}
		**/
		
		checkStationarity(p, timestamp);
		
		if(initialBuild){
			++incomingPoints;
			reservoir.add(p);
		} else {
			processPoint(p);
			return;
		}	
		
		if(initialBuild && incomingPoints > initSize){
			initialBuild = false;
			// build first model
			initializeModel();
		}
	}
	
	private void checkStationarity(double[] p, long ts){
		CircularFifoQueue<double[]> lol = new CircularFifoQueue<double[]>(CPCsize);
		lol.add(null);
		//CHECK WHETHER ARRIVING POINTS ARE STATIONRY
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
	private void processPoint(double[] p) {
		//COMPUTE E_I
		double ei = 0;
		
		if(ei < epsilon){
			updateModel();
		} else {
			reservoir.add(p);
		}
		
		// check rebuild criteria
		if(reservoiFull() || CPD){
			rebuildModel();
			reservoir.clear();
		}
		
	}
	
	private Boolean reservoiFull(){
		return (reservoir.size() > resSize) ? true : false;
	}

}
