import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class CSVdata {
	/***
	 * A class to read/write numerical CSV files and allow easy access of data.
	 * 
	 * @author schi631
	 *
	 */

	private double[][] data;
	private String[] columnNames;
	private int numRows;
	private String filePathToCSV;
	
	public CSVdata(double[][] data, String[] columnNames) {
		super();
		this.data = data;
		this.columnNames = columnNames;
		
	}
	
	public CSVdata(String filepath, String[] columnNames, int startRow){
		this.filePathToCSV = filepath;
		
		String dataString = readFileAsString(filepath);
		String[] lines = dataString.split("\n");
		
		int n = lines.length - startRow;
		this.numRows = n;
		int numColumns = columnNames.length;
		
		this.columnNames = columnNames;
		
		this.data = new double[n][numColumns];
		for(int i = 0; i < lines.length - startRow; i++){
			String line = lines[startRow + 1];
			String[] coords = line.split(",");
			
			for(int j = 0; j < numColumns; j++){
				if(coords[j].endsWith("#")) coords[j] = coords[j].substring(0, coords[j].length()-1);
				double val = Double.parseDouble(coords[j]);
				data[i][j] = val;
			}
		}
		
		
	}

		
		
	

	public String readFileAsString(String filepath) {
		StringBuilder output = new StringBuilder();
		
		try(Scanner scanner = new Scanner(new File(filepath))){
				
				while(scanner.hasNext()){
					java.lang.String line = scanner.nextLine();
					output.append(line + System.getProperty("line.separator"));
				}
				
		}catch(IOException e){
			e.printStackTrace();
		}return output.toString();
	}

	public static CSVdata readCSVFile(String filename, int numLinesToIgnore, String[] columnNames) {
		return null;
	}

	/***
	 * Returns a new CSVData object for a file ignoring lines at the top. It
	 * uses the first row as the column names. All other data is stored as
	 * doubles
	 * 
	 * @param filename
	 *            the file to read
	 * @param numLinesToIgnore
	 *            number of lines at the top to ignore
	 * @return a CSVData object for that file.
	 */
	public static CSVdata readCSVFile(String filename, int numLinesToIgnore) {
		return null;
	}

	/***
	 * returns an individual row
	 * 
	 * @param row
	 *            index of row to return
	 * @return the row we're returning
	 */
	public double[] getIndividualRow(int row) {
		return this.data[row];
	}

	/***
	 * returns an individual column
	 * 
	 * @param col
	 *            index of column to return
	 * @return the column we're returning
	 */
	public double[] getIndividualCol(int col) {
		double[] colArr = new double[data.length];
		for (int r = 0; r < data.length; r++) {
			colArr[r] = data[r][col];
		}
		return colArr;
	}

	/***
	 * returns an individual column
	 * 
	 * @param name
	 *            name of column to return
	 * @return the column we're returning
	 */
	public double[] getIndividualCol(String name) {
		for (int c = 0; c < columnNames.length; c++) {
			if (columnNames[c] == name)
				return getIndividualCol(c);
		}
		return null;
	}

	/***
	 * returns multiple rows
	 * 
	 * @param startRow
	 *            index of the row to start at
	 * @param endRow
	 *            index of the row to end at
	 * @return the multiple rows we're returning
	 */
	public double[][] getMultipleRows(int startRow, int endRow) {
		double[][] multiRow = new double[endRow - startRow + 1][data[0].length];
		int rowNum = 0;
		for (int r = startRow; r <= endRow; r++) {
			for (int c = 0; c < data[0].length; c++) {
				multiRow[rowNum][c] = data[r][c];
			}
			rowNum++;
		}
		return multiRow;
	}

	/***
	 * return multiple columns
	 * 
	 * @param startCol
	 *            index of column to start at
	 * @param endCol
	 *            index of row to end at
	 * @return the multiple columns we're returning
	 */
	public double[][] getMultipleCols(int startCol, int endCol) {
		double[][] multiCol = new double[endCol - startCol+1][data.length];
		int rowNum = 0;
		for (int c = startCol; c <= endCol; c++) {
			for (int r = 0; r < data.length; r++) {
				multiCol[rowNum][r] = data[r][c];
			}
			rowNum++;
		}
		return multiCol;
	}

	/***
	 * return multiple columns
	 * 
	 * @param colIndexes
	 *            array of the column indexes we want
	 * @return the multiple columns we're returning
	 */
	public double[][] getMultipleCols(int[] colIndexes) {
		double[][] multiCol = new double[colIndexes.length][data.length];
		double[] getCol = new double[data.length];
		int row = 0;
		for (int i = 0; i < colIndexes.length; i++) {
			for (int c = 0; c < data.length; c++) {
				getCol = getIndividualCol(colIndexes[i]);
				multiCol[row][c] = getCol[c];
			}
			row++;
		}
		return multiCol;

	}

	/***
	 * returns multiple rows
	 * 
	 * @param rowIndexes
	 *            array of row indexes we want
	 * @return the multiple rows we're returning
	 */

	public double[][] getMultipleRows(int[] rowIndexes) {
		double[][] multiRow = new double[rowIndexes.length][data[0].length];
		int row = 0;
		for (int r = 0; r < rowIndexes.length; r++) {
			multiRow[row] = data[rowIndexes[r]];
			row++;
		}
		return multiRow;
	}

	/***
	 * returns multiple columns
	 * 
	 * @param colName
	 *            array of column names from the columns we want
	 * @return the multiple columns we're returning
	 */
	public double[][] getMultipleCols(String[] colName) {
		double[][] multiCols = new double[colName.length][data.length];
		int row = 0;
		for (int i = 0; i < colName.length; i++) {
			for (int n = 0; n < columnNames.length; n++) {
				if (colName[i] == columnNames[n]) {
					multiCols[row] = getIndividualCol(n);
					row++;
				}
			}
		}
		return multiCols;

	}

	/***
	 * sets a row that we want to change
	 * 
	 * @param rowIndex
	 *            the row index we want to change
	 * @param vals
	 *            an array of the values we want to substitute for that row
	 */
	public void setRow(int rowIndex, double[] vals) {
		data[rowIndex] = vals;
	}

	/***
	 * sets a column that we want to change
	 * 
	 * @param colIndex
	 *            the column index we want to change
	 * @param vals
	 *            an array of the values we want to substitute for that column
	 */
	public void setCol(int colIndex, double[] vals) {
		if (vals.length == data.length) {
			for (int r = 0; r < data.length; r++) {
				data[r][colIndex] = vals[r];
			}
		}
	}

	/***
	 * sets an individual value that we want to change
	 * 
	 * @param col
	 *            column index of value that we want to change
	 * @param row
	 *            row index of value that we want to change
	 * @param newValue
	 *            the value that we want to substitute
	 */
	public void setValue(int row, int col, double newValue) {
		data[row][col] = newValue;
	}

	/***
	 * returns the name of a title column
	 * 
	 * @param col
	 *            column that we want the title of
	 * @return title of column
	 */
	public String getTitleCol(int col) {
		return columnNames[col];
	}

	/***
	 * saves current state of the object back into a CSV file
	 * 
	 * @param fileName
	 *            name of the file we want to save
	 */
	public static void saveToFile(String fileName) {

	}

}
