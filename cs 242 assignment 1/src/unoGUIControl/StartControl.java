package unoGUIControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import unoGUIModel.StartModel;
import unoGUIView.StartView;

/**
 * The control class for start GUI
 */
public class StartControl {
	
	/**
	 * corresponding startView
	 */
	private StartView view;
	
	/**
	 * corresponding startModel
	 */
	private StartModel model;
	
	/**
	 * constructor for startControl
	 */
	public StartControl() {
		view = new StartView();
		model = new StartModel();
		// set up action listeners
		setUpStartButtonListener();
		setUpAISelectionListener();
		setUpAISpinnerListener();
		setUpHumanSpinnerListener();
	}
	
	public void setUpStartButtonListener() {
		ActionListener startButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame startFrame = view.getFrame();
				// close startFrame
				startFrame.setVisible(false);
				startFrame.dispose();
				// start InGame control
				int numHuman = model.getNumHumanPlayers();
				int numAI = model.getNumAI();
				String aiType = model.getAiType();
				new InGameControl(numHuman, numAI, aiType);
			}
		};
		view.addStartButtonListener(startButtonListener);
	}
	
	/**
	 * Initialize and add a action listener for AI selection drop 
	 * down menu
	 * when value of number of human player change, update numHumanPlayers
	 * in start mode;
	 */
	public void setUpHumanSpinnerListener() {
		ChangeListener humanListener = new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	        	JSpinner spinner = (JSpinner) e.getSource();
	        	int numHumanPlayers = (int) spinner.getValue();
	        	model.setNumHumanPlayers(numHumanPlayers);
	        }
	    };
	    view.addHumanNumListener(humanListener);
	}
	
	/**
	 * Initialize and add a action listener for AI selection drop 
	 * down menu
	 * when value of number of AI player change, update numAI
	 * in start mode;
	 */
	public void setUpAISpinnerListener() {
		ChangeListener aiListener = new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	        	JSpinner spinner = (JSpinner) e.getSource();
	        	int numAI = (int) spinner.getValue();
	        	model.setNumAI(numAI);
	        }
	    };
	    view.addAINumListener(aiListener);
	}
	
	/**
	 * Initialize and add a action listener for AI selection drop 
	 * down menu
	 * when a AI type is selected, change the AI type stored in start model
	 */
	public void setUpAISelectionListener() {
		ActionListener selectionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> dropdown = (JComboBox<String>) e.getSource();
				String selection = (String) dropdown.getSelectedItem();
				// update aiType in model
				model.setAiType(selection);
			}
		};
		view.addAITypeListener(selectionListener);
	}
}
