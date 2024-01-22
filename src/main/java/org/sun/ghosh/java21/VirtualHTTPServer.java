package org.sun.ghosh.java21;

// Inspired by https://github.com/ammbra/wrapup

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.Executors;

public class VirtualHTTPServer implements HttpHandler {
    void main() throws IOException {
        var server = HttpServer.create(
                new InetSocketAddress("", 2024), 0);
        var serverAddress = server.getAddress();
        server.createContext("/", new VirtualHTTPServer());
        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        server.start();
        print(STR."""
              Server Executor Details
              Class: \{server.getExecutor().getClass()}""");
        print(STR."""
                Your Virtual Thread Server is available @
                http://\{serverAddress.getHostString()}:\{serverAddress.getPort()}""");
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, 0);
        exchange.getResponseBody().write(STR."""
        Date: \{java.time.LocalDateTime.now()}

        Hello! - From Virtual Thread Server
        I handled this request using a Virtual Thread. Details below:

        Thread Class: \{Thread.currentThread().getClass()}
        Thread Group: \{Thread.currentThread().getThreadGroup()}

        Thread Stack:

        \{Arrays.toString(Thread.currentThread().getStackTrace())}"""
                                        .getBytes());
        exchange.getResponseBody().close();
    }

    void print(String s){
        System.out.println(s);
    }
}
