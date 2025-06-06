// APPENDIX E

import java.io.InputStream;
import java.util.*;
import java.util.LinkedList;
import java.util.Scanner; 

class Game {

  public static void main(String[] args){
    
    /*1*/ Player blue = new Player("b", 1, 1, 1, 0, 0, "r");
    /*2*/ Player red = new Player("r", 0, 1, 0, 0, 0, "y");
    /*3*/ Player green = new Player("g", 0, 0, 0, 0, 1,"b");
    /*4*/ Player yellow = new Player("y", 0, 0, 0, 0, 2,"g");
    boolean flag = true;
    int row = 0;
    int score = Math.max( Math.max(blue.getScore(), red.getScore()), Math.max(green.getScore(), yellow.getScore()) );
    Scanner scanner = new Scanner(System.in);
    String input = "yes";
    String currentPlayer = "";
    LinkedList<Move> allMoves = new LinkedList<Move>();
    LinkedList<String> possibleNP = new LinkedList<String>();
    Player[] players = new Player[] {blue,red,green,yellow};
    Table table = new Table(8);
    
    // set up the table
    while ( input.isEmpty() == false && row<8 ){
      
      flag = false;
      
      while (flag == false){  
        
        System.out.println("Input Table Row " + row + ": ");
        input = scanner.nextLine();
        
        flag = true;
        if (input.contains("bb") || input.contains("rr") || input.contains("gg") || input.contains("yy")){
          flag = false;}
        else{
          for (int i = 0; i< input.length(); i++){
            if (input.charAt(i) != 'b' && input.charAt(i) != 'r' && input.charAt(i) != 'g' && input.charAt(i) != 'y'){
              flag = false;}}}
      }
      
      for (int i = 0; i < input.length(); i++){
        table.rows[row].addLast(Character.toString(input.charAt(i)));}
      
      row++;} // end while loop
    
    // display set up 
    for (int i = 0 ; i<4 ; i++){
      players[i].display();}
    
    table.display();
    
    if (blue.getScore()==0){
      possibleNP.addLast("b");}
    if (red.getScore()==0){
      possibleNP.addLast("r");}
    if (green.getScore()==0){
      possibleNP.addLast("g");}
    if (yellow.getScore()==0){
      possibleNP.addLast("y");}
   
    while (flag != false){
      System.out.println("The possible next players are: " + possibleNP.toString());
      System.out.println("Who do you want to play? Answer: b ; r ; g ; y");
      input = scanner.nextLine();
      if (possibleNP.indexOf(input) != -1){
        flag = false;}
    }
    
    int currentPlayerInt = blue.convertInt(input); 
    // what's going on here? Do we need a for loop? 
    // create move of current set up and feed that into minimax.minimax ??
    allMoves = (LinkedList) minimax.getAllMoves(table, players, currentPlayerInt).clone();
    System.out.println(allMoves.size());
    allMoves.get(3).display();
    int result = minimax.minimax(allMoves.get(3));
    System.out.println(result);
    
  }
}