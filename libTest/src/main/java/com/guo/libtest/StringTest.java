package com.guo.libtest;

import java.util.Calendar;

public class StringTest {
    public static void main(String[] args) {
//        String s = "123455";
//        String wsw = "123455";
//        System.out.println(s.hashCode());//1450575458
//        System.out.println(s.hashCode() == wsw.hashCode());
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR,20);
        System.out.println(calendar.get(Calendar.AM_PM)+"");

//        for (int i = 0; i < 12; i++) {
//            System.out.println(String.format("%02d", i * 5));
//        }

    }
}
