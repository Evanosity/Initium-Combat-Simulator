package initiumCombatSimulator;
/**
 * IO Class - this class will read a file and turn it into a string array.
 * @author Evanosity
 * @date June 12/2017
 */
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JTextArea;

public class IO {
	private static File file;
	private static BufferedReader reader;
	private static BufferedWriter writer;
	private JTextArea writeTo;
	
	/**
	 * public IO - this is the constructor for the IO class. Normally, this would not be necessary, however, I needed to send a JTextArea
	 * to this class in order for the error messages to be displayed properly.
	 * @param output
	 */
	public IO(JTextArea output){
		writeTo=output;
	}
	
	/**
	 * Method readFile - this method will turn a text file into a String array.
	 * @return toFill - the array representation of the text file.
	 */
	public String[] readFile(String fileName, String secondPath){
		String[] toFill = null;
		try{
			String sep=System.getProperty("file.separator"); //this ensures that this program works on many different Operating Systems, because it sounds stupid but different operating systems have different file seperators which breaks File IO stuff.
			file=findLocation(String.format("%sresources%s"+secondPath+"%s"+fileName, sep, sep, sep));
			
			reader=new BufferedReader(new FileReader(file));
			
			//this will read the number of lines in the file in preparation to load it into an array
			int lines=1;
			reader.mark(1000000);
			while(reader.readLine()!=null){
				lines++;
			}
			
			toFill=new String[lines];
			reader.reset();
			for(int i=0;i!=lines-1;i++){
				toFill[i]=reader.readLine();
			}
		}
		catch(FileNotFoundException e){
			writeTo.append("The Specified File, "+fileName+", could not be found.\n\n");
		}
		catch(IOException e){
			writeTo.append("An unexpected error occured.");
		}
		finally{
			try{
				reader.close();
			}
			catch (IOException e) {
				writeTo.setText("An unexpected error occured.");
			}
			catch(Exception e){
				
			}
		}
		return toFill;
	}
	
	/**
	 * Method writeFile - this method rewrites the database with the users changes.
	 * @param toWrite - 2D string array that stores everything
	 */
	public void writeFile(String[] toWrite, String fileName){
		try{
			//this finds/creates the appropriate file in preparation to fill it
			String sep=System.getProperty("file.separator");
			file=findLocation(String.format("%sresources%sentities%s"+fileName, sep, sep, sep));
			if(file.createNewFile()){
				System.out.println("Filed created succesfully");
			}
			
			//this saves the array to the file, with each increment being a new line.
			writer=new BufferedWriter(new FileWriter(file));
			for(int i=0;i!=toWrite.length;i++){
				writer.write(toWrite[i]+"\n");
			}
		}
		catch(IOException e){
			writeTo.setText("An unexpected error occured.");
		}
		finally{
			try{
				writer.flush();
				writer.close();
				System.out.println("Write file complete.");
			}
			catch(IOException e){
				writeTo.setText("An unexpected error occured.");
			}
		}
	}
	
	/**
	 * method findLocation - this method finds the location of the program being run, and then appends the needed destination onto the end, thus loading the appropriate file into memory.
	 * @param file - file to manipulate and return.
	 * @param find - sublocation of the file.
	 * @return file - this will return the file with the correct path.
	 */
	public static File findLocation(String find){
		File file=new File("");
		file=new File(file.getAbsolutePath()+find); 
		return file;
	}
	
	/**
	 * method findLocation - finds the location of the program being run.
	 * @return the path in the form of a string
	 */
	public static String findLocation(){
		File dummy=new File("");
		return dummy.getAbsolutePath();
	}
	
	/**
	 * method validateFile - will evaluate a file's existence based on its path.
	 * @param path - the path of the file in string form
	 * @return true or false
	 */
	public static boolean validateFile(String path){
		File dummy=new File(path);
		if(dummy.exists()){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * method createPath - this method will generate a proper file path given a string of parameters.
	 * @param input - the given parameters
	 * @param fullPath - true will generate the location of the running program, then add the parameters on top. False will just do the parameters.
	 * @return the completed path
	 */
	public static String createPath(String[]input, boolean fullPath){
		String before="";
		String sep=System.getProperty("file.separator");
		for(int i=0;i!=input.length;i++){
			before+=sep+input[i];
		}
		if(fullPath){
			return IO.findLocation()+before;
		}
		return before;
	}
}