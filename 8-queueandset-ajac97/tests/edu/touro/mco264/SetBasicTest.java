package edu.touro.mco264;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SetBasicTest {
    @Test
    void addTest() {
        SetBasic mySet = new SetBasic();
        mySet.add("A");
        mySet.add("A");
        mySet.add("A");
        assertEquals(1, mySet.size());
    }

    @Test
    void addAllTest() {
        Set mySet = new SetBasic();
        ArrayList<Integer> al = new ArrayList();
        al.add(1);
        al.add(1);
        al.add(1);
        al.add(1);
        mySet.addAll(al);
        assertEquals(1, mySet.size());
    }


}
