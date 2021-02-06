package fusion;

import java.awt.Color;
import java.awt.Font;
import java.util.Collection;
import java.util.Random;

import cnuphys.splot.fit.FitType;
import cnuphys.splot.pdata.DataColumn;
import cnuphys.splot.pdata.DataColumnType;
import cnuphys.splot.pdata.DataSet;
import cnuphys.splot.pdata.DataSetException;
import cnuphys.splot.pdata.DataSetType;
import cnuphys.splot.pdata.HistoData;
import cnuphys.splot.plot.Environment;
import cnuphys.splot.plot.PlotCanvas;
import cnuphys.splot.plot.PlotGrid;
import cnuphys.splot.plot.PlotParameters;
import cnuphys.splot.style.IStyled;
import cnuphys.splot.style.Styled;
import cnuphys.splot.style.SymbolType;

/**
 * Used for testing the distribution
 * 
 * @author heddle
 *
 */
public class MaxwellPlotGrid extends PlotGrid {

	// number of plots
	private static int NUMPLOTS = 2;

	// total number of columns on the grid
	private static int NUMCOLS = 3;

	// temperature
	private static double _T = 10000;

	// particle
	private static Particle _particle = Particle.H1;

	// get the fonts
	private Font _titleFont = Environment.getInstance().getCommonFont(16);
	private Font _statusFont = Environment.getInstance().getCommonFont(10);
	private Font _axesFont = Environment.getInstance().getCommonFont(14);
	private Font _legendFont = Environment.getInstance().getCommonFont(14);

	// the plot canvases
	private static PlotCanvas[] _canvases = new PlotCanvas[NUMPLOTS];

	/**
	 * Create a grid of plots for testing
	 */
	public MaxwellPlotGrid() {
		super(1 + ((NUMPLOTS - 1) / NUMCOLS), NUMCOLS);
		createPlots();
		SetData(Particle.H1, 1.0e4);

	}

	// set the data for a particle and a temp
	public void SetData(Particle particle, double T) {
		_particle = particle;
		_T = T;
		clearData();
		
		//reset histo data
		_canvases[1].setDataSet(getHistoDataSet());

		for (int index = 0; index < NUMPLOTS; index++) {
			setPlotTitle(index);
			fillData(index);
			_canvases[index].setWorldSystem();
		}
		
		//some extra text
		double vmp = Maxwell.mostProbable(particle.m, T);
		double vavg = Maxwell.average(particle.m, T);
		double vrms = Maxwell.rms(particle.m, T);
		
		String smp = String.format("Vmp = %-10.4e m/s", vmp);
		String savg = String.format("Vavg = %-10.4e m/s", vavg);
		String srms = String.format("Vrms = %-10.4e m/s", vrms);
		_canvases[0].getParameters().setExtraStrings(smp, savg, srms);

	}

	// clear the data from all the plots
	private void clearData() {
		for (int index = 0; index < NUMPLOTS; index++) {
			DataSet ds = _canvases[index].getDataSet();
			ds.clear();
		}
	}

	// create the plots
	private void createPlots() {
		for (int index = 0; index < NUMPLOTS; index++) {
			try {
				_canvases[index] = new PlotCanvas(createDataSet(index), getPlotTitle(index), getXAxisLabel(index),
						getYAxisLabel(index));

				setPreferences(index);
				addPlotCanvas(_canvases[index]);
			} catch (DataSetException e) {
				e.printStackTrace();
				return;
			}

		}
	}

	// fill the plot data based on index
	private void fillData(int index) {

		PlotCanvas canvas = _canvases[index];

		DataSet ds = canvas.getDataSet();

		switch (index) {
		case 0:

			int numPoints = 200;

			double speed[] = new double[numPoints];
			double maxwell[] = new double[numPoints];
			double normal[] = new double[numPoints];

			double mFactor = Maxwell.getMFactor(_particle.m, _T, speed, maxwell, normal);
			System.err.println("MFactor: " + mFactor);

			for (int i = 0; i < numPoints; i++) {
				try {
					ds.add(speed[i], maxwell[i], normal[i], mFactor * normal[i]);
				} catch (DataSetException e) {
					e.printStackTrace();
				}
			}

			break;

		case 1:
			mFactor = Maxwell.getMFactor(_particle.m, _T);
			numPoints =  100000;
			Random rand = new Random();
			
			for (int i = 0; i < numPoints; i++) {
				double v = Maxwell.randomSpeed(rand, _particle.m, _T, mFactor);
				try {
					ds.add(v);
				} catch (DataSetException e) {
					e.printStackTrace();
				}
			}
			break;
		}
	}

