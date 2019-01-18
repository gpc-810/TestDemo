package com.guo.libtest;

import java.util.ArrayDeque;
import java.util.Queue;

public class TestClass {

    public static void main(String[] args) {
        ArrayDeque<Integer> a = new ArrayDeque<>();
        for (int i = 0; i < 100; i++) {
            a.add(i);
        }

        delete(a);

    }


    private static void delete(ArrayDeque<Integer> a) {
        if (a.size() <= 0) {
            return;
        }
        a.add(a.poll());
        a.add(a.poll());
        System.out.println(a.poll());
        delete(a);


    }

}
