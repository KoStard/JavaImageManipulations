package imagemanipulator;

import java.util.Random;

public class Noise {
	public static int[][][] createNoise(int[][][] pixels, boolean colored, int rangeFrom, int rangeTo){
		Random r = new Random();
		for (int y = 0; y < pixels.length; y++) {
			for (int x = 0; x < pixels[0].length; x++) {
				if (colored) {
					for (int i = 0; i < pixels[y][x].length; i++) {
						int d = (int)((r.nextDouble())*rangeTo+rangeFrom);
						if (d > rangeTo) d = rangeTo;
						pixels[y][x][i] = Universals.standardizeByte(Universals.standardizeByte(pixels[y][x][i])
								+ d);
					}
				} else {
					int d = (int)((r.nextDouble())*rangeTo+rangeFrom);
					if (d > rangeTo) d = rangeTo;
					for (int i = 0; i < pixels[y][x].length; i++) {
						pixels[y][x][i] = Universals.standardizeByte(Universals.standardizeByte(pixels[y][x][i])
								+ d);
					}
				}
			}
		}
		return pixels;
	}
}
