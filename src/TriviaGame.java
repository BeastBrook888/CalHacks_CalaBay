import java.awt.Color;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class TriviaGame extends JFrame implements ActionListener {

	private JPanel contentPane;
	JFrame frame = new JFrame();
	JTextArea question = new JTextArea();
	JTextArea gameFacts = new JTextArea();
	JTextField endText = new JTextField();

	JButton buttonA = new JButton();
	JButton buttonB = new JButton();
	JButton buttonC = new JButton();
	JButton buttonD = new JButton();
	JButton instructionsButton = new JButton("Instructions");
	JButton closeButton = new JButton();

	JLabel avatar;
	JLabel Background1;
	JLabel Background2;
	JLabel Background3;

	Image[] spriteList = new Image[24];
	int spriteCount = 0;

	boolean animate = true;
	Timer timer;
	double seconds = 1, milliseconds = 1000;
	double seconds2 = 5, milliseconds2 = 1000;
	boolean bckgrnd1;
	boolean speedUp = false;

	String[] questions = { "Which of the following transportation methods has the lowest carbon footprint?",
			"Which of these items is not recyclable?", "What does the term 'carpooling' mean?",
			"What does the recycling symbol with three arrows in a triangle represent?",
			"What is the best way to save energy when doing laundry?" };
	String[][] options = { { "Walking", "Biking", "Public transportation", "Driving alone" },
			{ "INTACT Glassware", "Plastic bags", "Cardboard boxes", "Aluminum cans" },
			{ "Driving alone", "Taking public transportation", "Riding a bike", "Riding in a car with someone else" },
			{ "Recyclable material", "Non-recyclable material", "Biodegradable material",
					"Material that should be incinerated" },
			{ "Wash only small loads of laundry", "Clean the lint filter on the dryer", "Air dry when possible",
					"All of the above" }

	};
	char[] answers = { 'A', 'B', 'D', 'A', 'D' };

	String[] dialogue = {
			"In Barcelona, an urban mobility plan was implemented to "
					+ "counter the large-scale air pollution in the city. "
					+ "The plan allows citizens more accessibility to eco-friendly alternatives of transportation "
					+ "such as public buses and designated walking or biking paths. *Source ideas.ted.com*",

			"A glass bottle can take more than 4000 years to " + "completely decompose. *Source: fit.edu*",

			"The average car in the United States can emit about 4.6 metric tons of carbon "
					+ "dioxide emissions yearly. *Source: 8billiontrees.com*",

			"Heaters and air conditioning make up nearly 50 percent "
					+ "of energy usage at home. *Source: paylesspower.com*",

			"Air drying laundry will save you about 50 dollars yearly compared to using a dryer. *Source: paylesspower.com*"

	};

	char guess;
	char answer;
	int index;
	int numCorrect = 0;
	int numQuestions = questions.length;
	boolean correct = false;

	Font font;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TriviaGame frame = new TriviaGame();
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
	public TriviaGame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 873, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

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

		question.setWrapStyleWord(true);
		question.setBounds(48, 49, 763, 73);
		question.setForeground(Color.decode("#7B4E3F"));
		question.setFont(font.deriveFont(35f));
		question.setWrapStyleWord(true);
		question.setLineWrap(true);
		question.setEditable(false);
		question.setBorder(BorderFactory.createBevelBorder(1));

		endText.setBounds(175, 68, 525, 50);
		endText.setForeground(Color.decode("#7B4E3F"));
		endText.setFont(font.deriveFont(30f));
		endText.setBorder(BorderFactory.createBevelBorder(1));
		endText.setHorizontalAlignment(JTextField.CENTER);
		endText.setEditable(false);
		endText.setVisible(false);
		endText.setText("Congratulations!");

		buttonA.setForeground(Color.decode("#7B4E3F"));
		buttonA.setBounds(175, 130, 250, 75);
		buttonA.setFont(font.deriveFont(17f));
		buttonA.setFocusable(false);
		buttonA.addActionListener(this);
		buttonA.setText("A");

		buttonB.setForeground(Color.decode("#7B4E3F"));
		buttonB.setBounds(450, 130, 250, 75);
		buttonB.setFont(font.deriveFont(17f));
		buttonB.setFocusable(false);
		buttonB.addActionListener(this);
		buttonB.setText("B");

		buttonC.setForeground(Color.decode("#7B4E3F"));
		buttonC.setBounds(175, 217, 250, 75);
		buttonC.setFont(font.deriveFont(17f));
		buttonC.setFocusable(false);
		buttonC.addActionListener(this);
		buttonC.setText("C");

		buttonD.setForeground(Color.decode("#7B4E3F"));
		buttonD.setBounds(450, 217, 250, 75);
		buttonD.setFont(font.deriveFont(17f));
		buttonD.setFocusable(false);
		buttonD.addActionListener(this);
		buttonD.setText("D");

		gameFacts.setBounds(48, 124, 763, 117);
		gameFacts.setForeground(Color.decode("#7B4E3F"));
		gameFacts.setFont(font.deriveFont(25f));
		gameFacts.setWrapStyleWord(true);
		gameFacts.setLineWrap(true);
		gameFacts.setEditable(false);
		gameFacts.setBorder(BorderFactory.createBevelBorder(1));
		gameFacts.setVisible(false);

		contentPane.add(gameFacts);

		contentPane.add(buttonA);
		contentPane.add(buttonB);
		contentPane.add(buttonC);
		contentPane.add(buttonD);

		instructionsButton.setForeground(Color.decode("#7B4E3F"));
		instructionsButton.setFont(font.deriveFont(17f));
		instructionsButton.setFocusable(false);
		instructionsButton.addActionListener(this);
		instructionsButton.setBounds(24, 390, 133, 23);
		contentPane.add(instructionsButton);

		closeButton.setForeground(Color.decode("#7B4E3F"));
		closeButton.setBounds(319, 296, 250, 75);
		closeButton.setFont(font.deriveFont(17f));
		closeButton.setFocusable(false);
		closeButton.addActionListener(this);
		closeButton.setVisible(false);
		closeButton.setText("click to continue!");
		contentPane.add(closeButton);
		contentPane.add(question);
		contentPane.add(endText);

		Image sprite1 = new ImageIcon(this.getClass().getResource("/Final_Images/Sprites/avatarRIGHT1.png")).getImage();
		Image sprite2 = new ImageIcon(this.getClass().getResource("/Final_Images/Sprites/avatarRIGHT2.png")).getImage();
		Image sprite3 = new ImageIcon(this.getClass().getResource("/Final_Images/Sprites/avatarRIGHT3.png")).getImage();
		Image sprite4 = new ImageIcon(this.getClass().getResource("/Final_Images/Sprites/avatarRIGHT4.png")).getImage();

		avatar = new JLabel("");
		avatar.setHorizontalAlignment(SwingConstants.CENTER);
		avatar.setIcon(new ImageIcon(sprite1));
		avatar.setBounds(389, 236, 99, 197);
		contentPane.add(avatar);

		Image bg1 = new ImageIcon(this.getClass().getResource("/Final_Images/Backgrounds/bg_trivia.png")).getImage();
		Image bg2 = new ImageIcon(this.getClass().getResource("/Final_Images/Backgrounds/bg_trivia.png")).getImage();

		Background1 = new JLabel("");
		Background1.setIcon(new ImageIcon(bg1));
		Background1.setBounds(0, 0, 1673, 496);
		contentPane.add(Background1);

		Background2 = new JLabel("");
		Background2.setIcon(new ImageIcon(bg2));
		Background2.setBounds(getWidth() - 3, 0, 1673, 496);
		contentPane.add(Background2);
		Background2.setVisible(false);

		Background3 = new JLabel("");
		Background3.setIcon(new ImageIcon(bg1));
		Background3.setBounds(0, 0, 1600, 496);
		contentPane.add(Background3);
		Background3.setVisible(false);

		int diff = -(Background1.getWidth() - getWidth());
		bckgrnd1 = true;

		spriteList[0] = sprite1;
		spriteList[1] = sprite1;
		spriteList[2] = sprite1;
		spriteList[3] = sprite1;
		spriteList[4] = sprite1;
		spriteList[5] = sprite1;
		spriteList[6] = sprite2;
		spriteList[7] = sprite2;
		spriteList[8] = sprite2;
		spriteList[9] = sprite2;
		spriteList[10] = sprite2;
		spriteList[11] = sprite2;
		spriteList[12] = sprite3;
		spriteList[13] = sprite3;
		spriteList[14] = sprite3;
		spriteList[15] = sprite3;
		spriteList[16] = sprite3;
		spriteList[17] = sprite3;
		spriteList[18] = sprite4;
		spriteList[19] = sprite4;
		spriteList[20] = sprite4;
		spriteList[21] = sprite4;
		spriteList[22] = sprite4;
		spriteList[23] = sprite4;

		// Timer
		ActionListener timerListener = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (animate == true) {
					if (speedUp == true) {
						seconds = 1;
					}
					++milliseconds;
					if (milliseconds >= 1) {
						--seconds;
						milliseconds = 0;
					}

					++milliseconds2;
					if (milliseconds2 >= 1) {
						--seconds2;
						milliseconds2 = 0;
					}

					if (seconds2 <= 0) {
						// move sprite at different rate as background (sprite moved too fast if synced
						// with background movement speed)
						if (spriteCount == (spriteList.length - 1)) {
							spriteCount = 0;
							avatar.setIcon(new ImageIcon(spriteList[spriteCount]));
						} else {
							avatar.setIcon(new ImageIcon(spriteList[spriteCount]));
							spriteCount++;
						}
						milliseconds2 = 1000;
						seconds2 = 5;
					}

					if (seconds <= 0) {
						// move background
						int x1 = Background1.getX();
						int y1 = Background1.getY();

						int x2 = Background2.getX();
						int y2 = Background2.getY();

						if (bckgrnd1 == true) {
							Background1.setLocation(x1 - 5, y1);
						} else {
							Background2.setLocation(x2 - 5, y2);
						}

						if (x1 <= diff) {
							Background2.setVisible(true);
							Background2.setLocation(x2 - 5, y2);
						}

						if (x2 <= diff) {
							Background1.setVisible(true);
							Background1.setLocation(x1 - 5, y1);
						}

						if (x1 <= -(getWidth() + 900)) {
							Background1.setVisible(false);
							Background1.setLocation(getWidth() - 3, y1);
							bckgrnd1 = false;
						}
						if (x2 <= -(getWidth() + 900)) {
							Background2.setVisible(false);
							Background2.setLocation(getWidth() - 3, y2);
							bckgrnd1 = true;
						}

						milliseconds = 1000;
						seconds = 3;
					}
				}

			}
		};

		instructions();

		timer = new Timer(10, timerListener);

		timer.start();

		nextQuestion();

		instructionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timer.stop();
				instructions();
				timer.start();
			}
		});

		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NPC_Walk frame = new NPC_Walk(2);
				frame.setVisible(true);
				dispose();
			}
		});

	}

	public void instructions() {
		JOptionPane.showMessageDialog(null, "Instructions: Simply pick the correct answer to get to your destination!",
				"Instructions #1", JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(TriviaGame.class.getResource("/Final_Images/Sprites/163deer.png")));
		JOptionPane.showMessageDialog(null,
				"There are no penalties for picking the wrong answer, but try to pick the correct answer first so you can get to your destination faster.",
				"Instructions #2", JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(TriviaGame.class.getResource("/Final_Images/Sprites/163deer.png")));
		JOptionPane.showMessageDialog(null,
				"Let's work together to make our city a cleaner and safer place for everyone!", "Instructions #3",
				JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(TriviaGame.class.getResource("/Final_Images/Sprites/163deer.png")));
		JOptionPane.showMessageDialog(null, "Click 'OK' or press the 'Enter' key to start/resume the game.",
				"END of Instructions", JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(TriviaGame.class.getResource("/Final_Images/Sprites/163deer.png")));
	}

	public void nextQuestion() {
		question.setText(questions[index]);
		buttonA.setText(options[index][0]);
		buttonB.setText(options[index][1]);
		buttonC.setText(options[index][2]);
		buttonD.setText(options[index][3]);
		gameFacts.setText(dialogue[index]);

	}

	public void endScene() {

		question.setVisible(false);
		buttonA.setVisible(false);
		buttonB.setVisible(false);
		buttonC.setVisible(false);
		buttonD.setVisible(false);
		gameFacts.setVisible(false);
		avatar.setVisible(false);
		Background2.setVisible(false);
		Background3.setVisible(true);
		animate = false;
		endText.setVisible(true);
		closeButton.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == buttonA) {
			answer = 'A';
			if (answer == answers[index]) {
				animation(buttonA, true);
			} else {
				animation(buttonA, false);
			}
		}
		if (e.getSource() == buttonB) {
			answer = 'B';
			if (answer == answers[index]) {
				animation(buttonB, true);
			} else {
				animation(buttonB, false);
			}
		}
		if (e.getSource() == buttonC) {
			answer = 'C';
			if (answer == answers[index]) {
				animation(buttonC, true);
			} else {
				animation(buttonC, false);
			}
		}
		if (e.getSource() == buttonD) {
			answer = 'D';
			if (answer == answers[index]) {
				animation(buttonD, true);
			} else {
				animation(buttonD, false);
			}

		}

	}

	// IMPLEMENT: the background and animations of the bike will change depending on
	// the answer (this will be a separate method, and it
	// will be called here.
	// When correct answer is chosen, other options will be grayed out with
	// setEnnabled(); and the color of the frame will change indicating
	// right (green) or wrong (red).
	// After the correct option is chosen, there will be a 5 second delay before
	// actionPerformed() is called, and resetting the buttons and
	// calling nextQuestion();

	public void animation(JButton button, boolean result) {
		if (result == true) {
			speedUp = true;
			button.setForeground(Color.GREEN);

			Timer pause2 = new Timer(1200, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent c) {

					buttonA.setVisible(false);
					buttonB.setVisible(false);
					buttonC.setVisible(false);
					buttonD.setVisible(false);
					question.setVisible(false);
					gameFacts.setVisible(true);

				}
			});

			Timer pause = new Timer(8000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					answer = ' ';
					buttonA.setForeground(Color.decode("#7B4E3F"));
					buttonB.setForeground(Color.decode("#7B4E3F"));
					buttonC.setForeground(Color.decode("#7B4E3F"));
					buttonD.setForeground(Color.decode("#7B4E3F"));

					buttonA.setEnabled(true);
					buttonB.setEnabled(true);
					buttonC.setEnabled(true);
					buttonD.setEnabled(true);
					index++;
					speedUp = false;

					buttonA.setVisible(true);
					buttonB.setVisible(true);
					buttonC.setVisible(true);
					buttonD.setVisible(true);
					question.setVisible(true);
					gameFacts.setVisible(false);

					if (index >= numQuestions) {
						endScene();

					} else {
						nextQuestion();
					}
				}
			});

			pause2.setRepeats(false);
			pause2.start();

			pause.setRepeats(false);
			pause.start();
		} else {
			button.setEnabled(false);
		}

	}
}
