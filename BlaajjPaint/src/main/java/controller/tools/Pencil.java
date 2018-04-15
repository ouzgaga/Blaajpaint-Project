package controller.tools;

import controller.ICmd;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.Layer;
import model.RecordCmd;
import utils.UndoException;

public class Pencil extends LayerController implements ICmd {

    Image undosave = null;
    Image redosave = null;
    SnapshotParameters params;

    WritableImage pencil = null;

    EventHandler<MouseEvent> mousedrag;
    EventHandler<MouseEvent> mouserelease;


    public Pencil(Canvas canvas) {
        // stock le cnaevas dans le parent
        super(canvas);

        // définit le pinceau qui sera utilisé par l'évènement de drag pour colorier le canvas
        pencil = new WritableImage(1,1);
        pencil.getPixelWriter().setColor(0,0, Color.BLACK);

        // configuration des paramètres utilisés pour la sauvegarde du canevas
        params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        // exécute le snapshot de l'état actuel du canvas
        undosave = canvas.snapshot(params, null);

        // définit un handler qui est utilisé pour dessiner sur le canvas et l'ajoute au canvas
        mousedrag =  new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                canvas.getGraphicsContext2D().drawImage(pencil, event.getX(), event.getY());
            }
        };
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, mousedrag);

        // definit un event qui est utilisé pour gérer le release du bouton de la souric sur le canvas
        mouserelease =  new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                execute();
                canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, mousedrag);
                canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, mouserelease);
            }
        };
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, mouserelease);
    }


    @Override
    public void execute() {
        RecordCmd.getInstance().saveCmd(this);
    }

    @Override
    public void undo() throws UndoException {
        if(undosave == null){
            throw new UndoException();
        }
        redosave = canvas.snapshot(params, null);
        getGraphics().drawImage(undosave, 0,0);
        undosave = null;
    }

    @Override
    public void redo() throws UndoException {
        if(redosave == null){
            throw new UndoException();
        }
        undosave = canvas.snapshot(params, null);
        getGraphics().drawImage(redosave, 0,0);
        redosave = null;
    }


}