

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MinesweeperBoard
extends JPanel
{
	int size;
	Minesweeper board;
	
	public MinesweeperBoard(Minesweeper board)
	{
	
		
		this.size = 20;
		this.board = board;
		
		initialize();
	}
	
	private void initialize()
	{
		setSize(board.width, board.height);
		addMouseListener(new MinesweeperMouseListener());
	}
	
	public boolean isGameOver()
	{
		return board.isGameOver();
	}
	
	@Override
    public void paintComponent(final Graphics g)
    {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		
		String ms = board.toString();
		for (int row = 0; row < board.height; row++)
			for (int column = 0; column < board.width; column++)
			{
				g2.drawRect(row * size, column * size, size, size);
				if (ms.charAt(row * board.width + column) != '_')
					g2.drawString("" + ms.charAt(row * board.width + column), row * size + size / 3, column * size + 3 * size / 4);
			}
    }
	
	class MinesweeperMouseListener
	extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent e)
		{
			int x = e.getX() / size;
			int y = e.getY() / size;
			board.click(x, y);
			repaint();
		}
	}
	
}

