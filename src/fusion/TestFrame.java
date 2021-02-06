package fusion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Used to add a quick testing window
 * @author heddle
 *
 */
public class TestFrame extends JFrame {

	public TestFrame(String title, double fractionalSize) {
		
		setLayout(new BorderLayout(4, 4));
		
		// set up what to do if the window is closed
		WindowAdapter windowAdapter = new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent event) {
				System.exit(1);
			}
		};
		addWindowListener(windowAdapter);
		
		//set the fractional size
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		
		d.width = (int) (fractionalSize * d.width);
		d.height = (int) (fractionalSize * d.height);
		setSize(d);
	}
	
	public void open() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				setVisible(true);
				setLocationRelativeTo(null);
			}
		});		
	}
}
