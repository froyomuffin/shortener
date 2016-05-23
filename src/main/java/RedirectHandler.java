package io.eclair.shortener;

import io.eclair.shortener.UrlShortener;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class RedirectHandler implements HttpHandler {
    private UrlShortener shortener;

    public RedirectHandler(UrlShortener shortener) {
        this.shortener = shortener;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response;

        exchange.getResponseHeaders().add("Cache-Control", "private");
        exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");

        String shortUrl = exchange.getRequestURI().toString();
        String longUrl = shortener.getLongUrl(shortUrl);

        if (longUrl != null) {
            System.out.println("Found " + shortUrl + " -> " + longUrl);
            response = "";
            exchange.getResponseHeaders().add("Location", longUrl);
            exchange.sendResponseHeaders(301, response.length());
        } else {
            System.out.println("Could not find shortUrl: " + shortUrl);
            response = "Not found";
            exchange.sendResponseHeaders(404, response.length());
        }

        OutputStream os = exchange.getResponseBody();

        os.write(response.getBytes());
        os.close();
    }
}
