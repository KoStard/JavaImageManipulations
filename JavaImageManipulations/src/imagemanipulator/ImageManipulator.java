package imagemanipulator;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageManipulator {
	
	public static int[][][] getPixels(BufferedImage img) {
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
	
	public static BufferedImage saveImageFromPixels(int[][][] pixels, String name, String format, int width, int height, int type) {
		BufferedImage temp = new BufferedImage(width, height, type);
		if (type == BufferedImage.TYPE_INT_ARGB) {
			for (int y = 0; y<height; y++) {
				for (int x = 0; x<width;x++) {
					temp.setRGB(x, y, ARGBtoInt(pixels[y][x]));
				}
			}
		} else for (int y = 0; y<height; y++) {
				for (int x = 0; x<width;x++) {
					temp.setRGB(x, y, RGBtoInt(pixels[y][x]));
				}
			}
		try {
			ImageIO.write(temp, format, new File("res/"+name));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	public static int[] IntToARGB(int argb) {
		return new int[] {
				((argb>>24)&0xff),
				((argb)&0xff),
				((argb>>8)&0xff),
				((argb>>16)&0xff)
		};
	}
	
	public static int ARGBtoInt(int[] ARGB) {
		int argb = 0;
		argb += (((int)ARGB[0]&0xff)<<24);
		argb += (((int)ARGB[1]&0xff));
		argb += (((int)ARGB[2]&0xff)<<8);
		argb += (((int)ARGB[3]&0xff)<<16);
		return argb;
	}
	
	public static int RGBtoInt(int[] RGB) {
		return ARGBtoInt(new int[] {255, RGB[0], RGB[1], RGB[2]});
	}
	
	public static void main(String[] args) {
		BufferedImage img = null;
		try {
			 img = ImageIO.read(new File("res/input.jpg"));
		} catch (IOException e) {
			System.out.println("Can't import image");
			System.exit(0);
		}
		int[][][] pixels = getPixels(img);
		
		
		//Select what function you want to run
		
		pixels = Decay.make(pixels);
		
		//
		
		saveImageFromPixels(pixels, "output.jpg", "jpg", img.getWidth(), img.getHeight(), img.getType());
	}
}