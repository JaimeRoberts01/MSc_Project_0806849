import java.io.*;
import java.awt.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;


public class ScatterPlot implements ActionListener {
	
	
	/**Instance Variables*/
	
	private Object [][] scatterPlotArray;
	private JFreeChart scatterPlot;
	
	private JFrame frame;
	private JButton Button1, Button2;

	private Processing2 Process2;
	
	
	/**Constructor*/
	
	public ScatterPlot (Processing2 Process2) {
		this.Process2 = Process2;
	}
	
	
	/**This method calls the allPillarAllFrames method and creates a new array using that data. 
	 * An ID is passed to the method so that it can be used to build the line graph.
	 */
	
	public void scatterPlotData (int ID) {
		
		int rows = Process2.getOutputDataByFrame().size();
		int columns = 3;

		scatterPlotArray = new Object [rows][columns]; 

		for (int i = 0; i < rows; i++) {

			scatterPlotArray [i] = (Process2.getOutputDataByFrame().get(i)).toString().split(",");
			System.out.println(Arrays.toString(scatterPlotArray[i]));
		}

		createScatterPlot (ID);
	}
	
	
	/**This method creates the scatter-plot. It uses the ID to run through the array in scatterPlotData
	 * and pulls out the data for a particular pillar. This is then plotted on a formatted graph plot
	 * that can be saved to file.
	 */
	
	public void createScatterPlot (int ID) {
		
		XYSeriesCollection dataset = new XYSeriesCollection ();
		XYSeries series = new XYSeries ("Pillars");

		for (int i =0; i< scatterPlotArray.length; i++ ) {

			int x = Integer.parseInt((String) scatterPlotArray [i][1]); // pillar
			double y = Double.parseDouble((String) scatterPlotArray [i][2]); // force
			series.add (x,y);
		}
		
		dataset.addSeries(series);

		scatterPlot = ChartFactory.createScatterPlot("Pillar Forces: Frame " + ID, "Pillar ID", "Force (pN)", dataset);
		scatterPlot.getTitle().setFont(new Font ("monspaced", Font.BOLD, 14));
		scatterPlot.setBackgroundPaint(Color.white);
		scatterPlot.removeLegend();
		
		XYPlot plot = (XYPlot) scatterPlot.getPlot();
		plot.setBackgroundPaint(Color.lightGray);

		NumberAxis axisY = (NumberAxis) plot.getRangeAxis();
		axisY.setAxisLinePaint(Color.black);
		axisY.setTickMarkPaint(Color.black); 
		axisY.setTickLabelFont(new Font ("monspaced", Font.PLAIN, 11));
		axisY.setLabelFont(new Font ("monspaced", Font.BOLD, 12));
		axisY.setAxisLineStroke(new BasicStroke(1.2f)); 

		ValueAxis axisX = plot.getDomainAxis();
		axisX.setAxisLinePaint(Color.black);
		axisX.setTickMarkPaint(Color.black); 
		axisX.setTickLabelFont(new Font ("monspaced", Font.PLAIN, 11));
		axisX.setLabelFont(new Font ("monspaced", Font.BOLD, 12));
		axisX.setAxisLineStroke(new BasicStroke(1.2f));
		axisX.setLowerMargin(0.01);
		axisX.setUpperMargin(0.01);
		axisX = scatterPlot.getXYPlot().getDomainAxis();
		DecimalFormat format = new DecimalFormat("####");
		((NumberAxis) axisX).setNumberFormatOverride(format);
		axisX.setAutoTickUnitSelection(true);

		XYLineAndShapeRenderer render = (XYLineAndShapeRenderer) plot.getRenderer();
		GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, 
		new Color(5, 5, 140), 0.0F, 0.0F, new Color(209, 16, 196));
		render.setSeriesPaint(0, gradientpaint);
		GeneralPath cross = new GeneralPath();
		cross.moveTo(-1.0f, -3.0f);
		cross.lineTo(1.0f, -3.0f);
		cross.lineTo(1.0f, -1.0f);
		cross.lineTo(3.0f, -1.0f);
		cross.lineTo(3.0f, 1.0f);
		cross.lineTo(1.0f, 1.0f);
		cross.lineTo(1.0f, 3.0f);
		cross.lineTo(-1.0f, 3.0f);
		cross.lineTo(-1.0f, 1.0f);
		cross.lineTo(-3.0f, 1.0f);
		cross.lineTo(-3.0f, -1.0f);
		cross.lineTo(-1.0f, -1.0f);
		cross.closePath();
		render.setSeriesShape(0, cross);
		
		frameLayout(ID);
	}
	
	
	/**This method lays out the graph frame*/
	
	public void frameLayout (int ID) {
		
		frame = new JFrame ();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Frame: " + ID);
		frame.setSize(700, 500);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));

		ChartPanel chartpanel = new ChartPanel(scatterPlot);
		chartpanel.setBackground(Color.white);
		chartpanel.setPreferredSize(new Dimension(700,446));
		chartpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		chartpanel.setVisible(true);
		frame.add(chartpanel);

		Button1 = new JButton ("Save");
		Button1.setPreferredSize(new Dimension(125,23));
		Button1.setOpaque(true);
		Button1.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		Button1.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Button1.setBorder(BorderFactory.createLineBorder(Color.black));
		Button1.addActionListener (this);
		frame.add(Button1);

		Button2 = new JButton ("Close");
		Button2.setPreferredSize(new Dimension(125,23));
		Button2.setOpaque(true);
		Button2.setBackground(Color.getHSBColor(0.0f, 0.0f, 0.90f));
		Button2.setFont(new Font ("SansSerif", Font.PLAIN, 14));
		Button2.setBorder(BorderFactory.createLineBorder(Color.black));
		Button2.addActionListener (this);
		frame.add(Button2);
	}
	
	
	/**FileChooser allows files to be saved in a particular directory and with a give name.
	 * The fileName is passed to the FileWriter method for saving the data.
	 */
	
	public void fileChooser () {
		
		JFileChooser JFC = new JFileChooser ();
		String fileName = "";
		int saveVal = JFC.showSaveDialog(null);

		if (saveVal == JFileChooser.APPROVE_OPTION) {

			File savedFile = JFC.getSelectedFile();

			if (savedFile.exists()) {
				int response = JOptionPane.showConfirmDialog (null, "FILE ALREADY EXISTS. REPLACE?", 
						"Select an Option...", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

				if (response == JOptionPane.YES_OPTION) {
					fileName = savedFile.toString();
					savePlot (fileName);
				}

				else if (response == JOptionPane.NO_OPTION) {
					response = JOptionPane.CLOSED_OPTION;
				}
			}

			else {
				fileName = savedFile.toString();
				savePlot (fileName);	
			}
		}
	}
	
	
	/**This method saves the graph to file*/
	
	public void savePlot (String fileName) {
		
		try {

			ChartUtilities.saveChartAsJPEG(new File(fileName), scatterPlot, 1200, 800);
		} 

		catch (Exception e) {

			System.out.println("Problem occurred creating chart.jska " + e.getMessage());
		}

		System.out.println("I did well2");
		
	}


	/**ActionPerformed method for the save button*/
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == Button1) {
			this.fileChooser();
		}
		
		if (e.getSource() == Button2) {
			frame.dispose();
		}
	}
}