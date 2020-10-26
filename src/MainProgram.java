import java.util.Scanner;
public class MainProgram {
	public static void main(String[] args) {
		System.out.println("Please Insert the full path of file (must end with .sudoku or .puzzle)");
		Scanner input = new Scanner(System.in);
		String filenamePath = input.nextLine();
		String [] splited = filenamePath.split("\\\\"); //split to take the filename.ham
		String filename = splited[splited.length-1]; //filename.sudoku or .puzzle
		String [] splited2 = filename.split("\\."); // split to take just filename
		filename = splited2[0]; //filename
		input.close();

		SodukuBoard SB = null;
		try {
			SB = HandlerFile.Extract(filenamePath); //build graph
		} catch (Exception e) { // file not found
			e.printStackTrace();
			System.out.println("the file is not exist");
			System.exit(0);
		}
		if(!splited[splited.length-1].contains(".puzzle")) {
			String pre = "c the solution for the example in file " + filename+".sudoku" + "\nc \n";  
			SodukuSolver SS = new SodukuSolver(SB.sodukuBoard, SB.N);
			SS.solve();
			//SS.printBoard();
			HandlerFile.craeteAndWriteFile(filename, SS.board, pre, "", true);	
		}
		else {
			String pre = "c the solution for the example in file " + filename+".puzzle" + "\nc \n";  
			SodukuSolver SS = new SodukuSolver(SB.sodukuBoard, SB.N);
			String post_s = "";
			int number_solutions = SS.solveMoreSol(0, 0, SB.sodukuBoard, 0);
			if(number_solutions > 1) {
				int [] result = SS.getDifference();
				post_s = "s multisol "+result[0]+" "+result[1]+" "+result[2]+" "+result[3]+"\n";
			}
			else {
				SS.solve();
				if(!SS.failSolve()) {
					post_s = "s nosolution\n";
				}
				else {
					post_s = "s sudoku\n";
				}
			}
			HandlerFile.craeteAndWriteFile(filename, SS.board, pre, post_s, false);
		}
	}
}
