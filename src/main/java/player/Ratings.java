package player;

public class Ratings {
  private int height;
  private int weight;
  private int speed;
  private int ratings[];
  private int extraUsage;
  private String attributes;

  public Ratings(Position position) {
    setDefaultRatings();
    adjustRatingsForPosition(position);
    generateAttributes();
    setDependentRatings();
  }

  private void adjustRatingsForCenter() {
    height += 8 * Math.random() + 2;
    weight += 40 * Math.random() + 40;
    speed += 20 * Math.random() - 30;
    ratings[RatingsType.INTERIOR_SHOOTING.ordinal()] += 10 * Math.random() + 5;
    ratings[RatingsType.MID_SHOOTING.ordinal()] += 20 * Math.random() - 15;
    ratings[RatingsType.OUTSIDE_SHOOTING.ordinal()] += 30 * Math.random() - 45;
    ratings[RatingsType.PASSING.ordinal()] += 20 * Math.random() - 20;
    ratings[RatingsType.HANDLING.ordinal()] += 30 * Math.random() - 40;
    ratings[RatingsType.STEALING.ordinal()] += 30 * Math.random() - 40;
    ratings[RatingsType.BLOCKING.ordinal()] += 10 * Math.random() + 5;
    ratings[RatingsType.INTERIOR_DEFENSE.ordinal()] += 10 * Math.random() + 5;
    ratings[RatingsType.OUTSIDE_DEFENSE.ordinal()] += 20 * Math.random() - 20;
    ratings[RatingsType.REBOUNDING.ordinal()] += 12 * Math.random() + 5;
  }

  private void adjustRatingsForPointGuard() {
    height -= 3 * Math.random() + 3;
    weight -= 30 * Math.random();
    speed += 5 * Math.random() + 5;
    ratings[RatingsType.INTERIOR_SHOOTING.ordinal()] -= 8 * Math.random() + 8;
    ratings[RatingsType.MID_SHOOTING.ordinal()] += 10 * Math.random() - 5;
    ratings[RatingsType.OUTSIDE_SHOOTING.ordinal()] += 10 * Math.random() - 5;
    ratings[RatingsType.PASSING.ordinal()] += 10 * Math.random() + 5;
    ratings[RatingsType.HANDLING.ordinal()] += 10 * Math.random();
    ratings[RatingsType.STEALING.ordinal()] += 10 * Math.random() - 2;
    ratings[RatingsType.BLOCKING.ordinal()] -= 20 * Math.random() + 20;
    ratings[RatingsType.INTERIOR_DEFENSE.ordinal()] -= 8 * Math.random() + 8;
    ratings[RatingsType.OUTSIDE_DEFENSE.ordinal()] += 10 * Math.random() - 5;
    ratings[RatingsType.REBOUNDING.ordinal()] -= 20 * Math.random() + 10;
  }

  private void adjustRatingsForPosition(Position position) {
    switch (position) {
    case POINT_GUARD:
      adjustRatingsForPointGuard();
      break;
    case SHOOTING_GUARD:
      adjustRatingsForShootingGuard();
      break;
    case SMALL_FORWARD:
      adjustRatingsForSmallForward();
      break;
    case POWER_FORWARD:
      adjustRatingsForPowerForward();
      break;
    case CENTER:
      adjustRatingsForCenter();
      break;
    default:
      throw new IllegalArgumentException();
    }
  }

  private void adjustRatingsForPowerForward() {
    height += 6 * Math.random() + 1;
    weight += 40 * Math.random() + 20;
    speed += 15 * Math.random() - 15;
    ratings[RatingsType.INTERIOR_SHOOTING.ordinal()] += 20 * Math.random() - 5;
    ratings[RatingsType.MID_SHOOTING.ordinal()] += 16 * Math.random() - 8;
    ratings[RatingsType.OUTSIDE_SHOOTING.ordinal()] += 12 * Math.random() - 6;
    ratings[RatingsType.PASSING.ordinal()] += 20 * Math.random() - 15;
    ratings[RatingsType.HANDLING.ordinal()] += 20 * Math.random() - 20;
    ratings[RatingsType.STEALING.ordinal()] += 20 * Math.random() - 15;
    ratings[RatingsType.BLOCKING.ordinal()] += 20 * Math.random() - 5;
    ratings[RatingsType.INTERIOR_DEFENSE.ordinal()] += 20 * Math.random() - 5;
    ratings[RatingsType.OUTSIDE_DEFENSE.ordinal()] += 10 * Math.random() - 8;
    ratings[RatingsType.REBOUNDING.ordinal()] += 20 * Math.random() - 5;
  }

