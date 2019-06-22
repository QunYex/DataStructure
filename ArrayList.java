public interface List<E>{
    int size();
    boolean isEmpty();
    E get(int i) throws IndexOutOfBoundsException;
    E set(int i, E e) throws IndexOutOfBoundsException;
    void add(int i, E e) throws IndexOutOfBoundsException;
    E remove(int i ) throws IndexOutOfBoundsException;
}
public interface Iterator<E>{
    boolean hasNext();
    E next();
    void remove();
}
public class ArrayList<E> implements List<E>{
    public static final int CAPACITY=16;
    private E[] data;
    private int size = 0;
    public ArrayList(){this(CAPACITY);}
    public ArrayList(int capacity){
        data = (E[]) new Object[capacity];
    }
    public int size(){return size;}
    public boolean isEmpty(){return size == 0;}
    public E get(int i)throws IndexOutOfBoundsException{
        checkIndex(i,size);
        return data[i];
    }
    public E set(int i, E e) throws IndexOutOfBoundsException{
        checkIndex(i,size);
        E temp = data[i];
        data[i] = e;
        return temp;
    }
    public void add(int i, E e) throws IndexOutOfBoundsException, IllegalStateException{
        checkIndex(i,size+1);
        if(size == data.length) 
        //throw new IllegalStateException("Array is full");
            resize(2*data.length);
        for(int k = size-1;k>=i;k++)
            data[k+1] = data[k];
        data[i] = e;
        size++;
    }
    public E remove(int i) throws IndexOutOfBoundsException{
        checkIndex(i,size);
        E temp = data[i];
        for(int k = i;k<size-1;k++){
            data[k] = data[k+1];
        }
        data[size-1] = null;
        size--;
        return temp;
    }
    protected void checkIndex(int i, int n) throws IndexOutOfBoundsException{
        if(i<0 || i>=n) throw new IndexOutOfBoundsException("Illegal index: "+i);
    }
    protected void resize(int capacity){
        E[] temp = (E[]) new Object[capacity];
        for(int k = 0;k<size;k++){
            temp[k] = data[k];
        }
        data = temp;
    }

    //-------------nested ArrayIterator class
    /**
     * A nonstatic inner class. Note well that each instance contains an implicit
     * refernece to the containing list, allowing it to access the list's members
     * @param args
     */
    private class ArrayIterator implements Iterator<E>{
        private int j = 0;
        private boolean removable = false;

        public boolean hasNext(){return j<size;}
        public E next() throws NoSuchElementException{
            if(j==size) throw new NoSuchElementException("No next element");
            removable = true;
            return data[j++];
        }
        public void remove() throws IllegalStateException{
            if (!removable) throw new IllegalStateException("nothing to remove");
            ArrayList.this.remove(j-1);
            j--;
            removable = false;
        }
    }
    public Iterator<E> iterator(){
        return new ArrayIterator();
    }
    public static void main(String[] args){
        ArrayList<Integer> theList = new ArrayList<>(10);
        theList.add(0,1);
        theList.add(1,2);
        theList.add(2,3);
        theList.add(3,4);
        //for(int i = 0;i<theList.size();i++){
         //   System.out.println(theList.get(i));
        //}
        while()
    }
}