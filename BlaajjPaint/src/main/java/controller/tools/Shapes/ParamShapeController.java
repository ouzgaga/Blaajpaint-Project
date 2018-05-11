package controller.tools.Shapes;

import controller.tools.Tool;
import controller.tools.ToolDrawer.ToolDrawer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

public class ParamShapeController {


    @FXML
    public ToggleButton filledRect;
    @FXML
    public ToggleButton emptyEllipse;
    @FXML
    public ToggleButton filledEllipse;
    @FXML
    public ToggleButton emptyRect;
    @FXML
    private Slider thicknessSlider;
    @FXML
    private TextField thicknessTextField;
    @FXML
    private HBox paramShapeTools;

    private ShapeDrawer tool = (ShapeDrawer) Tool.getCurrentTool();
    @FXML
    private void initialize() {
        thicknessTextField.setText(String.valueOf(tool.getThickness()));
        thicknessSlider.setValue(tool.getThickness());

        // Handle Slider value change events.
        thicknessSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            tool.setThickness(Double.parseDouble(newValue.toString()));
            thicknessTextField.setText(String.valueOf(tool.getThickness()));
            ((ToolDrawer) Tool.getCurrentTool()).setThickness(tool.getThickness());
        });
        // Handle TextField text changes.
        thicknessTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            tool.setThickness(Double.parseDouble(newValue));
            thicknessSlider.setValue(tool.getThickness());
            ((ToolDrawer) Tool.getCurrentTool()).setThickness(tool.getThickness());
        });
    }

    @FXML
    public void handleEmptyRect(ActionEvent actionEvent) {
        Tool.setCurrentTool(EmptyRectangle.getInstance());
        if (Tool.getToolHasChanged()) {
            Tool.setToolHasChanged(false);
            filledRect.setSelected(false);
            emptyEllipse.setSelected(false);
            filledEllipse.setSelected(false);
        }
    }

    @FXML
    public void handleFilledRect(ActionEvent actionEvent) {
        Tool.setCurrentTool(FilledRectangle.getInstance());
        if (Tool.getToolHasChanged()) {
            Tool.setToolHasChanged(false);
            emptyRect.setSelected(false);
            emptyEllipse.setSelected(false);
            filledEllipse.setSelected(false);
        }
    }

    @FXML
    public void handleEmptyEllipse(ActionEvent actionEvent) {
        Tool.setCurrentTool(EmptyEllipse.getInstance());
        if (Tool.getToolHasChanged()) {
            Tool.setToolHasChanged(false);
            emptyRect.setSelected(false);
            filledRect.setSelected(false);
            filledEllipse.setSelected(false);
        }
    }

    @FXML
    public void handleFilledEllipse(ActionEvent actionEvent) {
        Tool.setCurrentTool(FilledEllipse.getInstance());
        if (Tool.getToolHasChanged()) {
            Tool.setToolHasChanged(false);
            emptyRect.setSelected(false);
            filledRect.setSelected(false);
            emptyEllipse.setSelected(false);
        }
    }
}
