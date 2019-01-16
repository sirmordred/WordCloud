package com.mordred.wordcloud;

import android.graphics.Color;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class WordCloud {
    private List<Word> wordList;
    private int dimenWidth = 480;
    private int dimenHeight = 640;
    private int defaultWordColor = Color.BLACK;
    private int defaultBackgroundColor = Color.WHITE;

    public WordCloud() {
        wordList = new ArrayList<>();
    }

    public WordCloud(int dimenWidth, int dimenHeight) {
        wordList = new ArrayList<>();
        this.dimenWidth = dimenWidth;
        this.dimenHeight = dimenHeight;
    }

    public WordCloud(List<Word> wordList, int dimenWidth, int dimenHeight) {
        this.wordList = wordList;
        this.dimenWidth = dimenWidth;
        this.dimenHeight = dimenHeight;
    }

    public WordCloud(List<Word> wordList, int dimenWidth, int dimenHeight, int defaultWordColor, int defaultBackgroundColor) {
        this.wordList = wordList;
        this.dimenWidth = dimenWidth;
        this.dimenHeight = dimenHeight;
        this.defaultWordColor = defaultWordColor;
        this.defaultBackgroundColor = defaultBackgroundColor;
    }

    public void generate() { // TODO it will return bitmap object
        if (wordList.size() > 0) {
            for (Word w2: wordList) { // Place every rect to 0,0 for init
                w2.getWordRect().offsetTo(0,0);
            }
            for(int h = 1; h < wordList.size(); h++) { // TODO shuffle wordlist
                // if intersect increment x but if x + width >= dimenWidth then make x = 0 and increment y
                // first rect will stay in 0,0
                int y = 0; // TODO dimenHeight is still not bounded
                for (int x = 0; x < dimenWidth; x++) {
                    // set loc first
                    if (x + wordList.get(h).getWordRect().width() > dimenWidth) {
                        y++;
                        x = 0;
                        continue;
                    }
                    if (!isWordIntersectWithOthers(wordList.get(h))) {
                        break;
                    } else {
                        wordList.get(h).getWordRect().offsetTo(x, y);
                    }
                }
            }
        }
    }

    private boolean isWordIntersectWithOthers(Word w) {
        if (wordList.size() > 0) {
            for (Word wd: wordList) {
                if (!w.equals(wd) && Rect.intersects(w.getWordRect(), wd.getWordRect())) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public void addWord(Word w) {
        wordList.add(w);
    }

    public void setDefaultWordColor(int defaultWordColor) { // TODO otherwise every word will get random rgb
        this.defaultWordColor = defaultWordColor;
    }

    public void setDefaultBackgroundColor(int defaultBackgroundColor) {
        this.defaultBackgroundColor = defaultBackgroundColor;
    }
}
