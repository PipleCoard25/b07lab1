import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;

class Polynomial {
	public double[] coeffs;
	public int[] exp;

	
	public Polynomial() {
		this.coeffs = new double[] {0};
		this.exp = new int[] {0};
	}
	
	public Polynomial(double[] hello) {
		int zeros = 0;
		for (int i = 0; i < hello.length; i++) {
			if (hello[i] == 0) {
				zeros++;
			}
		}

		int total = hello.length-zeros;

		this.coeffs = new double[total];
		this.exp = new int[total];
		int spot = 0;
		for (int i = 0; i < total; i++) {
			while (hello[spot] == 0) {
				spot++;
			}
			coeffs[i] = hello[spot];
			exp[i] = spot;
			spot++;
		}
	}

	public Polynomial(Polynomial why) {
		this.coeffs = new double[why.coeffs.length];
		this.exp = new int[why.coeffs.length];

		for (int a=0; a<why.coeffs.length; a++) {
			this.coeffs[a] = why.coeffs[a];
			this.exp[a] = why.exp[a];
		}
	}

	public Polynomial(File file) {
		try {
			Scanner reading = new Scanner(file);
			String line = (reading.nextLine()+" ");

			System.out.println(line);

			//counting '-' and '+'
			int total = 0;
			for (int i = 0; i<line.length();i++){
				if (line.charAt(i) == '-' || line.charAt(i) == '+') {
					total++;
				}
			}
			if (line.charAt(0) != '-') total ++;
			this.coeffs = new double[total];
			this.exp = new int[total];

			String num = "+";
			int spot = 0;
			for (int a=0; a<total;a++) {
				exp[a] = 0;
				if (line.charAt(spot) == '-' || line.charAt(spot) == '+') {
					num = Character.toString(line.charAt(spot));
					spot++;
				}
				while (Character.isDigit(line.charAt(spot))){
					num = num + Character.toString(line.charAt(spot));
					spot++;
				}
				coeffs[a] = Double.valueOf(num);
				if (line.charAt(spot) == 'x') {
					spot++;
					num = "";
					while (Character.isDigit(line.charAt(spot))){
						System.out.println("blob"); 
						num = num + Character.toString(line.charAt(spot));
						spot++;
					}
					exp[a] = Integer.parseInt(num);
				}
			}
			System.out.println("Printing List:");
			for (int c = 0; c < this.coeffs.length; c++) {
				System.out.println(this.coeffs[c] + "x" + this.exp[c]); 
			} 
			
		}
		catch (FileNotFoundException e) {
	    System.out.println("An error occurred.");
	    e.printStackTrace();
		}

	}
	
	public Polynomial add(Polynomial hello) {
		this.coeffs = Arrays.copyOf(this.coeffs, hello.coeffs.length + this.coeffs.length);
		this.exp = Arrays.copyOf(this.exp, hello.exp.length + this.exp.length);

		for (int i = 0; i< hello.coeffs.length; i++) {
			int spot = 0;
			while (hello.exp[i] != this.exp[spot] && this.coeffs[spot] != 0 ){
				spot++;
			}
			System.out.println("adding at " + this.coeffs[spot] + " and " + this.exp[spot] + " which is at " + spot);
			this.coeffs[spot] += hello.coeffs[i];
			if (this.exp[spot] == 0) {
				this.exp[spot] = hello.exp[i];
			}
		}

		int zeroes = 0;
		for (int a = 0; a< this.exp.length; a++) {
			if (this.coeffs[a] == 0) zeroes++;
		}

		double[] tempco = new double[this.coeffs.length-zeroes];
		int[] tempin = new int[this.coeffs.length-zeroes];

		int part = 0;
		for (int b = 0; b< this.coeffs.length; b++) {
			if (this.coeffs[b] != 0) {
				tempco[part] = this.coeffs[b];
				tempin[part] = this.exp[b];
				part++;
			}
		}

		this.coeffs = tempco;
		this.exp = tempin;
		
		return this;
	}
	
	public Polynomial multiply(Polynomial hello) {
		double[] init = new double[this.coeffs.length];
		for (int a=0 ; a < this.coeffs.length; a++) init[a] = 1;
		Polynomial temp = new Polynomial(init);
		Polynomial term = new Polynomial(init);

		for (int i=0; i < hello.coeffs.length; i++) {
			System.out.println(hello.coeffs[i]);
			for (int j=0; j < this.coeffs.length; j++){
				//System.out.println(this.co);
				term.coeffs[j] = hello.coeffs[i] * this.coeffs[j];
				term.exp[j] = hello.exp[i] + this.exp[j];
			}
			temp = temp.add(term);
		}
		return temp;
	}

	public double evaluate(double x) {
		double result = 0;

		for (int i = 0; i < coeffs.length; i++) {
			result += coeffs[i]*Math.pow(x, exp[i]);
		}
		return result;
	}
	
	public boolean hasRoot(double x) {
		double result;
		result = evaluate(x);
		

		if (result == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public void saveToFile (String file) {
		try {
			File output = new File(file);
			output.createNewFile();

			int max = this.exp[0];
			for (int i = 0; i < this.exp.length; i++){
				if (this.exp[i] > max) max = this.exp[i];
			}

			String line = "";
			int prev = -1;
			int smallest;
			int pos = 0;
			for (int i = 0; i < this.exp.length; i++){
				smallest = max;
				for (int j = 0; j < this.exp.length; j++){
					if (this.exp[j] <= smallest && this.exp[j] > prev){
						smallest = this.exp[j];
						pos = j;
					}
				}
				prev = smallest;
				line = line + this.coeffs[pos];
				if (this.exp[pos] != 0) line = line + "x" +this.exp[pos];
			}

			FileWriter writer = new FileWriter(file);
			writer.write(line);
			writer.close();
			System.out.println("Aoccurred.");

		} catch (IOException e) {
			System.out.println("An error occurred.");
      		e.printStackTrace();
		}
	}

}