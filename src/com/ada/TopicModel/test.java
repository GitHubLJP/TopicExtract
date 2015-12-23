package com.ada.TopicModel;

/**
 * Created by ljp on 12/22/15.
 */
public class test {
    public static void main(String[] args) {
        String a = "朝阳路67号院10财满街财经中心东区10";
        String b = "朝阳路";
        System.out.println(b.charAt(0));
        b = a;
        System.out.println(b);
        for (int i = 0; i < a.length(); i++) {
            System.out.println(b.charAt(i));
        }

    }
}
