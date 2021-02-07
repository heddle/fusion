package fusion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame {

	// main tabbed pane
	private JTabbedPane _tabbedPane;

	// plot panel
	private PlotPanel _plotPanel;

	// simulation panel
	private SimPanel _simPanel;

	/**
	 * Create the main frame with the given fractional size of the screen
	 * 
	 * @param fractionalSize
	 */
	public MainFrame(double fractionalSize) {
		super("Fusion Simulation");
		setLayout(new BorderLayout(4, 4));
		addTabbedPane();

		// set up what to do if the window is closed
		WindowAdapter windowAdapter = new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent event) {
				System.exit(1);
			}
		};
		addWindowListener(windowAdapter);

		// set the fractional size
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

		d.width = (int) (fractionalSize * d.width);
		d.height = (int) (fractionalSize * d.height);
		setSize(d);
	}

	// make the plot panel
	private void addPlotPanel() {
		_plotPanel = new PlotPanel();
		_tabbedPane.add("Plots", _plotPanel);
	}

	// add the simulation panel
	private void makeSimPanel() {
		_simPanel = new SimPanel();
		_tabbedPane.add("Simulation", _simPanel);
	}

	// add the tabbed pane to the center
	private void addTabbedPane() {
		_tabbedPane = new JTabbedPane();

		// add the sim panel
		makeSimPanel();

		// add the plot panel
		addPlotPanel();

		add(_tabbedPane, BorderLayout.CENTER);
	}

}
