import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class BuildTestFileWP {

	public static void main(String[] args) {
		if(args.length < 3) {
			System.out.println("Usage: java BuildTestFile filename nxm p nxm p nxm p ... 0x0, where filename is the name of the output file you want, nxm is the rows by columns and p is a rough percentage of mines desired, 0 - 100. End input with 0x0");
		}
		generateMineField(args);
		System.out.println("Done Generating");
	}
	
	
	public static String generateFieldLayer(int m, int p){
		char[] layer = new char[m];
		Random rand = new Random();
		int random = 0;
		for(int i = 0; i < m; i++) {
			random = rand.nextInt(100 - 1 + 1) + 1;
			if(random < p){
				layer[i] = '*';
			}else {
				layer[i] = '.';
			}
		}
		return new String(layer);
	}
	
	public static void generateMineField(String[] args) {
		try {
			FileWriter write = new FileWriter(args[0]);
			int i = 1;
			while(i < args.length && !(args[i].contains("0x0"))) {
				write.write(args[i].replaceAll("x", " ") + "\n");
				String[] temp = args[i].split("x");
				int n = Integer.parseInt(temp[0]);
				int m = Integer.parseInt(temp[1]);
				
				for(int j = 0; j < n; j++) {
					write.write(generateFieldLayer(m, Integer.parseInt(args[i+1])) + "\n");
				}
				i += 2;
			}
			write.write("0 0\n");
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
