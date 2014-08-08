package featureExtractors;

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
}
