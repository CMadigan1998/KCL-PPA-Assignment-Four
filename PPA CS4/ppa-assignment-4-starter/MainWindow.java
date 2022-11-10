import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.input.*;
import javafx.scene.image.*;
import javafx.event.*;
import javafx.scene.control.Alert.AlertType;
import java.io.File;
import java.util.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.geometry.Orientation;
import javafx.geometry.NodeOrientation;
import javafx.collections.*;
import java.util.HashMap;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.effect.*;
import javafx.scene.text.Text;
/**
 * Main window of application
 *
 * @author Charlie Madigan (19019003), Justinas Kiskis (K1889820), Carlos Navarro (K19016418), Monjur Mirza (K1921571)
 * 
 */
public class MainWindow extends Application {
    private FileIO fileReader = new FileIO();

    private static final int DEFAULT_HEIGHT = 500;
    private static final int DEFAULT_WIDTH = 400;
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 800;
    private final int MAX_PANEL_NUMBER = 4 - 1;
    private final int DEFAULT_PANEL_NUMBER = 0;
    private final String ACCOUNT_DATABASE = "Account_Information.csv";

    //From Map class
    private int xStartOffset = 30; // offsets the entire field to the right
    private int yStartOffset = 30; // offsets the entire fiels downwards
    private AnchorPane hexagonMap = new AnchorPane();
    private final static double r = 25; // the inner radius from hexagon center to outer corner
    private final static double n = Math.sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis
    private final static double HEXAGON_HEIGHT = 2 * r;
    private final static double HEXAGON_WIDTH = 2 * n;
    private FlowPane map;
    //From Map class
    
    private HashMap<String,String> boroughNames = new HashMap<String,String>();

    private int currentMinPrice, currentMaxPrice;
    private boolean notBasicStat = false;
    private SortingAirbnbLists sortingAirbnbLists = new SortingAirbnbLists();
    private AccountManagement accountManagement = new AccountManagement();

    private HashMap<String, Integer> boroughPropertiesNumber = new HashMap<>();

    private boolean priceSelected = false;
    private Stage stage;
    private Scene scene;
    //Define two new combo boxes that will be used to show the price range drop down menus
    private ComboBox minPriceRange, maxPriceRange;
    private VBox panel2;
    //Button which applies selected Price Range
    private Button submitPriceRange = new Button(); 
    private Button selectProp = new Button("Select property");

    private SelectedBorough selectedBorough = new SelectedBorough();

    private EventHandler<ActionEvent> setMaxPrice;
    private EventHandler<ActionEvent> sorting;

    private int panelNumber = DEFAULT_PANEL_NUMBER;

    private BorderPane contentPane;
    //Create a new list of string variables that will be used to populate the combo boxes
    private ObservableList<Integer> pricesMin = FXCollections.observableArrayList(0,100,200,300,400,500,600,700,800);
    private ObservableList<Integer> pricesMax;
    private HashMap<String,AirbnbListing> currentAirbnbList; 
    private ArrayList<AirbnbListing> currentPriceRangeList;

    private ComboBox airbnbListing;
    private ComboBox pref1;
    private Button next, previous;

    private Deque<String> current1StatBox;
    private Deque<String> current2StatBox;
    private Deque<String> current3StatBox;
    private Deque<String> current4StatBox;
    private Deque<String> previousStatBoxes;

    private BorderPane statisticsBox1;
    private BorderPane statisticsBox2;
    private BorderPane statisticsBox3;
    private BorderPane statisticsBox4;

    private Label priceRangeTxt = new Label("");
    private TextField usernameInput,firstNameInput,surnameInput,createUsernameInput,createPasswordInput; 
    private PasswordField passwordInput;

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
        this.stage = stage;
        accountManagement.checkForLoginDatabase(ACCOUNT_DATABASE);
        AirbnbListing.setNewPropertyID(AirbnbDataLoader.getNumberOfProperties());

        VBox root = new VBox();


        createBoroughNames();
        map = new FlowPane(hexagonMap);
                
                

        Label from = new Label("From:");
        Label to = new Label("To:");

        minPriceRange = new ComboBox(pricesMin);           //Initialised the combo boxes with the String List
        maxPriceRange = new ComboBox(pricesMax);           //Initialised the combo boxes with the String List

