import java.io.FileNotFoundException;
import java.io.IOException;

class Main {
    public static void main (String [] args) {
        try {
            Steganography steg = new Steganography("input.txt", "2.png", "new2.png");
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found: " + fileNotFoundException);
        } catch (IOException ioException) {
            System.out.println("IO Exception: " + ioException);
        } catch (Exception ex) {
            System.out.println("Something went wrong: " + ex);
        }
        
    }
}