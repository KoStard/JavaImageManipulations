package imagemanipulator;

public class Universals {
	public static void printArray(int[]... arrs) {
		int index = 0;
		for (int[] arr : arrs) {
			for (int c: arr) {
				System.out.print(c+" ");
			}
			index ++;
			if (index!=arrs.length)
			System.out.print(" - ");
		}
		System.out.println();
	}
	public static int standardizeByte(int b) { // Solving the problem with byte sign -
//		if (b > 255) b = b%255; another interesting method of standardization
		if (b > 255) b = 255;
		if (b < 0) b += 256;
		else if (b > 127) b-=256;
		return b;
	}
}
