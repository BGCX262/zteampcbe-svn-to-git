import java.util.ArrayList;
import java.util.Vector;

public class MovementSpace {
	private Vector<Vector<ChemicalEntity>> space = null;
	private static MovementSpace movementSpace = null;
	public static int  lengthOx =10;
	public static int lengthOy = 20;

	private MovementSpace() {
		space = new Vector<Vector<ChemicalEntity>>(lengthOx);
		for (int i = 0; i < lengthOx; i++)
			{
			space.add(new Vector<ChemicalEntity>(lengthOy));
			for (int j=0; j< lengthOy; j++)
				space.get(i).add(null);
			}
			
		MovementSpace.movementSpace = this;
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
				//calculate if reactions with near cells is possible and do { ...}while posible
				}
			return newPosition; 	
			}
			else
			{
			if(mergePossible(currentPosition,newPosition))
			{
				System.out.println("2 atoms merged");
				ChemicalEntity temp = space.get(newPosition.getX()).get(newPosition.getY());
				Molecules newMolec = new Molecules(newPosition);
				temp.setCurentPosition(newPosition);
				temp.pauseThread();
				newMolec.addToMolecule(temp);
				
				temp = space.get(currentPosition.getX()).get(currentPosition.getY());
//				System.out.println("poz curenta"+currentPosition);

//				System.out.println("val obt la poz"+temp);
				temp.pauseThread();
				newMolec.addToMolecule(temp);
				
				space.get(currentPosition.getX()).set(currentPosition.getY(),null);
				space.get(newPosition.getX()).set(newPosition.getY(), newMolec);
//				System.out.println("new molecule "+newMolec);
				newMolec.start();
			
				return newPosition;
			}
			
				//if merge possible move to cell and after 
				//calculate if reactions with near cells is possible and do { ...}while posible
			
			}
		return null;
	}
	
//	calculate if merge possible
	private synchronized boolean mergePossible(Position currentPosition, Position newPosition) 
	{
	
		return true;
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
			movementSpace = new MovementSpace();
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
