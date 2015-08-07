package player;

import java.util.Arrays;

public class Stats {
  private final int[] stats;

  public Stats() {
    stats = new int[StatsType.values().length];
  }

  public void add(final Stats otherStats) {
    for (int i = 0; i < stats.length; i++) {
      this.stats[i] += otherStats.stats[i];
    }
  }

  public void add(final StatsType type, final int amount) {
    stats[type.ordinal()] += amount;
  }

  public int get(final StatsType type) {
    return stats[type.ordinal()];
  }

  public void increment(final StatsType type) {
    stats[type.ordinal()]++;
  }

  public void reset() {
    Arrays.fill(stats, 0);
  }

  @Override
  public String toString() {
    return Arrays.toString(stats);
  }
}
