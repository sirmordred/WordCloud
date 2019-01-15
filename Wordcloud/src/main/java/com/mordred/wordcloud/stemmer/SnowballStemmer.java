/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mordred.wordcloud.stemmer;

public class SnowballStemmer implements Stemmer {

    private AbstractSnowballStemmer stemmer = null;
    private int repeat = 1;

    public SnowballStemmer() {
        // empty constructor
    }

    public boolean setLanguage(String lang) {
        switch (lang) {
            case "ar":
                stemmer = new arabicStemmer();
                break;
            case "da":
                stemmer = new danishStemmer();
                break;
            case "nl":
                stemmer = new dutchStemmer();
                break;
            case "en":
                stemmer = new englishStemmer();
                break;
            case "fi":
                stemmer = new finnishStemmer();
                break;
            case "fr":
                stemmer = new frenchStemmer();
                break;
            case "de":
                stemmer = new germanStemmer();
                break;
            case "el":
                stemmer = new greekStemmer();
                break;
            case "hu":
                stemmer = new hungarianStemmer();
                break;
            case "id":
                stemmer = new indonesianStemmer();
                break;
            case "ga":
                stemmer = new irishStemmer();
                break;
            case "it":
                stemmer = new italianStemmer();
                break;
            case "lt":
                stemmer = new lithuanianStemmer();
                break;
            case "ne":
                stemmer = new nepaliStemmer();
                break;
            case "nb":
                stemmer = new norwegianStemmer();
                break;
            case "pt":
                stemmer = new portugueseStemmer();
                break;
            case "ro":
                stemmer = new romanianStemmer();
                break;
            case "ru":
                stemmer = new russianStemmer();
                break;
            case "es":
                stemmer = new spanishStemmer();
                break;
            case "sv":
                stemmer = new swedishStemmer();
                break;
            case "ta":
                stemmer = new tamilStemmer();
                break;
            case "tr":
                stemmer = new turkishStemmer();
                break;
            default:
                return false;
        }
        return true;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public String stem(String word) {
        if (stemmer == null) {
            return word;
        }

        stemmer.setCurrent(word);

        for (int i = 0; i < repeat; i++) {
            stemmer.stem();
        }

        return stemmer.getCurrent();
    }
}
