package fusion;


import java.awt.BorderLayout;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Extend a slider to give it a logarithmic scale
 * @author heddle
 *
 */
public class LogSlider extends JSlider {
	
	private int _minDecade;
	private int _maxDecade;
	
	private static int maxVal = 1000;
	
	public LogSlider(int minDecade, int maxDecade, double defaultVal) {
		super(0, maxVal);
		_minDecade = minDecade;
		_maxDecade = maxDecade;
		setActualValue(defaultVal);
		
		
		int numTick = maxVal/(_maxDecade -_minDecade);
		
		//custom labels
		Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
		for (int i = 0; i < numTick; i++) {
			double val = Math.pow(10, _minDecade+i);
			int iv = actualToSlider(val);
			String label = String.format("<html>10<SUP>%d</SUP>", _minDecade+i);
			labelTable.put(iv, new JLabel(label));
		}
		
		setLabelTable( labelTable );
		setMajorTickSpacing(numTick);

		setPaintTicks(true);
		setPaintLabels(true);
	}
	
	/**
	 * Set the slider based on the actual log scale value
	 * @param val the actual log scale value
	 */
	public void setActualValue(double val) {
		setValue(actualToSlider(val));
	}
	
	/**
	 * get the real, log scale value
	 * @return the real, log scale value
	 */
	public double value() {
		double exp = _minDecade + fract() * (_maxDecade - _minDecade);
		return Math.pow(10, exp);
	}
	
	//the fractional value
	private double fract() {
		double num = getValue() - getMinimum();
		double den = getMaximum() - getMinimum();
		return num/den;
	}
	
	//convert an actual value toa slider value
	private int actualToSlider(double v) {
		double f = Math.log10(v) - _minDecade;
		double del = _maxDecade - _minDecade;
		double fract = f/del;
		
		int sdel = (int)(fract*(getMaximum() - getMinimum()));
		return getMinimum() + sdel;
	}
	
	/**
	 * main program for testing
	 * @param arg command line arguments ignored
	 */
	public static void main(String arg[]) {
		TestFrame frame = new TestFrame("Test Log Slider", 0.6);
		
		LogSliderPanel sliderPanel = new LogSliderPanel("Log Slider", 1, 7, 1000000);
		
		ChangeListener cl = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				LogSlider source = (LogSlider)e.getSource();
				if (!source.getValueIsAdjusting()) {
					System.err.println("Slider value: " + source.getValue());
					System.err.println("Fractional value: " + source.fract());
					System.err.println("Actual value: " + source.value());
					System.err.println("Actual to slider: " + source.actualToSlider(source.value()));
				}
			}
			
		};
		
		sliderPanel.addChangeListener(cl);
		
		frame.add(sliderPanel, BorderLayout.NORTH);
		
		frame.open();
	}
}
