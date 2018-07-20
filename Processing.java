import java.util.*;


public class Processing {


	ArrayList<Double> nanometers;
	private ArrayList<Double> picoNewtons;
//	private int frame, pillar;
//	private double x, y, dx, dy, deflection;
	private Object [][] data;
	private Object [][] newData;
	private int frames, uniquePillars; 


	public Processing () {	

	}


	/*Getters and Setters*/

	public Object[][] data() {return data;}
	public void setdata(Object[][] newData) {this.data = newData;}
	public int getFrames() {return frames;}
	public void setFrames(int frames) {this.frames = frames;}
	public int getUniquePillars() {return uniquePillars;}
	public void setUniquePillars(int uniquePillars) {this.uniquePillars = uniquePillars;}


	/*This method breaks the ArrayList<String> into a 2D array using "," as the delimiter.
	Then adds the values of the different indices to specific arrayLists for further work*/

	public Processing (ArrayList<String> fileLine) { 

		for (int i = 0; i < fileLine.size(); i++) { // Removes any line in the arrayList that contains "NaN" as a value.

			if (fileLine.get(i).contains("NaN")) {
				fileLine.remove(i);
			}
		}

		int rows = fileLine.size();
		int columns = 7;

		data = new Object [rows][columns];

		for (int i = 0; i < rows; i++) {

			data [i] = fileLine.get(i).split(",");	
			System.out.println(Arrays.toString(data[i]));
		}
	}
	

	public ArrayList<Double> nanoMeters (double conversion) {

		nanometers = new ArrayList <Double>();

		for (int i= 0; i<data.length; i++) {

			double nm =  Double.parseDouble((String) data [i][6]) * conversion;
			nanometers.add(nm);
		}

		return nanometers;
	}


	public ArrayList<Double> forces (double youngsModulous, double pillarD, double pillarL) {

		picoNewtons = new ArrayList <Double>();

		double constant = (double) 3/64;
		double E = youngsModulous; //double E = 2.0 for PDMS;
		double pi = Math.PI;
		double diameter = pillarD; //double diameter = 0.5 for the 500 nm pillars;
		double length = pillarL; //double length = 1.3 for the 500 nm pillars;

		for (int i=0; i<data.length; i++) {

			double picoMeters = (double) nanometers.get(i)*1000;
			double picoForces = (constant * pi *E * (Math.pow(diameter, 4)/Math.pow(length, 3))*picoMeters);
			picoNewtons.add(picoForces);
		}

		return picoNewtons;
	}


	public Object [][] newDataArray () {

		int columns = 9;
		int rows = data.length;
		
		newData = new Object [rows][columns];

		for (int i = 0; i < rows; i++) {
					
			newData [i][0] = data [i][0]; //frame
			newData [i][1] = data [i][1]; // pillar
			newData [i][2] = data [i][2]; // x
			newData [i][3] = data [i][3]; // y
			newData [i][4] = data [i][4]; // dx
			newData [i][5] = data [i][5]; // dy
			newData [i][6] = data [i][6]; // deflection
			newData [i][7] = nanometers.get(i); // nanometres	
			newData [i][8] = picoNewtons.get(i); // picometers

			System.out.println("Here is newData: " + Arrays.toString(newData[i]));
		}

		return data;
	}


	public int getNumberOfFrames () {

		frames = Integer.parseInt((String) data [0][0]);

		for (int i = 0; i< data.length; i++) {

			if (Integer.parseInt((String) data [i][0]) > frames) {

				frames = Integer.parseInt((String) data [i][0]); 
			}
		}

		System.out.println("Here are the number of frames: " + frames);
		return frames;
	}


	public int getNumberOfUniquePillars () {

		uniquePillars = 0;
		int pillars = 0; // there will never be a pillar 0.

		for (int i = 0; i< data.length; i++) {

			if (Integer.parseInt((String) data [i][1]) > pillars) {

				pillars = Integer.parseInt((String) data [i][1]);
				uniquePillars ++;	
			}
		}

		System.out.println("Here are the number of unique pillars: " + uniquePillars);
		return uniquePillars;
	}


