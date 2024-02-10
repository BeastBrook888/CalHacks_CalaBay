import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Start_Screen extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Start_Screen frame = new Start_Screen();
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
	public Start_Screen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 873, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton startGame = new JButton("");
		startGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NPC_Walk frame = new NPC_Walk(1);
				frame.setVisible(true);
				dispose();
			}
		});
		startGame.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		startGame.setBounds(332, 389, 200, 48);
		startGame.setBorder(null);
		startGame.setContentAreaFilled(false);
		contentPane.add(startGame);

		JLabel background = new JLabel("");
		background.setIcon(
				new ImageIcon(Start_Screen.class.getResource("/Final_Images/Backgrounds/startScreenPage.png")));
		background.setBounds(0, 0, 873, 496);
		contentPane.add(background);
	}
}
