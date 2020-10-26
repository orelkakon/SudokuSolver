public class SodukuSolver {
	public int SIZE;
	public int[][] board;
	boolean first_time = true;
	public int [][] first_board;
	public int [][] second_board;

	public SodukuSolver(int[][] preBoard,int N) {
		this.SIZE = N;
		this.board = preBoard;
		first_board = new int[N][N];
		second_board = new int[N][N];
	}

	// I used a recursive BackTracking algorithm.
	public boolean solve() {
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				if (board[row][col] == 0) {
					// try possible numbers
					for (int number = 1; number <= SIZE; number++) {
						if (isOkCell(row, col, number)) {
							board[row][col] = number;
							if (solve()) { // we start backtracking recursively
								return true;
							} else { // if not a solution, we empty the cell and we continue
								board[row][col] = 0;
							}
						}
					}
					return false; // we return false
				}
			}
		}
		return true; // sudoku solved
	}

	// check if a number is already in a row
	private boolean isInRow(int row, int number) {
		for (int i = 0; i < SIZE; i++)
			if (board[row][i] == number)
				return true;

		return false;
	}

	// check if a number is already in a column
	private boolean isInCol(int col, int number) {
		for (int i = 0; i < SIZE; i++)
			if (board[i][col] == number)
				return true;

		return false;
	}

	// check if is in box
	private boolean isInBox(int row, int col, int number,int sqrtN) {
		int r = row - row % sqrtN;
		int c = col - col % sqrtN;
		for (int i = r; i < r + sqrtN; i++)
			for (int j = c; j < c + sqrtN; j++)
				if (board[i][j] == number)
					return true;
		return false;
	}

	// combined methods
	private boolean isOkCell(int row, int col, int number) {
		return !isInRow(row, number)  &&  !isInCol(col, number)  &&  !isInBox(row, col, number,(int) Math.sqrt(this.SIZE));
	}

	// check if fail to solve sudoku
	public boolean failSolve() {
		for(int i = 0; i < this.SIZE; i++){
			for(int j = 0; j < this.SIZE; j++){
				if(this.board[i][j] == 0)
					return false; //fail
			}
		}
		return true; //success
	}

	//check more solutions for sudoku
	public int solveMoreSol(int i, int j, int[][] cells, int count) {
		if (i == this.SIZE) {
			i = 0;
			if (++j == this.SIZE) {
				if(first_time) {
					deepCopyBoard(true, cells); 
					first_time = false;
				}
				else {
					deepCopyBoard(false, cells); 
				}
				return 1+count;
			}
		}
		if (cells[i][j] != 0)  // skip filled cells
		return solveMoreSol(i+1,j,cells, count);
		for (int val = 1; val <= this.SIZE && count < 2; ++val) {
			if (legal(i,j,val,cells)) {
				cells[i][j] = val;
				// add additional solutions
				count = solveMoreSol(i+1,j,cells, count);
			}
		}
		cells[i][j] = 0; // reset on backtrack
		return count;
	}
	
	// copy board
	private void deepCopyBoard(boolean first,int [][] cells) {
		if(first) {
			for(int i = 0; i < this.SIZE; i++){
				for(int j = 0; j < this.SIZE; j++){
					first_board[i][j] = cells[i][j];
				}
			}
		}
		else {
			for(int i = 0; i < this.SIZE; i++){
				for(int j = 0; j < this.SIZE; j++){
					second_board[i][j] = cells[i][j];
				}
			}
		}
	}

	// check legal for solveMoreSol
	private boolean legal(int i, int j, int val, int[][] cells) {
		this.board = cells;
		return isOkCell(i, j, val);
	}

	public int[] getDifference() {
		int[] result = new int[4];
		for(int i = 0; i < this.SIZE; i++){
			for(int j = 0; j < this.SIZE; j++){
				if(first_board[i][j] != second_board[i][j]) {
					result[0] = i;
					result[1] = j;
					result[2] = first_board[i][j];
					result[3] = second_board[i][j];
					return result;
				}
			}
		}
		return null;
	}
	
	// for debugging
	public void printBoard() { 
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				System.out.print(" " + board[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
}
