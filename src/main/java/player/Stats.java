package player;

import java.util.Arrays;

public class Stats {
  private final int[] stats;

  public Stats() {
    stats = new int[StatsType.values().length];
  }

  public Stats(final Stats otherStats) {
    stats = Arrays.copyOf(otherStats.stats, otherStats.stats.length);
  }

  public void add(final Stats otherStats) {
    for (int i = 0; i < stats.length; i++) {
      this.stats[i] += otherStats.stats[i];
    }
  }

  public void add(final StatsType type, final int amount) {
    stats[type.ordinal()] += amount;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Stats other = (Stats) obj;
    if (!Arrays.equals(stats, other.stats)) {
      return false;
    }
    return true;
  }

  public int get(final StatsType type) {
    return stats[type.ordinal()];
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(stats);
    return result;
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
