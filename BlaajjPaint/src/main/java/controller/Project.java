package controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.util.LinkedList;
import javafx.scene.paint.Color;
//import javafx.scene.layout.StackPane;

public class Project {
	private Dimension dimension;
	private LinkedList<Layer> layers = new LinkedList<>();
	private Layer currentLayer;
	private MainViewController mainViewController;
	
	private GraphicsContext gc;
	//private StackPane pane = new StackPane();

	private Color currentColor;
	
	private static Project projectInstance = new Project();
	
	public static Project getInstance() {
		return projectInstance;
	}
	
	private Project() {
		currentColor = Color.BLACK;
	}
	
	public void setData (int width, int height, MainViewController mainViewController){
		this.mainViewController = mainViewController;
		dimension = new Dimension(width, height);
		currentLayer = new Layer(width, height);
		gc = currentLayer.getGraphicsContext2D();
		layers.add(currentLayer);
		mainViewController.getRightMenuController().updateLayerList();
		draw();
	}
	
	public Canvas getCurrentCanvas(){
		return currentLayer;
	}
	
	private void draw() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, currentLayer.getWidth(), currentLayer.getHeight());
		currentLayer.toFront();
		currentLayer.setVisible(true);
		
		mainViewController.showCanvas(currentLayer);
	}

	public void setCurrentColor(Color color){
		currentColor = color;
	}

	public Color getCurrentColor(){
		return currentColor;
	}

	public void addLayer(Layer newLayer){
		layers.add(newLayer);
	}

	public LinkedList<Layer> getLayers(){
		return layers;
	}

	public void setCurrentLayer(Layer currentLayer) {
		this.currentLayer = currentLayer;
	}

	public Dimension getDimension(){
		return dimension;
	}
}
