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
	private static JTextField[][] Fields;
	private static JTextField outputError;
	private static JButton saveButton;
	private static JOptionPane confirmDialog;
	
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
	
	public void createEditor(){
		
	}
	public void createEditor(String[] toLoad){
		
	}
	public void createEditorGUI(){
		Editor=new JFrame("Entity Editor - "+fileName);
		Editor.getContentPane();
		Editor.setLayout(null);
		Editor.setDefaultCloseOperation(3);
		Editor.setResizable(false);
		Editor.setPreferredSize(new Dimension(400,550));
		Editor.pack();
	}
	
}