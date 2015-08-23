import javax.crypto.spec.PSource;

public abstract class ChemicalEntity extends Thread {

	private MovementSpace mvSpace;
	private Position currentPosition;
	private boolean keepThreadAliveFlag= true;

	public ChemicalEntity(Position currentPosition) {
		this.currentPosition = currentPosition;
		mvSpace = MovementSpace.getMovementSpace();
		mvSpace.addAtom(this,currentPosition);
	}

	public void run() {

		while (keepThreadAliveFlag) {

			synchronized (this) {
			
				while(!canMove())// || keepThreadAliveFlag)
					try {
						System.out.println("i'm in wait"+this.getId()+" "+ this.toString());
						wait();
						System.out.println("out of wait");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}

			Position newPosition =generatePosition();
			Position retPosition = mvSpace.moveInSpace(currentPosition,newPosition);
			if(retPosition == null)
				System.out.println(toString()+this.getId()+"couldn't move in space: "+newPosition.toString());
			else 
			{
				System.out.println(toString()+this.getId()+" moved to: "+retPosition.toString());
				setCurentPosition(retPosition);
				//						currentPosition = retPosition;
				mvSpace.printSpace();
			}
			try {
				sleep(600);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	private synchronized Position generatePosition() // shouldn't be sync?
	{
		Position newPosition ;
		do{
			int differenceOnX = (int) (Math.random() * 3 )- 1; // can be -1, 0 or 1
			int differenceOnY = (int) (Math.random() * 3 )- 1; // can be -1, 0 or 1
			newPosition = new Position(currentPosition.getX() + differenceOnX,	currentPosition.getY() + differenceOnY);
		}while(currentPosition.equals(newPosition)); //shouldn't generate the same position 

		return newPosition;
	}

	public abstract boolean canMove();

	public abstract int getValence();

	public abstract void pauseThread();

	public abstract void startThread();

	public void stopThread()
	{
		keepThreadAliveFlag = false;
	}
	public String toString() {

		return "("+currentPosition.getX()+","+currentPosition.getY()+")";
	}

	/**
	 * when fee-in moleculs we need this method to set thei position to the new one. 
	 */
	public void setCurentPosition(Position newPosition)
	{
		this.currentPosition = newPosition;
	}

}
