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

import android.content.Context;

import com.mordred.wordcloud.stemmer.SnowballStemmer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class WordFrequency {
    private CountMap wordMap = new CountMap();
    private String document = null;
    private HashSet<String> stopWords = new HashSet<>();
    private int minWordLength = 1;
    private SnowballStemmer stemmer = null;

    public WordFrequency() {
        // empty constructor
    }

    public void insertWord(String word) {
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

    public Map<String, Integer> generate() {
        List<Map.Entry<String, CountMap.MutableInt>> entries = new ArrayList<>(wordMap.entrySet());
        sortAsDescending(entries);
        Map<String, Integer> sortedMap = new HashMap<>();
        for (int i = 0; i < entries.size(); i++) {
            sortedMap.put(entries.get(i).getKey(), entries.get(i).getValue().get());
        }
        return sortedMap;
    }

    // DONE
    public Map<String, Integer> generate(int nWord) {
        int n = 1;
        if (nWord >= 1 && nWord <= wordMap.size()) {
            n = nWord;
        } else if (nWord > wordMap.size()) {
            n = wordMap.size();
        }
        List<Map.Entry<String, CountMap.MutableInt>> entries = new ArrayList<>(wordMap.entrySet());
        sortAsDescending(entries);
        Map<String, Integer> sortedMap = new LinkedHashMap<>(n);
        for (int i = 0; i < n; i++) {
            sortedMap.put(entries.get(i).getKey(), entries.get(i).getValue().get());
        }
        return sortedMap;
    }

    private void sortAsDescending(List<Map.Entry<String, CountMap.MutableInt>> unsortedList) {
        Collections.sort(unsortedList, Collections.reverseOrder(new Comparator<Map.Entry<String,
                CountMap.MutableInt>>() {
            public int compare(Map.Entry<String, CountMap.MutableInt> left,
                               Map.Entry<String, CountMap.MutableInt> right) {
                return Integer.compare(left.getValue().get(), right.getValue().get());
            }
        }));
    }

    private boolean isStopWord(String word) {
        return stopWords.contains(word);
    }

    public void setDefaultStopWords(Context ctx, String lang) {
        if (stopWords.size() == 0) {
            int stopWordFileId = getStopWordFileId(lang);
            if (stopWordFileId == 0) {
                return;
            }
            StringBuilder text = new StringBuilder();
            InputStream is = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            try {
                is = ctx.getResources().openRawResource(stopWordFileId);
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);

                String line;
                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
            } catch (IOException e) {
                // empty
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }

                    if (isr != null) {
                        isr.close();
                    }

                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    // empty
                }
            }

            if (text.length() > 2) {
                text.deleteCharAt(0); // remove first [ from json
                text.deleteCharAt(text.length() - 1); // remove last [ from json

                String stopwordsArr[] = text.toString().replace("\"","").
                        split(",");
                stopWords.addAll(Arrays.asList(stopwordsArr));
            }
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
        boolean res = this.stemmer.setLanguage(lang);
        if (!res) {
            this.stemmer = null;
        }
    }

    private int getStopWordFileId(String lang) {
        switch (lang) {
            case "hy":
                return R.raw.hy;
            case "zh":
                return R.raw.zh;
            case "tr":
                return R.raw.tr;
            case "sl":
                return R.raw.sl;
            case "hu":
                return R.raw.hu;
            case "mr":
                return R.raw.mr;
            case "la":
                return R.raw.la;
            case "bn":
                return R.raw.bn;
            case "ha":
                return R.raw.ha;
            case "yo":
                return R.raw.yo;
            case "nl":
                return R.raw.nl;
            case "st":
                return R.raw.st;
            case "ja":
                return R.raw.ja;
            case "de":
                return R.raw.de;
            case "ru":
                return R.raw.ru;
            case "pl":
                return R.raw.pl;
            case "fi":
                return R.raw.fi;
            case "eo":
                return R.raw.eo;
            case "sk":
                return R.raw.sk;
            case "pt":
                return R.raw.pt;
            case "en":
                return R.raw.en;
            case "it":
                return R.raw.it;
            case "hr":
                return R.raw.hr;
            case "zu":
                return R.raw.zu;
            case "et":
                return R.raw.et;
            case "ga":
                return R.raw.ga;
            case "fr":
                return R.raw.fr;
            case "br":
                return R.raw.br;
            case "el":
                return R.raw.el;
            case "bg":
                return R.raw.bg;
            case "ro":
                return R.raw.ro;
            case "hi":
                return R.raw.hi;
            case "ca":
                return R.raw.ca;
            case "ko":
                return R.raw.ko;
            case "eu":
                return R.raw.eu;
            case "gl":
                return R.raw.gl;
            case "he":
                return R.raw.he;
            case "fa":
                return R.raw.fa;
            case "cs":
                return R.raw.cs;
            case "id":
                return R.raw.id;
            case "lv":
                return R.raw.lv;
            case "af":
                return R.raw.af;
            case "sw":
                return R.raw.sw;
            case "da":
                return R.raw.da;
            case "th":
                return R.raw.th;
            case "sv":
                return R.raw.sv;
            case "es":
                return R.raw.es;
            case "ar":
                return R.raw.ar;
            case "nb":
                return R.raw.nb;
            case "so":
                return R.raw.so;
            default:
                return 0;
        }
    }
}
