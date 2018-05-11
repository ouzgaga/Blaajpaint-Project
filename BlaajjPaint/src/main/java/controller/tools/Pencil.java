/*
Author: Benoît
 */
package controller.tools;

import controller.Project;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineJoin;

/**
 * Classe implémentant l'outil pinceau
 */
public class Pencil extends ToolDrawer {
	
	private static Pencil toolInstance = new Pencil(); // l'instance unique du pinceau
	
	/**
	 * Retourne l'instance unique du pinceau
	 *
	 * @return l'instance unique du pinceau
	 */
	public static Pencil getInstance() {
		return toolInstance;
	}

	public class PencilStrike extends Trait {
		public String toString(){
			return "Pencil Strike";
		}
	}
	
	/**
	 * Constructeur privé (modèle singleton)
	 */
	private Pencil() {
		toolType = ToolType.PENCIL;
	}
	
	@Override
	protected EventHandler<MouseEvent> createMousePressedEventHandlers() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				currentTrait = new PencilStrike();
				Project.getInstance().getCurrentLayer().getGraphicsContext2D().beginPath();
				Project.getInstance().getCurrentLayer().getGraphicsContext2D().moveTo(event.getX(), event.getY());
				Project.getInstance().getCurrentLayer().getGraphicsContext2D().setLineWidth(thickness); // définit l'épaisseur du pencil
				Project.getInstance().getCurrentLayer().getGraphicsContext2D().setStroke(Project.getInstance().getCurrentColor()); // définit la couleur du pencil
				Project.getInstance().getCurrentLayer().getGraphicsContext2D().stroke();
			}
		};
	}
	
	@Override
	protected EventHandler<MouseEvent> createMouseDraggedEventHandlers() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Project.getInstance().getCurrentLayer().getGraphicsContext2D().lineTo(event.getX(), event.getY());
				Project.getInstance().getCurrentLayer().getGraphicsContext2D().stroke();
			}
		};
	}
	
	@Override
	protected EventHandler<MouseEvent> createMouseReleasedEventHandlers() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Project.getInstance().getCurrentLayer().getGraphicsContext2D().closePath();
				currentTrait.execute();
			}
		};
	}
}