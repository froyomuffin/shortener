package io.eclair.shortener;

import io.eclair.shortener.UrlShortener;
import io.eclair.shortener.RedirectHandler;
import io.eclair.shortener.RegistrationHandler;

import java.io.IOException;
import java.io.OutputStream;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class Server {
    private UrlShortener shortener;
    private HttpServer server;

    public Server(String host, InetSocketAddress socket) throws IOException {
        this.shortener = new UrlShortener(host);

        this.server = HttpServer.create(socket, 0);
        this.server.createContext("/", new RedirectHandler(this.shortener));
        this.server.createContext("/shorten", new RegistrationHandler(this.shortener));
        this.server.setExecutor(null);
    }

    public void start() {
        server.start();
    }

    public void stop() {
        server.stop(100);
    }
}
