import java.util.Vector;

public class Atoms extends ChemicalEntity {

	private String name;
	private Vector<Integer> MaxLayer;
	private Vector<Integer> OccLayer;
	private Vector<Integer> OccLayerNow;
	private boolean canMoveFlag = true; // it's sayt that this thread can move
	public  Atoms(Position currentPosition, String name, Vector<Integer> MaxLayer, Vector<Integer> OccLayer)
	{
		super(currentPosition);
		this.name = name;
		this.MaxLayer = (Vector) MaxLayer.clone();
		this.OccLayer = (Vector) OccLayer.clone();
		this.OccLayerNow = (Vector) OccLayer.clone();
	}

	public synchronized void stopThread()
	{
		canMoveFlag = false;
	}
	public synchronized void startThread()
	{
		canMoveFlag = true;
	}
	
	public boolean canMove() {
		
		return canMoveFlag;
	}
	
	public String toString() {
	
		return name+super.toString();

	}

	public Vector<Integer> getMax()
	{
		return MaxLayer;
	}
	
	public Vector<Integer> getOccuppied()
	{
		return OccLayer;
	}
	
	/*
	 * Add in pos n.
	 */
	public boolean setOccupied(int pos, int n)
	{
		if (OccLayerNow.get(pos) + n >= MaxLayer.get(pos))
			return false;
		OccLayerNow.set(pos, OccLayerNow.get(pos) + n);
			return true;
	}
	
	public boolean unsetOccupied(int pos, int n)
	{
		if (OccLayerNow.get(pos) - n < OccLayer.get(pos))
			return false;
		OccLayerNow.set(pos, OccLayerNow.get(pos) - n);
			return true;
	}
	/*
	 * Return the force of the atom.
	 */
	public int GetForce()
	{
		int force = 0;
		int factor = OccLayerNow.size();
		for (int i = 0; i < OccLayerNow.size(); ++i)
			force += factor--*(MaxLayer.get(i) - OccLayerNow.get(i));
		return force;
	}
	
	/*
	 * When an atom encounters an atom they will 
	 * try to combine if: The sum of the 'electrons'
	 * don't exceed the maximum.
	 */
	public boolean combine(Atoms atom)
	{
		Vector<Integer> aux = new Vector<Integer>(OccLayer.size());
		for (int i = 0; i < aux.size(); ++i)
		{
			aux.add(i, OccLayerNow.get(i) + atom.OccLayerNow.get(i));
			if ((aux.get(i) >= MaxLayer.get(i)) || (aux.get(i) >= atom.MaxLayer.get(i)))
				return false;
		}
		copyVector(OccLayerNow, aux);
		copyVector(atom.OccLayerNow, aux);
		return true;
	}
	
	/*
	 * For the molecule the atom may try one time
	 * with a random atom of the molecule.
	 */
	public boolean combine(Molecules molecule)
	{
		return true;
	}
	@Override
	public int getValence() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void copyVector(Vector<Integer> src, Vector<Integer> org)
	{
		int size = src.size();
		for (int i = 0; i < size; ++i)
			src.add(i, org.get(i));
	}
}
