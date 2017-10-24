package initiumCombatSimulator;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * EntityEditor class - this class provides a GUI for a user-friendly way to edit and create entities.
 * @author Evanosity
 *
 */
public class EntityEditor extends IO{
	private static JFrame Editor;
	private static JTextField[] characterFields;
	private static JTextField[][] equipmentFields;
	private static JTextField outputError;
	private static JButton saveButton;
	private static JOptionPane confirmDialog;
	private static String[]equips={"Head","Chest","Shirt","Gloves","Legs","Boots","RightRing","LeftRing","Neck"};
	private static String[]characterStuff={"File Name","Character Name","Strength","Dexterity","Intelligence","HP"};
	private static String[]equipStats={"DP","BC","DR","Bldg","Prce","Slsh"};
	
	private static String fileName;
	
	/**
	 * constructor EntityEditor - initializes a blank entityeditor, ripe for the user to fill.
	 * @param output - instant error reporting
	 */
	public EntityEditor(JTextArea output) {
		super(output);
		createEditor();
	}
	
	/**
	 * constructor EntityEditor - initializes a already filled entityeditor, so that the user can edit a pre-existing file
	 * @param output - instant error reporting
	 * @param fileName - the file to pull from. Must be in the director \resources\entities\fileName.txt
	 */
	public EntityEditor(JTextArea output, String fileToLoad){
		super(output);
		fileName=fileToLoad;
		createEditor(readFile(fileName));
	}
	
	/**
	 * public void createEditor - creates the editor GUI without a source file.
	 */
	public void createEditor(){
		createEditorGUI();
		Editor.setVisible(true);
	}
	
	/**
	 * public void createEditor - creates the editor GUI with a source file
	 * @param toLoad - an string array representation of that file
	 */
	public void createEditor(String[] toLoad){
		createEditorGUI();
		initFields();
		fillFields(toLoad);
		Editor.setVisible(true);
	}
	
	/**
	 * public void createEditorGUI - creates the actual editor
	 */
	public void createEditorGUI(){
		Editor=new JFrame("Entity Editor - "+fileName);
		Editor.getContentPane();
		Editor.setLayout(null);
		Editor.setDefaultCloseOperation(3); //CHANGE TO 2 WHEN YOU'RE DONE YOU FUCKING FOOL
		Editor.setResizable(false);
		Editor.setPreferredSize(new Dimension(1200,450));
		Editor.pack();
		
	}
	
