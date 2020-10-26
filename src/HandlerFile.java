import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;

public class HandlerFile {
	/***
	 * In this class I was help from stack overflow in create/write/read from files.
	 */
	static String prefix = ""; // maybe need add \n
	public static SodukuBoard Extract(String path) throws Exception {
		SodukuBoard result;
		int N = 0;
		List<Cell> cells = new ArrayList<>(0);
		List<String> allLines = readFile(path); // extract the lines from file
		if(allLines == null) {
			throw new Exception();
		}
		for(int i = 0; i < allLines.size(); i++) { // run over all the lines
			if(allLines.get(i) == " ") { // not need to be 
				continue;
			}
			else if(allLines.get(i).charAt(0) == 'c') { // the first char is c 
				continue; // ignore (its comment)
			}
			else if(allLines.get(i).charAt(0) == 'p') { // the first char is p
				prefix += allLines.get(i) + "\n";
				String[] arr = allLines.get(i).split(" "); // split to 3 cells
				N = Integer.parseInt(arr[2]);
				N = (int) Math.pow(N, 2);
			}
			else { // the first char is v
				String[] arr = allLines.get(i).split(" "); // split to 4 cells
				int I = Integer.parseInt(arr[1]); // INDEX I
				int J = Integer.parseInt(arr[2]); // INDEX J
				int V = Integer.parseInt(arr[3]); // VALUE
				cells.add(new Cell(I,J,V)); // add cell
			}
		}
		result = new SodukuBoard(cells,N); //build soduku
		return result;
	}
	public static void craeteAndWriteFile(String filename,int[][] toWrite,String pre,String post, boolean todo) {
		String message = "";
		if(todo) {
			for(int i = 0; i < toWrite.length; i++){
				for(int j = 0; j < toWrite.length; j++){
					message += "v "+(i+1)+" "+(j+1)+" "+toWrite[i][j]+"\n";
				}
			}
		}
		PrintWriter writer = null;
		try {
			if(todo) {
				writer = new PrintWriter(filename+".suduko.solution", "UTF-8");
				writer.println(pre + prefix + message);
			}
			else {
				writer = new PrintWriter(filename+".puzzle.solution", "UTF-8");
				writer.println(pre + prefix + post);
			}
			System.out.println("The file craeted , it's found in directory of project!");
		} catch (Exception e) {
			System.out.println("An error occurred.");
		}
		writer.close();
	}

	private static List<String> readFile(String path)
	{
		List<String> allLines = new ArrayList<String>();
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line;
			while ((line = reader.readLine()) != null)
			{
				allLines.add(line);
			}
			reader.close();
			return allLines;
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
