import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;

public class NPC_Walk extends JFrame implements KeyListener {

	private JPanel contentPane;
	private JLabel avatar;

	private JLabel Background;
	private JButton NPC;
	private JLabel bubble;
	private JTextArea goRight;

	private int bgWidth;

	private java.util.List<String> spriteListRIGHT;
	private java.util.List<String> spriteListLEFT;
	private int spriteCount = 0;
	private boolean changeDirection = false;
	private boolean facingNPC = true;

	private static int levelNumber;

	private Font font;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NPC_Walk frame = new NPC_Walk(3);
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
	public NPC_Walk(int lvlNum) {
		levelNumber = lvlNum;

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

		NPC = new JButton("");
		NPC.setBorder(null);
		NPC.setContentAreaFilled(false);
		NPC.setBorder(BorderFactory.createEmptyBorder()); // makes border transparent on Mac (above 2 lines are
															// sufficient only for Windows?)
		contentPane.add(NPC);

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		avatar = new JLabel("");
		avatar.setHorizontalAlignment(SwingConstants.CENTER);
		avatar.setIcon(new ImageIcon(NPC_Walk.class.getResource("/Final_Images/Sprites/avatarRIGHT1.png")));
		avatar.setBounds(22, 158, 101, 179);
		contentPane.add(avatar);

		bubble = new JLabel("");
		bubble.setHorizontalAlignment(SwingConstants.CENTER);
		bubble.setIcon(new ImageIcon(NPC_Walk.class.getResource("/Final_Images/153bubble_exclamation_mark.png")));
		bubble.setBounds(875, 34, 153, 153);
		contentPane.add(bubble);

		goRight = new JTextArea("");
		goRight.setLineWrap(true);
		goRight.setWrapStyleWord(true);
		goRight.setEditable(false);
		goRight.setFont(font.deriveFont(25f));
		goRight.setForeground(Color.decode("#7B4E3F"));
		goRight.setBackground(Color.WHITE);
		goRight.setBounds(201, 33, 404, 66);
		contentPane.add(goRight);

		Background = new JLabel("");
		Background.setBounds(0, -90, 1130, 628);
		contentPane.add(Background);
		bgWidth = Background.getWidth();

		if (levelNumber == 1) {
			goRight.setText(
					"Press and hold the RIGHT ARROW KEY to find the animal that wants to talk to you, then click on them");
			Background.setIcon(new ImageIcon(NPC_Walk.class.getResource("/Final_Images/Backgrounds/bg_trivia.png")));

			NPC.setIcon(new ImageIcon(NPC_Walk.class.getResource("/Final_Images/Sprites/163deer.png")));
			NPC.setToolTipText("I have something important to tell you!");
			NPC.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					NPC_Dialogue frame = new NPC_Dialogue("/Final_Images/Sprites/avatarRIGHT1.png",
							"/Final_Images/Sprites/163deer.png", "level1.txt", 1,
							"/Final_Images/Backgrounds/bg_trivia.png");
					frame.setVisible(true);
					dispose();
				}
			});
			NPC.setBounds(900, 182, 163, 163); // NPC needs to be about 100 less than width of background image
		}
		if (levelNumber == 2) {
			goRight.setText("Click on the duck! It has another task for you!");

			Background
					.setIcon(new ImageIcon(NPC_Walk.class.getResource("/Final_Images/Backgrounds/bg_recyclables.jpg")));

			NPC.setIcon(new ImageIcon(NPC_Walk.class.getResource("/Final_Images/Sprites/163goose.png")));
			NPC.setToolTipText("I have something important to tell you!");
			NPC.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					NPC_Dialogue frame = new NPC_Dialogue("/Final_Images/Sprites/avatarRIGHT1.png",
							"/Final_Images/Sprites/163goose.png", "level2.txt", 2,
							"/Final_Images/Backgrounds/bg_recyclables.jpg");
					frame.setVisible(true);
					dispose();
				}
			});
			NPC.setBounds(700, 300, 163, 163);
			bubble.setBounds(700, 148, 153, 153);

			avatar.setBounds(22, 280, 101, 179);
		}
		if (levelNumber == 3) {
			goRight.setText("Where is the animal we need to talk to? Maybe try clicking the house?");
			Background.setIcon(new ImageIcon(NPC_Walk.class.getResource("/Final_Images/Backgrounds/house.png")));

			NPC.setIcon(null);
			NPC.setToolTipText("I have something important to tell you!");
			NPC.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					NPC_Dialogue frame = new NPC_Dialogue("/Final_Images/Sprites/163turtle.png",
							"/Final_Images/Sprites/avatarLEFT1.png", "level3.txt", 3,
							"/Final_Images/Backgrounds/house.png");
					frame.setVisible(true);
					dispose();
				}
			});
			NPC.setBounds(521, 20, 329, 330);
			bubble.setIcon(new ImageIcon(NPC_Walk.class.getResource("Final_Images/60bubble_exclamation_mark.png")));
			bubble.setBounds(600, 200, 60, 60);

			avatar.setBounds(22, 230, 101, 179);
		}

		spriteListRIGHT = new java.util.ArrayList<String>();
		spriteListLEFT = new java.util.ArrayList<String>();

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				spriteListRIGHT.add("/Final_Images/Sprites/avatarRIGHT" + (i + 1) + ".png");
				spriteListLEFT.add("/Final_Images/Sprites/avatarLEFT" + (i + 1) + ".png");
			}
		}

	}

	public void moveSprite(java.util.List<String> spriteList, boolean changeDirection) {
		if (changeDirection == true) {
			spriteCount = 0; // to make sure sprite does not have legs and arms out by putting sprite in
								// stand-still position (first image in each sprite list)
		}

		if (spriteCount == spriteList.size()) {
			spriteCount = 0;
			avatar.setIcon(new ImageIcon(NPC_Walk.class.getResource(spriteList.get(spriteCount))));
		} else {
			avatar.setIcon(new ImageIcon(NPC_Walk.class.getResource(spriteList.get(spriteCount))));
			spriteCount++;
		}
	}

	public void keyReleased(KeyEvent event) {

	}

	public void keyTyped(KeyEvent event) {

	}

	public void keyPressed(KeyEvent event) {
		int avatarX = avatar.getX();
		int avatarY = avatar.getY();

		int backgroundX = Background.getX();
		int backgroundY = Background.getY();

		int npcX = NPC.getX();
		int npcY = NPC.getY();

		int bubbleX = bubble.getX();
		int bubbleY = bubble.getY();

		// Change icon to make person walk when you click the key
		if (event.getKeyCode() == event.VK_LEFT) {

			if (avatarX >= 0) {
				avatar.setLocation(avatarX - 10, avatarY);
				if (facingNPC == true) { // change direction and sprite picture
					moveSprite(spriteListLEFT, true);
				} else {
					moveSprite(spriteListLEFT, false);
				}
				facingNPC = false; // make sure sprite does not have legs and arms out
				// and so that spriteCount does not increment when switching directions
			}
			if (avatarX <= 50 && backgroundX <= -5 && levelNumber == 1) { // this is the only level where they go off
																			// screen
				Background.setLocation(backgroundX + 5, backgroundY);
				NPC.setLocation(npcX + 5, npcY);
				bubble.setLocation(bubbleX + 5, bubbleY);
				if (facingNPC == true) { // change direction and sprite picture
					moveSprite(spriteListLEFT, true);
				} else {
					moveSprite(spriteListLEFT, false);
				}
			}

		} else if (event.getKeyCode() == event.VK_RIGHT) {

			int rightBounds = 564;
			if (avatarX <= rightBounds) {
				avatar.setLocation(avatarX + 10, avatarY);
				if (facingNPC == false) { // change direction and sprite picture
					moveSprite(spriteListRIGHT, true);
				} else {
					moveSprite(spriteListRIGHT, false);
				}
			}
			if (avatarX >= rightBounds && backgroundX >= -(bgWidth - getWidth()) && levelNumber == 1) { // this is the
																										// only level
																										// where they go
																										// off screen {
				Background.setLocation(backgroundX - 5, backgroundY);
				NPC.setLocation(npcX - 5, npcY);
				bubble.setLocation(bubbleX - 5, bubbleY);
				if (facingNPC == false) { // change direction and sprite picture
					moveSprite(spriteListRIGHT, true);
				} else {
					moveSprite(spriteListRIGHT, false);
				}
			}

			facingNPC = true; // make sure sprite does not have legs and arms out
								// and so that spriteCount does not increment when switching directions
		}

		// Remove instruction from screen when close enough to deer
		if (NPC.getX() < getWidth() - (NPC.getWidth() / 2)) {
			goRight.setVisible(false);
		} else {
			goRight.setVisible(true);
		}
	}
}
