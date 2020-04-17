/*
 * Author: Wryan Parr
 * Team Members: Quinton Tiller and Nate Hiblar
 * Revision 1
 * Revision Author: Wryan Parr
 * Description: Program generates a txt file containing the layouts of one or more minesweeper fields by taking in a series of field size and mine population percentages and generating fields from that criteria.
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class BuildTestFileWP {

	//Main method that executes the program and it's functions.
	public static void main(String[] args) 
	{
		if(args.length < 3) 
		{
			System.out.println("Usage: java BuildTestFile filename nxm p nxm p nxm p ... 0x0, where filename is the name of the output file you want, nxm is the rows by columns and p is a rough percentage of mines desired, 0 - 100. End input with 0x0");
		}
		GenerateMineField(args);
		System.out.println("Done Generating");
	}
	
	//Generates a single row of a mineminefield given the number of elements in that row and the percentage of bombs desired for that field.
	//Parameters:
	//int column - represents the number of elements per row
	//int percent - represents the desired percentage of bombs to clear spaces.
	//Returns:
	//String - the current minefield layer that was made
	public static String GenerateFieldLayer(int column, int percent)
	{
		char[] layer = new char[column];
		Random rand = new Random();
		int random = 0;
		for(int i = 0; i < column; i++) 
		{
			random = rand.nextInt(100 - 1 + 1) + 1;
			if(random < percent)
			{
				layer[i] = '*';
			}else 
			{
				layer[i] = '.';
			}
		}//End for loop i
		return new String(layer);
	}
	
	//Generates the minefield for a given field specification
	//Parameters:
	//args - the system args passed in via the cmd line arguments
	//Catches:
	//IOException
	public static void GenerateMineField(String[] args) 
	{
		try 
		{
			FileWriter write = new FileWriter(args[0]);
			int i = 1;
			while(i < args.length && !(args[i].contains("0x0"))) 
			{
				write.write(args[i].replaceAll("x", " ") + "\n");
				String[] temp = args[i].split("x");
				int row = Integer.parseInt(temp[0]);
				int column = Integer.parseInt(temp[1]);
				
				for(int j = 0; j < row; j++) 
				{
					write.write(GenerateFieldLayer(column, Integer.parseInt(args[i+1])) + "\n");
				}//End for loop j
				i += 2;
			}//End while loop
			write.write("0 0\n");
			write.close();
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

}
