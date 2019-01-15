package com.mordred.wordcloud;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

public class Word {
    private String word;
    private float wordSize;
    private Rect wordRect;
    private Paint wordPaint;

    public Word(String word, float wordSize, int wordColor) {
        this.word = word;
        this.wordSize = wordSize;

        // init Paint object
        wordPaint = new Paint();
        wordPaint.setAntiAlias(true);
        wordPaint.setColor(wordColor);
        wordPaint.setTextAlign(Paint.Align.LEFT);
        wordPaint.setStyle(Paint.Style.FILL);
        wordPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        wordPaint.setTextSize(wordSize);

        // calculate rect
        wordRect = new Rect();
        wordPaint.getTextBounds(word, 0, word.length(), wordRect);
    }

    public String getWord() {
        return word;
    }

    public Paint getWordPaint() {
        return wordPaint;
    }

    public Rect getWordRect() {
        return wordRect;
    }
}
