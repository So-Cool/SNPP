package afinityPropagation;

import java.util.ArrayList;
import java.util.List;

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
	
	//MODEL
	
	
	
	// Expandable array for memorized data = i.e. = exemplars
//	private List<Double> classes = new ArrayList<Double>();
	
	// Data for printing ??????
		// Do separate class for printing and sending to esper engine
		//-> which would just copy the necessary elements and send it as "new XYZ(x, y, z)"
		//
		// #clusters | mean values of each cluster | print to which class each newcomming point belong
	//
//	private List<Double> printable = new ArrayList<Double>();
	
	// Remember the current cluster count
//	private int clusters = 1;
	
	// number of datapoints reseived
//	private int received = 0;
	
	// values for statistical tests
//	private double p;
	
	// FEATURES??????? - feature functions for signal, etc
	// HOW TO HANDLE??????????????????????
	
	// Maximum number of points that can be remembered
	// ????????????????????????
	
/////////////////////////////////////////////////////////////////////////////////////
	// functions //
	
	// Initialize
	public Afinity( int reservoirSize, int initialSize, double eps ) {
		this.initSize = initialSize;
		this.resSize = reservoirSize;
		this.epsilon = eps;
	}
	
	// Receive new datum point
	public void getPoint(double[] p) {
		/*for (double element : p) {
		 *	System.out.println("O: " + element);
		 *}
		**/
		
		checkStationarity(p);
		
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
	
	private void checkStationarity(double[] p){
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
		//BUILD MODEL
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
