package controller.menubar;

import controller.Layer;
import controller.Project;
import controller.history.ICmd;
import controller.history.RecordCmd;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

/**
 * Controller associé au fichier FXML Transformations.fxml et controlant l'ensemble des actions associées au sous menu <b>Calque -> Transformations</b>.
 */
public class Transformations {
	
	@FXML
	public Menu transformations;
	@FXML
	private TextField degrees;
	
	/**
	 * Initialise le controlleur. Appelé automatiquement par javaFX lors de la création du FXML.
	 */
	@FXML
	private void initialize() {
		degrees.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue,
			                    String newValue) {
				if (!newValue.matches("[-]?[0-9]*")) {
					degrees.setText(oldValue);
				}
			}
		});
	}
	
	@FXML
	public void handleValidateRotate(ActionEvent event) {
		if (!degrees.getText().isEmpty()) {
			Layer currentLayer = Project.getInstance().getCurrentLayer();
			RotateSave rs = new RotateSave();
			Image image = currentLayer.createImageFromCanvas(1);
			currentLayer.getGraphicsContext2D().clearRect(0, 0, currentLayer.getWidth(), currentLayer.getHeight());
			
			currentLayer.getGraphicsContext2D().save();
			Rotate r = new Rotate(Double.valueOf(degrees.getText()), currentLayer.getWidth() / 2, currentLayer.getHeight() / 2);
			currentLayer.getGraphicsContext2D().setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
			currentLayer.getGraphicsContext2D().drawImage(image, 0, 0);
			currentLayer.getGraphicsContext2D().restore();
			rs.execute();
		}
	}
	
	private class VerticalSymmetry extends ICmd {
		
		private Layer currentLayer;
		
		private VerticalSymmetry() {
			currentLayer = Project.getInstance().getCurrentLayer();
		}
		
		@Override
		public void execute() {
			RecordCmd.getInstance().saveCmd(this);
		}
		
		@Override
		public void undo() {
			Rotate r = new Rotate(180, currentLayer.getWidth() / 2, currentLayer.getHeight() / 2, 0, Rotate.Y_AXIS);
			currentLayer.getTransforms().add(r);
		}
		
		@Override
		public void redo() {
			Rotate r = new Rotate(180, currentLayer.getWidth() / 2, currentLayer.getHeight() / 2, 0, Rotate.Y_AXIS);
			currentLayer.getTransforms().add(r);
		}
		
		@Override
		public String toString() {
			return "Symétrie verticale de " + currentLayer;
		}
	}
	
	@FXML
	public void handleVerticalSymmetry() {
		VerticalSymmetry verticalSymmetry = new VerticalSymmetry();
		Layer currentLayer = Project.getInstance().getCurrentLayer();
		Rotate r = new Rotate(180, currentLayer.getWidth() / 2, currentLayer.getHeight() / 2, 0, Rotate.Y_AXIS);
		currentLayer.getTransforms().add(r);
		verticalSymmetry.execute();
	}
	
	//TODO : à factoriser avec au dessus
	private class HorizontalSymmetry extends ICmd {
		
		private Layer currentLayer;
		
		private HorizontalSymmetry() {
			currentLayer = Project.getInstance().getCurrentLayer();
		}
		
		@Override
		public void execute() {
			RecordCmd.getInstance().saveCmd(this);
		}
		
		@Override
		public void undo() {
			Rotate r = new Rotate(180, currentLayer.getWidth() / 2, currentLayer.getHeight() / 2, 0, Rotate.X_AXIS);
			currentLayer.getTransforms().add(r);
		}
		
		@Override
		public void redo() {
			Rotate r = new Rotate(180, currentLayer.getWidth() / 2, currentLayer.getHeight() / 2, 0, Rotate.X_AXIS);
			currentLayer.getTransforms().add(r);
		}
		
		@Override
		public String toString() {
			return "Symétrie horizontale de " + currentLayer;
		}
	}
	
	@FXML
	public void handleHorizontalSymmetry() {
		HorizontalSymmetry horizontalSymmetry = new HorizontalSymmetry();
		Layer currentLayer = Project.getInstance().getCurrentLayer();
		Rotate r = new Rotate(180, currentLayer.getWidth() / 2, currentLayer.getHeight() / 2, 0, Rotate.X_AXIS);
		currentLayer.getTransforms().add(r);
		horizontalSymmetry.execute();
	}
	
	private class RotateSave extends ICmd {
		private Layer currentLayer;
		private Image image;
		private double angleDegres;
		private Affine getTransform;
		
		private RotateSave() {
			currentLayer = Project.getInstance().getCurrentLayer();
			image = currentLayer.createImageFromCanvas(1);
			angleDegres = Double.valueOf(degrees.getText());
			getTransform = currentLayer.getGraphicsContext2D().getTransform();
		}
		
		@Override
		public void execute() {
			RecordCmd.getInstance().saveCmd(this);
		}
		
		@Override
		public void undo() {
			currentLayer.getGraphicsContext2D().clearRect(0, 0, currentLayer.getWidth(), currentLayer.getHeight());
			currentLayer.getGraphicsContext2D().save();
			currentLayer.getGraphicsContext2D().setTransform(getTransform);
			currentLayer.getGraphicsContext2D().drawImage(image, 0, 0);
			currentLayer.getGraphicsContext2D().restore();
			
		}
		
		@Override
		public void redo() {
			currentLayer.getGraphicsContext2D().clearRect(0, 0, currentLayer.getWidth(), currentLayer.getHeight());
			currentLayer.getGraphicsContext2D().save();
			Rotate r = new Rotate(angleDegres, currentLayer.getWidth() / 2, currentLayer.getHeight() / 2);
			currentLayer.getGraphicsContext2D().setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
			currentLayer.getGraphicsContext2D().drawImage(image, 0, 0);
			currentLayer.getGraphicsContext2D().restore();
			
		}
		
		@Override
		public String toString() {
			return "Rotation de " + currentLayer;
		}
	}
}
