package initiumCombatSimulator;

import webTools.getCharacter;
import webTools.getItem;
import webTools.iDrive;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebParser {
	public static String[] getCharacter(String username, String password, String ID) {
		iDrive drive=new iDrive("default", username, password);
		
		String[]load=getCharacter.getChar(ID, drive);
		
		for(int i=0;i!=load.length-5;i++) {
			load[i+5]=getItem.getItemInfo(load[i+5], drive);
			//System.out.println(load[i]);
		}
		//for(int k=0;k!=load.length;k++) {
		//	System.out.println(load[k]);
		//}
		drive.stop();
		return load;
	}
}
