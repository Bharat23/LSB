import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.awt.Color;

class ImageProcess{

	private String inputImageName;
	private String outputImageName;

	ImageProcess (String inputImageName, String outputImageName) {
		this.inputImageName = inputImageName;
		this.outputImageName = outputImageName;
	}

	BufferedImage fetchImage() throws Exception {
		File f = new File(this.inputImageName);
		BufferedImage img = ImageIO.read(f);
		return img;
	}
	
	void hideText(BufferedImage img , byte [] txt) throws Exception {
		
		int i = 0;
		int j = 0;
		for(byte b : txt){
			for(int k=7;k>=0;k--){
				Color c = new Color(img.getRGB(j,i));
				byte blue = (byte)c.getBlue();
				int red = c.getRed();
				int green = c.getGreen();
				int bitVal = (b >>> k) & 1;  
				blue = (byte)((blue & 0xFE)| bitVal);
				Color newColor = new Color(red,
				green,(blue & 0xFF));
				img.setRGB(j,i,newColor.getRGB());
				j++;
			}
			i++;
		}
		
		System.out.println("Text Hidden");
		createImgWithMsg(img);
	}
	
	void createImgWithMsg(BufferedImage img){
		try {
			File ouptut = new File(this.outputImageName);
			ImageIO.write(img, "png", ouptut);
		}
		catch(Exception ex) {}
	}
	
	BufferedImage newImageFetch(){
		File f = new File(this.outputImageName);
		BufferedImage img = null;
		try{
			img = ImageIO.read(f);
		}
		catch(Exception ex)
		{}
		return img;
	}
	
	String revealMsg(int msgLen , int offset){
		BufferedImage img = newImageFetch();
		byte [] msgBytes = extractHiddenBytes(img, msgLen, offset);
		if(msgBytes == null)
			return null;
		String msg = new String(msgBytes);
		return msg;
	}
	
	byte[] extractHiddenBytes(BufferedImage img , int size , int offset){
		
		int i = 0;
		int j = 0;
		byte [] hiddenBytes = new byte[size];
		
		for(int l=0;l<size;l++){
			for(int k=0 ; k<8 ; k++){
				Color c = new Color(img.getRGB(j,i));
				byte blue = (byte)c.getBlue();
				int red = c.getRed();
				int green = c.getGreen();
				hiddenBytes[l] = (byte) ((hiddenBytes[l]<<1)|(blue&1));
				j++;
			}
			i++;
		}
		return hiddenBytes;
	}

	void decodeMessage (int messageLength) throws IOException {
		System.out.println("Decode? Y or N");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		switch(in.readLine().trim()){
			case "Y":
			case "y":
				{
					String k = revealMsg(messageLength,0);
		 			System.out.println("Text is: " + k);
				}
				break;
			default:
					System.out.println("Program is now ending");
				break;
		}
	}
}
