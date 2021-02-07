package fusion;

public class Simulation {

	// temperature in Kelvin
	private static double _T = 300;

	// density in particles per m^3
	private static double _Density = 2.0e13;

	// number of particles
	private static int _N = 10000;

	/**
	 * Set the current simulation temperature
	 * 
	 * @param t the simulation temperature in Kelvin
	 */
	public static void setTemperature(double t) {
		_T = t;
		MaxwellPlotGrid.instance().setData();
	}

	/**
	 * Get the current simulation temperature
	 * 
	 * @return the current simulation temperature in K
	 */
	public static double getTemperature() {
		return _T;
	}

	/**
	 * Set the current simulation density
	 * 
	 * @param rho the simulation density in particles/m^3
	 */
	public static void setDensity(double rho) {
		_Density = rho;
		MaxwellPlotGrid.instance().setData();
	}

	/**
	 * Get the current simulation density
	 * 
	 * @return the current simulation density in particles/m^3
	 */
	public static double getDensity() {
		return _Density;
	}

	/**
	 * Set the number of particles
	 * 
	 * @param n the number of particles
	 */
	public static void setNumParticles(int n) {
		_N = n;
	}

	/**
	 * Get the number of particles
	 * 
	 * @return the number of particles
	 */
	public static int getNumParticles() {
		return _N;
	}

}