        //When min price is selected, the choices of max price is adjusted
        minPriceRange.setOnAction(setMaxPrice ->{
                ArrayList<Integer> tempArray = new ArrayList<Integer>();
                pricesMax = null;
                for(Integer numb : pricesMin){
                    if(numb > (Integer) minPriceRange.getValue()){
                        tempArray.add(numb);
                    }
                }
                pricesMax = FXCollections.observableArrayList(tempArray);
                maxPriceRange.setItems(pricesMax);
            });

        submitPriceRange.setMaxWidth(Double.MAX_VALUE);
        submitPriceRange.setText("Apply");
        submitPriceRange.setDefaultButton(true);        //Whenever the user hits the enter key, this button is fired
        submitPriceRange.setOnAction(this::applyPriceRange); //Whenever the button is fired, it calls the button clicked function
        //Price range panel
        FlowPane priceRange = new FlowPane(Orientation.HORIZONTAL, 5, 0);
        priceRange.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        priceRange.getChildren().addAll(maxPriceRange, to, minPriceRange, from, submitPriceRange);

        //Navigation buttons for switching panels
        next = new Button("Next");
        next.setMaxWidth(Double.MAX_VALUE);
        next.setDisable(true);
        next.setOnAction(this::switchPanelRight);//Whenever the button is fired, it callss the button clicked function

        previous = new Button("Previous");
        previous.setMaxWidth(Double.MAX_VALUE);
        previous.setDisable(true);
        previous.setOnAction(this::switchPanelLeft);//Whenever the button is fired, it callss the button clicked function

        previous.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        next.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        //AnchorPane that stores navigation buttons
        AnchorPane navigation = new AnchorPane();
        navigation.getChildren().addAll(next, previous); //edited
        AnchorPane.setRightAnchor(next, 20.0);
        AnchorPane.setLeftAnchor(previous, 20.0);

        //Whole window design
        contentPane = new BorderPane(null,priceRange,null,navigation,null);
        root.getChildren().add(contentPane);
        switchPanel();

        Scene scene = new Scene(root, DEFAULT_HEIGHT, DEFAULT_WIDTH);

        stage.setTitle("Airbnb");
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     *  Creates a panel for loging in.
     */
    private void createLoginPanel () {
        VBox loginPanel = new VBox();
        FlowPane userNameField = new FlowPane(Orientation.HORIZONTAL, 5, 0);
        usernameInput = new TextField();
        Label userNameLabel = new Label("Username:");
        userNameField.getChildren().addAll(userNameLabel, usernameInput);
        FlowPane passwordField = new FlowPane(Orientation.HORIZONTAL, 5, 0);
        passwordInput = new PasswordField();
        Label passwordLabel = new Label("Password:");
        userNameField.getChildren().addAll(passwordLabel, passwordInput);
        Button login = new Button("Login");
        login.setOnAction(this::loginCall);
        Button createAccountBtn = new Button("Create Account");
        createAccountBtn.setOnAction(this::createAccount);
        loginPanel.getChildren().addAll(userNameField, passwordField, login, createAccountBtn);
        contentPane.setCenter(loginPanel);
    }
    
    /**
     * When a login button is called, check whether entered 
     * login details are correct.
     */
    private void loginCall(ActionEvent Event) {
        if (accountManagement.checkAccountDetails(usernameInput.getText(), passwordInput.getText())) {
            CreatePropertyEntry propertyEntry = new CreatePropertyEntry();
            propertyEntry.createWindow(accountManagement.getLoginAccount());
        } else {
            System.out.println("There was an error logging into your account. Please check your details and try again");
        }
    }
    
