package com.wd.tech.bean;

import java.math.BigInteger;

/**
 * date:2019/2/22 19:16
 * author:赵明珠(啊哈)
 * function:练习
 */
public class Number {

    public static void main(String[] args) {

        BigInteger s = new BigInteger("1");


        for (long i = 1000; i >= 1; i--) {

            String valueOf = String.valueOf(i);
            BigInteger n = new BigInteger(valueOf);

            s = s.multiply(n);
        }
        System.out.println("1000的阶乘："+s);

        int ping = 100;

        System.out.println("瓶盖问题："+(2*ping -1));

    }
}
