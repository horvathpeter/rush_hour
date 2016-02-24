package engine;

/**
 * Class represent position of an element - mostly car's head
 * 
 * @author Peter
 * 
 */
public class Position {
	private int row;
	private int column;

	Position() {
		row = -1;
		column = -1;
	}

	Position(int row, int column) {
		setPosition(row, column);
	}

	public void setPosition(int row, int column) {
		setRow(row);
		setColumn(column);
	}
	
	/* Getters and setters */
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
}
