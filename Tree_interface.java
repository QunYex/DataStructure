public interface Position<E>{
    E getElement() throws IllegalStateException;

}
public interface Tree<E> extends Iterable<E>{
    Position<E> root();
    Position<E> parent(Position<E> p) throws IllegalStateException;
    Iterable<Position<E>> children(Position<E> p) throws IllegalStateException;
    int numChildren(Position<E> p) throws IllegalStateException;
    boolean isInternal(Position<E> p) throws IllegalStateException;
    boolean isExternal(Position<E> p) throws IllegalStateException;
    boolean isRoot(Position<E> p) throws IllegalStateException;
    int size();
    boolean isEmpty();
    Iterator<E> Iterator();
    Iterable<Position<E>> positions();
}

public abstract class AbstractTree<E> implements Tree<E>{
    public boolean isInternal(Position<E> p){return numChildren(p) > 0;}
    public boolean isExternal(Position<E> p){return numChildren(p) ==0;}
    public boolean isRoot(Position<E> p){return p == root();}
    public boolean isEmpty(){return size() == 0;}

    /*Returns the number of levels separating position p from the root */
    public int depth(Position<E> p){
        if(isRoot(p)){
            return 0;
        }else{
            return 1+depth(parent(p));
        }
    }
    /*Rerurn the Height of tree*/
    private int heightBad(){
        int h = 0;
        for(Position<E> p:positions()){
            if(isExternal(p))           // only consider leaf positions
                h = Math.max(h,depth(p));
        }
        return h;
    }
    public int height(Position<E> p){
        int h =0;
        for(Position<E> c: children(p)){
            h = Math.max(h,1+height(c));
        }
        return h;
    }
}