// CS2210
// Elijah Kennie
// Student Number 251163208

public class Layout {
	
	private String boardLayout; // the current layout of the board
	private int score; // the current score
	
	 // initializes the Layout class with the input boardLayout and string 
	public Layout(String boardLayout, int score) {
		this.boardLayout = boardLayout;
		this.score = score;
	}
	
	 // getter returning board layout string
	public String getBoardLayout() {
		return boardLayout;
	}
	
	 // getter returning score integer
	public int getScore() {
		return score;
	}
	
}