    /**
     *  Creates a window for registering new user.
     */
    private void createAccount (ActionEvent Event) {
        VBox createAccountBox = new VBox();
        
        FlowPane firstNameField = new FlowPane();
        Label firstNameLabel = new Label("First Name:");
        firstNameInput = new TextField();
        firstNameField.getChildren().addAll(firstNameLabel, firstNameInput);
        
        FlowPane surnameField = new FlowPane();
        Label surnameLabel = new Label("Surname:");
        surnameInput = new TextField();
        surnameField.getChildren().addAll(surnameLabel, surnameInput);
        
        FlowPane usernameField = new FlowPane();
        Label usernameLabel = new Label("Username:");
        createUsernameInput = new TextField();
        usernameField.getChildren().addAll(usernameLabel, createUsernameInput);
        
        FlowPane passwordField = new FlowPane();
        Label passwordLabel = new Label("Password:");
        createPasswordInput = new PasswordField();
        passwordField.getChildren().addAll(passwordLabel, createPasswordInput);
        
        FlowPane buttonsPane = new FlowPane();
        Button createAccountData = new Button("Create Account");
        createAccountData.setOnAction(this::createAccountData);
        Button goBackBtn = new Button("Cancel");
        goBackBtn.setOnAction(this::cancelAccountCreation);
        buttonsPane.getChildren().addAll(goBackBtn, createAccountData);
        
        createAccountBox.getChildren().addAll(firstNameField, surnameField, usernameField, passwordField, buttonsPane);
        contentPane.setCenter(createAccountBox);
    }
    
    /**
     *  Creates a new user account and stores it in a file.
     */
    private void createAccountData(ActionEvent Event) {
        ArrayList<String> data = new ArrayList<>();
        data.add(firstNameInput.getText());
        data.add(surnameInput.getText());
        data.add(createUsernameInput.getText());
        data.add(createPasswordInput.getText());
        accountManagement.writeLoginDatabase(ACCOUNT_DATABASE,data,false);
        createLoginPanel();
    }
    
    /**
     *  Cancels new user creation.
     */
    private void cancelAccountCreation(ActionEvent Event) {
        createLoginPanel();
    }

    /**
     * Creates panel which shows welcome information and selected price range.
     */
    private void createPanel0()
    {
        //Information labels for center panel
        fileReader.getFile("welcome_information.txt");
        Label infoPriceRange = new Label(fileReader.readData());

        //Center panel
        VBox panel = new VBox();
        panel.getChildren().addAll(infoPriceRange, priceRangeTxt);
        panel.setAlignment(Pos.CENTER);
        contentPane.setCenter(panel);
    }

    /**
     * This will be executed when the price range apply button is called
     */
    private void applyPriceRange(ActionEvent event)
    {   
        if(getMinPriceRange() >= MIN_VALUE && getMaxPriceRange() <= MAX_VALUE){
            priceSelected = true;
            //there is no point of calling this method here!!!
            sortingAirbnbLists.sortPriceRange(getMinPriceRange(), getMaxPriceRange());
            switchPanel();
            next.setDisable(false);
            submitPriceRange.setDefaultButton(false);
            next.setDefaultButton(true);
            previous.setDisable(false);
            priceRangeTxt.setText("Current price range: £" + getMinPriceRange() + " - £" + getMaxPriceRange());
        }
        else {
            priceSelected = false;
        }
    }

    /**
     * Creates information for statistic panel.
     */
    private void createStatistics()
    {
        current1StatBox = new ArrayDeque<>();
        current2StatBox = new ArrayDeque<>();
        current3StatBox = new ArrayDeque<>();
        current4StatBox = new ArrayDeque<>();
        previousStatBoxes = new ArrayDeque<>();
        createStatisticsBoxes();

        String borough = "Most expensive borough: ";
        String available = "Available properties: ";
        String homeProp = "Home/Apartament properties: ";
        String avrgRew = "Average number of reviews: ";
        String avrgNight = "Average number of nights: ";
        String highList = "Highest number of properties by one host";
        String privProp = "Private properties: ";
        String sharProp = "Shared properties: ";

        String infoBorough = borough +
            sortingAirbnbLists.getMostExpensiveBorough();
        String infoAvailable = available +
            Integer.toString(sortingAirbnbLists.getNumberAvailableProp());
        String infoHome = homeProp +
            Integer.toString(sortingAirbnbLists.getNumberHomeApartments());
        String infoReviews = avrgRew +
            Integer.toString(sortingAirbnbLists.getNumberAverageReviews());

        String basicInfoNights = avrgNight +
            Integer.toString(sortingAirbnbLists.getNumberAverageNights());
        String basicInfoHighest = highList +
            Integer.toString(sortingAirbnbLists.getNumberHighestListing());
        String basicInfoPrivate = privProp +
            Integer.toString(sortingAirbnbLists.getNumberPrivateRooms());
        String basicInfoShared = sharProp + 
            Integer.toString(sortingAirbnbLists.getNumberSharedRooms());

        current1StatBox.add(infoBorough);
        current2StatBox.add(infoAvailable);
        current3StatBox.add(infoHome);
        current4StatBox.add(infoReviews);

        switchInfoStatBox(statisticsBox1, infoBorough);
        switchInfoStatBox(statisticsBox2, infoAvailable);
        switchInfoStatBox(statisticsBox3, infoHome);
        switchInfoStatBox(statisticsBox4, infoReviews);

        previousStatBoxes.add(basicInfoNights);
        previousStatBoxes.add(basicInfoHighest);
        previousStatBoxes.add(basicInfoPrivate);
        previousStatBoxes.add(basicInfoShared);
    }

