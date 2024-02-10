import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class MemoryGame extends JFrame implements ActionListener {

	private JPanel contentPane;
	JFrame frame = new JFrame();
	JTextField textField = new JTextField();
	JTextField endText = new JTextField();

	JLabel prompt = new JLabel();
	JLabel sequencePrompt = new JLabel();
	JLabel score = new JLabel();

	JButton buttonA = new JButton();
	JButton buttonB = new JButton();
	JButton buttonC = new JButton();
	JButton buttonD = new JButton();
	JButton closeButton = new JButton();

	Random rand = new Random();

	String currentStr = "";
	String message = "Remember the following sequence!";
	int count = 0;
	int current = rand.nextInt(20);
	int scoreCount = 0;

	String[] sequences = { "ACBD", "BADC", "CBDA", "DBAC", "BCAD", "DACB", "CDAB", "ACDB", "BDCA", "CABD", "BDAC",
			"CADB", "DCBA", "CBAD", "DACB", "BACD", "ACBD", "DBCA", "ACDB", "BCDA" };

	private static String appliance;

	private JButton goBackOrExit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemoryGame frame = new MemoryGame(appliance);
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
	public MemoryGame(String chosenApp) {
		appliance = chosenApp;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 873, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

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

		textField.setBounds(175, 68, 525, 50);
		textField.setBackground(new Color(25, 25, 25));
		textField.setForeground(new Color(243, 255, 245));
		textField.setFont(new Font("Arial", Font.BOLD, 30));
		textField.setBorder(BorderFactory.createBevelBorder(1));
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setEditable(false);
		textField.setText(currentStr);

		buttonA.setBounds(175, 130, 250, 75);
		buttonA.setFont(new Font("Arial", Font.BOLD, 15));
		buttonA.setFocusable(false);
		buttonA.addActionListener(this);
		buttonA.setText("A");

		buttonB.setBounds(450, 130, 250, 75);
		buttonB.setFont(new Font("Arial", Font.BOLD, 15));
		buttonB.setFocusable(false);
		buttonB.addActionListener(this);
		buttonB.setText("B");

		buttonC.setBounds(175, 217, 250, 75);
		buttonC.setFont(new Font("Arial", Font.BOLD, 15));
		buttonC.setFocusable(false);
		buttonC.addActionListener(this);
		buttonC.setText("C");

		buttonD.setBounds(450, 217, 250, 75);
		buttonD.setFont(new Font("Arial", Font.BOLD, 15));
		buttonD.setFocusable(false);
		buttonD.addActionListener(this);
		buttonD.setText("D");

		closeButton.setBounds(319, 296, 250, 75);
		closeButton.setFont(new Font("Arial", Font.BOLD, 15));
		closeButton.setFocusable(false);
		closeButton.addActionListener(this);
		closeButton.setVisible(false);
		closeButton.setText("click to continue!");
		contentPane.add(closeButton);
		contentPane.add(textField);
		contentPane.add(endText);

		sequencePrompt.setBounds(175, 203, 525, 50);
		sequencePrompt.setFont(new Font("Arial", Font.BOLD, 30));
		sequencePrompt.setBorder(BorderFactory.createBevelBorder(1));
		sequencePrompt.setHorizontalAlignment(JTextField.CENTER);
		sequencePrompt.setText(sequences[current]);
		sequencePrompt.setVisible(true);

		prompt.setBounds(175, 117, 525, 50);
		prompt.setFont(new Font("Arial", Font.BOLD, 30));
		prompt.setBorder(BorderFactory.createBevelBorder(1));
		prompt.setHorizontalAlignment(JTextField.CENTER);
		prompt.setText(message);
		prompt.setVisible(true);

		endText.setBounds(175, 68, 525, 50);
		endText.setBackground(new Color(25, 25, 25));
		endText.setForeground(new Color(243, 255, 245));
		endText.setFont(new Font("Arial", Font.BOLD, 30));
		endText.setBorder(BorderFactory.createBevelBorder(1));
		endText.setHorizontalAlignment(JTextField.CENTER);
		endText.setEditable(false);
		endText.setVisible(false);
		endText.setText("Congratulations!");

		score.setBounds(175, 356, 525, 50);
		score.setFont(new Font("Arial", Font.BOLD, 30));
		score.setBorder(BorderFactory.createBevelBorder(1));
		score.setHorizontalAlignment(JTextField.CENTER);
		score.setText("Score: " + scoreCount);
		score.setVisible(true);
		contentPane.add(score);
		contentPane.add(prompt);
		contentPane.add(sequencePrompt);

		contentPane.add(buttonA);
		contentPane.add(buttonB);
		contentPane.add(buttonC);
		contentPane.add(buttonD);
		contentPane.add(textField);

		startRound();

		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Appliances_OneFrame frame = new Appliances_OneFrame(appliance, false);
				frame.setVisible(true);
				dispose();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonA) {
			checkSequence(buttonA, "A");
		}
		if (e.getSource() == buttonB) {
			checkSequence(buttonB, "B");
		}
		if (e.getSource() == buttonC) {
			checkSequence(buttonC, "C");
		}
		if (e.getSource() == buttonD) {
			checkSequence(buttonD, "D");
		}
	}

	public void checkSequence(JButton button, String sequence) {
		currentStr += sequence;
		textField.setText(currentStr);
		count++;
		button.setEnabled(false);
		if (count == 4) {
			if (currentStr.equals(sequences[current])) {
				textField.setText("Good job!");
				if (scoreCount > 8) {
					Timer end = new Timer(1500, new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent c) {
							endScene();
						}
					});

					end.setRepeats(false);
					end.start();
				} else {

					Timer pause1 = new Timer(1500, new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent c) {
							nextSequence();
						}
					});

					pause1.setRepeats(false);
					pause1.start();
				}
			} else {
				textField.setText("try again");
				Timer pause = new Timer(1500, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent c) {
						reset();
						startRound();
					}
				});

				pause.setRepeats(false);
				pause.start();
			}
		}

	}

	public void endScene() {
		textField.setVisible(false);
		buttonA.setVisible(false);
		buttonB.setVisible(false);
		buttonC.setVisible(false);
		buttonD.setVisible(false);
		prompt.setVisible(false);
		sequencePrompt.setVisible(false);
		score.setVisible(false);
		endText.setVisible(true);
		closeButton.setVisible(true);
	}

	public void reset() {
		currentStr = "";
		count = 0;
		buttonA.setEnabled(true);
		buttonB.setEnabled(true);
		buttonC.setEnabled(true);
		buttonD.setEnabled(true);

	}

	public void nextSequence() {
		current = rand.nextInt(20);
		sequencePrompt.setText(sequences[current]);
		scoreCount++;
		score.setText("Score: " + scoreCount);
		Timer pause = new Timer(1500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent c) {
				startRound();
			}
		});

		pause.setRepeats(false);
		pause.start();
	}

	public void startRound() {
		buttonA.setVisible(false);
		buttonB.setVisible(false);
		buttonC.setVisible(false);
		buttonD.setVisible(false);
		textField.setVisible(false);
		textField.setText("");
		prompt.setVisible(true);
		sequencePrompt.setVisible(true);

		if (scoreCount < 5) {
			Timer pause = new Timer(3000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent c) {
					reset();
					buttonA.setVisible(true);
					buttonB.setVisible(true);
					buttonC.setVisible(true);
					buttonD.setVisible(true);
					textField.setVisible(true);
					prompt.setVisible(false);
					sequencePrompt.setVisible(false);

				}
			});

			pause.setRepeats(false);
			pause.start();
		} else if (scoreCount < 8) {
			Timer pause2 = new Timer(1000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent c) {
					reset();
					buttonA.setVisible(true);
					buttonB.setVisible(true);
					buttonC.setVisible(true);
					buttonD.setVisible(true);
					textField.setVisible(true);
					prompt.setVisible(false);
					sequencePrompt.setVisible(false);

				}
			});

			pause2.setRepeats(false);
			pause2.start();
		} else {
			Timer pause3 = new Timer(500, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent c) {
					reset();
					buttonA.setVisible(true);
					buttonB.setVisible(true);
					buttonC.setVisible(true);
					buttonD.setVisible(true);
					textField.setVisible(true);
					prompt.setVisible(false);
					sequencePrompt.setVisible(false);

				}
			});

			pause3.setRepeats(false);
			pause3.start();
		}

	}

}
