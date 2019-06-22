public interface Position<E>{
    E getElement() throws IllegalStateException;
}
public interface PositionalList<E>{
    int size();
    boolean isEmtpy();
    Position<E> first();
    Position<E> last();
    Position<E> before(Position<E> p) throws IllegalStateException;
    Position<E> after(Position<E> p) throws IllegalStateException;
    Position<E> addFirst(E e);
    Position<E> addLast(E e);
    Position<E> addBefore(Position<E> p, E e)throws IllegalStateException;
    Position<E> addAfter(Position<E> p, E e) throws IllegalStateException;
    E set(Position<E> p, E e) throws IllegalStateException;
    E remove(Position<E> p) throws IllegalStateException;
}
public interface Iterator<E>{
    boolean hasNext();
    E next();
    void remove();
}
/**Implementation of a positional list stored as a doubly linked list */
public class LinkedPositionalList<E> implements PositionalList<E>{
    //--------------nested Node class----------------
    private static class Node<E> implements Position<E>{
        private E element;
        private Node<E> prev;
        private Node<E> next;
        public Node(E e, Node<E> p, Node<E> n){
            element = e;
            prev = p;
            next = n;
        }
        public E getElement() throws IllegalStateException{
            if(next == null) 
                throw new IllegalStateException("Position no longer valid");
            return element;
        }
        public Node<E> getPrev(){
            return prev;
        }
        public Node<E> getNext(){
            return next;
        }
        public void setElement(E e){
            element = e;
        }
        public void setPrev(Node<E> p){
            prev = p;
        }
        public void setNext(Node<E> n){
            next = n;
        }
    }

    private class PositionIterator implements Iterator<Position<E>>{
        private Position<E> cursor = first();
        private Position<E> recent = null;
        public boolean hasNext(){return (cursor != null);}
        public Position<E> next() throws NoSuchElementException{
            if(cursor == null) throw new NoSuchElementException("nothing left");
            recent = cursor;
            cursor = after(cursor);
            return recent;
        }
        public void remove() throws IllegalStateException{
            if(recent == null) throw new IllegalStateException("nothing to remove");
            LinkedPositionalList.this.remove(recent);
            recent = null;
        }
    }

    private class PositionIterable implements Iterable<Position<E>>{
        public Iterator<Position<E>> iterator(){return new PositionIterator();}
    }

    /**Returns an iterable representation of the list's positions. */
    public Iterable<Position<E>> positions(){
        return new PositionIterable();
    }
    private class ElementIterator implements Iterator<E>{
        Iterator<Position<E>> posIterator = new PositionIterator();
        public boolean hasNext(){return posIterator.hasNext();}
        public E next(){return posIterator.next().getElement();}
        public void remove(){posIterator.remove();}
    }
    public Iterator<E> iterator(){return new ElementIterator();}
    //insrance variables of the linkedPositinalList
    private Node<E> header;
    private Node<E> trailer;
    private int size = 0;

    /**Construct a new empty list */
    public LinkedPositionalList(){
        header = new Node<>(null,null,null);
        trailer = new Node<>(null,header,null);
        header.setNext(trailer);
    }

    //private utilities
    /**Validates the position and returns it as a node */
    private Node<E> Validate(Position<E> p) throws IllegalStateException{
        if(!(p instanceof Node)) throw new IllegalStateException("Invalid p");
        Node<E> node = (Node<E>) p;
        if(node.getNext() == null) throw new IllegalStateException("p is no longer in the list");
        return node;
    }

    /**Returns the given node as a Position(or null, if it is a sentinel) */
    private Position<E> position(Node<E> node){
        if(node == header || node == trailer)
            return null;
        return node;
    }
    //public acessor methods
    /**Return the number of elements in the linked list */
    public int size(){return size;}
    /**Test whether the linked list is empty */
    public boolean isEmtpy(){return size == 0;}
    /**Return the first position in the linked list(or null, if empty) */
    public Position<E> first(){
        return position(header.getNext());
    }
    /**return the last position in the linked list */
    public Position<E> last(){
        return position(trailer.getPrev());
    }
    /**returns the position immediately before position p(or null, if p is first) */
    public Position<E> before(Position<E> p) throws IllegalStateException{
        Node<E> node = Validate(p);
        return position(node.getPrev());
    }
    /** Returns the Position immediately after Position p (or null, if p is last).*/
    public Position<E> after(Position<E> p) throws IllegalStateException{
        Node<E> node = Validate(p);
        return position(node.getNext());
    }
    private Position<E> addBetween(E e, Node<E> pred,Node<E> succ){
        Node<E> newest = new Node<>(e,pred,succ);
        pred.setNext(newest);
        succ.setPrev(newest);
        size++;
        return newest;
    }
    public Position<E> addFirst(E e){
        return addBetween(e,header,header.getNext());
    }
    public Position<E> addLast(E e){
        return addBetween(e,trailer.getPrev(),trailer);
    }
    public Position<E> addBefore(Position<E> p, E e) throws IllegalStateException{
        Node<E> node = validates(p);
        return addBetween(e,node.getPrev(),node);
    }
    public Position<E> addAfter(Position<E> p, E e) throws IllegalStateException{
        Node<E> node = validates(p);
        return addBetween(e,node,node.getNext());
    }
    public E set(Position<E> p, E e) throws IllegalStateException{
        Node<E> node = validates(p);
        E answer = node.getElement();
        node.setElement(e);
        return answer;
    }
    public E remove(Position<E> p) throws IllegalStateException{
        Node<E> node = validates(p);
        Node<E> predecessor = node.getPrev();
        Node<E> successor = node.getNext();
        predecessor.setNext(successor);
        successor.setPrev(predecessor);
        size--;
        E answer = node.getElement;
        node.setElement(null);
        node.setNext(null);
        node.setPrev(null);
        return answer;
    }
    public static void main(String[] args){
        LinkedPositionalList<Integer> theList = new LinkedPositionalList<>();
        theList.addFirst(1);
        theList.addFirst(2);
        theList.addFirst(3);
        theList.addFirst(4);
        theList.addFirst(5);
        theList.addFirst(6);
        theList.addFirst(7);
        System.out.println("hello, world");
    }
}