package controller.menubar;

import controller.MainViewController;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import main.Main;

import java.io.File;

public class MenuBarController {
	// Reference to the mainViewController
	@FXML
	private MainViewController mainViewController;
	
	
	/**
	 * Is called by the main application to give a reference back to itself.
	 *
	 * @param mainViewController
	 */
	public void setMainViewController(MainViewController mainViewController) {
		this.mainViewController = mainViewController;
	}
	
	/**
	 * Creates an empty address book.
	 */
	@FXML
	private void handleNew() {
		System.out.println("appeler la fonction pour créer un nouveau projet!");
		// FIXME: appeler fonction nouveau
	}
	
	/**
	 * Creates an empty address book.
	 */
	@FXML
	public void handleOpen() {
		FileChooser fileChooser = new FileChooser();
		
		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(".blaaj files(*.blaajj)", "*.blaajj");
		fileChooser.getExtensionFilters().add(extFilter);
		
		// Show save file dialog
		File file = fileChooser.showOpenDialog(mainViewController.getMain().getPrimaryStage());
		
		if (file != null) {
			// main.loadBlaajjFile(file); // FIXME: appeler fonction ouvrir
			System.out.println("path fichier choisi: " + file.getPath());
		}
	}
	
	/**
	 * Saves the file to the person file that is currently open. If there is no
	 * open file, the "save as" dialog is shown.
	 */
	@FXML
	private void handleSave() {
		System.out.println("appeler la fonction de sauvegarde!");
		// FIXME appeler fonction sauvegarder
	}
	
	/**
	 * Opens a FileChooser to let the user select a file to save to.
	 */
	@FXML
	private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();
		
		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(".blaaj files(*.blaajj)", "*.blaajj");
		fileChooser.getExtensionFilters().add(extFilter);
		
		Main mainApp = mainViewController.getMain();
		
		// Show save file dialog
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		
		if (file != null) {
			// Make sure it has the correct extension
			if (!file.getPath().endsWith(".blaajj")) {
				file = new File(file.getPath() + ".blaajj");
			}
			//mainApp.savePersonDataToFile(file);
			System.out.println("appeler la fonction de sauvegarde!"); // FIXME: appeler fct sauvegarder
		}
	}
	
}
