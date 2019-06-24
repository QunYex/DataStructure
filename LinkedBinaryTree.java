public interface BinaryTree<E> extend Tree<E>{
    Position<E> left(Position<E> p) throws IllegalStateException;
    Position<E> right(Position<E> p) throws IllegalStateException;
    Position<E> sibling(Position<E> p) throws IllegalStateException;
}
public abstract class AbstractBinaryTree<T> extends AbstractTree<E> implements BinaryTree<E>{
    public Position<E> sibling(Position<E> p){
        Position<E> parent = parent(p);
        if(parent == null) return null;
        if(p == left(parent))
            return right(parent);
        else
            return left(parent);
    }
    public int numChildren(Position<E> p){
        int count = 0;
        if(left(p) != null){
            count++;
        }
        if(right(p) != null)
            count++;
        return count;
    }
    public Iterable<Position<E>> children(Position<E> p){
        List<Position<E>> snapshot = new ArrayList<>(2);
        if(left(p) != null){
            snapshot.add(left(p));
        }
        if(right(p) != null){
            snapshot.add(right(p));
        }
        return snapshot;
    }
    
}
public class LinkedBinaryTree<E> extend AbstractTree<E>{

    //-------------nested Node class--------------------
    protected static class Node<E> implements Position<E> {
        private E element;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;

        /**Constructs a node with the given element and neighbors. */
        public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild){
            element = e;
            parent = above;
            left = leftChild;
            right = rightChild;
        }
        //accessor methods
        public E getElement(){return element;}
        public Node<E> getParent(){return parent;}
        public Node<E> getLeft(){return left;}
        public Node<E> getRight(){return right;}
        public void setElement(E e){ element = e;}
        public void setParent(Node<E> parentNode){parent = parentNode;}
        public void setLeft(Node<E> leftChild){left = leftChild;}
        public void setRight(Node<E> rightChild){right = rightChild;}

        protected Node<E> createNode(E e, Node<E> parent, Node<E> left,Node<E> right){
            return new Node<E>(e,parent, left, right);
        }
    }
    protected Node<E> root = null;
    private size = 0;

    public LinkedBinaryTree(){}
    protected Node<E> validate(Position<E> p) throws IllegalStateException{
        if(!(p instanceof Node)) throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p;
        if(node.getParent() == node) throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }

    public int size(){return size;}
    public Position<E> root(){return root;}
    public Position<E> parent(Position<E> p) throws IllegalArgumentException{
        Node<E> node = validate(p);
        return node.getParent();
        }
    public Position<E> left(Position<E> p) throws IllegalArgumentException{
        Node<E> node = validate(p);
        return node.getLeft();
    }
    public Position<E> right(Position<E> p) throws IllegalArgumentException{
        Node<E> node = validate(p);
        return node.getRight();
    }
        public Position<E> addRoot(E e) throws IllegalArgumentException{
            if(!isEmpty()) throw new IllegalArgumentException("Tree is not empty");
            root = createNode(e,null,null,null);
            size = 1;
            return root;
        }

        public Position<E> addLeft(Position<E> p,E e) throws IllegalArgumentException{
            Node<E> parent = validate(p);
            if(parent.getLeft()!=null) throw new IllegalArgumentException("p already has a left child");
            Node<E> child = createNode(e, parent,null,null);
            parent,setLeft(child);
            size++;
            return child;
        }

        public Position<E> addRight(Position<E> p,E e) throws IllegalArgumentException{
            Node<E> parent = validate(p);
            if(parent.getRight()!=null) throw new IllegalArgumentException("p already has a right child");
            Node<E> child = createNode(e, parent,null,null);
            parent,setRight(child);
            size++;
            return child;
        }

        public E set(Position<E> p, E e) throws IllegalArgumentException{
            Node<E> node = validate(p);
            E temp = node.getElement();
            node.setElement(e);
            return temp;
        }

        public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException{
            Node<E> node = validate(p);
            if(isInternal(p)) throw new IllegalArgumentException("p mus be a leaf");
            size += t1.size()+t2.size();
            if(!t1.isEmpty()){
                t1.root.setParent(node);
                node.setLeft(t1.root);
                t1.root = null;
                t1.size = 0;
            }
            if(!t2.isEmpty()){
                t2.root.setParent(node);
                node.setRight(t2.root);
                t2.root = null;
                t2.size = 0;
            }
        }

        /** Removes the node at Position p and replaces it with its child, if any */
        public E remove(Position<E> p) throws IllegalArgumentException{
            Node<E> node = validate(p);
            if(numChildren(p)==2) throw new  IllegalArgumentException("p has two children");
            Node<E> child = (node.getLeft() != null? node.getLeft() : node.getRight());
            if(child != null){
                child.setParent(node.getParent());
            }
            if(node == root){
                root = child;
            }else{
                Node<E> parent = node.getParent();
                if(node == parent.getLeft()){
                    parent.setLeft(child);
                }else{
                    parent.setRight(child);
                }
            }
            size--;
            E temp = node.getElement();
            node.setElement(null);
            node.setLeft(null);
            node.setRight(null);
            node.setParent(node);
            return temp;
        }
        private class ElementIterator implements Iterator<E>{
            Iterator<Position<E>> posIteratot = positions().Iterator();
            public boolean hasNext(){return posIteratot.hasNext();}
            public E next(){return posIteratot.next().getElement();}
            public void remove(){posIteratot.remove();}
        }
        public Iterator<E> iterator(){return new ElementIterator();}    
    }

}