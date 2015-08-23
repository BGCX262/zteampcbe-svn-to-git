import java.util.ArrayList;
import java.util.Vector;

public class MovementSpace {
	private Vector<Vector<ChemicalEntity>> space = null;
	private static MovementSpace movementSpace = null;
	private int lengthOx;
	private int lengthOy;

	public MovementSpace(int lengthOx, int lengthOy) {
		this.lengthOx = lengthOx;
		this.lengthOy = lengthOy;
		space = new Vector<Vector<ChemicalEntity>>(lengthOx);
		for (int i = 0; i < lengthOx; i++)
			{
			space.add(new Vector<ChemicalEntity>(lengthOy));
			for (int j=0; j< lengthOy; j++)
				space.get(i).add(null);
			}
			
		this.movementSpace = this;
	}

	/**
	 * @return : true if movement is posible, false otherwise
	 * 
	 */
	public synchronized Position moveInSpace(Position currentPosition,Position newPosition) 
	{
		
		if(validPosition(newPosition))
			if (emptySpace(newPosition)) {
				{
				space.get(newPosition.getX()).set(newPosition.getY(),	space.get(currentPosition.getX()).get(currentPosition.getY()));
				space.get(currentPosition.getX()).set(currentPosition.getY(),null);
				}
			return newPosition; 	
			}
	
		return null;
	}

	public synchronized void printSpace() {
		System.out.println("New space "+ space.size()+" "+space.get(1).size());
		for (int i = 0; i < lengthOx; i++)
		{
			for (int j = 0; j < lengthOy; j++) 
				System.out.print(space.get(i).get(j)+" ");
			System.out.println();
		}


	}

	private synchronized boolean emptySpace(Position pos) {

		if(space.get(pos.getX()).get(pos.getY()) == null)
			return true;
		return false;
	}

	public static MovementSpace getMovementSpace() {
		if (movementSpace == null)
			movementSpace = new MovementSpace(100, 100);
		return movementSpace;
	}
	public boolean validPosition(Position p)
	{
		if(p.getX() >= lengthOx || p.getX() <0 )
			return false;
		if(p.getY() >= lengthOy || p.getY() <0 )
			return false;
		return true;
	}

	public synchronized void addAtom(ChemicalEntity chemicalEntity, Position currentPosition) {
		space.get(currentPosition.getX()).set(currentPosition.getY(),chemicalEntity);
		
	}
}
