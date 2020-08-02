package initiumCombatSimulator;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
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
	private static JScrollPane scrollResults;
	
	private static ImageIcon icon;
	
	private static JButton editAttacker;
	private static JButton editDefender;
	private static JButton newEntity;
	
	@SuppressWarnings("unused")

	public static void main(String[]args){
		//set up the GUI.
		GUI=new JFrame("Initium Combat Simulator");
		GUI.getContentPane();
		GUI.setLayout(null);
		GUI.setDefaultCloseOperation(3);
		GUI.setResizable(false);
		GUI.setPreferredSize(new Dimension(400,600));
		GUI.pack();
				
		//Find and set the icon for the window
		String sep=System.getProperty("file.separator");
		String iconName="Blizzard";
		icon=new ImageIcon(IO.findLocation()+String.format("%sresources%simages%s",sep,sep,sep)+iconName+".png");		
		GUI.setIconImage(icon.getImage());
		
		//this button, when clicked, will run the simulation.
		runSim=new JButton("Run Simulation");
		runSim.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
				int runs=0;
				boolean attackerValid=false;
				boolean defenderValid=false;
				
				//this checks to see if the user entered an actual number into the textbox.
				if(isInteger(numberOfRuns.getText())) {
					runs=Integer.parseInt(numberOfRuns.getText());
				}
				if(runs<=0) {
					results.append("That number of runs doesn't make sense...\n\n");
				}
				else {
					Entity attacker=null;
					Entity defender=null;
					
					//checks if the attacker's string is a number. if yes, pulls the information from initium itself
					if(isDouble(attackerFile.getText())) {
						attackerValid=true;
						attacker=new Entity(WebParser.getCharacter(Sensitive.getUsername(),Sensitive.getPassword(),attackerFile.getText()), results);
					}
					
					//if its not a number, we assume that it's a file location.
					else {
						String[]pathAttacker={"resources","entities",attackerFile.getText()};
						//we try to validate the file
						if(!IO.validateFile(IO.createPath(pathAttacker,true))){
							results.append("The file "+IO.createPath(pathAttacker, true)+" does not exist.\n\n");
						}
						else {//if the file is valid, we set attackerValid, build the entity and move on.
							attackerValid=true;
							attacker=new Entity(attackerFile.getText(), results);
						}
					}
					
					//checks if the defender's string is a number. if yes, pulls the information from initium itself.
					if(isDouble(defenderFile.getText())) {
						defenderValid=true;
						defender=new Entity(WebParser.getCharacter(Sensitive.getUsername(),Sensitive.getPassword(), defenderFile.getText()), results);
					}
					else {
						String[]pathDefender={"resources","entities",defenderFile.getText()};
						//we try to validate the file
						if(!IO.validateFile(IO.createPath(pathDefender,true))){
							results.append("The file "+IO.createPath(pathDefender, true)+" does not exist.\n\n");
						}
						else {//if the file is valid, we set defenderValid, build the entity and move on
							defenderValid=true;
							defender=new Entity(defenderFile.getText(), results);
						}
					}
					
					//if both entities were created properly, we begin.
					if(attackerValid&&defenderValid&&attacker.getValid()&&defender.getValid()) {
						results.append("Running Simulation. This may take a while...\n\n");
						Combat Battle=new Combat(attacker, defender, results);
						Battle.runSim(runs);
					}
				}
			}	
		});
		runSim.setBounds(100,450,200,100);
		
		//this is a base text field for whatever I need to convey to the user, be it simulation results or error messages.
		results=new JTextArea();
		//results.setBounds(50,175,300,200);
		results.setEditable(false);
		results.setLineWrap(true);
		results.setWrapStyleWord(true);
		
		//this gives the user some context as to how to load the files.
		String[]info={"resources","entities","filename.txt"};
		results.setText("Entity files should be placed in "+IO.createPath(info, true)+".\n\nPlease see template.txt for an example.\n\n");
		
		//this is a scroll pane that will rule the universe
		scrollResults=new JScrollPane(results, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollResults.setBounds(50,225,300,200);
		scrollResults.setVisible(true);
		
		//this is a text field for the file name of the attacker
		//the focus listener emulates the look for real text fields on websites and such, how there's text saying what to do for the field,
		//then you click it and it disappears and you can put whatever you want.
		attackerFile=new JTextField("Attacker File Name here!");
		attackerFile.setBounds(25, 75, 200, 25);
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
		defenderFile.setBounds(25, 125, 200, 25);
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
		numberOfRuns=new JTextField("Number of runs here!");
		numberOfRuns.setBounds(100, 175, 200, 25);
		numberOfRuns.addFocusListener(new FocusListener(){
			//the focus listener emulates the look for real text fields on websites and such, how there's text saying what to do for the field,
			//then you click it and it disappears and you can put whatever you want.
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
		
		//this button will generate an entity editor window for the chosen attacker.
		editAttacker=new JButton("Edit this Entity");
		editAttacker.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//this will validate the given filename.
				String[]values={"resources","entities",attackerFile.getText()};
				String fileName=IO.createPath(values, true);
				
				//if the file exists, then off we go!
				if(IO.validateFile(fileName)){
					EntityEditor attackerEditor=new EntityEditor(attackerFile.getText());
				}
				//otherwise, flame the user a little bit.
				else{
					results.append("The file "+fileName+" does not exist. Please check your spelling.\n\n");
				}
			}			
		});
		editAttacker.setBounds(225, 75, 150, 25);
		
		//this button will generate an entity editor window for the chosen defender.
		editDefender=new JButton("Edit this Entity");
		editDefender.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				//this will validate the given filename.
				String[]values={"resources","entities",defenderFile.getText()};
				String fileName=IO.createPath(values, true);
				
				//if the file exists, then off we go!
				if(IO.validateFile(fileName)){
					EntityEditor defenderEditor=new EntityEditor(defenderFile.getText());
				}
				//otherwise, flame the user a little bit.
				else{
					results.append("The file "+fileName+" does not exist. Please check your spelling.\n\n");
				}
			}
		});
		editDefender.setBounds(225, 125, 150, 25);
		
		//this button will generate a blank entity editor, which serves as an alternate to entering all of the information manually.
		newEntity=new JButton("Create a new Entity");
		newEntity.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {	
				EntityEditor newEntity=new EntityEditor();
			}
		});
		newEntity.setBounds(100, 25, 200, 25);
		
		GUI.add(runSim);
		GUI.add(scrollResults);
		GUI.add(defenderFile);
		GUI.add(attackerFile);
		GUI.add(numberOfRuns);
		GUI.add(editAttacker);
		GUI.add(editDefender);
		GUI.add(newEntity);
		GUI.setVisible(true);
	}
	
	/**
	 * Tests string to see if it can be converted cleanly to an integer
	 * For our uses, sees if the user entered a character ID or a file name.
	 * @param test - the string to test
	 * @return
	 */
	private static boolean isInteger(String test) {
	    try {
	        Integer.parseInt(test);
	        return true;
	    }
	    catch(NumberFormatException e) {
	        return false;
	    }
	}
	
	/**
	 * Tests string to see if it can be converted cleanly to a double.
	 * @param test - the string to test
	 * @return
	 */
	private static boolean isDouble(String test) {
		try {
			Double.parseDouble(test);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
}