    /**
     * Creates a panel with statistics about properties within selected price range
     */
    private void createPanel2()
    {
        createStatistics();
        VBox statistics = new VBox();
        Label info = new Label("Statistics:");
        statistics.getChildren().addAll(info,createPreferences(),statisticsBox1,
            statisticsBox2,statisticsBox3,statisticsBox4);
        statistics.setAlignment(Pos.CENTER);
        contentPane.setCenter(statistics);
        notBasicStat = true;
    }

    /**
     * Creates preference options for statistics
     */
    private HBox createPreferences()
    {
        ArrayList<String> pref1List = new ArrayList<String>();
        pref1List.add("No preference");
        pref1List.add("Room type");
        pref1List.add("Price");
        pref1List.add("Need ideas for this :)");
        ObservableList<String> pref1ObsList = FXCollections.observableArrayList(pref1List);

        pref1 = new ComboBox(pref1ObsList);
        pref1.setValue(pref1List.get(0));
        pref1.setOnAction(e -> setPreferences());
        Label info = new Label("Your preference for statistics: ");
        HBox preferences = new HBox(info,pref1);
        preferences.setAlignment(Pos.CENTER);
        return preferences;
    }

    /**
     * Sets user's selected preferences and creates
     * statistics
     */
    private void setPreferences()
    {
        String temp = (String) pref1.getValue();
        if(temp.equals("Room type")){
            setForRoomType();
        }
        if(temp.equals("Price")){
            setForPriceType();
        }
        System.out.println("setPreferences()");
    }

    /**
     *  Creates a statistics for room type preference.
     */
    private void setForRoomType()
    {
        previousStatBoxes = new ArrayDeque<>();
        previousStatBoxes.add(sortingAirbnbLists.getString1());
        previousStatBoxes.add(sortingAirbnbLists.getString2());
        previousStatBoxes.add(sortingAirbnbLists.getString3());
        previousStatBoxes.add(sortingAirbnbLists.getString4());
    }

    /**
     *  Creates a statistics for price type preference.
     */
    private void setForPriceType()
    {
        previousStatBoxes = new ArrayDeque<>();
        previousStatBoxes.add(sortingAirbnbLists.getString5());
        previousStatBoxes.add(sortingAirbnbLists.getString6());
        previousStatBoxes.add(sortingAirbnbLists.getString7());
        previousStatBoxes.add(sortingAirbnbLists.getString8());
    }

    /**
     * Changes the statistics box
     */
    private void goRightStatistics1(ActionEvent event)
    {
        String tempInfo = current1StatBox.remove();
        String nextInfo = previousStatBoxes.removeFirst();
        current1StatBox.add(nextInfo);
        previousStatBoxes.addLast(tempInfo);
        switchInfoStatBox(statisticsBox1, nextInfo);
    }

    /**
     * Changes the statistics box
     */
    private void goLeftStatistics1(ActionEvent event)
    {
        String tempInfo = current1StatBox.remove();
        String nextInfo = previousStatBoxes.removeLast();
        current1StatBox.add(nextInfo);
        previousStatBoxes.addFirst(tempInfo);
        switchInfoStatBox(statisticsBox1, nextInfo);
    }

