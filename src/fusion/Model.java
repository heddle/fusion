package fusion;

import java.util.Random;

/**
 * This class holds all the data. For efficiency we use 
 * parallel arrays rather than arrays of bjects
 * @author heddle
 *
 */
public class Model {
	
	/** what type of particle */
	public static byte type[];
	
	/** x coordinates */
	public static float x[];
	
	/** y coordinates */
	public static float y[];

	/** square of distance between particles */
	public static LowerTriMatrix distanceSq;
	
	/** force between the particles */
	public static LowerTriMatrix force;
	
	/** the size of the box */
	public static float size;
	
	/**
	 * Reset the model
	 * @param n the number of particles
	 * @param boxSize the size of a side of the box (m)
	 */
	public static void reset(int n, float boxSize) {
		
		size = boxSize;
		type = new byte[n];
		x = new float[n];
		y = new float[n];
		
		distanceSq = new LowerTriMatrix(n);
		force = new LowerTriMatrix(n);
		
		Random rand = new Random();
		
		for (int i = 0; i < n; i++) {
			type[i] = Particle.H_1;
			
			x[i] = boxSize * rand.nextFloat();
			y[i] = boxSize * rand.nextFloat();
		}
	}
	
	
	
}
