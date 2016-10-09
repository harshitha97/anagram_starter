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

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();

    private ArrayList<String> wordList;
    private HashSet<String> wordSet;
    private HashMap<String,ArrayList<String>> lettersToWord;

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        wordList = new ArrayList<>();
        wordSet = new HashSet<>();
        lettersToWord = new HashMap<>();
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            String sortedWord = sortLetters(word);
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

    private String sortLetters(String word) {
        char[] alpha = word.toCharArray();
        Arrays.sort(alpha);
        String sorted = alpha.toString();
        return sorted;
    }

    public boolean isGoodWord(String word, String base) {
        return wordSet.contains(word) && !word.contains(base);
    }

    public ArrayList<String> getAnagrams(String targetWord) {

        return lettersToWord.get(sortLetters(targetWord));

     }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        
        return result;
    }

    public String pickGoodStarterWord() {
        return "stop";
    }
}