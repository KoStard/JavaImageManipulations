package imagemanipulator;

public class Treshold {
	public static enum Color {RED(2, "Red"), GREEN(1, "Green"), BLUE(0, "Blue");
		public int index = -1;
		public String name = "null";
		Color(int index, String name) {
			this.index = index;
			this.name = name;
		}}
	public static int[] calc(int[] pixel, int treshold, Color color) {
		int[] res = null;
		if (pixel.length == 3) {
			res = new int[3];
			int d = 0;
			for (int i = 0; i < pixel.length; i++) {
				if (i != color.index) {
					d -= Universals.standardizeByte(pixel[i]);
				} else {
					d += Universals.standardizeByte(pixel[i]);
				}
			}
			if (d > treshold) {
				res[color.index] = -1;
			}
		} else if (pixel.length == 4) {
			res = new int[4];
			res[0] = pixel[0];
			int d = 0;
			for (int i = 1; i < pixel.length; i++) {
				if (i != color.index+1) {
					d -= Universals.standardizeByte(pixel[i]);
				} else {
					d += Universals.standardizeByte(pixel[i]);
				}
			}
			if (d > treshold) {
				res[color.index+1] = -1;
			}
		}
		return res;
	}
	public static int[][][] make(int[][][] pixels, int treshold, Color color) {
		for (int y = 0; y < pixels.length; y++) {
			for (int x = 0; x < pixels[0].length; x++) {
				pixels[y][x] = calc(pixels[y][x], treshold, color);
			}
		}
		return pixels;
	}
}
