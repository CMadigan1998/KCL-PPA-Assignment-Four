import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class SortingAirbnbLists is responsible for sorting Airbnb lists,
 * creating statistics.
 *
 * @author Charlie Madigan (19019003), Justinas Kiskis (K1889820), Carlos Navarro (K19016418)
 */
public class SortingAirbnbLists
{
    //CurrentListing can also be stored in MainWindow in order to avoid confusion and mistakes
    //Still need to decide where to store it (which way is better)
    private ArrayList<AirbnbListing> currentListing, newListing,
    preferenceListing;
    private static AirbnbDataLoader airbnbDataLoader;
    private AirbnbListing mostExpensive;
    private ArrayList<String> forStatPanels;
    private String string1,string2,string3,string4,string5,string6,string7,string8;
    /**
     * Constructor for objects of class SortingAirbnbLists
     */
    public SortingAirbnbLists()
    {
        AirbnbDataLoader airbnbnDataLoader = new AirbnbDataLoader(); 
        newListing = airbnbnDataLoader.load();
        setPrefInfoRoomType();
        setPrefInfoPriceType();
    }

    /**
     * Sorting the list by prefered price range
     */
    public ArrayList sortPriceRange(int minPrice, int maxPrice)
    {
        currentListing = new ArrayList<AirbnbListing>();
        for(AirbnbListing tempListing : newListing) {
            if(tempListing.getPrice() < maxPrice && tempListing.getPrice() > minPrice){
                currentListing.add(tempListing);
            }
        }
        return currentListing;
    }
    
    /**
     * Sorting the list by prefered borough
     */
    public ArrayList getSortedBoroughList(String name)
    {
        System.out.println("method call is succesful");
        ArrayList<AirbnbListing> tempList = new ArrayList<AirbnbListing>();
        for(AirbnbListing tempListing : currentListing) {
            if(tempListing.getNeighbourhood().equals(name)){
                tempList.add(tempListing);
            }
        }
        return tempList;
    }
    
    /**
     * Counts the average number of reviews per property in the current listing
     */
    public int getNumberAverageReviews()
    {
        ArrayList<AirbnbListing> tempList = new ArrayList<AirbnbListing>();
        int count = 0;
        int numbReviews = 0;
        for(AirbnbListing tempListing : currentListing){
            count++;
            numbReviews += tempListing.getNumberOfReviews();
        }
        return numbReviews/count;
    }
    
    /**
     * Finds the most expensive borough in the current list
     */
    public String getMostExpensiveBorough()
    {
        int propertyPrice = 0;
        int prevPropPrice = 0;
        String expensiveBoroughName;
        for(AirbnbListing tempListing : currentListing){
            propertyPrice = tempListing.getMinimumNights() * tempListing.getPrice();
            if(propertyPrice > prevPropPrice){
                mostExpensive = tempListing;
            }
            prevPropPrice = propertyPrice;
        }
        return mostExpensive.getNeighbourhood();
    }
    
    /**
     * Counts the number of home/apartaments in the current list
     */
    public int getNumberHomeApartments()
    {
        int home = 0;
        for(AirbnbListing tempListing : currentListing){
            if(tempListing.getRoom_type().equals("Entire home/apt")){
                home++;
            }
        }
        return home;
    }
    
    /**
     * Counts the number of available properties in the current list
     */
    public int getNumberAvailableProp()
    {
        int available = 0;
        for(AirbnbListing tempListing : currentListing){
            if(tempListing.getAvailability365() > 0){
                available++;
            }
        }
        return available;
    }
    
    
    
    /**
     * Finds the host with the highest number of listings in the current list
     */
    public int getNumberHighestListing()
    {
        int highest = 0;
        int prevHighest = 0;
        for(AirbnbListing tempListing : currentListing){
            if(tempListing.getCalculatedHostListingsCount() > prevHighest){
                highest = tempListing.getCalculatedHostListingsCount();
            }
            prevHighest = highest;
        }
        return highest;
    }
    
    /**
     * Counts the average number of nights in the current listing
     */
    public int getNumberAverageNights()
    {
        ArrayList<AirbnbListing> tempList = new ArrayList<AirbnbListing>();
        int count = 0;
        int numbNights = 0;
        for(AirbnbListing tempListing : currentListing){
            count++;
            numbNights += tempListing.getMinimumNights();
        }
        return numbNights/count;
    }
    
    /**
     * Counts the number of shared in the current list
     */
    public int getNumberSharedRooms()
    {
        int shared = 0;
        for(AirbnbListing tempListing : currentListing){
            if(tempListing.getRoom_type().equals("Shared room")){
                shared++;
            }
        }
        return shared;
    }
    
