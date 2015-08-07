package player;

public enum Position {

  POINT_GUARD(1), SHOOTING_GUARD(2), SMALL_FORWARD(3), POWER_FORWARD(4), CENTER(5);

  private final int value;

  private Position(final int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    switch (this) {
    case POINT_GUARD:
      return "PG";
    case SHOOTING_GUARD:
      return "SG";
    case SMALL_FORWARD:
      return "SF";
    case POWER_FORWARD:
      return "PF";
    case CENTER:
      return "C";
    default:
      throw new IllegalArgumentException();
    }
  }
}
