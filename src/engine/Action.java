package engine;

/**
 * This class represents actions made by cars.
 * 
 * @author Peter
 * 
 */
public class Action {
	private char car_id;
	private int move;

	/**
	 * Constructor of the class
	 * 
	 * @param car_id - A,B,C,D,E....
	 * @param move - amount of slots to go
	 */
	public Action(char car_id, int move) {
		setCarId(car_id);
		setMove(move);
	}

	/* Getters and setters */
	public char getCarId() {
		return car_id;
	}

	public int getMove() {
		return move;
	}

	public void setCarId(char car_id) {
		this.car_id = car_id;
	}

	public void setMove(int move) {
		this.move = move;
	}

}