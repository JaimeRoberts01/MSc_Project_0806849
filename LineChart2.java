import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;


public class LineChart2 implements ActionListener {


	/**Instance Variables*/

	private Processing2 Process2;
	private JFreeChart lineChart;
	private XYPlot plot;
	private Object [][] lineChartArray;
	private JFrame frame;
	private JButton Button1, Button2;


	/**Constructor*/

	public LineChart2 (Processing2 Process2) {
		this.Process2 = Process2;
	}

	public void lineChartData (Object[] values) { // getByMultipillar


		int rows = Process2.getOutputMultipillar().size();
		int columns = 3;

		lineChartArray = new Object [rows][columns]; 
		int [] ID = null;
		
		try {

		for (int i = 0; i < rows; i++) {

			lineChartArray [i] = (Process2.getOutputMultipillar().get(i)).toString().split(",");
			System.out.println("LineChart2: " + Arrays.toString(lineChartArray[i]));
		}

		ID = new int [values.length];

		for (int i = 0; i < values.length; i++) { 

			ID [i] = Integer.parseInt((String)values [i]);	
		}
		
		}
		catch (NullPointerException NPE) {
			System.err.println("Invalid input");
		}

		createLineChart (ID);	
	}
	
	
	public void lineChartData2 (Object[] values) { // all data 
		
		int rows = Process2.getDataByPillarFrame().size();
		int columns = 3;
		
		int [] ID = null;

		lineChartArray = new Object [rows][columns]; 

		for (int i = 0; i < rows; i++) {

			lineChartArray [i] = (Process2.getDataByPillarFrame().get(i)).toString().split(",");
			System.out.println(Arrays.toString(lineChartArray[i]));
		}	
		
		ID = new int [values.length];

		for (int i = 0; i < values.length; i++) { // creating a new array of the pillar index

			ID [i] = (int) values [i];	
		}
		createLineChart (ID);
	}


	public void createLineChart (int [] ID) {

		XYSeriesCollection dataset = new XYSeriesCollection ();

		double y = 0.0;
		int x = 0;

		for (int i = 0; i< ID.length; i++) {

			XYSeries series = new XYSeries (ID[i]);
			int inputID = ID[i];

			for (int j =0; j< lineChartArray.length; j++) {

				int pillar = Integer.parseInt((String) lineChartArray [j][1]);

				if (inputID == pillar) {
					x = Integer.parseInt((String) lineChartArray [j][0]); // frame
					y = Double.parseDouble((String) lineChartArray [j][2]); // force
					series.add(x, y);
				}
			}

			dataset.addSeries(series);
		}

		lineChart = ChartFactory.createXYLineChart("Forces Over Time", "Frame ID", "Force (pN)", dataset);
		lineChart.getTitle().setFont(new Font ("monspaced", Font.BOLD, 14));
		plot =  lineChart.getXYPlot();
		
		
		Axis axisY = plot.getRangeAxis();
		axisY.setAxisLinePaint(Color.black);
		axisY.setTickMarkPaint(Color.black); 
		axisY.setTickLabelFont(new Font ("monspaced", Font.PLAIN, 11));
		axisY.setLabelFont(new Font ("monspaced", Font.BOLD, 12));
		axisY.setAxisLineStroke(new BasicStroke(1.2f));

		NumberAxis axisX = (NumberAxis) plot.getDomainAxis();
		//axisX.setTickUnit(new NumberTickUnit(5));
		axisX.setAxisLinePaint(Color.black);
		axisX.setTickMarkPaint(Color.black); 
		axisX.setTickLabelFont(new Font ("monspaced", Font.PLAIN, 11));
		axisX.setLabelFont(new Font ("monspaced", Font.BOLD, 12));
		axisX.setAxisLineStroke(new BasicStroke(1.2f));
		axisX.setLowerMargin(0.01);
		axisX.setUpperMargin(0.01);
		axisX.setLowerBound(0.0);
		axisX.setAutoTickUnitSelection(true);

		XYLineAndShapeRenderer render = (XYLineAndShapeRenderer) plot.getRenderer();
				
		render.setSeriesPaint(0, new Color (5,5,140));
		render.setSeriesPaint(1, new Color (209,16,196));
		render.setSeriesPaint(2, new Color(204, 0, 0));
		render.setSeriesPaint(3, new Color(9, 224, 2));
		render.setSeriesPaint(4, new Color(249, 245, 7));
		
		render.setSeriesOutlineStroke(0, new BasicStroke(1));
		render.setSeriesOutlineStroke(1, new BasicStroke(1));
		render.setSeriesOutlineStroke(2, new BasicStroke(1));
		render.setSeriesOutlineStroke(3, new BasicStroke(1));
		render.setSeriesOutlineStroke(4, new BasicStroke(1));
		
		

		frameLayout (ID);
	}


	/**This method lays out the graph frame*/

	public void frameLayout (int [] ID) {

		//ChartFrame frame = new ChartFrame("Multipillar Data", lineChart);

		frame = new JFrame ();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Forces Over Time - Multipillar Data");
		frame.setSize(700, 500);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));

		ChartPanel chartpanel = new ChartPanel(lineChart);
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
					saveChart (fileName);
				}

				else if (response == JOptionPane.NO_OPTION) {
					response = JOptionPane.CLOSED_OPTION;
				}
			}

			else {
				fileName = savedFile.toString();
				saveChart (fileName);	
			}
		}
	}


	/**This method saves the graph to file*/

	public void saveChart (String fileName) {

		try {

			ChartUtilities.saveChartAsJPEG(new File(fileName), lineChart, 1200, 800);
		} 

		catch (Exception e) {

			System.out.println("Problem occurred creating chart.jska " + e.getMessage());
		}

		System.out.println("I did well");
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