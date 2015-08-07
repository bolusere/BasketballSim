/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Achi Jones
 */
public class PlayerGen {

  final private static String PATH_TO_RESOURCES = "src/main/resources/";
  final private static String PLAYER_NAMES_FILE = PATH_TO_RESOURCES
      + "player_names.txt";

  List<String> listNames;

  public PlayerGen() {
    // get list of names from file
    listNames = new ArrayList<String>();
    try {
      final List<String> nameList = Files.readAllLines(Paths
          .get(PLAYER_NAMES_FILE));
      for (final String name : nameList) {
        listNames.add(name);
      }
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  public Player genPlayer(int position) {
    final int def_rat = 80;
    int height = 78;
    int weight = 180;
    int speed = def_rat;
    final int age = 25;
    int int_s = def_rat;
    int mid_s = def_rat;
    int out_s = def_rat;
    int passing = def_rat;
    int handling = def_rat;
    int steal = def_rat;
    int block = def_rat;
    int int_d = def_rat;
    int out_d = def_rat;
    int rebounding = def_rat;
    int extra_usage = 0;
    String name = getRandName();
    String att = "";

    if (position == 1) {
      // Point Guard
      height -= 3 * Math.random() + 3;
      weight -= 30 * Math.random();
      speed += 5 * Math.random() + 5;
      int_s -= 8 * Math.random() + 8;
      mid_s += 10 * Math.random() - 5;
      out_s += 10 * Math.random() - 5;
      passing += 10 * Math.random() + 5;
      handling += 10 * Math.random();
      steal += 10 * Math.random() - 2;
      block -= 20 * Math.random() + 20;
      int_d -= 8 * Math.random() + 8;
      out_d += 10 * Math.random() - 5;
      rebounding -= 20 * Math.random() + 10;
      name += " PG";
    } else if (position == 2) {
      // Shooting Guard
      height += 4 * Math.random() - 3;
      weight += 30 * Math.random() - 15;
      speed += 6 * Math.random();
      int_s += 16 * Math.random() - 8;
      mid_s += 13 * Math.random() - 5;
      out_s += 13 * Math.random() - 5;
      passing += 10 * Math.random();
      handling += 10 * Math.random() - 2;
      steal += 10 * Math.random() - 5;
      block -= 20 * Math.random() + 10;
      int_d -= 5 * Math.random() + 5;
      out_d += 10 * Math.random() - 5;
      rebounding -= 10 * Math.random() + 5;
      name += " SG";
    } else if (position == 3) {
      // Small Forward
      height += 6 * Math.random() - 2;
      weight += 40 * Math.random() - 10;
      speed += 16 * Math.random() - 8;
      int_s += 20 * Math.random() - 8;
      mid_s += 20 * Math.random() - 10;
      out_s += 20 * Math.random() - 10;
      passing += 20 * Math.random() - 10;
      handling += 20 * Math.random() - 10;
      steal += 20 * Math.random() - 10;
      block += 15 * Math.random() - 5;
      int_d += 15 * Math.random() - 5;
      out_d += 15 * Math.random() - 5;
      rebounding += 15 * Math.random() - 5;
      name += " SF";
    } else if (position == 4) {
      // Power Forward
      height += 6 * Math.random() + 1;
      weight += 40 * Math.random() + 20;
      speed += 15 * Math.random() - 15;
      int_s += 20 * Math.random() - 5;
      mid_s += 16 * Math.random() - 8;
      out_s += 12 * Math.random() - 6;
      passing += 20 * Math.random() - 15;
      handling += 20 * Math.random() - 20;
      steal += 20 * Math.random() - 15;
      block += 20 * Math.random() - 5;
      int_d += 20 * Math.random() - 5;
      out_d += 10 * Math.random() - 8;
      rebounding += 20 * Math.random() - 5;
      name += " PF";
    } else if (position == 5) {
      // Center
      height += 8 * Math.random() + 2;
      weight += 40 * Math.random() + 40;
      speed += 20 * Math.random() - 30;
      int_s += 10 * Math.random() + 5;
      mid_s += 20 * Math.random() - 15;
      out_s += 30 * Math.random() - 45;
      passing += 20 * Math.random() - 20;
      handling += 30 * Math.random() - 40;
      steal += 30 * Math.random() - 40;
      block += 10 * Math.random() + 5;
      int_d += 10 * Math.random() + 5;
      out_d += 20 * Math.random() - 20;
      rebounding += 12 * Math.random() + 5;
      name += " C";
    }

    // Assign attributes to boost or lower stats
    int choice;
    for (int i = 0; i < 5; i++) {
      choice = (int) (Math.random() * 20);
      if (choice == 0) {
        // Passer
        passing += 15 * Math.random() + 5;
        att += "Ps ";
      } else if (choice == 1) {
        // Off Weapon
        out_s += 5 * Math.random() + 5;
        mid_s += 6 * Math.random() + 6;
        int_s += 7 * Math.random() + 7;
        att += "OW ";
      } else if (choice == 2) {
        // Blocker
        block += 10 * Math.random() + 5;
        att += "Bl ";
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
        steal += 5 * Math.random() + 5;
        out_d += 5 * Math.random() + 5;
        att += "Ob ";
      } else if (choice == 6) {
        // Rebounding
        rebounding += 10 * Math.random() + 5;
        height += 1;
        att += "Rb ";
      } else if (choice == 7) {
        // Fumbler
        handling -= 5 * Math.random() + 5;
        passing -= 5 * Math.random() + 5;
        att += "Fm ";
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
        out_s -= 10 * Math.random() + 15;
        if (out_s < 0) {
          out_s = 0;
        }
        att += "n3 ";
      } else if (choice == 11) {
        // Dunker
        int_s += 10 * Math.random() + 10;
        att += "Dn ";
      } else if (choice == 12) {
        // Defensive Liability
        steal -= 5 * Math.random() - 5;
        block -= 5 * Math.random() - 5;
        out_d -= 5 * Math.random() - 5;
        int_d -= 5 * Math.random() - 5;
        att += "Dl ";
      } else if (choice == 13) {
        // Offensive Liability
        int_s -= 5 * Math.random() - 5;
        mid_s -= 5 * Math.random() - 5;
        out_s -= 5 * Math.random() - 5;
        att += "Ol ";
      } else if (choice == 14) {
        // Mid Range Jesus
        mid_s += 12 * Math.random() + 5;
        att += "Md ";
      } else if (choice == 15) {
        // The Wall
        int_d += 10 * Math.random() + 5;
        block += 5 * Math.random() + 5;
        att += "Wa ";
      } else if (choice == 16) {
        // 3pt Specialist
        out_s += 5 * Math.random() + 8;
        passing -= 5 * Math.random() + 5;
        int_s -= 5 * Math.random() + 5;
        mid_s -= 5 * Math.random() + 5;
        att += "3p ";
      } else if (choice == 17) {
        // Chucker
        if (extra_usage == 0) {
          extra_usage += 5000;
          att += "Ch ";
        }
      } else if (choice == 18 && Math.random() < 0.1) {
        // Whole Package (rare)
        extra_usage += 3;
        int_s += 5 * Math.random() + 5;
        mid_s += 5 * Math.random() + 5;
        out_s += 5 * Math.random() + 5;
        passing += 5 * Math.random() + 5;
        handling += 5 * Math.random() + 5;
        steal += 5 * Math.random() + 5;
        block += 5 * Math.random() + 5;
        int_d += 5 * Math.random() + 5;
        out_d += 5 * Math.random() + 5;
        att += "XX ";
      } else if (choice == 19) {
        // The Thief
        steal += 10 * Math.random() + 5;
        att += "St ";
      }
    }

    final int usage = (int) (Math.round(Math.pow(int_s, 2) + Math.pow(mid_s, 2)
        + Math.pow(out_s, 2)) + extra_usage) / 1000;
    final int ins_t = (int) (1000 * Math.pow(int_s, 1.2) / (Math
        .pow(int_s, 1.2) + Math.pow(mid_s, 1.2) + Math.pow(out_s, 1.2)));
    final int mid_t = (int) (1000 * Math.pow(mid_s, 1.2) / (Math
        .pow(int_s, 1.2) + Math.pow(mid_s, 1.2) + Math.pow(out_s, 1.2)));
    final int out_t = (int) (1000 * Math.pow(out_s, 1.2) / (Math
        .pow(int_s, 1.2) + Math.pow(mid_s, 1.2) + Math.pow(out_s, 1.2)));

    final int[] ratings = new int[16];
    ratings[0] = position;
    ratings[2] = int_s;
    ratings[3] = mid_s;
    ratings[4] = out_s;
    ratings[5] = passing;
    ratings[6] = handling;
    ratings[7] = steal;
    ratings[8] = block;
    ratings[9] = int_d;
    ratings[10] = out_d;
    ratings[11] = rebounding;
    ratings[12] = usage;
    ratings[13] = ins_t;
    ratings[14] = mid_t;
    ratings[15] = out_t;

    ratings[1] = (int) Math.round(Math.pow(ratings[2], 1.3)
        + Math.pow(ratings[3], 1.3) + Math.pow(ratings[4], 1.3)
        + Math.pow(ratings[5], 1.1) + ratings[6] + Math.pow(ratings[7], 1.1)
        + Math.pow(ratings[8], 1.1) + Math.pow(ratings[9], 1.2)
        + Math.pow(ratings[10], 1.2) + Math.pow(ratings[11], 1.2));
    ratings[1] = 100 * ratings[1] / 2500;

    final Player p = new Player(name, ratings, att);

    return p;
  }

  public List<Player> genRandPlayers(int number) {
    // generate number of players, with an equal number of each position
    final List<Player> players = new ArrayList<Player>();
    for (int i = 0; i < number / 5; ++i) {
      final Player genPG = genPlayer(1);
      final Player genSG = genPlayer(2);
      final Player genSF = genPlayer(3);
      final Player genPF = genPlayer(4);
      final Player genC = genPlayer(5);
      players.add(genPG);
      players.add(genSG);
      players.add(genSF);
      players.add(genPF);
      players.add(genC);
    }

    return players;
  }

  public String getRandName() {
    // get random name from list and remove it so it won't be used again
    final int firstName = (int) (Math.random() * listNames.size());
    final String fName = listNames.get(firstName);
    listNames.remove(firstName);
    final int lastName = (int) (Math.random() * listNames.size());
    final String lName = listNames.get(lastName);
    listNames.remove(lastName);
    return (fName + " " + lName);
  }

}
