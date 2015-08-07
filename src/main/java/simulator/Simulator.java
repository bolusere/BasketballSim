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
 * Has all the code responsible for simulating games.
 *
 * @author Achi Jones
 */
public class Simulator {
  private static final Logger LOG = LoggerFactory.getLogger(Simulator.class);

  private int calcMismatch(Player shooter, Player defender) {
    final double intMis = (2 * shooter.getRating(RatingsType.INTERIOR_SHOOTING) - defender
        .getRating(RatingsType.INTERIOR_DEFENSE))
        * transformRating(shooter.getRating(RatingsType.INSIDE_T));
    final double midMis = (2 * shooter.getRating(RatingsType.MID_SHOOTING) - (float) (defender
        .getRating(RatingsType.INTERIOR_DEFENSE) + defender
        .getRating(RatingsType.OUTSIDE_DEFENSE)))
        * transformRating(shooter.getRating(RatingsType.MID_T));
    final double outMis = (2 * shooter.getRating(RatingsType.OUTSIDE_SHOOTING) - defender
        .getRating(RatingsType.OUTSIDE_DEFENSE))
        * transformRating(shooter.getRating(RatingsType.OUTSIDE_T));
    return (int) (Math.pow(shooter.getRating(RatingsType.USAGE)
        * (intMis + midMis + outMis), 1.3) / 100);
  }

  private int[] detectMismatch(Team offense, Team defense) {
    final int[] mismatches = new int[5];
    for (int i = 0; i < 5; ++i) {
      mismatches[i] = calcMismatch(offense.playersInTeam[i],
          defense.playersInTeam[i]);
    }
    return mismatches;
  }

  public Player findRebounder(Team t) {
    final double cnReb = t.getPlayer(Position.CENTER).getRating(
        RatingsType.REBOUNDING)
        * Math.random();
    final double pfReb = t.getPlayer(Position.POWER_FORWARD).getRating(
        RatingsType.REBOUNDING)
        * Math.random();
    final double sfReb = t.getPlayer(Position.SMALL_FORWARD).getRating(
        RatingsType.REBOUNDING)
        * Math.random();
    final double sgReb = t.getPlayer(Position.SHOOTING_GUARD).getRating(
        RatingsType.REBOUNDING)
        * Math.random();
    final double pgReb = t.getPlayer(Position.POINT_GUARD).getRating(
        RatingsType.REBOUNDING)
        * Math.random();
    if (pgReb > pfReb && pgReb > sfReb && pgReb > sgReb && pgReb > cnReb) {
      return t.getPlayer(Position.POINT_GUARD);
    } else if (sgReb > cnReb && sgReb > pfReb && sgReb > sfReb && sfReb > pgReb) {
      return t.getPlayer(Position.SHOOTING_GUARD);
    } else if (sfReb > cnReb && sfReb > pfReb && sfReb > sgReb && sfReb > pgReb) {
      return t.getPlayer(Position.SMALL_FORWARD);
    } else if (pfReb > cnReb && pfReb > sfReb && pfReb > sgReb && pfReb > pgReb) {
      return t.getPlayer(Position.POWER_FORWARD);
    } else {
      return t.getPlayer(Position.CENTER);
    }
  }

  public Player getBallCarrier(Team t) {
    final double sfBall = t.getPlayer(Position.SMALL_FORWARD).getRating(
        RatingsType.PASSING)
        * Math.random();
    final double sgBall = t.getPlayer(Position.SHOOTING_GUARD).getRating(
        RatingsType.PASSING)
        * Math.random();
    final double pgBall = t.getPlayer(Position.POINT_GUARD).getRating(
        RatingsType.PASSING)
        * Math.random();
    if (sfBall > sgBall && sfBall > pgBall) {
      return t.getPlayer(Position.SMALL_FORWARD);
    } else if (sgBall > sfBall && sgBall > pgBall) {
      return t.getPlayer(Position.SHOOTING_GUARD);
    } else {
      return t.getPlayer(Position.POINT_GUARD);
    }
  }

