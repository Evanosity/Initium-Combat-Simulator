package initiumCombatSimulator;

import webTools.getCharacter;
import webTools.getItem;
import webTools.iDrive;

public class WebParser {
	public static String[] getCharacter(String username, String password, String ID) {
		iDrive drive=new iDrive("default", username, password);
		
		String[]load=getCharacter.getChar(ID, drive);
		
		for(int i=0;i!=load.length-5;i++) {	
			load[i+5]=getItem.getItemInfo(load[i+5], drive);
			
			//if this item is a weapon, a linebreak will separate the offensive and defensive stats.
			if(load[i+5].contains("\n")) {
				
				String[]temp=new String[2];
				temp[0]=load[i+5].split("\n")[0];
				temp[1]=load[i+5].split("\n")[1];
				
				load=insertElement(load,temp[1],i+6);
				load[i+5]=temp[0];
				
				i++;
			}
		}
		
		load=truncateArray(load,18);
		
		//this part reorders the array so its in line with the equip order of a file.
		String[]test=new String[load.length];
		int k;
		int j;
		int h;
		for(k=0;k!=11;k++) {
			test[k]=load[k];
		}
		
		for(j=14;j!=18;j++) {
			test[j]=load[j-3];
		}
		
		for(h=11;h!=14;h++) {
			test[h]=load[h+4];
		}
		
		drive.stop();
		return load;
	}
	
	/**
	 * This method adds an element into a string array at a designated position
	 * @param base - The string array to extend.
	 * @param newElement - The string to be added.
	 * @param pos - The position within the array that newElement will be at.
	 * @return the modified string array
	 */
	public static String[] insertElement(String[]base, String newElement, int pos) {
		String[]newt=new String[base.length+1];
		
		for(int i=0;i!=newt.length;i++) {
			if(i<pos-1) {
				newt[i]=base[i];
			}
			else if(i==pos) {
				newt[i]=newElement;
			}
			else {
				newt[i]=base[i-1];
			}
		}
		
		return newt;
	}
	
	public static String[] truncateArray(String[]base, int length) {
		String[]newArray=new String[length];
		for(int i=0;i!=length;i++) {
			newArray[i]=base[i];
		}
		return newArray;
	}
}
