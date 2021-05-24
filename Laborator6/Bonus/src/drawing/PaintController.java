package drawing;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class PaintController implements Initializable {
    private List<Circle> points;
    static public double toGetRadius;

    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private Label strokeLabel;

    @FXML
    private Slider strokeSlide;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Pane centerPane;

    @FXML
    private Pane circlePane;

    @FXML
    private Pane polygonPane;

    @FXML
    private Pane drawingPane;

    @FXML
    private Slider radiusSlider;

    @FXML
    private Slider lengthSlider;

    @FXML
    private ChoiceBox<Integer> sidesBox;

    @FXML
    private Slider brushSize;

    @FXML
    private CheckBox recognition;

    private int elementNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the choice box selection
        ObservableList<String> shapeChoice = FXCollections.observableArrayList("Circle", "Polygon", "Free Drawing");
        choiceBox.setValue("Circle");
        choiceBox.setItems(shapeChoice);

        //Initialize the sides choice box
        ObservableList<Integer> sidesChoice = FXCollections.observableArrayList(3,4,5,6,7,8);
        sidesBox.setItems(sidesChoice);
        sidesBox.setValue(3);

        colorPicker.setValue(Color.BLACK);

        polygonPane.setVisible(false);
        drawingPane.setVisible(false);
        circlePane.setVisible(true);

        choiceBox.getSelectionModel().selectedIndexProperty().addListener((observableValue, first, second) -> {
            switch (choiceBox.getItems().get((Integer) second)) {
                case "Circle" -> {
                    polygonPane.setVisible(false);
                    circlePane.setVisible(true);
                    drawingPane.setVisible(false);
                    strokeLabel.setVisible(true);
                    strokeSlide.setVisible(true);
                }
                case "Polygon" -> {
                    circlePane.setVisible(false);
                    polygonPane.setVisible(true);
                    drawingPane.setVisible(false);
                    strokeLabel.setVisible(true);
                    strokeSlide.setVisible(true);
                }
                default -> {
                    drawingPane.setVisible(true);
                    polygonPane.setVisible(false);
                    circlePane.setVisible(false);
                    strokeLabel.setVisible(false);
                    strokeSlide.setVisible(false);
                }
            }
        });

        this.elementNumber = 0;
        points = new LinkedList<>();

        AuxiliaryFunction.clipPane(centerPane);
    }

    private void drawPolygon(double centerX, double centerY, double radius, int sides) {
        Polygon polygon = new Polygon();

        final double angleStep = Math.PI * 2 / sides;
        double angle = Math.PI; // assumes one point is located directly beneath the center point

        for (int i = 0; i < sides; i++, angle += angleStep) {
            polygon.getPoints().addAll(
                  Math.sin(angle) * radius + centerX, // x coordinate of the corner
                        Math.cos(angle) * radius + centerY // y coordinate of the corner
            );
        }

        polygon.setFill(Color.TRANSPARENT);
        polygon.setStroke(colorPicker.getValue());
        polygon.setStrokeWidth(strokeSlide.getValue());

        AuxiliaryFunction.setEvent(centerPane,polygon);

        centerPane.getChildren().add(polygon);
    }

    private void drawCircle(double x, double y) {
        Circle circle = new Circle(x,y,radiusSlider.getValue()/2);
        circle.setStroke(colorPicker.getValue());
        circle.setStrokeWidth(strokeSlide.getValue());
        circle.setFill(Color.TRANSPARENT);

        centerPane.getChildren().add(circle);

        AuxiliaryFunction.setEvent(centerPane,circle);
    }


    @FXML
    private void freeDraw(MouseEvent click)
    {
        MouseButton button = click.getButton();
        if(button == MouseButton.PRIMARY)
        {
            if("Free Drawing".equals(choiceBox.getValue()))
            {
                Circle newCircle = new Circle(click.getX(),click.getY(),brushSize.getValue());
                newCircle.setFill(colorPicker.getValue());
                AuxiliaryFunction.setEvent(centerPane,newCircle);

                elementNumber++;

                centerPane.getChildren().add(newCircle);
                points.add(newCircle);
            }
        }
    }

    @FXML
    private void drawOnClick(MouseEvent click) {
        MouseButton button = click.getButton();
        if(button == MouseButton.PRIMARY ) {
            if ("Circle".equals(choiceBox.getValue())) {
                drawCircle(click.getX(), click.getY());
            } else if("Polygon".equals(choiceBox.getValue())){
                drawPolygon(click.getX(), click.getY(), lengthSlider.getValue(), sidesBox.getValue());
            }
        }
        this.elementNumber++;
    }

    @FXML
    private void recognize()
    {
        if(recognition.isSelected())
        {
            if(AuxiliaryFunction.isLine(points))
            {
                Line toDraw = new Line();
                toDraw.setStartX(points.get(0).getCenterX());
                toDraw.setStartY(points.get(0).getCenterY());
                toDraw.setEndX(points.get(points.size()-1).getCenterX());
                toDraw.setEndY(points.get(points.size()-1).getCenterY());
                toDraw.setStrokeWidth(brushSize.getValue());
                toDraw.setStroke(colorPicker.getValue());

                centerPane.getChildren().add(toDraw);
                centerPane.getChildren().removeAll(points);
                elementNumber-=points.size()-1;
                System.out.println("HERE");
            }else if(AuxiliaryFunction.isCircle(points)) {
                Circle toDraw = new Circle();
                toDraw.setCenterX(points.get(0).getCenterX());
                toDraw.setCenterY(points.get(0).getCenterY());
                toDraw.setRadius(toGetRadius);
                toDraw.setFill(Color.TRANSPARENT);
                toDraw.setStroke(colorPicker.getValue());
                toDraw.setStrokeWidth(brushSize.getValue());

                centerPane.getChildren().add(toDraw);
                centerPane.getChildren().removeAll(points);
                elementNumber-=points.size()-1;
            }
        }
        points.clear();
    }

    @FXML
    private void resetBoard() {
        centerPane.getChildren().clear();
        elementNumber = 0;
    }

    @FXML
    private void exitAction() {
        System.exit(0);
    }

    @FXML
    private void undoAction()
    {
        if(elementNumber != 0) {
            elementNumber--;
            int length = centerPane.getChildren().size() - 1;
            centerPane.getChildren().remove(length);
        }
    }

    @FXML
    private void saveAction() {
        WritableImage screenshot = centerPane.snapshot(new SnapshotParameters(), null);

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File toSaveFile = fileChooser.showSaveDialog(new Stage());

        try {
            if (toSaveFile != null) {
                ImageIO.write(SwingFXUtils.fromFXImage(screenshot, null), "png", toSaveFile);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void loadAction()
    {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files (*.png)", "*.jpg");

        fileChooser.getExtensionFilters().add(pngFilter);
        fileChooser.getExtensionFilters().add(jpgFilter);

        File toLoadFile = fileChooser.showOpenDialog(new Stage());

        try{
            if(toLoadFile != null)
            {
                BufferedImage bufferedImage = ImageIO.read(toLoadFile);

                Image image = SwingFXUtils.toFXImage(bufferedImage, null);

                centerPane.getChildren().add(new ImageView(image));
            }
        }catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

}
