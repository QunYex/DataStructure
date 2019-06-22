public interface Stack<E>{

    public void push(E e);
    public E pop();
    public E top();
    public int size();
    public boolean isEmpty();
}
public class LinkedStack<E> implements Stack<E>{
    private SinglyLinkedList<E> list = new SinglyLinkedList<>();
    public LinkedStack(){}
    public int size(){return list.size();}
    public boolean isEmpty(){return list.isEmpty();}
    public void push(E element){list.addFirst(element);}
    public E top(){return list.first();}
    public E pop(){return list.removeFirst();}
    public static void main(String[] args){
        LinkedStack<Integer> theStack = new LinkedStack();
        theStack.push(1);
        theStack.push(2);
        theStack.push(4);
        theStack.push(5);
        theStack.push(3);
        theStack.push(2);
        while(!theStack.isEmpty()){
            System.out.println(theStack.pop());
        }
    }
}