/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import player.Player;
import player.Position;
import player.RatingsType;
import player.StatsType;

import java.util.List;

/**
 *
 * @author Achi Jones
 */
public class Team {
  private static final Logger LOG = LoggerFactory.getLogger(Team.class);

  public Player[] playersInTeam;
  public int wins;
  int losses;
  int games;
  int pointsFor;
  int pointsAga;
  public String name;
  int[] startersIn;
  int[] benchIn;

  public Team(String name) {
    this.name = name;
    wins = 0;
    losses = 0;
    games = 0;
    playersInTeam = new Player[10];
    startersIn = new int[5];
    benchIn = new int[5];
    for (int i = 0; i < 5; ++i) {
      startersIn[i] = 1;
      benchIn[i] = 0;
    }
  }

  public void addPlayer(Player player) {
    // add player (used by AI)
    if (playersInTeam[player.getPosition().getValue() - 1] == null) {
      // no starter yet
      playersInTeam[player.getPosition().getValue() - 1] = player;
    } else {
      // put in bench
      playersInTeam[player.getPosition().getValue() - 1 + 5] = player;
    }

  }

  public double get3GAPG() {
    return getTeamStatsPerGame(StatsType.THREES_GA);
  }

  public double get3GP() {
    double res = 0;
    for (final Player player : playersInTeam) {
      res += player.get3GP()
          * (player.getPerGameStatsOfType(StatsType.THREES_GA) / get3GAPG());
    }
    return (double) ((int) (res * 10)) / 10;
  }

  public double getAPG() {
    return getTeamStatsPerGame(StatsType.ASSISTS);
  }

  public double getBPG() {
    return getTeamStatsPerGame(StatsType.BLOCKS);
  }

  public double getFGAPG() {
    return getTeamStatsPerGame(StatsType.FGA);
  }

  public double getFGP() {
    double res = 0.0;
    for (final Player player : playersInTeam) {
      res += player.getFGP()
          * (player.getPerGameStatsOfType(StatsType.FGA) / getFGAPG());
    }
    return (double) ((int) (res * 10)) / 10;
  }

  public int getLosses82() {
    return 82 - getWins82();
  }

  public double getOFP() {
    int totalOFA = 0;
    int totalOFM = 0;
    for (final Player player : playersInTeam) {
      totalOFA += player.getTotalStatsOfType(StatsType.OFA);
      totalOFM += player.getTotalStatsOfType(StatsType.OFM);
    }
    LOG.info("{} {}/{}", name, totalOFM, totalOFA);
    return (double) ((int) ((float) totalOFM / totalOFA * 1000)) / 10;
  }

  public String getPDStr() {
    final double pd = getPointDiff();
    if (pd >= 0) {
      return "+" + pd;
    } else {
      return "" + pd;
    }
  }

  public Player getPlayer(final Position position) {
    return playersInTeam[position.getValue() - 1];
  }

  public double getPointDiff() {
    final double pd = (double) (pointsFor - pointsAga) / games;
    return (double) ((int) (pd * 10)) / 10;
  }

  // getters for team stats per game
  public double getPPG() {
    double res = 0.0;
    for (final Player player : playersInTeam) {
      res += player.getPPG();
    }
    return res;
  }

  public double getRPG() {
    return getTeamStatsPerGame(StatsType.REBOUNDS);
  }

  public double getSPG() {
    return getTeamStatsPerGame(StatsType.STEALS);
  }

  private double getTeamStatsPerGame(StatsType type) {
    double res = 0;
    for (final Player player : playersInTeam) {
      res += player.getPerGameStatsOfType(type);
    }
    return (double) ((int) (res * 10)) / 10;
  }

  public int getTotalRating(RatingsType type) {
    int totalRating = 0;
    for (final Position position : Position.values()) {
      totalRating += getPlayer(position).getRating(type);
    }
    return totalRating;
  }

  public int getWins82() {
    return (int) (wins * (float) 82 / games);
  }

  public void selectPlayer(List<Player> players) {
    // assumes list is sorted by overall
    for (int p = 0; p < players.size(); ++p) {
      if (playersInTeam[0] != null && playersInTeam[1] != null
          && playersInTeam[2] != null && playersInTeam[3] != null
          && playersInTeam[4] != null) {
        // starters all selected, need bench
        if (playersInTeam[players.get(p).getPosition().getValue() - 1 + 5] == null) {
          // dont have bench guy in this position yet
          final Player selectedPlayer = players.get(p);
          addPlayer(selectedPlayer);
          players.remove(selectedPlayer);
          LOG.info("{} selected {} for bench", name, selectedPlayer.name);
          return;
        }
      } else {
        if (playersInTeam[players.get(p).getPosition().getValue() - 1] == null) {
          // dont have starter in this position yet
          final Player selectedPlayer = players.get(p);
          addPlayer(selectedPlayer);
          players.remove(selectedPlayer);
          LOG.info("{} selected {}", name, selectedPlayer.name);
          return;
        }
      }
    }
    LOG.info("{} DIDNT PICK ENOUGH PEOPLE!", name);
  }

