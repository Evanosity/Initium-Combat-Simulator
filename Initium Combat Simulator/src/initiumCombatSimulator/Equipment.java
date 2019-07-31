package initiumCombatSimulator;

import javax.swing.JTextArea;

/**
 * This is the base object for all of the different equipment style objects, such as Weapon and Armor.
 * @author Evanosity
 * @date June 14 2017
 */
public class Equipment {
	private Entity equippedTo;
	private String name;
	private String equipmentSlot;
	private int dexPen;
	private int strMod;
	private int intMod;
	private double blockChance;
	private double storedBC;
	private double damageReduction;
	private double bludgeMod;
	private double pierceMod;
	private double slashMod;
	
	private int dura=-1;
	private int ogDura=-1;
	
	/**
	 * public Equipment - this is the main constructor for the class.
	 * @param base - String representation of this piece of equipment
	 * @param thisEntity - the entity that this piece of equipment will belong to
	 */
	public Equipment(String base, Entity thisEntity, String slot, JTextArea output){
		//all of the System.out.println calls in this are for debugging to the console in case things break spectacularly.
		try{
			equipmentSlot=slot;
			//System.out.println("\n\n");
			//System.out.println(base);
			equippedTo=thisEntity;
			
			//what each of these snippets does is find the substring of the first parameter, record it, remove it, and then go on to the next.
			dexPen=Integer.parseInt(base.substring(0, base.indexOf("/")));
			base=base.substring(base.indexOf("/")+1, base.length());
			//System.out.println(dexPen);
			
			blockChance=Double.parseDouble(base.substring(0, base.indexOf("/")));
			base=base.substring(base.indexOf("/")+1, base.length());
			//System.out.println(blockChance);
			
			//for the dura.
			storedBC=blockChance;
			
			damageReduction=Double.parseDouble(base.substring(0, base.indexOf("/")));
			base=base.substring(base.indexOf("/")+1, base.length());
			//System.out.println(damageReduction);
			
			bludgeMod=Double.parseDouble(base.substring(0, base.indexOf("/")));
			base=base.substring(base.indexOf("/")+1, base.length());
			//System.out.println(bludgeMod);
			
			pierceMod=Double.parseDouble(base.substring(0, base.indexOf("/")));
			base=base.substring(base.indexOf("/")+1, base.length());
			//System.out.println(pierceMod);
			
			slashMod=Double.parseDouble(base.substring(0, base.indexOf("/")));
			base=base.substring(base.indexOf("/")+1, base.length());
			//System.out.println(slashMod);
			
			if(base.indexOf("/")!=-1){
				dura=Integer.parseInt(base.substring(0, base.indexOf("/")));
				base=base.substring(base.indexOf("/")+1, base.length());
				ogDura=dura;
			}
			if(base.indexOf("/")!=-1){
				strMod=Integer.parseInt(base.substring(0, base.indexOf("/")));
				base=base.substring(base.indexOf("/")+1, base.length());
			}
			else{
				strMod=0;
			}
			if(base.indexOf("/")!=-1){
				intMod=Integer.parseInt(base.substring(0, base.indexOf("/")));
				base=base.substring(base.indexOf("/")+1, base.length());
			}
			else{
				intMod=0;
			}
			
			name=base;
			//System.out.println(name);
		}
		catch(Exception e){
			thisEntity.setValid(false);
			output.append("An issue occured when generating the equipment piece '"+slot+"' for character "+thisEntity.getName()+". Please double check your source file.\n\n");
		}
	}
	
	/**
	 * public String toString - returns a properly formatted string representation of this weapon.
	 * @return the formatted string.
	 */
	public String toString(){
		String toReturn="";
		toReturn= getDexPen()
				+"/"+getBlockChance()
				+"/"+getDamageReduction()
				+"/"+getBludgeMod()
				+"/"+getPierceMod()
				+"/"+getSlashMod();
		
		if(dura>-1){
			toReturn+=dura;
		}
		if(strMod!=0){
			toReturn+=strMod;
		}
		if(intMod!=0){
			toReturn+=intMod;
		}
		return toReturn+="/"+name;
	}
	
