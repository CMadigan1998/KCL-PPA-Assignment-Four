import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import com.opencsv.CSVReader;
import com.opencsv.stream.reader.LineReader;
import java.io.BufferedReader;
/**
 * Class AccountManagement is responsible for account management (for panel 4).
 *
 * @author Charlie Madigan (19019003), Justinas Kiskis (K1889820), Carlos Navarro (K19016418)
 */
public class AccountManagement
{
    private File loginDB;
    private Scanner scan;
    private FileWriter fileWriter;
    private int ID = 0;
    private String fileNameDatabase;
    private HashMap<Integer, AccountEntry> users = new HashMap<>();
    private AccountEntry loginAccount;
    
    /**
     *  Checks whether there is a database fo user login details or not.
     *  If there is no database, new database is created to store 
     *  user login details.
     */
    public void checkForLoginDatabase(String fileName) {
        loginDB = new File(fileName);
        try {
            if (loginDB.createNewFile()) {                                  //Is the file create here?
                ArrayList<String> data = new ArrayList<>();                 //If the file was just created, we need to create the colum titles for the data.
                data.add("ID");
                data.add("First Name");
                data.add("Surname");
                data.add("Username");
                data.add("Password");
                writeLoginDatabase(fileName, data, true);
                System.out.println("Database was created successfully");    //Write the message that the file was created;
            } else {
                countInitialEntries(fileName);
                loadUsers(fileName);
                fileNameDatabase = fileName;
                System.out.println("File already exists, loading...");
            }
            
        } catch (Exception E) {
            System.out.println("There was an error accessing the database, please restart the application and try again");
        }
    }
    
    /**
     *  Creates a new user and stores it in a database.
     */
    public void writeLoginDatabase (String fileName, ArrayList<String> data, Boolean newDataBase) {
        try {
            fileWriter = new FileWriter(fileName,true);
            StringBuilder builder = new StringBuilder();
            if (!newDataBase) {
                 builder.append(ID);
                 builder.append(",");
            }
            for (int i = 0; i < data.size(); i++) {
                System.out.println(data.get(i));
                builder.append(data.get(i));
                if (i != data.size() -1 ) {
                    builder.append(",");
                } else {
                    builder.append("\n");
                }
            }
            fileWriter.write(builder.toString());
            fileWriter.close();
            AccountEntry newAccount = new AccountEntry (ID, data.get(0), data.get(1), data.get(2), data.get(3));
            users.put(ID, newAccount);
            ID += 1;
        } catch (Exception E){
            System.out.println("There was an error writing to the database, please restart the application can try again");
        }
    }
    
    /**
     * Counts initial entries, number of IDs.
     */
    private void countInitialEntries (String fileName) {
        try {
            CSVReader csvReader = new CSVReader(new FileReader(fileName));
            String[] line;
            while ((line = csvReader.readNext())!=null){
                ID += 1;
                System.out.println("New Line");
            }
            ID -= 1;                                                //I remove one from the ID as it counts the colum titles
        } catch (Exception E) {
            System.out.println("There was an error accessing the database, please restart the application and try again");
        }
    }
    
    /**
     * Loads users' details that are stored in a file.
     */
    private void loadUsers(String fileName) {
        try {
            CSVReader reader = new CSVReader(new FileReader(fileName));
            AccountEntry tmp;
            String[] entries;
            
            String line ="";
            String splitBy = ",";
            int i = 0;
            LineReader lineRead = new LineReader(new BufferedReader(new FileReader(fileName)),true);
            while ((line = lineRead.readLine())!=null) {
                if(i > 0){
                    entries = line.split(splitBy);
                    tmp = new AccountEntry(Integer.parseInt(entries[0]), entries[1], entries[2], entries[3], entries[4]);
                    System.out.println(entries[0] +  entries[1]);
                    users.put(Integer.parseInt(entries[0]), tmp);
                    System.out.println(users.get(i).getUserName());
                }
                i++;
                
            }
        } catch (Exception E) {
            System.out.println("There was an error accessing the database, please restart the application and try again");
        }
    }
    
    /**
     *  Check whether given username and password match any 
     *  details stored in a file.
     */
    public Boolean checkAccountDetails (String username, String password) 
    {
        for(HashMap.Entry temp : users.entrySet()){
            AccountEntry user = (AccountEntry) temp.getValue();
            System.out.println(user.getUserName()+ " " + user.getPassword()+ " "+
            username+ " "+ password);
            String name = user.getUserName();
            String pass = user.getPassword();
            if(username.equals(name)&&password.equals(pass)){
                loginAccount = user;
                return true;
            }
        }
        loginAccount = null;
        return false;
    }
    
    /**
     *  Returns a login account.
     */
    public AccountEntry getLoginAccount () {
        return loginAccount;
    }
}
