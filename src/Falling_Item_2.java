import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Falling_Item_2 {
	public int timerSpeed = 0; // similar to changing "seconds" variable
	public String chosenImage;
	public boolean recyclable = false;
	public JLabel itemLabel;

	public ArrayList<String> recyclables = new ArrayList<String>();
	{
		String marker = "/Final_Images/Catch_Recyclables/(r)";
		recyclables.add(marker + "A cardboard box.png");
		recyclables.add(marker + "Intact glassware.png");
		recyclables.add(marker + "A plastic bottle.png");
		recyclables.add(marker + "Tin cans.png");
	}
	public ArrayList<String> garbage = new ArrayList<String>();
	{
		String marker = "/Final_Images/Catch_Recyclables/(g)";
		garbage.add(marker + "Ceramic Dishware.png");
		garbage.add(marker + "A light bulb.png");
		garbage.add(marker + "A plastic bag.png");
		garbage.add(marker + "Pots and pans.png");
	}

	public Falling_Item_2() {
		itemLabel = new JLabel("");
		itemLabel.setHorizontalAlignment(SwingConstants.CENTER);
		itemLabel.setBounds(randomNum(0, 800), 0, 120, 120);

		itemLabel.setBounds(randomNum(0, 800), -(itemLabel.getHeight()), 120, 120);
		// need to setBounds twice since we want to declare the label's height, then use
		// that as
		// the y-coordinate of the label.
		// this is to make the objects start from slightly outside the frame

		int rnd = randomNum(0, 1);
		if (rnd == 0) {
			setRecyclable(false);
		} else {
			setRecyclable(true);
		}

	}

	public JLabel getJLabel() {
		return itemLabel;
	}

	public void resetJLabel() {
		itemLabel.setBounds(randomNum(0, 800), -(itemLabel.getHeight()), 151, 184);
	}

	public int randomNum(int min, int max) {
		int rndNum = (int) Math.floor(Math.random() * (max - min + 1) + min);
		return rndNum;
	}

	public boolean getRecyclable() {
		return recyclable;
	}

	// allows us to both randomize recyclable variable (like in the constructor
	// above)
	// or set it manually if we want to make a recyclable object because all the
	// other objects are garbage
	public void setRecyclable(boolean marker) {
		recyclable = marker;
		if (recyclable == false) {
			int rndIndex = randomNum(0, 3);
			chosenImage = garbage.get(rndIndex);
			itemLabel.setIcon(new ImageIcon(Catch_Recyclables_Working2.class.getResource(chosenImage)));
		} else {
			int rndIndex = randomNum(0, 3);
			chosenImage = recyclables.get(rndIndex);
			itemLabel.setIcon(new ImageIcon(Catch_Recyclables_Working2.class.getResource(chosenImage)));
		}
	}

	public String getItemName() {
		return chosenImage.substring(35, chosenImage.length() - 4);
	}

}
