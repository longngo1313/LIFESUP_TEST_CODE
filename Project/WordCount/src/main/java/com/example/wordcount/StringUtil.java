package com.example.wordcount;

import java.util.Map;

/**
 * Created by Admin on 11/30/2017.
 */

public class StringUtil {

    public static String getThreeStringFromList(Map<String, Integer> map) {
        int count = 0;
        StringBuilder threeWord = new StringBuilder();

        for (String word : map.keySet()) {
            threeWord.append(word);
            count++;
            if (count >= 3) {
                threeWord.append(". ");
                break;
            } else {
                threeWord.append(", ");
            }
        }
        return threeWord.toString();
    }

    public static String getFirstStringFromList(Map<String, Integer> map) {
        StringBuilder word = new StringBuilder();
        Map.Entry<String, Integer> entry = map.entrySet().iterator().next();
        word.append(entry.getKey());
        return word.toString();
    }


}
