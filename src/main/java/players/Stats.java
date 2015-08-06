package players;

import java.util.Arrays;

public class Stats {
  private final int[] stats;

  public Stats() {
    stats = new int[StatsTypes.values().length];
  }

  public void add(final Stats otherStats) {
    for (int i = 0; i < stats.length; i++) {
      this.stats[i] += otherStats.stats[i];
    }
  }

  public void add(final StatsTypes type, final int amount) {
    stats[type.getValue()] += amount;
  }

  public int get(final StatsTypes type) {
    return stats[type.getValue()];
  }

  public void increment(final StatsTypes type) {
    stats[type.getValue()]++;
  }

  public void reset() {
    Arrays.fill(stats, 0);
  }

  @Override
  public String toString() {
    return Arrays.toString(stats);
  }
}
