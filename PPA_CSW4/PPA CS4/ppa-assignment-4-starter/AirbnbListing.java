 import java.io.FileWriter;
 import java.util.ArrayList;
 import com.opencsv.CSVReader;
 import java.io.FileReader;
/**
 * Represents one listing of a property for rental on Airbnb.
 * This is essentially one row in the data table. Each column
 * has a corresponding field.
 * 
 * Modified by Charlie Madigan (19019003), Justinas Kiskis (K1889820)
 */ 

public class AirbnbListing {
    /**
     * The id and name of the individual property
     */
    private String id;
    private String name;
    /**
     * The id and name of the host for this listing.
     * Each listing has only one host, but one host may
     * list many properties.
     */
    private String host_id;
    private String host_name;

    /**
     * The grouped location to where the listed property is situated.
     * For this data set, it is a london borough.
     */
    private String neighbourhood;

    /**
     * The location on a map where the property is situated.
     */
    private double latitude;
    private double longitude;

    /**
     * The type of property, either "Private room" or "Entire Home/apt".
     */
    private String room_type;

    /**
     * The price per night's stay
     */
    private int price;

    /**
     * The minimum number of nights the listed property must be booked for.
     */
    private int minimumNights;
    private int numberOfReviews;

    /**
     * The date of the last review, but as a String
     */
    private String lastReview;
    private double reviewsPerMonth;
    private static int newPropertyID;

    /**
     * The total number of listings the host holds across AirBnB
     */
    private int calculatedHostListingsCount;
    /**
     * The total number of days in the year that the property is available for
     */
    private int availability365;

    public AirbnbListing(String id, String name, String host_id,
                         String host_name, String neighbourhood, double latitude,
                         double longitude, String room_type, int price,
                         int minimumNights, int numberOfReviews, String lastReview,
                         double reviewsPerMonth, int calculatedHostListingsCount, int availability365) {
        this.id = id;
        this.name = name;
        this.host_id = host_id;
        this.host_name = host_name;
        this.neighbourhood = neighbourhood;
        this.latitude = latitude;
        this.longitude = longitude;
        this.room_type = room_type;
        this.price = price;
        this.minimumNights = minimumNights;
        this.numberOfReviews = numberOfReviews;
        this.lastReview = lastReview;
        this.reviewsPerMonth = reviewsPerMonth;
        this.calculatedHostListingsCount = calculatedHostListingsCount;
        this.availability365 = availability365;
    }
    
    public static void addPropertyToDataBase (ArrayList<String> entryData) {
        try {
            FileWriter fileWriter = new FileWriter("airbnb-london.csv", true);
            StringBuilder builder = new StringBuilder();
            builder.append(newPropertyID);
            builder.append(",");
            for (int i = 0; i < entryData.size(); i++) {
                System.out.println(entryData.get(i));
                builder.append(entryData.get(i));
                if (i != entryData.size() -1 ) {
                    builder.append(",");
                } else {
                    builder.append("\n");
                }
            }
            System.out.println(builder.toString());
            fileWriter.write(builder.toString());
            fileWriter.close();
            newPropertyID +=1;
        } catch (Exception E) {
            System.out.println("ERROR");
        }
    }
    
    public static Integer findNumOfPropertiesByHost (String hostID) {
        Integer numberOfListings = 0;
        try {
            CSVReader reader = new CSVReader(new FileReader("airbnb-london.csv"));
            String[] line;
            while ((line = reader.readNext())!=null){
                if (line[2] == hostID) {
                    numberOfListings += 1;
                }
            }
            return numberOfListings;
        } catch (Exception E) {
            System.out.println("There was an error adding your listing to the property listing. Please restart the application and try again");
        }
        return numberOfListings;
    }
    
    public static void setNewPropertyID (int newID) {
        newPropertyID = newID;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHost_id() {
        return host_id;
    }

    public String getHost_name() {
        return host_name;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getRoom_type() {
        return room_type;
    }

    public int getPrice() {
        return price;
    }

    public int getMinimumNights() {
        return minimumNights;
    }

    public int getNumberOfReviews() {
        return numberOfReviews;
    }

    public String getLastReview() {
        return lastReview;
    }

    public double getReviewsPerMonth() {
        return reviewsPerMonth;
    }

    public int getCalculatedHostListingsCount() {
        return calculatedHostListingsCount;
    }

    public int getAvailability365() {
        return availability365;
    }

    @Override
    public String toString() {
        return "AirbnbListing{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", host_id='" + host_id + '\'' +
                ", host_name='" + host_name + '\'' +
                ", neighbourhood='" + neighbourhood + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", room_type='" + room_type + '\'' +
                ", price=" + price +
                ", minimumNights=" + minimumNights +
                ", numberOfReviews=" + numberOfReviews +
                ", lastReview='" + lastReview + '\'' +
                ", reviewsPerMonth=" + reviewsPerMonth +
                ", calculatedHostListingsCount=" + calculatedHostListingsCount +
                ", availability365=" + availability365 +
                '}';
    }
}
