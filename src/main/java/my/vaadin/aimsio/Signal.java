package my.vaadin.aimsio;
import java.util.*;
public class Signal {

	private String SignalType;
	private Date Date;
	private int Count;
	public Signal(){}
	public Signal(String type,Date date, int count)
	{
		this.SignalType=type;
		this.Date=date;
		this.Count=count;
	}
	public String getSignalType() {
		return SignalType;
	}

	public void setDate(Date date) {
		this.Date = date;
	}
	
	public Date getDate() {
		return Date;
	}

	public void setCount(int count) {
		this.Count = count;
	}
	public int getCount(){
		return Count;
	}
	
}
