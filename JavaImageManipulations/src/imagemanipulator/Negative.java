package imagemanipulator;

public class Negative {
	public static int[][][] make(int[][][] pixels){
		for (int y = 0; y < pixels.length; y++) {
			for (int x = 0; x < pixels[0].length; x++) {
				int[] current = pixels[y][x]; 
				pixels[y][x] = new int[]{255-current[0], 255-current[1], 255-current[2]};
			}
		}
		return pixels;
	}
}
