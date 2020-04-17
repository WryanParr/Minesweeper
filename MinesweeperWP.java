import java.io.*;
import java.util.*;
import java.util.regex.*;

public class MinesweeperWP {

	public static void main(String[] args) {
		if(args.length <= 0) {
			System.out.println("Usage: java Minesweeper minefieldtxt");
			System.exit(0);
		}
		
		int fieldCount = countFields(args[0]);
		List<String[][]> fields = new ArrayList<String[][]>(fieldCount);
		fillFieldsList(args[0], fields);
		convertFieldsLists(fields);
		printLists(fields);
	}
	
	public static int countFields(String fileName) {
		String line = "";
		int fields = 0;
		FileReader fileR = null;
		BufferedReader bufferedR = null;
		try{
			fileR = new FileReader(fileName);
			bufferedR = new BufferedReader(fileR);
			while(!((line = bufferedR.readLine()).contains("0 0"))) {
				if(isFieldSize(line)) {
					fields++;
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				bufferedR.close();
				fileR.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fields;
	}

	public static void fillFieldsList(String fileName, List<String[][]> fields){
		String line = "";
		FileReader fileR = null;
		BufferedReader bufferedR = null;
		try{
			fileR = new FileReader(fileName);
			bufferedR = new BufferedReader(fileR);
			while(!((line = bufferedR.readLine()).contains("0 0"))) {
				int n = 0, m = 0;
				if(isFieldSize(line)) {
					String[] temp = line.split(" ");
					n = Integer.parseInt(temp[0]);
					m = Integer.parseInt(temp[1]);
				}
				String[][] tempField = new String[n][m];
				for(int i = 0; i < n; i++) {
					line = bufferedR.readLine();
					for(int j = 0; j < m; j++) {
						tempField[i][j] = line.substring(j, j+1);
					}
				}
				fields.add(tempField);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				bufferedR.close();
				fileR.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void convertFieldsLists(List<String[][]> fields) {
		for(int k = 0; k < fields.size(); k++) {
			String[][] temp = fields.get(k);
			for (int i = 0; i < temp.length; i++){
		        for (int j = 0; j < temp[i].length; j++){
		            if (!(temp[i][j].contains("*"))){
		                int count = 0;

		                for (int p = i - 1; p <= i + 1; p++){
		                    for (int q = j - 1; q <= j + 1; q++){
		                        if (0 <= p && p < temp.length && 0 <= q && q < temp[i].length){
		                            if (temp[p][q].contains("*"))
		                                ++count;
		                        }
		                    }
		                }

		                temp[i][j] = Integer.toString(count);
		            }
		        }
		    }
		}
	}
	
	public static void printLists(List<String[][]> fields) {
		for(int i = 0; i < fields.size(); i++) {
			String[][] temp = fields.get(i);
			System.out.println("Field #" + (i+1) + ":");
			for(int j = 0; j < temp.length; j++) {
				for(int k = 0; k < temp[j].length; k++) {
					System.out.print(temp[j][k]);
				}
				System.out.println("");
			}
			System.out.println("");
		}
	}
	
	public static boolean isFieldSize(String input) {
		String temp = input.replaceAll("[\n\r]",  "");
		Pattern p = Pattern.compile("\\d+ \\d+");
		Matcher m = p.matcher(temp);
		return m.matches();
	}
}
