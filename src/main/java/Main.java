package io.eclair.shortener;

import io.eclair.shortener.UniqueStringGenerator;

import io.eclair.jutils.map.DoubleHashMap;
import io.eclair.jutils.map.BasicDoubleHashMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting!");

        DoubleHashMap<String, String> store = new BasicDoubleHashMap<String, String>();

        store.put("A", "Hello");
        store.put("Z", "Bye");

        UniqueStringGenerator generator = new UniqueStringGenerator(store, 4);

        for (int i = 0; i < 1000; i++) {
            String random = generator.getUnique();
            store.put(random, "Wee");
            System.out.println(random);
        }
    }
}
