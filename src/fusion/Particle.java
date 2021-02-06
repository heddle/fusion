package fusion;

public class Particle {
	
	public static final Particle H1 = new Particle(1, 1, Constants.Mp, "H<SUB>1</SUB>");
	public static final Particle H2 = new Particle(1, 2, Constants.Md, "H<SUB>2</SUB>");
	
	/** the Z symbol, e.g. "H" for Z = 1, "He for Z = 2, etc" */ 
	public String symbol;
	
	/** the charge, e.g. 1 for Hydrogen */
	public int Z;
	

	/** the atomic number, e.g. 1 for proton, 2 for dueterium */
	public int A; 
	
	/** the mass in GeV */
	public double m;
	

	public Particle(int Z, int A, double m, String symbol) {
		this.Z = Z;
		this.A = A;
		this.m = m;
		this.symbol = symbol;
	}
	
}
