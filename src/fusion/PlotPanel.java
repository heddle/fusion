package fusion;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PlotPanel extends JPanel implements ChangeListener {
	
	// plot grid
	private MaxwellPlotGrid _plotGrid;
	
	//temp slider panel
	private LogSliderPanel _tempSliderPanel;

	/**
	 * Will hold the simulation display and all control widgets
	 */
	public PlotPanel() {
		setLayout(new BorderLayout(4, 4));
		addCenter();
		
		addNorth();
	}
	
	private void addNorth() {
		_tempSliderPanel = new LogSliderPanel("Distribution Temperature (K)", 2, 7, _plotGrid.getTemperature());
		_tempSliderPanel.addChangeListener(this);
		add(_tempSliderPanel, BorderLayout.NORTH);
	}
	
	//add the plotgrid to the center
	private void addCenter() {
		_plotGrid = new MaxwellPlotGrid();
		add(_plotGrid, BorderLayout.CENTER);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		LogSlider source = (LogSlider)e.getSource();
		if (!source.getValueIsAdjusting()) {
			_plotGrid.setTemperature(source.value());
		}
	}
}
