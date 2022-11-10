import java.io.File;
import java.util.Scanner;
/**
 * Class FileIO is dealing with files
 *
 * @author Charlie Madigan (19019003), Justinas Kiskis (K1889820)
 */
public class FileIO
{
    private File mainMenuTxt;
    private Scanner scan;
    /**
     * Takes file from which information is scanned.
     */
    public void getFile (String filePath) {
        mainMenuTxt = new File(filePath);
        try {
            scan = new Scanner (mainMenuTxt);
        } catch (Exception E) {
            System.out.println("There was an error accessing the file that contains the welcome information. Please restart the application and try again");
        }
    }
    
    /**
     * Reads line from file.
     */
    public String readData () {
        String fileData = "";
        while (scan.hasNextLine()) {
            fileData = fileData + "\n" + scan.nextLine();
        }
        return fileData;
    }
}
