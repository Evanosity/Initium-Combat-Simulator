package initiumCombatSimulator;

import javax.swing.JTextArea;

/**
 * public class Entity - this is the main object class for this program. It holds all the information of the characters, or "Entities" as
 * I refer to them as there is a distinct possibility the user will be fighting the computer.
 * @author Evanosity
 * @date June 14 2017
 */
public class Entity extends IO{
	private String name;
	private String fileName;
	private double[] str;
	private double[] dex;
	private double[] inte;
	private int[] hp;
	private Armor[] armor;
	private Weapon lefthand;
	private Weapon righthand;
	private Buff[] buffs;
	private boolean valid;
	//private JTextArea writeTo;
	
	/**
	 * public Entity - basic constructor for Entities.
	 * @param fileLocation - String of the location of the file for the info to be pulled from. Generally is the entities' name but could be anything
	 * @param output - JTextArea to print out the results of the combat simulation.
	 */
	public Entity(String fileLocation, JTextArea output){
		super(output);
		String[] base=readFile(fileLocation, "entities");
		try{
			fileName=fileLocation.substring(fileLocation.indexOf("/"), fileLocation.length());
		}
		catch(Exception e){
			fileName=fileLocation;
		}
		createEntity(base, output);
	}
	
	/**
	 * public Entity - another constructor for Entities. This constructor accepts a string array.
	 * @param loadFrom - String array that will contain all of the information required to initialize the entity.
	 * @param output - JTextArea to print out the results of the combat simulation.
	 */
	public Entity(String[] loadFrom, JTextArea output){
		super(output);
		createEntity(loadFrom, output);
	}
	
	/**
	 * public void writeToFile - this turns all information attached to this object, including equipment, into a string array and sends
	 * it to the IO class to be put into a file.
	 * @param fileName - the name of the file to write to. Generally, it should be the name of the entity to avoid confusion but it could be anything.
	 */
	public void writeToFile(String fileName){
		String[]toWrite=new String[18+buffs.length];
		toWrite[0]=name;
		toWrite[1]=Double.toString(str[0]);
		toWrite[2]=Double.toString(dex[0]);
		toWrite[3]=Double.toString(inte[0]);
		toWrite[4]=Integer.toString(hp[0]);
		for(int i=5;i!=14;i++){
			toWrite[i]=armor[i-5].toString();
		}
		toWrite[14]=lefthand.weaponToString();
		toWrite[15]=lefthand.toString();
		toWrite[16]=righthand.weaponToString();
		toWrite[17]=righthand.toString();
		
		if(toWrite.length!=18) {
			for(int i=0;i!=buffs.length;i++) {
				toWrite[17+1]=buffs[i].getName();
			}
		}
		
		writeFile(toWrite, fileName);
	}
	
	/**
	 * public void createEntity - this is the method that both of the constructors call. I could have put this inside both of them but
	 * this was a better option.
	 * @param base - A string array representation of the Entity we are going to create.
	 */
	private void createEntity(String[] base, JTextArea outputZone){
		valid=true;
		try{
			str=new double[2];
			dex=new double[2];
			inte=new double[2];
			hp=new int[2];
			armor=new Armor[9];
			
			name=base[0];
			str[0]=Double.parseDouble(base[1]);
			str[1]=Double.parseDouble(base[1]);
			dex[0]=Double.parseDouble(base[2]);
			dex[1]=Double.parseDouble(base[2]);
			inte[0]=Double.parseDouble(base[3]);
			inte[1]=Double.parseDouble(base[3]);
			hp[0]=Integer.parseInt(base[4]);
			hp[1]=Integer.parseInt(base[4]);
			armor[0]=new Armor(base[5],this, "head", outputZone);
			armor[1]=new Armor(base[6], this, "chest", outputZone);
			armor[2]=new Armor(base[7],this, "shirt", outputZone);
			armor[3]=new Armor(base[8],this, "gloves", outputZone);
			armor[4]=new Armor(base[9],this, "legs", outputZone);
			armor[5]=new Armor(base[10],this, "boots", outputZone);
			armor[6]=new Armor(base[11],this, "leftring", outputZone);
			armor[7]=new Armor(base[12],this, "rightring", outputZone);
			armor[8]=new Armor(base[13],this, "neck", outputZone);
			lefthand=new Weapon(base[14],base[15],this, "lefthand", outputZone);
			righthand=new Weapon(base[16],base[17],this, "righthand", outputZone);		
		}
		catch(Exception e){
			outputZone.append("There was an error when generating the stats for entity "+getName()+". Please double check your file.\n\n");
			valid=false;
		}
		applyDexPenalty();
		applyStrMod();
		applyIntMod();
		
		buffs=new Buff[base.length-18];
		
		for(int i=18;i!=base.length-1;i++) {
			loadBuff(base[i],outputZone,i-18);
		}
	}
	
	/**
	 * public Armor getArmor - this method returns an armor object based on what "slot" it is in.
	 * @param slot - String representation of the slot we are looking for.
	 * @return the equipment object corresponding to the given slot.
	 */
	public Armor getArmor(String slot){
		for(int i=0;i!=armor.length;i++){
			if(armor[i].getSlot().equals(slot)){
				return armor[i];
			}
		}
		return null;
	}
	
