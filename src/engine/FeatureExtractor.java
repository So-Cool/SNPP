package engine;

// feature extraction for incoming points
public class FeatureExtractor {

	// Affinity propagation features extractor
	//private int featuresNumber = 2;
	
	// Lag1 value
	private double lag1val = 0;
	
	private double lagged( double current ) {
		double temp = current - lag1val;
		lag1val = current;
		return temp;
	}
	private double currentValue( double current ) {
		return current;
	}
	
	public double F1( double v ) { return lagged(v); }
	public double F2( double v ) { return currentValue(v); }
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// ESPER Part
	// Define EPL statements(Strings) to extract features form the ESPER engine
	private String E1 = "";
	private String E2 = "";
	
	public String getE1() { return E1; }
	public String getE2() { return E2; }
	
}
