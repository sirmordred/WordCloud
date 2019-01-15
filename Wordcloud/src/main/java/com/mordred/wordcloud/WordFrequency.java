package com.mordred.wordcloud;

import android.content.Context;

import com.mordred.wordcloud.stemmer.SnowballStemmer;
import com.mordred.wordcloud.stemmer.Stemmer;

import java.util.*;

public class WordFrequency {
    private static CountMap wordMap = new CountMap();
    private String document = null;
    private static List<String> stopWords = new ArrayList<>();
    private int minWordLength = 1;
    private SnowballStemmer stemmer = null;

    public WordFrequency() {
        // empty constructor
    }

    public void processAndInsertWord(String word) {
        String[] dirtyWordArr = word.split("\\s+");
        for (String dirtyWord: dirtyWordArr) {
            String cleanWord = dirtyWord.replaceAll("[^\\p{L} ]", "").toLowerCase();
            if (cleanWord.length() > minWordLength) {
                if (stemmer != null) {
                    cleanWord = stemmer.stem(cleanWord);
                }
                if (!isStopWord(cleanWord)) {
                    wordMap.add(cleanWord);
                }
            }
        }
    }

    // DONE
    private Map<String, Integer> getTopNWords(int nWord) {
        int n = 1;
        if (nWord >= 1 && nWord <= wordMap.size()) {
            n = nWord;
        } else if (nWord > wordMap.size()) {
            n = wordMap.size();
        }
        List<Map.Entry<String, CountMap.MutableInt>> entries = new ArrayList<>(wordMap.entrySet());
        Collections.sort(entries, Collections.reverseOrder(new Comparator<Map.Entry<String,
                CountMap.MutableInt>>() {
            public int compare(Map.Entry<String, CountMap.MutableInt> left,
                               Map.Entry<String, CountMap.MutableInt> right) {
                return Integer.compare(left.getValue().get(), right.getValue().get());
            }
        }));
        Map<String, Integer> sortedMap = new LinkedHashMap<>(n);
        for (int i = 0; i < n; i++) {
            sortedMap.put(entries.get(i).getKey(), entries.get(i).getValue().get());
        }
        return sortedMap;
    }

    public List<Word> generate(int nWord) {
        if (document == null) {
            return null;
        } else {
            processAndInsertWord(document);
        }

        if (wordMap.size() == 0) {
            return null;
        }

        List<Word> finalWordList = new ArrayList<>();

        // TODO get min TF and max TF
        // TODO generate word object by giving appropriate textSize

        Map<String, Integer> finalWordMap = getTopNWords(nWord);
        for (Map.Entry<String, Integer> entry : finalWordMap.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            System.out.println(key + " == " + value);
        }
        return null;
    }

    private boolean isStopWord(String word) {
        return stopWords.contains(word);
    }

    public void setDefaultStopWords(Context ctx, String lang) { // WILL BE CALLED ONLY ONCE
        // TODO get stopword from asset, read JSON and fill stopwordlist List
        if (stopWords.size() == 0) {

        }
    }

    public void setCustomStopWords(List<String> customStopWords) {
        if (stopWords.size() == 0) {
            stopWords.addAll(customStopWords);
        }
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public void setMinWordLength(int minWordLength) {
        this.minWordLength = minWordLength;
    }

    public void setStemmer(String lang) {
        this.stemmer = new SnowballStemmer();
        this.stemmer.setLanguage(lang);
    }
}
