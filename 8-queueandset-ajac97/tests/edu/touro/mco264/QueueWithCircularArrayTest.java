package edu.touro.mco264;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class QueueWithCircularArrayTest {

    @Test
    void size() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        q.add("A");
        q.add("A");
        q.add("A");
        q.add("A");
        q.add("A");
        q.add("A");
        q.add("A");
        assertEquals(q.size(), 7);

    }

    @Test
    void isEmptyTrue() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        assertTrue(q.isEmpty());

    }

    @Test
    void isEmptyFalse() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        q.add("A");
        assertFalse(q.isEmpty());

    }

    @Test
    void isFullTrue() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        q.add("0");
        q.add("1");
        q.add("2");
        q.add("3");
        q.add("4");
        q.add("5");
        q.add("6");
        q.add("7");
        q.add("8");
        q.add("9");
        assertTrue(q.isFull());//because was initialized to [10]


    }

    @Test
    void isFullFalse() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        q.add("0");
        q.add("1");
        q.add("2");
        q.add("3");
        q.add("4");
        q.add("5");
        q.add("6");
        q.add("7");
        q.add("8");
        assertFalse(q.isFull());

    }

    @Test
    void growBackingArray() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        q.add("0");
        q.add("1");
        q.add("2");
        q.add("3");
        q.add("4");
        q.add("5");
        q.add("6");
        q.add("7");
        q.add("8");
        q.add("9");
        q.add("10");//at this point, it would have had to grow bc initialized to [10]
        q.add("11");
        q.add("12");
        q.add("13");
        assertEquals(q.size(), 14);// starting from index 0;
    }

    @Test
    void containsTrue() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        q.add("a");
        q.add("b");
        q.add("c");
        assertTrue(q.contains("b"));
    }

    @Test
    void containsFalse() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        q.add("a");
        q.add("b");
        q.add("c");
        assertFalse(q.contains("z"));
    }

    @Test
    void add() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        q.add("a");
        assertEquals(q.size(), 1);
        assertFalse(q.isEmpty());
    }

    @Test
    void growBackingArray2() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        q.add("0");//rem
        q.add("1");//rem
        q.add("2");
        q.add("3");
        q.add("4");
        q.add("5");
        q.remove();
        q.remove();
        q.add("6");
        q.add("7");
        q.add("8");
        q.add("9");
        q.add("10");
        q.add("11");
        q.add("12");// should grow
        q.add("13");
        q.add("14");
        assertEquals(q.get(0), "2");
        assertEquals(q.get(1), "3");
        assertEquals(q.get(2), "4");
        assertEquals(q.get(3), "5");
    }

    @Test
    void growBackingArray3() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        q.add("0");
        q.add("1");
        q.add("2");
        q.add("3");
        q.add("4");
        q.add("5");
        q.add("6");
        q.add("7");
        q.add("8");
        q.add("9");
        q.add("10");//should grow
        q.add("11");
        q.add("12");
        q.add("13");
        q.add("14");
    }

    @Test
    public void clear() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        q.clear();
        assertTrue(q.isEmpty());
    }

    @Test
    public void remove() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        q.add("0");
        q.add("1");
        q.add("2");
        q.add("3");
        q.add("4");
        q.remove();
        q.add("5");
        q.add("6");
        q.add("7");
        q.add("8");
        q.add("9");
        q.add("10");//will move around to front of array
        assertEquals(q.get(0), "10");
    }

    @Test
    public void poll() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        q.add("0");
        q.add("1");
        q.add("2");
        q.add("3");
        q.add("4");
        q.poll();
        q.add("5");
        q.add("6");
        q.add("7");
        q.add("8");
        q.add("9");
        q.add("10");//will move around to front of array
        assertEquals(q.get(0), "10");
    }

    @Test
    public void toArray() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        q.add("0");
        q.add("1");
        q.add("2");
        q.add("3");
        q.add("4");
        q.poll();
        q.add("5");
        q.add("6");
        q.add("7");
        q.add("8");
        q.add("9");
        q.add("10");
        Object[] arr = q.toArray();
        assertEquals(arr[0], "1");
        assertEquals(arr[1], "2");
        assertEquals(arr[2], "3");
        assertEquals(arr[3], "4");
        assertEquals(arr[4], "5");
        assertEquals(arr[5], "6");
        assertEquals(arr[6], "7");
        assertEquals(arr[7], "8");
        assertEquals(arr[8], "9");
        assertEquals(arr[9], "10");
    }

    @Test
    public void addAll() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        ArrayList<Integer> al = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        q.addAll(al);
        assertEquals(q.get(0), 1);
        assertEquals(q.get(1), 2);
        assertEquals(q.get(2), 3);
    }

    @Test
    public void removeAll() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        ArrayList<Integer> al = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        q.add(8);
        q.add(4);
        q.add(5);
        q.add(7);
        q.add(6);
        q.removeAll(al);
        assertEquals(q.size(), 2);
        assertEquals(q.get(0), 8);
        assertEquals(q.get(1), 7);
    }

    @Test
    public void retainAll() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        ArrayList<Integer> al = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        q.add("A");
        q.add("B");
        q.add(1);
        q.add(3);
        q.retainAll(al);
        assertEquals(q.size(), 2);
        assertEquals(q.get(0), 1);
        assertEquals(q.get(1), 3);
    }

    @Test
    public void containsAllTrue() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        ArrayList<Integer> al = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        q.add(2);
        q.add(5);
        q.add(1);
        q.add(3);
        q.add(4);
        q.add(6);
        assertTrue(q.containsAll(al));
    }

    @Test
    public void containsAllFalse() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        ArrayList<Integer> al = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        q.add(2);
        q.add(1);
        q.add(3);
        q.add(4);
        q.add(6);
        assertFalse(q.containsAll(al));
    }

    @Test
    public void element() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        q.add("A");
        q.add("B");
        q.add("C");
        q.add("D");
        q.add("E");
        assertEquals(q.element(), "A");
    }

    @Test
    public void peek() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        q.add("A");
        q.add("B");
        q.add("C");
        q.add("D");
        q.add("E");
        assertEquals(q.peek(), "A");
    }

    @Test
    void toArray2() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        q.add("A");
        q.add("B");
        q.add("C");
        String[] toArr2 = new String[5];
        q.toArray(toArr2);
        assertEquals(toArr2[0], "A");
        assertEquals(toArr2[1], "B");
        assertEquals(toArr2[2], "C");
        assertNull(toArr2[3]);
    }

    @Test
    void toArr2NotEnuf() {
        QueueWithCircularArray q = new QueueWithCircularArray();
        q.add("A");
        q.add("B");
        q.add("C");
        Object[] notEnuf = new String[2];
        Object[] newArr;
        newArr = q.toArray(notEnuf);
        assertEquals(newArr[1], "B");
    }


}