	/**
	 * public double findWeakSpot - calculates the damage type that will be done against a piece of equipment
	 * @param swinger - the Weapon that we will be testing against.
	 * @return the modifier that is weakest to the given weapon.
	 */
	public double findWeakSpot(Weapon swinger){
		String damageTypes=swinger.getDamageTypes();
		if(damageTypes.length()==1){
			//if the weapon only deals one type of damage, then return that resistance modifier.
			if(damageTypes.equals("p")){
				return pierceMod;
			}
			if(damageTypes.equals("b")){
				return bludgeMod;
			}
			if(damageTypes.equals("s")){
				return slashMod;
			}
		}
		else if(damageTypes.length()==3){
			//if the weapon has 3 damage types, return the smallest resistance.
			double test[]=new double[3];
			test[0]=bludgeMod;
			test[1]=pierceMod;
			test[2]=slashMod;
			double smallest=2;
			
			for(int i=0;i!=2;i++){
				if(test[i]<smallest){
					smallest=test[i];
				}
			}
			
			if(smallest==bludgeMod){
				return bludgeMod;
			}
			if(smallest==slashMod){
				return slashMod;
			}
			if(smallest==pierceMod){
				return pierceMod;
			}
		}
		else if(damageTypes.length()==2){
			//if the weapon has 2 damage types, return the smaller resistance.
			if(damageTypes.contains("b")&&damageTypes.contains("p")){
				if(bludgeMod<pierceMod){
					return bludgeMod;
				}
				else if(bludgeMod>pierceMod){
					return pierceMod;
				}
				else{
					return bludgeMod;
				}
			}
			if(damageTypes.contains("b")&&damageTypes.contains("s")){
				if(bludgeMod<slashMod){
					return bludgeMod;
				}
				else if(bludgeMod>slashMod){
					return slashMod;
				}
				else{
					return bludgeMod;
				}
			}
			if(damageTypes.contains("s")&&damageTypes.contains("p")){
				if(slashMod<pierceMod){
					return slashMod;
				}
				else if(slashMod>pierceMod){
					return pierceMod;
				}
				else{
					return slashMod;
				}
			}
		}
		return 0;
	}
	
	/**
	 * public boolean isBlock - rolls the chance for this equipment piece to block an incoming attack.
	 * @return true if blocked, false if not blocked.
	 */
	public boolean isBlock(){
		double roll=Math.random();
		if((int)(roll*100)>blockChance){
			return false;
		}
		//if it blocks, subtract one dura.
		if(dura>=0){
			dura--;
			if(dura<0){
				blockChance=0;
			}
		}
		return true;
	}
	
	/**
	 * public void setSlot - this method will change the equipment slot that this item is equipped to.
	 * @param newSlot - a string representation of the new slot, should be all lowercase.
	 */
	public void setSlot(String newSlot){
		equipmentSlot=newSlot;
	}
	
	/**
	 * public String getSlot - returns the given equipment slot
	 * @return equipmentSlot - a string representation of the item's slot.
	 */
	public String getSlot(){
		return equipmentSlot;
	}
	
	/**
	 * public void setEquippedTo - method to set which entity this equipment is attatched to.
	 * @param newGuy - entity in which this equipment is equipped to
	 */
	public void setEquippedTo(Entity newGuy){
		equippedTo=newGuy;
	}
	
	/**
	 * public Entity getEquippedTo - method for retrieving which entity this equipment is attached to
	 * @return equippedTo - the entity which this is equipped to
	 */
	public Entity getEquippedTo(){
		return equippedTo;
	}
	
	/**
	 * public void setDexPen - sets the dexterity penalty for this piece of equipment
	 * @param newDexPen - the new dex penalty
	 */
	public void setDexPen(int newDexPen){
		dexPen=newDexPen;
	}
	
	/**
	 * public int getDexPen - returns the dexterity penalty for this piece of equipment
	 * @return dexPen - the dex pen of this equipment
	 */
	public double getDexPen(){
		return dexPen;
	}
	
