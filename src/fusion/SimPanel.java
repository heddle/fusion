package fusion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import bCNU3D.Panel3D;
import bCNU3D.PointSetPanel3D;
import item3D.Axes3D;
import item3D.Cube;

public class SimPanel extends JPanel implements ChangeListener {

	// simulation temp slider panel
	private LogSliderPanel _tempSliderPanel;

	// density slider panel
	private LogSliderPanel _densSliderPanel;

	// number of particles
	private LogSliderPanel _numSliderPanel;

	// 3D panel
	private PointSetPanel3D _panel3D;

	/**
	 * Will hold the simulation display and all control widgets
	 */
	public SimPanel() {
		setLayout(new BorderLayout(4, 4));
		addCenter();
		addNorth();
	}

	/**
	 * Get the 3D panel
	 * 
	 * @return the 3D panel
	 */
	public PointSetPanel3D get3DPanel() {
		return _panel3D;
	}

	public void addNorth() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1, 2, -1));

		_tempSliderPanel = new LogSliderPanel("Simulation Temperature (K)", 2, 8, Simulation.getTemperature());
		_densSliderPanel = new LogSliderPanel("<html>Simulation Density (particles/m<SUP>3</SUP>)", 13, 33,
				Simulation.getDensity());
		_numSliderPanel = new LogSliderPanel("<html>Number of Particles", 2, 6, Simulation.getNumParticles(), true);

		_tempSliderPanel.addChangeListener(this);
		_densSliderPanel.addChangeListener(this);
		_numSliderPanel.addChangeListener(this);

		panel.add(_tempSliderPanel);
		panel.add(_densSliderPanel);
		panel.add(_numSliderPanel);

		add(panel, BorderLayout.NORTH);
	}

	public void addCenter() {
//		_simComponent = new SimComponent();
//		add(_simComponent, BorderLayout.CENTER);

		_panel3D = createPanel3D();
		add(_panel3D, BorderLayout.CENTER);
	}

	private static PointSetPanel3D createPanel3D() {

		final float xymax = 1f;
		final float zmax = 1f;
		final float zmin = -1f;
		final float xdist = 0f;
		final float ydist = 0f;
		final float zdist = -3.2f;

		final float thetax = 15f;
		final float thetay = -15f;
		final float thetaz = 0f;

		PointSetPanel3D p3d = new PointSetPanel3D(thetax, thetay, thetaz, xdist, ydist, zdist) {
			@Override
			public void createInitialItems() {

				String labels[] = { "X", "Y", "Z" };
				Axes3D axes = new Axes3D(this, -xymax, xymax, -xymax, xymax, zmin, zmax, labels, Color.darkGray, 2f, 2,
						2, 2, Color.black, Color.blue, new Font("SansSerif", Font.PLAIN, 14), 0);
				addItem(axes);

				makeCube(this);
			}

			/**
			 * This gets the z step used by the mouse and key adapters, to see how fast we
			 * move in or in in response to mouse wheel or up/down arrows. It should be
			 * overridden to give something sensible. like the scale/100;
			 * 
			 * @return the z step (changes to zDist) for moving in and out
			 */
			@Override
			public float getZStep() {
				return (zmax - zmin) / 50f;
			}

		};

		return p3d;
	}

	private static Cube makeCube(Panel3D p3D) {
		float xc = 0;
		float yc = 0;
		float zc = 0;

		Color c = new Color(80, 80, 80, 8);

		Cube cube = new Cube(p3D, xc, yc, zc, 2, c);
		p3D.addItem(cube);
		return cube;

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		LogSlider source = (LogSlider) e.getSource();
		if (!source.getValueIsAdjusting()) {

			if (source == _tempSliderPanel.getSlider()) {
				Simulation.setTemperature(source.value());
			} else if (source == _densSliderPanel.getSlider()) {
				Simulation.setDensity(source.value());
			} else if (source == _numSliderPanel.getSlider()) {
				Simulation.setNumParticles(source.intValue());
			}

		}
	}

}
