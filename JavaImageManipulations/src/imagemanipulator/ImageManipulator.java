package imagemanipulator;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageManipulator {
	public final BufferedImage img;
	public int[][][] pixels;
	public int[][][] mem;
	public int type;
	public ImageManipulator(BufferedImage img) {
		this.img = img;
		this.type = img.getType();
		pixels = getPixels(img);
		mem = pixels.clone();
	}
	
	public ImageManipulator reset() {
		pixels = mem.clone();
		StatusLogger.finished("imanip reset");
		return this;
	}
	
	public ImageManipulator checkPoint() {
		mem = pixels.clone();
		StatusLogger.finished("imanip checkpoint creation");
		return this;
	}
	
	public ImageManipulator avBlur(){
		StatusLogger.started("avBlur");
		pixels = BlurAveraging.make(pixels);
		StatusLogger.finished("avBlur");
		return this; // Will allow usign one method after another - imanip.avBlur().negative().decay().decay().decay()
	}
	
	public ImageManipulator avBlur(int kernelWidth, int kernelHeight){
		StatusLogger.started("avBlur");
		pixels = BlurAveraging.make(pixels, kernelWidth, kernelHeight);
		StatusLogger.finished("avBlur");
		return this; // Will allow usign one method after another - imanip.avBlur().negative().decay().decay().decay()
	}
	
	public ImageManipulator negative() {
		StatusLogger.started("negative");
		pixels = Negative.make(pixels);
		StatusLogger.finished("negative");
		return this;
	}
	
	public ImageManipulator decay() {
		StatusLogger.started("decay");
		pixels = Decay.make(pixels);
		StatusLogger.finished("decay");
		return this;
	}
	
	public ImageManipulator normalize() {
		StatusLogger.started("normalize");
		pixels = Normalize.make(pixels);
		StatusLogger.finished("normalize");
		return this;
	}
	
	public ImageManipulator erase() {
		pixels = new int[pixels.length][pixels[0].length][pixels[0][0].length];
		StatusLogger.finished("erase");
		return this;
	}
	
	public ImageManipulator noise(boolean colored, int rangeFrom, int rangeTo) { // 0-255
		StatusLogger.started((colored?"colored ":"")+"noise");
		pixels = Noise.createNoise(pixels, colored, rangeFrom, rangeTo);
		StatusLogger.finished((colored?"colored ":"")+"noise");
		return this;
	}
	
	public ImageManipulator noise(boolean colored) {
		return noise (colored, 0, 255);
	}
	
	public ImageManipulator noise() {
		return noise(false);
	}
	
	public ImageManipulator treshold (Treshold.Color color, int treshold) {
		StatusLogger.started(color.name + " treshold");
		pixels = Treshold.make(pixels, treshold, color);
		StatusLogger.finished(color.name + " treshold");
		return this;
	}
	
	// New functions come here
	
	public ImageManipulator save(String name) {
		String format = "png";
		if (name.indexOf(".")>0) {
			String[] temp = name.split("\\.");
			format = temp[temp.length-1];
		} else {
			name += "." + format;
		}
		saveImageFromPixels(pixels, 
				name, format, // Rename the output file and it's format as you want
				img.getWidth(), img.getHeight(), img.getType());
		StatusLogger.finished("save");
		return this;
	}
	
	public static int[][][] getPixels(BufferedImage img) {
		// Will return 2D array of pixel {A, R, G, B} or {R, G, B } type arrays from given bufferedImage
		byte[] pixels = ((DataBufferByte)img.getRaster().getDataBuffer()).getData();
		
		int width = img.getWidth();
		
		int[][][] finalPixels = new int[img.getHeight()][img.getWidth()][];
		
		if (img.getAlphaRaster()!=null) {
			final int pixelLength = 4;
			for (int pixel = 0, row = 0, col = 0; pixel<pixels.length; pixel+=pixelLength) {
				finalPixels[row][col] = new int[] {pixels[pixel], pixels[pixel+1], pixels[pixel+2], pixels[pixel+3]};
				col++;
				if (col == img.getWidth()) {
					row++;
					col = 0;
				}
			}
		} else {
			final int pixelLength = 3;
			for (int pixel = 0, row = 0, col = 0; pixel<pixels.length; pixel+=pixelLength) {
				finalPixels[row][col] = new int[] {pixels[pixel], pixels[pixel+1], pixels[pixel+2]};
				col++;
				if (col == width) {
					row++;
					col = 0;
				}
			}
		}
		return finalPixels;
	}
	
	public static BufferedImage convertIManipToImage(ImageManipulator imanip) {
		return convertPixelsToImage(imanip.pixels, imanip.type);
	}
	
	public static BufferedImage convertPixelsToImage(int[][][] pixels, int type) {
		BufferedImage temp = new BufferedImage(pixels[0].length, pixels.length, type);
//		if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_4BYTE_ABGR) {
		if (pixels[0][0].length == 4) {
			Universals.printArray(pixels[0][0]);
			for (int y = 0; y<pixels.length; y++) {
				for (int x = 0; x<pixels[0].length;x++) {
					temp.setRGB(x, y, ARGBtoInt(pixels[y][x]));
				}
			}
		} else for (int y = 0; y<pixels.length; y++) {
				for (int x = 0; x<pixels[0].length;x++) {
					temp.setRGB(x, y, RGBtoInt(pixels[y][x]));
				}
			}
		return temp;
	}
	
	public static void saveImage(BufferedImage img, String name, String format) {
		try {
			ImageIO.write(img, format, new File("res/"+name));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage saveImageFromPixels(int[][][] pixels, String name, String format, int width, int height, int type) {
		// Creates image from given {A, R, G, B} or {R, G, B } pixels array, saves and returns it  
		BufferedImage temp = convertPixelsToImage(pixels, type);
		saveImage(temp, name, format);
		return temp;
	}
	
	public static int[] IntToARGB(int argb) { // Converts int to array {A, R, G, B}
		return new int[] {
				((argb>>24)&0xff),
				((argb)&0xff),
				((argb>>8)&0xff),
				((argb>>16)&0xff)
		};
	}
	
	public static int ARGBtoInt(int[] ARGB) { // Converts array of {A, R, G, B} to the pixel int view.
		int argb = 0;
		argb += (((int)ARGB[0]&0xff)<<24);
		argb += (((int)ARGB[1]&0xff));
		argb += (((int)ARGB[2]&0xff)<<8);
		argb += (((int)ARGB[3]&0xff)<<16);
		return argb;
	}
	
	public static int RGBtoInt(int[] RGB) { // Converts array of {R, G, B} to the pixel int view.
		return ARGBtoInt(new int[] {255, RGB[0], RGB[1], RGB[2]});
	}
	
	public static BufferedImage getNewBlank(int width, int height) {
		BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		return temp;
	}
	
	public static BufferedImage getNoise(int width, int height, boolean colored) {
		return convertIManipToImage(getNewIManipWithNoise(width, height, colored));
	}
	
	public static ImageManipulator getNewIManipWithNoise(int width, int height, boolean colored) {
		BufferedImage img = getNewBlank(width, height);
		ImageManipulator imanip = new ImageManipulator(img).noise(colored);
		return imanip;
	}
	
	public static void main(String[] args) {
		BufferedImage img = null;
		try {
			 img = ImageIO.read(new File("res/tresholdTest.png")); // Rename the input file as you want
			 // Your images have to be in the res folder, so if you don't have it, just create one.
		} catch (IOException e) {
			System.out.println("Can't import image");
			System.exit(0);
		}
		ImageManipulator imanip = new ImageManipulator(img);
		imanip.treshold(Treshold.Color.GREEN, -10).save("tempOutput.png");
	}
}