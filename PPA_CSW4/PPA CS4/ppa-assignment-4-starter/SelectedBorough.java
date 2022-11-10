import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.*;
import java.io.File;
import java.util.*;
import javafx.application.Application;
import javafx.collections.*;
import javafx.geometry.*;

/**
 * Class SelectedBorough is responsible for a window which is created 
 * for a specific borough.
 *
 * @author Charlie Madigan (19019003), Justinas Kiskis (K1889820), Carlos Navarro (K19016418)
 */
public class SelectedBorough extends Application
{
    private HashMap<String,AirbnbListing> currentAirbnbList;
    private ComboBox airbnbListing;
    private ComboBox sortingOptions;
    Button selectProp = new Button("Select property");
    private ArrayList<String> propertyNames;
    private ArrayList<AirbnbListing> propertyList;
    private ArrayList<AirbnbListing> unsortedList;

    private SelectedProperty selectedProperty = new SelectedProperty();

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
     * Creates a list of sorted properties within selected price range and borough
     */
    public void createListing(ArrayList<AirbnbListing> sortedList)
    {
        VBox sortList = new VBox();
        propertyNames = new ArrayList<String>();
        propertyList = sortedList;
        currentAirbnbList = new HashMap<String,AirbnbListing>();
        airbnbListing = new ComboBox(generateComboListing(propertyList)); 
        airbnbListing.setItems(generateComboListing(propertyList));

        String boroughName = sortedList.get(1).getNeighbourhood();
        Label borough = new Label(boroughName);
        Label info = new Label("List of properties:");
        selectProp.setDefaultButton(true); 
        selectProp.setOnAction(this::selectProperty);

        FlowPane sortingPane = new FlowPane (Orientation.HORIZONTAL, 5, 0);
        sortingPane.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        Button apply = new Button("Apply");
        apply.setOnAction(this::applySort);
        ObservableList<String> options = FXCollections.observableArrayList("Price: Highest", "Price: Lowest", "Reviews: Most Reviewed", "Reviews: Least Reviewed");
        sortingOptions = new ComboBox (options);
        sortingPane.getChildren().addAll(apply, sortingOptions);
        
        sortList.getChildren().addAll(borough,info,airbnbListing,selectProp, sortingPane);
        sortList.setAlignment(Pos.CENTER);


        Stage stage3 = new Stage();
        stage3.setTitle(boroughName);
        stage3.setScene(new Scene(sortList, 750, 200));
        stage3.show();
    }

    /**
     * Sorting is applied to the shown list.
     */
    private void applySort (ActionEvent event) {
        unsortedList = propertyList;
        AirbnbListing tmpHolder;
        ArrayList<String> sortedList = new ArrayList<>();
        if (sortingOptions.getValue().toString() == "Price: Highest") {
            for(int i = 0; i < unsortedList.size() - 1; i++){
                for (int x = 0 ; x < unsortedList.size() - 1; x++) {
                    if (unsortedList.get(x).getPrice() < unsortedList.get(x + 1).getPrice()) {
                        System.out.println("Sorting");
                        tmpHolder = unsortedList.get(x);
                        unsortedList.set(x, unsortedList.get(x+1));
                        unsortedList.set(x+1, tmpHolder);
                    }
                }
            }
            airbnbListing.setItems(generateComboListing(unsortedList));
        } else if (sortingOptions.getValue().toString() == "Price: Lowest") {

            for(int i = 0; i > unsortedList.size() - 1; i++){
                for (int x = 0 ; x > unsortedList.size() - 1; x++) {
                    if (unsortedList.get(x).getPrice() > unsortedList.get(x+1).getPrice()) {
                        tmpHolder = unsortedList.get(x);
                        unsortedList.set(x, unsortedList.get(x+1));
                        unsortedList.set(x+1, tmpHolder);
                    }
                }
            }
            airbnbListing.setItems(generateComboListing(unsortedList));
        } else if (sortingOptions.getValue().toString() == "Reviews: Most Reviewed") {
            for(int i = 0; i > unsortedList.size() - 1; i++){
                for (int x = 0 ; x > unsortedList.size() - 1; x++) {
                    if (unsortedList.get(x).getReviewsPerMonth() < unsortedList.get(x+1).getReviewsPerMonth()) {
                        tmpHolder = unsortedList.get(x);
                        unsortedList.set(x, unsortedList.get(x+1));
                        unsortedList.set(x+1, tmpHolder);
                    }
                }
            }
            airbnbListing.setItems(generateComboListing(unsortedList));
        } else if (sortingOptions.getValue().toString() == "Reviews: Least Reviewed") {
            for(int i = 0; i > unsortedList.size() - 1; i++){
                for (int x = 0 ; x > unsortedList.size() - 1; x++) {
                    if (unsortedList.get(x).getReviewsPerMonth() > unsortedList.get(x+1).getReviewsPerMonth()) {
                        tmpHolder = unsortedList.get(x);
                        unsortedList.set(x, unsortedList.get(x+1));
                        unsortedList.set(x+1, tmpHolder);
                    }
                }
            }
            airbnbListing.setItems(generateComboListing(unsortedList));
        }
    }
    
    /**
     * Creates a combo list in which information about Airbnb 
     * listings in a specific borough is shown.
     */
    private ObservableList<String> generateComboListing (ArrayList<AirbnbListing> propertyListings) {
        ArrayList<String> listings = new ArrayList<>();
        for(AirbnbListing single : propertyListings){
                String stringToShow = single.getName() +" Price: " + 
                    Integer.toString(single.getPrice())+ " Reviews: " +
                    Integer.toString(single.getNumberOfReviews()) + 
                    " Minimum nights: " + Integer.toString(single.getMinimumNights())+" Price: " + 
                    Integer.toString(single.getPrice())+ " Reviews: " +
                    Integer.toString(single.getNumberOfReviews()) + 
                    " Minimum nights: " + Integer.toString(single.getMinimumNights());
                listings.add(stringToShow);
                currentAirbnbList.put(stringToShow, single);
            }

            return FXCollections.observableArrayList(listings);
    }

    /**
     * Creates a new window for a selected property
     */
    private void selectProperty(ActionEvent event)
    {
        String temp = (String) airbnbListing.getValue();
        AirbnbListing airbnbListing = currentAirbnbList.get(temp);
        selectedProperty.selectProperty(airbnbListing);
    }
}
