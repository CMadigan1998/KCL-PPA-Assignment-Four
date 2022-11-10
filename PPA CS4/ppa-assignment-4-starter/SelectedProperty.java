    import javafx.application.Application;
    import javafx.stage.*;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.layout.*;
    import javafx.event.*;
    import javafx.geometry.Pos;
    
    import java.awt.Desktop;
    import java.net.URI;
    import java.net.URISyntaxException;
/**
 * Class SelectedProperty is responsible for creating a window for a selected property.
 *
 * @author Charlie Madigan (19019003), Justinas Kiskis (K1889820), Carlos Navarro (K19016418)
 */
public class SelectedProperty extends Application
{
    private AirbnbListing currentProperty;
    private Button checkLocation = new Button("Check location");
    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage)
    {
        
    }

    /**
     * Returns room type of selected property
     */
    public void selectProperty(AirbnbListing selectedProperty)
    {
        currentProperty = selectedProperty;

        checkLocation.setOnAction(this::showPropertyOnMap);
        
        VBox root2 = new VBox(getPropertyInfo());
        root2.setAlignment(Pos.CENTER);

        Stage stage2 = new Stage();
        stage2.setTitle(currentProperty.getName());
        stage2.setScene(new Scene(root2, 400, 400));
        stage2.show();
    }
    
    /**
     *  Shows information about selected property.
     */
    public VBox getPropertyInfo()
    {
        Label name = new Label(currentProperty.getName());
        
        Label nameHost = new Label("Host name: " + currentProperty.getHost_name());
        Label neihbourhood = new Label("Borough: " + currentProperty.getNeighbourhood());
        Label roomType = new Label("Room type: " + currentProperty.getRoom_type());
        Label price = new Label("Price: " + Integer.toString(currentProperty.getPrice()));
        Label minimumNights = new Label("Minimum nights to stay: " +Integer.toString(currentProperty.getMinimumNights()));
        Label availability = new Label("Availability: " +Integer.toString(currentProperty.getAvailability365()));
        
        Label numbReviews = new Label(Integer.toString(currentProperty.getNumberOfReviews()) + " reviews");
        Label lastReview = new Label("Last review: " + "\n" + currentProperty.getLastReview());
        Label reviewMonth = new Label("Reviews per month: " + Double.toString(currentProperty.getReviewsPerMonth()));
        
        VBox info = new VBox(name,nameHost,neihbourhood,checkLocation,roomType,price,minimumNights,availability,numbReviews,lastReview,reviewMonth);
        info.setAlignment(Pos.CENTER);
        return info;
    }
    
    /**
     * Is called when "Select property" button is clicked.
     */
    private void showPropertyOnMap(ActionEvent event)
    {
        try{
            getshowPropertyOnMap();
        }
        catch(Exception e){
            // CAN BE REPLACED BY A METHOD FROM ERROR HANDLER CLASS
            // THAT CLASS COULD SHOW ERRORS
            System.out.println("Something is wrong with the property location");
        }
    }
    
    /**
     *  The system's default browser is opened and
     *  the location of selected property is shown
     *  on the Google maps page.
     */
    private void getshowPropertyOnMap() throws Exception
    {
        double latit = currentProperty.getLatitude();
        double longi = currentProperty.getLongitude();
        URI uri = new URI("https://www.google.com/maps/place/" + latit + "," + longi);
        java.awt.Desktop.getDesktop().browse(uri);
    }
}
