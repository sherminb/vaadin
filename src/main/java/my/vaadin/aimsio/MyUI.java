package my.vaadin.aimsio;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.LayoutDirection;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.PlotOptionsColumn;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
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
        
        ComboBox resolutionCombobox = new ComboBox("Resolution");
        resolutionCombobox.setInvalidAllowed(false);
        resolutionCombobox.setNullSelectionAllowed(false);
        resolutionCombobox.addItem("Daily");
        resolutionCombobox.addItem("Monthly");
        resolutionCombobox.addItem("Annually");
        resolutionCombobox.setValue("Daily");
        
        ComboBox signalTypeCombobox = new ComboBox("Asset");
        signalTypeCombobox.setInvalidAllowed(false);
        signalTypeCombobox.setNullSelectionAllowed(false);
        signalTypeCombobox.addItem("Override");
        signalTypeCombobox.addItem("Engaged");
        signalTypeCombobox.addItem("Active");
        signalTypeCombobox.setValue("Override");
        
       
        
        layout.addComponent(resolutionCombobox);
        layout.addComponent(signalTypeCombobox);
        
        Chart chart=new Chart();
       
        
        Configuration conf = chart.getConfiguration();
        conf.setTitle("# of signals over time");
        conf.getChart().setType(ChartType.COLUMN);
        
        Legend legend = new Legend();
        legend.setLayout(LayoutDirection.VERTICAL);
        legend.setBackgroundColor(new SolidColor("#FFFFFF"));
        legend.setHorizontalAlign(HorizontalAlign.LEFT);
        legend.setVerticalAlign(VerticalAlign.TOP);
        legend.setX(100);
        legend.setY(70);
        legend.setFloating(true);
        legend.setShadow(true);
        conf.setLegend(legend);

        Tooltip tooltip = new Tooltip();
        tooltip.setFormatter("this.x +': '+ this.y");
        conf.setTooltip(tooltip);
     
        PlotOptionsColumn plot = new PlotOptionsColumn();
        plot.setPointPadding(0.2);
        plot.setBorderWidth(0);
        
		DataSeries chartData=GetChartData((String)signalTypeCombobox.getValue(),(String)resolutionCombobox.getValue());
		conf.addSeries(chartData);
		
		conf.getxAxis().setTitle("Time");
		conf.getxAxis().setType(AxisType.DATETIME);
		conf.getyAxis().setTitle("Signal Count");
		
		 signalTypeCombobox.addValueChangeListener(new ValueChangeListener() {
            	
				private static final long serialVersionUID = 1L;

				@Override
				public void valueChange(ValueChangeEvent event) {
					if(signalTypeCombobox.getValue()!=null)
					{
						DataSeries chartData=GetChartData((String)signalTypeCombobox.getValue(),(String)resolutionCombobox.getValue());
						conf.addSeries(chartData);

					}
				}});
		 
		layout.addComponent(chart);
		 
		
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
    }
    private DataSeries GetChartData(String selectedSignalType,String selectedResolution)
    {
    	DataSeries signals = new DataSeries("Signals");
		List<Signal> selectedSignals=repo.ListAll(selectedResolution,selectedSignalType);
//		List<String> selectedSignalTypes=selectedSignals.stream()
//										 .map(Signal::getSignalType).distinct()
//										 .collect(Collectors.toList());
//		for(String signalType:selectedSignalTypes)
//		{
			for(Signal signal : selectedSignals) {
			 // time on the X-axis, count on the Y-axis
			 signals.add(new DataSeriesItem(
			                   signal.getDate(),
			                   signal.getCount()));
			}
			
		//}
		return signals;
    }
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
   
}
