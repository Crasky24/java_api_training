package fr.lernejo.navy_battle.Serveur;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.Partie.Game;

import java.io.IOException;


public class FireHandler implements HttpHandler {
    final public Game game;

    public FireHandler(Game game) {
        this.game = game;
    }

    public void handle(HttpExchange exchange) throws IOException {

        String schema = "{\"$schema\":\"http://json-schema.org/schema#\"," +
            "\"type\":\"object\"," +
            "\"properties\":{\"consequence\":{\"type\":\"string\"," +
            "\"enum\":[\"miss\",\"hit\",\"sunk\"]}, \"shipLeft\":{\"type\":\"boolean\"}}," +
            "\"required\":[\"consequence\",\"shipLeft\"]}";

        String consequence = "";
        String shipLeft = "";
        String reponse = "{\"consequence\":\"" + consequence + "\", \"shipLeft\":" + shipLeft + "}";
    }
}
