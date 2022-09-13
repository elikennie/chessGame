// CS2210
// Elijah Kennie
// Student Number 251163208

public class Board implements BoardADT{
	
	private Dictionary newDict; // the dictionary created in this method
	private int size; // size of the board 
	private char[][] theBoard; // 2d character array storing position of pieces
	private char win; // if a win is detected this variable will store whether it was either c or h that won 
	
	// constructor that gives theBoard instance variable an n*n size and populates it, as well as giving size variable a value 
	public Board (int board_size, int empty_positions, int max_levels) {
		size = board_size;
		
		// make theBoard a boardSize*boardSize array, then fill with empty spaces ('e')
		theBoard = new char[size][size];
		for(int i = 0; i < size; i++) {
			
			for(int j = 0; j < size; j++) {
				theBoard[i][j] = 'e';
			}
		}
	}
	
	// creates a new dictionary
	public Dictionary makeDictionary() {
		newDict = new Dictionary(9967); // initialize newDict with a prime number close to 10000
		return newDict;
	}
	
	// checks for a node in the dictionary with the same string as the current board
	public int repeatedLayout(Dictionary dict) {
		String currBoard = String.valueOf(theBoard);
		return dict.getScore(currBoard);
	}
	
	// represents the content of the board as a string then creates a new layout object with this and score
	public void storeLayout(Dictionary dict, int score){
		String currBoard = String.valueOf(theBoard);
		new Layout(currBoard, score); // creates a new layout variable with the string value of the board and score
	}
	
	// saves specified tile as the character given
	public void saveTile(int row, int col, char symbol) {
		theBoard[row][col] = symbol; // make the nth row by nth column equal to the input character
	}
	
	// checks if the tile is empty
	public boolean positionIsEmpty (int row, int col) { // returns true if the position is filled with char 'e' and false otherwise
		if(theBoard[row][col] == 'e') {
			return true;
		}
		else {
			return false;
		}
	}
	
	// checks if the tile is a computer tile
	public boolean isComputerTile (int row, int col) { // returns true if the position is filled with char 'c' and false otherwise
		if(theBoard[row][col] == 'c') {
			return true;
		}
		else {
			return false;
		}
	}
	
	// checks if specified tile is human tile
	public boolean isHumanTile (int row, int col) { // returns true if the position is filled with char 'h' and false otherwise
		if(theBoard[row][col] == 'h') {
			return true;
		}
		else {
			return false;
		}
	}
	
	// checks if a winning move has been executed from upper right to lower left
	private boolean diagWin() {  
		boolean fullDiag = true;
		int j = 0;
		
        if(positionIsEmpty(0, size-1)) { // if first position is empty return false
            fullDiag = false;
        }
        char starterTile = theBoard[0][size-1];
        
        for(int i = size - 1; i >= 0; i--) { // check to make sure the tiles are all the same as the starter tile
            if(theBoard[j][i] != starterTile) {
            	fullDiag = false; // if one is not the same as the others return false
            }
            j++;
            
            if(fullDiag == true) {
            	win = theBoard[size-1][0];
            }
        }
        return fullDiag;
	}
	
	// checks if a winning move has been executed from upper left to lower right
		private boolean oppositeDiagWin () {  
			boolean fullOppDiag = true;
			
			if(positionIsEmpty(0, size-1)) { // if first position is empty return false
	            fullOppDiag = false;
}
			char tile = theBoard[0][0];
			
			for(int i = 0; i < size-1; i++) {
				if(tile != theBoard[i+1][i+1]) {
					fullOppDiag = false; // the win is false as soon as the piece to the diagonal of the current is not equal to the current piece 
				}
				if(fullOppDiag == true) {
					win = theBoard[size-1][size-1];
				}
			}
			return fullOppDiag;
		}
	
	// checks if a winning move has been executed in a row
	private boolean rowWin () {
		boolean fullRow = false; // will be set to true if the entire row is either human or computer tiles
		
		for(int i = 0; i < size && !fullRow; i++) { // row count
			fullRow = true;
			
			for(int j = 0; j < size-1 && fullRow; j++) { // col count
				if (theBoard[i][j] != theBoard[i][j+1]) {
					fullRow = false; // if the next index in the row is not the same as the previous, fullRow is false
				}
			}
			if(fullRow == true) {
				win = theBoard[i][size-1];
			}
		}
		return fullRow;
	}
	
	// checks if a winning move has been executed in a col
	private boolean colWin () {
		boolean fullCol = false; // will be set to true if the entire col is either human or computer tiles
		
		for(int j = 0; j < size && !fullCol; j++) {
			fullCol = true;
			
			for(int i = 0; i < size-1 && fullCol; i++) {
				if(theBoard[i][j] != theBoard[i+1][j]) { 
					fullCol = false; // checks that the next element in the col will be the same as the prev, if not return false
				}
			}
			if(fullCol == true) {
				win = theBoard[0][j];
			}
		}
		return fullCol; // return if it is a win or not at end of function
	}

	// detects if there is a draw by checking if there is a win, then checking if there are any open tiles 
	public boolean isDraw(char symbol, int empty_positions) {
		if(rowWin() || colWin() || diagWin() || oppositeDiagWin()) { // if any of these are true, return false (cannot be a draw if a win occurs)
			return false;
		}
		else {
			
			for(int i = 0; i < size; i++) {
				
				for(int j = 0; j < size; j++) {
					if(positionIsEmpty(i,j)) { // if there are any empty positions on the board
						return false;
					}
				}
			}
			return true; // if all squares are filled and no win is detected, the game is a draw
		}
	}

	// will return true if a win has been detected 
	public boolean winner(char symbol) {
		if(rowWin() || colWin() || diagWin() || oppositeDiagWin()) {
			if(win == symbol) {    //if the winning symbol equals the entered symbol
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	// returns a number based on the winner, a draw, or if the game has not finished yet 
	public int evaluate(char symbol, int empty_positions) {
		if(winner('h')) { // human win returns 0
			return 0;
		}
		
		else if(winner('c')) { // computer win returns 3
			return 3;
		}
		
		else if(isDraw(symbol, empty_positions)) { // if the game has been decided as a draw return 2
			return 2;
		}
		
		else { // no win or draw yet return 1
			return 1;
		}
		
	}
}
