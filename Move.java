// APPENDIX C

public class Move {
  
  private int row;                // row being played
  private int playerCurrent;   // player making the move
  private String chipPlayed;      // chip being played
  private int playerNext;      // player who gets next move
  private int capture;            // 0 = capture; 1 = no capture
  private boolean elimination;    // True = player is eliminated at the end of the move
  private String discardingChips; // chips in captured row that can be discarded
  private String discardingCap;
  private Table tableStart;
  private Table tableEnd;
  private Player[] playersStart;
  private Player[] playersEnd;
  
  
  // constructor
  public Move(int playerCurrent, int row, String chipPlayed, int capture, boolean elimination, int playerNext, String discardingChips,
                String discardingCap, Table tableStart, Table tableEnd, Player[] playersStart, Player[] playersEnd){
    
    this.playerCurrent = playerCurrent;
    this.row = row;
    this.chipPlayed = chipPlayed;
    this.capture = capture;
    this.elimination = elimination;
    this.playerNext = playerNext;
    this.discardingChips = discardingChips;
    this.discardingCap = discardingCap;
    this.tableStart = tableStart;
    this.tableEnd = tableEnd;
    this.playersStart = playersStart;
    this.playersEnd = playersEnd;}
  
  // getters
  public int getPlayer(){
    return this.playerCurrent;}
  
  public int getRow(){
    return this.row;}
  
  public String getChipPlayed(){
    return this.chipPlayed;}
  
  public int getCapture(){
    return this.capture;}
  
  public boolean getElimination(){
    return this.elimination;}
  
  public int getPlayerNext(){
    return this.playerNext;}
  
  public String getDiscardingChips(){
    return this.discardingChips;}
  
  public String getDiscardingCap(){
    return this.discardingCap;}
  
  public Table getTableStart(){
    return this.tableStart;}
  
  public Table getTableEnd(){
    return this.tableEnd;}
  
  public Player getPlayersStart(int i){
    return this.playersStart[i];}
  
  public Player getPlayersEnd(int i){
    return this.playersEnd[i];}
  
  // returns highest score at beginning of move
  public int getMaxScore(){
    int max = 0;
    for (int i = 0; i<4; i++){
      if ( this.getPlayersEnd(i).getScore() > max){
        max = this.getPlayersEnd(i).getScore();}
    }
    return max;
  }
  
  // returns string of remaining players at beginning of move
  public String getRemPlayers(){
    String remPlayers = "";
    for (int i = 0; i<4; i++){
      if ( this.getPlayersStart(i).getScore() == 0){
        remPlayers = remPlayers + this.getPlayersEnd(i).getPlayer();}}
    return remPlayers;
  }
  
  // setters
  public void setPlayer(int p){
    this.playerCurrent = p;}
  
  public void setRow(int r){
    this.row = r;}
  
  public void setChipPlayed(String c){
    this.chipPlayed = c;}
  
  public void setCapture(int c){
    this.capture = c;}
  
  public void setElimination(boolean e){
    this.elimination = e;}
  
  public void setPlayerNext(int p){
    this.playerNext = p;}
  
  public void setDiscardingChips(String d){
    this.discardingChips = d;}
  
  public void setDiscardingCap (String d){
    this.discardingCap = d;}
  
  public void setTableStart(Table t1){
    this.tableStart = t1;}
  
  public void setTableEnd(Table t2){
    this.tableEnd = t2;}
  
  public void setPlayersStart(int i, Player p1){
    this.playersStart[i] = p1;}
  
  public void setPlayersEnd(int i, Player p2){
    this.playersEnd[i] = p2;}
  
  public void replace(Move other){
    this.playerCurrent = other.playerCurrent;
    this.row = other.row;
    this.chipPlayed = other.chipPlayed;
    this.capture = other.capture;
    this.elimination = other.elimination;
    this.playerNext = other.playerNext;
    this.discardingChips = other.discardingChips;
    this.discardingCap = other.discardingCap;
    this.tableStart = Table.replace(other.tableStart);
    this.tableEnd = Table.replace(other.tableEnd);
    for (int i = 0 ; i < 4 ; i++){
      this.playersStart[i] = Player.replace(other.playersStart[i]);
      this.playersEnd[i] = Player.replace(other.playersEnd[i]);}
  }
  
  public static int[] discardCount(String d){  
    int[] discard_cap_count = new int[4];
    for (int i = 0; i < d.length(); i++){
      if (d.charAt(i) == 'b'){
        discard_cap_count[0] = discard_cap_count[0] +1;}
      else if (d.charAt(i) == 'r'){
        discard_cap_count[1] = discard_cap_count[1] +1;}
      else if (d.charAt(i) == 'g'){
        discard_cap_count[2] = discard_cap_count[2] +1;}
      else if (d.charAt(i)== 'y'){
        discard_cap_count[3] = discard_cap_count[3] +1;}
    } 
    return discard_cap_count;}  
  
  // display information about this move
  public void display(){
    System.out.println("Current Player: " + this.playerCurrent);
    System.out.println("Row #" + this.row);
    System.out.println("Chip placed: " + this.chipPlayed);
    System.out.println("There is a capture: " + this.capture);
    System.out.println("Next Player: "+ this.playerNext);
    System.out.println("General chips to discard " + this.discardingChips);
    System.out.println("Capture chips to discard " + this.discardingCap);
    System.out.println("Table Start");
    this.tableStart.display();
    System.out.println("Table End");
    this.tableEnd.display();
    System.out.println("Players Start");
    for (int i = 0 ; i < 4 ; i++){
      this.playersStart[i].display();}
    System.out.println("Players End");
    for (int i = 0 ; i < 4 ; i++){
      this.playersEnd[i].display();}
  }

}