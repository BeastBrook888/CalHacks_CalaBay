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
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.SwingConstants;

public class Button_Mash extends JFrame implements KeyListener {

	private JPanel contentPane;

	private Timer timer;
	private static int seconds;
	private int milliseconds = 1000;

	private JLabel greenZone;
	private JLabel lblMashTheSpace;
	private JLabel lblTimer;

	private static String chosenAppliance;

	private boolean released; // to prevent holding down the space bar from working
	private JButton goBackOrExit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Button_Mash frame = new Button_Mash(chosenAppliance, seconds);
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
	public Button_Mash(String appliance, int sec) {
		chosenAppliance = appliance;
		seconds = sec;

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
		greenZone.setIcon(
				new ImageIcon(Button_Mash.class.getResource("/Final_Images/TurnOffAppliances/long_green_bar.jpg")));
		greenZone.setBounds(28, 421, 82, 39);
		contentPane.add(greenZone);

		JLabel redZone = new JLabel("");
		redZone.setIcon(
				new ImageIcon(Button_Mash.class.getResource("/Final_Images/TurnOffAppliances/long_red_bar.jpg")));
		redZone.setBounds(28, 145, 82, 315);
		contentPane.add(redZone);

		lblMashTheSpace = new JLabel(
				"Repeatedly click the button below or mash the space bar to fill the meter before time runs out!");
		lblMashTheSpace.setHorizontalAlignment(SwingConstants.CENTER);
		lblMashTheSpace.setForeground(Color.RED);
		lblMashTheSpace.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblMashTheSpace.setBounds(0, 0, 862, 85);
		contentPane.add(lblMashTheSpace);

		lblTimer = new JLabel(":10");
		lblTimer.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimer.setForeground(Color.RED);
		lblTimer.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblTimer.setBounds(0, 56, 862, 42);
		contentPane.add(lblTimer);

		JButton btnNewButton = new JButton("Keep clicking me to fill the bar!");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				greenZone.setBounds(28, greenZone.getY() - 5, 82, greenZone.getHeight() + 5);
			}
		});
		btnNewButton.setBounds(231, 386, 372, 74);
		contentPane.add(btnNewButton);
		
		goBackOrExit = new JButton("Go Back to House");
		goBackOrExit.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		goBackOrExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timer.stop();
				JOptionPane.getRootFrame().dispose();

				int confirm = JOptionPane.showConfirmDialog(null,
						"Are you sure you would like to go back to the house? You will lose your progress.", "Go back?",
						JOptionPane.WARNING_MESSAGE);

				if (confirm == JOptionPane.YES_OPTION) {
					Appliances_OneFrame frame = new Appliances_OneFrame(null, false);
					frame.setVisible(true);
					dispose();
				} else {
					goBackOrExit.setSelected(false);
					goBackOrExit.setFocusable(false);
					timer.start();
				}
			}
		});
		goBackOrExit.setForeground(Color.decode("#7B4E3F"));
		goBackOrExit.setBounds(0, 0, 208, 39);
		contentPane.add(goBackOrExit);

		// Timer
		ActionListener timerListener = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {

				if ((greenZone.getHeight() >= redZone.getHeight())) {
					JOptionPane.showMessageDialog(null, "Hooray!", "Hooray!", JOptionPane.INFORMATION_MESSAGE,
							new ImageIcon(ImagePanel.class.getResource("/Final_Images/Sprites/163turtle.png")));
					timer.stop();
					Appliances_OneFrame frame = new Appliances_OneFrame(appliance, false);
					frame.setVisible(true);
					dispose();
				}

				++milliseconds;
				if (milliseconds >= 1000) {
					--seconds;
					milliseconds = 0;
					lblTimer.setText("00:0" + seconds);
				}

				if (seconds <= 0) {
					JOptionPane.showMessageDialog(null, "Oh no! Please try again.", "OOPS!",
							JOptionPane.WARNING_MESSAGE);
					greenZone.setBounds(28, 421, 82, 39);

					milliseconds = 1000;
					seconds = sec;
				}
			}
		};

		timer = new Timer(1, timerListener);
		timer.start();
	}

	public void keyReleased(KeyEvent event) {
		released = true;
	}

	public void keyTyped(KeyEvent event) {

	}

	public void keyPressed(KeyEvent event) {

		if (event.getKeyCode() == event.VK_SPACE && released == true) {
			greenZone.setBounds(28, greenZone.getY() - 5, 82, greenZone.getHeight() + 5);
			released = false;
		}
	}
}
