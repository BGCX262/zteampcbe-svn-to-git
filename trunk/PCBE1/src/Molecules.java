import java.util.Vector;


public class Molecules extends ChemicalEntity {

	private boolean canMoveFlag = true;
	private Vector<ChemicalEntity> containedAtoms;
	private int valene; 

	public Molecules(Position currentPosition)
	{
		super(currentPosition);
		containedAtoms = new Vector<ChemicalEntity>();
	}

	@Override
	public String toString() {
		String str= ""; //+super.toString();
		for ( int i=0; i<containedAtoms.size(); i++)
		{
			str = str + containedAtoms.get(i).toString() + " ";
		}

		return str;
	}





	public boolean canMove() 
	{

		return canMoveFlag;
	}


	@Override
	public void startThread() {
		/*TODO asta se aplica daca vreau as le dau drumul la toate, 
		 * dar probabil alta va fi regula; eventual dau drumul doar la cea mai slaba
		*/
		for ( int i=0 ; i<containedAtoms.size(); i++ )
			containedAtoms.get(i).startThread();
		this.stopThread(); //nush daca merge 
	}

	public void pauseThread() {
		for ( int i=0 ; i<containedAtoms.size(); i++ )
			containedAtoms.get(i).pauseThread();
		canMoveFlag = false;
	}
	/**
	 *  - add the entity to molecule and stops it's thread. 
	 */
	public void addToMolecule(ChemicalEntity ch)
	{
		containedAtoms.add(ch);
	}


	@Override
	public int getValence() {
		return 0;
	}
	
	@Override
	public void setCurentPosition(Position newPosition)
	{
		super.setCurentPosition(newPosition);
		for (int i =0; i<containedAtoms.size(); i++)
			containedAtoms.get(i).setCurentPosition(newPosition);
		
	}
	//TODO. create a spilt mothod that removes a chemicalentity form the list and start's it thread
}
