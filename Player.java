// APPENDIX A
import java.io.InputStream;
import java.util.Scanner;
import java.util.LinkedList;

public class Player {
  
  
  private int score, blue, red, green, yellow;
  private String color, before;
  
  // constructor
  public Player( String color, int b, int r, int g, int y, int score, String before){
    this.color = color;
    this.blue = b;
    this.red = r;
    this.green = g;
    this.yellow = y;
    this.score = score;
    this.before = before;}
  
  // getters
  public String getPlayer(){
    return this.color;}
  
  public int getChip(int chip_color){
    if (chip_color == 0){ return this.blue;}
    else if (chip_color == 1) {return this.red;}
    else if (chip_color == 2) {return this.green;}
    else if (chip_color == 3) {return this.yellow;}
    return -1;}
  
  public int getTotal(){
    return this.blue + this.red + this.green + this.yellow;}
  
  public int getScore(){
    return this.score;}
  
  public String getBefore(){
    return this.before;}
  
  public int getPrisoners(){
    if (this.getPlayer().equals("b")){ return this.red + this.green + this.yellow;}
    else if (this.getPlayer().equals("r")){ return this.blue + this.green + this.yellow;}
    else if (this.getPlayer().equals("g")){ return this.blue + this.red + this.yellow;}
    else if (this.getPlayer().equals("y")){ return this.blue + this.red + this.green;}
    return -1;}
  
  public String getDiscard(){
    String discard = "";
    if (this.getPrisoners() != 0){
      for (int i = 0; i<4; i++){
        if (this.convertInt(this.color) != i){
          for (int j = 0; j< this.getChip(i); j++){
            discard = discard.concat(this.convertStr(i));}}}
    }
    return discard;}
  
  //setters
  public void setColor(String c){
    this.color = c;}
  
  public void setChip (int chip_color, int chip_num){
    if (chip_color == 0){this.blue = this.blue + chip_num;}
    else if (chip_color == 1){this.red = this.red + chip_num;}
    else if (chip_color == 2){this.green = this.green + chip_num;}
    else if (chip_color == 3){this.yellow = this.yellow + chip_num;}}
  
  public void setScore(int i){
    this.score = i;}
  
  public void setBefore(String b){
    this.before = b;}
  
  // convert color to number 
  public int convertInt(String s){
    if (s.equals("b")){return 0;}
    else if (s.equals("r")){return 1;}
    else if (s.equals("g")){return 2;}
    else {return 3;}}
  
  // convert number to color 
  public String convertStr(int i){
    if (i == 0) {return "b";}
    else if (i == 1) {return "r";}
    else if (i == 2) {return "g";}
    else {return "y";}}
  
  // replace this Player with other
  public static Player replace(Player other){
    Player rep = new Player("", 0, 0, 0, 0, 0, "");
    rep.color = other.color;
    rep.blue = other.blue;
    rep.red = other.red;
    rep.green = other.green;
    rep.yellow = other.yellow;
    rep.score = other.score;
    rep.before = other.before;
    return rep;}
  
  // this Player captures row i
  public void capture(String captured_row){
    for (int i = 0; i < captured_row.length(); ++i){
      this.setChip( this.convertInt(Character.toString(captured_row.charAt(i))), 1);;}}
  
  // discards chip from this Player pile
  public void discard(String discarded_chip, int num){
    this.setChip(this.convertInt(discarded_chip) , -num);}
  
  // returns true if this Player is eliminated
  public boolean eliminated () {
    return (this.getScore() != 0);}

  // donate chip_number amount of color chips from Player this to other
  // eligibility for donation verified in main code
  public void donate(Player other, String color, int chip_number){
    other.setChip(this.convertInt(color) , chip_number);
    this.setChip(this.convertInt(color) , -chip_number);}
 
  // returns string of next possible players
  public String nextPlayer(int captureturn, String played_row, LinkedList<String> remaining_players, 
                           Player[] players, String chipPlayed, String currentPlayer){
    
    String possibleNextPlayers = "";
    String refrain = "";
    
    if (captureturn == 0){
      
      // creating played_row_sub
      String played_row_sub="";
      for (int m = 0 ; m<played_row.length() ; m++){
        if (remaining_players.contains(Character.toString(played_row.charAt(m)))){
          played_row_sub = played_row_sub.concat(Character.toString(played_row.charAt(m)));}}
      
      // if not all players are in played_row
      for(int i = 0; i < remaining_players.size() ; i++) { 
        if (played_row_sub.contains(remaining_players.get(i)) == false) {
          possibleNextPlayers = possibleNextPlayers.concat(remaining_players.get(i));}}
      
      // if all players are represented in played_row
      if (possibleNextPlayers.isEmpty()==true){
        for (int j = played_row_sub.length() - 1 ; j>=0 ; j--){
          if (refrain.indexOf(played_row_sub.charAt(j)) == -1){
            refrain = refrain.concat(Character.toString(played_row_sub.charAt(j)));}}
        possibleNextPlayers = possibleNextPlayers.concat(Character.toString(refrain.charAt(refrain.length()-1)));}
      
    } // end- if (captureturn == 0)
    
    else if (captureturn ==1 && players[this.convertInt(chipPlayed)].eliminated() == false){
      possibleNextPlayers = possibleNextPlayers.concat(chipPlayed);}
    
    else if (captureturn == 1 && players[this.convertInt(chipPlayed)].eliminated() == true) {
      possibleNextPlayers = possibleNextPlayers.concat(currentPlayer);}
    
    return possibleNextPlayers;}
  
  // display all information about this player
  public void display(){
    System.out.println("Player " + this.color + ": (" + this.blue + " Blue, " + this.red + " Red, "
                         + this.green + " Green, " + this.yellow + " Yellow). Score: " + this.score);}
  
}