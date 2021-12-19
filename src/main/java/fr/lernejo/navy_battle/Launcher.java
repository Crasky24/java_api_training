package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.Partie.Game;
import fr.lernejo.navy_battle.Serveur.Callping;
import fr.lernejo.navy_battle.Serveur.FireHandler;
import fr.lernejo.navy_battle.Serveur.StartHandler;

public class Launcher {
    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.out.println("Entrer le port");
            return;
        }
        Game game = new Game();
        int port = Integer.parseInt(args[0]);

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.setExecutor(Executors.newFixedThreadPool(1));
        server.createContext("/ping", new Callping());
        server.createContext("/api/game/start", new StartHandler(game));
        server.createContext("/api/game/fire", new FireHandler(game));
        server.start();
    }
}
