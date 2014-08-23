package ro.j.cache;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

public class OverflowLimitTest {

	@Test
	public void noOverflow() {
		OverflowLimit noOverflow = OverflowLimitBuilder.noOverflow();
		assertThat(noOverflow.isExceeded(0)).isFalse();
		assertThat(noOverflow.isExceeded(12345)).isFalse();
		assertThat(noOverflow.isExceeded(Integer.MAX_VALUE)).isFalse();
	}

}
