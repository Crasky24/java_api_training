package fr.lernejo.navy_battle.Serveur;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.Partie.Game;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class StartHandlerTest {
    private final Game game = new Game();
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void sendResponseHeaders202() throws IOException, InterruptedException {
        HttpServer server = HttpServer.create(new InetSocketAddress(9876), 0);
        HttpClient client = HttpClient.newHttpClient();
        server.createContext("/api/game/start", new StartHandler(game, 9876));
        server.setExecutor(Executors.newFixedThreadPool(1));
        server.start();
        HttpRequest postRequest = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"0\"," +
                " \"url\":\"http://localhost:9876\",\"message\":\"Hello\"}"))
            .build();
        Assertions.assertThat(client.send(postRequest, HttpResponse.BodyHandlers.ofString()).statusCode())
            .isEqualTo(202);
        server.stop(1);
    }

    @Test
    void sendResponseHeaders404() throws IOException, InterruptedException {
        HttpServer server = HttpServer.create(new InetSocketAddress(9876), 0);
        HttpClient client = HttpClient.newHttpClient();
        server.setExecutor(Executors.newFixedThreadPool(1));
        server.start();
        HttpRequest postRequest = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{}"))
            .build();
        Assertions.assertThat(client.send(postRequest, HttpResponse.BodyHandlers.ofString()).statusCode())
            .isEqualTo(404);
        server.stop(1);
    }
}
