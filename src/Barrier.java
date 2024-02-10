import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Barrier {

	private Rectangle bounds;
	boolean isGoal;
	boolean isVisible = true;

	/*
	 * Constructor for the Barrier object
	 */
	public Barrier(int x, int y, int width, int height, boolean isGoal) {
		bounds = new Rectangle(x, y, width, height);
		this.isGoal = isGoal;

	}

	/*
	 * Sets the location of the rectangle
	 */
	public void setLocation(int x, int y) {
		bounds.setLocation(x, y);
	}

	/*
	 * Sets whether the barrier object is a goal or an obstacle
	 */
	public boolean isGoal() {
		return isGoal;
	}

	/*
	 * Checks if the sprite has intersected with the barrier object
	 */
	public boolean intersects(Rectangle other) {
		return bounds.intersects(other);
	}

	/*
	 * Draws the barrier object If the barrier is a goal, the color is set to green
	 * If the barrier is an obstacle, the color is set to red if isVisible is true,
	 * it will draw the rectangle, otherwise it will not
	 */
	public void draw(Graphics g) {
		if (isVisible == true) {
			if (isGoal == true) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.RED);
			}
			g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		}
	}

	/*
	 * Sets the visibility of the barrier objects If true, the barrier object is
	 * drawn, otherwise it is not drawn
	 */
	public void setVisible(boolean visible) {
		this.isVisible = visible;
	}

}
