package data;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.SeriesRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StackedXYAreaRenderer;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimePeriod;
import org.jfree.data.time.TimeTableXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import bioSimulation.World;

public class AreaChartTest extends ApplicationFrame {

    private static final String TITLE = "Dynamic Series";
    private static final String START = "Start";
    private static final String STOP = "Stop";
    private static final float MINMAX = 100;
    private static final int COUNT = 15;
    private static final int FAST = 1000;
    private static final int SLOW = FAST * 5;
    private static final Random random = new Random();
    private Timer timer;
    private static final String SERIES1 = "Positive";
    private static final String SERIES2 = "Negative";
    private SpeciesOrganizer speciesOrganizer = new SpeciesOrganizer();
    final StackedXYAreaRenderer render = new StackedXYAreaRenderer();
    private int speciesCounter;
    final TimeTableXYDataset dataset = new TimeTableXYDataset();  
    SpeciesOrganizer mySpecies = new SpeciesOrganizer();

    public AreaChartTest(final String title,final World world) {
        super(title);
              
        JFreeChart chart = createAreaChart(dataset);

        final JButton run = new JButton(STOP);
        run.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                if (STOP.equals(cmd)) {
                    timer.stop();
                    run.setText(START);
                } else {
                    timer.start();
                    run.setText(STOP);
                }
            }
        });

        final JComboBox combo = new JComboBox();
        combo.addItem("Fast");
        combo.addItem("Slow");
        combo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if ("Fast".equals(combo.getSelectedItem())) {
                    timer.setDelay(FAST);
                } else {
                    timer.setDelay(SLOW);
                }
            }
        });

        this.add(new ChartPanel(chart), BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(run);
        btnPanel.add(combo);
        this.add(btnPanel, BorderLayout.SOUTH);
        /*
        timer = new Timer(FAST, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                updateChart(dataset,world);
            }
        });*/
    }
    
    public void updateChart(World world)
    {
    	
    	//Map<Color,Integer> populationMap =speciesOrganizer.speciesData(world.getPopulation());
    	mySpecies = world.getWorldSpecies();
    	TimePeriod period = new Second();
    	
    	//for (SpeciesOrganizer species : mySpecies) {
    	for (Entry<Color, Integer> species : mySpecies.getMap().entrySet()) 
    		
    			//
    	{
    			
    				//Color myColor = new Color(entry.getKey().getRed(),entry.getKey().getGreen(),entry.getKey().getBlue());
    					
    				dataset.add(period,(double)species.getValue(),mySpecies.getSpeciesNum());
    				render.setSeriesPaint(mySpecies.getSpeciesNum(),mySpecies.getSpeciesColor());
    				//dataset.add(period, species.getSpeciesPop() , species.getSpeciesNum());
    				//render.setSeriesPaint(species.getSpeciesNum(), species.getSpeciesColor());  
    				
    			System.out.println(dataset);
    			
    		
    		
		}
    	
       // dataset.add(period, randomValue(), SERIES1);
       // dataset.add(period, randomValue(), SERIES2);
       // if(dataset.getItemCount() > COUNT) {
       //     TimePeriod firstItemTime = dataset.getTimePeriod(0);
            //dataset.clear();
      //  }
    }

    private float randomValue() {
        float randValue = (float) (random.nextGaussian() * MINMAX / 3);
        return randValue < 0 ? -randValue : randValue;
    }

    private JFreeChart createAreaChart(final TimeTableXYDataset dataset) {
        final JFreeChart chart = ChartFactory.createStackedXYAreaChart(
                "Species", "Time", "Number of individuals", dataset, PlotOrientation.VERTICAL, false, true, false);

        
        //render.setSeriesPaint(0, Color.RED);
       // render.setSeriesPaint(1, Color.GREEN);

        DateAxis domainAxis = new DateAxis();
        domainAxis.setAutoRange(true);
        domainAxis.setDateFormatOverride(new SimpleDateFormat("HH:mm:ss"));
        domainAxis.setTickUnit(new DateTickUnit(DateTickUnitType.SECOND, 1));

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setRenderer(render);
        plot.setDomainAxis(domainAxis);
        plot.setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
        plot.setForegroundAlpha(0.5f);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setNumberFormatOverride(new DecimalFormat("#,###.#"));
        rangeAxis.setAutoRange(true);

        return chart;
    }

    public void start() {
        timer.start();
    }
/*
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                AreaChartTest demo = new AreaChartTest(TITLE);
                demo.pack();
                RefineryUtilities.centerFrameOnScreen(demo);
                demo.setVisible(true);
                demo.start();
            }
        });
    }*/
}