package initiumCombatSimulator;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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
	private static JTextField[][] weaponFields;
	private static JCheckBox[][] damageTypes;
	private static JTextField outputError;
	private static JButton saveButton;
	private static JOptionPane confirmDialog;
	
	private static String[]equips={"Head","Chest","Shirt","Gloves","Legs","Boots","RightRing","LeftRing","Neck"};
	private static String[]characterStuff={"File Name","Character Name","Strength","Dexterity","Intelligence","HP"};
	private static String[]equipStats={"DP","BC","DR","Bldg","Prce","Slsh"};
	private static String[]weaponStats={"Xdy","xdY","CrtM","Crt%","Bldg","Prce","Slsh","2H"};
	
	private static String fileName;
	
	/**
	 * constructor EntityEditor - initializes a blank entityeditor, ripe for the user to fill.
	 * @param output - instant error reporting
	 */
	public EntityEditor(JTextArea output) {
		super(output);
		fileName="";
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
		initFields();
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
		Editor.setDefaultCloseOperation(3); //2 means just the window closes; 3 means the entire programs ends.
		Editor.setResizable(false);
		Editor.setPreferredSize(new Dimension(1325,450));
		Editor.pack();
		
	}
	
	/**
	 * public void initFields - this method initializes, creates and places all of the entry fields and labels.
	 */
	public void initFields(){
		characterFields=new JTextField[12];
		
		//initialize the fields for character stuff
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
		
		//initialize all the equipment fields
		for(int i=0;i!=3;i++){
			equipmentFields[i][0]=new JTextField(equips[i]);
			equipmentFields[i][0].setBounds(150,25+(100*i),250,25);
			equipmentFields[i][0].setOpaque(false);
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
		
		//initialize all the weapon fields
		weaponFields=new JTextField[2][27];
		
		weaponFields[0][0]=new JTextField("Left Hand Weapon");
		weaponFields[0][0].setBounds(1050,25,250,25);
		weaponFields[0][0].setOpaque(false);
		weaponFields[0][0].setEditable(false);
		Editor.add(weaponFields[0][0]);
		
		weaponFields[1][0]=new JTextField("Right Hand Weapon");
		weaponFields[1][0].setBounds(1050,175,250,25);
		weaponFields[1][0].setOpaque(false);
		weaponFields[1][0].setEditable(false);
		Editor.add(weaponFields[1][0]);
		
		for(int i=0;i!=2;i++){
			weaponFields[i][1]=new JTextField("Name:");
			weaponFields[i][1].setBounds(1050,50+(i*150),40,25);
			weaponFields[i][1].setOpaque(false);
			weaponFields[i][1].setBorder(null);
			weaponFields[i][1].setEditable(false);
			Editor.add(weaponFields[i][1]);
			
			weaponFields[i][2]=new JTextField("");
			weaponFields[i][2].setBounds(1090,50+(i*150),210,25);
			Editor.add(weaponFields[i][2]);
			
			for(int f=3;f!=9;f++){
				weaponFields[i][f]=new JTextField(equipStats[f-3]);
				weaponFields[i][f].setBounds(1050+(30*(f-3)),75+(i*150),30,25);
				weaponFields[i][f].setOpaque(false);
				weaponFields[i][f].setBorder(null);
				weaponFields[i][f].setEditable(false);
				Editor.add(weaponFields[i][f]);
			}
			
			for(int f=9;f!=15;f++){
				weaponFields[i][f]=new JTextField("");
				weaponFields[i][f].setBounds(1050+(30*(f-9)),100+(i*150),30,25);
				Editor.add(weaponFields[i][f]);
			}
			for(int f=15;f!=23;f++){
				weaponFields[i][f]=new JTextField(weaponStats[f-15]);
				weaponFields[i][f].setBounds(1050+(30*(f-15)),125+(i*150),30,25);
				weaponFields[i][f].setOpaque(false);
				weaponFields[i][f].setBorder(null);
				weaponFields[i][f].setEditable(false);
				Editor.add(weaponFields[i][f]);
			}
			for(int f=23;f!=27;f++){
				weaponFields[i][f]=new JTextField("");
				weaponFields[i][f].setBounds(1050+(30*(f-23)),150+(i*150),30,25);
				Editor.add(weaponFields[i][f]);
			}
		}
		
		//initialize the damage type/2h stuff
		
		damageTypes=new JCheckBox[2][4];
		for(int i=0;i!=2;i++){
			for(int f=0;f!=4;f++){
				damageTypes[i][f]=new JCheckBox();
				damageTypes[i][f].setBounds(1175+(30*f),150+(i*150),25,25);
				Editor.add(damageTypes[i][f]);
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
		//Load the file into a 2d string array.
		for(int i=5;i!=toLoad.length-1;i++){
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
		
		//load character info
		for(int i=0;i!=5;i++){
			characterFields[i+7].setText(toLoad[i]);
		}
		
		//load all the armor pieces
		for(int i=5;i!=dd.length-5;i++){
			equipmentFields[i-5][2].setText(dd[i][6]);
			for(int f=0;f!=6;f++){
				equipmentFields[i-5][f+9].setText(dd[i][f]);
			}
		}
		
		//load both the weapons
		for(int i=0;i!=2;i++){
			weaponFields[i][2].setText(dd[15+(i*2)][6]);
			for(int f=0;f!=6;f++){
				weaponFields[i][9+f].setText(dd[15+(i*2)][f]);
			}
			for(int f=0;f!=4;f++){
				weaponFields[i][23+f].setText(dd[14+(i*2)][f]);
			}
			if(dd[14+(i*2)][4].contains("b")||dd[14+(i*2)][4].contains("B")){
				damageTypes[i][0].setSelected(true);
			}
			if(dd[14+(i*2)][4].contains("p")||dd[14+(i*2)][4].contains("P")){
				damageTypes[i][1].setSelected(true);
			}
			if(dd[14+(i*2)][4].contains("s")||dd[14+(i*2)][4].contains("S")){
				damageTypes[i][2].setSelected(true);
			}
			if(dd[14+(i*2)][4].contains("t")||dd[14+(i*2)][4].contains("T")){
				damageTypes[i][3].setSelected(true);
			}
		}
	}
	
	/**
	 * public void saveFile - saves the current entity a file. Will only save if it has been formatted correctly.
	 */
	public void saveFile(){
		
	}
}