  private void adjustRatingsForShootingGuard() {
    height += 4 * Math.random() - 3;
    weight += 30 * Math.random() - 15;
    speed += 6 * Math.random();
    ratings[RatingsType.INTERIOR_SHOOTING.ordinal()] += 16 * Math.random() - 8;
    ratings[RatingsType.MID_SHOOTING.ordinal()] += 13 * Math.random() - 5;
    ratings[RatingsType.OUTSIDE_SHOOTING.ordinal()] += 13 * Math.random() - 5;
    ratings[RatingsType.PASSING.ordinal()] += 10 * Math.random();
    ratings[RatingsType.HANDLING.ordinal()] += 10 * Math.random() - 2;
    ratings[RatingsType.STEALING.ordinal()] += 10 * Math.random() - 5;
    ratings[RatingsType.BLOCKING.ordinal()] -= 20 * Math.random() + 10;
    ratings[RatingsType.INTERIOR_DEFENSE.ordinal()] -= 5 * Math.random() + 5;
    ratings[RatingsType.OUTSIDE_DEFENSE.ordinal()] += 10 * Math.random() - 5;
    ratings[RatingsType.REBOUNDING.ordinal()] -= 10 * Math.random() + 5;
  }

  private void adjustRatingsForSmallForward() {
    height += 6 * Math.random() - 2;
    weight += 40 * Math.random() - 10;
    speed += 16 * Math.random() - 8;
    ratings[RatingsType.INTERIOR_SHOOTING.ordinal()] += 20 * Math.random() - 8;
    ratings[RatingsType.MID_SHOOTING.ordinal()] += 20 * Math.random() - 10;
    ratings[RatingsType.OUTSIDE_SHOOTING.ordinal()] += 20 * Math.random() - 10;
    ratings[RatingsType.PASSING.ordinal()] += 20 * Math.random() - 10;
    ratings[RatingsType.HANDLING.ordinal()] += 20 * Math.random() - 10;
    ratings[RatingsType.STEALING.ordinal()] += 20 * Math.random() - 10;
    ratings[RatingsType.BLOCKING.ordinal()] += 15 * Math.random() - 5;
    ratings[RatingsType.INTERIOR_DEFENSE.ordinal()] += 15 * Math.random() - 5;
    ratings[RatingsType.OUTSIDE_DEFENSE.ordinal()] += 15 * Math.random() - 5;
    ratings[RatingsType.REBOUNDING.ordinal()] += 15 * Math.random() - 5;
  }

  private int calculateOverall() {
    final int weightedScore = (int) Math.round(Math.pow(
        ratings[RatingsType.INTERIOR_SHOOTING.ordinal()], 1.3)
        + Math.pow(ratings[RatingsType.MID_SHOOTING.ordinal()], 1.3)
        + Math.pow(ratings[RatingsType.OUTSIDE_SHOOTING.ordinal()], 1.3)
        + Math.pow(ratings[RatingsType.PASSING.ordinal()], 1.1)
        + ratings[RatingsType.HANDLING.ordinal()]
            + Math.pow(ratings[RatingsType.STEALING.ordinal()], 1.1)
            + Math.pow(ratings[RatingsType.BLOCKING.ordinal()], 1.1)
            + Math.pow(ratings[RatingsType.INTERIOR_DEFENSE.ordinal()], 1.2)
            + Math.pow(ratings[RatingsType.OUTSIDE_DEFENSE.ordinal()], 1.2)
            + Math.pow(ratings[RatingsType.REBOUNDING.ordinal()], 1.2));
    return 100 * weightedScore / 2500;
  }