    /**
     * Changes the statistics box
     */
    private void goRightStatistics2(ActionEvent event)
    {
        String tempInfo = current2StatBox.remove();
        String nextInfo = previousStatBoxes.removeFirst();
        current2StatBox.add(nextInfo);
        previousStatBoxes.addLast(tempInfo);
        switchInfoStatBox(statisticsBox2, nextInfo);
    }

    /**
     * Changes the statistics box
     */
    private void goLeftStatistics2(ActionEvent event)
    {
        String tempInfo = current2StatBox.remove();
        String nextInfo = previousStatBoxes.removeLast();
        current2StatBox.add(nextInfo);
        previousStatBoxes.addFirst(tempInfo);
        switchInfoStatBox(statisticsBox2, nextInfo);
    }

    /**
     * Changes the statistics box
     */
    private void goRightStatistics3(ActionEvent event)
    {
        String tempInfo = current3StatBox.remove();
        String nextInfo = previousStatBoxes.removeFirst();
        current3StatBox.add(nextInfo);
        previousStatBoxes.addLast(tempInfo);
        switchInfoStatBox(statisticsBox3, nextInfo);
    }

    /**
     * Changes the statistics box
     */
    private void goLeftStatistics3(ActionEvent event)
    {
        String tempInfo = current3StatBox.remove();
        String nextInfo = previousStatBoxes.removeLast();
        current3StatBox.add(nextInfo);
        previousStatBoxes.addFirst(tempInfo);
        switchInfoStatBox(statisticsBox3, nextInfo);
    }

    /**
     * Changes the statistics box
     */
    private void goRightStatistics4(ActionEvent event)
    {
        String tempInfo = current4StatBox.remove();
        String nextInfo = previousStatBoxes.removeFirst();
        current4StatBox.add(nextInfo);
        previousStatBoxes.addLast(tempInfo);
        switchInfoStatBox(statisticsBox4, nextInfo);
    }

    /**
     * Changes the statistics box
     */
    private void goLeftStatistics4(ActionEvent event)
    {
        String tempInfo = current4StatBox.remove();
        String nextInfo = previousStatBoxes.removeLast();
        current4StatBox.add(nextInfo);
        previousStatBoxes.addFirst(tempInfo);
        switchInfoStatBox(statisticsBox4, nextInfo);
    }

    /**
     * Changes information with the given information in given panel.
     */
    private void switchInfoStatBox(BorderPane pane, String info)
    {
        pane.setCenter(null);
        Label temp = new Label(info);
        System.out.println(info);
        pane.setCenter(temp);
    }

    /**
     * Creates 4 panels for statistics.
     */
    private void createStatisticsBoxes()
    {
        Button right = new Button("Next");
        right.setOnAction(this::goRightStatistics1);
        Button left = new Button("Prev");
        left.setOnAction(this::goLeftStatistics1);
        statisticsBox1 = new BorderPane(null,null,right,null,left);

        // TEST BY DELETING REPETETIVE BUTTONS

        Button right1 = new Button("Next");
        right1.setOnAction(this::goRightStatistics2);
        Button left1 = new Button("Prev");
        left1.setOnAction(this::goLeftStatistics2);
        statisticsBox2 = new BorderPane(null,null,right1,null,left1);

        Button right2 = new Button("Next");
        right2.setOnAction(this::goRightStatistics3);
        Button left2 = new Button("Prev");
        left2.setOnAction(this::goLeftStatistics3);
        statisticsBox3 = new BorderPane(null,null,right2,null,left2);

        Button right3 = new Button("Next");
        right3.setOnAction(this::goRightStatistics4);
        Button left3 = new Button("Prev");
        left3.setOnAction(this::goLeftStatistics4);
        statisticsBox4 = new BorderPane(null,null,right3,null,left3);
    }

    /**
     * Returns selected minimum price
     */
    private int getMinPriceRange()
    {
        return (Integer) minPriceRange.getValue();
    }

    /**
     * Returns selected maximum price
     */
    private int getMaxPriceRange()
    {
        return (Integer) maxPriceRange.getValue();
    }

    /**
     * When this method is controls which panel should be created
     */
    private void switchPanel()
    {
        if(panelNumber == 0){
            createPanel0();
        }
        else if(panelNumber == 1){
            createPanel1();
        }
        else if(panelNumber == 2){
            createPanel2();
        } else if (panelNumber == 3) {
            createLoginPanel();
        }
    }

