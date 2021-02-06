package fusion;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class SimPanel extends JPanel {
	
	//simulation graphical component
	private SimComponent _simComponent;

	/**
	 * Will hold the simulation display and all control widgets
	 */
	public SimPanel() {
		setLayout(new BorderLayout(4, 4));
		addCenter();
	}
	
	public void addCenter() {
		_simComponent = new SimComponent();
		add(_simComponent, BorderLayout.CENTER);
	}

}
