package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

public class Launcher {
    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.out.println("Entrer le port");
            return;
        }
        int port = Integer.parseInt(args[0]);

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.setExecutor(Executors.newFixedThreadPool(1));
        server.createContext("/ping", new Callping());
        server.createContext("/api/game/start", new PostHandler());
        server.start();
    }
}
