package players;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class StatsTest {
  private Stats stats;

  @Test
  public void ayy() {
    assertThat(stats.toString(), is(equalTo("ayy")));
  }

  private int countMatches(final String str, final String token) {
    return str.length() - str.replace(token, "").length();
  }

  @Before
  public void initializeStats() {
    stats = new Stats();
    stats.increment(StatsTypes.THREES_GA);
  }

  @Test
  public void resetClearsFields() {
    stats.reset();
    assertThat("All fields were not 0", countMatches(stats.toString(), "0"),
        is(11));
  }
}