	/**
	 * public void initFields - this method initializes, creates and places all of the entry fields and labels.
	 */
	public void initFields(){
		characterFields=new JTextField[12];
		
		for(int i=0;i!=6;i++){
			characterFields[i]=new JTextField(characterStuff[i]);
			characterFields[i].setBounds(25,25+(i*50),100,25);
			characterFields[i].setOpaque(false);
			characterFields[i].setBorder(null);
			characterFields[i].setEditable(false);
			Editor.add(characterFields[i]);
		}
		for(int i=6;i!=12;i++){
			if(i==6){
				characterFields[i]=new JTextField(fileName);
			}
			else{
				characterFields[i]=new JTextField("");
			}
			characterFields[i].setBounds(25,((i-5)*50),100,25);
			Editor.add(characterFields[i]);
		}
		
		equipmentFields=new JTextField[9][15];
		
		for(int i=0;i!=3;i++){
			equipmentFields[i][0]=new JTextField(equips[i]);
			equipmentFields[i][0].setBounds(150,25+(100*i),250,25);
			equipmentFields[i][0].setOpaque(false);
			//equipmentFields[i][0].setBorder(null);
			equipmentFields[i][0].setEditable(false);
			Editor.add(equipmentFields[i][0]);
			
			equipmentFields[i][1]=new JTextField("Name:");
			equipmentFields[i][1].setBounds(150,50+(100*i),40,25);
			equipmentFields[i][1].setOpaque(false);
			equipmentFields[i][1].setBorder(null);
			equipmentFields[i][1].setEditable(false);
			Editor.add(equipmentFields[i][1]);
			
			equipmentFields[i][2]=new JTextField("");
			equipmentFields[i][2].setBounds(190,50+(100*i),210,25);
			Editor.add(equipmentFields[i][2]);
			
			for(int f=3;f!=9;f++){
				equipmentFields[i][f]=new JTextField(equipStats[f-3]);
				equipmentFields[i][f].setBounds(150+(30*(f-3)),75+(100*i),30,25);
				equipmentFields[i][f].setOpaque(false);
				equipmentFields[i][f].setBorder(null);
				equipmentFields[i][f].setEditable(false);
				Editor.add(equipmentFields[i][f]);
			}
			
			for(int f=9;f!=15;f++){
				equipmentFields[i][f]=new JTextField("");
				equipmentFields[i][f].setBounds(150+(30*(f-9)),100+(100*i),30,25);
				Editor.add(equipmentFields[i][f]);
			}
		}
		for(int i=3;i!=6;i++){
			equipmentFields[i][0]=new JTextField(equips[i]);
			equipmentFields[i][0].setBounds(450,25+(100*(i-3)),250,25);
			equipmentFields[i][0].setOpaque(false);
			//equipmentFields[i][0].setBorder(null);
			equipmentFields[i][0].setEditable(false);
			Editor.add(equipmentFields[i][0]);
			
			equipmentFields[i][1]=new JTextField("Name:");
			equipmentFields[i][1].setBounds(450,50+(100*(i-3)),40,25);
			equipmentFields[i][1].setOpaque(false);
			equipmentFields[i][1].setBorder(null);
			equipmentFields[i][1].setEditable(false);
			Editor.add(equipmentFields[i][1]);
			
			equipmentFields[i][2]=new JTextField("");
			equipmentFields[i][2].setBounds(490,50+(100*(i-3)),210,25);
			Editor.add(equipmentFields[i][2]);
			
			for(int f=3;f!=9;f++){
				equipmentFields[i][f]=new JTextField(equipStats[f-3]);
				equipmentFields[i][f].setBounds(450+(30*(f-3)),75+(100*(i-3)),30,25);
				equipmentFields[i][f].setOpaque(false);
				equipmentFields[i][f].setBorder(null);
				equipmentFields[i][f].setEditable(false);
				Editor.add(equipmentFields[i][f]);
			}
			
			for(int f=9;f!=15;f++){
				equipmentFields[i][f]=new JTextField("");
				equipmentFields[i][f].setBounds(450+(30*(f-9)),100+(100*(i-3)),30,25);
				Editor.add(equipmentFields[i][f]);
			}
		}
		for(int i=6;i!=9;i++){
			equipmentFields[i][0]=new JTextField(equips[i]);
			equipmentFields[i][0].setBounds(750,25+(100*(i-6)),250,25);
			equipmentFields[i][0].setOpaque(false);
			//equipmentFields[i][0].setBorder(null);
			equipmentFields[i][0].setEditable(false);
			Editor.add(equipmentFields[i][0]);
			
			equipmentFields[i][1]=new JTextField("Name:");
			equipmentFields[i][1].setBounds(750,50+(100*(i-6)),40,25);
			equipmentFields[i][1].setOpaque(false);
			equipmentFields[i][1].setBorder(null);
			equipmentFields[i][1].setEditable(false);
			Editor.add(equipmentFields[i][1]);
			
			equipmentFields[i][2]=new JTextField("");
			equipmentFields[i][2].setBounds(790,50+(100*(i-6)),210,25);
			Editor.add(equipmentFields[i][2]);
			
			for(int f=3;f!=9;f++){
				equipmentFields[i][f]=new JTextField(equipStats[f-3]);
				equipmentFields[i][f].setBounds(750+(30*(f-3)),75+(100*(i-6)),30,25);
				equipmentFields[i][f].setOpaque(false);
				equipmentFields[i][f].setBorder(null);
				equipmentFields[i][f].setEditable(false);
				Editor.add(equipmentFields[i][f]);
			}
			
			for(int f=9;f!=15;f++){
				equipmentFields[i][f]=new JTextField("");
				equipmentFields[i][f].setBounds(750+(30*(f-9)),100+(100*(i-6)),30,25);
				Editor.add(equipmentFields[i][f]);
			}
		}
	}
	
	/**
	 * public void fillFields - loads the editor with the specified file.
	 * @param toLoad - String array representation of that file.
	 */
	public void fillFields(String[] toLoad){
		String[]base=toLoad;
		String[][]dd=new String[19][7];
		for(int i=6;i!=toLoad.length-1;i++){
			System.out.println(toLoad[i]);
			for(int f=0;f!=7;f++){
				try{
					dd[i][f]=base[i].substring(0, base[i].indexOf("/"));
					base[i]=base[i].substring(base[i].indexOf("/")+1, base[i].length());
				}
				catch(StringIndexOutOfBoundsException e){
					dd[i][f]=base[i];
				}
			}
		}
		//loop to fill all the fields with the specified info
	}
	
	public void saveFile(){
		
	}
}