  public void subPlayers(double time) {
    // sub players based on game time
    time = time / 60;
    // PG
    if (startersIn[0] == 1
        && benchIn[0] == 0
        && time >= (double) getPlayer(Position.POINT_GUARD).getPlayingTime() / 2
        && time < 47 - (double) getPlayer(Position.POINT_GUARD)
            .getPlayingTime() / 2) {
      // sub out starting PG
      final Player tmp = playersInTeam[0];
      playersInTeam[0] = playersInTeam[5];
      playersInTeam[5] = tmp;
      startersIn[0] = 0;
      benchIn[0] = 1;
      if ("PLAYER TEAM".equals(name) && games == 0) {
        LOG.info("Subbed out {} for {} at time {}", playersInTeam[5].name,
            getPlayer(Position.POINT_GUARD).name, time);
      }
    } else if (startersIn[0] == 0
        && benchIn[0] == 1
        && time >= 48 - (double) getPlayer(Position.POINT_GUARD)
            .getPlayingTime() / 2) {
      // sub in starting PG
      final Player tmp = playersInTeam[0];
      playersInTeam[0] = playersInTeam[5];
      playersInTeam[5] = tmp;
      startersIn[0] = 1;
      benchIn[0] = 0;
      if ("PLAYER TEAM".equals(name) && games == 0) {
        LOG.info("Subbed out {} for {} at time {}", playersInTeam[5].name,
            getPlayer(Position.POINT_GUARD).name, time);
      }
    }
    // SG
    if (startersIn[1] == 1
        && benchIn[1] == 0
        && time >= (double) getPlayer(Position.SHOOTING_GUARD).getPlayingTime() / 2
        && time < 47 - (double) getPlayer(Position.SHOOTING_GUARD)
        .getPlayingTime() / 2) {
      // sub out starting SG
      final Player tmp = playersInTeam[1];
      playersInTeam[1] = playersInTeam[6];
      playersInTeam[6] = tmp;
      startersIn[1] = 0;
      benchIn[1] = 1;
    } else if (startersIn[1] == 0
        && benchIn[1] == 1
        && time >= 48 - (double) getPlayer(Position.SHOOTING_GUARD)
        .getPlayingTime() / 2) {
      // sub in starting SG
      final Player tmp = playersInTeam[1];
      playersInTeam[1] = playersInTeam[6];
      playersInTeam[6] = tmp;
      startersIn[1] = 1;
      benchIn[1] = 0;
    }
    // SF
    if (startersIn[2] == 1
        && benchIn[2] == 0
        && time >= (double) getPlayer(Position.SMALL_FORWARD).getPlayingTime() / 2
        && time < 47 - (double) getPlayer(Position.SMALL_FORWARD)
            .getPlayingTime() / 2) {
      // sub out starting SF
      final Player tmp = playersInTeam[2];
      playersInTeam[2] = playersInTeam[7];
      playersInTeam[7] = tmp;
      startersIn[2] = 0;
      benchIn[2] = 1;
    } else if (startersIn[2] == 0
        && benchIn[2] == 1
        && time >= 48 - (double) getPlayer(Position.SMALL_FORWARD)
            .getPlayingTime() / 2) {
      // sub in starting SF
      final Player tmp = playersInTeam[2];
      playersInTeam[2] = playersInTeam[7];
      playersInTeam[7] = tmp;
      startersIn[2] = 1;
      benchIn[2] = 0;
    }
    // PF
    if (startersIn[3] == 1
        && benchIn[3] == 0
        && time >= (double) getPlayer(Position.POWER_FORWARD).getPlayingTime() / 2
        && time < 47 - (double) getPlayer(Position.POWER_FORWARD)
        .getPlayingTime() / 2) {
      // sub out starting PF
      final Player tmp = playersInTeam[3];
      playersInTeam[3] = playersInTeam[8];
      playersInTeam[8] = tmp;
      startersIn[3] = 0;
      benchIn[3] = 1;
    } else if (startersIn[3] == 0
        && benchIn[3] == 1
        && time >= 48 - (double) getPlayer(Position.POWER_FORWARD)
        .getPlayingTime() / 2) {
      // sub in starting PF
      final Player tmp = playersInTeam[3];
      playersInTeam[3] = playersInTeam[8];
      playersInTeam[8] = tmp;
      startersIn[3] = 1;
      benchIn[3] = 0;
    }
    // C
    if (startersIn[4] == 1 && benchIn[4] == 0
        && time >= (double) getPlayer(Position.CENTER).getPlayingTime() / 2
        && time < 47 - (double) getPlayer(Position.CENTER).getPlayingTime() / 2) {
      // sub out starting C
      final Player tmp = playersInTeam[4];
      playersInTeam[4] = playersInTeam[9];
      playersInTeam[9] = tmp;
      startersIn[4] = 0;
      benchIn[4] = 1;
    } else if (startersIn[4] == 0
        && benchIn[4] == 1
        && time >= 48 - (double) getPlayer(Position.POWER_FORWARD)
        .getPlayingTime() / 2) {
      // sub in starting C
      final Player tmp = playersInTeam[4];
      playersInTeam[4] = playersInTeam[9];
      playersInTeam[9] = tmp;
      startersIn[4] = 1;
      benchIn[4] = 0;
    }
  }

}