  private String generateAttributes() {
    attributes = "";
    int choice;
    for (int i = 0; i < 5; i++) {
      choice = (int) (Math.random() * 20);
      if (choice == 0) {
        // Passer
        ratings[RatingsType.PASSING.ordinal()] += 15 * Math.random() + 5;
        attributes += "Ps ";
      } else if (choice == 1) {
        // Off Weapon
        ratings[RatingsType.OUTSIDE_SHOOTING.ordinal()] += 5 * Math.random() + 5;
        ratings[RatingsType.MID_SHOOTING.ordinal()] += 6 * Math.random() + 6;
        ratings[RatingsType.INTERIOR_SHOOTING.ordinal()] += 7 * Math.random() + 7;
        attributes += "OW ";
      } else if (choice == 2) {
        // Blocker
        ratings[RatingsType.BLOCKING.ordinal()] += 10 * Math.random() + 5;
        attributes += "Bl ";
      } else if (choice == 3) {
        // Tall (does nothing?)
        height += 2;
        // att += "Tll ";
      } else if (choice == 4) {
        // Short (does nothing?)
        height -= 2;
        // att += "Sht ";
      } else if (choice == 5) {
        // On-Ball Defense
        ratings[RatingsType.STEALING.ordinal()] += 5 * Math.random() + 5;
        ratings[RatingsType.OUTSIDE_DEFENSE.ordinal()] += 5 * Math.random() + 5;
        attributes += "Ob ";
      } else if (choice == 6) {
        ratings[RatingsType.REBOUNDING.ordinal()] += 10 * Math.random() + 5;
        height += 1;
        attributes += "Rb ";
      } else if (choice == 7) {
        // Fumbler
        ratings[RatingsType.HANDLING.ordinal()] -= 5 * Math.random() + 5;
        ratings[RatingsType.PASSING.ordinal()] -= 5 * Math.random() + 5;
        attributes += "Fm ";
      } else if (choice == 8) {
        // Fatty
        weight += 30;
        // att += "Fa ";
      } else if (choice == 9) {
        // Slow
        speed -= 15;
        // att += "Sl ";
      } else if (choice == 10) {
        // No Threes
        ratings[RatingsType.OUTSIDE_SHOOTING.ordinal()] -= 10 * Math.random() + 15;
        if (ratings[RatingsType.OUTSIDE_SHOOTING.ordinal()] < 0) {
          ratings[RatingsType.OUTSIDE_SHOOTING.ordinal()] = 0;
        }
        attributes += "n3 ";
      } else if (choice == 11) {
        // Dunker
        ratings[RatingsType.INTERIOR_SHOOTING.ordinal()] += 10 * Math.random() + 10;
        attributes += "Dn ";
      } else if (choice == 12) {
        // Defensive Liability
        ratings[RatingsType.STEALING.ordinal()] -= 5 * Math.random() - 5;
        ratings[RatingsType.BLOCKING.ordinal()] -= 5 * Math.random() - 5;
        ratings[RatingsType.OUTSIDE_DEFENSE.ordinal()] -= 5 * Math.random() - 5;
        ratings[RatingsType.INTERIOR_DEFENSE.ordinal()] -= 5 * Math.random() - 5;
        attributes += "Dl ";
      } else if (choice == 13) {
        // Offensive Liability
        ratings[RatingsType.INTERIOR_SHOOTING.ordinal()] -= 5 * Math.random() - 5;
        ratings[RatingsType.MID_SHOOTING.ordinal()] -= 5 * Math.random() - 5;
        ratings[RatingsType.OUTSIDE_SHOOTING.ordinal()] -= 5 * Math.random() - 5;
        attributes += "Ol ";
      } else if (choice == 14) {
        // Mid Range Jesus
        ratings[RatingsType.MID_SHOOTING.ordinal()] += 12 * Math.random() + 5;
        attributes += "Md ";
      } else if (choice == 15) {
        // The Wall
        ratings[RatingsType.INTERIOR_DEFENSE.ordinal()] += 10 * Math.random() + 5;
        ratings[RatingsType.BLOCKING.ordinal()] += 5 * Math.random() + 5;
        attributes += "Wa ";
      } else if (choice == 16) {
        // 3pt Specialist
        ratings[RatingsType.OUTSIDE_SHOOTING.ordinal()] += 5 * Math.random() + 8;
        ratings[RatingsType.PASSING.ordinal()] -= 5 * Math.random() + 5;
        ratings[RatingsType.INTERIOR_SHOOTING.ordinal()] -= 5 * Math.random() + 5;
        ratings[RatingsType.MID_SHOOTING.ordinal()] -= 5 * Math.random() + 5;
        attributes += "3p ";
      } else if (choice == 17) {
        // Chucker
        if (extraUsage == 0) {
          extraUsage += 5000;
          attributes += "Ch ";
        }
      } else if (choice == 18 && Math.random() < 0.1) {
        // Whole Package (rare)
        extraUsage += 3;
        ratings[RatingsType.INTERIOR_SHOOTING.ordinal()] += 5 * Math.random() + 5;
        ratings[RatingsType.MID_SHOOTING.ordinal()] += 5 * Math.random() + 5;
        ratings[RatingsType.OUTSIDE_SHOOTING.ordinal()] += 5 * Math.random() + 5;
        ratings[RatingsType.PASSING.ordinal()] += 5 * Math.random() + 5;
        ratings[RatingsType.HANDLING.ordinal()] += 5 * Math.random() + 5;
        ratings[RatingsType.STEALING.ordinal()] += 5 * Math.random() + 5;
        ratings[RatingsType.BLOCKING.ordinal()] += 5 * Math.random() + 5;
        ratings[RatingsType.INTERIOR_DEFENSE.ordinal()] += 5 * Math.random() + 5;
        ratings[RatingsType.OUTSIDE_DEFENSE.ordinal()] += 5 * Math.random() + 5;
        attributes += "XX ";
      } else if (choice == 19) {
        // The Thief
        ratings[RatingsType.STEALING.ordinal()] += 10 * Math.random() + 5;
        attributes += "St ";
      }
    }
    return attributes;
  }

