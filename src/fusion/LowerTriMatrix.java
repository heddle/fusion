package fusion;

/**
 * This class implements a lower triangular matrix.
 * if it is declared with dimension N the legal values
 * are row: 1..N-1, col 1..N-1
 * 
 * if N is 5, valid row indices are 1..4 and valid col indices
 * are 0 to row -1
 * 
 * in general:
 *  ROW:  1..N-1
 *  COL:  0..ROW-1
 * 
 * 
 * col must be less than row
 * @author heddle
 *
 */
public class LowerTriMatrix {
	
	//dimension
	private int _n;
	private int _nm1;
	private int _nm2;

	//backing data
	private FArray[] _data;
	
	
	public LowerTriMatrix(int n) {
		_n = n;
		_nm1 = n - 1;
		_nm2 = n - 2;
		
		_data = new FArray[_nm1];
		
		int size = 1;
		for (int row = 0; row < _nm1; row++ ) {
			_data[row] = new FArray(size++);
		}
	}
	
	public void put(int row, int col, float val) {
		
		//treat as symmetric with zeros on diagonal
		
		if (row == col) {
			return;
		}
		
		if (col > row) {
			int temp = row;
			row = col;
			col = temp;
		}
		
		if ((row < 1) || (row > _nm1)) {
			return;
		}
		
		if (col >= row) {
			return;
		}
		
		
		_data[row-1].data[col] = val;
	}
	
	public float get(int row, int col) {
		
		//treat as symmetric with zeros on diagonal
		
		if (row == col) {
			return 0;
		}
		
		if (col > row) {
			int temp = row;
			row = col;
			col = temp;
		}
		
		if ((row < 1) || (row > _nm1)) {
			return Float.NaN;
		}
		
		if (col >= row) {
			return Float.NaN;
		}
		
		
		return _data[row-1].data[col];
		
	}
	
	//inner class
	class FArray {
	
		//backing data
		public float data[];
		
		public FArray(int n) {
			data = new float[n];
		}
	}
	
	
	public void print() {
		for (int row = 0; row < _n; row++) {
			System.err.println();
			
			for (int col = 0; col < _n; col++) {
				System.err.print(valStr(get(row, col)));
			}
		}
	}
	
	private String valStr(float v) {
		return String.format("  %-7.2f", v);
	}
	
	public static void main(String arg[]) {
		int n = 5;
		LowerTriMatrix matrix = new LowerTriMatrix(n);
		
		for (int row = 0; row < n; row++) {
			for (int col = 0; col < row; col++) {
				matrix.put(row, col, row+col);
			}
		}
		
		matrix.print();
	}
}
