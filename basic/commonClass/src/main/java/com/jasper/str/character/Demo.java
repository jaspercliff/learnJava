package com.jasper.str.character;

/**
 * @author jasper
 * @since 2026-04-30 10:46:17
 */
public class Demo {
    public static void main(String[] args) {
        String word = "USA";
        char[] charArray = word.toCharArray();
        boolean upperCase = Character.isUpperCase(charArray[1]);
        System.out.println("upperCase = " + upperCase);
        boolean lowerCase = Character.isLowerCase(charArray[0]);
        System.out.println("lowerCase = " + lowerCase);
    }
}
