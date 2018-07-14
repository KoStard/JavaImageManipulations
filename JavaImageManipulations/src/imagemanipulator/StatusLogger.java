package imagemanipulator;

/**
 * You can use this just with static methods (started, finished, print)
 * or create an instance and embeding this to your code.
 * For example if you use blur method from ImageManipulator, you'll get something like this.
 * |-->> avBlur started
 * |-->> Current avBlur status 0,05
 * |-->> Current avBlur status 0,10
 * |-->> ...
 * |-->> Current avBlur status 0,90
 * |-->> Current avBlur status 0,95
 * |-->> avBlur finished
 * 
 * @author KoStard
 *
 */

public class StatusLogger {
	String name;
	long size, current;
	long step, last;
	double stepPercentage, lastPercentage;
	public StatusLogger(String name, long size) {
		this.name = name;
		this.size = size;
	}
	public StatusLogger(String name, long size, int step) {
		this(name, size);
		this.step = step;
	}
	public StatusLogger(String name, long size, double stepPercentage) {
		this(name, size);
		this.stepPercentage = stepPercentage;
	}
	public void progress (int prg) {
		current+=prg;
		checkProgressStatus();
	}
	public void setCurrent (int newCurrent) {
		current = newCurrent;
		checkProgressStatus();
	}
	void checkProgressStatus() {
		if (step > 0) {
			if (current-last >= step) {
				last += step;
				logProgress();
			}
		} else if (stepPercentage > 0) {
			double currentPercentage = (double)current/(double)size;
			if (currentPercentage-lastPercentage >= stepPercentage) {
				lastPercentage += stepPercentage;
				logProgressPercentage(currentPercentage);
			}
		} else {
			logProgressPercentage();
		}
	}
	public void logProgress() {
		print(String.format("Current status %d/%d", current, size));
	}
	public void logProgressPercentage() {
		double currentPercentage = (double)current/(double)size;
		logProgressPercentage(currentPercentage);
	}
	public void logProgressPercentage(double currentPercentage) {
		print(String.format("Current %s status %.2f", name, currentPercentage));
	}
	public static void print(String text) {
		System.out.println("|-->> "+text);
	}
	public static void started(String name) {
		print(name + " started");
	}
	public static void finished(String name) {
		print(name + " finished");
	}
}
