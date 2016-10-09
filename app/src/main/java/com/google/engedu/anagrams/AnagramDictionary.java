package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private int wordLength = DEFAULT_WORD_LENGTH;
    private static final int MAX_WORD_LENGTH = 9;
    private Random random = new Random();

    private HashSet<String> wordSet = new HashSet<>();
    private HashMap<String,ArrayList<String>> lettersToWord = new HashMap<>();
    private HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<>();
    private ArrayList<String> wordList = new ArrayList<>();

    AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        ArrayList<String> wordMapList;

        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);

            //values and keys to sizeToWords hashmap
            if (sizeToWords.containsKey(word.length())) {
                wordMapList = sizeToWords.get(word.length());
                wordMapList.add(word);
                sizeToWords.put(word.length(), wordMapList);
            } else {
                ArrayList<String> newWordList = new ArrayList<>();
                newWordList.add(word);
                sizeToWords.put(word.length(), newWordList);
            }

            ArrayList<String> sortedList = new ArrayList<>();
            String sortedWord = sortLetters(word);

            //values and keys to lettersToWord hashmap
            if (lettersToWord.containsKey(sortedWord)){
                ArrayList<String> words = lettersToWord.get(sortedWord);
                words.add(word);
                lettersToWord.put(sortedWord,words);
            }
            else{
                ArrayList<String> listWords = new ArrayList<>();
                listWords.add(word);
                lettersToWord.put(sortedWord,listWords);
            }
        }
    }

    //checks if the entered word is a dictionary word
    //and does not contain bae word as a substring of entered word
    boolean isGoodWord(String word, String base) {
        return wordSet.contains(word) && !word.contains(base);
    }

    //gives anagrams of base word
    ArrayList<String> getAnagrams(String targetWord) {
        return lettersToWord.get(sortLetters(targetWord));
     }

    // Anagram with One Extra Letter
    private ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();
        for (char ch = 'a'; ch <= 'z'; ch++){
            if (lettersToWord.containsKey(sortLetters(word+ch))){
                temp = lettersToWord.get(sortLetters(word+ch));

            for (int i = 0; i<temp.size();i++) {
                if (!(temp.get(i).contains(word))) {
                    result.add(temp.get(i));
                }
            }}
        }
        return result;
    }
    // Anagram with Two Extra Letters - extension 1
    public ArrayList<String> getAnagramsWithTwoMoreLetters(String word) {
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();
        for (char ch = 'a'; ch <= 'z'; ch++)
            for(char ch2 = 'a';ch2<='z';ch2++){
            if (lettersToWord.containsKey(sortLetters(word+ch+ch2))){
                temp = lettersToWord.get(sortLetters(word+ch+ch2));

                for (int i = 0; i<temp.size();i++) {
                if (!(temp.get(i).contains(word))) {
                    result.add(temp.get(i));
                    }
                }
            }
        }
        return result;
    }

    //To give words of inceasing word length
  String pickGoodStarterWord() {
        int length = 0;
        ArrayList<String> temp = sizeToWords.get(wordLength);
        while (length < MIN_NUM_ANAGRAMS) {
            int sizeArrayLength = temp.size();
            int wordLocate = new Random().nextInt(sizeArrayLength);
            String word = temp.get(wordLocate);
            length = getAnagramsWithTwoMoreLetters(word).size();
            if (length >= MIN_NUM_ANAGRAMS) {
                if (wordLength < MAX_WORD_LENGTH) wordLength++;
                return word;
            } else {                        //extension 2
                temp.remove(wordLocate); //Optimize word selection by removing words that do not have enough anagrams
            }
        }
        return "stop";
    }

    //To sort letters of a word according to english alphabets
   private String sortLetters(String word) {
        char[] alpha = word.toCharArray();
        Arrays.sort(alpha);
        return Arrays.toString(alpha);
   }

}
