package sample;

import javafx.scene.image.Image;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
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
    private Pane centerPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> sidesChoices = FXCollections.observableArrayList("Circle", "3", "4", "5", "6", "7", "8","CustomTriangle");
        sidesBox.setValue("3");
        sidesBox.setItems(sidesChoices);

        clipPane(centerPane,600,449.6);
    }

   private void clipPane(Region region, double width, double height)
   {
       final Rectangle outputClip = new Rectangle();
       outputClip.setWidth(width);
       outputClip.setHeight(height);
       region.setClip(outputClip);

       region.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
           outputClip.setWidth(newValue.getWidth());
           outputClip.setHeight(newValue.getHeight());
       });
   }

    private void drawPolygon(double centerX, double centerY, double radius, String sides) {
        int intSides;
        intSides = Integer.parseInt(sides);

        Polygon polygon = new Polygon();

        final double angleStep = Math.PI * 2 / intSides;
        double angle = Math.PI; // assumes one point is located directly beneath the center point

        for (int i = 0; i < intSides; i++, angle += angleStep) {
            polygon.getPoints().addAll(
                  Math.sin(angle) * radius + centerX, // x coordinate of the corner
                        Math.cos(angle) * radius + centerY // y coordinate of the corner
            );
        }

        polygon.setFill(Color.TRANSPARENT);
        polygon.setStroke(colorPicker.getValue());
        polygon.setStrokeWidth(strokeSlide.getValue());

        centerPane.getChildren().add(polygon);
    }

    private void drawCircle(double x, double y) {
        Circle circle = new Circle(x,y,sizeSlide.getValue()/2);
        circle.setStroke(colorPicker.getValue());
        circle.setStrokeWidth(strokeSlide.getValue());
        circle.setFill(Color.TRANSPARENT);

        centerPane.getChildren().add(circle);
    }

    @FXML
    private void drawOnClick(MouseEvent click) {
        if ("Circle".equals(sidesBox.getValue())) {
            drawCircle(click.getX(), click.getY());
        } else {
            drawPolygon(click.getX(), click.getY(), sizeSlide.getValue(), sidesBox.getValue());
        }
    }

    @FXML
    private void resetBoard() {
        centerPane.getChildren().clear();
    }

    @FXML
    private void exitAction() {
        System.exit(0);
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
