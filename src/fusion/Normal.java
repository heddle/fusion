package fusion;

import java.util.Random;

/**
 * Methods related to a normal distribution.
 * @author heddle
 *
 */
public class Normal {
	
	/**
	 * The normal distribution
	 * @param sigma the standard deviation
	 * @param mu the mean
	 * @param x the variable
	 * @return the distribuiton value
	 */
	public static double distribution(double sigma, double mu, double x) {
		double fact = (x - mu)/sigma;
		double exp = -0.5*fact*fact;
		return Math.exp(exp)/(sigma*Constants.ROOT2PI);
	}
	
	
	
	/**
	 * Generate a sample for the normal distribution
	 * @param sigma the standard deviation of the distribution
	 * @param mu the mean of the distribution
	 * @param rand the random number generator
	 * @return a random sample
	 */
	public static double nextNormal(double sigma, double mu, Random rand) {
		return rand.nextGaussian()*sigma + mu;
	}
}

