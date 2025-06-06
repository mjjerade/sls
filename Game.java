// APPENDIX E

import java.io.InputStream;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.*;

class Game {
  
  // Generic method to get subarray of a non-primitive array
  // between specified indices
  public static<T> T[] subArray(T[] array, int beg, int end) {
    return Arrays.copyOfRange(array, beg, end + 1);}
  
  
  public static void main(String[] args){
    
    int score = 0;           // score of the game (first player out gets 1, last player out gets 4)
    int turn = 0;            // turn of the game being played
    int captureturn = 0;     // if no capture == 0; else == 1
    String playAgain = "0";
    String currentPlayer = "";
    String played_row = "";
    String chipPlayed = "";
    String checkElim = "";
    Scanner scanner;
    
    // played[i]: player who played turn i
    LinkedList<String> played = new LinkedList<String>();
    
    // played: same as player[i] minus eliminated_players
    LinkedList<String> played_sub = new LinkedList<String>();
    
    // possibleNextPlayers[i]: list of eligible players for turn i+1
    // assuming <= 100 turns are played
    LinkedList<String>[] possibleNextPlayers;
    possibleNextPlayers = new LinkedList[100];
    for (int i = 0; i<100; i++){
      possibleNextPlayers[i] = new LinkedList<String>();}
    
    // trackTables[i]: state of table at beginning of turn i
    LinkedList<Table> trackTables = new LinkedList<Table>();
    
    // trackPlayers[i]: state of players at beginning of turn i
    // after eliminating, placing, capturing, discarding, donating
    LinkedList<Player>[] trackPlayers;
    trackPlayers = new LinkedList[100];
    for (int i = 0; i<100 ; i++){
      trackPlayers[i] = new LinkedList<Player>();}
    
    // initializing the 4 players
    // b == 0; r == 1; g == 2; y == 3
    Player blue = new Player("b", 0, 0, 0, 0, 0, "r");
    Player red = new Player("r", 1, 1, 0, 0, 0, "y");
    Player green = new Player("g", 0, 1, 1, 0, 0,"b");
    Player yellow = new Player("y", 0, 0, 0, 1, 0,"g");
    Player[] players = new Player[] {blue,red,green,yellow};
    
    // players not yet eliminated
    LinkedList<String> remaining_players = new LinkedList<String>();
    remaining_players.addLast("b");
    remaining_players.addLast("r");
    remaining_players.addLast("g");
    remaining_players.addLast("y");
    
    // initializing and displaying table
    Table table = new Table(8);
    table.display();
    
    // displaying player information
    for (int i = 0 ; i<4 ; i++){
      System.out.print(players[i].getPlayer()+": ");
      players[i].display();}
    
    // inserting chips into the initial table
    String input = "yes";
    int j = 0;
    
    //////////////////////////////////////// START: SETTING THE TABLE////////////////////////////////////////////////// 
    while ( input.equals("yes") && j<8 ){
      
      scanner = new Scanner(System.in);
      System.out.print("Do you want to insert chips in row " + j + "? Answer yes or no.");
      input = scanner.nextLine();
      
      if (input.equals("yes")){
        boolean valid = false;
        String inputRow = new String();
        
        // checks if string is valid
        while (valid == false){  
          scanner = new Scanner(System.in);
          System.out.print("How would you like the row to look?");
          inputRow = scanner.nextLine();
          valid = true; // assumes input is true and then checks for error
          if (inputRow.contains("bb") || inputRow.contains("rr") || inputRow.contains("gg") || inputRow.contains("yy")){
            valid = false;
            System.out.print("The input is not a valid row. Please try again.");}
          else{
            for (int i = 0; i< inputRow.length(); i++){
              if (inputRow.charAt(i) != 'b' && inputRow.charAt(i) != 'r' && inputRow.charAt(i) != 'g' && inputRow.charAt(i) != 'y'){
                valid = false;
                System.out.print("The input is not a valid row. Please try again.");}}}
        }
        
        for (int i = 0; i < inputRow.length(); i++){
          table.rows[j].addLast(Character.toString(inputRow.charAt(i)));}
      } // end if-statement
      
      j++;} // end while loop
    //////////////////////////////////////// END: SETTING THE TABLE//////////////////////////////////////////////////// 
    
    
    while (playAgain.equals("0") && score<4) { // while-loop to play multiple turns
      
      System.out.println("This is turn #"+turn);
      // track state of players at beginning of turn
      Player blue2 = Player.replace(blue);
      Player red2 = Player.replace(red);
      Player green2 = Player.replace(green);
      Player yellow2 = Player.replace(yellow);
      
      trackPlayers[turn].addLast(blue2);
      trackPlayers[turn].addLast(red2);
      trackPlayers[turn].addLast(green2);
      trackPlayers[turn].addLast(yellow2);
      
      // track state of table at beginning of turn
      Table table2 = Table.replace(table);
      trackTables.addLast(table2);
      table.display();
      
      captureturn = 0;
      
      // choosing player for turn = 0 
      if (turn == 0){
        for (j = 0; j<4; j++){
          if (players[j].getScore() == 0){
            possibleNextPlayers[turn].addLast(blue.convertStr(j));}
        }}
      
      System.out.print("The possible next players are: ");
      for (j = 0; j<possibleNextPlayers[turn].size(); j++){
        if (remaining_players.contains(possibleNextPlayers[turn].get(j))){
          System.out.print(possibleNextPlayers[turn].get(j));}}
      System.out.println();
      
      int flagValidPlayer = 1;
      while (flagValidPlayer!=0){
        scanner = new Scanner(System.in);
        System.out.println("Who do you want to play? Answer: b ; r ; g ; y");
        currentPlayer = scanner.nextLine();
        if (possibleNextPlayers[turn].indexOf(currentPlayer) != -1 && (players[blue.convertInt(currentPlayer)].eliminated() == false) ){
          flagValidPlayer=0;
          if (turn > 0) {players[blue.convertInt(currentPlayer)].setBefore(played.get(turn-1));}
        }
      }
      
      int currentPlayerInt = blue.convertInt(currentPlayer); 
      System.out.println( "It is "+ currentPlayer +"'s turn to play.");
      played.addLast( players[currentPlayerInt].getPlayer() );
      played_sub.addLast( players[currentPlayerInt].getPlayer() );
      
      ////////////////////////////////////// START: CHECK ELIMINATION /////////////////////////////////////////////////
      checkElim = "";
      // checks if player should be eliminated
      if ((players[currentPlayerInt].getTotal()== 0) && (players[currentPlayerInt].getScore()==0)){
        System.out.println(players[currentPlayerInt].getPlayer()+" is eliminated unless a donation take place.");
        
        scanner = new Scanner(System.in);
        System.out.println("Would any players like to donate chips? Answer: b, r, y, g, none.");
        String ansDonatePlayer = scanner.nextLine();
        
        if (ansDonatePlayer.compareTo("none")!=0){
          scanner = new Scanner(System.in);
          System.out.println("Which color would you like to donate?");
          String ansDonateColor = scanner.nextLine();
          
          scanner = new Scanner(System.in);
          System.out.println("How many would you like to donate?");
          int ansDonateNum = Integer.parseInt(scanner.nextLine());
          
          players[blue.convertInt(ansDonatePlayer)].donate(players[currentPlayerInt],ansDonateColor,ansDonateNum);}
        
        else {
          
          players[currentPlayerInt].setScore(++score);
          System.out.println("Player is eliminated. Their score is "+players[currentPlayerInt].getScore());
          remaining_players.remove(currentPlayer);
          
          // remove eliminated player from played_sub
          while (played_sub.contains(players[currentPlayerInt].getPlayer()) == true){
            played_sub.remove(players[currentPlayerInt].getPlayer());}
          
          if (played_sub.size() > 0 && score < 4) {
            checkElim = played_sub.get(played_sub.size()-1);} 
          
          else if ( played_sub.size()==0 && score<4 ){
            checkElim = players[currentPlayerInt].getBefore();
            possibleNextPlayers[turn+1].addLast(checkElim);}
          System.out.println("Next turn goes to " + checkElim);
        }
      } // end if-statement
      //////////////////////////////////// END: CHECK ELIMINATION /////////////////////////////////////////////////////
      //
      // Turn continues if player is not eliminated
      boolean flagValidChip = false;
      chipPlayed = "";
      
      if (checkElim.equals("") && score<4){
        while ( flagValidChip == false ) {
          
          scanner = new Scanner(System.in);
          System.out.print("Chip color to play: b = blue ; r = red ; g = green ; y = yellow");
          chipPlayed = scanner.nextLine();
          flagValidChip = true;
          
          if ( players[currentPlayerInt].getChip(blue.convertInt(chipPlayed))>0){
            players[currentPlayerInt].setChip(blue.convertInt(chipPlayed) , players[currentPlayerInt].getChip(blue.convertInt(chipPlayed))-1);}
          else {
            System.out.println("No chips of that color. Please choose another color.");
            flagValidChip = false; }
          
        } // end while loop 
        
        scanner = new Scanner(System.in);
        System.out.print("Row to place the chip:");
        String rowPlayed = scanner.nextLine();
        table.rows[Integer.parseInt(rowPlayed)].addLast(chipPlayed);
        
        System.out.println(chipPlayed+" chip is added to row "+rowPlayed); 
        
        played_row = "";
        for (int i = 0 ; i < table.rows[Integer.parseInt(rowPlayed)].size() ; i++){
          played_row += table.rows[Integer.parseInt(rowPlayed)].get(i);}
        
        
        // if-statement for capturing
        
        if (table.rows[Integer.parseInt(rowPlayed)].size()>1 &&
            table.rows[Integer.parseInt(rowPlayed)].get(table.rows[Integer.parseInt(rowPlayed)].size()-2).equals(table.rows[Integer.parseInt(rowPlayed)].get(table.rows[Integer.parseInt(rowPlayed)].size()-1))){
          
          captureturn = 1;
          System.out.println("A capture will take place.");
          if (players[blue.convertInt(chipPlayed)].eliminated() == true ) {
            System.out.println("Player " + chipPlayed + " is eliminated. The row will be discarded and the move goes back to "+currentPlayer);}
          else{
            int b = 0 ;
            while (b < played_row.length() ) {
              String a = Character.toString(played_row.charAt(b));
              if ( players[blue.convertInt(a)].eliminated() == true ){
                System.out.println("Chip "+ a + " will be discarded because Player " + a + " is eliminated.");
                  played_row = played_row.replace(a,"");}
              b = ++b ;
            }
            System.out.println("Player " + chipPlayed + " takes all the remaining chips, will choose to discard one and the next move goes back to them.");}
          if (players[blue.convertInt(chipPlayed)].eliminated() == false){
            players[blue.convertInt(chipPlayed)].capture(played_row);
            
            // discards chip
            int index = -1;
            String chipDiscard = "";
            
            while (index == -1){
              scanner = new Scanner(System.in);
              System.out.print("Which chip color do you want to discard? b = blue ; r = red ; g = green ; y = yellow");
              chipDiscard = scanner.nextLine();
              index = played_row.indexOf(chipDiscard);}
            
            players[blue.convertInt(chipPlayed)].discard(chipDiscard,1);}
          
          // reinitiates the row to an empty row
          while(table.rows[Integer.parseInt(rowPlayed)].isEmpty() == false){
            table.rows[Integer.parseInt(rowPlayed)].removeLast() ;}} // end if-statement for capturing      
      
        // finding possible next players
        String nxtp = players[blue.convertInt(chipPlayed)].nextPlayer(captureturn, played_row, remaining_players, players, chipPlayed, currentPlayer);
        for (int i = 0; i<nxtp.length(); i++){
          possibleNextPlayers[turn+1].addLast(Character.toString(nxtp.charAt(i)));}
        
        // donating chip 
        // same for donate as for discarding
        boolean flagDonate = false;
        
        while (flagDonate == false && players[blue.convertInt(chipPlayed)].getPrisoners()>0) {
          
          String ansDonate = new String();
          String colorDonate = new String();
          
          scanner = new Scanner(System.in);
          System.out.print("Would you like to donate a chip to a player? Answer: yes or no.");
          ansDonate = scanner.nextLine();
          
          if (ansDonate.equals("yes")){
            
            scanner = new Scanner(System.in);
            System.out.print("Who would you like to donate the chip to? Answer: b, r, y, g.");
            String chipDiscard = scanner.nextLine();
            
            scanner = new Scanner(System.in);
            System.out.print("Which color would you like to donate?");
            colorDonate = scanner.nextLine();
            
            scanner = new Scanner(System.in);
            System.out.print("How many would you like to donate?");
            String numbDonate = scanner.nextLine();
            players[blue.convertInt(chipPlayed)].donate(players[blue.convertInt(chipDiscard)],colorDonate,Integer.parseInt(numbDonate));}
          
          else{
            flagDonate=true;}
        } // end while loop 
        
        // discarding chip 
        
        boolean flagDiscard = false;
        
        while (flagDiscard == false && players[blue.convertInt(chipPlayed)].getPrisoners()>0) {
          
          // if prisoners > 0: will ask
          // then only ask about possible chips to discard + give numbers 
          // keep asking till input is correct
          
          scanner = new Scanner(System.in);
          System.out.print("Would you like to discard a chip? Answer: yes or no.");
          String ansDiscard = scanner.nextLine();
          if (ansDiscard.equals("yes")){
            
            scanner = new Scanner(System.in);
            System.out.print("Which chip would you like to discard? Answer: b, r, y, g.");
            String chipDiscard = scanner.nextLine();
            
            scanner = new Scanner(System.in);
            System.out.print("How many chips would you like to discard?");
            String numbDiscard = scanner.nextLine();
            
            if (chipPlayed.equals(chipDiscard) == false){
              players[blue.convertInt(chipPlayed)].discard(chipDiscard, Integer.parseInt(numbDiscard));}
          }
          else { flagDiscard = true;}
        } // end while loop 
        
      } // end if ( checkElim.equals("") && score<4 )
      

      // displays the table in the end and information about players
      table.display();
      System.out.print("Blue: ");
      blue.display();
      System.out.print("Red: ");
      red.display();
      System.out.print("Green: ");
      green.display();
      System.out.print("Yellow: ");
      yellow.display();
      
      
      for (int i = 0 ; i<4 ; i++){
        if (players[i].getScore() == 4){
        System.out.println(players[i].getPlayer() + " wins!");
        }
      }
      
      scanner = new Scanner(System.in);
      System.out.println("Do you want to play another turn or stop the game? 0 = next turn ; 1 = quit ");
      playAgain = scanner.nextLine();
      if (playAgain.equals("0")){;
        turn = turn + 1;}
      else if (playAgain.equals("1")){
        System.out.println("The End.");}
      
      
      
      
    } // end while-loop of game 
    
    // add state of Players and Table at end of game
    Player blue2 = Player.replace(blue);
    Player red2 = Player.replace(red);
    Player green2 = Player.replace(green);
    Player yellow2 = Player.replace(yellow);
    
    // track state of players at end of turn
    trackPlayers[turn].addLast(blue2);
    trackPlayers[turn].addLast(red2);
    trackPlayers[turn].addLast(green2);
    trackPlayers[turn].addLast(yellow2);
    
    Table table2 = Table.replace(table);
    trackTables.addLast(table2);
    
    // end game analysis
    scanner = new Scanner(System.in);
    System.out.println("Would you like to look back on the turns? Answer yes or no");
    String ans1 = scanner.nextLine();
    
    if (ans1.equals("yes")){
      scanner = new Scanner(System.in);
      System.out.println("What would you like to look back on ? 1 = table ; 2 = players");
      String ans2 = scanner.nextLine();
      if (ans2.equals("1")){
        scanner = new Scanner(System.in);
        System.out.println("At which turn would you like to see the table?");
        ans2 = scanner.nextLine();
        trackTables.get(Integer.parseInt(ans2)).display();}
      else if (ans2.equals("2")){
        scanner = new Scanner(System.in);
        System.out.println("After which turn would you like to see the players' chips?");
        ans2 = scanner.nextLine();
        for (int i = 0 ; i < trackPlayers[Integer.parseInt(ans2)].size() ; i++){
          trackPlayers[Integer.parseInt(ans2)].get(i).display();}
      }
    }
    
    
  }
}