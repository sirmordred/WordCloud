package com.mordred.wordcloud;

import android.content.Context;

import com.mordred.wordcloud.stemmer.SnowballStemmer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class WordFrequency {
    private static CountMap wordMap = new CountMap();
    private String document = null;
    private static List<String> stopWords = new ArrayList<>();
    private Map<String, Integer> wordFreqList = new HashMap<>();
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

        if (wordFreqList.size() == 0) {
            wordFreqList.putAll(getTopNWords(nWord));
        }

        List<Word> finalWordList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordFreqList.entrySet()) {
            // TODO get min TF and max TF
            // TODO generate word object by giving appropriate textSize
            // TODO and fill finalWordList
            String key = entry.getKey();
            int value = entry.getValue();
            System.out.println(key + " == " + value);
        }

        return finalWordList;
    }

    private boolean isStopWord(String word) {
        return stopWords.contains(word);
    }

    public void setDefaultStopWords(Context ctx, String lang) { // WILL BE CALLED ONLY ONCE
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

                stopWords.addAll(Arrays.asList(text.toString().split(",")));
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
        this.stemmer.setLanguage(lang);
    }

    public void setWordFreqList(Map<String, Integer> defWordFreqList) {
        this.wordFreqList.putAll(defWordFreqList);
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
