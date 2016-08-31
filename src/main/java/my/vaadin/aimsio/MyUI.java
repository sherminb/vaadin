package my.vaadin.aimsio;

import javax.servlet.annotation.WebServlet;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


@Theme("mytheme")
@Widgetset("my.vaadin.aimsio.MyAppWidgetset")
public class MyUI extends UI {
	private SignalRepository repo=new SignalRepository();
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	
        final VerticalLayout layout = new VerticalLayout();
        
                
        Chart chart=new Chart();
        layout.addComponent(chart);
        
        Configuration conf = chart.getConfiguration();
        conf.setTitle("# of signals over time");
        conf.getChart().setType(ChartType.BAR);
        

		DataSeries signals = new DataSeries("Signals");
		for(Signal signal : repo.ListAll()) {
		 // time on the X-axis, count on the Y-axis
		 signals.add(new DataSeriesItem(
		                   signal.getDate(),
		                   signal.getCount()));
		}
		conf.addSeries(signals);
		
		conf.getxAxis().setTitle("Time");
		conf.getyAxis().setTitle("Signal Count");
		
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
