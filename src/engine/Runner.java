package engine;

import java.util.HashMap;
import gui.MainFrame;

/**
 * Class with static main functions, creates an instance of main frame window.
 * Also HashMap is here, where are stored already visited combinations so they aren't solved another time. 
 * @author Peter
 * 
 */
public class Runner {

	public static HashMap<Integer, String> mapa = new HashMap<Integer, String>();

	public static void main(String[] args) {		
		MainFrame start_window = new MainFrame();
		start_window.frame.setVisible(true);		
	}
}

