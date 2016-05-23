package io.eclair.shortener;

import io.eclair.jutils.map.DoubleHashMap;
import io.eclair.jutils.map.BasicDoubleHashMap;

import io.eclair.shortener.UniqueStringGenerator;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlShortener {
    private DoubleHashMap<String, String> store;
    private UniqueStringGenerator generator;
    private String host;

    public UrlShortener(String host) {
        this.store = new BasicDoubleHashMap<String, String>();
        this.generator = new UniqueStringGenerator(store, 4);

        if (host.endsWith("/") == false) {
            this.host = host + "/";
        } else {
            this.host = host;
        }
    }

    // Get a long url from a short url
    public String getLongUrl(String shortUrl) {
        return store.get(getUrlKey(shortUrl));
    }

    // Get a short url from a long url
    // If a short url already exists, return it
    public String getShortUrl(String longUrl) {
        String key = store.getKey(longUrl);

        if (key == null) {
            System.out.println("Creating new shortUrl");

            key = generator.getUnique();
            store.put(key, longUrl);

            System.out.println(key + " -> " + longUrl);
        }

        return host + key;
    }

    // Helper. Get the last element in a string split by a given delimiter
    private static String getLast(String input, String delim) {
        String[] splitted = input.split(delim);

        return splitted[splitted.length - 1];
    }

    // Extract the urlKey from a shortUrl
    private static String getUrlKey(String shortUrl) {
        // TODO: Hopefully the compiler chains these. Maybe chain them manually.
        String strippedUrl = getLast(shortUrl, "://");
        String urlKey = getLast(strippedUrl, "/");

        return urlKey;
    }
}
