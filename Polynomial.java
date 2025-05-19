

class Polynomial {
	public double[] coeffs;

	
	public Polynomial() {
		this.coeffs = new double[] {0};
	}
	
	public Polynomial(double[] hello) {
		this.coeffs = new double[hello.length];
		for (int i = 0; i < hello.length; i++) {
			coeffs[i] = hello[i];
		}
	}
	
	public Polynomial add(Polynomial hello) {
		if (this.coeffs.length >= hello.coeffs.length) {
			for (int i = 0; i < hello.coeffs.length; i++) {
				this.coeffs[i] += hello.coeffs[i];
			}
		}
		else {
			double[] temp = this.coeffs;
			
			this.coeffs = new double[hello.coeffs.length];
			
			for (int i = 0; i < temp.length; i++) {
				this.coeffs[i] = temp[i] + hello.coeffs[i];
			}
			for (int i = temp.length; i < hello.coeffs.length; i++) {
				this.coeffs[i] = hello.coeffs[i];
			}
		}
		
		return this;
	}
	
	public double evaluate(double x) {
		double result = 0;

		for (int i = 0; i < coeffs.length; i++) {
			result += coeffs[i]*Math.pow(x, i);
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
}