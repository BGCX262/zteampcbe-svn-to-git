
public abstract class ChemicalEntity extends Thread {

	private MovementSpace mvSpace;
	private Position currentPosition;

	public ChemicalEntity(Position currentPosition) {
		this.currentPosition = currentPosition;
		mvSpace = MovementSpace.getMovementSpace();
		mvSpace.addAtom(this,currentPosition);
	}

	public void run() {

		while (true) {

			synchronized (this) {
				while (canMove()) {
					Position newPosition =generatePosition();
					Position retPosition = mvSpace.moveInSpace(currentPosition,newPosition);
					if(retPosition == null)
						System.out.println(toString()+"couldn't move in space: "+newPosition.toString());
					else 
						{
						currentPosition = retPosition;
						System.out.println(toString()+"moved to: "+retPosition.toString());
						mvSpace.printSpace();
						}
					try {
						sleep(600);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
//			System.out.println(newPosition.toString());	
		return newPosition;
	}

	public abstract boolean canMove();

	public abstract int getValence();

	public abstract void stopThread();

	public abstract void startThread();

	@Override
	public String toString() {

		return "("+currentPosition.getX()+","+currentPosition.getY()+")";
	}
}
