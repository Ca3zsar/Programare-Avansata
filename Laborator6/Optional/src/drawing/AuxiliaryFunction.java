package drawing;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.List;

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

    private static double distance(double x1, double y1, double x2, double y2)
    {
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }

    public static boolean isLine(List<Circle> points)
    {
        int listSize = points.size();
        int correct = 0 ;

        double x1,y1,x2,y2;
        x1 = points.get(0).getCenterX();
        x2 = points.get(listSize-1).getCenterX();
        y1 = points.get(0).getCenterY();
        y2 = points.get(listSize-1).getCenterY();

        double gradient = (y2 - y1) / (x2 - x1);

        for(int i=2;i<listSize-1;i++)
        {
            double currentX = points.get(i).getCenterX();
            double currentY = points.get(i).getCenterY();
            if(Math.abs(distance(currentX,currentY,x1,y1) + distance(currentX,currentY,x2,y2)-distance(x1,y1,x2,y2)) <= 1)
            {
                correct++;
            }
        }

        if(correct >= listSize/10)
        {
            return true;
        }
        return false;
    }

    public static boolean isCircle(List<Circle> points)
    {
        return false;
    }
}