  public String getAttributes() {
    return attributes;
  }

  public int getRating(RatingsType type) {
    return ratings[type.ordinal()];
  }

  private void setDefaultRatings() {
    ratings = new int[RatingsType.values().length];
    final int defaultRating = 80;
    height = 78;
    weight = 180;
    speed = defaultRating;
    ratings[RatingsType.INTERIOR_SHOOTING.ordinal()] = defaultRating;
    ratings[RatingsType.MID_SHOOTING.ordinal()] = defaultRating;
    ratings[RatingsType.OUTSIDE_SHOOTING.ordinal()] = defaultRating;
    ratings[RatingsType.PASSING.ordinal()] = defaultRating;
    ratings[RatingsType.HANDLING.ordinal()] = defaultRating;
    ratings[RatingsType.STEALING.ordinal()] = defaultRating;
    ratings[RatingsType.BLOCKING.ordinal()] = defaultRating;
    ratings[RatingsType.INTERIOR_DEFENSE.ordinal()] = defaultRating;
    ratings[RatingsType.OUTSIDE_DEFENSE.ordinal()] = defaultRating;
    ratings[RatingsType.REBOUNDING.ordinal()] = defaultRating;
    extraUsage = 0;
  }

  private void setDependentRatings() {
    ratings[RatingsType.OVERALL.ordinal()] = calculateOverall();
    ratings[RatingsType.USAGE.ordinal()] = (int) (Math.round(Math.pow(
        getRating(RatingsType.INTERIOR_SHOOTING), 2)
        + Math.pow(getRating(RatingsType.MID_SHOOTING), 2)
        + Math.pow(getRating(RatingsType.OUTSIDE_SHOOTING), 2)) + extraUsage) / 1000;
    ratings[RatingsType.INSIDE_T.ordinal()] = (int) (1000 * Math.pow(
        getRating(RatingsType.INTERIOR_SHOOTING), 1.2) / (Math.pow(
            getRating(RatingsType.INTERIOR_SHOOTING), 1.2)
            + Math.pow(getRating(RatingsType.MID_SHOOTING), 1.2) + Math.pow(
                getRating(RatingsType.OUTSIDE_SHOOTING), 1.2)));
    ratings[RatingsType.MID_T.ordinal()] = (int) (1000 * Math.pow(
        getRating(RatingsType.MID_SHOOTING), 1.2) / (Math.pow(
            getRating(RatingsType.INTERIOR_SHOOTING), 1.2)
            + Math.pow(getRating(RatingsType.MID_SHOOTING), 1.2) + Math.pow(
                getRating(RatingsType.OUTSIDE_SHOOTING), 1.2)));
    ratings[RatingsType.OUTSIDE_T.ordinal()] = (int) (1000 * Math.pow(
        getRating(RatingsType.OUTSIDE_SHOOTING), 1.2) / (Math.pow(
            getRating(RatingsType.INTERIOR_SHOOTING), 1.2)
            + Math.pow(getRating(RatingsType.MID_SHOOTING), 1.2) + Math.pow(
                getRating(RatingsType.OUTSIDE_SHOOTING), 1.2)));
  }
}
