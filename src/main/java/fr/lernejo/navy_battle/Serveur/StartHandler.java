package fr.lernejo.navy_battle.Serveur;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.Partie.Game;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.OutputStream;

public class StartHandler implements HttpHandler {
    final public Game game;
    private final int myPort;

    public final String schema = "{ \"$schema\": \"http://json-schema.org/schema#\", " +
        " \"type\":\"object\"," +
        " \"properties\": {\"id\": {\"type\": \"string\"}, " +
        "                  \"url\": {\"type\": \"string\"}, " +
        "                  \"message\": {\"type\": \"string\"}}, " +
        " \"required\": [\"id\", \"url\", \"message\"]}";

    public StartHandler(Game game, int port) {
        this.game = game;
        this.myPort = port;
    }

    public int ValidationSchema(String request) {
        JSONTokener TokenSchema = new JSONTokener(schema);
        JSONTokener TokenRequest = new JSONTokener(request);
        JSONObject ObjSchema = new JSONObject(TokenSchema);
        JSONObject ObjRequest = new JSONObject(TokenRequest);
        Schema schemaValidator = SchemaLoader.load(ObjSchema);
        try {
            schemaValidator.validate(ObjRequest);
            return 1;
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
            e.getCausingExceptions().stream().map(ValidationException::getMessage).forEach(System.out::println);
            return 0;
        }
    }

    public String Reponse() {
        String reponse = "{\"id\":\"0\", \"url\":\"http://localhost:" + myPort + "\", " +
            "\"message\":\"I will crush you !\"}";
        return reponse;
    }

    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("POST")) {
            String reponse = Reponse();
            if (ValidationSchema(reponse) == 1) {
                exchange.sendResponseHeaders(202, reponse.length());
            } else {
                exchange.sendResponseHeaders(400, reponse.length());
            }
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(reponse.getBytes());
            }
            System.out.println("La Partie commence");
            FireHandler fire = new FireHandler(this.game);
        }
    }
}