  private Player intelligentPass(Player whoPoss, Team offense, Team defense,
      int[] matches) {
    // pass intelligently
    final int mismFactor = 18;
    final double pgTen = offense.getPlayer(Position.POINT_GUARD).getRating(
        RatingsType.USAGE)
        + (float) matches[0] / mismFactor;
    final double sgTen = offense.getPlayer(Position.SHOOTING_GUARD).getRating(
        RatingsType.USAGE)
        + (float) matches[1] / mismFactor;
    final double sfTen = offense.getPlayer(Position.SMALL_FORWARD).getRating(
        RatingsType.USAGE)
        + (float) matches[2] / mismFactor;
    final double pfTen = offense.getPlayer(Position.POWER_FORWARD).getRating(
        RatingsType.USAGE)
        + (float) matches[3] / mismFactor;
    final double cTen = offense.getPlayer(Position.CENTER).getRating(
        RatingsType.USAGE)
        + (float) matches[4] / mismFactor;

    final double totTen = pgTen + sgTen + sfTen + pfTen + cTen;
    final double whoPass = Math.random() * totTen;

    if (whoPass < pgTen) {
      return offense.getPlayer(Position.POINT_GUARD);
    } else if (whoPass < pgTen + sgTen) {
      return offense.getPlayer(Position.SHOOTING_GUARD);
    } else if (whoPass < pgTen + sgTen + sfTen) {
      return offense.getPlayer(Position.SMALL_FORWARD);
    } else if (whoPass < pgTen + sgTen + sfTen + pfTen) {
      return offense.getPlayer(Position.POWER_FORWARD);
    } else {
      return offense.getPlayer(Position.CENTER);
    }
  }

  public void playGame(Team home, Team away) {
    boolean poss_home = true;
    boolean poss_away = false;
    double gametime = 0;
    double max_gametime = 2880;
    int hscore = 0; // score of home team
    int ascore = 0; // score of away team
    boolean playing = true;

    final int homeTotalOutsideDefense = home
        .getTotalRating(RatingsType.OUTSIDE_DEFENSE);
    final int awayTotalOutsideDefense = away
        .getTotalRating(RatingsType.OUTSIDE_DEFENSE);
    final double hspeed = 6 - (homeTotalOutsideDefense - awayTotalOutsideDefense) / 8;
    final double aspeed = 6 - (awayTotalOutsideDefense - homeTotalOutsideDefense) / 8;
    // detect mismatches and add to total
    int[] matches_h = detectMismatch(home, away);
    for (int i = 0; i < 5; ++i) {
      home.playersInTeam[i].addMismatch(matches_h[i]);
    }
    int[] matches_a = detectMismatch(away, home);
    for (int i = 0; i < 5; ++i) {
      away.playersInTeam[i].addMismatch(matches_a[i]);
    }

    while (playing) {
      if (poss_home) {
        hscore += runPlay(home, away, matches_h);
        poss_away = true;
        poss_home = false;
        gametime += hspeed + 19 * Math.random();
        home.subPlayers(gametime);
        matches_h = detectMismatch(home, away);
      } else if (poss_away) {
        ascore += runPlay(away, home, matches_a);
        poss_away = false;
        poss_home = true;
        gametime += aspeed + 19 * Math.random();
        away.subPlayers(gametime);
        matches_a = detectMismatch(away, home);
      }
      // check if game has ended, or go to OT if needed
      if (gametime > max_gametime) {
        gametime = max_gametime;
        if (hscore != ascore) {
          playing = false;
        } else {
          poss_home = true;
          poss_away = false;
          max_gametime += 300;
        }
      }
    }

    // add each players stats to his career total
    for (int p = 0; p < 10; ++p) {
      home.playersInTeam[p].addGameStatsToTotal();
      away.playersInTeam[p].addGameStatsToTotal();
    }

    home.pointsFor += hscore;
    home.pointsAga += ascore;
    away.pointsFor += ascore;
    away.pointsAga += hscore;

    if (hscore > ascore) {
      home.wins++;
      away.losses++;
      home.games++;
      away.games++;
    } else {
      home.losses++;
      away.wins++;
      home.games++;
      away.games++;
    }

  }

