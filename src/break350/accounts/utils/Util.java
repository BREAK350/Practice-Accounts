package break350.accounts.utils;

public class Util {
	public static double round(double value, int dec) {
		return (double) ((int) (value * dec)) / dec;
	}
}
