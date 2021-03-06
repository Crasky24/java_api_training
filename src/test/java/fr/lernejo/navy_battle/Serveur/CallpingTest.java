package fr.lernejo.navy_battle.Serveur;

import com.sun.net.httpserver.HttpServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;;
import java.net.http.HttpResponse;

public class CallpingTest {

    private final Callping callping = new Callping();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testPing() throws IOException, InterruptedException {

        HttpServer server = HttpServer.create(new InetSocketAddress(9876), 0);
        server.createContext("/ping", callping);
        server.start();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/ping"))
            .build();

        Assertions.assertThat(client.send(request, HttpResponse.BodyHandlers.ofString()).body()).isEqualTo("OK");
        Assertions.assertThat(client.send(request, HttpResponse.BodyHandlers.ofString()).statusCode()).isEqualTo(200);

        server.stop(0);
    }
}
