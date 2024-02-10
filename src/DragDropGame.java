import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class DragDropGame extends JFrame implements ActionListener {

	private JPanel contentPane;
	JFrame frame = new JFrame();
	JTextField endText = new JTextField();

	JButton closeButton = new JButton();

	private static String chosenAppliance;
	private JButton goBackOrExit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DragDropGame frame = new DragDropGame(chosenAppliance);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DragDropGame(String chosenApp) {
		chosenAppliance = chosenApp;

		instructions();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 873, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		endText.setBounds(175, 68, 525, 50);
		endText.setBackground(new Color(25, 25, 25));
		endText.setForeground(new Color(243, 255, 245));
		endText.setFont(new Font("Arial", Font.BOLD, 30));
		endText.setBorder(BorderFactory.createBevelBorder(1));
		endText.setHorizontalAlignment(JTextField.CENTER);
		endText.setEditable(false);
		endText.setVisible(false);
		endText.setText("Congratulations!");
		contentPane.add(endText);
		
		goBackOrExit = new JButton("Go Back to House");
		goBackOrExit.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		goBackOrExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null,
						"Are you sure you would like to go back to the house? You will lose your progress and score.", "Go back?",
						JOptionPane.WARNING_MESSAGE);

				if (confirm == JOptionPane.YES_OPTION) {
					Appliances_OneFrame frame = new Appliances_OneFrame(null, false);
					frame.setVisible(true);
					dispose();
				}
			}
		});
		goBackOrExit.setForeground(Color.decode("#7B4E3F"));
		goBackOrExit.setBounds(0, 0, 208, 39);
		contentPane.add(goBackOrExit);

		closeButton.setBounds(319, 296, 250, 75);
		closeButton.setFont(new Font("Arial", Font.BOLD, 15));
		closeButton.setFocusable(false);
		closeButton.addActionListener(this);
		closeButton.setVisible(false);
		closeButton.setText("click to continue!");
		contentPane.add(closeButton);

		ImagePanel imagePanel = new ImagePanel(
				new ImageIcon(DragDropGame.class.getResource("Final_Images/TurnOffAppliances/plugDragNDrop100.PNG")));
		contentPane.add(imagePanel);
		imagePanel.setBounds(0, 60, 873, 496);

		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Appliances_OneFrame frame = new Appliances_OneFrame(chosenAppliance, false);
				frame.setVisible(true);
				dispose();
			}
		});
	}

	// called at the end of the game, closes the image panel object, and sets the
	// contents
	// of the content pane visible
	// end screen is visible
	public void showContentPane() {
		getContentPane().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public void instructions() {
		JOptionPane.showMessageDialog(null, "Drag the plug to the green goal!", "Instructions #1",
				JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(Appliances_OneFrame.class.getResource("/Final_Images/Sprites/163turtle.png")));
		JOptionPane.showMessageDialog(null,
				"Be careful of the red walls! If the plug touches the wall, it'll reset back to the start.",
				"Instructions #2", JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(Appliances_OneFrame.class.getResource("/Final_Images/Sprites/163turtle.png")));
		JOptionPane.showMessageDialog(null, "Score 10 times to win the game! Good luck!", "Instructions #3",
				JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(Appliances_OneFrame.class.getResource("/Final_Images/Sprites/163turtle.png")));
		JOptionPane.showMessageDialog(null, "Click 'OK' or press the 'Enter' key to start/resume the game.",
				"END of Instructions", JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(Appliances_OneFrame.class.getResource("/Final_Images/Sprites/163turtle.png")));
	}
}
