package my.vaadin.aimsio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class SignalRepository {

	public List<Signal> ListAll(String resolution, String signalType)
	{
		ArrayList<Signal> allSignals = new ArrayList<>();
		List<Signal> filtered;
		
		
		allSignals.add(new Signal("Override",new Date(),200));
		allSignals.add(new Signal("Active",new Date(),300));
		allSignals.add(new Signal("Engaged",new Date(),500));
		//filtered=allSignals;
		filtered = allSignals.stream().filter(s->s.getSignalType()==signalType).collect(Collectors.toList());
		switch(resolution)
		{
			case "Daily":
				break;
		}
		
		return filtered;
				
		
	}
}
