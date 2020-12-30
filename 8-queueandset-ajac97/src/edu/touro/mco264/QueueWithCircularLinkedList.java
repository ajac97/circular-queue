package edu.touro.mco264;

import java.util.*;

/**
 * A dynamic queue implemented using a circular array
 *
 * @param <E>
 */

public class QueueWithCircularLinkedList<E> implements Queue<E> {
    private static class Node<E> {
        E data;
        Node<E> prev, next;

        public Node() {
        }

        public Node(E data, Node<E> prev, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

    }

    private Node<E> head, tail;
    private int size;

    public QueueWithCircularLinkedList() {
        head = tail = new Node<E>();
        tail.next = head;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        // for (Iterator<T> i = this.iterator(); i.hasNext();) - non "syntactic sugar" way of writing for each
        for (Object obj : this) {
            if (obj.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyQueueIterator();
    }

    // Principle of Least Privilege - grant as little access as needed
    // static inner class is when inner class has no need to access outer class
    // and is placed in outer class to help organize code

    private class MyQueueIterator implements Iterator<E> { // inner class (static inner classes vs nonstatic inner classes)

        private Node<E> prevPtr = head;
        private boolean nextWasCalled, removeWasCalled;

        @Override
        public boolean hasNext() {
            return this.prevPtr.next != null;
        }

        @Override
        public E next() { // returns next data elt, and updates the ptr
            if (!hasNext())
                throw new NoSuchElementException();
            nextWasCalled = true;
            removeWasCalled = false; // allow remove to be called again
            // incr iterator;
            prevPtr = prevPtr.next;
            return prevPtr.data;   // return data elt that was next
        }

        public void remove() {
            if (!nextWasCalled) {
                throw new IllegalStateException("Cannot call remove unless next called first");
            }
            if (removeWasCalled) {
                throw new IllegalStateException("Cannot call remove twice unless next was called first");
            }
            removeWasCalled = true;
            // remove elt last returned by this iterator
            Node<E> beforeCurrent = prevPtr.prev;
            Node<E> afterCurrent = prevPtr.next;

            beforeCurrent.next = afterCurrent;
            afterCurrent.prev = beforeCurrent;
            size--;
        }
    }

    private void checkNull(Collection<?> c) {
        for (Object o : c) {
            if (o == null) {
                throw new NullPointerException("The values in the collection cannot be null");
            }
        }
    }

    private void checkBoundaries(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException("The index provided is out of the bounds of this LinkedList");
        }
    }

    @Override//TODO
    public Object[] toArray() {
        Object[] arr = new Object[size()];
        int i = 0;
        for (Object o : this) {
            arr[i++] = o;
        }
        return arr;
    }

    @Override//TODO
    public <T1> T1[] toArray(T1[] a) {
        if (a.length >= size) {
            for (int i = 0; i < a.length; i++) {
                while (i < size) {
                    a[i] = (T1) get(i);
                }
                a[i] = null;
            }
            return a;
        }
        return (T1[]) toArray();


    }

    private Node<E> getNode(int index) {
        checkBoundaries(index);
        Node<E> currentNode = head.next;
        for (int counter = 0; counter < index; currentNode = currentNode.next, counter++)
            ; // BLANK
        return currentNode;
    }

    public E get(int index) {
        return getNode(index).data;
    }

    public E set(int index, E t) {
        Node<E> node = getNode(index);
        E oldData = node.data;
        node.data = t;
        return oldData;
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

    @Override//
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        checkNull(c);
        for (E o : c) {
            add(o);
        }
        return true;
    }


    @Override//
    public boolean removeAll(Collection<?> c) {
        checkNull(c);
        if (c.isEmpty()) {
            return false;
        }
        int startingSize = size();
        for (Object o : c) {
            remove(o);
        }
        return (startingSize != size());
    }

    @Override//
    public boolean retainAll(Collection<?> c) {
        checkNull(c);
        if (c.isEmpty()) {
            return false;
        }
        int startingSize = size();
        for (Iterator<E> i = this.iterator(); i.hasNext(); ) {
            E e = i.next();
            if (!c.contains(e)) {
                i.remove();
            }
        }
        return (startingSize != size());
    }

    @Override
    public void clear() {
        head = tail = new Node<>(); // disconnected nodes will be garbage collected
        size = 0;
    }

    @Override
    public boolean add(E e) {
        Node<E> newNode = new Node<>(e, tail, head); //set the newNode.next to head to make this circular, although basically pointless in my opinion.
        tail.next = newNode; // old last node point to new
        tail = newNode; // or tail = tail.next // tail point to new Node
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int startingSize = size();
        remove(indexOf(o));
        return (startingSize != size());
    }

    public E remove(int index) {
        Node<E> node = getNode(index);
        E oldValue = node.data;
        Node<E> beforeNode = node.prev;
        Node<E> afterNode = node.next;
        beforeNode.next = afterNode;
        if (afterNode == null) {
            tail = beforeNode;
        } else {
            afterNode.prev = beforeNode;
        }
        size--;
        return oldValue;

    }


    @Override
    public boolean offer(E e) {
        Node<E> newNode = new Node<>(e, tail, head); //set the newNode.next to head to make this circular, although basically pointless in my opinion.
        tail.next = newNode; // old last node point to new
        tail = newNode; // or tail = tail.next // tail point to new Node
        size++;
        return true;
    }

    @Override
    public E remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove item as queue is empty");
        }
        E elt = head.data;
        head = head.next;
        tail.next = head;
        return elt;
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        E elt = head.data;
        head = head.next;
        tail.next = head;
        return elt;
    }

    @Override
    public E element() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot return item as queue is empty");
        }
        return head.data;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return head.data;
    }


    public int indexOf(Object o) {
        Node<E> currentNode = head.next;
        for (int counter = 0; counter < size; currentNode = currentNode.next, counter++) {
            if (currentNode.data.equals(o)) {
                return counter;
            }

        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        Node<E> currentNode = tail;
        for (int counter = size() - 1; counter >= 0; currentNode = currentNode.prev, counter--) {
            if (currentNode.data.equals(o)) {
                return counter;
            }
        }
        return -1;
    }
}

