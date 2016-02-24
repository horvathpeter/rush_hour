package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import engine.Solver;

public class MainFrame {

	public JFrame frame;
	private JFileChooser f;
	private FileNameExtensionFilter filter;
	private Solver solver = new Solver();
	private JTextPane textPane;
	private File selectedFile = null;
	private JScrollPane scrollPane;


	/**
	 * Create the application.
	 */
	public MainFrame() {
		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			System.err.println("setLookAndFeel failed");
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 553, 402);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		final JButton btnReadFromFile = new JButton("Read from file");
		btnReadFromFile.setBounds(10, 11, 112, 23);
		frame.getContentPane().add(btnReadFromFile);

		btnReadFromFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				f = new JFileChooser();
				f.setAcceptAllFileFilterUsed(false);
				filter = new FileNameExtensionFilter("TEXT files", "txt");
				f.addChoosableFileFilter(filter);
				int selection = f.showOpenDialog(btnReadFromFile);
				if (selection == JFileChooser.APPROVE_OPTION) {
					selectedFile = f.getSelectedFile();
					textPane.setText("");
					textPane.setText(textPane.getText() + "File was selected: "+selectedFile.getPath()+ "\nClick SOLVE! to start "
							+ "searching\n");
				} else if (selection == JFileChooser.CANCEL_OPTION) {
					JOptionPane.showMessageDialog(null,
							"Zrusili ste vyberanie", "Denied",
							JOptionPane.WARNING_MESSAGE);

				}
			}
		});

		JButton btnSolve = new JButton("Solve !");
		btnSolve.setBounds(10, 45, 112, 23);
		frame.getContentPane().add(btnSolve);
		
		btnSolve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(selectedFile != null){
				solver.start(selectedFile.getPath(), textPane);
				}
				else{
					textPane.setText("You need to select file first. Click on Read from file\n");
				}
			}
		});
		
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(132, 11, 395, 342);
		frame.getContentPane().add(scrollPane);
		
		textPane = new JTextPane();
		textPane.setEditable(false);		
		textPane.setFont(new Font("Consolas", Font.PLAIN, 12));
		scrollPane.setViewportView(textPane);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(10, 79, 112, 23);
		frame.getContentPane().add(btnExit);
		
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

	}

}
