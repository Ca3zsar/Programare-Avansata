package drawing;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class AuxiliaryFunction {

    /**
     * Clip a region to a rectangle in order not to draw outside the border
     * @param region the region to be clipped
     */
    static public void clipPane(Region region)
    {
        final Rectangle outputClip = new Rectangle();
        outputClip.setWidth(600);
        outputClip.setHeight(449.6);
        region.setClip(outputClip);

        region.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            outputClip.setWidth(newValue.getWidth());
            outputClip.setHeight(newValue.getHeight());
        });
    }

    static public void setEvent(Pane region, Shape shape)
    {
        shape.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                region.getChildren().remove(shape);
            }
        });
    }

    static public void switchScene(VBox toShow)
    {
        StackPane.setAlignment(toShow,Pos.BASELINE_CENTER);
    }

    static public void initiateCirclePane(Pane specificPanel, VBox circlePanel)
    {
        circlePanel = new VBox();
        Label radiusLabel = new Label("Radius");

        radiusLabel.setPrefWidth(specificPanel.getPrefWidth());
        radiusLabel.setAlignment(Pos.CENTER);

        Slider radiusSlider = new Slider();
        radiusSlider.setPrefWidth(specificPanel.getPrefWidth());
        radiusSlider.setValue(2);
        radiusSlider.setMax(100);
        radiusSlider.setMin(2);
        radiusSlider.setShowTickMarks(true);

        circlePanel.getChildren().add(radiusLabel);
        circlePanel.getChildren().add(radiusSlider);

        specificPanel.getChildren().add(circlePanel);
    }
}
