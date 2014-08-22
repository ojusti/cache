package ro.j.cache;

public class OverflowLimit {

	private final int maxSize;

	OverflowLimit(int maxSize) {
		this.maxSize = maxSize;
	}

	public boolean isExceeded(int size) {
		return size > maxSize;
	}

}
