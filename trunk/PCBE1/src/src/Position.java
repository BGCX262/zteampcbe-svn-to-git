
public class Position {

	private int x;
	private int y;
	
	public Position(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public String toString() {
			return "x="+x+" y="+y;
	}
	
	public boolean equals(Object obj) {
		if( obj instanceof Position)
		 if(((Position)obj).x == this.x && ((Position)obj).y == this.y )
			 return true;
		return false;
	}
}
