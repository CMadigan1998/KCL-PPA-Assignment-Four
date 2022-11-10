import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.ArrayList;

/**
 * CreatePropertyEntry class is responsible for listing a new property
 *
 * @author Charlie Madigan (19019003), Justinas Kiskis (K1889820), Carlos Navarro (K19016418)
 */
public class CreatePropertyEntry extends Application
{
    private TextField propertyNameInput, neighbourHoodInput, latitudeInput, longitudeInput, roomTypeInput, priceInput, numberOfNightsInput, avaliabilityInput;
    private AccountEntry currentUser;
    private Stage window;
    private AirbnbListing listings;
    @Override
    public void start(Stage stage)
    {
    }
    
    /**
     *  Creates a window for a property entry.
     */
    public void createWindow(AccountEntry loginAccount) {
        VBox baseBox = new VBox();
        currentUser = loginAccount;
        FlowPane titleBar = new FlowPane();
        Label title = new Label ("Currently Logged in: " + loginAccount.getFirstName() + " " + loginAccount.getSurname() + " (" + loginAccount.getUserID() + ")");
        titleBar.getChildren().addAll(title);
        
        FlowPane propertyNameField = new FlowPane();
        Label propertyNameLabel = new Label("Property Name:");
        propertyNameInput = new TextField();
        propertyNameField.getChildren().addAll(propertyNameLabel, propertyNameInput);
        
        
        FlowPane neighbourHoodField = new FlowPane();
        Label neighbourHoodLabel = new Label("Neighbour Hood:");
        neighbourHoodInput = new TextField();
        neighbourHoodField.getChildren().addAll(neighbourHoodLabel, neighbourHoodInput);
        
        FlowPane latitudeField = new FlowPane();
        Label latitudeLabel = new Label("Latitude:");
        latitudeInput = new TextField();
        latitudeField.getChildren().addAll(latitudeLabel, latitudeInput);
        
        FlowPane longitudeField = new FlowPane();
        Label longitudeLabel = new Label("Longitude:");
        longitudeInput = new TextField();
        longitudeField.getChildren().addAll(longitudeLabel, longitudeInput);
        
        FlowPane roomTypeField = new FlowPane();
        Label roomTypeLabel = new Label("Room Type:");
        roomTypeInput = new TextField();
        roomTypeField.getChildren().addAll(roomTypeLabel, roomTypeInput);
        
        FlowPane priceField = new FlowPane();
        Label priceLabel = new Label("Price:");
        priceInput = new TextField();
        priceField.getChildren().addAll(priceLabel, priceInput);
        
        FlowPane minimumNumberOfNightsField = new FlowPane();
        Label numberOfNightslabel = new Label("Minimum nights:");
        numberOfNightsInput = new TextField();
        minimumNumberOfNightsField.getChildren().addAll(numberOfNightslabel, numberOfNightsInput);
        
        FlowPane avaliability365Field = new FlowPane();
        Label avaliabilityLabel = new Label("Total avaliable days:");
        avaliabilityInput = new TextField();
        avaliability365Field.getChildren().addAll(avaliabilityLabel, avaliabilityInput);
        
        Button create = new Button("Create Listing");
        create.setOnAction(this::createEntry);
        
        Button logout = new Button("Logout");
        logout.setOnAction(this::logout);
        
        baseBox.getChildren().addAll(titleBar, propertyNameField, neighbourHoodField, latitudeField, longitudeField, roomTypeField, priceField, minimumNumberOfNightsField, avaliability365Field, create, logout);
        Scene scene = new Scene(baseBox);
        window = new Stage();
        window.setTitle("Create Listing");
        window.setScene(scene);
        window.show();
        System.out.println("Window Created");
    }
    
    /**
     *  Creates a listing and stores it in a file.
     */
    private void createEntry (ActionEvent Event) {
        String userID = currentUser.getUserID().toString();
        ArrayList<String> data = new ArrayList();
        data.add(propertyNameInput.getText());
        data.add(userID);
        data.add(currentUser.getFirstName() + currentUser.getSurname());
        data.add(neighbourHoodInput.getText());
        data.add(latitudeInput.getText());
        data.add(longitudeInput.getText());
        data.add(roomTypeInput.getText());
        data.add(priceInput.getText());
        data.add(numberOfNightsInput.getText());
        data.add("0");
        data.add("");
        data.add("");
        data.add(listings.findNumOfPropertiesByHost(userID).toString());
        data.add(avaliabilityInput.getText());
        listings.addPropertyToDataBase(data);
    }
    
    /**
     *  Logouts after logout button is pressed
     */
    private void logout (ActionEvent Event) {
        currentUser = null;
        window.close();
    }
}
