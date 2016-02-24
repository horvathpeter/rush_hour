package engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JTextPane;

import engine.Car.Orientation;

/**
 * Solver contains function which triggers the iterative deepening
 * depth-first search (IDDFS) algorithm by calling a method start.
 * @author Peter
 *
 */
public class Solver {
	
	public void start(String file, JTextPane textPane){
		State initial_state = new State(8);
		initial_state.CreateStateFromFile(file);
		initial_state.printCarsInfo(textPane);
		initial_state.printState(textPane);
		boolean success = false;
		int max_depth = 0; // border in the depth first search, increasing in a loop
		Solver solver = new Solver();

		List<Action> path = new ArrayList<Action>();

		long start = System.currentTimeMillis();
		while (success == false) {
			// System.out.println("Starting again !");
			success = solver.IDDFS(initial_state, path, max_depth, textPane);
			Runner.mapa.clear();
			max_depth++;
		}
		

		long end = System.currentTimeMillis();
		Collections.reverse(path);
		if (success == true)
			textPane.setText(textPane.getText() + "Solution founded !\nTime: " + (end - start)
					+ " ms\n" + "Number of steps: " + path.size() + "\n");
		
		textPane.setText(textPane.getText() + "Depth needed: " + max_depth + "\n");

		for (int i = 0; i < path.size(); i++) {
			String st = String.valueOf(path.get(i).getCarId());
			if (initial_state.getCarList()
					.get(path.get(i).getCarId() - 'A').getOrientation() == Orientation.HORIZONTAL) {
				if (path.get(i).getMove() > 0) {
					st += " doprava o ";
				} else {
					st += " dolava o ";
				}
			} else {
				if (path.get(i).getMove() > 0)
					st += " dole o ";
				else
					st += " hore o ";
			}
			st += Math.abs(path.get(i).getMove());
			textPane.setText(textPane.getText() + (i+1) + ". " + st + "\n");
		}
	}

	boolean IDDFS(State state, List<Action> path, int max_depth, JTextPane textPane) {

	//	textPane.setText(textPane.getText() + "Depth is now "+max_depth + "\n");
		Integer str = state.getStateID();
		//textPane.setText(textPane.getText() + "This state has id "+ str + "\n");
		if (Runner.mapa.containsKey(str)) {
		//	textPane.setText(textPane.getText() + "This state was explored already\n");
			return false;
		} else
			Runner.mapa.put(str, "explored");

		if (state.isFinalState(state)) {
			return true;
		} else if (max_depth == 0) {
			return false;
		}

		List<Action> possible_actions = new ArrayList<Action>();
		possible_actions = state.getPossibleActions();
		//state.printState(textPane);

		/*
		 * for(int i =0; i<possible_actions.size(); i++){
		 * textPane.setText(textPane.getText() + possible_actions.get(i).getCarId() + " can make "
		 * + possible_actions.get(i).getMove() + "\n"); }
		 */

		boolean success = false;
		for (int i = 0; i < possible_actions.size(); i++) {
			state.executeMove(possible_actions.get(i));
			success = IDDFS(state, path, max_depth - 1, textPane);
			state.backMove(possible_actions.get(i));
			if (success) {
				path.add(possible_actions.get(i));
			//	state.printState(textPane);
				break;
			}
		}

		return success;
	}
}
