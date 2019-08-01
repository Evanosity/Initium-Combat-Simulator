package initiumCombatSimulator;

import javax.swing.JTextArea;

/**
 * Weapon Class - this is the object for weapons, that entities will use to beat each other up with.
 * @author Evanosity
 * @date June 13 2017
 */
public class Weapon extends Equipment{
	private boolean piercing;
	private boolean bludgeoning;
	private boolean slashing;
	private boolean isTwoHanded;
	private int numberOfDice;
	private int diceSides;
	private double critChance;
	private double critMultiplier;
	
	/**
	 * public Weapon - this is the basic constructor for Weapon objects.
	 * @param offenses - this is the string that contains the offensive stats of the weapon.
	 * @param defenses - this is the string that contains the defensive stats of the weapon.
	 * @param thisEntity - this is the entity that this Weapon will be equipped to.
	 * @param slot - this is the slot under in which this item is equipped to.
	 */
	public Weapon(String offenses, String defenses, Entity thisEntity, String slot, JTextArea output){
		super(defenses, thisEntity, slot, output);
		
		//this determines the stats of the weapon
		numberOfDice=Integer.parseInt(offenses.substring(0, offenses.indexOf("/")));
		offenses=offenses.substring(offenses.indexOf("/")+1, offenses.length());
		
		diceSides=Integer.parseInt(offenses.substring(0, offenses.indexOf("/")));
		offenses=offenses.substring(offenses.indexOf("/")+1, offenses.length());
		
		critMultiplier=Double.parseDouble(offenses.substring(0, offenses.indexOf("/")));
		offenses=offenses.substring(offenses.indexOf("/")+1, offenses.length());
		
		critChance=Double.parseDouble(offenses.substring(0, offenses.indexOf("/")));
		offenses=offenses.substring(offenses.indexOf("/")+1, offenses.length());
		
		//this determines the damage type of the weapon
		String damageTypes=offenses.substring(0, offenses.length()).toLowerCase();
		if(damageTypes.contains("p")){
			piercing=true;
		}
		if(damageTypes.contains("b")){
			bludgeoning=true;
		}
		if(damageTypes.contains("s")){
			slashing=true;
		}
		if(damageTypes.contains("t")){
			isTwoHanded=true;
		}
	}
	
	/**
	 * public String weaponToString - this method returns a string representation of all of this weapon's offensive stats. If you want the defensive stats, call weaponName.toString();
	 * @return a string representation of this weapon's offensive stats.
	 */
	public String weaponToString(){
		return numberOfDice+"/"+diceSides+"/"+critMultiplier+"/"+critChance+"/"+getDamageTypes();
	}
	
	/**
	 * public String getDamageTypes - this method returns a string with up to 3 characters, with each one representing a different damage type.
	 * @return toReturn - will contain P if the weapon deals piercing damage, B is the weapon deals bludgeoning damage and S if the weapon contains slashing damage.
	 */
	public String getDamageTypes(){
		String toReturn="";
		if(piercing){
			toReturn+="p";
		}
		if(bludgeoning){
			toReturn+="b";
		}
		if(slashing){
			toReturn+="s";
		}
		return toReturn;
	}
	
	/**
	 * public boolean getIsTwoHanded - returns wether or not the weapon is a two handed weapon. It is not possible to change this variable, as it would cause unnecesary complications.
	 * @return isTwoHanded - true if the weapon is two handed, false if it is not.
	 */
	public boolean getIsTwoHanded(){
		return isTwoHanded;
	}
	
	/**
	 * public void setNumberOfDice - sets the number of dice to be rolled when calculating damage.
	 * @param newNOD - the new number of dice.
	 */
	public void setNumberOfDice(int newNOD){
		numberOfDice=newNOD;
	}
	/**
	 * public void getNumberOfDice - returns the number of dice to be rolled when calculating damage.
	 * @return numberOfDice - self explanatory.
	 */
	public int getNumberOfDice(){
		return numberOfDice;
	}
	
	/**
	 * public void setDiceSides - sets the number of sides on each dice, for the damage roll.
	 * @param newDS - the new number of sides.
	 */
	public void setDiceSides(int newDS){
		diceSides=newDS;
	}
	/**
	 * public void getDiceSides - returns the number of sides on each dice, for the damage roll.
	 * @return diceSides - the number of sides on each dice.
	 */
	public int getDiceSides(){
		return diceSides;
	}

	/**
	 * public void setCritChance - sets the critical hit chance for this weapon.
	 * @param newCC - the new critical hit chance.
	 */
	public void setCritChance(double newCC){
		critChance=newCC;
	}
	/**
	 * public double getCritChance - returns the critical hit chance for this weapon BEFORE applying intelligence bonus.
	 * @return critChance - the critical hit chance for this weapon.
	 */
	public double getCritChance(){
		return critChance;
	}
	/**
	 * public double getCritChanceAfterInt - returns the critical hit chance of this weapon AFTER applying intelligence bonus.
	 * @return
	 */
	public double getCritChanceAfterInt(){
		double ccai=critChance;
		double temp=2.5*(getEquippedTo().getInte()[1]-4); 
		ccai+=temp;
		return ccai;
	}
	
	/**
	 * public void setCritMultiplier - sets the critical hit multiplier for this weapon.
	 * @param newCM - the new critical hit multiplier.
	 */
	public void setCritMultiplier(double newCM){
		critMultiplier=newCM;
	}
	/**
	 * public double getCritMultiplier - returns the critical hit multiplier for this weapon.
	 * @return critMultiplier - the critical hit multiplier for this weapon.
	 */
	public double getCritMultiplier(){
		return critMultiplier;
	}
	
	/**
	 * public int rollDamage - rolls the damage dealt. Includes critical hits, with intelligence bonus, and strength bonus factored in.
	 * @return the damage to be dealt.
	 */
	public int rollDamage(){
		if(numberOfDice!=0) {
		int damage=0;
		for(int i=0;i!=numberOfDice;i++){
			int temp=(int)(Math.random()*diceSides)+1;
			damage+=temp;
		}
		
		double critX=Math.random();
		if((int)(critX*100) <= (int)getCritChanceAfterInt()){
			//System.out.println("CRIT!");
			damage=(int) (damage*critMultiplier);
		}
		
		int strengthDamage=(int)(Math.random()*(getEquippedTo().getStr()[1]-1));
		if(isTwoHanded){
			damage+=(strengthDamage*3);
		}
		else{
			damage+=(strengthDamage*2);
		}
		return damage;
		} else {
			return 0;
		}
	}
	
	/**
	 * public boolean dualWield - calculates the chance of the player hitting with this weapon in addition to the primary weapon. 
	 * @return true if it rolls above the crit chance, false otherwise
	 */
	public boolean dualWield(){
		double test=Math.random();
		//System.out.println("Dual Wield Roll: "+test*100+" compared to "+getCritChanceAfterInt());
		if((int)(test*100)<=getCritChanceAfterInt()){
			return true;
		}
		return false;
	}
}
