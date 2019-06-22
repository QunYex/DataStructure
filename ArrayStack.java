public interface Stack<E>{

    public void push(E e);
    public E pop();
    public E top();
    public int size();
    public boolean isEmpty();
}
public class ArrayStack<E> implements Stack<E>{
    public static final int CAPACITY = 1000;
    private E[] data;
    private int t = -1;
    public ArrayStack(){this(CAPACITY);}
    public ArrayStack(int capacity){
        data = (E[]) new Object[capacity];
    }
    public int size(){return t+1;}
    public boolean isEmpty(){return t == -1;}
    public void push(E e) throws IllegalStateException{
        if(size() == data.length) throw new IllegalStateException("Stack is full");
        data[++t] = e;
    }
    public E top(){
        if(isEmpty()) return null;
        return data[t];
    }
    public E pop(){
        if(isEmpty()) return null;
        E answer = data[t];
        data[t] = null;
        t--;
        return answer;
    }
    public static void main(String[] args){
        ArrayStack<Integer> theStack = new ArrayStack<Integer>();
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