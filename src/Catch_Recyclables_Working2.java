import javax.swing.JFrame;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Color;

public class Catch_Recyclables_Working2 extends JFrame implements MouseMotionListener {
	private JPanel contentPane;
	private Timer timer;
	private static double seconds = 3, milliseconds = 10;

	private int score = 0;
	private int pointsAdded = 0;

	private JLabel recyclingBin;
	private JLabel background;

	public java.util.List<Falling_Item_2> entityList;
	private JLabel catchMessage;

	private Font font;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Catch_Recyclables_Working2 frame = new Catch_Recyclables_Working2();
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
	public Catch_Recyclables_Working2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 873, 496);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		addMouseMotionListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Winkle-Regular.ttf")));
		} catch (IOException | FontFormatException e) {

		}

		try {
			font = Font.createFont(Font.TRUETYPE_FONT,
					new File(System.getProperty("user.dir") + "//src//" + "Winkle-Regular.ttf"));

		} catch (IOException | FontFormatException e) {

		}

		instructions();

		recyclingBin = new JLabel("");
		recyclingBin.setVerticalAlignment(SwingConstants.BOTTOM);
		recyclingBin.setHorizontalAlignment(SwingConstants.CENTER);
		recyclingBin.setIcon(new ImageIcon(
				Catch_Recyclables_Working2.class.getResource("/Final_Images/Catch_Recyclables/recyclingBin.png")));
		recyclingBin.setBounds(287, 269, 127, 190);
		contentPane.add(recyclingBin);

		JLabel scoreCounter = new JLabel("Score: 0");
		scoreCounter.setHorizontalAlignment(SwingConstants.CENTER);
		scoreCounter.setForeground(Color.decode("#7B4E3F"));
		scoreCounter.setFont(font.deriveFont(45f));
		scoreCounter.setBounds(0, 0, 859, 42);
		contentPane.add(scoreCounter);

		catchMessage = new JLabel("");
		catchMessage.setHorizontalAlignment(SwingConstants.CENTER);
		catchMessage.setFont(font.deriveFont(45f));
		catchMessage.setBounds(0, 250, 859, 42);
		contentPane.add(catchMessage);

		background = new JLabel("");
		background.setIcon(new ImageIcon(
				Catch_Recyclables_Working2.class.getResource("/Final_Images/Backgrounds/bg_recyclables.jpg")));
		background.setBounds(0, 0, 873, 496);
		contentPane.add(background);

		entityList = new java.util.ArrayList<Falling_Item_2>();

		// populate EntityList
		replaceObject();
		replaceObject();
		replaceObject();

		// Timer
		ActionListener timerListener = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				if (score >= 2000) { // in case two recyclables are caught at the same time
					JOptionPane.showMessageDialog(null, "Congratulations!", "Hooray!", JOptionPane.INFORMATION_MESSAGE,
							new ImageIcon(NPC_Walk.class.getResource("/Final_Images/Sprites/163goose.png")));
					timer.stop();
					NPC_Walk frame = new NPC_Walk(3);
					frame.setVisible(true);
					dispose();
				}

				// go through each element in entitylist to see if it hit the bin.
				// if it hit the bin, destroy it and create a new object
				for (int i = 0; i < entityList.size(); i++) {

					// check if item fell into recycle bin
					if (entityList.get(i).getJLabel().getY() >= recyclingBin.getY() - 5
							&& entityList.get(i).getJLabel().getY() <= recyclingBin.getY() + 5) {
						if (entityList.get(i).getJLabel().getX() >= recyclingBin.getX() - 50
								&& entityList.get(i).getJLabel().getX() <= recyclingBin.getX() + 50) {
							if (entityList.get(i).getRecyclable() == true) {
								score += 100;
								pointsAdded += 100;
								scoreCounter.setText("Score: " + score);
								catchMessage.setText("Recyclable! +" + pointsAdded); // when you catch multiple in a
																						// row, it shows how many points
																						// you got from that streak
								catchMessage.setForeground(new Color(0, 100, 0)); // Dark green
							} else {
								pointsAdded = 0;
								catchMessage.setText("NOT recyclable!");
								catchMessage.setForeground(Color.RED);
							}
							Timer pause = new Timer(2000, new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									catchMessage.setText("");
									pointsAdded = 0;
								}
							});
							pause.setRepeats(false);
							pause.start();

							entityList.get(i).getJLabel().setVisible(false);
							entityList.remove(i);
							replaceObject();
						}
					}

					// if object hits ground, reset
					if (entityList.get(i).getJLabel().getY() >= getHeight()
							- entityList.get(i).getJLabel().getHeight()) {
						// should there be a penalty if you don't catch the recyclable?
						entityList.get(i).getJLabel().setVisible(false);
						entityList.remove(i);
						replaceObject();

						// THIS would ADD 3 items every time the 3 objects on the screen hit the ground
						// WHICH WE DONT WANT
//						entityList.add(new Falling_Item_2());
//						entityList.get(entityList.size() - 1).createJLabel();
//						contentPane.add(entityList.get(entityList.size() - 1).getJLabel());
//						System.out.println(entityList.size());
					}
				}

				++milliseconds;
				if (milliseconds >= 10) {
					--seconds;
					milliseconds = 0;
				}

				if (seconds <= 0) {
					for (int i = 0; i < entityList.size(); i++) {
						JLabel label = entityList.get(i).getJLabel();
						// objects fall faster as score increases to certain values (increasing
						// difficulty)
						if (score < 500) {
							label.setLocation(label.getX(), label.getY() + 10);
						}
						if (score >= 500 && score < 1200) {
							label.setLocation(label.getX(), label.getY() + 12);
						}
						if (score >= 1200) {
							label.setLocation(label.getX(), label.getY() + 16);
						}

						// to make the objects not fall at same time, maybe
						// get each object's timer
					}

					milliseconds = 10;
					seconds = 3;
				}

			}
		};

		timer = new Timer(1, timerListener);
		timer.start();

		JButton instructionsButton = new JButton("Instructions");
		instructionsButton.setForeground(Color.decode("#7B4E3F"));
		instructionsButton.setFont(font.deriveFont(17f));
		instructionsButton.setFocusable(false);
		instructionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timer.stop();
				instructions();
				timer.start();
			}
		});
		instructionsButton.setBounds(0, 17, 133, 23);
		contentPane.add(instructionsButton);

	}

	public void replaceObject() {
		// Trying to make sure that we don't have ALL recyclables or ALL garbage at one
		// time

		Falling_Item_2 item = new Falling_Item_2();
		entityList.add(item);

		if (entityList.size() != 0) {
			int count = 0;
			for (int i = 0; i < entityList.size(); i++) {
				boolean marker = entityList.get(i).getRecyclable();
				if (marker == true) { // if item is recyclable, add 1
					count += 1;
				}

			}

			if (count == entityList.size()) { // all current objects are recyclable
				// (since we add 1 for every object that is recyclable in the loop above,
				// the count of recyclable items would be the same as the size of the list)
				entityList.get(entityList.size() - 1).setRecyclable(false);
			}
			if (count == 0) { // all current objects are garbage
				entityList.get(entityList.size() - 1).setRecyclable(true);
			}
		}
		contentPane.remove(background);
		repaint();
		contentPane.add(entityList.get(entityList.size() - 1).getJLabel());
		contentPane.add(background);
		repaint();

	}

	// Recycling bin does not go out of screen since it cannot follow the mouse when
	// it goes outside the frame
	@Override
	public void mouseDragged(MouseEvent e) {
		if (e.getX() >= 0 && e.getX() < (getWidth() - recyclingBin.getWidth())) { // keep bin within bounds of frame
																					// even if you move mouse out of
																					// frame
			recyclingBin.setBounds(e.getX(), recyclingBin.getY(), recyclingBin.getWidth(), recyclingBin.getHeight());
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (e.getX() >= 0 && e.getX() < (getWidth() - recyclingBin.getWidth())) {
			recyclingBin.setBounds(e.getX(), recyclingBin.getY(), recyclingBin.getWidth(), recyclingBin.getHeight());
		}
	}

	public void instructions() {
		JOptionPane.showMessageDialog(null,
				"Oh, and one more thing - use your mouse to move the recycling bin left or right.", "Instructions #1",
				JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(NPC_Walk.class.getResource("/Final_Images/Sprites/163goose.png")));
		JOptionPane.showMessageDialog(null,
				"The objects will start falling faster when you catch enough recyclables so watch out for that!",
				"Instructions #2", JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(NPC_Walk.class.getResource("/Final_Images/Sprites/163goose.png")));
		JOptionPane.showMessageDialog(null,
				"Each recyclable you catch is worth 100 points. Get 2000 points (20 recyclables) to complete the level!",
				"Instructions #3", JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(NPC_Walk.class.getResource("/Final_Images/Sprites/163goose.png")));
		JOptionPane.showMessageDialog(null, "Click 'OK' or press the 'Enter' key to start/resume the game.",
				"END of Instructions", JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(NPC_Walk.class.getResource("/Final_Images/Sprites/163goose.png")));
	}
}
