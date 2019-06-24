public class FavoritesListMTF<E> extends FavoritesLis<E>{
    protected void moveUp(Posiition<Item<E>> p) {
        if(p != list.first())
            list.addFirst(list.remove(p));
    }

    public Iterable<E> getFavorites(int k) throws IllegalArgumentException{
        if(k<0 || k>size())
            throw new IllegalArgumentException("invalid k");
        PositionalList<Item<E>> temp = new LinkedPositionalList<>();
        for(item<E> item:list){
            temp.addLast(item);
        }
        PosiitionalLisy<E> result = new LinkedPositionalList<>();
        for(int j =0;j<k;j++){
            Posiition<Item<E>> highPos = temp.first();
            Posiition<Item<E>> walk = temp.after(highPos);
            while (walk != null) {
                if(count(walk) > count(highPos))
                    highPos = walk;
                walk = temp.after(walk);
            }
            result.addLast(value(highPos));
            temp.remove(highPos);

        }
        return result;
    }
}