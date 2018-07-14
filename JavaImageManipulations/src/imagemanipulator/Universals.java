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
}
