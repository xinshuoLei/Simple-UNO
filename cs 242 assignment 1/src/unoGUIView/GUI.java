package unoGUI;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * The abstract class of GUI
 * contains all basic functions GUI, such as adding components to JFrame
 */
public abstract class GUI {

	/**
	  * Display an image
	  * reference: https://stackoverflow.com/questions/13448368/trying-to-display-url-image-in-jframe
	  * @param panel JPanel of the window
	  * @param width width of the image
	  * @param height height of the image
	  * @param locationX x-coordination of the image
	  * @param locationY y-coordination of the image
	  * @param path url of the image
	  * @return JLabel created
	  */
	 public static JLabel displayImageFromUrl(JPanel panel, int width , 
			 int height, int locationX, int locationY, String path){
		 try {
			 
			 // try getting the uno logo from the url
			 URL url = new URL(path);
			 BufferedImage image = ImageIO.read(url);
	         
			 // scale the image
			 Image scaled_image = image.getScaledInstance(width, height,
	        		 Image.SCALE_SMOOTH);
	         
			 JLabel picLabel = new JLabel(new ImageIcon(scaled_image));
			 
			 // add image to startPanel
			 panel.add(picLabel);
			 picLabel.setSize(width, height);
			 picLabel.setLocation(locationX, locationY);
			 return picLabel;
			 
			 // TODO: add action
		 } catch (Exception e) {
			 // print error message if exception occurs
			 e.printStackTrace();
			 return null;
		 } 
	 }
	 
	 /**
	  * Create JPanel, set size and layout
	  * @param width width of the panel
	  * @param height height of the panel
	  * @return JPanel created
	  */
	 public static JPanel initializePanel(int width, int height) {
	        JPanel myPanel = new JPanel();
	        myPanel.setPreferredSize(new Dimension(width,height));
	        myPanel.setLayout(null);
	        return myPanel;
	 }
	 
	 
	 /**
	  * Display a text with custom font size in the middle of the screen
	  * @param panel JPanel of the window
	  * @param text the string to display
	  * @param fontSize size of the font
	  * @param windowWidth width of the window, so that text can be placed in the middle
	  * @param locationY y-coordinate of the image
	  * @return JLabel created
	  */
	 public static JLabel displayTextInMiddle(JPanel panel, String text, float fontSize, 
			 int windowWidth, int locationY) {
		 // text prompt
		 JLabel inputPrompt = new JLabel(text);
		 panel.add(inputPrompt);
		 
		 // make font size larger
		 inputPrompt.setFont(inputPrompt.getFont().deriveFont(fontSize));
		 Dimension promptDimension = inputPrompt.getPreferredSize();
		 inputPrompt.setSize(promptDimension);
		 int locationX = (windowWidth- promptDimension.width) / 2;
		 inputPrompt.setLocation(locationX, locationY);
		 
		 return inputPrompt;
	 }
	 
	 /**
	  * Display a text with custom font size
	  * @param panel JPanel of the window
	  * @param text the string to display
	  * @param fontSize size of the font
	  * @param windowWidth width of the window, so that text can be placed in the middle
	  * @param locationY y-coordinate of the image
	  * @return JLabel created
	  */
	 public static JLabel displayText(JPanel panel, String text, float fontSize, 
			 int locationX, int locationY) {
		 // text prompt
		 JLabel inputPrompt = new JLabel(text);
		 panel.add(inputPrompt);
		 
		 // make font size larger
		 inputPrompt.setFont(inputPrompt.getFont().deriveFont(fontSize));
		 Dimension promptDimension = inputPrompt.getPreferredSize();
		 inputPrompt.setSize(promptDimension);
		 inputPrompt.setLocation(locationX, locationY);
		 
		 return inputPrompt;
	 }
	 
	/**
	 * Add a button
	 * @param panel JPanel of the window
	 * @param text text that is displayed on the button
	 * @param width width of the button
	 * @param height height of the button
	 * @param locationX x-coordinate of the button
	 * @param locationY y-coordinate of the button
	 * @return button created
	 */
	 public static JButton addButton(JPanel panel, String text, int width, int height, 
			 int locationX, int locationY) {
	        JButton button = new JButton(text);
	        // TODO: add action listener
	        panel.add(button);
	        button.setSize(width, height);
	        button.setLocation(locationX, locationY);
	        return button;
	 }
	 
	 /**
	  * Add a spinner with the given SpinnerNumberModel
	  * @param panel JPanel of the window
	  * @param SpinnerNumberModel the spinner number model that 
	  * define value, min, max, step
	  * @param width width of the spinner
	  * @param height height of the spinner
	  * @param locationX x-coordinate of the JSpinner
	  * @param locationY y-coordinate of the JSpinner
	  * @return spinner created
	  */
	 public static JSpinner addSpinner(JPanel panel, SpinnerNumberModel model, int width,
			 int height, int locationX, int locationY) {
		 JSpinner spinner = new JSpinner(model);
		 spinner.setBounds(50,80,70,100);
		 spinner.setSize(width,height);
		 spinner.setLocation(locationX, locationY);
		 panel.add(spinner);
		 return spinner;
	 }
	 
	 /**
	  * Add a drop down menu with the given selections
	  * @param panel JPanel of the window
	  * @param selections selections for the drop down menu
	  * @param width width of the drop down menu
	  * @param height height of the drop down menu
	  * @param locationX x-coordinate of the drop down menu
	  * @param locationY y-coordinate of the drop down menu
	  * @return drop down menu created
	  */
	 public static JComboBox<String> addDropDown(JPanel panel, String[] selections, int width,
			 int height, int locationX, int locationY) {
		 JComboBox<String> dropDown = new JComboBox<String>(selections);
		 dropDown.setSize(width, height);
		 dropDown.setLocation(locationX, locationY);
		 panel.add(dropDown);
		 return dropDown;
	 }
	
	
}
