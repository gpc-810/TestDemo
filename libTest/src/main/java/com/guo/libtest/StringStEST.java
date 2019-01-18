package com.guo.libtest;

public class StringStEST {

    public static void main(String[] args) {
        String s = "123455";
        String wsw = "123455";
        System.out.println(s.hashCode());//1450575458
        System.out.println(s.hashCode() == wsw.hashCode());

    }
}
