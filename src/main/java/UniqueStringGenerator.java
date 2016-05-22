package io.eclair.shortener;

import io.eclair.jutils.map.DoubleHashMap;

import java.util.Random;

public class UniqueStringGenerator {
    private Random random;
    private int maxTries;
    private DoubleHashMap store;
    private int stringLength;
    private String characters;

    public UniqueStringGenerator(DoubleHashMap store, int stringLength) {
        this.random = new Random();
        this.maxTries = 100;
        this.store = store;
        this.stringLength = stringLength;
        this.characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    }

    public String getUnique() {
        for (int i = 0; i < maxTries; i++) {
            String candidate = getRandom();

            if (store.get(candidate) == null) {
                return candidate;
            }
        }

        return null;
    }

    private String getRandom() {
        char[] randomChars = new char[stringLength];

        for (int i = 0; i < stringLength; i++) {
            randomChars[i] = characters.charAt(random.nextInt(characters.length()));
        }

        return new String(randomChars);
    }
}
