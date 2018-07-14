package imagemanipulator;

public class BlurAveraging {
	public static int[] blur(int x, int y, int[][][] pixels, int kernelWidth, int kernelHeight) {
		if (kernelWidth%2 == 0) kernelWidth++;
		if (kernelHeight%2 == 0) kernelHeight++;
		int num = 0;
		int[] sum = new int[3];
		for (int yi = y-(kernelHeight-1)/2; yi <= y+(kernelHeight-1)/2; yi++) {
			if (yi < 0 || yi >= pixels.length) continue;
			for (int xi = x-(kernelWidth-1)/2; xi <= x+(kernelWidth-1)/2; xi++) {
				if (xi < 0 || xi >= pixels[0].length) continue;
				for (int pi = 0; pi < sum.length; pi++) {
					sum[pi] += pixels[yi][xi][pi];
					if (pixels[yi][xi][pi] < 0) sum[pi] += 256;
				}
				num++;
			}
		}
		for (int pi = 0; pi < sum.length; pi++) {
			sum[pi] = (int)sum[pi]/num;
			if (sum[pi] > 127) {
				sum[pi]-=256;
			}
		}
		return sum;
	}
	
	public static int[] blur(int x, int y, int[][][] pixels) {
		return blur(x,y,pixels, 3, 3);
	}
	
	public static int[][][] make(int[][][] pixels) {
		for (int y = 0; y < pixels.length; y++) {
			for (int x = 0; x < pixels[0].length; x++) {
				pixels[y][x] = blur(x, y, pixels);
			}
		}
		return pixels;
	}
	
	public static int[][][] make(int[][][] pixels, int kernelWidth, int kernelHeight) {
		for (int y = 0; y < pixels.length; y++) {
			for (int x = 0; x < pixels[0].length; x++) {
				pixels[y][x] = blur(x, y, pixels, kernelWidth, kernelHeight);
			}
		}
		return pixels;
	}
}