	/**
	 * public void setStrMod - sets the strength modifier for this piece of equipment
	 * @param newStrMod - the new strength modifier
	 */
	public void setStrMod(int newStrMod){
		strMod=newStrMod;
	}
	/**
	 * public int getStrMod - returns the strength modifier for this piece of equipment
	 * @return - the str modifier of this equipment
	 */
	public int getStrMod(){
		return strMod;
	}
	
	/**
	 * public void setIntMod - sets the intelligence modifier for this piece of equipment
	 * @param newIntMod - the new int modifier for this piece of equipment
	 */
	public void setIntMod(int newIntMod){
		intMod=newIntMod;
	}
	/**
	 * public void getIntMod - returns the intelligence modifier for this piece of equipment
	 * @return the int modifier for this thing
	 */
	public int getIntMod(){
		return intMod;
	}
	
	/**
	 * public void setBlockChance - sets the block chance for this piece of equipment
	 * @param newBC - the new block chance
	 */
	public void setBlockChance(double newBC){
		blockChance=newBC;
	}
	
	/**
	 * public double getBlockChance - returns the block chance for this piece of equipment
	 * @return  blockChance - the block chance of this equipment
	 */
	public double getBlockChance(){
		return blockChance;
	}
	
	/**
	 * public void setDamageReduction - sets the damage reduction for this piece of equipment
	 * @param newDR - the new damage reduction
	 */
	public void setDamageReduction(double newDR){
		damageReduction=newDR;
	}
	
	/**
	 * public double getDamageReduction - returns the damage reduction for this piece of equipment
	 * @return damageReduction - the damage reduction for this piece of equipment
	 */
	public double getDamageReduction(){
		return damageReduction;
	}
	
	/**
	 * public void setBludgeMod - sets the bludgeoning resistance modifier for this piece of equipment.
	 * @param newBM - the new bludgeoning resistance modifier.
	 */
	public void setBludgeMod(double newBM){
		bludgeMod=newBM;
	}
	/**
	 * public double getBludgeMod - returns the bludgeoning resistance modifier for this piece of equipment.
	 * @return bludgeMod - the bludgeoning resistance modifier.
	 */
	public double getBludgeMod(){
		return bludgeMod;
	}
	
	/**
	 * public void setPierceMod - sets the piercing resistance modifier for this piece of equipment.
	 * @param newPM - the new piercing resistance modifier.
	 */
	public void setPierceMod(double newPM){
		pierceMod=newPM;
	}
	/**
	 * public double getPierceMod - returns the piercing resistance modifier for this piece of equipment.
	 * @return pierceMod - the piercing resistance modifier.
	 */
	public double getPierceMod(){
		return pierceMod;
	}
	
	/**
	 * public void setSlashMod - sets the slashing resistance modifier for this piece of equipment.
	 * @param newSM - the new slashing resistance modifier.
	 */
	public void setSlashMod(double newSM){
		slashMod=newSM;
	}
	/**
	 * public double getSlashMod - returns the slashing resistance modifier for this piece of equipment.
	 * @return slashMod - the slashing resistance modifier.
	 */
	public double getSlashMod(){
		return slashMod;
	}
	
	/**
	 * public int getDura - returns the max durability of the item.
	 * @return ogDura - the original maximum durability.
	 */
	public int getDura(){
		return ogDura;
	}
	
	/**
	 * public void resetDura - resets the durability of the item to its original maximum.
	 */
	public void resetDura(){
		dura=ogDura;
		blockChance=storedBC;
	}
	
	/**
	 * public void setName - changes the name for this piece of equipment - **WARNING** - may break some stuff. NOT recommended unless you are using upon initialization.
	 * @param newName - the new name.
	 */
	public void setName(String newName){
		name=newName;
	}
	
	/**
	 * public String getName - returns the name of the equipment.
	 * @return name - self explanatory.
	 */
	public String getName(){
		return name;
	}
}