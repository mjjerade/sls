public class Move {
  
  private int row;                // row being played
  private Player playerCurrent;   // player making the move
  private String chipPlayed;      // chip being played
  private Player playerNext;      // player who gets next move
  private int capture;            // 0 = capture; 1 = no capture
  private boolean elimination;    // True = player is eliminated at the end of the move
  private String discardingChips; // chips in captured row that can be discarded
  private String discardingCap;
  private Table tableStart;
  private Table tableEnd;
  private Player[] playersStart;
  private Player[] playersEnd;
  
  
  // constructor
  public Move(Player playerCurrent, int row, String chipPlayed, int capture, boolean elimination, Player playerNext, String discardingChips,
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
  public Player getPlayer(){
    return this.playerCurrent;}
  
  public int getRow(){
    return this.row;}
  
  public String getChipPlayed(){
    return this.chipPlayed;}
  
  public int getCapture(){
    return this.capture;}
  
  public boolean getElimination(){
    return this.elimination;}
  
  public Player getPlayerNext(){
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
  
  // setters
  public void setPlayer(Player p){
    this.playerCurrent = p;}
  
  public void setRow(int r){
    this.row = r;}
  
  public void setChipPlayed(String c){
    this.chipPlayed = c;}
  
  public void setCapture(int c){
    this.capture = c;}
  
  public void setElimination(boolean e){
    this.elimination = e;}
  
  public void setPlayerNext(Player p){
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
    this.setPlayer( other.playerCurrent );
    this.row = other.row;
    this.chipPlayed = other.chipPlayed;
    this.capture = other.capture;
    this.elimination = other.elimination;
    this.setPlayerNext( other.playerNext );
    this.discardingChips = other.discardingChips;
    this.discardingCap = other.discardingCap;
    this.tableStart = Table.replace( other.tableStart);
    this.tableEnd = Table.replace( other.tableEnd);
    for (int i = 0 ; i < 4 ; i++){
      this.playersStart[i] = Player.replace(other.playersStart[i]);
      this.playersEnd[i] = Player.replace(other.playersEnd[i]);}
  }
  
  // display information about this move
  public void display(){
    System.out.print("Current Player: ");
    this.playerCurrent.display();
    System.out.println("Row #" + this.row);
    System.out.println("Chip placed: " + this.chipPlayed);
    System.out.println("There is a capture: " + this.capture);
    System.out.print("Next turn goes to Player: ");
    this.playerNext.display();
    System.out.println("Possible chips to discard " + this.discardingChips);
    System.out.println("At least one chip to be discarded from captured row "+ this.discardingCap);
    System.out.println();
    this.tableStart.display();
    this.tableEnd.display();
    for (int i = 0 ; i < 4 ; i++){
      this.playersStart[i].display();}
    for (int i = 0 ; i < 4 ; i++){
      this.playersEnd[i].display();}
  }

}