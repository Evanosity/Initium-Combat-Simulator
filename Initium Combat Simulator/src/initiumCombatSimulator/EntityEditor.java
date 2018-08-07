package initiumCombatSimulator;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * EntityEditor class - this class provides a GUI for a user-friendly way to edit and create entities.
 * This entire class is a mess. That being said, it works. It might get a reader friendly rewrite eventually. Who knows.
 * @author Evanosity
 *
 */
public class EntityEditor extends IO{
	private static JFrame Editor;
	private static JTextField[] characterFields;
	private static JTextField[][] equipmentFields;
	private static JTextField[][] weaponFields;
	private static JCheckBox[][] damageTypes;
	private static JTextArea outputError;
	private static JScrollPane outputErrorScroll;
	private static JButton saveButton;
	
	private static String[]equips={"Head","Chest","Shirt","Gloves","Legs","Boots","RightRing","LeftRing","Neck"};
	private static String[]characterStuff={"File Name","Character Name","Strength","Dexterity","Intelligence","HP"};
	private static String[]equipStats={"DP","BC","DR","Bldg","Prce","Slsh"};
	private static String[]weaponStats={"Xdy","xdY","CrtM","Crt%","Bldg","Prce","Slsh","2H"};
	
	private static String fileName;
	
	/**
	 * constructor EntityEditor - initializes a blank entityeditor, ripe for the user to fill.
	 */
	public EntityEditor() {
		super(outputError);
		fileName="";
		createEditor();
	}
	
	/**
	 * constructor EntityEditor - initializes a already filled entityeditor, so that the user can edit a pre-existing file
	 * @param fileName - the file to pull from. Must be in the directory \resources\entities\fileName.txt
	 */
	public EntityEditor(String fileToLoad){
		super(outputError);
		fileName=fileToLoad;
		createEditor(readFile(fileName, "entities"));
	}
	
	/**
	 * public void createEditor - creates the editor GUI without a source file.
	 */
	public void createEditor(){
		createEditorGUI();
		initFields();
		createErrorBox();
		Editor.setVisible(true);
	}
	
	/**
	 * public void createEditor - creates the editor GUI with a source file
	 * @param toLoad - an string array representation of that file
	 */
	public void createEditor(String[] toLoad){
		createEditorGUI();
		initFields();
		createErrorBox();
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
		Editor.setDefaultCloseOperation(2); //2 means just the window closes; 3 means the entire programs ends.
		Editor.setResizable(false);
		Editor.setPreferredSize(new Dimension(1325,450));
		Editor.pack();
		
	}
	
	public void createErrorBox(){
		//this is a base text field for whatever I need to convey to the user, be it simulation results or error messages.
		outputError=new JTextArea();
		outputError.setEditable(false);
		outputError.setLineWrap(true);
		outputError.setWrapStyleWord(true);
		
		//this is a scroll pane that will rule the universe
		outputErrorScroll=new JScrollPane(outputError, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		outputErrorScroll.setBounds(400,340,500,75);
		outputErrorScroll.setVisible(true);
		
		outputError.append("Errors will show up here when you try to save.\n\n");
		
		Editor.add(outputErrorScroll);
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
		
		saveButton=new JButton("Save to file");
		saveButton.setBounds(25, 350, 200, 25);
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.out.println("pushed");
				File file=new File("");
				String sep=System.getProperty("file.separator");
				file=new File(file.getAbsolutePath()+String.format("%sresources%sentities%s"+characterFields[6].getText(), sep, sep, sep)); 
				if(file.exists()){
				    int reply = JOptionPane.showConfirmDialog(null, "File "+characterFields[6].getText()+" already exists! Do you want to overwrite it?", "File "+characterFields[6].getText()+" already exists!", JOptionPane.YES_NO_OPTION);
			        if (reply == JOptionPane.YES_OPTION) {
			          saveFile();
			        }
			        else {
			           JOptionPane.showMessageDialog(null, "File save cancelled.");
			        }
				}
				else{
					saveFile();
				}
			}
		});
		Editor.add(saveButton);
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
	
	public void displayErrors(String toDisplay){
		outputError.setText(toDisplay);
	}
	
	/**
	 * public void saveFile - saves the current entity a file. Will only save if it has been formatted correctly.
	 */
	@SuppressWarnings("unused")
	public void saveFile(){
		//checks to see if all the fields are formatted correctly. Then, if so, writes to the specified file.
		String[] toWrite=new String[18];
		String issues="";
		boolean isValid=true;
		
		System.out.println(characterFields[6].getText());

		
		for(int i=0; i!=5; i++){
			toWrite[i]=characterFields[i+7].getText();
			//System.out.println(toWrite[i]);
		}
		
		//this loop builds the armor values within an array of text, identical to how the main program reads it.
		for(int i=5;i!=14;i++){
			toWrite[i]="";
			for(int f=0;f!=6;f++){
				toWrite[i]+=equipmentFields[i-5][9+f].getText()+"/";
				
				//this will test to make sure every parameter is actually a number. It does accept doubles, since the game does.
				try{
					double x=Double.parseDouble(equipmentFields[i-5][f+9].getText());
				}
				catch(Exception e){
					issues+="Issue with parameter "+equipStats[f]+" for item "+equipmentFields[i-5][2].getText()+".\n";
					isValid=false;
					//System.out.println("Issue with parameter "+equipStats[f]+" for item "+equipmentFields[i-5][2].getText()+".");
				}

			}
			toWrite[i]+=equipmentFields[i-5][2].getText();
			//System.out.println(toWrite[i]);
		}
		
		//this loop builds the weapon values within an array of text, identical to how the main program reads it.
		for(int i=0;i!=2;i++){
			toWrite[14+(i*2)]="";
			for(int g=23;g!=27;g++){
				toWrite[14+(i*2)]+=weaponFields[i][g].getText()+"/";
				
				try{
					double x=Double.parseDouble(weaponFields[i][g].getText());
				}
				catch(Exception e){
					issues+="Issue with parameter "+weaponStats[g-23]+" for item "+weaponFields[i][2].getText()+".\n";
					isValid=false;
				}
			}
			
			String typesOfPain="";
			
			if(damageTypes[i][0].isSelected()){
				typesOfPain+="b";
			}
			if(damageTypes[i][1].isSelected()){
				typesOfPain+="p";
			}
			if(damageTypes[i][2].isSelected()){
				typesOfPain+="s";
			}
			if(damageTypes[i][3].isSelected()){
				typesOfPain+="t";
			}
			
			toWrite[14+(i*2)]+=typesOfPain;
			//System.out.println(toWrite[14+(i*2)]);
			
			toWrite[15+(i*2)]="";
			for(int f=9;f!=15;f++){
				toWrite[15+(i*2)]+=weaponFields[i][f].getText()+"/";
				
				try{
					double x=Double.parseDouble(weaponFields[i][f].getText());
				}
				catch(Exception e){
					issues+="Issue with parameter "+equipStats[f-9]+" for item "+weaponFields[i][2].getText()+".\n";
					isValid=false;
				}
			}
			toWrite[15+(i*2)]+=weaponFields[i][2].getText();
			//System.out.println(toWrite[15+(i*2)]);
			
		}
		
		//System.out.println(issues);
		
		//for(int i=0;i!=18;i++){
		//	System.out.println(toWrite[i]);
		//}
		
		displayErrors(issues);
		if(isValid){
			writeFile(toWrite, characterFields[6].getText());
			JOptionPane.showMessageDialog(null, "File Save Complete");
		}
		else{
			JOptionPane.showMessageDialog(null, "Please see the error log for a list of invalid parameters.");
		}
	}
}