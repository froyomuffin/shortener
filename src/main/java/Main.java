package io.eclair.shortener;

import io.eclair.shortener.UniqueStringGenerator;

import io.eclair.jutils.map.DoubleHashMap;
import io.eclair.jutils.map.BasicDoubleHashMap;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Starting!");

        Server server = new Server("short.url", new InetSocketAddress("127.0.0.1", 10000));
        server.start();
    }
}
