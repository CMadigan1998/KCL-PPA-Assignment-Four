import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class SortingAirbnbListsTest.
 *
 * @author Charlie Madigan (19019003), Justinas Kiskis (K1889820), Carlos Navarro (K19016418)
 */
public class SortingAirbnbListsTest
{
    /**
     * Default constructor for test class SortingAirbnbListsTest
     */
    public SortingAirbnbListsTest()
    {
    }

    /**
     * Checks if changing the price range updates the statistics in the panel correctly.
     */
    @Test
    public void statisticsRefreshRange(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(100, 600));
        assertEquals(32, testList.getNumberSharedRooms());
        assertNotNull(testList.sortPriceRange(300, 700));
        assertEquals(2, testList.getNumberSharedRooms());
        assertNotNull(testList.sortPriceRange(0, 200));
        assertEquals(697, testList.getNumberSharedRooms());
    }

    /**
     * Checks if changing the price range updates the statistics in the panel correctly.
     */
    @Test
    public void statisticsRefreshRange2(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(700, 800));
        assertEquals("Hackney", testList.getMostExpensiveBorough());
        assertNotNull(testList.sortPriceRange(0, 100));
        assertEquals("Tower Hamlets", testList.getMostExpensiveBorough());
        assertNotNull(testList.sortPriceRange(300, 600));
        assertEquals("Newham", testList.getMostExpensiveBorough());
    }

    /**
     * Check price range is applied correctly and value returned for most expensive borough is correct.
     */
    @Test
    public void expensiveBorough(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(300, 700));
        assertEquals("Newham", testList.getMostExpensiveBorough());
    }
    
    /**
     * Check price range is applied correctly and value returned for most expensive borough is correct.
     */
    @Test
    public void expensiveBorough2(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(100, 600));
        assertEquals("Wandsworth", testList.getMostExpensiveBorough());
    }
    
    /**
     * Check price range is applied correctly and value returned for most expensive borough is correct.
     */
    @Test
    public void expensiveBorough3(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(0, 800));
        assertEquals("Wandsworth", testList.getMostExpensiveBorough());
    }

    /**
     * Check price range is applied correctly and value returned for most total properties avaiable is correct.
     */
    @Test
    public void availProperties(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(300, 700));
        assertEquals(924, testList.getNumberAvailableProp());
    }
    
    /**
     * Check price range is applied correctly and value returned for most total properties avaiable is correct.
     */
    @Test
    public void availProperties2(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(100, 600));
        assertEquals(12550, testList.getNumberAvailableProp());
    }
    
    /**
     * Check price range is applied correctly and value returned for most total properties avaiable is correct.
     */
    @Test
    public void availProperties3(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(0, 800));
        assertEquals(41856, testList.getNumberAvailableProp());
    }

    /**
     * Check price range is applied correctly and value returned for number of homes/apartments is correct.
     */
    @Test
    public void homeApartmentProperties(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(300, 700));
        assertEquals(1155, testList.getNumberHomeApartments());
    } 
    
    /**
     * Check price range is applied correctly and value returned for number of homes/apartments is correct.
     */
    @Test
    public void homeApartmentProperties2(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(100, 600));
        assertEquals(14680, testList.getNumberHomeApartments());
    } 
    
    /**
     * Check price range is applied correctly and value returned for number of homes/apartments is correct.
     */
    @Test
    public void homeApartmentProperties3(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(0, 800));
        assertEquals(27084, testList.getNumberHomeApartments());
    } 

    /**
     * Check price range is applied correctly and value returned for most average property reviews is correct.
     */
    @Test
    public void averageReviews(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(300, 700));
        assertEquals(6, testList.getNumberAverageReviews());
    }
    
    /**
     * Check price range is applied correctly and value returned for most average property reviews is correct.
     */
    @Test
    public void averageReviews2(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(100, 600));
        assertEquals(12, testList.getNumberAverageReviews());
    }
    
    /**
     * Check price range is applied correctly and value returned for most average property reviews is correct.
     */
    @Test
    public void averageReviews3(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(0, 800));
        assertEquals(12, testList.getNumberAverageReviews());
    }

    /**
     * Check price range is applied correctly and value returned for the average number of property min nights is correct.
     */
    @Test
    public void averageNumberNights(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(300, 700));
        assertEquals(8, testList.getNumberAverageNights());
    }
    
    /**
     * Check price range is applied correctly and value returned for the average number of property min nights is correct.
     */
    @Test
    public void averageNumberNights2(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(100, 600));
        assertEquals(4, testList.getNumberAverageNights());
    }
    
    /**
     * Check price range is applied correctly and value returned for the average number of property min nights is correct.
     */
    @Test
    public void averageNumberNights3(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(0, 800));
        assertEquals(3, testList.getNumberAverageNights());
    }

    /**
     * Check price range is applied correctly and value returned for number of properties the host with most property has.
     */
    @Test
    public void highestPropertyHost(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(300, 700));
        assertEquals(711, testList.getNumberHighestListing());
    }
    
    /**
     * Check price range is applied correctly and value returned for number of properties the host with most property has.
     */
    @Test
    public void highestPropertyHost2(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(100, 600));
        assertEquals(711, testList.getNumberHighestListing());
    }
    
    /**
     * Check price range is applied correctly and value returned for number of properties the host with most property has.
     */
    @Test
    public void highestPropertyHost3(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(0, 800));
        assertEquals(711, testList.getNumberHighestListing());
    }

    /**
     * Check price range is applied correctly and value returned is the number of properties in range are private rooms.
     */
    @Test
    public void privateRooms(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(300, 700));
        assertEquals(46, testList.getNumberPrivateRooms());
    }
    
    /**
     * Check price range is applied correctly and value returned is the number of properties in range are private rooms.
     */
    @Test
    public void privateRooms2(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(100, 600));
        assertEquals(930, testList.getNumberPrivateRooms());
    }
    
    /**
     * Check price range is applied correctly and value returned is the number of properties in range are private rooms.
     */
    @Test
    public void privateRooms3(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(0, 800));
        assertEquals(26007, testList.getNumberPrivateRooms());
    }

    /**
     * Check price range is applied correctly and value returned is the number of properties in range are shared rooms.
     */
    @Test
    public void sharedRooms(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(300, 700));
        assertEquals(2, testList.getNumberSharedRooms());
    }
    
    /**
     * Check price range is applied correctly and value returned is the number of properties in range are shared rooms.
     */
    @Test
    public void sharedRooms2(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(100, 600));
        assertEquals(32, testList.getNumberSharedRooms());
    }
    
    /**
     * Check price range is applied correctly and value returned is the number of properties in range are shared rooms.
     */
    @Test
    public void sharedRooms3(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertNotNull(testList.sortPriceRange(0, 800));
        assertEquals(709, testList.getNumberSharedRooms());
    }

    /**
     * Check setPrefInfoRoomType() calculations are correct and return the appropriate value.
     */
    @Test
    public void statisticsString1(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertEquals("Private room avrg price: 51\nHome room avrg price: 140\nShared room avrg price: 46", testList.getString1());
    }
    @Test
    public void statisticsString2(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertEquals("Private room avrg availability: 177\nHome room avrg availability: 133\nShared room avrg availability: 221", testList.getString2());
    }
    @Test
    public void statisticsString3(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertEquals("Private room avrg review/month: 1.3565016521\nHome room avrg review/month: 1.2564987852\nShared room avrg review/month: 1.4065893271", testList.getString3());
    }
    @Test
    public void statisticsString4(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertEquals("Private room avrg minimum nights: 2\nHome room avrg minimum nights: 3\nShared room avrg minimum nights: 1", testList.getString4());
    }
    
    /**
     * Check setPrefInfoPriceType() calculations are correct and return the appropriate value.
     */
    @Test
    public void statisticsString5(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertEquals("Properties under 100: 38049", testList.getString5());
    }
    @Test
    public void statisticsString6(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertEquals("Properties under 50: 19033", testList.getString6());
    }
    @Test
    public void statisticsString7(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertEquals("Properties under 100 & available for more than half year: 14765", testList.getString7());
    }
    @Test
    public void statisticsString8(){
        SortingAirbnbLists testList = new SortingAirbnbLists();
        assertEquals("Value for money (whole apartament, under 100): 16374", testList.getString8());
    }
}

