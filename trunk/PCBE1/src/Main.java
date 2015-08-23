
public class Main 
{

	public static void main(String[] args) {
	
		Atoms nr1 = new Atoms(new Position(0, 0), "C");
		Atoms nr2 = new Atoms(new Position(1,0), "H");
		Atoms nr3 = new Atoms(new Position(0,1),"O");
		
		nr1.start();
		nr2.start();
		nr3.start();
	}
	
}
