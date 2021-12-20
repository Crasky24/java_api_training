package fr.lernejo.navy_battle.Serveur;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.Launcher;
import fr.lernejo.navy_battle.Partie.Game;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.http.HttpClient;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class PostRequestTest {
    private final Game game = new Game();
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
    @Test
    void sendPostRequest() throws IOException, InterruptedException {
        HttpServer server = HttpServer.create(new InetSocketAddress(9876), 0);
        server.createContext("/api/game/start", new StartHandler(game, 9876));
        server.setExecutor(Executors.newFixedThreadPool(1));
        server.start();
        PostRequest postRequest = new PostRequest(9876);
        String response = postRequest.sendPostRequest("http://localhost:9876");
        Assertions.assertThat(response)
            .as("Post Request return response")
            .isEqualTo("{\"id\":\"0\"," +
                " \"url\":\"http://localhost:9876\"," +
                " \"message\":\"I will crush you !\"}");
        server.stop(1);
    }
}