  public void playSeason(List<Team> league) {
    // each team plays 4 games against each other team, and then is normalized
    // to 82 games
    int inner_itr = 0;
    int outer_itr = 0;
    while (outer_itr < league.size()) {
      inner_itr = outer_itr + 1;
      while (inner_itr < league.size()) {
        for (int g = 0; g < 20; g++) {
          playGame(league.get(inner_itr), league.get(outer_itr));
          playGame(league.get(outer_itr), league.get(inner_itr));
        }
        inner_itr++;
      }
      outer_itr++;
    }
  }

  public boolean potSteal(Player off, Player def) {
    if (Math.random() < 0.1) {
      int stl = def.getRating(RatingsType.STEALING) - 75;
      if (stl < 0) {
        stl = 0;
      }
      final double chance = Math.random() * Math.pow(stl, 0.75);
      if (chance > 5 || Math.random() < 0.1) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  public int runPlay(Team offense, Team defense, int[] matches) {

    final int offTotalOutsideDefense = offense
        .getTotalRating(RatingsType.OUTSIDE_DEFENSE);
    final int defTotalOutsideDefense = defense
        .getTotalRating(RatingsType.OUTSIDE_DEFENSE);
    final int fastbreak_possibility = offTotalOutsideDefense - defTotalOutsideDefense;

    int totPasses = 0;
    final boolean offPoss = true;
    Player whoPoss = getBallCarrier(offense);
    Player whoDef = defense.playersInTeam[whoPoss.getPosition().getValue() - 1];
    Player assister = whoPoss;

    while (offPoss) {
      if ((int) (6 * Math.random()) + totPasses < 5
          || (totPasses == 0 && Math.random() < 0.97)) {
        // pass the ball
        totPasses++;
        if (potSteal(whoPoss, whoDef)) {
          whoDef.incrementCurrentGameStat(StatsType.STEALS);
          return 0;
        }
        // get receiver of pass
        assister = whoPoss;
        whoPoss = intelligentPass(whoPoss, offense, defense, matches);
        whoDef = defense.playersInTeam[whoPoss.getPosition().getValue() - 1];
      } else if (fastbreak_possibility * Math.random() > 60) {
        // punish all-bigs lineup, they give up fast break points
        LOG.info("FAST BREAK");
        whoPoss.makeTwoPointShot(whoDef);
        if (assister == whoPoss) {
          return 2;
        } else {
          if (Math.pow(assister.getRating(RatingsType.PASSING) / 14, 2.4)
              * Math.random() > 20) {
            assister.incrementCurrentGameStat(StatsType.ASSISTS);
          }
          return 2;
        }
      } else {
        // whoPoss will shoot the ball
        final int points = takeShot(whoPoss, whoDef, defense, assister);
        if (points > 0) {
          // made the shot!
          if (assister == whoPoss) { // can't pass to yourself
            return points;
          } else {
            if (Math.pow(assister.getRating(RatingsType.PASSING) / 14, 2.4)
                * Math.random() > 20) {
              assister.incrementCurrentGameStat(StatsType.ASSISTS);
            }
            return points;
          }
        } else {
          // miss, scramble for rebound
          final int[] rebAdvArr = new int[3];
          for (int r = 0; r < 3; ++r) {
            // calculate each players rebounding advantage
            rebAdvArr[r] = offense.playersInTeam[r + 2]
                .getRating(RatingsType.REBOUNDING)
                - defense.playersInTeam[r + 2]
                    .getRating(RatingsType.REBOUNDING);
          }
          final double rebAdv = 0.175 * (rebAdvArr[(int) (Math.random() * 3)] + rebAdvArr[(int) (Math
              .random() * 3)]);
          if (Math.random() * 100 + rebAdv > 25) {
            // defensive rebound
            final Player rebounder = findRebounder(defense);
            rebounder.incrementCurrentGameStat(StatsType.REBOUNDS);
            return 0; // exit without scoring any points
          } else {
            // offensive rebound
            final Player rebounder = findRebounder(offense);
            rebounder.incrementCurrentGameStat(StatsType.REBOUNDS);
            whoPoss = rebounder;
            totPasses = 2;
            // goes back into loop to try another play
          }
        }
      }
    }
  }

  public int takeShot(Player shooter, Player defender, Team defense,
      Player assister) {
    int assBonus = 0;
    if (assister != shooter) {
      // shooter gets bonus for having a good passer
      assBonus = (int) ((float) (assister.getRating(RatingsType.PASSING) - 75) / 5);
    }

    final double selShot = Math.random();
    // get intelligent tendencies based on mismatches
    double intelOutT = transformRating(shooter.getRating(RatingsType.OUTSIDE_T));
    double intelMidT = transformRating(shooter.getRating(RatingsType.MID_T));
    final int mismMid = shooter.getRating(RatingsType.MID_SHOOTING)
        - (int) ((float) defender.getRating(RatingsType.OUTSIDE_DEFENSE) / 2 + (float) defender
            .getRating(RatingsType.INTERIOR_DEFENSE) / 2);
    if (Math.abs(mismMid) > 30) {
      intelMidT += (int) ((float) mismMid / 8);
    }
    final int mismOut = shooter.getRating(RatingsType.OUTSIDE_SHOOTING)
        - defender.getRating(RatingsType.OUTSIDE_DEFENSE);
    if (Math.abs(mismOut) > 30) {
      intelOutT += (int) ((float) mismOut / 8);
    }
    if (selShot < intelOutT && intelOutT >= 0
        && shooter.getRating(RatingsType.OUTSIDE_SHOOTING) > 50) {
      // 3 point shot
      final double chance = 22
          + (float) shooter.getRating(RatingsType.OUTSIDE_SHOOTING) / 3
          + assBonus - (float) defender.getRating(RatingsType.OUTSIDE_DEFENSE)
          / 6;
      if (chance > Math.random() * 100) {
        shooter.makeThreePointShot(defender);
        return 3;
      } else {
        shooter.missThreePointShot(defender);
        return 0;
      }
    } else if (selShot < intelMidT && intelMidT >= 0) {
      // mid range shot
      final int defMidD = (int) (defender
          .getRating(RatingsType.OUTSIDE_DEFENSE) * 0.5 + defender
          .getRating(RatingsType.INTERIOR_DEFENSE) * 0.5);
      final double chance = 30
          + (float) shooter.getRating(RatingsType.OUTSIDE_SHOOTING) / 3
          + assBonus - (float) defMidD / 7;
      if (chance > Math.random() * 100) {
        shooter.makeTwoPointShot(defender);
        return 2;
      } else {
        shooter.missTwoPointShot(defender);
        return 0;
      }
    } else {
      // inside shot, layup, dunk

      // check for block
      if (Math.random() < 0.35) {
        int blk = defender.getRating(RatingsType.BLOCKING) - 75;
        if (blk < 0) {
          blk = 0;
        }
        if (Math.random() * Math.pow(blk, 0.75) > 5 || Math.random() < 0.02) {
          defender.block(shooter);
          return 0;
        }
      }

      final double chance = 35
          + (float) shooter.getRating(RatingsType.INTERIOR_SHOOTING)
          / 3
          + assBonus
          - (float) defender.getRating(RatingsType.INTERIOR_DEFENSE)
          / 14
          - (float) defense.getPlayer(Position.POWER_FORWARD).getRating(
              RatingsType.INTERIOR_DEFENSE)
          / 25
          - (float) defense.getPlayer(Position.CENTER).getRating(
              RatingsType.INTERIOR_DEFENSE) / 25;
      if (chance > Math.random() * 100) {
        shooter.makeTwoPointShot(defender);
        return 2;
      } else {
        shooter.missTwoPointShot(defender);
        return 0;
      }
    }
  }

  private double transformRating(final int rating) {
    return (double) rating / 1000;
  }
}
