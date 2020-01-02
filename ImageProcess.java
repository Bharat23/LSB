import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.awt.Color;

class ImageProcess{
	BufferedImage fetchImage() throws Exception {
        // TODO: make the image name dynamic
		File f = new File("2.png");
		BufferedImage img = ImageIO.read(f);
		return img;
	}
	
	void hideText(BufferedImage img , byte [] txt) throws Exception{
		
		int i = 0;
		int j = 0;
		for(byte b : txt){
			for(int k=7;k>=0;k--){
				Color c = new Color(img.getRGB(j,i));
				byte blue = (byte)c.getBlue();
				//System.out.println("Blue : "+c.getBlue());
				int red = c.getRed();
				int green = c.getGreen();
				//System.out.println("Red Green Blue : " + red + " "+ green + " "+blue);
				//System.out.println("Sum : "+ (red+green+(int)blue));
				int bitVal = (b >>> k) & 1;  
				blue = (byte)((blue & 0xFE)| bitVal);
				//System.out.println("New Blue: " + (int)(blue & 0xFF) +" "+ blue);
				//System.out.println(j+" "+i);
				Color newColor = new Color(red,
				green,(blue & 0xFF));
				img.setRGB(j,i,newColor.getRGB());
				j++;
			}
			i++;
		}
		
		System.out.println("Text Hidden");
		createImgWithMsg(img);
		System.out.println("Decode? Y or N");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		switch(in.readLine().trim()){
			case "Y":
			case "y":
				{
					String k = revealMsg(txt.length,0);
		 			System.out.println("Text is: " + k);
				}
				break;
			default:
					System.out.println("Program is now ending");
				break;
		}
	}
	
	void createImgWithMsg(BufferedImage img){
		try{
			File ouptut = new File("new2.png");
			ImageIO.write(img, "png", ouptut);
		}
		catch(Exception ex)
		{}
	}
	
	BufferedImage newImageFetch(){
		File f = new File("new2.png");
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
		byte [] msgBytes = extractHiddenBytes(img,msgLen,offset);
		if(msgBytes == null)
			return null;
		String msg = new String(msgBytes);
		return (msg);
	}
	
	byte[] extractHiddenBytes(BufferedImage img , int size , int offset){
		
		int i = 0;
		int j = 0;
		byte [] hiddenBytes = new byte[size];
		
		for(int l=0;l<size;l++){
			for(int k=0 ; k<8 ; k++){
				Color c = new Color(img.getRGB(j,i));
				byte blue = (byte)c.getBlue();
				//System.out.println("Blue : "+c.getBlue()+" "+blue);
				int red = c.getRed();
				int green = c.getGreen();
				//System.out.println("blue : "+blue);
				//System.out.println("Hidden byte<<1: "+(hiddenBytes[l]<<1));
				//System.out.println("blue&1 : "+(blue&1));
				//System.out.println("(hiddenBytes[l]<<1)|(blue&1) : "+ ((hiddenBytes[l]<<1)|(blue&1)));
				hiddenBytes[l] = (byte) ((hiddenBytes[l]<<1)|(blue&1));
				//System.out.println("Hidden byte"+ l +" : "+hiddenBytes[l]);
				//System.out.println(j+" "+i);
				j++;
			}
			i++;
		}
		return hiddenBytes;
		
	}
}