    /**
     * Returns number of panel which is currently shown.
     */
    private int getPanelNumber()
    {
        if(panelNumber > MAX_PANEL_NUMBER){
            return panelNumber = DEFAULT_PANEL_NUMBER;
        }
        else if(panelNumber < 0){
            return panelNumber = MAX_PANEL_NUMBER;
        }
        return panelNumber;
    }

    /**
     * Switch panels to next one on the right
     */
    private void switchPanelRight(ActionEvent event)
    {
        panelNumber++;
        getPanelNumber();
        if(priceSelected){
            System.out.println("Next");
            switchPanel();
        }
    }

    /**
     * Switch panels to next one on the left
     */
    private void switchPanelLeft(ActionEvent event)
    {
        panelNumber--;
        getPanelNumber();
        if(priceSelected){
            System.out.println("Previous");
            switchPanel();
        }
    }

    /**
     * Calls method from the different class which is responsible for new window.
     */
    private void createListing(ArrayList<AirbnbListing> sortedList)
    {
        selectedBorough.createListing(sortedList);
    }

    /**
     * Creates panel which shows welcome information and selected price range.
    */
    private void setStyleBoroughButton(Hexagon button, Text text)
    {
        String name = text.getText();
        System.out.println(name);
        ArrayList<AirbnbListing> tempList = 
        sortingAirbnbLists.getSortedBoroughList(boroughNames.get(name));
        System.out.println(name);
        Tooltip size = new Tooltip("Properties: " + 
        Integer.toString(tempList.size()));
        Tooltip.install(button,size);
        Tooltip.install(text,size);
        if(tempList.size() == 0) {
            System.out.println("==0");
            button.setOff(true);
            button.setFill(Color.WHITE);
            text.setFill(Color.BLACK);
        } else if (tempList.size() <= 100) {
            System.out.println("<=100");
            button.setFill(Color.PALEGREEN.darker());
        } else if (tempList.size() <= 250) {
            button.setFill(Color.PALEGREEN.darker().darker());
        } else if (tempList.size() <= 500) {
            button.setFill((((Color)button.getFill()).brighter()));
        } else if (tempList.size() <= 750) {
            button.setFill((((Color)button.getFill())));
        } else if (tempList.size() <= 2000) {
            button.setFill(((Color)button.getFill()).darker());
        } else {
            button.setFill(((Color)button.getFill()).darker().darker());
        }
    }
    
    /**
     *  Sets hexagon on action, when pressed creates
     *  a new window with the list of properties from the selected borough.
     */
    public void hexagonOnAction(String name)
    {
        ArrayList<AirbnbListing> tempList = 
        sortingAirbnbLists.getSortedBoroughList(boroughNames.get(name));
        createListing(tempList);
    }
    
    /**
     *  Creates a panel 1, borough map.
     */
    private void createPanel1()
    {
        createMap();
        contentPane.setCenter(map);
    }
    
    /**
     *  Creates a statistics for room type preference.
     */
    public void setHexagon(String name, int x, int y)
    {
        double xAxis = x * HEXAGON_WIDTH + (y % 2) * n + xStartOffset;
        double yAxis = y * HEXAGON_HEIGHT * 0.75 + yStartOffset;
        Hexagon hexagon = new Hexagon(xAxis, yAxis);
        hexagon.setFill(Color.SEAGREEN);
        hexagon.setStroke(Color.BLACK);
        hexagon.setStrokeWidth(2);
        Text text = new Text(name);
        text.setFill(Color.WHITE);
        
        text.setOnMouseEntered(e -> {if(!(hexagon.isOff())){text.setEffect(new GaussianBlur(1.0));
        hexagon.setStroke(Color.GREY);
        hexagon.setFill(((Color)hexagon.getFill()).brighter());}});
        
        text.setOnMouseExited(e -> {if(!(hexagon.isOff())){text.setEffect(new GaussianBlur(0.0));
        hexagon.setStroke(Color.BLACK);
        hexagon.setFill(((Color)hexagon.getFill()).darker());}});
        
        hexagon.setOnMouseEntered(e -> {if(!(hexagon.isOff())){hexagon.setStroke(Color.GREY);
        hexagon.setFill(((Color)hexagon.getFill()).brighter());}});
        
        hexagon.setOnMouseExited(e -> {if(!(hexagon.isOff())){hexagon.setStroke(Color.BLACK);
        hexagon.setFill(((Color)hexagon.getFill()).darker());}});
        
        hexagon.setOnMouseClicked(e -> {if(!(hexagon.isOff()))hexagonOnAction(name);});
        text.setOnMouseClicked(e -> {if(!(hexagon.isOff()))hexagonOnAction(name);});
            
        
            
        setStyleBoroughButton(hexagon,text);
            
        
        StackPane stack = new StackPane(hexagon,text);
        stack.setLayoutX(xAxis);
        stack.setLayoutY(yAxis);
        
        
        hexagonMap.getChildren().add(stack);
    }
    
