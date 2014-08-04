package engine;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;

public class FeatureExtractor {

	/////////////////////////////features extractors///////////////////////////////
	public static int threshold(double n) {
		if( n > 100 )
			return 1;
		else
			return 0;
	}
	public static int gradient(double n) {
		if( n < 25 )
			return 0;
		else if( n < 50 )
			return 1;
		else if( n < 75 )
			return 2;
		else if( n < 100 )
			return 3;
		else if( n < 125 )
			return 4;
		else if( n < 150 )
			return 5;
		else
			return 6;
	}
	///////////////////////////////////////////////////////////////////////////////
	
	// Get the EPL processing statement
	public static EPStatement getStatement( EPServiceProvider srv, int type ) {

		String expression = "";

		switch (type) {
			case 1: expression = "select avg(current) as AvgCur, stddev(current) as StdCur, (current - prev(1, current)) as Lag1Cur, "+
						"(current - prev(2, current)) as Lag2Cur, current as CurCur, engine.FeatureExtractor.threshold(current) as thrCur, "+
						"timer as TimeCur from NormTick.win:time(60 sec)";
					break;
			case 2: expression = "select avg(current) as AvgCur, stddev(current) as StdCur, (current - prev(1, current)) as Lag1Cur, "+
						"(current - prev(2, current)) as Lag2Cur, current as CurCur, engine.FeatureExtractor.threshold(current) as thrCur, "+
						"timer as TimeCur from NormTick.win:time_batch(1 min)";
					break;
			case 3: expression = "select avg(current) as AvgCur, stddev(current) as StdCur, (current - prev(1, current)) as Lag1Cur, "+
						"(current - prev(2, current)) as Lag2Cur, current as CurCur, engine.FeatureExtractor.threshold(current) as thrCur, "+
						"timer as TimeCur from NormTick.win:length(10)";
					break;
		}

		return srv.getEPAdministrator().createEPL(expression);
	}
	
	public static int getFeatures( int type ) {
		
		int features = 0;

		switch (type) {
			case 1: features = 7;
					break;
			case 2: features = 7;
					break;
			case 3: features = 7;
					break;
		}

		return features;
	}
	
}