	/**
	 * public Weapon getWeapon - returns a specified weapon.
	 * @param slot - string representation of the slot of which weapon we are returning.
	 * @return the specified weapon.
	 */
	public Weapon getWeapon(String slot){
		if(slot.equals("lefthand")){
			return lefthand;
		}
		if(slot.equals("righthand")){
			return righthand;
		}
		return null;
	}
	
	/**
	 * public void applyDexPenalty - Calculates the dexterity of the Entity after applying the dexterity penalty of all equipment.
	 */
	public void applyDexPenalty(){
		int toApply=0;
		for(int i=0;i!=armor.length;i++){
			toApply=1-(armor[i].getDexPen()/100);
			dex[1]=dex[1]*toApply;
		}
		dex[1]=dex[1]*(1-lefthand.getDexPen()/100);
		dex[1]=dex[1]*(1-righthand.getDexPen()/100);
	}
	
	/**
	 * public void applyStrMod - Calculates the strength of the Entity after applying the strength modifier of all equipment.
	 */
	public void applyStrMod(){
		int toApply=0;
		for(int i=0;i!=armor.length;i++){
			toApply=1-(armor[i].getStrMod()/100);
			str[1]=str[1]*toApply;
		}
		str[1]=str[1]*(1-lefthand.getStrMod()/100);
		str[1]=str[1]*(1-righthand.getStrMod()/100);
	}
	
	/**
	 * public void applyStrMod - Calculates the strength of the Entity after applying the strength modifier of all equipment.
	 */
	public void applyIntMod(){
		int toApply=0;
		for(int i=0;i!=armor.length;i++){
			toApply=1-(armor[i].getIntMod()/100);
			inte[1]=inte[1]*toApply;
		}
		inte[1]=inte[1]*(1-lefthand.getIntMod()/100);
		inte[1]=inte[1]*(1-righthand.getIntMod()/100);
	}
	
	/**
	 * public double rollDex - calculates a random number between 0 and the entities dexterity, then returns it.
	 * @return the dexterity roll.
	 */
	public double rollDex(){
		double rolled=0;
		rolled=Math.random()*dex[1];
		//System.out.println("Dex Roll: "+rolled);
		return rolled;
	}
	
	/**
	 * public Armor hitWhere - this method will roll a random chance to see which armor slot will be blocking an attack.
	 * @return the appropriate armor piece.
	 */
	public Armor hitWhere(){
		double roll=Math.random();
		int testAgainst=(int)(roll*100);
		if(testAgainst<=50){
			return getArmor("chest");
		}
		if(testAgainst>50 && testAgainst<=80){
			return getArmor("legs");
		}
		if(testAgainst>80 && testAgainst<=90){
			return getArmor("head");
		}
		if(testAgainst>90 && testAgainst<=95){
			return getArmor("boots");
		}
		if(testAgainst>95 && testAgainst<100){
			return getArmor("gloves");
		}
		return null;
	}
	
	/**
	 * Fuck
	 * @param buff
	 */
	public void loadBuff(String buff, JTextArea output, int place){
		System.out.println(str[1]);
		System.out.println(dex[1]);
		System.out.println(inte[1]);
		System.out.println();
		

		buffs[place]=new Buff(buff,output);
		for(int i=0;i!=buffs[place].getSize()-2;i++){
			String[]temp=buffs[place].getInfo(i);
			
			System.out.println(temp[0]);
			System.out.println(temp[1]);
			System.out.println(temp[2]);
			System.out.println();


			if(temp[1].equals("+")){
				applyBuffFixed(temp[0],Integer.parseInt(temp[2]));
			}
			else if(temp[1].equals("%")){
				applyBuffPercent(temp[0],Integer.parseInt(temp[2]));
			}
		}
		System.out.println(str[1]);
		System.out.println(dex[1]);
		System.out.println(inte[1]);
		System.out.println();

	}
	
	/**
	 * public void applyBuffFixed - adds a full point value buff to the entity.
	 * @param type - the stat to increase with this buff. 0 is strength, 1 is dex, 2 is int.
	 * @param increase - the number of points you want to increase the specified stat by EG: 2 will increase the specified stat by 2.
	 */
	public void applyBuffFixed(String type, int increase){
		if(type.toLowerCase().equals("str")){
			str[1]+=increase;
		}
		else if(type.toLowerCase().equals("dex")){
			dex[1]+=increase;
		}
		else if(type.toLowerCase().equals("int")){
			inte[1]+=increase;
		}
	}
	
	/**
	 * public void applyBuffPercent - adds a buff percent to a specified stat
	 * @param type - the stat to increase with this buff. 0 is strength, 1 is dex, 2 is int.
	 * @param increase - the percent you want to increase this stage by. EG: 0.13 will increase the specified stat by 13%.
	 */
	public void applyBuffPercent(String type, double increase){
		if(type.toLowerCase().equals("str")){
			str[1]=str[1]*(1+(increase/100));
		}
		else if(type.toLowerCase().equals("dex")){
			dex[1]=dex[1]*(1+(increase/100));
		}
		else if(type.toLowerCase().equals("int")){
			inte[1]=inte[1]*(1+(increase/100));
		}
	}
	
