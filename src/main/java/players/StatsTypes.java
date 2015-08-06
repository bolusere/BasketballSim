package players;

public enum StatsTypes {

  POINTS(0), FGA(1), FGM(2), THREES_GA(3), THREES_GM(4), ASSISTS(5), REBOUNDS(6), STEALS(
    7), BLOCKS(8), OFA(9), OFM(10), MSM(11);

  private final int value;

  private StatsTypes(final int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

}
