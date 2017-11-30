package com.example.wordcount;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Admin on 11/30/2017.
 */

public class WordCount {

    private static int numberWords, numberLetters, numberSymbols;

    private static Map<String, Integer> sListWords = new HashMap<>();
    private static Map<String, Integer> sListLetters = new HashMap<>();
    private static Map<String, Integer> sFirstWords = new HashMap<>();

    private static String PATH = "./WordCount/huckleberry-finn.txt";
    //    private static String NON_ASCII_REGEX = "[^\\p{ASCII}]";
    private static String UNDERSCORE_REGEX = "[^a-zA-Z0-9]";
    private static ArrayList<Character> sListAlphabet = new ArrayList<>();

    public static void main(String[] args) {

        initData();

        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {

            String data = br.readLine();

            while (data != null) {

                //Remove strange characters from a String
//                String line = data.replaceAll(NON_ASCII_REGEX, "");

                //Get list of strings from line
                String listWords[] = data.trim().toLowerCase().split("\\s+");

                //Get first word of paragraphs in line
                String firstWord = listWords[0];
                if (firstWord.length() > 0) {
                    HashMapUtil.addToHashMap(firstWord, sFirstWords);
                }

                // Analytic each words in line
                for (String singleWord : listWords) {
                    //Add word to list words map and count it
                    if (singleWord.length() > 0) {

                        numberWords++;

                        //Analytic letter in one word
                        analyticLetter(singleWord);

                        //Add to List hash map word
                        String result = singleWord.replaceAll(UNDERSCORE_REGEX, "");
                        HashMapUtil.addToHashMap(result, sListWords);
                    }
                }
                data = br.readLine();
            }


            System.out.println(numberWords + " words");
            System.out.println(numberLetters + " letters");
            System.out.println(numberSymbols + " symbols");

            if (sListWords.isEmpty() || sListLetters.isEmpty() || sFirstWords.isEmpty()) {
                return;
            }

            //Sort list words and letters in order to find the most used
            sListWords = HashMapUtil.sortByValue(sListWords);
            sListLetters = HashMapUtil.sortByValue(sListLetters);
            sFirstWords = HashMapUtil.sortByValue(sFirstWords);

            //Query all one used word from list words
            Set<String> mListOneUsedWord = HashMapUtil.getKeysByValue(sListWords, 1);
            String oneUsedString = String.join(", ", mListOneUsedWord);

            //Create string to show in screen from data
            String threeWords = StringUtil.getThreeStringFromList(sListWords);
            String threeLetters = StringUtil.getThreeStringFromList(sListLetters);
            String firstLetter = StringUtil.getFirstStringFromList(sFirstWords);

            //Print result
            System.out.println("Top three most common words: " + threeWords);
            System.out.println("Top three most common letters: " + threeLetters);
            System.out.println("\"" + firstLetter + "\"" + " is the most common first word of all paragraphs.");
            System.out.println("Words only used once:  " + oneUsedString);

            if (!sListAlphabet.isEmpty()) {
                System.out.println("Letters not used in the document " + sListAlphabet);
            } else {
                System.out.println("No letters not used in the document");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initData() {
        numberWords = 0;
        numberLetters = 0;
        numberSymbols = 0;

        for (char data : "abcdefghijklmnopqrstuvwxyz".toCharArray()) {
            sListAlphabet.add(data);
        }
    }

    private static void analyticLetter(String string) {
        for (int i = 0; i < string.length(); i++) {

            char letter = string.charAt(i);

            if (Character.isLetter(letter)) {
                numberLetters++;

                //Remove from unused list
                if (sListAlphabet.contains(letter)) {
                    sListAlphabet.remove(Character.valueOf(letter));
                }

                //Add or update to list letter
                HashMapUtil.addToHashMap(Character.toString(letter), sListLetters);

            } else if (Character.isDigit(letter)) {
                // numberOfDigit
            } else if (letter == ' ') {
                // numberOfSpace
            } else {
                numberSymbols++;
            }
        }
    }


}