    /**
     *  Creates a borough map.
     */
    public void createMap() 
    {
        setHexagon("ENFI",4,0);setHexagon("BARN",2,1);setHexagon("HRGY",3,1);setHexagon("WALT",4,1);
        setHexagon("HRRW",1,2);setHexagon("BREN",2,2);setHexagon("CAMD",3,2);setHexagon("ISLI",4,2);
        setHexagon("HACK",5,2);setHexagon("REDB",6,2);setHexagon("HAVE",7,2);setHexagon("HILL",0,3);
        setHexagon("EALI",1,3);setHexagon("KENS",2,3);setHexagon("WSTM",3,3);setHexagon("TOWH",4,3);
        setHexagon("NEWH",5,3);setHexagon("BARK",6,3);setHexagon("HOUN",1,4);setHexagon("HAMM",2,4);
        setHexagon("WAND",3,4);setHexagon("CITY",4,4);setHexagon("GWCH",5,4);setHexagon("BEXL",6,4);
        setHexagon("RICH",1,5);setHexagon("MERT",2,5);setHexagon("LAMB",3,5);setHexagon("STHW",4,5);
        setHexagon("LEWS",5,5);setHexagon("KING",2,6);setHexagon("SUTT",3,6);setHexagon("CROY",4,6);
        setHexagon("BROM",5,6);
    }
    
    /**
     *  Creates a hash map of borough names as values and short names as keys.
     */
    private void createBoroughNames()
    {
        boroughNames.put("ENFI", "Enfield");
        boroughNames.put("BARN", "Barnet");
        boroughNames.put("HRGY", "Haringey");
        boroughNames.put("WALT", "Waltham Forest");
        boroughNames.put("HRRW", "Harrow");
        boroughNames.put("BREN", "Brent");
        boroughNames.put("CAMD", "Camden");
        boroughNames.put("ISLI", "Islington");
        boroughNames.put("HACK", "Hackney");
        boroughNames.put("REDB", "Redbridge");
        boroughNames.put("HAVE", "Havering");
        boroughNames.put("HILL", "Hillingdon");
        boroughNames.put("EALI", "Ealing");
        boroughNames.put("KENS", "Kensington and Chelsea");
        boroughNames.put("WSTM", "Westminster");
        boroughNames.put("TOWH", "Tower Hamlets");
        boroughNames.put("NEWH", "Newham");
        boroughNames.put("BARK", "Barking and Dagenham");
        boroughNames.put("HOUN", "Hounslow");
        boroughNames.put("HAMM", "Hammersmith and Fulham");
        boroughNames.put("WAND", "Wandsworth");
        boroughNames.put("CITY", "City of London");
        boroughNames.put("GWCH", "Greenwich");
        boroughNames.put("BEXL", "Bexley");
        boroughNames.put("RICH", "Richmond upon Thames");
        boroughNames.put("MERT", "Merton");
        boroughNames.put("LAMB", "Lambeth");
        boroughNames.put("STHW", "Southwark");
        boroughNames.put("LEWS", "Lewisham");
        boroughNames.put("KING", "Kingston upon Thames");
        boroughNames.put("SUTT", "Sutton");
        boroughNames.put("CROY", "Croydon");
        boroughNames.put("BROM", "Bromley");
    }
}
