import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Color;
import javax.swing.SwingConstants;

public class Time_Button_Press extends JFrame implements KeyListener {

	private JPanel contentPane;

	private Timer timer;
	private boolean timerStarted; // to prevent key input from being accepted when arrow hasn't moved
	private double seconds = 5, milliseconds = 1000;

	private JLabel arrow;
	private JLabel greenZone;
	private JLabel redZone;

	private static String appliance;
	private static int speed;

	private Timer pause;
	private JLabel readyGo;

	private JButton goBackOrExit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Time_Button_Press frame = new Time_Button_Press(appliance, speed);
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
	public Time_Button_Press(String chosenApp, int spd) {
		appliance = chosenApp;
		speed = spd;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 876, 508);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		greenZone = new JLabel("");
		greenZone.setIcon(new ImageIcon(Time_Button_Press.class.getResource("/Final_Images/1solidgreen.jpg")));
		greenZone.setBounds(410, 384, 17, 48);
		contentPane.add(greenZone);

		redZone = new JLabel("");
		redZone.setIcon(new ImageIcon(Time_Button_Press.class.getResource("/Final_Images/plain_red.jpg")));
		redZone.setBounds(315, 384, 209, 48);
		contentPane.add(redZone);

		arrow = new JLabel("");
		arrow.setIcon(new ImageIcon(Time_Button_Press.class
				.getResource("/Final_Images/TurnOffAppliances/Black_triangle_EVEN_smaller.png")));
		arrow.setBounds(287, 319, 67, 59);
		contentPane.add(arrow);

		JLabel lblPressTheSpacebar = new JLabel("Press the spacebar when the arrow is inside the green zone");
		lblPressTheSpacebar.setHorizontalAlignment(SwingConstants.CENTER);
		lblPressTheSpacebar.setForeground(Color.RED);
		lblPressTheSpacebar.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblPressTheSpacebar.setBounds(0, 40, 862, 85);
		contentPane.add(lblPressTheSpacebar);

		readyGo = new JLabel("");
		readyGo.setHorizontalAlignment(SwingConstants.CENTER);
		readyGo.setForeground(Color.RED);
		readyGo.setFont(new Font("Tahoma", Font.BOLD, 40));
		readyGo.setBounds(0, 222, 862, 85);
		contentPane.add(readyGo);

		// Timer
		ActionListener timerListener = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				if (arrow.getX() + (arrow.getWidth() / 2) >= (redZone.getX() + redZone.getWidth())) {
					timer.stop();
					JOptionPane.showMessageDialog(null, "You didn't press the space bar at all. Try again.", "OOPS!",
							JOptionPane.WARNING_MESSAGE);

					reset();
				}

				++milliseconds;
				if (milliseconds >= 5) {
					--seconds;
					milliseconds = 0;
				}

				if (seconds <= 0) {
					arrow.setLocation(arrow.getX() + speed, arrow.getY());
					milliseconds = 1000;
					seconds = 0.5;
				}
			}
		};

		timer = new Timer(10, timerListener);
		readyGo.setText("Ready?");

		delayTimer();

		goBackOrExit = new JButton("Go Back to House");
		goBackOrExit.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		goBackOrExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timer.stop();
				JOptionPane.getRootFrame().dispose();

				int confirm = JOptionPane.showConfirmDialog(null,
						"Are you sure you would like to go back to the house?", "Go back?",
						JOptionPane.WARNING_MESSAGE);

				if (confirm == JOptionPane.YES_OPTION) {
					Appliances_OneFrame frame = new Appliances_OneFrame(null, false);
					frame.setVisible(true);
					dispose();
				} else {
					goBackOrExit.setSelected(false);
					goBackOrExit.setFocusable(false);
					reset();
					delayTimer();
				}
			}
		});
		goBackOrExit.setForeground(Color.decode("#7B4E3F"));
		goBackOrExit.setBounds(0, 0, 208, 39);
		contentPane.add(goBackOrExit);
	}

	public void delayTimer() {
		pause = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				readyGo.setForeground(new Color(0, 100, 0));
				readyGo.setText("Go!");
				timer.start();
				timerStarted = true;
			}
		});
		pause.setRepeats(false);
		pause.start();
	}

	public void reset() {
		arrow.setLocation(287, 319);
		readyGo.setForeground(Color.RED);
		readyGo.setText("Ready?");
		timerStarted = false;

		delayTimer();
	}

	public void keyReleased(KeyEvent event) {
	}

	public void keyTyped(KeyEvent event) {

	}

	public void keyPressed(KeyEvent event) {

		if (event.getKeyCode() == event.VK_SPACE && timerStarted == true) { // to prevent key input from being accepted
																			// when arrow hasn't moved
			timer.stop();
			if ((arrow.getX() + (arrow.getWidth() / 2)) >= greenZone.getX()
					&& (arrow.getX() + (arrow.getWidth() / 2)) <= greenZone.getX() + greenZone.getWidth()) {
				JOptionPane.showMessageDialog(null, "SUCCESS!!!", "Hooray!", JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(ImagePanel.class.getResource("/Final_Images/Sprites/163turtle.png")));
				Appliances_OneFrame frame = new Appliances_OneFrame(appliance, false);
				frame.setVisible(true);
				dispose();
			} else {
				timer.stop();
				JOptionPane.showMessageDialog(null, "You didn't hit it at the right time. Try again.", "OOPS!",
						JOptionPane.WARNING_MESSAGE);

				reset();
			}
			System.out.println("Still running!");
		}
	}
}
