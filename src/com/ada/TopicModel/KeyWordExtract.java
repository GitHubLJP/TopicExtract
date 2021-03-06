package com.ada.TopicModel;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ljp on 12/21/15.
 *
 */

public class KeyWordExtract {
    private static String[] POG;
    private static String dictionary;
    private static double initRadit;
    private final int flagCompare = 2;

    KeyWordExtract() {
        dictionary = "街路号里区边";
        initRadit = 0.5;
    }

    KeyWordExtract(String[] pog, String dic, double ir) {
        POG = pog;
        dictionary = dic;
        initRadit = ir;
    }

    boolean isFeature(char c) {
        if(c - '0' >= 0 && c - '0' <= 9 || (c == '-') ||
                (c - 'a' >= 0 && c - 'a' < 26 || (c - 'A' >= 0 && c - 'Z' < 26)))
            return true;
        return false;
    }

    boolean isLeftBracket(char c) {
        if(c == '('|| c == '（')
            return true;
        return false;
    }

    boolean isRightBracket(char c) {
        if(c == '）' || c == ')')
            return true;
        return false;
    }

    boolean isInDictionary(char c) {
        for(int i = 0; i < dictionary.length(); i++) {
            if(dictionary.charAt(i) == c)
                return true;
        }
        return false;
    }

    boolean isEndCompare(char c,char b) {
        if(isInDictionary(c) || c != b || isFeature(c))
            return true;
        return false;
    }

    String groupExtract(String[] group) {
        int countMax = 0;
        int max= 0;
        String goal = null;
        for(int i = 0; i < group.length; i++) {
            for(int j = 0; j < group.length; j++) {
                if(i != j && group[i]!= null && group[j]!= null) {
                    if(group[j].indexOf(group[i]) != -1) {
                        countMax++;
                    }
                }
            }
            if(max < countMax) {
                max = countMax;
                goal = group[i];
            }
            countMax = 0;
        }
        return goal;
    }

    String comStr(String str1, String str2) {
        //Common String between str1 and str2
        String common = null;

        int beginIndex, endIndex;
        int  countCommonChar;

        for(int i = str1.length() - 1; i >= 0; i--) {

            //Init var in loop
            countCommonChar = 0;
            for (int j = str2.length() - 1; j >= 0; j--) {
                if (!isFeature(str1.charAt(i)) && str1.charAt(i) == str2.charAt(j)) {
                    endIndex = i + 1;
                    int m = i,n = j;
                    if(isRightBracket(str1.charAt(m))) {
                        for( ;  (!isLeftBracket(str1.charAt(m))) && (m > 0 && n> 0) ; m--, n--) {
                            if(str1.charAt(m) == str2.charAt(n))
                                countCommonChar++;
                            else break;
                        }
                    }
                    for ( ; !isEndCompare(str1.charAt(m),
                            str2.charAt(n)) && (m > 0 && n > 0); m--, n--) {
                        //   System.out.println(m + " " + n);
                        countCommonChar++;
                    }

                    beginIndex = m + 1;
                    if( countCommonChar > flagCompare) {
                        common = str1.substring(beginIndex, endIndex);
                        return common;
                    } else {
                        break;
                    }
                } else if(isFeature(str1.charAt(i))){
                    break;
                } else if(str1.charAt(i) != str2.charAt(j))
                    continue;
            }
        }
        return common;
    }

    String keyWord() {
        String goal;
        Random random = new Random();
        int sampleNumber = (int)Math.ceil(POG.length * initRadit);
        sampleNumber /= 2;

        ArrayList<String> POGTmp = new ArrayList<>();
        //System.out.println(sampleNumber);

        if(POG.length <= 2) {
            return POG[0];
        }
        for(int i = 0; i < sampleNumber; i++) {
            int randomIndex1 = Math.abs(random.nextInt()) % POG.length;
            int randomIndex2 = Math.abs(random.nextInt()) % POG.length;

            String sampleString1 = POG[randomIndex1];
            String sampleString2 = POG[randomIndex2];

            //逆序求出最近相同字串
            String tmp = comStr(sampleString1, sampleString2);
            POGTmp.add(tmp);
        }
        String[] group = POGTmp.toArray(new String[0]);
        goal = groupExtract(group);

        return goal;
    }
}
