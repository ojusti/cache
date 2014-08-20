package ro.j.cache.utils;

public class Misc {
	public static void assertNotNull(Object arg) {
		if (arg == null) {
			throw new IllegalArgumentException();
		}
	}
}