	//	public void byFrame (int ID) { //This will need checks for non-existent frames. This should probably be in its own class.
	//
	//		ArrayList <Double> byFrame = new ArrayList<Double>();
	//
	//		for (int i = 0; i<rows; i++) {
	//
	//			int frameID = (int) newData [i][0];
	//			if (frameID == ID) {
	//				double something = (double) newData [i][8];
	//				byFrame.add(something);
	//				//System.out.println("Here is the row for Frame: " + Arrays.toString(newData[i]));
	//				//System.out.println("Here is picoNewtons for Frame: " + (newData[i][8]));
	//			}
	//		}
	//
	//		System.out.println("Here is byframe: " + byFrame);
	//		for (double d : byFrame) {
	//			frameAverage += d;
	//		}
	//
	//		frameAverage = frameAverage/byFrame.size();
	//		//System.out.println("Here is byframe average: " + frameAverage);
	//	}
	//
	//
	//	public void byPillar (int ID) { //This will need checks for non-existent trajectories. This should probably be in its own class with byFrame.
	//
	//		ArrayList <Double> byPillar = new ArrayList<Double>();
	//
	//		for (int i = 0; i<rows; i++) {
	//
	//			int pillarID = (int) newData[i][1];
	//			if (pillarID == ID) {
	//				double something = (double) newData[i][8];
	//				byPillar.add(something);
	//				//System.out.println("Here is the row for Trajectory: " + Arrays.toString(newData[i]));
	//				//System.out.println("Here is picoNewtons for Trajectory: " + (newData[i][8]));
	//			}
	//		}
	//
	//		System.out.println("Here is byPillar: " + byPillar);
	//		for (double d: byPillar){
	//			pillarAverage += d; 	
	//		}
	//
	//		pillarAverage = pillarAverage/byPillar.size();
	//		//System.out.println("Here is byPillar average: " + pillarAverage);
	//	}


	public void allFrames () {

		for (int i = 0; i<uniquePillars; i++) {

			double averageTest =0.0;
			int pillarCounter = Integer.parseInt((String) newData [i][1]);
			System.out.println("This is the value of pillarCounter: " + pillarCounter);

			for (int j =0; j< newData.length; j++) {

				int pillarID = Integer.parseInt((String) newData [j][1]);
	
				if (pillarID == pillarCounter) {
					double something = (double) newData [j][8];
					averageTest += (double) newData [j][8];
					System.out.println("This is the force value matching the pillar: " + something);
				}
			}	

			System.out.println("Averagetest: " + averageTest/frames);
			System.out.println("This is the next value of pillarCounter: " + pillarCounter);
		}
	}


	public String outputString () { // For the reader - this will be deleted eventually.

		StringBuilder SB = new StringBuilder();
		for (int i = 0; i <newData.length; i++) {
		
			SB.append(String.format("%3s",newData[i][0]) + "\t" + String.format("%8.4s", newData [i][1])  + "\t"  + String.format("%8.7s", newData [i][4]) + "\t" 
					+ String.format("%8.7s", newData[i][5]) + "\t"  + String.format("%8.7s", newData [i][6]) + "\t" + String.format("%10.7s", newData [i][7]) 
					+ "\t" + String.format("%10.8s", newData [i][8]) + "\n");
		}

		String output = SB.toString();
		return output;
	}


	public String outputFile () { // For a CSV file.

		String header = ("frame" + "," + "pillar" + "," + "x" + "," + "y" + ","  + "dx" + ","
				+ "dy" + "," + "deflection" + "," + "nanometers"+ "," + "picoNewtons" + ",");
		String body = "";

		for (int i =0; i<newData.length;i++) {
	
			body += Arrays.toString(newData[i]) +"\n";
		}

		StringBuilder SB = new StringBuilder();
		SB.append(header + "\n");
		SB.append(body);
		String outputFile = SB.toString();
		outputFile = outputFile.replace("[", "");
		outputFile = outputFile.replace("]", "");

		return outputFile;
	}
}