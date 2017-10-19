package initiumCombatSimulator;

import javax.swing.JTextArea;

/**
 * EntityEditor class - this class provides a GUI for a user-friendly way to edit and create entities.
 * @author Evanosity
 *
 */
public class EntityEditor extends IO{
	public EntityEditor(JTextArea output) {
		super(output);
	}
	
	public EntityEditor(JTextArea output, String fileName){
		super(output);
	}
}
