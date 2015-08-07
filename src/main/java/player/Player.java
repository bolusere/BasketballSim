/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

/**
 *
 * @author Achi Jones
 */
public class Player {

  public String name;
  private Position position;
  public int gamesPlayed;
  private final Stats statsCurrentGame;
  private final Stats statsTotal;
  private final Ratings ratings;

  public Player(String name, Position position) {
    statsCurrentGame = new Stats();
    statsTotal = new Stats();
    gamesPlayed = 0;
    this.name = name;
    this.position = position;
    ratings = new Ratings(position);
  }

  public void addCurrentGameStat(StatsType stat, int amount) {
    statsCurrentGame.add(stat, amount);
  }

  public void addGameStatsToTotal() {
    statsTotal.add(statsCurrentGame);
    statsCurrentGame.reset();
    gamesPlayed++;
  }

  public void addMismatch(final int amountMismatch) {
    statsTotal.add(StatsType.MSM, amountMismatch);
  }

  private void allowShot() {
    incrementCurrentGameStat(StatsType.OFA);
    incrementCurrentGameStat(StatsType.OFM);
  }

  public void block(Player shooter) {
    shooter.incrementCurrentGameStat(StatsType.FGA);
    incrementCurrentGameStat(StatsType.OFA);
    incrementCurrentGameStat(StatsType.BLOCKS);
  }

  private double convertTotalToPerGame(StatsType type) {
    return (double) ((int) ((double) statsTotal.get(type) / gamesPlayed * 10)) / 10;
  }

  public double get3GP() {
    if (statsTotal.get(StatsType.THREES_GA) > 0) {
      return (double) ((int) ((double) statsTotal.get(StatsType.THREES_GM)
          / statsTotal.get(StatsType.THREES_GA) * 1000)) / 10;
    } else {
      return 0;
    }
  }

  public String getAttributes() {
    return ratings.getAttributes();
  }

  public double getFGP() {
    return (double) ((int) ((double) statsTotal.get(StatsType.FGM)
        / statsTotal.get(StatsType.FGA) * 1000)) / 10;
  }

  public double getOFP() {
    return (double) ((int) ((double) statsTotal.get(StatsType.OFM)
        / statsTotal.get(StatsType.OFA) * 1000)) / 10;
  }

  public int getPerGameStatsOfType(StatsType type) {
    return statsCurrentGame.get(type);
  }

  public int getPlayingTime() {
    // playing time in minutes
    return 25 + getRating(RatingsType.OVERALL) / 8;
  }

  public Position getPosition() {
    return position;
  }

  public double getPPG() {
    return convertTotalToPerGame(StatsType.POINTS);
  }

  public int getRating(RatingsType type) {
    return ratings.getRating(type);
  }

  public double getStatPerGame(StatsType stat) {
    return convertTotalToPerGame(stat);
  }

  public int getTotalStatsOfType(StatsType type) {
    return statsTotal.get(type);
  }

  public void incrementCurrentGameStat(StatsType stat) {
    statsCurrentGame.increment(stat);
  }

  private void makeShot(final int points, final Player defender) {
    addCurrentGameStat(StatsType.POINTS, points);
    incrementCurrentGameStat(StatsType.FGA);
    incrementCurrentGameStat(StatsType.FGM);
    defender.allowShot();
  }

  public void makeThreePointShot(final Player defender) {
    makeShot(3, defender);
  }

  public void makeTwoPointShot(final Player defender) {
    makeShot(2, defender);
  }

  public void missThreePointShot(final Player defender) {
    missTwoPointShot(defender);
    incrementCurrentGameStat(StatsType.THREES_GA);
  }

  public void missTwoPointShot(final Player defender) {
    incrementCurrentGameStat(StatsType.FGA);
    defender.incrementCurrentGameStat(StatsType.OFA);
  }

  public void setPosition(final Position position) {
    this.position = position;
  }

}
