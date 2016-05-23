package io.eclair.shortener;

import io.eclair.shortener.UrlShortener;

import java.io.IOException;
import java.io.OutputStream;

import java.net.URLDecoder;

import java.util.Map;
import java.util.TreeMap;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.lang.Thread;

public class RegistrationHandler implements HttpHandler {
    private UrlShortener shortener;

    public RegistrationHandler(UrlShortener shortener) {
        this.shortener = shortener;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;

        exchange.getResponseHeaders().add("Cache-Control", "private");
        exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");

        Map<String, String> queryMap = parseQuery(exchange.getRequestURI().getQuery());

        String longUrl = queryMap.get("url");

        if (longUrl != null) {
            System.out.println("Found valid shortening request for longUrl " + longUrl);
            response = shortener.getShortUrl(longUrl);
            exchange.sendResponseHeaders(200, response.length());
        } else {
            System.out.println("Bad request on " + queryMap);
            response="Bad Request";
            exchange.sendResponseHeaders(400, response.length());
        }

        OutputStream os = exchange.getResponseBody();

        os.write(response.getBytes());
        os.close();
    }

    private Map<String, String> parseQuery(String query) {
        Map<String, String> queryMap = new TreeMap<String, String>();

        if (query != null && query != "") {
            String[] pairs = query.split("&");

            for (String pair : pairs) {
                String[] content = pair.split("=");

                if (content.length == 2) {
                    queryMap.put(URLDecoder.decode(content[0]), URLDecoder.decode(content[1]));
                }
            }

        }

        return queryMap;
    }
}
