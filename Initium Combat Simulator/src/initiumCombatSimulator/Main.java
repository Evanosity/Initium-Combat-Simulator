package initiumCombatSimulator;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;

/**
 * public class Main - this is the main class for the Initium combat simulator. It pulls all of the other classes together into a working program.
 * @author Evanosity
 * @date June 18 2017
 */
public class Main {
	private static JFrame GUI;
	private static JButton runSim;
	private static JTextField attackerFile;
	private static JTextField defenderFile;
	private static JTextField numberOfRuns;
	private static JTextArea results;
	public static void main(String[]args){
		
		//set up the GUI.
		GUI=new JFrame("Initium Combat Simulator");
		GUI.getContentPane();
		GUI.setLayout(null);
		GUI.setDefaultCloseOperation(3);
		GUI.setResizable(false);
		GUI.setPreferredSize(new Dimension(400,500));
		GUI.pack();
		
		//this button, when clicked, will run the simulation.
		runSim=new JButton("Run Simulation");
		runSim.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {		

				int runs=0;
				try{
					runs=Integer.parseInt(numberOfRuns.getText());
				}
				catch(Exception e){
					results.setText("That number of runs doesn't make sense...");
				}
				if(runs<=0){
				}
				else{
					Entity Attacker=new Entity(attackerFile.getText(), results);
					Entity Defender=new Entity(defenderFile.getText(), results);
					Combat Battle=new Combat(Attacker, Defender, results);
					results.setText("Running Simulation. This may take a while....");
					Battle.runSim(runs);
				}
			}	
		});
		runSim.setBounds(100,350,200,100);
		
		//this is a base text field for whatever I need to convey to the user, be it simulation results or error messages.
		results=new JTextArea();
		results.setBounds(50,175,300,150);
		results.setEditable(false);
		
		//this is a text field for the file name of the attacker
		//the focus listener emulates the look for real text fields on websites and such, how there's text saying what to do for the field,
		//then you click it and it disappears and you can put whatever you want.
		attackerFile=new JTextField("Attacker File Name here!");
		attackerFile.setBounds(100, 25, 200, 25);
		attackerFile.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0) {
				if(attackerFile.getText().equals("Attacker File Name here!")){
					attackerFile.setText("");
				}
			}
			public void focusLost(FocusEvent arg0) {
				if(attackerFile.getText().equals("")){
					attackerFile.setText("Attacker File Name here!");
				}
			}
		});
		
		//this is a text field for the file name of the defender
		//the focus listener emulates the look for real text fields on websites and such, how there's text saying what to do for the field,
		//then you click it and it disappears and you can put whatever you want.
		defenderFile=new JTextField("Defender File Name here!");
		defenderFile.setBounds(100, 75, 200, 25);
		defenderFile.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0) {
				if(defenderFile.getText().equals("Defender File Name here!")){
					defenderFile.setText("");
				}
			}
			public void focusLost(FocusEvent arg0) {
				if(defenderFile.getText().equals("")){
					defenderFile.setText("Defender File Name here!");
				}
			}
		});
		
		
		//this is a text field for the number of repetitions to do
		//the focus listener emulates the look for real text fields on websites and such, how there's text saying what to do for the field,
		//then you click it and it disappears and you can put whatever you want.
		numberOfRuns=new JTextField("Number of runs here!");
		numberOfRuns.setBounds(100, 125, 200, 25);
		numberOfRuns.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent arg0) {
				if(numberOfRuns.getText().equals("Number of runs here!")){
					numberOfRuns.setText("");
				}
			}
			public void focusLost(FocusEvent arg0) {
				if(numberOfRuns.getText().equals("")){
					numberOfRuns.setText("Number of runs here!");
				}
			}
		});
		
		GUI.add(runSim);
		GUI.add(results);
		GUI.add(defenderFile);
		GUI.add(attackerFile);
		GUI.add(numberOfRuns);
		GUI.setVisible(true);
	}
}