package initiumCombatSimulator;

import javax.swing.JTextArea;

/**
 * Current has no function.
 * @author Evanosity
 * @date June 14 2017
 */
public class Armor extends Equipment{	
	/**
	 * public Armor - basic constructor for the Armor object.
	 * @param base - String representation of this armor piece.
	 * @param thisEntity - the entity that this armor is equipped to.
	 */
	public Armor(String base, Entity thisEntity, String slot, JTextArea output){
		super(base, thisEntity, slot, output);
		
	}
}
