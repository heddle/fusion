package fusion;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class PlotPanel extends JPanel {

	// plot grid
	private MaxwellPlotGrid _plotGrid;

	/**
	 * Will hold the simulation display and all control widgets
	 */
	public PlotPanel() {
		setLayout(new BorderLayout(4, 4));
		addCenter();
	}

	// add the plotgrid to the center
	private void addCenter() {
		_plotGrid = MaxwellPlotGrid.instance();
		add(_plotGrid, BorderLayout.CENTER);
	}

}
