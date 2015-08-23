
public class Atoms extends ChemicalEntity {

	private String name;
	private boolean canMoveFlag = true; // it's sayt that this thread can move
	public  Atoms(Position currentPosition, String name)
	{
		super(currentPosition);
		this.name = name;
	}

	public synchronized void pauseThread()
	{
		System.out.println(toString() +" is beeing paused");
		canMoveFlag = false;
	}
	public synchronized void startThread()
	{
		canMoveFlag = true;
	}
	
	public synchronized boolean canMove() {
		
		return canMoveFlag;
	}
	
	public String toString() {
	
		return name+super.toString();

	}

	@Override
	public int getValence() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
