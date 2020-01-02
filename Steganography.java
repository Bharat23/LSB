import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.image.BufferedImage;

class Steganography {
	public static void main(String [] args) throws Exception{
		// read the file for fetching the text
		// TODO: make the input text dynamic
		FileReader code = new FileReader("input.txt");
		BufferedReader in = new BufferedReader(code);
		String s = "";
		String g = "";
		while((s=in.readLine()) != null)
			g+=s;
		
		// initialize the Convert class object
		Convert c = new Convert();
		// initialize the ImageProcess class object
		ImageProcess impro = new ImageProcess();
		// convert text to bytes
		byte [] txtBytes = c.txtToByte(g);
		// fetch the image
		BufferedImage img = impro.fetchImage();
		// embed the text into the image
		impro.hideText(img,txtBytes);
	}
}