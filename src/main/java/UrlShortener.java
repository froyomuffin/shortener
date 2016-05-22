package io.eclair.shortener;

import io.eclair.jutils.map.DoubleHashMap;
import io.eclair.jutils.map.BasicDoubleHashMap;

import io.eclair.shortener.UniqueStringGenerator;

public class UrlShortener {
    private DoubleHashMap<String, String> store;
    private UniqueStringGenerator generator;

    public UrlShortener() {
        this.store = new BasicDoubleHashMap<String, String>();
        this.generator = new UniqueStringGenerator(store, 4);
    }

    public String getLongUrl(String shortUrl) {
        return store.get(shortUrl);
    }

    public String getShortUrl(String longUrl) {
        String shortUrl = store.getKey(longUrl);

        if (shortUrl == null) {
            shortUrl = generator.getUnique();
        }

        return shortUrl;
    }

    // TODO: URL Stripping and padding functions
}
