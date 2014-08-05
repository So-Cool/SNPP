package engine;

import com.espertech.esper.client.EventBean;

public class ListenerFeatures implements com.espertech.esper.client.UpdateListener {

	private Afinity clustering;
	private GeneratorCSV csv;
	
	public ListenerFeatures( Afinity cls ) {
		this.clustering = cls;
		// Create CSV writer
		csv = new GeneratorCSV("ListenerFeatures");
		csv.header( "F1,F2,F3,F4,F5,F6,F7,TS" );
	}
	
	// Get the CSV handler back
	public GeneratorCSV getCsv() { return this.csv; }
	
	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		System.out.println("Features=" + newEvents[0].getUnderlying()); // get("NormAvgCur") is *Object* // length
		
		// test
		String container = newEvents[0].getUnderlying().toString();
		
		// Extract features
		double F1 = Double.NaN;
		if( container.contains("F1") )
			F1 = (newEvents[0].get("F1") == null ) ? Double.NaN : Double.parseDouble( newEvents[0].get("F1").toString() );
		double F2 = Double.NaN;
		if( container.contains("F2") )
			F2 = (newEvents[0].get("F2") == null ) ? Double.NaN : Double.parseDouble( newEvents[0].get("F2").toString() );
		double F3 = Double.NaN;
		if( container.contains("F3") )
			F3 = (newEvents[0].get("F3") == null ) ? Double.NaN : Double.parseDouble( newEvents[0].get("F3").toString() );
		double F4 = Double.NaN;
		if( container.contains("F4") )
			F4 = (newEvents[0].get("F4") == null ) ? Double.NaN : Double.parseDouble( newEvents[0].get("F4").toString() );
		double F5 = Double.NaN;
		if( container.contains("F5") )
			F5 = (newEvents[0].get("F5") == null ) ? Double.NaN : Double.parseDouble( newEvents[0].get("F5").toString() );
		double F6 = Double.NaN;
		if( container.contains("F6") )
			F6 = (newEvents[0].get("F6") == null ) ? Double.NaN : Double.parseDouble( newEvents[0].get("F6").toString() );
		double F7 = Double.NaN;
		if( container.contains("F7") )
			F7 = (newEvents[0].get("F7") == null ) ? Double.NaN : Double.parseDouble( newEvents[0].get("F7").toString() );
		String TS = "";
		if( container.contains("TS") )
			TS  = (newEvents[0].get("TS") == null ) ? "" : newEvents[0].get("TS").toString();
		
		// Write features to file
		csv.element( Double.isNaN(F1) ? "?" : Double.toString(F1) );
		csv.comma();
		csv.element( Double.isNaN(F2) ? "?" : Double.toString(F2) );
		csv.comma();
		csv.element( Double.isNaN(F3) ? "?" : Double.toString(F3) );
		csv.comma();
		csv.element( Double.isNaN(F4) ? "?" : Double.toString(F4) );
		csv.comma();
		csv.element( Double.isNaN(F5) ? "?" : Double.toString(F5) );
		csv.comma();
		csv.element( Double.isNaN(F6) ? "?" : Double.toString(F6) );
		csv.comma();
		csv.element( Double.isNaN(F7) ? "?" : Double.toString(F7) );
		csv.comma();
		csv.element( (TS == "") ? "?" : TS );
		csv.newLine();
		
		// Send features to clustering algorithm
		double[] x = {F1, F2, F3, F4, F5, F6, F7};
		clustering.getSome( x );
		clustering.getSome( TS );
		System.out.println( F1 + "," + F2 + "," + F3 + "," + F4 + "," + F5 + "," + F6 + "," + F7 + "," + TS );
	}
	
}
