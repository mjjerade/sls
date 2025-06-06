//APPENDIX B

public class LinkedList<E> implements List<E> {
  
  // definition of class Node
  private static class Node<T> {
    
    private T value;
    private Node<T> next;
    
    // constructor
    private Node( T value, Node<T> next ) {
      this.value = value;
      this.next = next;}
  }
  
  // variable
  private Node<E> head;
  
  // constructor
  public LinkedList() {
    head=null;}
  
  // returns true if this is empty
  public boolean isEmpty(){
    return this.head==null;}
  
  // returns size of LinkedList this
  public int size() {
    Node<E> p=head;
    int counter =0;
    while(p!=null){
      p=p.next;
      counter ++;}
    return counter;}
  
  // returns string of values in LinkedList this
  public String toString(){
    
    String res= "";
    Node<E> p=head;
    
    while(p!=null){
      res=res+""+ p.value;
      p=p.next;}
    
    return res;}
  
  // add an element value at position pos
  public void add(E value, int pos){
    
    if ( pos < 0) 
      return;
    
    else if(pos==0)
      head = new Node<E>(value, head);     
    
    else {Node<E> p=head;
      int currPos=0;  
      while(currPos<pos-1&& p.next!=null){
        p=p.next;
        currPos ++;}
      if(currPos==pos-1)
        p.next= new Node<E>(value, p.next);     
      else return;}}
  
  // add element value at the beginning
  public void addFirst(E value){   
    head= new Node<E>(value, head);}
  
  // add element value at the end
  public void addLast(E value){
    Node<E> newNode = new Node<E>(value, null);
    
    if( head == null)
      head= newNode;
    
    else{
      Node<E> p = head;
      while(p.next!= null){
        p=p.next;}
      p.next= newNode;}}
  
  // return element at position pos 
  public E get( int pos ) {
    
    if ( pos < 0) 
      return null;
    
    else {Node<E> p=head;
      int currPos=0;
      while(currPos<pos&& p!=null){
        p=p.next;
        currPos ++;}
      if(currPos==pos&& p!=null)
        return p.value;
      else 
        return null;}}
  
  // returns true if element value is found and removed
  public boolean remove(E value){
    
    if(head == null || value == null)
      return false;
    
    else if (head.value.equals(value)){
      //clean the memory, then delete
      Node<E> toDelete = head;
      head= head.next;
      toDelete.value =null; 
      toDelete.next =null;
      return true;}
    
    else{Node<E> p=head;
      while(p.next!=null&& !p.next.value.equals(value))
        p=p.next;
      if(p.next== null)
        return false;
      else {
        Node<E> toDelete =p.next;
        p.next= p.next.next;
        toDelete.value =null; 
        toDelete.next =null;
        return true;}} 
  }
  
  // removes and returns first element in the list
  public E removeFirst(){
    
    if(head==null)
      return null;
    
    else { Node<E> toDelete=head;
      E saved =toDelete.value;
      head=head.next;
      toDelete.value=null; 
      toDelete.next=null;
      return saved;}}
  
  // removes and returns last element in the list
  public E removeLast(){
    
    if (head== null)
      return null;
    
    if(head.next==null){
      E saved= head.value;
      head=head.next;
      return saved;}
    
    Node<E> p = head;
    while ( p.next.next != null ) {
      p = p.next;}
    E saved= p.next.value;
    p.next = null;
    return saved;}
  
  public void copy(LinkedList<E> other){
    for (int i = 0 ; i<other.size() ; i++){
      this.addLast(other.get(i));}
  }
  
  // returns true if LinkedList other and this are equal
  public boolean equals(LinkedList<E> other){
    
    if(other==null)
      return false; 
    
    Boolean equal= true;
    Node<E> p= this.head;
    Node<E> q = other.head;
    
    while (p!=null && q!=null &&equal) {
      if(q.value.equals(p.value)){
        p=p.next;
        q=q.next;}
      else equal =false;}
    
    if(!equal){
      return false;}
    
    else if(q==null && p!=null){
      return false;}
    
    else if(q!=null && p==null){
      return false;}
    
    else {
      return true;}
  }
}
