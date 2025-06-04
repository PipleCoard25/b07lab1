import java.io.File;

public class Driver {
	public static void main(String [] args) {
		double [] c1 = {6,0,0,5};
		Polynomial p1 = new Polynomial(c1);
		System.out.println(p1.evaluate(1));

		double [] c2 = {5,3};
		Polynomial p2 = new Polynomial(c2);
		System.out.println(p2.evaluate(1));

		File test = new File("test.txt");
		Polynomial p3 = new Polynomial(test);
		System.out.println(p3.evaluate(2));

		System.out.println("p1(0.1) = " + p1.evaluate(0.1));
		System.out.println("p2(0.1) = " + p2.evaluate(0.1));

		System.out.println("p3(0.1) = " + p3.evaluate(0.1));

		Polynomial a = p3.multiply(p2);

		System.out.println("a(0.1) = " + a.evaluate(0.1));

		p2.saveToFile("Fart");
		/*
		if(s.hasRoot(1))
			System.out.println("1 is a root of s"); 
		else
			System.out.println("1 is not a root of s");*/
	}
}