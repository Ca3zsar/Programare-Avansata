package drawing;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
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

        for(int i=2;i<listSize-1;i++)
        {
            double currentX = points.get(i).getCenterX();
            double currentY = points.get(i).getCenterY();
            if(Math.abs(distance(currentX,currentY,x1,y1) + distance(currentX,currentY,x2,y2)-distance(x1,y1,x2,y2)) <= 1)
            {
                correct++;
            }
        }

        return correct >= listSize / 10;
    }

    public static boolean isCircle(List<Circle> points)
    {
        double centerX, centerY;
        double x1,x2,x3,y1,y2,y3;

        int listSize = points.size();

        x1 = points.get(0).getCenterX();
        x2 = points.get(listSize/2).getCenterX();
        x3 = points.get(listSize-1).getCenterX();

        y1 = points.get(0).getCenterY();
        y2 = points.get(listSize/2).getCenterY();
        y3 = points.get(listSize-1).getCenterY();

        centerX = ((x1*x1+y1*y1)*(y2-y3) + (x2*x2 + y2*y2)*(y3-y1)+(x3*x3+y3*y3)*(y1-y2))/
                    (2*(x1*(y2-y3)-y1*(x2-x3)+x2*y3-x3*y2));
        centerY = ((x1*x1+y1*y1)*(x3-x2)+(x2*x2+y2*y2)*(x1-x3)+(x3*x3+y3*y3)*(x2-x1))/
                    (2*(x1*(y2-y3)-y1*(x2-x3)+x2*y3-x3*y2));

        double radius = Math.sqrt((centerX-x1)*(centerX-x1) + (centerY-y1)*(centerY-y1));

        int correct = 0;

        for(Circle point : points)
        {
            if((point.getCenterX()-centerX)*(point.getCenterX()-centerX) + (point.getCenterY()-centerY)*(point.getCenterY()-centerY) <= radius*radius)
            {
                correct++;
            }
        }

        PaintController.toGetRadius = radius;
        return correct >= listSize / 10;
    }
}