	// set the preferences
	public void setPreferences(int index) {

		PlotCanvas canvas = _canvases[index];
		DataSet ds = canvas.getDataSet();
		PlotParameters params = canvas.getParameters();

		params.setTitleFont(_titleFont);
		params.setAxesFont(_axesFont);
		params.setStatusFont(_statusFont);
		params.setStatusFont(_legendFont);
		params.setLegendLineLength(40);

		switch (index) {
		case 0:
			params.setExtraDrawing(true);
			Collection<DataColumn> ycols = ds.getAllColumnsByType(DataColumnType.Y);

			int i = 0;
			for (DataColumn dc : ycols) {
				dc.getFit().setFitType(FitType.CONNECT);

				Styled style = dc.getStyle();
				style.setSymbolType(SymbolType.NOSYMBOL);
				style.setFitLineWidth(3);

				if (i == 0) {
					style.setFitLineColor(Color.black);
				} else if (i == 1) {
					style.setFitLineColor(Color.red);
				} else if (i == 2) {
					style.setFitLineColor(Color.green);
				}

				i++;
			}

			break;

		case 1:
			break;
		}
	}

	private IStyled getStyled(int index, int curve) {
		PlotCanvas canvas = _canvases[index];
		return canvas.getDataSet().getCurveStyle(curve);
	}

	// get the dataset based in the plot index
	private DataSet createDataSet(int index) throws DataSetException {
		switch (index) {
		case 0:
			return new DataSet(DataSetType.XYY, getColumnNames(index));

		case 1:
			return getHistoDataSet();

		}

		return null;
	}
	
	private DataSet getHistoDataSet() {
		double vmp = Maxwell.mostProbable(_particle.m, _T);
		HistoData h1 = new HistoData("Speeds", 0, 3 * vmp, 200);
		try {
			DataSet ds = new DataSet(h1);
			
			
			ds.getCurveStyle(0).setFillColor(new Color(196, 196, 196, 64));
			ds.getCurveStyle(0).setBorderColor(Color.black);
			ds.getCurveStyle(0).setBorderColor(Color.black);
			ds.getCurve(0).getFit().setFitType(FitType.NOLINE);

			
			return ds;
			
		} catch (DataSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	// get the column names based on plot index
	private String[] getColumnNames(int index) {
		switch (index) {
		case 0:
			String names0[] = { "X", "Maxwell", "Normal", "M*Normal" };
			return names0;

		case 1:
			break;

		}

		return null;
	}

	// get the x axis label based on plot index
	private String getXAxisLabel(int index) {
		switch (index) {
		case 0:
			return "<html>v (m/s)";

		case 1:
			return "<html>v (m/s)";

		}

		return null;
	}

	// get the y axis label based on index
	private String getYAxisLabel(int index) {
		switch (index) {
		case 0:
			return "<html>f<SUB>v</SUB>(v)";

		case 1:
			return "Counts";

		}

		return null;
	}

	// get the plot title based on index
	private String getPlotTitle(int index) {
		switch (index) {
		case 0:
			return "<html>Maxwell Boltzman Distribution";

		case 1:
			return "<html>Speed Frequency Histogram";

		}

		return null;
	}

	// set the plot title based on index
	private void setPlotTitle(int index) {

		PlotCanvas canvas = _canvases[index];
		PlotParameters params = canvas.getParameters();

		switch (index) {
		case 0:
			params.setPlotTitle("<html>Maxwell Boltzman Distribution  " + _particle.symbol + "  T = " + _T + "K");
			break;

		case 1:
			params.setPlotTitle("<html>Speed Frequency Histogram  " + _particle.symbol + "  T = " + _T + "K");
			break;

		}

	}

}
