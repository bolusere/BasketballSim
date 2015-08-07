package players;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import player.Stats;
import player.StatsType;

public class StatsTest {
  private Stats stats;

  @Test
  public void ayy() {
    assertThat(stats.toString(), is(equalTo("ayy")));
  }

  @Before
  public void initializeStats() {
    stats = new Stats();
    stats.increment(StatsType.THREES_GA);
  }

  @Test
  public void resetClearsFields() {
    stats.reset();
    for (final StatsType type : StatsType.values()) {
      assertThat("A field was not zero after reset", stats.get(type), is(0));
    }
  }
}
