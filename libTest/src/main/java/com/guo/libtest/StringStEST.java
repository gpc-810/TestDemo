package com.guo.libtest;

import java.util.Random;

public class StringStEST {

    public static void main(String[] args)
    {  int temp=0;
        int myarr[] = new int[12];
        Random r=new Random();
        for(int i=1;i<=10;i++)
            myarr[i]=r.nextInt(1000);
        for (int k=1;k<=10;k++)
            System.out.print(myarr[k]+",");
        for(int i=1;i<=9;i++)
            for(int k=i+1;k<=10;k++)
                if(myarr[i]>myarr[k])
                {
                    temp=myarr[i];
                    myarr[i]=myarr[k];
                    myarr[k]=temp;
                }
        System.out.println("");
        for (int k=1;k<=10;k++)
            System.out.print(myarr[k]+",");

        myarr[11]=r.nextInt(1000);
        for(int k=1;k<=10;k++)
            if(myarr[k]>myarr[11])
            {
                temp=myarr[11];
                for(int j=11;j>=k+1;j--)
                    myarr[j]=myarr[j-1];
                myarr[k]=temp;
            }
        System.out.println("");
        for (int k=1;k<=11;k++)
            System.out.print(myarr[k]+",");
    }
}
