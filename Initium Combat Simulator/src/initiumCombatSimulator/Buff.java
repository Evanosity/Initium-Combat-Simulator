package initiumCombatSimulator;

import javax.swing.JTextArea;

public class Buff extends IO{
	private String name;
	private String[][]bonuses;
	
	public Buff(String fileName, JTextArea output){
		super(output);
		createBuff(readFile(fileName, "buffs"));
		
	}
	private void createBuff(String[]base){
		bonuses=new String[base.length][3];
		for(int i=1;i!=base.length;i++){
			bonuses[i][0]=base[i].substring(0, base[i].indexOf("/"));
			base[i]=base[i].substring(base[i].indexOf("/")+1, base[i].length());
			System.out.println(bonuses[i][0]);
		}
	}

}
