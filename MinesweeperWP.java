/*
 * Author: Wryan Parr
 * Team Members: Quinton Tiller and Nate Hiblar
 * Revision 1
 * Revision Author: Wryan Parr
 * Description: Program takes in a txt file containing a number of minefield layouts that use '*' and '.' to denote mines or clear spaces and then reprints each field to show the number of mines next to each non-mine field square.
 */

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class MinesweeperWP {

	//Main method that executes the program and it's contained functions.
	public static void main(String[] args) 
	{
		if(args.length <= 0) 
		{
			System.out.println("Usage: java Minesweeper minefieldtxt");
			System.exit(0);
		}
		
		int fieldCount = CountFields(args[0]);
		List<String[][]> fields = new ArrayList<String[][]>(fieldCount);
		FillFieldsList(args[0], fields);
		ConvertFieldsLists(fields);
		PrintLists(fields);
	}
	
	//Counts the number of minefields present in the txt input file
	//Parameters:
	//String filename - represents the args parameter that holds the filename
	//Returns:
	//int - number of minefields in input file
	//Catches:
	//Catches a general Exception
	public static int CountFields(String fileName) 
	{
		String line = "";
		int fields = 0;
		FileReader fileR = null;
		BufferedReader bufferedR = null;
		try{
			fileR = new FileReader(fileName);
			bufferedR = new BufferedReader(fileR);
			while(!((line = bufferedR.readLine()).contains("0 0"))) 
			{
				if(IsFieldSize(line)) 
				{
					fields++;
				}
			}	
		} catch (Exception e) 
		{
			e.printStackTrace();
		}finally {
			try {
				bufferedR.close();
				fileR.close();
			} catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fields;
	}

	//Fills an ArrayList with 2d String arrays which each represent a single minefield.
	//Parameters:
	//String filename - represents the filename string passed as an arg
	//List<String[][]> fields - Array list containing the 2d String arrays which represent minefields.
	//Catches:
	//IOExeption
	//Exception
	public static void FillFieldsList(String fileName, List<String[][]> fields)
	{
		String line = "";
		FileReader fileR = null;
		BufferedReader bufferedR = null;
		try
		{
			fileR = new FileReader(fileName);
			bufferedR = new BufferedReader(fileR);
			while(!((line = bufferedR.readLine()).contains("0 0"))) 
			{
				int row = 0, column = 0;
				if(IsFieldSize(line)) 
				{
					String[] temp = line.split(" ");
					row = Integer.parseInt(temp[0]);
					column = Integer.parseInt(temp[1]);
				}
				String[][] tempField = new String[row][column];
				for(int i = 0; i < row; i++) 
				{
					line = bufferedR.readLine();
					for(int j = 0; j < column; j++) 
					{
						tempField[i][j] = line.substring(j, j + 1);
					}
				}
				fields.add(tempField);
			}	
		} catch (Exception e) 
		{
			e.printStackTrace();
		}finally 
		{
			try 
			{
				bufferedR.close();
				fileR.close();
			} catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	//Takes the filled fields arraylist and converts the 2d string arrays from the input form "*"/".", to the desired "*"/numeric form.
	//Parameters:
	//List<String[][]> fields - Array list containing the 2d String arrays which represent minefields.
	public static void ConvertFieldsLists(List<String[][]> fields) 
	{
		for(int i = 0; i < fields.size(); i++) 
		{
			String[][] temp = fields.get(i);
			for (int j = 0; j < temp.length; j++)
			{
		        for (int k = 0; k < temp[j].length; k++)
		        {
		            if (!(temp[j][k].contains("*")))
		            {
		                int count = 0;

		                for (int l = j - 1; l <= j + 1; l++)
		                {
		                    for (int m = k - 1; m <= k + 1; m++)
		                    {
		                        if (0 <= l && l < temp.length && 0 <= m && m < temp[j].length)
		                        {
		                            if (temp[l][m].contains("*")) 
					    {
		                                ++count;
		                            }
		                        }
		                    }//End for loop m
		                }//End for loop l

		                temp[j][k] = Integer.toString(count);
		            }
		        }//End for loop k
		    }//End for loop j
		}//End for loop i
	}
	
	//Takes the converted arraylist of minefields and prints the field number followed by the converted field layout.
	//Parameters:
	//List<String[][]> fields - Array list containing the 2d String arrays which represent minefields.
	public static void PrintLists(List<String[][]> fields) 
	{
		for(int i = 0; i < fields.size(); i++) 
		{
			String[][] temp = fields.get(i);
			System.out.println("Field #" + (i + 1) + ":");
			for(int j = 0; j < temp.length; j++) 
			{
				for(int k = 0; k < temp[j].length; k++) 
				{
					System.out.print(temp[j][k]);
				}//End for loop k
				System.out.println("");
			}//End for loop j
			System.out.println("");
		}//End for loop i
	}
	
	
	//Determines if a line of minefield layout input is the field square size, if yes returns true, if no returns false.
	//Parameters:
	//String input - any given minefield input line
	//Returns:
	//boolean - whether a line contains the field size informatio or not
	public static boolean IsFieldSize(String input) 
	{
		String temp = input.replaceAll("[\n\r]",  "");
		Pattern p = Pattern.compile("\\d+ \\d+");
		Matcher m = p.matcher(temp);
		return m.matches();
	}
}
