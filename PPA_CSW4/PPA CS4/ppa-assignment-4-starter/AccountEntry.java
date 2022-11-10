
/**
 * Manages the accounts, stores register user
 *
 * @author Charlie Madigan (19019003), Justinas Kiskis (K1889820), Carlos Navarro (K19016418)
 */
public class AccountEntry
{
    private Integer ID;
    private String firstName, surname, userName, password;
    /**
     * Creates a user with ID, name, surname, userName and password
     */
    public AccountEntry (Integer ID, String firstName, String surname, String userName, String password) {
        this.ID = ID;
        this.firstName = firstName;
        this.surname = surname;
        this.userName = userName;
        this.password = password;
    }
    
    /**
     * Returns user's name.
     */
    public String getFirstName () {
        return firstName;
    }
    
    /**
     * Returns user's surname.
     */
    public String getSurname () {
        return surname;
    }
    
    /**
     * Returns user's name.
     */
    public String getUserName () {
        return userName;
    }
    
    /**
     * Returns user's password.
     */
    public String getPassword () {
        return password;
    }
    
    /**
     * Returns user's ID.
     */
    public Integer getUserID () {
        return ID;
    }
}
