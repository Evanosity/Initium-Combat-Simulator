package initiumCombatSimulator;

import javax.swing.JTextArea;

/**
 * I dont really know, but I know that it works.
 * This class needs to be rethough. Really, the entire buff framework needs to be torn out and redone. Soon....
 * @author Evan
 * @date September 2/2019
 */
public class Buff extends IO{
	private String name;
	private String[][]bonuses;
	
	public Buff(String fileName, JTextArea output){
		super(output);
		createBuff(readFile(fileName, "buffs")); //read the designated file and load it.
		
	}
	
	/**
	 * Loads the buff from the given text file.
	 * @param base
	 */
	private void createBuff(String[]base){
		bonuses=new String[base.length][3];
		for(int i=1;i!=base.length-1;i++){
			
			bonuses[i][0]=base[i].substring(0, base[i].indexOf("/")); //load what the stat is
			
			base[i]=base[i].substring(base[i].indexOf("/")+1, base[i].length()); //modify the string
			
			bonuses[i][1]=base[i].substring(0, base[i].indexOf("/")); //load the type of buff. Percent or flat
			
			base[i]=base[i].substring(base[i].indexOf("/")+1, base[i].length()); //modify the string
			
			bonuses[i][2]=base[i]; //load the buff value
		}
	}
	
	/**
	 * public int getSize - returns the amount of different statistical buffs that are inside the buff.
	 * For example, witch's hat has one. Stew and pumped have 3.
	 * @return
	 */
	public int getSize() {
		return bonuses.length;
	}
	
	/**
	 * Retrieve information about the buff.
	 * @param x
	 * @return
	 */
	public String[]getInfo(int x){
		return bonuses[x+1];
	}
	
	public String getName() {
		return name;
	}
}