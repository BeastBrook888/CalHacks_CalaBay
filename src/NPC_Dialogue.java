import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;

public class NPC_Dialogue extends JFrame {

	private JPanel contentPane;

	public static String leftPerson;
	public static String rightPerson;
	public static String dialogueScript;
	private static int lvlNum;
	private static String bgImg;

	private JLabel dialogueBubble;

	private BufferedReader read;
	private Font font;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NPC_Dialogue frame = new NPC_Dialogue(leftPerson, rightPerson, dialogueScript, lvlNum, bgImg);
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
	public NPC_Dialogue(String left, String right, String scrp, int levelNumber, String bg) {
		leftPerson = left;
		rightPerson = right;
		dialogueScript = scrp;
		lvlNum = levelNumber;
		bgImg = bg;

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

		JLabel dialogue = new JLabel("");
		dialogue.setBackground(new Color(255, 0, 255));
		dialogue.setBounds(196, 373, 446, 75);
		contentPane.add(dialogue);

		JLabel rightCharacter = new JLabel("");
		rightCharacter.setIcon(new ImageIcon(NPC_Dialogue.class.getResource(rightPerson)));
		rightCharacter.setBounds(360, 107, 163, 179);
		contentPane.add(rightCharacter);

		JLabel leftCharacter = new JLabel("");
		leftCharacter.setIcon(new ImageIcon(NPC_Dialogue.class.getResource(leftPerson)));
		leftCharacter.setBounds(190, 107, 163, 179);
		contentPane.add(leftCharacter);

		dialogueBubble = new JLabel("");
		dialogueBubble.setIcon(new ImageIcon(NPC_Dialogue.class.getResource("/Final_Images/deerBubble.png")));
		dialogueBubble.setBounds(0, 0, 873, 496);
		contentPane.add(dialogueBubble);

		try {
			File script = new File(System.getProperty("user.dir") + "//src//Dialogue//" + dialogueScript);
			FileReader rd = new FileReader(script);
			read = new BufferedReader(rd);

			// these lines are up here so you can see the first line before you press the
			// "next line"
			String talker = read.readLine();
			String line = read.readLine();

			dialogueBubble
					.setIcon(new ImageIcon(NPC_Dialogue.class.getResource("/Final_Images/" + talker + "Bubble.png")));
			repaint();
			dialogue.setText("<html><p>" + line + "</p></html>");
			dialogue.setFont(font.deriveFont(17f));

		} catch (Exception e) {
			System.out.println(e);
		}

		JButton nextLine = new JButton("");
		nextLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String talker = read.readLine();
					String line = read.readLine();

					if (line != null) {
						dialogueBubble.setIcon(new ImageIcon(
								NPC_Dialogue.class.getResource("/Final_Images/" + talker + "Bubble.png")));
						repaint();
						dialogue.setText("<html><p>" + line + "</p></html>");
						dialogue.setFont(font.deriveFont(17f));
					} else {
						JFrame frame = null;
						if (lvlNum == 1) {
							frame = new TriviaGame();
						}
						if (lvlNum == 2) {
							frame = new Catch_Recyclables_Working2();
						}
						if (lvlNum == 3) {
							frame = new Appliances_OneFrame(null, true);
						}
						frame.setVisible(true);
						dispose();
					}
				} catch (Exception e2) {

				}
			}
		});
		nextLine.setIcon(null);
		nextLine.setBounds(632, 401, 63, 47);
		nextLine.setBorder(null);
		nextLine.setContentAreaFilled(false);
		contentPane.add(nextLine);
		
		if (lvlNum == 2) {
			leftCharacter.setBounds(260, 90, 163, 179);
			rightCharacter.setBounds(430, 90, 163, 179);
		}

		if (lvlNum == 3) {
			leftCharacter.setBounds(260, 140, 163, 179);
			rightCharacter.setBounds(440, 150, 163, 179);
		}

		JLabel background = new JLabel("");
		background.setIcon(new ImageIcon(NPC_Dialogue.class.getResource(bg)));
		background.setBounds(0, 0, 873, 496);
		contentPane.add(background);
	}
}
