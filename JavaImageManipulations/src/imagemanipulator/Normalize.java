package imagemanipulator;

public class Normalize {
	public static int[] calc(int[] pixel) {
		if (pixel.length == 4) {
			int[] res = calc(new int[] {pixel[1], pixel[2], pixel[3]});
			return new int[] {pixel[0], res[0], res[1], res[2]};
		}
		int[] res = new int[3];
		int sum = 0;
		for (int c:pixel) {
			sum += Universals.standardizeByte(c);
		}
		if (sum==0) return res;
		for (int i = 0; i < pixel.length; i++) {
			res[i] = Universals.standardizeByte(Universals.standardizeByte(pixel[i])*255/sum);
		}
		return res;
	}
 	public static int[][][] make(int[][][] pixels) {
		for (int y = 0; y < pixels.length; y++) {
			for (int x = 0; x < pixels[0].length; x++) {
				pixels[y][x] = calc(pixels[y][x]);
			}
		}
		return pixels;
	}
}