    /**
     * Counts the number of private in the current list
     */
    public int getNumberPrivateRooms()
    {
        int privateRoom = 0;
        for(AirbnbListing tempListing : currentListing){
            if(tempListing.getRoom_type().equals("Private room")){
                privateRoom++;
            }
        }
        return privateRoom;
    }
    
    /**
     * Counts the average price of property types
     * in the list
     */
    public void setPrefInfoRoomType()
    {
        int privateNumb = 0;
        int privatePrice = 0;
        int sharedNumb = 0;
        int sharedPrice = 0;
        int homeNumb = 0;
        int homePrice = 0;
        int sharedAvail = 0;
        int privateAvail = 0;
        int homeAvail = 0;
        int sharedRew = 0;
        int privateRew = 0;
        int homeRew = 0;
        int sharedNight = 0;
        int privateNight = 0;
        int homeNight = 0;
        ArrayList<String> forStatPanels = new ArrayList<String>();
        for(AirbnbListing tempListing : newListing){
            if(tempListing.getRoom_type().equals("Private room")){
                privateNumb++;
                privatePrice += tempListing.getPrice();
                privateAvail += tempListing.getAvailability365();
                privateNight += tempListing.getMinimumNights();
                privateRew += tempListing.getReviewsPerMonth();
            }
            if(tempListing.getRoom_type().equals("Entire home/apt")){
                homeNumb++;
                homePrice += tempListing.getPrice();
                homeAvail += tempListing.getAvailability365();
                homeNight += tempListing.getMinimumNights();
                homeRew += tempListing.getReviewsPerMonth();
            }
            if(tempListing.getRoom_type().equals("Shared room")){
                sharedNumb++;
                sharedPrice += tempListing.getPrice();
                sharedAvail += tempListing.getAvailability365();
                sharedNight += tempListing.getMinimumNights();
                sharedRew += tempListing.getReviewsPerMonth();
            }
        }
        string1 = ("Private room avrg price: " + Integer.toString(privatePrice/privateNumb) +
        "\n" + "Home room avrg price: " + Integer.toString(homePrice/homeNumb) + "\n" +
        "Shared room avrg price: " + Integer.toString(sharedPrice/sharedNumb));
        string2 = ("Private room avrg availability: " + Integer.toString(privateAvail/privateNumb) +
        "\n" + "Home room avrg availability: " + Integer.toString(homeAvail/homeNumb) + "\n" +
        "Shared room avrg availability: " + Integer.toString(sharedAvail/sharedNumb));
        string3 = ("Private room avrg review/month: " + Double.toString(privateRew/privateNumb) +
        "\n" + "Home room avrg review/month: " + Double.toString(homeRew/homeNumb) + "\n" +
        "Shared room avrg review/month: " + Double.toString(sharedRew/sharedNumb));
        string4 = ("Private room avrg minimum nights: " + Integer.toString(privateNight/privateNumb) +
        "\n" + "Home room avrg minimum nights: " + Integer.toString(homeNight/homeNumb) + "\n" +
        "Shared room avrg minimum nights: " + Integer.toString(sharedNight/sharedNumb));
    }
    
    /**
     *  GetString methods returns different statistics.
     */
    public String getString1()
    {
        return string1;
    }
    public String getString2()
    {
        return string2;
    }
    public String getString3()
    {
        return string3;
    }
    public String getString4()
    {
        return string4;
    }
    
    /**
     * Counts the average price of property types
     * in the list
     */
    public void setPrefInfoPriceType()
    {
        int availPropUnder50 = 0;
        int availPropUnder100 = 0;
        int under100AvailHalfYear = 0;
        int valueForMoney = 0;
        
        ArrayList<String> forStatPanels = new ArrayList<String>();
        for(AirbnbListing temp : newListing){
            if(temp.getPrice() <= 100){
                availPropUnder100++;
                if(temp.getPrice() <= 50){
                    availPropUnder50++;
                }
                if(temp.getAvailability365() >= 182){
                    under100AvailHalfYear++;
                }
            }
            if(temp.getPrice() <= 125 && temp.getRoom_type().equals("Entire home/apt")){
                valueForMoney++;
            }
        }
        string5 = ("Properties under 100: " + Integer.toString(availPropUnder100));
        string6 = ("Properties under 50: " + Integer.toString(availPropUnder50));
        string7 = ("Properties under 100 & available for more than half year: " + Integer.toString(under100AvailHalfYear));
        string8 = ("Value for money (whole apartament, under 100): " + Integer.toString(valueForMoney));
    }
    
    /**
     *  GetString methods returns different statistics.
     */
    public String getString5()
    {
        return string5;
    }
    public String getString6()
    {
        return string6;
    }
    public String getString7()
    {
        return string7;
    }
    public String getString8()
    {
        return string8;
    }
    
}