	/**
	 * public void resetAll - reverts all stats to their original values, then applies the dexterity penalty of all equipment. Effectively,
	 * this removes all buffs. Also, it fully heals a character.
	 */
	public void resetAll(){
		str[1]=str[0];
		dex[1]=dex[0];
		inte[1]=inte[0];
		hp[1]=hp[0];
		applyDexPenalty();
		
		//reset the dura for all armor pieces
		for(int i=0;i!=armor.length;i++){
			armor[i].resetDura();
		}
	}
	
	/**
	 * public boolean isDead - returns true if the character is at 0 or less hp, and returns false if they are above 0 hp.
	 * @return returns true if the character is under 1 hp, true if they are above 0.
	 */
	public boolean isDead(){
		if(hp[1]<1){
			return true;
		}
		return false;
	}
	
	/**
	 * public void resetSelect - this method reverts a given stat to it's original value.
	 * @param strR - true if you want to reset this stat, otherwise false.
	 * @param dexR - true if you want to reset this stat, otherwise false.
	 * @param inteR - true if you want to reset this stat, otherwise false.
	 * @param hpR - true if you want to reset this stat, otherwise false.
	 */
	public void resetSelect(boolean strR, boolean dexR, boolean inteR, boolean hpR){
		if(strR){
			str[1]=str[0];
		}
		if(dexR){
			dex[1]=dex[0];
		}
		if(inteR){
			inte[1]=inte[0];
		}
		if(hpR){
			hp[1]=hp[0];
		}
	}
	
	/**
	 * public double[] getStr - returns a double array representation of this character's strength.
	 * @return - double array representation of this character's strength. [0] is the base strength, [1] is after modifiers.
	 */
	public double[] getStr(){
		return str;
	}
	/**
	 * public void setStr - sets the base strength for this character.
	 * @param str - the new strength value.
	 */
	public void setStr(double str){
		this.str[0]=str;
		resetSelect(true,false,false,false);
	}
	
	/**
	 * public double[] getDex - returns a double array representation of this character's dexterity.
	 * @return - double array representation of this character's dexterity. [0] is the base dexterity, [1] is after modifiers.
	 */
	public double[] getDex(){
		return dex;
	}
	/**
	 * public void setDex - sets the base dexterity for this character.
	 * @param dex - the new dexterity value.
	 */
	public void setDex(double dex){
		this.dex[0]=dex;
		resetSelect(false,true,false,false);
	}
	
	/**
	 * public double[] getInte - returns a double array representation of this character's intelligence.
	 * @return - double array representation of this character's intelligence. [0] is the base intelligence, [1] is after modifiers.
	 */
	public double[] getInte(){		
		return inte;
	}
	/**
	 * public void setInte - sets the base intelligence for this character.
	 * @param inte - the new intelligence value.
	 */
	public void setInte(double inte){
		this.inte[0]=inte;
		resetSelect(false,false,true,false);
	}
	
	/**
	 * public int getCurrentHP - returns the current HP value of the character.
	 * @return the current HP value of the character.
	 */
	public int getCurrentHP(){
		return hp[1];
	}
	/**
	 * public int getMaxHP - returns the max HP value for this character.
	 * @return the max hp value for this character.
	 */
	public int getMaxHP(){
		return hp[0];
	}
	/**
	 * public void setCurrentHP - sets the current HP to a specified value.
	 * @param hpC - the new HP.
	 */
	public void setCurrentHP(int hpC){
		hp[1]=hpC;
	}
	/**
	 * public void setMaxHP - sets the maximum HP value of this character to a specified value.
	 * @param hpM - new max HP value.
	 */
	public void setMaxHP(int hpM){
		hp[0]=hpM;
		resetSelect(false,false,false,true);
	}
	
	/**
	 * public void takeDamage - this method reduces the current hp of the character by a specified amount.
	 * @param damage - the amount to subtract from the total hp.
	 */
	public void takeDamage(int damage){
		hp[1]=hp[1]-damage;
	}
	
	/**
	 * public String getName - returns the name of the entity.
	 * @return the name of the entity.
	 */
	public String getName(){
		return name;
	}
	/**
	 * public void setName - sets the name of the entity.
	 * @param newName - the new name of the entity
	 */
	public void setName(String newName){
		name=newName;
	}
	/**
	 * public String getFileName - returns the name of the file where the entity is stored.
	 * @return the file name.
	 */
	public String getFileName(){
		return fileName;
	}
	
	/**
	 * public boolean getValid
	 * @return true if the entity compiled properly, false otherwise.
	 */
	public boolean getValid(){
		return valid;
	}
	/**
	 * public void setValid
	 * @param validity - the new value of validity
	 */
	public void setValid(boolean validity){
		valid=validity;
	}
}