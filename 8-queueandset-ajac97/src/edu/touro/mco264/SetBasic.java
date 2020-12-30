package edu.touro.mco264;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * An unoptimized, but fully functional Set
 *
 * @param <E>
 */
public class SetBasic<E> implements Set<E> {
    private ArrayList<E> backingArrayList;

    public SetBasic() {
        backingArrayList = new ArrayList();
    }

    @Override
    public int size() {
        return backingArrayList.size();
    }

    @Override
    public boolean isEmpty() {
        return backingArrayList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return backingArrayList.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return backingArrayList.iterator();
    }

    @Override
    public Object[] toArray() {
        return backingArrayList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return (T[]) backingArrayList.toArray(a);
    }

    @Override
    public boolean add(E e) {
        if (!backingArrayList.contains(e)) {
            return backingArrayList.add(e);
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return backingArrayList.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return backingArrayList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        int startingSize = size();
        for (E obj : c) {
            add(obj);
        }
        return startingSize != size();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return backingArrayList.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return backingArrayList.removeAll(c);
    }

    @Override
    public void clear() {
        backingArrayList.clear();
    }
}
