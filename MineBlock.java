

public class MineBlock
{
	int count;				// the number of bomb around the block
	boolean bomb;			// if the block has bomb
	MineBlockState state;	// the state of the block
	
	public MineBlock()
	{
		this(false);
	}
	
	public MineBlock(boolean bomb)
	{
		if (bomb)
			count = 1;
		else count = 0;
		
		this.bomb = bomb;
		this.state = MineBlockState.HIDE;
	}
	
	public String toString()
	{
		if (bomb)
			return "B";
		else return String.valueOf(count);
	}
}
