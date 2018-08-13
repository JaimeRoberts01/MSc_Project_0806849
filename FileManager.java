import java.io.*; 
import java.util.*;
import javax.swing.*;


public class FileManager {


	/*Instance variables*/
	private Processing Process;
	private Processing2 Process2;
	
	private ArrayList <String> fileLine;


	/*Constructor*/
	public FileManager(Processing Process, Processing2 Process2) {
		this.Process = Process;
		this.Process2 = Process2;
	}

	/*FileReader for reading in the files - this doesn't yet work.*/
	public void fileReader (String fileName) { 

		FileReader reader = null;	
		BufferedReader bufferedReader = null;
		String file = null;
		String line = null;

		try {

			file = fileName;
			System.out.println ("WE'RE IN FM: " + file);
			fileLine = new ArrayList<String>();

			reader = new FileReader (file);
			bufferedReader = new BufferedReader(reader);

			while ((line = bufferedReader.readLine()) !=null) {
				fileLine.add (line);
			}

			Process = new Processing ();
			Process.data(fileLine);

			reader.close();	
			bufferedReader.close();
		}

		catch (IOException IOE) {

			JOptionPane.showMessageDialog (null, "FILE NOT FOUND", "ERROR", JOptionPane.ERROR_MESSAGE);
			IOE.printStackTrace();	
		}

		catch (InputMismatchException IME) {

			JOptionPane.showMessageDialog (null, "INVALID FILE", "ERROR", JOptionPane.ERROR_MESSAGE);
			IME.printStackTrace();	
		}	
	}


	/*FileWriter writes the getByFrame, getByPillar and MultiPillar data to file*/
	//public void fileWriter (String identifier, String output) {
	public void fileWriter (String identifier, String fileName) {

		FileWriter writer = null;

		try {

			try {

				writer = new FileWriter (fileName);
				
				if (identifier.equals("Data")) {
					writer.write(Process.outputFile());
				}
				
				else if (identifier.equals("Statistical Data")) {
					writer.write(Process2.outputFile());
				}
				
				else if (identifier.equals("Frame Data")) {
					writer.write(Process2.outputByFrame());
				}

				else if (identifier.equals("Pillar Data")) {
					writer.write(Process2.outputByPillar());
				}

				else if (identifier.equals("Multipillar Data")) {
					writer.write(Process2.outputMultipillar());
				}
			}

			finally {

				writer.close();	
			}
		}

		catch (IOException IOE) {

			IOE.printStackTrace();	
		}
	}
}