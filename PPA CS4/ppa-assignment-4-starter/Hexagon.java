import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.effect.*;
/**
 * Hexagon class is responsible for hexagon creation
 *
 * @author Charlie Madigan (19019003), Justinas Kiskis (K1889820)
 */
public class Hexagon extends Polygon{
    private int xStartOffset = 30; // offsets the entire field to the right
    private int yStartOffset = 30; // offsets the entire fiels downwards
    
    private final static double r = 25; // the inner radius from hexagon center to outer corner
    private final static double n = Math.sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis
    private final static double HEXAGON_HEIGHT = 2 * r;
    private final static double HEXAGON_WIDTH = 2 * n;
    
    private Boolean off = false;
    
    Hexagon(double x, double y) {
        // creates the polygon using the corner coordinates
        getPoints().addAll(
                x, y,
                x, y + r,
                x + n, y + r * 1.5,
                x + HEXAGON_WIDTH, y + r,
                x + HEXAGON_WIDTH, y,
                x + n, y - r * 0.5
        );
        
    }
    
    /**
     * Sets the Hexagon to desired boolean value (either true or valse).
     */
    public void setOff(Boolean value)
    {
        off = value;
    }
    
    /**
     * Returns current state of hexagon (whether it's turned off or not).
     */
    public Boolean isOff()
    {
        return off;
    }
    }
