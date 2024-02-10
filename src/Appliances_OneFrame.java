import java.util.ArrayList;
import java.util.Hashtable;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Appliances_OneFrame extends JFrame {

	private JPanel contentPane;
	private static Hashtable<String, Boolean> appliancesOff;
	private static boolean levelNotStarted;

	public static ArrayList<JButton> buttons;
	private static String chosenAppliance;
	private static JButton leftmost;
	private static JButton rightmost;
	private static JButton middle;

	public static ArrayList<JLabel> appStatuses;
	private static JLabel leftAppStatus;
	private static JLabel middleAppStatus;
	private static JLabel rightAppStatus;

	private static int levelsDone;
	private int allDone;

	private JLabel background;

	JButton endGame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Appliances_OneFrame frame = new Appliances_OneFrame(chosenAppliance, levelNotStarted);
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
	public Appliances_OneFrame(String chosenApp, boolean lvlNtStrtd) {
		chosenAppliance = chosenApp;
		levelNotStarted = lvlNtStrtd;

		if (levelNotStarted == true) {
			instructions();
		}

		if (levelsDone < 3) {
			initialize();
			updateTasks(levelsDone + 1);
			if (allDone < 3) {
				assignButtons(levelsDone + 1);
			}
		}

		endGame = new JButton("");
		endGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EndingScreen frame = new EndingScreen();
				frame.setVisible(true);
				dispose();
			}
		});

		endGame.setToolTipText("end game");
		endGame.setBounds(529, 121, 60, 60);
		endGame.setVisible(false);
		contentPane.add(endGame);
	}

	public void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 873, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		leftmost = new JButton("");
		leftmost.setToolTipText("1");
		leftmost.setEnabled(true);

		middle = new JButton("");
		middle.setToolTipText("2");
		middle.setEnabled(true);

		rightmost = new JButton("");
		rightmost.setToolTipText("3");
		rightmost.setEnabled(true);

		leftAppStatus = new JLabel("");
		leftAppStatus.setToolTipText("1");
		leftAppStatus.setBounds(0, 0, 873, 496);

		middleAppStatus = new JLabel("");
		middleAppStatus.setToolTipText("2");
		middleAppStatus.setBounds(0, 0, 873, 496);

		rightAppStatus = new JLabel("");
		rightAppStatus.setToolTipText("3");
		rightAppStatus.setBounds(0, 0, 873, 496);

		background = new JLabel("");
		background.setBounds(0, 0, 873, 496);

		// pair each appliance's JButton with a switch to say if their corresponding
		// task is done
		if (levelNotStarted == true) {
			levelsDone = 0;

			appliancesOff = new Hashtable<String, Boolean>();
			appliancesOff.put(leftmost.getToolTipText(), false);
			appliancesOff.put(middle.getToolTipText(), false);
			appliancesOff.put(rightmost.getToolTipText(), false);

			buttons = new ArrayList<JButton>();
			buttons.add(leftmost);
			buttons.add(middle);
			buttons.add(rightmost);

			appStatuses = new ArrayList<JLabel>();
			appStatuses.add(leftAppStatus);
			appStatuses.add(middleAppStatus);
			appStatuses.add(rightAppStatus);
		}
	}

	public void resetProgress() {

		appliancesOff.clear();
		buttons.clear();
		allDone = 0;

		chosenAppliance = null;
		appliancesOff.put(leftmost.getToolTipText(), false);
		appliancesOff.put(middle.getToolTipText(), false);
		appliancesOff.put(rightmost.getToolTipText(), false);

		buttons.add(leftmost);
		buttons.add(rightmost);
		buttons.add(middle);

	}

	public void updateTasks(int level) {

		// update chosen appliance to show its task is done
		for (int i = 0; i < buttons.size(); i++) {
			JButton bton = buttons.get(i);
			JLabel label = appStatuses.get(i);

			if (chosenAppliance != null) {
				if (bton.getToolTipText().equals(chosenAppliance)) {
					appliancesOff.put(chosenAppliance, true);
				}
			}

			if (appliancesOff.get(bton.getToolTipText()) == true) {
				bton.setEnabled(false); // user can't activate mini-game anymore
				try {
					label.setIcon(new ImageIcon(Appliances_OneFrame.class.getResource(
							"/Final_Images/TurnOffAppliances/" + label.getToolTipText() + "off" + level + ".png")));
				} catch (Exception e) {
					label.setIcon(null);
					// for the sink and sprinkler that don't need images when they're off since
					// those appliances are embedded in the background
				}
				allDone += 1;
			} else {
				bton.setEnabled(true);

				label.setIcon(new ImageIcon(Appliances_OneFrame.class.getResource(
						"/Final_Images/TurnOffAppliances/" + label.getToolTipText() + "on" + level + ".png")));
				// for the sink and sprinkler that don't need images when they're off since
				// those appliances are embedded in the background

			}

		}

		if (allDone == appliancesOff.size()) {
			levelsDone += 1;
			JOptionPane.showMessageDialog(null, "Congratulations! You finished level " + levelsDone + "!", "Hooray!",
					JOptionPane.INFORMATION_MESSAGE,
					new ImageIcon(Appliances_OneFrame.class.getResource("/Final_Images/Sprites/163turtle.png")));

			if (levelsDone >= 3) {
				EndingScreen frame = new EndingScreen();
				frame.setVisible(true);
				dispose();
			}

			resetProgress();
			updateTasks(levelsDone + 1);
		}
	}

	public void assignButtons(int level) {

		assignGames(level);

		for (int i = 0; i < buttons.size(); i++) {

			// give each button its corresponding task marker
			buttons.get(i).setIcon(new ImageIcon(
					Appliances_OneFrame.class.getResource("/Final_Images/60bubble_exclamation_mark.png")));
			buttons.get(i).setDisabledIcon(
					new ImageIcon(Appliances_OneFrame.class.getResource("/Final_Images/60bubble_heart.png")));

			repaint();

			// remove default fill and appearance of JButton
			buttons.get(i).setBorder(null);
			buttons.get(i).setContentAreaFilled(false);

			contentPane.add(buttons.get(i));
			repaint();
		}

		for (int i = 0; i < appStatuses.size(); i++) {
			contentPane.add(appStatuses.get(i));
			repaint();
		}

		contentPane.add(background);
		repaint();
	}

	public void assignGames(int lvl) {
		if (lvl == 1) {
			background.setIcon(
					new ImageIcon(Appliances_OneFrame.class.getResource("/Final_Images/Backgrounds/kitchen.PNG")));

			leftmost.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Time_Button_Press frame = new Time_Button_Press(leftmost.getToolTipText(), 3);
					frame.setVisible(true);
					dispose();
				}
			});
			leftmost.setBounds(121, 23, 60, 60);

			middle.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Button_Mash frame = new Button_Mash(middle.getToolTipText(), 10);
					frame.setVisible(true);
					dispose();
				}
			});
			middle.setBounds(404, 49, 60, 60);

			rightmost.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MemoryGame frame = new MemoryGame(rightmost.getToolTipText());
					frame.setVisible(true);
					dispose();
				}
			});
			rightmost.setBounds(725, 23, 60, 60);
		}

		if (lvl == 2) {
			background.setIcon(
					new ImageIcon(Appliances_OneFrame.class.getResource("/Final_Images/Backgrounds/livingRoom4.png")));

			leftmost.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Time_Button_Press frame = new Time_Button_Press(leftmost.getToolTipText(), 5);
					frame.setVisible(true);
					dispose();
				}
			});
			leftmost.setBounds(381, 0, 60, 60);

			middle.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DragDropGame frame = new DragDropGame(middle.getToolTipText());
					frame.setVisible(true);
					dispose();
				}
			});
			middle.setBounds(478, 181, 60, 60);
			middleAppStatus.setBounds(0, 25, 873, 496);

			rightmost.addActionListener(new ActionListener() { // light bulb
				public void actionPerformed(ActionEvent e) {
					Button_Mash frame = new Button_Mash(rightmost.getToolTipText(), 7);
					frame.setVisible(true);
					dispose();
				}
			});
			rightmost.setBounds(120, 25, 60, 60);

		}

		if (lvl == 3) {
			background.setIcon(
					new ImageIcon(Appliances_OneFrame.class.getResource("/Final_Images/Backgrounds/outdoors.PNG")));

			// Since only one final game appears in the last level, clear these data
			// containers
			// so that the win conditions and update task functions work
			appliancesOff.remove(middle.getToolTipText());
			appliancesOff.remove(rightmost.getToolTipText());

			buttons.remove(middle);
			buttons.remove(rightmost);

			appStatuses.remove(middleAppStatus);
			appStatuses.remove(rightAppStatus);

			leftmost.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					MemoryGame frame = new MemoryGame(leftmost.getToolTipText());
					frame.setVisible(true);
					dispose();
					endGame.setVisible(true);
				}
			});
			leftmost.setBounds(529, 306, 60, 60);
		}
	}

	public void instructions() {
		JOptionPane.showMessageDialog(null,
				"Look out for the cute bubbles with exclamation marks on the appliances that need to be turned off.",
				"Instructions #1", JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(Appliances_OneFrame.class.getResource("/Final_Images/Sprites/163turtle.png")));
		JOptionPane.showMessageDialog(null, "When you click on an appliance, you'll play a fun mini-game.",
				"Instructions #2", JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(Appliances_OneFrame.class.getResource("/Final_Images/Sprites/163turtle.png")));
		JOptionPane.showMessageDialog(null,
				"Don't worry, the instructions for each mini-game will pop up before they start.", "Instructions #3",
				JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(Appliances_OneFrame.class.getResource("/Final_Images/Sprites/163turtle.png")));
		JOptionPane.showMessageDialog(null,
				"Complete all the mini-games to clear this level and help reduce the amount of energy you use!",
				"Instructions #4", JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(Appliances_OneFrame.class.getResource("/Final_Images/Sprites/163turtle.png")));
		JOptionPane.showMessageDialog(null, "Click 'OK' or press the 'Enter' key to start/resume the game.",
				"END of Instructions", JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(Appliances_OneFrame.class.getResource("/Final_Images/Sprites/163turtle.png")));

	}

}
