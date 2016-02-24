package engine;

import java.util.ArrayList;

import javax.swing.JTextPane;

/**
 * This class represents data side of the crossroads - in 1D array, major row
 * order
 * 
 * @author Peter
 * 
 */
public class StateData {
	private char grid_data[];
	private int grid_width;
	private int grid_height;
	private int grid_size;

	public StateData(int width, int height) {
		setGridWidth(width);
		setGridHeight(height);
		setSize(width * height);
		grid_data = new char[grid_size];

		for (int i = 0; i < grid_size; i++) {
			grid_data[i] = '.';
		}
	}

	public int getGridHeight() {
		return grid_height;
	}

	public void setGridHeight(int height) {
		this.grid_height = height;
	}

	public int getGridWidth() {
		return grid_width;
	}

	public void setGridWidth(int width) {
		this.grid_width = width;
	}

	public char[] getData() {
		return grid_data;
	}

	public int getSize() {
		return grid_size;
	}

	public void setSize(int size) {
		this.grid_size = size;
	}

	public boolean slotFree(int row, int column) {
		return (getFromGrid(row, column) == '.');
	}

	public boolean slotExit(int row, int column) {
		return (getFromGrid(row, column) == '=');
	}

	public void setIntoGrid(int row, int column, char value) { // major row
																// order
		grid_data[grid_width * row + column] = value;
	}

	public void setIntoGrid(int i, char value) { // direct
		grid_data[i] = value;
	}

	public char getFromGrid(int row, int column) {
		return grid_data[grid_width * row + column];
	}

	public void printGrid(ArrayList<Car> cars, JTextPane textPane) {

		StateData temp = fillCarData(cars);

		textPane.setText(textPane.getText() + "\n");
		for (int i = 0; i < temp.getGridHeight(); i++) {
			for (int j = 0; j < temp.getGridWidth(); j++) {
				textPane.setText(textPane.getText() + temp.getFromGrid(i, j));
			}
			textPane.setText(textPane.getText() + "\n");
		}

	}

	public StateData fillCarData(ArrayList<Car> cars) {
		StateData temp = new StateData(grid_width, grid_height);

		for (int i = 0; i < grid_size; i++) {
			temp.setIntoGrid(i, grid_data[i]);
		}

		for (int i = 0; i < cars.size(); i++) {
			Car c = cars.get(i);
			for (int j = 0; j < c.getLength(); j++) {
				int car_row = c.getHead().getRow();
				int car_column = c.getHead().getColumn();
				if (c.getOrientation() == Car.Orientation.VERTICAL) {
					car_row += j;
				} else if (c.getOrientation() == Car.Orientation.HORIZONTAL) {
					car_column += j;
				}
				temp.setIntoGrid(car_row, car_column, c.getCarId());
			}
		}
		return temp;
	}
}
