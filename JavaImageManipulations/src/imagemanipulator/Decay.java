package imagemanipulator;

public class Decay { // making differences less expressed
	// 50 - 103 (all is getting closed to 127.5)
	// -50 - -105
	// Is the case of negatives, you have to know, that 255 is -1
	static int calc(int c) {
		double d = (double)c-127.5;
		if (c < 0) {
			d += 256;
		}
		double sq = Math.pow(d/255, 2);
		int res = (int)(sq*255*LocalMath.getSign(d)+127.5);
		if (res > 127) res-=256;
		return res;
	}
	public static int[][][] make(int[][][] pixels) {
		for (int y = 0; y < pixels.length; y++) {
			for (int x = 0; x < pixels[0].length; x++) {
				int[] current = pixels[y][x]; 
				pixels[y][x] = new int[]{calc(current[0]),
						calc(current[1]),
						calc(current[2])};
			}
		}
		return pixels;
	}
}
