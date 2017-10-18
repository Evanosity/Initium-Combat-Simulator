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
	private static FileReader fileReader;
	private static BufferedWriter writer;
	private static FileWriter fileWriter;
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
	public String[] readFile(String fileName){
		String[] toFill = null;
		try{
			String sep=System.getProperty("file.separator"); //this ensures that this program works on many different Operating Systems, because it sounds stupid but different operating systems have different file seperators which breaks File IO stuff.
			file=findLocation(file,String.format("%sresources%sentities%s"+fileName, sep, sep, sep));
			fileReader=new FileReader(file);
			reader=new BufferedReader(fileReader);
			int lines=1;
			reader.mark(1000000);
			while(reader.readLine()!=null){
				lines++; //this counts the number of lines in the file. Since the user *in theory* never tampers with the file it should be all good.
			}
			toFill=new String[lines];
			reader.reset();
			for(int i=0;i!=lines-1;i++){
				toFill[i]=reader.readLine();
			}
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
			writeTo.setText("The Specified File "+fileName+" could not be found.");
		}
		catch(IOException e){
			writeTo.setText("An unexpected error occured.");
		}
		catch(Exception e){
		}
		finally{
			try {
				reader.close();
				System.out.println("Read file complete.");
			} catch (IOException e) {
				writeTo.setText("An unexpected error occured.");
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
			String sep=System.getProperty("file.separator");
			file=findLocation(file,String.format("%sresources%sentities%s"+fileName, sep, sep, sep));
			fileWriter=new FileWriter(file);
			writer=new BufferedWriter(fileWriter);
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
	public static File findLocation(File file,String find){
		file=new File("");
		file=new File(file.getAbsolutePath()+find); 
		return file;
	}
}