package GUI;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Utilities {
	/**
	  * Display an image
	  * @param panel JPanel of the window
	  * @param width width of the image
	  * @param height height of the image
	  * @param locationX x-coordination of the image
	  * @param locationY y-coordination of the image
	  * @param path url of the image
	  * reference: https://stackoverflow.com/questions/13448368/trying-to-display-url-image-in-jframe
	  */
	 public static void displayImageFromUrl(JPanel panel, int width , 
			 			int height, int locationX, int locationY, String path){
		 try {
			 // try getting the uno logo from the url
	         URL url = new URL(path);
	         BufferedImage logo = ImageIO.read(url);
	         
	         // scale the image
	         Image scaled_logo = logo.getScaledInstance(width, height,
	        	        Image.SCALE_SMOOTH);
			 JLabel picLabel = new JLabel(new ImageIcon(scaled_logo));
			 
			 // add image to startPanel
			 panel.add(picLabel);
			 picLabel.setSize(width, height);
			 picLabel.setLocation(locationX, locationY);
			 
			 // TODO: add action
		 } catch (Exception e) {
			 // print error message if exception occurs
			 e.printStackTrace();
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
	  * Display a text with custom font size
	  * @param panel JPanel of the window
	  * @param text the string to display
	  * @param fontSize 
	  * @param windowWidth width of the window, so that text can be placed in the middle
	  * @param locationY y-coordination of the image
	  */
	 public static void displayText(JPanel panel, String text, float fontSize, 
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
	 }
	 
	/**
	 * Add a button
	 * @param panel JPanel of the window
	 * @param text text that is displayed on the button
	 * @param width width of the button
	 * @param height height of the button
	 * @param locationX x-coordination of the button
	 * @param locationY y-coordination of the button
	 */
	 public static void addButton(JPanel panel, String text, int width, int height, 
			 						int locationX, int locationY) {
	        JButton button = new JButton(text);
	        // TODO: add action listener
	        panel.add(button);
	        button.setSize(width, height);
	        button.setLocation(locationX, locationY);
	 }
	 
}
