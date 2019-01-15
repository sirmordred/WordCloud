package com.mordred.wordcloud;

import java.util.HashMap;

// Custom Hashmap implementation for counting inserted objects(strings in this case)
public class CountMap extends HashMap<String, CountMap.MutableInt> {

    public class MutableInt {
        private int value = 1;
        public void increment() { ++value;      }
        public int  get()       { return value; }
    }

    public void add(String key) { // custom add operation, it will both add and count words at the same time (optimization)
        MutableInt count = super.get(key);
        if (count == null) {
            super.put(key, new MutableInt());
        } else {
            count.increment();
        }
    }
}