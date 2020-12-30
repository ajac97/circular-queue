package edu.touro.mco264;
/*
I HAD ALREADY STARTED THE LINKEDLIST IMPLEMENTATION, SO I LEFT IT IN THE
FILE BC I FIGURED WHY NOT, BUT MY MAIN SUBMISSION IS THE ARRAY IMPLEMENTATION.
*/

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * A dynamic queue implemented using a circular array
 *
 * @param <E>
 */
public class QueueWithCircularArray<E> implements Queue<E> {
    private Object[] backingArray;
    private int rear, front;

    public QueueWithCircularArray() {
        front = rear = -1;
        backingArray = new Object[10];
    }

    public QueueWithCircularArray(int initialCapacity) {
        front = rear = -1;
        backingArray = new Object[initialCapacity];
    }

    @Override
    public int size() {
        return ((rear % backingArray.length) + 1);
    }

    public void ensureCapacity(int minCapacity) {
        if (backingArray.length < minCapacity) {
            Object[] newBackingStore = new Object[minCapacity];
            arrayCopy(newBackingStore);
        }
    }

    private Object[] arrayCopy(Object[] destinationArray) {
        if (front == 0) {
            System.arraycopy(backingArray, 0, destinationArray, 0, backingArray.length);
        } else {
            System.arraycopy(backingArray, front, destinationArray, 0, backingArray.length - front);
            System.arraycopy(backingArray, rear % backingArray.length, destinationArray, backingArray.length - front, backingArray.length - (backingArray.length - front));
        }
        return destinationArray;
    }


    private void checkBoundaries(int index) {
        if (index >= backingArray.length || index < 0) {
            throw new IndexOutOfBoundsException("The index provided is out of the bounds of the Queue");
        }
    }

    private void checkNull(Collection<?> c) {
        for (Object o : c) {
            if (o == null) {
                throw new NullPointerException("The values in the collection cannot be null");
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return rear == -1 && front == -1;
    }

    private boolean isFull() {
        return ((rear + 1) % backingArray.length) == front;

    }

    private void growBackingArray() { //need to think about a scenario when front is in the middle of the array
        Object[] newBackingStore = new Object[backingArray.length * 2 + 1];
        arrayCopy(newBackingStore);
        front = 0;
        rear = backingArray.length - 1;
        backingArray = newBackingStore;
    }


    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size(); i++) {
            if (o == null && backingArray[i] == null ||
                    o != null && o.equals(backingArray[i]))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyQueueIterator();
    }

    private class MyQueueIterator implements Iterator<E> {
        int current = -1;
        boolean nextWasCalled, removeWasCalled;

        @Override
        public boolean hasNext() {
            return current + 1 < size();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("There is no next element in this Queue");
            }
            current++;
            nextWasCalled = true;
            removeWasCalled = false;
            return (E) backingArray[current];
        }

        @Override
        public void remove() {
            if (!nextWasCalled) {
                throw new IllegalStateException("Cannot call remove unless next called first");
            }
            if (removeWasCalled) {
                throw new IllegalStateException("Cannot call remove twice unless next was called first");
            }
            removeWasCalled = true;
            QueueWithCircularArray.this.remove(current--);
        }

    }


    @Override
    public Object[] toArray() {
        Object[] toArr = new Object[backingArray.length];
        arrayCopy(toArr);
        return toArr;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        Object[] temp = new Object[backingArray.length];
        arrayCopy(temp);
        if (a.length >= size()) {
            for (int i = 0; i <= size(); i++) {
                if (i < size()) {
                    a[i] = (T) temp[i];
                }
                if (i == size()) {
                    a[i] = null;
                }
            }
            return a;
        }
        return (T[]) toArray();
    }


    @Override
    public boolean add(E e) {
        if (e == null) {
            throw new NullPointerException("Elements in the queue cannot be null");
        }
        if (isFull()) {
            growBackingArray();
        }
        if (isEmpty()) {
            front++;
        }
        backingArray[++rear % backingArray.length] = e;
        return true;
    }


    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size(); i++) {
            if (backingArray[i].equals(o)) {
                System.arraycopy(backingArray, i + 1, backingArray, i, (size() - 1) - i);
                rear--;
                return true;
            }
        }
        return false;
    }

    public Object remove(int index) {
        checkBoundaries(index);
        Object previous = backingArray[index];
        System.arraycopy(backingArray, index + 1, backingArray, index, (size() - 1) - index);
        rear--;
        return previous;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        checkNull(c);
        if (c.isEmpty()) {
            return true;
        }
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        checkNull(c);
        if (c.isEmpty()) {
            return false;
        }
        checkNull(c);
        ensureCapacity(size() + c.size());
        Object[] temp = new Object[size() + c.size()];
        Object[] cToArr = c.toArray();
        System.arraycopy(backingArray, 0, temp, 0, size());
        System.arraycopy(cToArr, 0, temp, size(), c.size());
        System.arraycopy(temp, 0, backingArray, 0, temp.length);
        rear += c.size();
        return true;

    }

    @Override//TODO
    public boolean removeAll(Collection<?> c) {
        checkNull(c);
        int size = size();
        if (c.isEmpty()) {
            return false;
        }
        for (Iterator itr = iterator(); itr.hasNext(); ) {
            if (c.contains(itr.next())) {
                itr.remove();
            }
        }

        return size != size();
    }

    @Override//TODO
    public boolean retainAll(Collection<?> c) {
        checkNull(c);
        if (c.isEmpty()) {
            return false;
        }
        int size = size();
        for (Iterator itr = iterator(); itr.hasNext(); ) {
            if (!c.contains(itr.next())) {
                itr.remove();
            }
        }
        return size != size();
    }

    @Override
    public void clear() {
        backingArray = new String[10];
        front = rear = -1;
    }

    @Override
    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException("Elements in the queue cannot be null");
        }
        if (isFull()) {
            growBackingArray();
        }
        if (isEmpty()) {
            front++;
        }
        backingArray[++rear % backingArray.length] = e;
        return true;
    }


    @Override
    public E remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("There are no items in the queue");
        }
        Object oldFirst = backingArray[front];
        if (front == rear) {
            front = rear = -1;
        } else {
            front++;
        }
        return (E) oldFirst;
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        Object oldFirst = backingArray[front];
        if (front == rear) {
            front = rear = -1;
        } else {
            front++;
        }
        return (E) oldFirst;
    }


    @Override
    public E element() {
        if (isEmpty()) {
            throw new NoSuchElementException("There are no items in the queue");
        }
        return (E) backingArray[front];
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return (E) backingArray[front];
    }

    public E get(int index) {
        checkBoundaries(index);
        return (E) backingArray[index];
    }

    public E set(int index, String element) {
        checkBoundaries(index);
        Object previous = backingArray[index];
        backingArray[index] = element;
        return (E) previous;
    }
}
