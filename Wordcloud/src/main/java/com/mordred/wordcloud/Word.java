/*
 *
 * Copyright (c) 2019, Oguzhan Yigit (sirmordred)
 * All rights reserved.
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * This software consists of voluntary contributions made by many individuals
 * and is licensed under the MIT license. For more information, see
 * <http://www.doctrine-project.org>.
 */

package com.mordred.wordcloud;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

public class Word {
    private String word;
    private float wordSize;
    private Rect wordRect;
    private Paint wordPaint;
    private int yOffset = 0;
    private int wordColorAlpha = 255;
    private int wordCount = 1;

    public Word(String word, int wordCount, float wordSize, int wordColor) {
        this(word, wordCount, wordSize, Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), wordColor, 255);
    }

    public Word(String word, int wordCount, float wordSize, int wordColor,  int wordColorAlpha) {
        this(word, wordCount, wordSize, Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), wordColor, wordColorAlpha);
    }

    public Word(String word, int wordCount, float wordSize, Typeface wordTypeFace, int wordColor) {
        this(word, wordCount, wordSize, wordTypeFace, wordColor, 255);
    }

    public Word(String word, int wordCount, float wordSize, Typeface wordTypeFace, int wordColor, int wordColorAlpha) {
        this.word = word;
        this.wordCount = wordCount;
        this.wordSize = wordSize;
        this.wordColorAlpha = wordColorAlpha;

        // init Paint object
        wordPaint = new Paint();
        wordPaint.setAntiAlias(true);
        wordPaint.setColor(wordColor);
        wordPaint.setAlpha(this.wordColorAlpha);
        wordPaint.setTextAlign(Paint.Align.LEFT);
        wordPaint.setStyle(Paint.Style.FILL);
        wordPaint.setTypeface(wordTypeFace);
        wordPaint.setTextSize(this.wordSize);

        // calculate rect
        wordRect = new Rect();
        wordPaint.getTextBounds(word, 0, word.length(), wordRect);
        yOffset = Math.abs(wordRect.top);
        wordRect.offsetTo(0,0);
    }

    public String getWord() {
        return word;
    }

    public int getWordCount() {
        return wordCount;
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
        return wordRect.top + yOffset;
    }

    public void changeTextSize(float newTextSize) {
        this.wordSize = newTextSize;
        this.wordPaint.setTextSize(this.wordSize);
        this.wordPaint.getTextBounds(word, 0, word.length(), wordRect);
        yOffset = Math.abs(wordRect.top);
        wordRect.offsetTo(0,0);
    }
}
