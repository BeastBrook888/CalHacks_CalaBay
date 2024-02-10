import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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
import java.awt.GraphicsEnvironment;
import java.awt.FontFormatException;

public class EndingScreen extends JFrame {

	private JPanel contentPane;
	
	private BufferedReader read;
	private GraphicsEnvironment ge;
	private Font font;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EndingScreen frame = new EndingScreen();
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
	public EndingScreen() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 873, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		try {
		     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Winkle-Regular.ttf")));
		} catch (IOException|FontFormatException e) {
			
		}
		
		try {
		     font = Font.createFont(Font.TRUETYPE_FONT, new File(System.getProperty("user.dir") + "//src//" + "Winkle-Regular.ttf"));

		} catch (IOException|FontFormatException e) {

		}
		
		JTextArea dialogue = new JTextArea("");
		dialogue.setFont(font.deriveFont(26f));
		dialogue.setBackground(Color.WHITE);
		dialogue.setBounds(59, 164, 763, 66);
		dialogue.setWrapStyleWord(true);
		dialogue.setForeground(Color.decode("#7B4E3F"));
		dialogue.setWrapStyleWord(true);
		dialogue.setLineWrap(true);
		dialogue.setEditable(false);
		contentPane.add(dialogue);
		
		dialogue.setLocation(59, 164);
		
		try {
			File script = new File(System.getProperty("user.dir") + "//src//Dialogue//Ending.txt");
			FileReader rd = new FileReader(script);
			read = new BufferedReader(rd);
			
			dialogue.setText(read.readLine());
		} catch(Exception e) {
			System.out.println(e);
		}
		
		JButton nextLine = new JButton("");
		nextLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String line = read.readLine();
					
					if(line != null) {
						dialogue.setText(line);
					} else {
						System.exit(0);
					}
				} catch(Exception e2) {
					
				}
			}
		});
		nextLine.setIcon(new ImageIcon(EndingScreen.class.getResource("/Final_Images/fast_forward.png")));
		nextLine.setBounds(382, 237, 89, 73);
		nextLine.setBorder(null);
		nextLine.setContentAreaFilled(false);
		contentPane.add(nextLine);
		
		JLabel background = new JLabel("");
		background.setIcon(new ImageIcon(EndingScreen.class.getResource("/Final_Images/Backgrounds/endingScreenF.jpeg")));
		background.setBounds(0, 0, 873, 496);
		contentPane.add(background);
	}
}
