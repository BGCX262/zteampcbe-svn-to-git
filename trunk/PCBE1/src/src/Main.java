import java.util.Vector;


public class Main 
{
	public static void main(String[] args) {
		MovementSpace mvsp= new MovementSpace(10,20);
		Atoms nr1 = new Atoms(new Position(0, 0), "C", toV("123", 3), toV("111", 3));
		Atoms nr2 = new Atoms(new Position(7,12), "H", toV("662", 3), toV("153", 3));
		mvsp.printSpace();
		nr1.start();
		nr2.start();
	}
	
	public static Vector<Integer> toV(String str, int n)
	{
		Vector<Integer> vector = new Vector<Integer>();
		for (int i = 0; i < n; ++i)
			vector.add(str.indexOf(i));
		return vector;
	}
}
