// APPENDIX D

import java.io.*;
import java.io.InputStream;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.*;
import java.util.Arrays;
import java.util.Collections;

public class minimax {
  
  // return LinkedList of all possible next moves
  public static LinkedList<Move> getAllMoves(Table table, Player[] players, int currentPlayerInt){
    
    LinkedList<String> remaining_players = new LinkedList<String>();
    int score = 0;
    LinkedList<String> played_sub = new LinkedList<String>();
    
    if (players[0].getScore()==0){
      remaining_players.addLast("b");}
    else if (players[0].getScore() > score) { 
      score = players[0].getScore();}
    if (players[1].getScore()==0){
      remaining_players.addLast("r");}
    else if (players[1].getScore() > score){
      score = players[1].getScore();}
    if (players[2].getScore()==0){
      remaining_players.addLast("g");}
    else if (players[2].getScore() > score){
      score = players[2].getScore();}
    if (players[3].getScore()==0){
      remaining_players.addLast("y");}
    else if (players[3].getScore() > score){
      score = players[3].getScore();}
    
    
    
    Table minimal_table = Table.replace(table);
    int[] pointer = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
    minimal_table.sort(pointer);
    
    int min_idx = 0;
    while (minimal_table.rows[min_idx].isEmpty() == false && min_idx<7){
      min_idx = min_idx+1;}
    
    Player temp = new Player("", 0, 0, 0, 0, 0, "");
    String currentPlayer = temp.convertStr(currentPlayerInt);
    Player[] playersStart = new Player[] {temp,temp,temp,temp}; 
    Player[] playersEnd = new Player[] {temp,temp,temp,temp};
    
    for (int u = 0; u<4; u++){
      playersStart[u] = Player.replace(players[u]);
      playersEnd[u] = Player.replace(players[u]);}
    
    // list to store all Submoves
    LinkedList<Move> allSubMoves = new LinkedList<Move>();
    int temp1 = currentPlayerInt;
    int temp2 = 0;
    Table tableStart = Table.replace(minimal_table);
    Table tableEnd = Table.replace(minimal_table);
    
    Move move = new Move(temp1, 0, "", 0, false, temp2, "", "", tableStart, tableEnd, playersStart, playersEnd);
    
    if (playersEnd[temp1].getTotal() > 0){
      allSubMoves.addLast(move);}
    
    else if (playersEnd[temp1].getTotal() == 0){
      move.setElimination(true);
      LinkedList<String> remaining_players_temp = new LinkedList<String>();
      for (int l = 0 ; l<remaining_players.size() ; l++){
        remaining_players_temp.addLast(remaining_players.get(l));}
      remaining_players_temp.remove(currentPlayer);
      LinkedList<String> played_sub_temp = new LinkedList<String>();
      for (int l = 0 ; l<played_sub.size() ; l++){
        if (remaining_players_temp.contains(played_sub.get(l))){
          played_sub_temp.addLast(played_sub.get(l));}}
      move.setPlayerNext( temp.convertInt(temp.nextPlayer(0, "", remaining_players_temp, playersEnd, "", currentPlayer)) );
      
      int s = Math.abs(remaining_players_temp.size()-4);
      move.getPlayersEnd(temp1).setScore(s);
      players[temp1].setScore(s);
      allSubMoves.addLast(move);
      
      /////////////////////add moves with donations /////////////////
      
      for (int m = 0; m<remaining_players_temp.size(); m++){
        int temp3 = temp.convertInt( remaining_players_temp.get(m) );
        if (move.getPlayersEnd(temp3).getPrisoners() > 0){
          for (int n = 0; n<4; n++){
            if (temp3 !=  n && move.getPlayersEnd(temp3).getChip(n) > 0){
              playersEnd = new Player[] {temp,temp,temp,temp};
              
              for (int u = 0; u<4; u++){
                playersEnd[u] = Player.replace(players[u]);}
              Move move_sub = new Move(temp1, 0, "", 0, false, temp2, "", "", tableStart, tableEnd, playersStart, playersEnd);
              move_sub.getPlayersEnd(temp3).donate(move_sub.getPlayersEnd(temp1), temp.convertStr(n),1);
              allSubMoves.addLast(move_sub);}
          }
        }
      }
    }
    
    // list to store all moves
    LinkedList<Move> allMoves = new LinkedList<Move>();
      
    // Move move = new Move( number, playerCurrent, row, chipPlayed, capture, elimination, playerNext, discardingChips, discardingCapture);
    for (int i = 0; i<min_idx+1 ; i++){
      
      for (int z = 0; z < allSubMoves.size(); z++){
        
        if (allSubMoves.get(z).getPlayersEnd(temp1).getTotal() == 0 && i==0){
          allMoves.addLast(allSubMoves.get(z));}
        
        else if (allSubMoves.get(z).getPlayersEnd(temp1).getTotal() > 0){
          
          for (int j = 0; j<4 ; j++){
            
            if ( allSubMoves.get(z).getPlayersEnd(temp1).getChip(j) > 0 ){
              tableStart = new Table(8);
              tableEnd = new Table(8);
              playersEnd = new Player[] {temp,temp,temp,temp};
              
              for (int u = 0; u<4; u++){
                playersEnd[u] = Player.replace(players[u]);}
              move = new Move(temp1, i, "", 0, false, temp2, "", "", tableStart, tableEnd, playersStart, playersEnd);
              move.replace(allSubMoves.get(z));
              move.setChipPlayed(temp.convertStr(j));
              move.getPlayersEnd(temp.convertInt(move.getPlayersEnd(temp1).getPlayer())).setChip(j, -1);
              move.getTableEnd().rows[i].addLast(temp.convertStr(j));
              String played_row = "";
              for (int k = 0 ; k < move.getTableEnd().rows[i].size() ; k++){
                played_row += move.getTableEnd().rows[i].get(k);}
              if ( move.getTableEnd().rows[i].size() > 1
                    && move.getTableEnd().rows[i].get(move.getTableEnd().rows[i].size()-2).equals(move.getTableEnd().rows[i].get(move.getTableEnd().rows[i].size()-1 )) == true ) {
                move.setCapture(1);
                  // reinitiates the row to an empty row
                while(move.getTableEnd().rows[i].isEmpty() == false){
                  move.getTableEnd().rows[i].removeLast() ;}
                String possibleNP = temp.nextPlayer( move.getCapture(), played_row, remaining_players, players, move.getChipPlayed(), move.getPlayersEnd(temp1).getPlayer()) ;
                move.setPlayerNext(temp.convertInt(possibleNP) ); // assumption: only 1 possibleNP
                String discard_cap = "";
                discard_cap = played_row;
                move.getPlayersEnd(j).capture(played_row);
                int[] discard_cap_count = new int[4];
                discard_cap_count = move.discardCount(discard_cap); // TESTING
                //for (int f = 0; i < discard_cap_count.length; f++){ //for loop to print the array  
                  //System.out.print( discard_cap_count[f]+ " ");}  
                //System.exit(0); // \\
                for (int a = 0; a<=discard_cap_count[0]; a++){
                  for (int b = 0; b<=discard_cap_count[1]; b++){
                    for (int c = 0; c<=discard_cap_count[2]; c++){
                      for (int d = 0; d<=discard_cap_count[3]; d++){
                        String discard_cap_sub = "";
                        discard_cap_sub = "bbbbbbbb".substring(0, a) + "rrrrrrrr".substring(0, b) + "gggggggg".substring(0, c) + "yyyyyyyy".substring(0, d);
                        if (discard_cap_sub.length()>0){ //
                          playersEnd = new Player[] {temp,temp,temp,temp};
                          
                          for (int u = 0; u<4; u++){
                            playersEnd[u] = Player.replace(players[u]);}
                          Move move_sub = new Move(temp1, i, "", 0, false, temp2, "", "", tableStart, tableEnd, playersStart, playersEnd);
                          move_sub.replace(move);
                          move_sub.setDiscardingCap(discard_cap_sub);
                          move_sub.getPlayersEnd(j).discard("b",a);
                          move_sub.getPlayersEnd(j).discard("r",b);
                          move_sub.getPlayersEnd(j).discard("g",c);
                          move_sub.getPlayersEnd(j).discard("y",d);
                          allMoves.addLast(move_sub);}
                      }}}}
              } // if (capture = 1)
              else{
                String possibleNP = temp.nextPlayer( move.getCapture(), played_row, remaining_players, players, move.getChipPlayed(), move.getPlayersEnd(temp1).getPlayer()) ;
                for (int x = 0; x<possibleNP.length(); x++){
                  Move move_sub = new Move(temp1, i, "", 0, false, temp2, "", "", tableStart, tableEnd, playersStart, playersEnd);
                  move_sub.replace(move);
                  move_sub.setPlayerNext(temp.convertInt( Character.toString(possibleNP.charAt(x)) ) );
                  allMoves.addLast(move_sub);
                }
              }
            }
          }
        } // end-if (move.getPlayer().getTotal() > 0)
      }
    }
    
    //System.exit(0); // \\
    
    LinkedList<Move> allMoves2 = new LinkedList<Move>();
    
    // add discarding chips
      for (int y = 0; y < allMoves.size(); y++){
        // create list to store all moves
        tableStart = Table.replace(minimal_table);
        tableEnd = new Table(8);
        playersEnd = new Player[] {temp,temp,temp,temp};
        for (int u = 0; u<4; u++){
          playersEnd[u] = Player.replace(players[u]);}
        move = new Move(temp1, 0, "", 0, false, temp2, "", "", tableStart, tableEnd, playersStart, playersEnd);
        move.replace(allMoves.get(y));
        
        if (move.getPlayersEnd(temp.convertInt(move.getPlayersEnd(temp1).getPlayer())).getPrisoners() > 0){
          String discard = move.getPlayersEnd(temp.convertInt(move.getPlayersEnd(temp1).getPlayer())).getDiscard();
          int[] discard_count = move.discardCount(discard);
          //for (int f = 0; f < discard_count.length; f++){ //for loop to print the array  
            //System.out.print( discard_count[f]+ " ");}  
          //System.exit(0); // \\
          discard_count = new int[4];
          for (int e = 0; e < discard.length(); e++){
            if (discard.charAt(e) == 'b'){
              discard_count[0] = discard_count[0] +1;}
            else if (discard.charAt(e) == 'r'){
              discard_count[1] = discard_count[1] +1;}
            else if (discard.charAt(e) == 'g'){
              discard_count[2] = discard_count[2] +1;}
            else if (discard.charAt(e)== 'y'){
              discard_count[3] = discard_count[3] +1;}
          }
          int limit_discard = 1;
          for (int a = 0; a<=Math.min(discard_count[0],limit_discard); a++){
            for (int b = 0; b<=Math.min(discard_count[1],limit_discard); b++){
              for (int c = 0; c<=Math.min(discard_count[2],limit_discard); c++){
                for (int d = 0; d<=Math.min(discard_count[3],limit_discard); d++){
                  String discard_sub = "";
                  tableStart = Table.replace(minimal_table);
                  tableEnd = new Table(8);
                  playersEnd = new Player[] {temp,temp,temp,temp};
                  for (int u = 0; u<4; u++){
                    playersEnd[u] = Player.replace(players[u]);}
                  Move move_sub = new Move(temp1, 0, "", 0, false, temp2, "", "", tableStart, tableEnd, playersStart, playersEnd);
                  move_sub.replace(move);
                  discard_sub = "bbbbbbbb".substring(0, a) + "rrrrrrrr".substring(0, b) + "gggggggg".substring(0, c) + "yyyyyyyy".substring(0, d);
                  move_sub.setDiscardingChips(discard_sub);
                  move_sub.getPlayersEnd(temp.convertInt(move.getPlayersEnd(temp1).getPlayer())).discard("b",a);
                  move_sub.getPlayersEnd(temp.convertInt(move.getPlayersEnd(temp1).getPlayer())).discard("r",b);
                move_sub.getPlayersEnd(temp.convertInt(move.getPlayersEnd(temp1).getPlayer())).discard("g",c);
                move_sub.getPlayersEnd(temp.convertInt(move.getPlayersEnd(temp1).getPlayer())).discard("y",d);
                allMoves2.addLast(move_sub);}}}}
        }
        else {
          allMoves2.addLast(move);}
        
      }
//      System.out.println("number of moves "+ allMoves2.size());
//      for (int y = 0; y < allMoves2.size(); y++){
//        System.out.println(y);
//        allMoves2.get(y).display();}
      
      return allMoves2;
  } 
  
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public static int minimax(Move m){
    
    if (m.getMaxScore() == 3){
      if (m.getPlayerNext() == 1){
        //System.out.println("A");
        return -1;}
      else {
        //System.out.println("B");
        return +1;}
    }
    
    else { // maxScore < 3, ie game has not ended 
      
      LinkedList<Move> allMoves = new LinkedList<Move>();
      Player[] players = new Player[] {m.getPlayersEnd(0), m.getPlayersEnd(1), m.getPlayersEnd(2), m.getPlayersEnd(3)};
      //System.out.println("C");
      allMoves = (LinkedList) minimax.getAllMoves(m.getTableEnd(), players, m.getPlayerNext()).clone();
      //System.out.println("D");
      Integer[] s = new Integer[allMoves.size()];
      for (int i = 0; i<allMoves.size(); i++){
        //System.out.println("E");
        s[i] = minimax(allMoves.get(i));
        //System.out.println(s[i]);
      }
      //System.out.println(Collections.max(Arrays.asList(s)));
      // what's going on here? 
      if (m.getPlayerNext() == 1){
        return Collections.min(Arrays.asList(s));} // looking at next player not current player ??
      else{
        return Collections.max(Arrays.asList(s));}
    }
  }
}

// now: return who wins
// later: return optimal move to make



// TIC TAC TOE method
// isMovesLeft: tests if there are any moves left to be done
// evaluate: evaluates if there is a winner after last move
// minimax function
// findBestMove: returns best move to play among valid moves by running minimax
// terminating condition: players have no more chips
// what should minimax do? Returns the optimal value a maximizer can obtain: ie highest score
//
//
// SITUATION
// 2 players, 3 chips one color each, empty board
// P1 is maximizer, P2 is minimizer
// method EvalConf (Move m)
// C is a game situation: table, players, chips
// { if game is over in C: return score
//   game is over when one player has no more chips, as the other won't donate. Otherwise, game is not over
//   else {
//          generate next possible configurations C_1, C_2, C_3 ...
//          for every C_i, score_i = EvalConf(C_i)
//          if currentPlayer == P1: return max{score_i)
//          else if currentPlayer == P2: return min(score_i)
//        }

// }