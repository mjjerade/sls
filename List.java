public interface List<E> {
  
  boolean isEmpty();  
  int size();
  String toString();
  void add(E value, int pos);
  void addFirst(E value);
  void addLast(E value);
  boolean remove(E value);
  E removeFirst();
  E removeLast();
  E get(int pos);
  boolean equals(LinkedList<E> other);
  
}
