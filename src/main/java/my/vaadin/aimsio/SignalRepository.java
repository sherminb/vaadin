package my.vaadin.aimsio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SignalRepository {

	public List<Signal> ListAll()
	{
		ArrayList<Signal> allSignals = new ArrayList<>();
		Signal s=new Signal("Override",new Date(),200);
		allSignals.add(s);
		return allSignals;
				
		
	}
}
