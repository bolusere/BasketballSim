/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import players.Player;
import players.PlayerGen;
import simulator.Simulator;
import simulator.Team;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Achi Jones
 */
public class BasketballSimUI extends javax.swing.JFrame {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  /**
   * @param args
   *          the command line arguments
   */
  public static void main(String args[]) {
    /* Set the Nimbus look and feel */
    // <editor-fold defaultstate="collapsed"
    // desc=" Look and feel setting code (optional) ">
    /*
     * If Nimbus (introduced in Java SE 6) is not available, stay with the
     * default look and feel. For details see
     * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
     */
    try {
      for (final javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
          .getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (final ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(BasketballSimUI.class.getName()).log(
          java.util.logging.Level.SEVERE, null, ex);
    } catch (final InstantiationException ex) {
      java.util.logging.Logger.getLogger(BasketballSimUI.class.getName()).log(
          java.util.logging.Level.SEVERE, null, ex);
    } catch (final IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(BasketballSimUI.class.getName()).log(
          java.util.logging.Level.SEVERE, null, ex);
    } catch (final javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(BasketballSimUI.class.getName()).log(
          java.util.logging.Level.SEVERE, null, ex);
    }
    // </editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        new BasketballSimUI().setVisible(true);
      }
    });
  }

  public ArrayList<Player> PlayerList;
  public ArrayList<Player> MyTeamPlayerList;

  public ArrayList<Team> League;

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton DraftPlayerButton;

  private javax.swing.JTable DraftTable;

  private javax.swing.JList DraftedList;

  private javax.swing.JButton StartDraftButton;

  private javax.swing.JFrame jFrameSeason;

  private javax.swing.JLabel jLabel1;

  private javax.swing.JLabel jLabelSeasonResults;

  private javax.swing.JLabel jLabelTeamName;

  private javax.swing.JList jListTeams;

  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JScrollPane jScrollPane3;
  private javax.swing.JScrollPane jScrollPane5;
  private javax.swing.JScrollPane jScrollPane6;
  private javax.swing.JTable jTableTeamPlayerStats;
  private javax.swing.JTable jTableTeamRatings;

  // End of variables declaration//GEN-END:variables
  /**
   * Creates new form BasketballSimUI
   */
  public BasketballSimUI() {
    PlayerList = new ArrayList<Player>();
    MyTeamPlayerList = new ArrayList<Player>();
    League = new ArrayList<Team>();
    initComponents();
    DraftedList.setModel(new DefaultListModel());
    jListTeams.setModel(new DefaultListModel());
  }

  private void draftPlayer() {
    // draft selected player into your team
    if (DraftTable.getSelectedRow() != -1) {
      final int row = DraftTable.convertRowIndexToModel(DraftTable
          .getSelectedRow());
      final String name = (String) DraftTable.getModel().getValueAt(row, 0);
      boolean found_player = false;
      int search = 0;
      while (!found_player) {
        if (PlayerList.get(search).name.equals(name)) {
          found_player = true;
        } else {
          search++;
        }
      }
      final Player drafted_player = PlayerList.get(search);
      MyTeamPlayerList.add(drafted_player);
      final DefaultListModel model = (DefaultListModel) DraftedList.getModel();
      model.addElement(drafted_player.name + " OVRL: "
          + drafted_player.ratingsArray[1]);
      PlayerList.remove(drafted_player);
      final DefaultTableModel table_model = (DefaultTableModel) DraftTable
          .getModel();
      table_model.removeRow(row);

      // sort playerList before AI drafts
      Collections.sort(PlayerList, new Comparator<Player>() {
        @Override
        public int compare(Player p1, Player p2) {
          return p1.ratingsArray[1] > p2.ratingsArray[1] ? -1
              : p1.ratingsArray[1] < p2.ratingsArray[1] ? 1 : 0;
        }
      });

      // make ai draft
      for (int t = 0; t < League.size(); ++t) {
        League.get(t).selectPlayer(PlayerList);
      }

      // make table model remove all the drafted players
      final DefaultTableModel tabModel = (DefaultTableModel) DraftTable
          .getModel();
      tabModel.setRowCount(0);

      for (int p = 0; p < PlayerList.size(); ++p) {
        final Player tmpPlayer = PlayerList.get(p);
        final Vector playerVec = new Vector(14);
        playerVec.addElement(tmpPlayer.name);
        playerVec.addElement(tmpPlayer.attributes);
        for (int i = 0; i < 13; ++i) {
          playerVec.addElement(tmpPlayer.ratingsArray[i]);
        }

        tabModel.addRow(playerVec);
      }

      if (MyTeamPlayerList.size() == 10) {
        final Team playerTeam = new Team("PLAYER TEAM");
        // drafted all they need
        Object[] options = { "PG", "SG", "SF", "PF", "C", "Backup PG",
            "Backup SG", "Backup SF", "Backup PF", "Backup C" };
        for (int i = 0; i < 10; ++i) {
          final Object selectedValue = JOptionPane.showInputDialog(null,
              "Where should " + MyTeamPlayerList.get(i).name + " be placed?",
              "Input", JOptionPane.INFORMATION_MESSAGE, null, options,
              options[0]);
          final ArrayList<Object> tmpremove = new ArrayList<Object>(
              Arrays.asList(options));
          if (selectedValue == "PG") {
            MyTeamPlayerList.get(i).ratingsArray[0] = 1;
            tmpremove.remove("PG");
          } else if (selectedValue == "SG") {
            MyTeamPlayerList.get(i).ratingsArray[0] = 2;
            tmpremove.remove("SG");
          } else if (selectedValue == "SF") {
            MyTeamPlayerList.get(i).ratingsArray[0] = 3;
            tmpremove.remove("SF");
          } else if (selectedValue == "PF") {
            MyTeamPlayerList.get(i).ratingsArray[0] = 4;
            tmpremove.remove("PF");
          } else if (selectedValue == "C") {
            MyTeamPlayerList.get(i).ratingsArray[0] = 5;
            tmpremove.remove("C");
          } else if (selectedValue == "Backup PG") {
            MyTeamPlayerList.get(i).ratingsArray[0] = 1;
            tmpremove.remove("Backup PG");
          } else if (selectedValue == "Backup SG") {
            MyTeamPlayerList.get(i).ratingsArray[0] = 2;
            tmpremove.remove("Backup SG");
          } else if (selectedValue == "Backup SF") {
            MyTeamPlayerList.get(i).ratingsArray[0] = 3;
            tmpremove.remove("Backup SF");
          } else if (selectedValue == "Backup PF") {
            MyTeamPlayerList.get(i).ratingsArray[0] = 4;
            tmpremove.remove("Backup PF");
          } else if (selectedValue == "Backup C") {
            MyTeamPlayerList.get(i).ratingsArray[0] = 5;
            tmpremove.remove("Backup C");
          }
          playerTeam.addPlayer(MyTeamPlayerList.get(i));
          options = tmpremove.toArray();
        }

        // add player team to league and simulate season
        League.add(playerTeam);
        final Simulator simmy = new Simulator();
        simmy.playSeason(League);
        System.out.println("Done simming season, you got " + playerTeam.wins
            + " wins.");
        jFrameSeason.setBounds(0, 0, 1150, 500);
        jFrameSeason.setVisible(true);
        reviewSeason();
      }

    }
  }

  private void DraftPlayerButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_DraftPlayerButtonActionPerformed
    draftPlayer();
  }// GEN-LAST:event_DraftPlayerButtonActionPerformed

  /**
   * 
   * @param evt
   */
  private void DraftTableMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_DraftTableMouseClicked
    // TODO add your handling code here:
    if (evt.getClickCount() == 2) {
      draftPlayer();
    }
  }// GEN-LAST:event_DraftTableMouseClicked

  private void getPlayerList() { // reads from file and adds every player to
                                 // global PlayerList
    final PlayerGen genny = new PlayerGen();
    PlayerList = genny.getPlayersFromFile();
  }

  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed"
  // desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jFrameSeason = new javax.swing.JFrame();
    jScrollPane2 = new javax.swing.JScrollPane();
    jListTeams = new javax.swing.JList();
    jLabelSeasonResults = new javax.swing.JLabel();
    jScrollPane3 = new javax.swing.JScrollPane();
    jTableTeamPlayerStats = new javax.swing.JTable();
    jLabelTeamName = new javax.swing.JLabel();
    jScrollPane6 = new javax.swing.JScrollPane();
    jTableTeamRatings = new javax.swing.JTable();
    jScrollPane5 = new javax.swing.JScrollPane();
    DraftTable = new javax.swing.JTable();
    StartDraftButton = new javax.swing.JButton();
    DraftPlayerButton = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();
    jScrollPane1 = new javax.swing.JScrollPane();
    DraftedList = new javax.swing.JList();

    jFrameSeason.setMinimumSize(new java.awt.Dimension(1100, 500));

    jListTeams.setModel(new javax.swing.AbstractListModel() {
      String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

      @Override
      public Object getElementAt(int i) {
        return strings[i];
      }

      @Override
      public int getSize() {
        return strings.length;
      }
    });
    jListTeams
        .setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    jListTeams.setToolTipText("");
    jListTeams.setMaximumSize(new java.awt.Dimension(60, 100));
    jListTeams.setMinimumSize(new java.awt.Dimension(60, 100));
    jListTeams.setPreferredSize(new java.awt.Dimension(60, 100));
    jListTeams
        .addListSelectionListener(new javax.swing.event.ListSelectionListener() {
          @Override
          public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
            jListTeamsValueChanged(evt);
          }
    });
    jScrollPane2.setViewportView(jListTeams);

    jLabelSeasonResults.setText("Results of Season:");

    jTableTeamPlayerStats.setModel(new javax.swing.table.DefaultTableModel(
        new Object[][] {
            { null, null, null, null, null, null, null, null, null, null, null,
                null, null },
            { null, null, null, null, null, null, null, null, null, null, null,
                null, null },
            { null, null, null, null, null, null, null, null, null, null, null,
                null, null },
            { null, null, null, null, null, null, null, null, null, null, null,
                null, null } }, new String[] { "Name", "POS", "PPG", "FG%",
            "3G%", "REB", "ASS", "STL", "BLK", "FGA", "3PA", "OFG%", "MSM" }) {
      Class[] types = new Class[] { java.lang.String.class,
          java.lang.String.class, java.lang.String.class,
          java.lang.String.class, java.lang.String.class,
          java.lang.String.class, java.lang.String.class,
          java.lang.String.class, java.lang.String.class,
          java.lang.String.class, java.lang.String.class,
          java.lang.String.class, java.lang.String.class };
      boolean[] canEdit = new boolean[] { false, false, false, false, false,
          false, false, false, false, false, false, false, false };

      @Override
      public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
      }

      @Override
      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
      }
    });
    jTableTeamPlayerStats.setColumnSelectionAllowed(true);
    jTableTeamPlayerStats.getTableHeader().setReorderingAllowed(false);
    jScrollPane3.setViewportView(jTableTeamPlayerStats);
    jTableTeamPlayerStats
        .getColumnModel()
        .getSelectionModel()
        .setSelectionMode(
            javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    if (jTableTeamPlayerStats.getColumnModel().getColumnCount() > 0) {
      jTableTeamPlayerStats.getColumnModel().getColumn(0).setResizable(false);
      jTableTeamPlayerStats.getColumnModel().getColumn(0)
          .setPreferredWidth(300);
      jTableTeamPlayerStats.getColumnModel().getColumn(1).setResizable(false);
      jTableTeamPlayerStats.getColumnModel().getColumn(2).setResizable(false);
      jTableTeamPlayerStats.getColumnModel().getColumn(3).setResizable(false);
      jTableTeamPlayerStats.getColumnModel().getColumn(3)
          .setPreferredWidth(100);
      jTableTeamPlayerStats.getColumnModel().getColumn(4).setResizable(false);
      jTableTeamPlayerStats.getColumnModel().getColumn(4)
          .setPreferredWidth(100);
      jTableTeamPlayerStats.getColumnModel().getColumn(5).setResizable(false);
      jTableTeamPlayerStats.getColumnModel().getColumn(6).setResizable(false);
      jTableTeamPlayerStats.getColumnModel().getColumn(7).setResizable(false);
      jTableTeamPlayerStats.getColumnModel().getColumn(8).setResizable(false);
      jTableTeamPlayerStats.getColumnModel().getColumn(9).setResizable(false);
      jTableTeamPlayerStats.getColumnModel().getColumn(10).setResizable(false);
      jTableTeamPlayerStats.getColumnModel().getColumn(11).setResizable(false);
      jTableTeamPlayerStats.getColumnModel().getColumn(11)
          .setPreferredWidth(100);
      jTableTeamPlayerStats.getColumnModel().getColumn(12).setResizable(false);
    }

    jLabelTeamName.setText("TEAM NAME: ( XX - XX )");

    jTableTeamRatings.setModel(new javax.swing.table.DefaultTableModel(
        new Object[][] {

        }, new String[] { "Name", "POSN", "OVRL", "INTS", "MIDS", "OUTS",
            "PASS", "HAND", "STL", "BLCK", "INTD", "OUTD", "REBD", "USAG" }) {
      Class[] types = new Class[] { java.lang.String.class,
          java.lang.Integer.class, java.lang.Integer.class,
          java.lang.Integer.class, java.lang.Integer.class,
          java.lang.Integer.class, java.lang.Integer.class,
          java.lang.Integer.class, java.lang.Integer.class,
          java.lang.Integer.class, java.lang.Integer.class,
          java.lang.Integer.class, java.lang.Integer.class,
          java.lang.Integer.class };
      boolean[] canEdit = new boolean[] { false, false, false, false, false,
          false, false, false, false, false, false, false, false, false };

      @Override
      public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
      }

      @Override
      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
      }
    });
    jTableTeamRatings.getTableHeader().setReorderingAllowed(false);
    jScrollPane6.setViewportView(jTableTeamRatings);
    jTableTeamRatings
        .getColumnModel()
        .getSelectionModel()
        .setSelectionMode(
            javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    if (jTableTeamRatings.getColumnModel().getColumnCount() > 0) {
      jTableTeamRatings.getColumnModel().getColumn(0).setResizable(false);
      jTableTeamRatings.getColumnModel().getColumn(0).setPreferredWidth(300);
      jTableTeamRatings.getColumnModel().getColumn(1).setResizable(false);
      jTableTeamRatings.getColumnModel().getColumn(2).setResizable(false);
      jTableTeamRatings.getColumnModel().getColumn(3).setResizable(false);
      jTableTeamRatings.getColumnModel().getColumn(4).setResizable(false);
      jTableTeamRatings.getColumnModel().getColumn(5).setResizable(false);
      jTableTeamRatings.getColumnModel().getColumn(6).setResizable(false);
      jTableTeamRatings.getColumnModel().getColumn(7).setResizable(false);
      jTableTeamRatings.getColumnModel().getColumn(8).setResizable(false);
      jTableTeamRatings.getColumnModel().getColumn(9).setResizable(false);
      jTableTeamRatings.getColumnModel().getColumn(10).setResizable(false);
      jTableTeamRatings.getColumnModel().getColumn(11).setResizable(false);
      jTableTeamRatings.getColumnModel().getColumn(12).setResizable(false);
      jTableTeamRatings.getColumnModel().getColumn(13).setResizable(false);
    }

    final javax.swing.GroupLayout jFrameSeasonLayout = new javax.swing.GroupLayout(
        jFrameSeason.getContentPane());
    jFrameSeason.getContentPane().setLayout(jFrameSeasonLayout);
    jFrameSeasonLayout
        .setHorizontalGroup(jFrameSeasonLayout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(
                jFrameSeasonLayout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addGroup(
                        jFrameSeasonLayout
                            .createParallelGroup(
                                javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(
                                jFrameSeasonLayout
                                    .createSequentialGroup()
                                    .addComponent(jScrollPane2,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        164,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(
                                        jFrameSeasonLayout
                                            .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(
                                                jScrollPane3,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                969, Short.MAX_VALUE)
                                            .addGroup(
                                                jFrameSeasonLayout
                                                    .createSequentialGroup()
                                                    .addComponent(
                                                        jLabelTeamName)
                                                    .addGap(0, 0,
                                                        Short.MAX_VALUE))
                                            .addComponent(jScrollPane6)))
                            .addComponent(jLabelSeasonResults))
                    .addContainerGap()));
    jFrameSeasonLayout
        .setVerticalGroup(jFrameSeasonLayout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(
                jFrameSeasonLayout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabelSeasonResults)
                    .addGap(5, 5, 5)
                    .addGroup(
                        jFrameSeasonLayout
                            .createParallelGroup(
                                javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(
                                jFrameSeasonLayout
                                    .createSequentialGroup()
                                    .addComponent(jLabelTeamName)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jScrollPane3,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        250,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jScrollPane6,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        268,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 8, Short.MAX_VALUE)))
                    .addContainerGap()));

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    DraftTable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {

    }, new String[] { "Name", "Skills", "POSN", "OVRL", "INTS", "MIDS", "OUTS",
        "PASS", "HAND", "STL", "BLCK", "INTD", "OUTD", "REBD", "USAG" }) {
      Class[] types = new Class[] { java.lang.String.class,
          java.lang.String.class, java.lang.Integer.class,
          java.lang.Integer.class, java.lang.Integer.class,
          java.lang.Integer.class, java.lang.Integer.class,
          java.lang.Integer.class, java.lang.Integer.class,
          java.lang.Integer.class, java.lang.Integer.class,
          java.lang.Integer.class, java.lang.Integer.class,
          java.lang.Integer.class, java.lang.Integer.class };
      boolean[] canEdit = new boolean[] { false, false, false, false, false,
          false, false, false, false, false, false, false, false, false, false };

      @Override
      public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
      }

      @Override
      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
      }
    });
    DraftTable.setColumnSelectionAllowed(true);
    DraftTable.getTableHeader().setReorderingAllowed(false);
    DraftTable.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        DraftTableMouseClicked(evt);
      }
    });
    jScrollPane5.setViewportView(DraftTable);
    DraftTable
        .getColumnModel()
        .getSelectionModel()
        .setSelectionMode(
            javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    if (DraftTable.getColumnModel().getColumnCount() > 0) {
      DraftTable.getColumnModel().getColumn(0).setResizable(false);
      DraftTable.getColumnModel().getColumn(0).setPreferredWidth(300);
      DraftTable.getColumnModel().getColumn(1).setPreferredWidth(175);
      DraftTable.getColumnModel().getColumn(1).setHeaderValue("Skills");
      DraftTable.getColumnModel().getColumn(2).setResizable(false);
      DraftTable.getColumnModel().getColumn(3).setResizable(false);
      DraftTable.getColumnModel().getColumn(4).setResizable(false);
      DraftTable.getColumnModel().getColumn(5).setResizable(false);
      DraftTable.getColumnModel().getColumn(6).setResizable(false);
      DraftTable.getColumnModel().getColumn(7).setResizable(false);
      DraftTable.getColumnModel().getColumn(8).setResizable(false);
      DraftTable.getColumnModel().getColumn(9).setResizable(false);
      DraftTable.getColumnModel().getColumn(10).setResizable(false);
      DraftTable.getColumnModel().getColumn(11).setResizable(false);
      DraftTable.getColumnModel().getColumn(12).setResizable(false);
      DraftTable.getColumnModel().getColumn(13).setResizable(false);
      DraftTable.getColumnModel().getColumn(14).setResizable(false);
    }

    StartDraftButton.setText("Start");
    StartDraftButton.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        StartDraftButtonActionPerformed(evt);
      }
    });

    DraftPlayerButton.setText("Draft Player");
    DraftPlayerButton.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        DraftPlayerButtonActionPerformed(evt);
      }
    });

    jLabel1.setText("Current Team:");

    DraftedList.setModel(new javax.swing.AbstractListModel() {
      String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

      @Override
      public Object getElementAt(int i) {
        return strings[i];
      }

      @Override
      public int getSize() {
        return strings.length;
      }
    });
    jScrollPane1.setViewportView(DraftedList);

    final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
        getContentPane());
    getContentPane().setLayout(layout);
    layout
        .setHorizontalGroup(layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addGroup(
                        layout
                            .createParallelGroup(
                                javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addComponent(StartDraftButton,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        98,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addComponent(jScrollPane5,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        979,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(
                                        layout
                                            .createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(
                                                DraftPlayerButton,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                199, Short.MAX_VALUE)
                                            .addGroup(
                                                layout
                                                    .createSequentialGroup()
                                                    .addComponent(jLabel1)
                                                    .addGap(0, 0,
                                                        Short.MAX_VALUE))
                                            .addComponent(jScrollPane1))))
                    .addContainerGap()));
    layout
        .setVerticalGroup(layout
            .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(
                layout
                    .createSequentialGroup()
                    .addContainerGap()
                    .addComponent(StartDraftButton,
                        javax.swing.GroupLayout.PREFERRED_SIZE, 29,
                        javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(
                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(
                        layout
                            .createParallelGroup(
                                javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(
                                layout
                                    .createSequentialGroup()
                                    .addComponent(DraftPlayerButton,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        37,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel1)
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jScrollPane1,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        259,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane5,
                                javax.swing.GroupLayout.PREFERRED_SIZE, 470,
                                javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(104, Short.MAX_VALUE)));

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jListTeamsValueChanged(javax.swing.event.ListSelectionEvent evt) {// GEN-FIRST:event_jListTeamsValueChanged
    // look at different team
    final int tnum = jListTeams.getSelectedIndex();
    if (tnum != -1) {
      // add stats to table
      final DefaultTableModel tabModel = (DefaultTableModel) jTableTeamPlayerStats
          .getModel();
      tabModel.setRowCount(0);
      for (int r = 0; r < 10; ++r) {
        final Player p = League.get(tnum).playersArray[r];
        final Vector playerVec = new Vector(13);
        playerVec.addElement(p.name);
        playerVec.addElement(p.getPosition());
        playerVec.addElement(p.getPPG());
        playerVec.addElement(p.getFGP() + "%");
        playerVec.addElement(p.get3GP() + "%");
        playerVec.addElement(p.getRPG());
        playerVec.addElement(p.getAPG());
        playerVec.addElement(p.getSPG());
        playerVec.addElement(p.getBPG());
        playerVec.addElement(p.getFGAPG());
        playerVec.addElement(p.get3GAPG());
        playerVec.addElement(p.getOFP() + "%");
        playerVec.addElement(p.getMSM());

        tabModel.addRow(playerVec);
        if (r == 4) {
          final Vector bVec = new Vector(13);
          bVec.addElement("BENCH:");
          tabModel.addRow(bVec);
        }
      }
      final Team t = League.get(tnum);
      final Vector teamVec = new Vector(13);
      teamVec.addElement("TOTALS:");
      teamVec.addElement(" ");
      teamVec.addElement(t.getPPG());
      teamVec.addElement(t.getFGP() + "%");
      teamVec.addElement(t.get3GP() + "%");
      teamVec.addElement(t.getRPG());
      teamVec.addElement(t.getAPG());
      teamVec.addElement(t.getSPG());
      teamVec.addElement(t.getBPG());
      teamVec.addElement(t.getFGAPG());
      teamVec.addElement(t.get3GAPG());
      teamVec.addElement(t.getOFP() + "%");
      tabModel.addRow(teamVec);

      // add ratings to table
      final DefaultTableModel ratingsModel = (DefaultTableModel) jTableTeamRatings
          .getModel();
      ratingsModel.setRowCount(0);
      for (int p = 0; p < 10; ++p) {
        final Vector playerVec = new Vector(13);
        playerVec.addElement(t.playersArray[p].name);
        for (int i = 0; i < 13; ++i) {
          playerVec.addElement(t.playersArray[p].ratingsArray[i]);
        }
        ratingsModel.addRow(playerVec);
      }

      // set label to right team name
      jLabelTeamName.setFont(new Font("Arial", Font.PLAIN, 36));
      jLabelTeamName.setText(t.name + ": " + t.getWins82() + "-"
          + t.getLosses82() + " (" + t.getPDStr() + ")");
    }

  }// GEN-LAST:event_jListTeamsValueChanged

  private void reviewSeason() {
    // sort league by wins before inserting into list
    Collections.sort(League, new Comparator<Team>() {
      @Override
      public int compare(Team t1, Team t2) {
        return t1.wins > t2.wins ? -1 : t1.wins < t2.wins ? 1 : 0;
      }
    });

    final DefaultListModel teamListmodel = (DefaultListModel) jListTeams
        .getModel();
    for (int i = 0; i < League.size(); i++) {
      teamListmodel.addElement(League.get(i).name + ": "
          + League.get(i).getWins82() + " - " + League.get(i).getLosses82());
    }
  }

  private void StartDraftButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_StartDraftButtonActionPerformed
    // add 15 ai opponent teams
    /*
     * getPlayerList(); String[] teamNames =
     * {"ATL","BOS","BKN","CHA","CHI","CLE","DAL","DEN","DET","GSW",
     * "HOU","IND","LAC","LAL","MEM","MIA","MIL","MIN","NOL","NYK",
     * "OKC","ORL","PHI","PHX","POR","SAC","SAN","TOR","UTA","WSH"};
     * 
     * for (int i = 0; i < 30; ++i) { Player[] startingPlayers = new Player[5];
     * for (int p = 0; p < 5; ++p) { startingPlayers[p] = PlayerList.get(i*5 +
     * p); } Team t = new Team( teamNames[i], startingPlayers ); League.add(t);
     * } Simulator simmy = new Simulator(); simmy.playSeason(League);
     * jFrameSeason.setBounds(0, 0, 800, 400); jFrameSeason.setVisible(true);
     * reviewSeason();
     */
    League.add(new Team("Kold Killas"));
    League.add(new Team("Super Troopers"));
    League.add(new Team("Cool Cats"));
    League.add(new Team("Diamond Stones"));
    League.add(new Team("Army Armadillos"));
    League.add(new Team("Ferguson Rioters"));
    League.add(new Team("Silicon iPlayers"));
    League.add(new Team("Swaggy Peas"));
    League.add(new Team("Black Whites"));
    League.add(new Team("Ballin Ballers"));
    League.add(new Team("Paint Buckets"));
    League.add(new Team("NYC Bankers"));
    League.add(new Team("Senile Senators"));
    League.add(new Team("Free Throwers"));
    League.add(new Team("The Prancers"));

    // Starts the Draft, reads file and makes players
    final PlayerGen genny = new PlayerGen();
    PlayerList = genny.genRandPlayers(250);
    final DefaultTableModel model = (DefaultTableModel) DraftTable.getModel();
    for (int p = 0; p < PlayerList.size(); ++p) {
      final Vector playerVec = new Vector(14);
      playerVec.addElement(PlayerList.get(p).name);
      playerVec.addElement(PlayerList.get(p).attributes);
      for (int i = 0; i < 13; ++i) {
        playerVec.addElement(PlayerList.get(p).ratingsArray[i]);
      }
      model.addRow(playerVec);
    }

    DraftTable.setAutoCreateRowSorter(true);

    StartDraftButton.setEnabled(false);

  }// GEN-LAST:event_StartDraftButtonActionPerformed
}
