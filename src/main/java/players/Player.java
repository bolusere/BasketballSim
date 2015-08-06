/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package players;

/**
 *
 * @author Achi Jones
 */
public class Player {

  public String name;
  public String attributes;
  // attributes = "3p ID DNK CHU WALL"
  public int gamesPlayed;
  private Stats statsPerGame;
  private Stats statsTotal;
  public int[] ratings;

  // ratings = { 0 Position, 1 Overall, 2 Int_S, 3 Mid_S, 4 Out_S, 5 Passing,
  // 6 Handling, 7 Steal, 8 Block, 9 Int_D, 10 Out_D,
  // 11 Rebounding, 12 Usage, 13 Ins_T, 14 Mid_T, 15 Out_T }

  public Player(String name, int[] ratings) {
    initializePlayer(name, ratings, "");
  }

  public Player(String name, int[] ratings, String attributes) {
    initializePlayer(name, ratings, attributes);
  }

  public void add3GA() {
    statsPerGame.increment(StatsTypes.THREES_GA);
  }

  public void add3GM() {
    statsPerGame.increment(StatsTypes.THREES_GM);
  }

  public void addAssist() {
    statsPerGame.increment(StatsTypes.ASSISTS);
  }

  public void addBlock() {
    statsPerGame.increment(StatsTypes.BLOCKS);
  }

  public void addFGA() {
    statsPerGame.increment(StatsTypes.FGA);
  }

  public void addFGM() {
    statsPerGame.increment(StatsTypes.FGM);
  }

  public void addGameStatsToTotal() {
    statsTotal.add(statsPerGame);
    statsPerGame.reset();
    gamesPlayed++;
  }

  public void addMismatch(final int amountMismatch) {
    statsTotal.add(StatsTypes.MSM, amountMismatch);
  }

  public void addOFA() {
    statsPerGame.increment(StatsTypes.OFA);
  }

  public void addOFM() {
    statsPerGame.increment(StatsTypes.OFM);
  }

  public void addPoints(int points) {
    statsPerGame.add(StatsTypes.POINTS, points);
  }

  public void addRebounds() {
    statsPerGame.increment(StatsTypes.REBOUNDS);
  }

  public void addSteals() {
    statsPerGame.increment(StatsTypes.STEALS);
  }

  private double convertTotalToPerGame(StatsTypes type) {
    return (double) ((int) ((double) statsTotal.get(type) / gamesPlayed * 10)) / 10;
  }

  public double get3GAPG() {
    return convertTotalToPerGame(StatsTypes.THREES_GA);
  }

  public double get3GP() {
    if (statsTotal.get(StatsTypes.THREES_GA) > 0) {
      return (double) ((int) ((double) statsTotal.get(StatsTypes.THREES_GM)
          / statsTotal.get(StatsTypes.THREES_GA) * 1000)) / 10;
    } else {
      return 0;
    }
  }

  public double getAPG() {
    return convertTotalToPerGame(StatsTypes.ASSISTS);
  }

  public int getBlockRating() {
    return ratings[8];
  }

  public double getBPG() {
    return convertTotalToPerGame(StatsTypes.BLOCKS);
  }

  public double getFGAPG() {
    return convertTotalToPerGame(StatsTypes.FGA);
  }

  public double getFGP() {
    return (double) ((int) ((double) statsTotal.get(StatsTypes.FGM)
        / statsTotal.get(StatsTypes.FGA) * 1000)) / 10;
  }

  public int getHandRating() {
    return ratings[6];
  }

  public double getInsT() {
    return (double) ratings[13] / 1000;
  }

  public int getIntD() {
    return ratings[9];
  }

  public int getIntS() {
    return ratings[2];
  }

  public int getMidS() {
    return ratings[3];
  }

  public double getMidT() {
    return (double) ratings[14] / 1000;
  }

  public int getMSM() {
    return (int) ((double) statsTotal.get(StatsTypes.MSM) / (gamesPlayed));
  }

  public double getOFP() {
    return (double) ((int) ((double) statsTotal.get(StatsTypes.OFM)
        / statsTotal.get(StatsTypes.OFA) * 1000)) / 10;
  }

  public int getOutD() {
    return ratings[10];
  }

  public int getOutS() {
    return ratings[4];
  }

  public double getOutT() {
    return (double) ratings[15] / 1000;
  }

  public int getOverall() {
    return ratings[1];
  }

  public int getPass() {
    return ratings[5];
  }

  public int getPerGameStatsOfType(StatsTypes type) {
    return statsPerGame.get(type);
  }

  public int getPlayingTime() {
    // playing time in minutes
    return 25 + getOverall() / 8;
  }

  public int getPosition() {
    return ratings[0];
  }

  public double getPPG() {
    return convertTotalToPerGame(StatsTypes.POINTS);
  }

  public int getReboundRating() {
    return ratings[11];
  }

  public double getRPG() {
    return convertTotalToPerGame(StatsTypes.REBOUNDS);
  }

  public double getSPG() {
    return convertTotalToPerGame(StatsTypes.STEALS);
  }

  public int getStealRating() {
    return ratings[7];
  }

  public int getTotalStatsOfType(StatsTypes type) {
    return statsTotal.get(type);
  }

  public int getUsage() {
    return ratings[12];
  }

  private void initializePlayer(String name, int[] ratings, String attributes) {
    statsPerGame = new Stats();
    statsTotal = new Stats();
    gamesPlayed = 0;
    this.ratings = ratings;
    this.name = name;
    this.attributes = attributes;
  }

  public void make3ptShot() {
    addPoints(3);
    addFGM();
    addFGA();
    add3GA();
    add3GM();
  }

}
