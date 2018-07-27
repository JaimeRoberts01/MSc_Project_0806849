import java.awt.*;


import javax.swing.*;
import javax.swing.border.*;


@SuppressWarnings("serial")
public class ReportFrame extends JFrame {


	private JTextArea displayFile;


	public ReportFrame () {		

		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		setTitle ("File Contents");
		setLocation (1500, 330);
		setSize (700, 330);
		setVisible (true);
		setResizable (false); 
		frameComponents ();
	}


	public void frameComponents () {

		displayFile = new JTextArea ();
		displayFile.setLineWrap (true);
		displayFile.setWrapStyleWord (true);
		displayFile.setFont (new Font ("Courier", Font.PLAIN, 14));
		displayFile.setBorder (new EmptyBorder (10,10,10,10));
		displayFile.setEditable (false);
		JScrollPane scroll = new JScrollPane (displayFile);
		add (scroll);
	}


	public void reportFormatter (String l) {
		
		displayFile.append (l + "\n");	
	}	
}