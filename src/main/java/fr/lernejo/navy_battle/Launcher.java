package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.Partie.Game;
import fr.lernejo.navy_battle.Serveur.Callping;
import fr.lernejo.navy_battle.Serveur.FireHandler;
import fr.lernejo.navy_battle.Serveur.PostRequest;
import fr.lernejo.navy_battle.Serveur.StartHandler;

public class Launcher {
    public static void main(String[] args) throws IOException, InterruptedException {

        if (args.length < 1) {
            System.out.println("Entrer un port");
            return;
        }

        Game game = new Game();
        int port = Integer.parseInt(args[0]);

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/ping", new Callping());
        server.createContext("/api/game/start", new StartHandler(game, port));
        server.createContext("/api/game/fire", new FireHandler(game));
        server.setExecutor(Executors.newFixedThreadPool(1));
        server.start();

        if(args.length == 2) {
            PostRequest postRequest = new PostRequest(port);
            postRequest.sendPostRequest(args[1]);
        }
    }
}
