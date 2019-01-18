package com.guo.libtest;

import java.util.ArrayList;

public class MyClass {

    public static void main(String[] args) {

        XUNhuan a = new XUNhuan(0, 0);

        XUNhuan cc = a;
        for (int i = 1; i < 10; i++) {
            XUNhuan b = new XUNhuan(i, i);
            cc.setNext(b);
            cc = b;

        }

        cc.setNext(a);

        sout(a);

        System.out.println();
        delete(cc.next);


    }


    private static void delete(XUNhuan a) {
        if (a.hasNext()){
            System.out.println(a.next.next.number);
            a.next.next=a.next.next.next;
        }else{
            System.out.println(a.next.next.number);
            return;
        }
        a=a.next.next;
        sout(a);
        delete(a);

    }

    private static void sout(XUNhuan a){
        XUNhuan cc=a;
        System.out.print(a.number+"\t");
        do {
            a = a.next;
            System.out.print(a.number+"\t");
        } while (a.hasNext()&&a!=cc);

        System.out.println();


    }


    static class XUNhuan {
        int index;
        int number;
        XUNhuan next;

        public XUNhuan(int index, int number) {
            this.index = index;
            this.number = number;
        }

        public void setNext(XUNhuan next) {
            this.next = next;
        }

        public XUNhuan getNext() {
            return next;
        }

        public boolean hasNext() {
            if (next != null) {
                if (equals(next)) {
                    return false;
                } else {
                    return true;
                }
            }
            return false;
        }
    }


}
