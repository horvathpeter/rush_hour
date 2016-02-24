package engine;

/**
 * Class represents a Car in rush hour puzzle. Car has 4 attributes - ID, LENGTH, ORIENTATION and POSITION of it's head. Head of the car
 * is the most upper left corner of the car.
 * @author Peter
 *
 */
public class Car {
	public enum Orientation {
		HORIZONTAL, VERTICAL
	};

	private char id;
	private int length;
	private Orientation orientation;
	private Position head;	

	/**
	 * Constructor
	 * @param id - char obtained from the source file
	 */
	public Car(char id) {
		setCarId(id);
		setLength(0);
		this.orientation = Orientation.HORIZONTAL; //default orientation is set to horizontal, it will change when initial state is read
		this.head = new Position(); //default position is set to [-1][-1]
	}
	
	/* getters and setters */
	public char getCarId() {
		return id;
	}

	public void setCarId(char value) {
		this.id = value;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int value) {
		this.length = value;
	}

	public Position getHead() {
		return head;
	}

	public void setHead(int row, int column) {
		head.setPosition(row, column);
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(int row, int column) {
		if (head.getRow() == -1) { //if car's head was not located yet
			head.setRow(row);
			head.setColumn(column);
		} else { //car's head was located, just length + 1
			if (row - head.getRow() == 0) { //if head and new row are in one horizontal line
				orientation = Orientation.HORIZONTAL;
				length = (column - head.getColumn()) + 1;
			} else if (column - head.getColumn() == 0) { //if head and new column are in one vertical line
				orientation = Orientation.VERTICAL;
				length = (row - head.getRow()) + 1;
			}
		}
	}
	
	/**
	 * Function to make a move with a car, first function get an orientation of the car and then move the head of the car forward
	 * if value > 0 or backwards if value < 0 - position of car's head is changed
	 * @param value - number of buckets to move
	 */
	public void makeMove(int value) {

		if (orientation == Orientation.HORIZONTAL) {
			head.setColumn(head.getColumn() + value);
		} else if (orientation == Orientation.VERTICAL) {
			head.setRow(head.getRow() + value);
		}
	}
}
