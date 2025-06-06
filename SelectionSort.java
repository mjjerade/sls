class SelectionSort {
  
  void sort(LinkedList<String>[] rows, int[] p) {
    
    // sorting by length
    for (int i = 0; i < 7; i++) {
      
      int max_idx = i;
      for (int j = i+1; j < 8; j++){
        if ( rows[j].size() > rows[max_idx].size() ){
          max_idx = j;}}
      
      LinkedList<String> temp = new LinkedList<String>();
      temp.copy(rows[max_idx]); // copy method
      rows[max_idx].copy(rows[i]);
      rows[i].copy(temp);
    }
  }
}

// copy first row to new table
// compare first row to those of same length: if found one equal, copy it to new Table
// if not move to the next available length 
// stop when length = 0 or made it to row #7