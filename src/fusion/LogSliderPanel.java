package fusion;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class LogSliderPanel extends JPanel implements ChangeListener {
	
	//underlying slider
	private LogSlider _slider;
	
	//base title
	private String _title;
	
	//title label
	private JLabel _titleLabel;
	
	public LogSliderPanel(String title, int minDecade, int maxDecade, double defaultVal) {
		setLayout(new BorderLayout(4, 4));
		
		_title = title;
		
		_titleLabel = new JLabel("     " + _title + "     ");
		_titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(_titleLabel, BorderLayout.NORTH);
		
		_slider =new LogSlider(minDecade, maxDecade, defaultVal);
		_slider.addChangeListener(this);
		add(_slider, BorderLayout.CENTER);
		
		
		setBorder(BorderFactory.createEtchedBorder());
		
		fixTitle();
	}
	
	@Override
	public Insets getInsets() {
		Insets def = super.getInsets();
		return new Insets(def.top + 6, def.left + 2, def.bottom + 2, def.right + 2);
	}
	
	private void fixTitle() {
		String valStr = String.format("  %7.3f", value());
		_titleLabel.setText(_title + valStr);
	}
		
	/**
	 * get the real, log scale value
	 * @return the real, log scale value
	 */
	public double value() {
		return _slider.value();
	}
	
	/**
	 * Set the slider based on the actual log scale value
	 * @param val the actual log scale value
	 */
	public void setActualValue(double val) {
		_slider.setActualValue(val);
	}
	
	/**
	 * Listen for changes on the slider
	 * @param cl the change listener
	 */
	public void addChangeListener(ChangeListener cl) {
		_slider.addChangeListener(cl);
	}

	/**
	 * Get the actual slider
	 * @return
	 */
	public LogSlider getSlider() {
		return _slider;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		fixTitle();
	}
}
