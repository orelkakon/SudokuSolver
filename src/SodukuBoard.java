import java.util.List;

public class SodukuBoard {
	public int [][] sodukuBoard = null; // An array of Lists to represent adjacency list
	public int N; // number of vertices
	
	public SodukuBoard(List<Cell> cells, int N) // Constructor
	{
		this.N = N;
		sodukuBoard = new int[N][N];
		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++){
				sodukuBoard[i][j] = 0;  // initialize board
			}
		}
		for(Cell C : cells) {
			sodukuBoard[C.indexI-1][C.indexJ-1] = C.value;
		}
	}
}

