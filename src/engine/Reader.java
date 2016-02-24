package engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class is used to read initial state of crossroads from text file
 * @author Peter
 *
 */
public class Reader {
	final int size = 8; // rush hour puzzle is 8 x 8 grid

	public Reader() {

	}

	public void loadProblemFromFile(State state, String file) {
		try {

			BufferedReader reader = new BufferedReader(new FileReader(file));

			int m_number_of_cars = Integer.valueOf(reader.readLine())
					.intValue(); // information on the first line of the file

			state.setNumberOfCars(m_number_of_cars);
			System.out.println("There are " + m_number_of_cars + " cars");

			for (int i = 0; i < m_number_of_cars; i++) {
				char id = (char) ('A' + i);
				state.getCarList().add(new Car(id));
			}

			StateData temp = new StateData(size, size);

			for (int row = 0; row < size; row++) {
				for (int column = 0; column < size; column++) {

					char readed_char = (char) reader.read();

					if (readed_char == '*'
							|| readed_char == '.') { // border or free slot

						temp.setIntoGrid(row, column, readed_char);
					}

					else if (readed_char >= 'A'
							&& readed_char <= 'Z') { // car

					/*	System.out.println(readed_char + "-"
								+ 'A' + "="
								+ (readed_char - 'A') + " ");*/

						state.getCarList()
								.get(readed_char - 'A')
								.setOrientation(row, column);

					} else if (readed_char == '=') { // exit
						state.getExitPos().setPosition(row, column);
						temp.setIntoGrid(row, column, readed_char);
					}
				}

				char n = (char) reader.read();
				if (n == '\r') {
					n = (char) reader.read();
				}
			}

			reader.close();
			state.setStateData(temp);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
