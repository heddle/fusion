package fusion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * static methods for dealing with the Maxwell Boltzmann speed distribution
 * 
 * all units are SI
 * 
 * @author heddle
 *
 */
public class Maxwell {
	
	//frame for testing
	private static JFrame _frame;
	
	//plots for testing
	private static MaxwellPlotGrid _plotGrid;
	

	/**
	 * Get the most probable spped
	 * @param m the mass of the particle in kg
	 * @param T the temperature in Kelvin
	 * @return the most probable speed in m/s
	 */
	public static double mostProbable(double m, double T) {
		double kTonM = Constants.k * T / m;
		return Math.sqrt(2*kTonM);
	}
	
	/**
	 * Get the average speed
	 * @param m the mass of the particle in kg
	 * @param T the temperature in Kelvin
	 * @return the average speed in m/s
	 */
	public static double average(double m, double T) {
		return 2*mostProbable(m, T)/Constants.ROOTPI;
	}
	
	/**
	 * Get the root mean square speed
	 * @param m the mass of the particle in kg
	 * @param T the temperature in Kelvin
	 * @return the root mean square speed in m/s
	 */
	public static double rms(double m, double T) {
		return Math.sqrt(1.5) * mostProbable(m, T);
	}
	
	/**
	 * Get the value of the Maxwell Boltzmann distribution
	 * @param m the mass of the particle in kg
	 * @param T the temperature in Kelvin
	 * @param v them speed in m/s
	 * @return the value of the Maxwell Boltzmann distribution
	 */
	public static double distribution(double m, double T, double v) {
		double mOnKT = m/(Constants.k * T);
		double vsq = v*v;
		return Math.pow(mOnKT, 1.5)*Constants.ROOT2ONPI*vsq*Math.exp(-vsq*mOnKT/2);
	}
	
	/**
	 * Get a random speed drawn from the Maxwell Boltzmann distribution. Uses a rejection algorithm.
	 * @param m the mass of the particle in kg
	 * @param T the temperature in Kelvin
	 * @return random speed distributed according to the Maxwell Boltzmann distribution 
	 */
	public static double randomSpeed(Random rand, double m, double T, double mFactor) {
		double vmp = Maxwell.mostProbable(m, T);		
		double mu = vmp;
		double sigma = 0.7 * vmp;

		while (true) {
			double v = Normal.nextNormal(sigma, mu, rand);
			if (v > 0) {
				double u = rand.nextDouble();
				double nx = Normal.distribution(sigma, mu, v);
				double mx = Maxwell.distribution(m, T, v);
				if (u < (mx / (mFactor * nx))) {
					return v;
				}
			}
		}

	}
	
	/**
	 * Computes the MFactor needed for the rejection algorithm to make sure
	 * the normal distribution is always bigger than the maxwell
	 * @param m the mass in kG
	 * @param T the temperature in K
	 * @return the M factor for the rejection algorithm
	 */
	public static double getMFactor(double m, double T) {
		double vmp = Maxwell.mostProbable(m, T);		
		double mu = vmp;
		double sigma = 0.7*vmp;
		
		int numPoints = 200;
		double delV = 3*vmp/numPoints;
		
		double mFactor = 0;

		for (int i = 0; i < numPoints; i++) {
			double speed = i*delV;
		    double maxwell = Maxwell.distribution(m, T, speed);
			double normal = Normal.distribution(sigma, mu, speed);
			
			mFactor = Math.max(mFactor, maxwell/normal);
		}

		return mFactor;
	}
	
	/**
	 * Computes the MFactor needed for the rejection algorithm to make sure
	 * the normal distribution is always bigger than the maxwell
	 * @param m the mass in kG
	 * @param T the temperature in K
	 * @param speed will hold an array of speeds for plotting
	 * @param maxwell will hold an maxwell distribution for plotting
	 * @param normal will hold an normal distribution  (not scaled by M) for plotting
	 * @return the M factor for the rejection algorithm
	 */
	public static double getMFactor(double m, double T, double speed[], double maxwell[], double normal[]) {
		int numPoints = speed.length;
		double vmp = Maxwell.mostProbable(m, T);		
		double mu = vmp;
		double sigma = 0.7*vmp;
		double delV = 3*vmp/numPoints;
		
		double mFactor = 0;

		for (int i = 0; i < numPoints; i++) {
			speed[i] = i*delV;
		    maxwell[i] = Maxwell.distribution(m, T, speed[i]);
			normal[i] = Normal.distribution(sigma, mu, speed[i]);
			
			mFactor = Math.max(mFactor, maxwell[i]/normal[i]);
		}

		return mFactor;
	}

	
	//create a frame for testing, make it a fraction of the screen
	private static JFrame makeFrame(double fractionalSize) {
		final JFrame frame = new JFrame("Maxwell Boltzmann Test");

		// set up what to do if the window is closed
		WindowAdapter windowAdapter = new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent event) {
				System.exit(1);
			}
		};

		frame.addWindowListener(windowAdapter);

		frame.setLayout(new BorderLayout());

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		
		d.width = (int) (fractionalSize * d.width);
		d.height = (int) (fractionalSize * d.height);
		frame.setSize(d);

		
		return frame;
	}
	
	// make the plot grid 
	private static MaxwellPlotGrid makePlotGrid(JFrame frame) {
		MaxwellPlotGrid grid = new MaxwellPlotGrid();
		frame.add(grid, BorderLayout.CENTER);
		return grid;
	}

	/**
	 * Main program for testing.
	 * @param arg command line arguments (ignored)
	 */
	public static void main(String arg[]) {
		_frame = makeFrame(0.85);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_plotGrid = makePlotGrid(_frame);
				_frame.setVisible(true);
				_frame.setLocationRelativeTo(null);
			}
		});		
	}
}
