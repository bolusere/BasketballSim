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

  public Player genPlayer(Position position) {
    final String name = getRandName() + " " + position.toString();
    return new Player(name, position);
  }

  public List<Player> genRandPlayers(int number) {
    // generate number of players, with an equal number of each position
    final List<Player> players = new ArrayList<Player>();
    for (int i = 0; i < number / 5; ++i) {
      for (final Position position : Position.values()) {
        players.add(genPlayer(position));
      }
    }

    return players;
  }

  private String getRandName() {
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
