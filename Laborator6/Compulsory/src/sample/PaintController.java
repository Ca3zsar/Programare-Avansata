package sample;

import javafx.scene.image.Image;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaintController implements Initializable {
    @FXML
    private ChoiceBox<String> sidesBox;

    @FXML
    private Slider strokeSlide;

    @FXML
    private Slider sizeSlide;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Canvas canvas;

    @FXML
    private BorderPane canvasRoot;

    private GraphicsContext graphicsContext;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> sidesChoices = FXCollections.observableArrayList("Circle", "3", "4", "5", "6", "7", "8");
        sidesBox.setValue("3");
        sidesBox.setItems(sidesChoices);

        graphicsContext = canvas.getGraphicsContext2D();

    }

    private void setPolygonSides(double centerX, double centerY, double radius, String sides) {
        int intSides = Integer.parseInt(sides);

        intSides = Integer.parseInt(sides);
        final double angleStep = Math.PI * 2 / intSides;
        double angle = Math.PI; // assumes one point is located directly beneath the center point

        double[] xArray = new double[intSides];
        double[] yArray = new double[intSides];

        for (int i = 0; i < intSides; i++, angle += angleStep) {
            xArray[i] = Math.sin(angle) * radius + centerX; // x coordinate of the corner
            yArray[i] = Math.cos(angle) * radius + centerY; // y coordinate of the corner
        }

        graphicsContext.setStroke(colorPicker.getValue());
        graphicsContext.setLineWidth(strokeSlide.getValue());
        graphicsContext.strokePolygon(xArray, yArray, intSides);

    }

    private void drawCircle(double x, double y) {
        graphicsContext.setStroke(colorPicker.getValue());
        graphicsContext.setLineWidth(strokeSlide.getValue());
        graphicsContext.strokeOval(x, y, sizeSlide.getValue() / 2, sizeSlide.getValue() / 2);
    }

    @FXML
    private void drawOnClick(MouseEvent click) {
        if ("Circle".equals(sidesBox.getValue())) {
            drawCircle(click.getX(), click.getY());
        } else {
            setPolygonSides(click.getX(), click.getY(), sizeSlide.getValue(), sidesBox.getValue());
        }
    }

    @FXML
    private void resetBoard() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @FXML
    private void exitAction() {
        System.exit(0);
    }

    @FXML
    private void saveAction() {
        WritableImage screenshot = canvas.snapshot(new SnapshotParameters(), null);

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

                graphicsContext.drawImage(image,0.0,0.0);
            }
        }catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

}
