import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class ImagePanel extends JPanel {

	JTextField endText = new JTextField();

	JLabel scoreCount = new JLabel();

	ImageIcon sprite;
	Point currPoint;
	Point previousPoint;

	int score = 0;

	private ArrayList<Barrier> barriers;

	/*
	 * creates the barrier objects and other objects that are on screen while game
	 * is running
	 */
	ImagePanel(ImageIcon sprite) {
		this.sprite = sprite;
		currPoint = new Point(0, 0);

		barriers = new ArrayList<>();

		Barrier barrier1 = new Barrier(250, 0, 400, 100, false);
		barriers.add(barrier1);

		Barrier barrier2 = new Barrier(250, 350, 400, 100, false);
		barriers.add(barrier2);

		Barrier goal = new Barrier(800, 0, 100, 500, true);
		barriers.add(goal);

		ClickListener clickListener = new ClickListener();
		addMouseListener(clickListener);

		DragListener dragListener = new DragListener();
		addMouseMotionListener(dragListener);

		scoreCount.setFont(new Font("Arial", Font.BOLD, 30));
		scoreCount.setBounds(getWidth() - 200, 0, 200, 50);
		scoreCount.setText("Score: " + score);
		add(scoreCount);

	}

	/*
	 * draws the sprite and barrier objects
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		sprite.paintIcon(this, g, (int) currPoint.getX(), (int) currPoint.getY());

		for (Barrier barrier : barriers) {
			barrier.draw(g);
		}
	}

	/*
	 * sets the contents of the content pane from DraDropGame to be visible while
	 * closes the image panel
	 */
	public void endGame() {
		DragDropGame game = (DragDropGame) SwingUtilities.getWindowAncestor(this);
		game.showContentPane();
		game.remove(this);
		game.closeButton.setVisible(true);
		game.endText.setVisible(true);
		game.revalidate();
		game.repaint();

	}

	/*
	 * randomly changes the y-position of both barriers method is called when sprite
	 * touches the goal the barriers are set to invisible after a few seconds
	 */
	public void nextRound() {
		Random rand = new Random();
		int maxY = 400;
		int minY = 0;

		int range = (maxY - minY + 1) - minY;

		int newY1 = rand.nextInt(range);
		int newY2 = rand.nextInt(range);

		barriers.get(0).setLocation(250, newY1);
		barriers.get(1).setLocation(250, newY2);

		Timer pause = new Timer(500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent c) {
				barriers.get(0).setVisible(false);
				barriers.get(1).setVisible(false);

			}
		});

		pause.setRepeats(false);
		pause.start();

	}

	/*
	 * Used to handle Mouse pressed events such as to track the movement of the
	 * sprite
	 */
	private class ClickListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			previousPoint = e.getPoint();
		}
	}

	/*
	 * Used to handle Mouse dragged events and how the sprite interacts with barrier
	 * objects Creates the bounds of the sprite of where the use is able to drag it
	 * If the barrier object is a goal, the score will be updated, sprite position
	 * is reset, and nextRound() will be called If the barrier object is not a goal,
	 * the sprite position is reset, and nexRound() will be called If score is 10,
	 * endGame() will be called, closing out the game
	 * 
	 */
	private class DragListener extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent e) {
			Point currentPoint = e.getPoint();

			Rectangle spriteBounds = new Rectangle((int) currPoint.getX(), (int) currPoint.getY(),
					sprite.getIconWidth(), sprite.getIconHeight());
			for (Barrier barrier : barriers) {
				if (barrier.intersects(spriteBounds)) {
					barriers.get(0).setVisible(true);
					barriers.get(1).setVisible(true);
					if (barrier.isGoal == true) {
						JOptionPane.showMessageDialog(null,
								"Good job!\nClick 'OK' or press the 'Enter' key to continue", "Hooray!",
								JOptionPane.INFORMATION_MESSAGE,
								new ImageIcon(ImagePanel.class.getResource("/Final_Images/Sprites/163turtle.png")));
						currPoint.setLocation(60, 0);
						repaint();
						score++;
						scoreCount.setText("Score: " + score);
						if (score == 10) {
							endGame();
						} else {
							nextRound();
						}
					} else {
						currPoint.setLocation(60, 0);
						JOptionPane.showMessageDialog(null,
								"You hit the barrier!\nClick 'OK' or press the 'Enter' key to continue", "OOPS!",
								JOptionPane.WARNING_MESSAGE);
						nextRound();
						repaint();
						break;
					}
				}
			}

			if (currentPoint.getX() >= 0 && currentPoint.getX() < getWidth()) {
				if (currentPoint.getY() >= 0 && currentPoint.getY() < getHeight() - 70) {
					currPoint.translate((int) (currentPoint.getX() - previousPoint.getX()),
							(int) (currentPoint.getY() - previousPoint.getY()));
				}
			}

			previousPoint = currentPoint;
			repaint();
		}

	}

}
