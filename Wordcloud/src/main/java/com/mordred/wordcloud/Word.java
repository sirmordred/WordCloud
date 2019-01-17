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
        this(word, wordSize, Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), wordColor, 255);
    }

    public Word(String word, float wordSize, int wordColor,  int wordColorAlpha) {
        this(word, wordSize, Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), wordColor, wordColorAlpha);
    }

    public Word(String word, float wordSize, Typeface wordTypeFace, int wordColor) {
        this(word, wordSize, wordTypeFace, wordColor, 255);
    }

    public Word(String word, float wordSize, Typeface wordTypeFace, int wordColor, int wordColorAlpha) {
        this.word = word;
        this.wordSize = wordSize;

        // init Paint object
        wordPaint = new Paint();
        wordPaint.setAntiAlias(true);
        wordPaint.setColor(wordColor);
        wordPaint.setAlpha(wordColorAlpha);
        wordPaint.setTextAlign(Paint.Align.LEFT);
        wordPaint.setStyle(Paint.Style.FILL);
        wordPaint.setTypeface(wordTypeFace);
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

    public float getX() { // x pos for drawing into canvas
        return (float) wordRect.left;
    }

    public float getY() { // y pos for drawing into canvas
        return wordRect.top + wordRect.height() - Math.round(wordPaint.descent() / 2); // TODO improve calibration
    }
}
