
public class Minesweeper {
	int height; // the height of the board
	int width; // the width of the board
	int safeblocks; // the number of safe blocks of the board
	int reveals; // the number of blocks revealed
	MineBlock[][] blocks; // the blocks of the board

	
	// The method initializes the settings for the game board
	
	private void initialize(int height, int width) {
		if (height < 10 || width < 10)
			throw new IllegalArgumentException("The height and width must not be less than 10");

		this.height = height;
		this.width = width;

		blocks = new MineBlock[height][width];
		setUpBoard();
	}

	//default height n width is 10
	public Minesweeper() {
		initialize(10, 10);
	}

	//else
	
	public Minesweeper(int height, int width) {
		initialize(height, width);
	}

	// put bomb randomly down and count the bomb surrounding
	private void setUpBoard() {
		setUpBombs(); // put down the bombs
		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				if (blocks[row][column] == null)
					blocks[row][column] = new MineBlock();

				blocks[row][column].count += countBombs(row - 1, column - 1);
				blocks[row][column].count += countBombs(row - 1, column);
				blocks[row][column].count += countBombs(row - 1, column + 1);

				blocks[row][column].count += countBombs(row, column - 1);
				// blocks[row][column].count+= countBombs(row , column );
				blocks[row][column].count += countBombs(row, column + 1);

				blocks[row][column].count += countBombs(row + 1, column - 1);
				blocks[row][column].count += countBombs(row + 1, column);
				blocks[row][column].count += countBombs(row + 1, column + 1);
			}
		}
	}

	// put bomb down
	private void setUpBombs() {
		int row, column;
		// how many bombs
		int bombs = height * width / 10;
		// how many safeblocks total
		safeblocks = height * width - bombs;

		int count = bombs;

		while (count > 0) // exit when all bomb set on the board
		{
			// pick a random position
			row = (int) (Math.random() * (height - 1));
			column = (int) (Math.random() * (width - 1));

			// if the position has not set bomb yet
			if (blocks[row][column] == null) {
				blocks[row][column] = new MineBlock(true);
				count = count - 1; // decrease the bomb amount
			}
		}
	}

	//The method counts the number of bombs around the current block
	private int countBombs(int row, int column) {
		if (row < 0 || row > height - 1 || column < 0 || column > width - 1){
			return 0;}

		if (blocks[row][column] == null){
			return 0;}

		if (!blocks[row][column].bomb){
			return 0;}

		else
			return 1;
	}
	// can you click ?
	private boolean isClickable(int row, int column) {
		if (row < 0 || row > height - 1 || column < 0 || column > width - 1) {
			return false;
		}

		if (safeblocks == 0) {
			return false;
		}

		if (blocks[row][column].state == MineBlockState.HIDE) {
			return true;
		}

		return false;
	}
	
	// if clickable -> reveal
	public void click(int row, int column) {
		if (isClickable(row, column))
			reveal(row, column);
	}

	// flood fill reveal
	public void reveal(int row, int column) {
		if (row < 0 || row > height - 1 || column < 0 || column > width - 1)
			return; // doing nothing if out of ranges

		MineBlock block = blocks[row][column];
		if (block.state != MineBlockState.REVEAL) {
			block.state = MineBlockState.REVEAL;

			if (block.bomb) {
				safeblocks = 0;
				throw new IllegalArgumentException("GAME OVER, YOU CLICKED ON THE BOMB");
			} // the winning condition depends on the number of bombs left
			else {
				safeblocks--;
				// if no bomb next to fill, move to other direction
				if (block.count == 0) {
					// flood fill 8 direction
					reveal(row - 1, column - 1); // North West
					reveal(row - 1, column); // North
					reveal(row - 1, column + 1); // North East

					reveal(row, column - 1); // West
					reveal(row, column + 1); // East

					reveal(row + 1, column - 1); // South West
					reveal(row + 1, column); // South
					reveal(row + 1, column + 1); // South East
				}
			}
		}

	}

	


	// check game over
	public boolean isGameOver() {
		if(safeblocks==0){
			System.out.println("You win!");
		}
		return safeblocks == 0;
	}
	// print the game
	public String toString() {
		String builder = new String();

		for (int row = 0; row < height; row++) {
			for (int column = 0; column < width; column++) {
				MineBlock block = blocks[row][column];
				if (block.state == MineBlockState.REVEAL) {
					if (block.bomb)
						builder=builder +"B";
					else
						builder=builder +block.count;
				} else
					builder=builder +"_";
			}
		}

		return builder.toString();
	}


}
