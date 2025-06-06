//APPENDIX B
import java.io.*;
import java.util.LinkedList;

public class Table {
  
  LinkedList<String>[] rows;
  
  // constructor
  public Table (int n){
    rows = new LinkedList[n];
    for (int i = 0; i<n ; i++){
      rows[i] = new LinkedList<String>();}}
  
  // display the table
  public void display(){
    for (int i = 0; i<8; i++){
      System.out.print("Row #"+i+": ");
        for (int j = 0; j<this.rows[i].size();j++){
          System.out.print(this.rows[i].get(j));}
      System.out.println();}}
  
  // replace this Table with other
  public static Table replace(Table other){
    Table rep = new Table(8);
    for (int i = 0 ; i < 8 ; i++){
      for (int j = 0 ; j < other.rows[i].size() ; j++){
        rep.rows[i].addLast(other.rows[i].get(j));}}
    return rep;}
  
  // sort this table by length
  public void sort(int[] p) {
    
    for (int i = 0; i < 7; i++) {
      
      int max_idx = i;
      for (int j = i+1; j < 8; j++){
        if ( this.rows[j].size() > this.rows[max_idx].size() ){
          max_idx = j;}}
      
      if (i != max_idx){
        p[i] = max_idx;
        p[max_idx] = i;
        LinkedList<String> temp = new LinkedList<String>();
        for (int k = 0 ; k<this.rows[i].size() ; k++){
          temp.addLast(this.rows[i].get(k));}
        this.rows[i].clear();
        for (int k = 0 ; k<this.rows[max_idx].size() ; k++){
          this.rows[i].addLast(this.rows[max_idx].get(k));}
        this.rows[max_idx].clear();
        for (int k = 0 ; k<temp.size() ; k++){
          this.rows[max_idx].addLast(temp.get(k));}
      }}}
  
}