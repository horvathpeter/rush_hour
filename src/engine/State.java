package engine;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextPane;

import engine.Car.Orientation;

/**
 * This class represents state of the crossroads, mainly it's functional side.
 * @author Peter
 *
 */
public class State {
	final int size = 8;
	StateData state_data;
	private int number_of_cars;
	private ArrayList<Car> cars;
	private Position exit;

	public State(int dimensions) {
		this.state_data = new StateData(dimensions, dimensions);
		cars = new ArrayList<Car>();
		exit = new Position();
	}

	protected void CreateStateFromFile(String file) {

		Reader reader = new Reader();
		reader.loadProblemFromFile(this, file);
	}

	public Position getExitPos() {
		return exit;
	}

	public StateData getStateData() {
		StateData g = state_data.fillCarData(cars);
		return g;
	}

	public void setStateData(StateData statedata) {
		this.state_data = statedata;
	}

	public void printState(JTextPane textPane) {
		state_data.printGrid(cars, textPane);
	}
	
	public List<Car> getCarList() {
		return cars;
	}


	public void setNumberOfCars(int value) {
		this.number_of_cars = value;
	}

	public void printCarsInfo(JTextPane textPane) {
		textPane.setText(textPane.getText() + "CARS INFO\n");
		for (int i = 0; i < number_of_cars; i++) {
			Car car = getCarList().get(i);
			textPane.setText(textPane.getText() + "ID: " + car.getCarId() + " Length: "
							+ car.getLength() + " Orientation: "
							+ car.getOrientation() + "\n");
		}
	}

	/**
	 * Function to find out if state given as parameter is final state - if car
	 * A has head or other part in exit bucket ('=')
	 * 
	 * @param state
	 * @return true - if state is final or false if state is not final
	 */
	protected boolean isFinalState(State state) {
		Car car = state.getCarList().get(0);
		if (car.getHead().getColumn() == exit.getColumn()
				|| car.getHead().getColumn() + (car.getLength() - 1) >= exit
						.getColumn()) {
			return true;
		}

		return false;
	}

	/**
	 * Function to fill the list given as parameter with all possible actions
	 * for this state. Horizontal car can go to left or to right, vertical can
	 * go up or down.
	 * 
	 * @return List
	 */

	protected List<Action> getPossibleActions() {

		StateData g = state_data.fillCarData(cars);
		List<Action> actions = new ArrayList<Action>();
		for (int i = 0; i < cars.size(); i++) {
			Car car = cars.get(i);
			int free_slots_before = 0;
			int free_slots_after = 0;
			int j;

			if (car.getOrientation() == Orientation.HORIZONTAL) {

				// get free slots in front of the car
				for (j = car.getHead().getColumn() - 1; j >= 0; j--) {
					// System.out.println(car.getCarid() + " Sending "
					// + car.getHead().getRow() + " " + j);
					if (g.slotFree(car.getHead().getRow(), j)
							|| (i == 0 && g.slotExit(car.getHead().getRow(), j))) {
						free_slots_before++;
					} else
						break;
				}
				// get free slots behind the car
				for (j = car.getHead().getColumn() + car.getLength(); j <= size; j++) {
					// System.out.println(car.getCarid() + " Sending "
					// + car.getHead().getRow() + " " + j);
					if (g.slotFree(car.getHead().getRow(), j)
							|| (i == 0 && g.slotExit(car.getHead().getRow(), j))) {
						free_slots_after++;
					} else
						break;
				}
			}

			else if (car.getOrientation() == Orientation.VERTICAL) {
				// get free slots before the car
				for (j = car.getHead().getRow() - 1; j >= 0; j--) {
					// System.out.println(car.getCarid() + " Sending " + j + " "
					// + car.getHead().getCol());
					if (g.slotFree(j, car.getHead().getColumn())) {
						free_slots_before++;
					} else
						break;
				}

				// get free slots behind the car
				for (j = car.getHead().getRow() + car.getLength(); j <= size; j++) {
					// System.out.println(car.getCarid() + " Sending " + j + " "
					// + car.getHead().getCol());
					if (g.slotFree(j, car.getHead().getColumn())) {
						free_slots_after++;
					} else
						break;
				}
			}

			for (int k = 1; k <= free_slots_before; k++) {
				Action a = new Action(car.getCarId(), -k);
				actions.add(a);
			}

			for (int k = 1; k <= free_slots_after; k++) {
				Action a = new Action(car.getCarId(), k);
				actions.add(a);
			}
		}
		return actions;
	}

	protected void executeMove(Action action) {
		int id_inList = (int) (action.getCarId() - 'A');
		cars.get(id_inList).makeMove(action.getMove());

	}

	protected void backMove(Action action) {
		int id_inList = (int) (action.getCarId() - 'A');
		cars.get(id_inList).makeMove(-action.getMove());
	}


	/**
	 * Generate ID for this combination
	 * 
	 * @return st - unique ID
	 */
	public int getStateID() {
		StateData d = state_data.fillCarData(cars);
		String st = String.valueOf(d.getData());
		return st.hashCode();
	}

}
