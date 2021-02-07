package fusion;

import javax.swing.SwingUtilities;

public class Fusion {

	/**
	 * Main program for the application.
	 * 
	 * @param arg command line arguments (ignored)
	 */
	public static void main(String arg[]) {
		MainFrame frame = new MainFrame(0.85);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
			}
		});
	}
}
