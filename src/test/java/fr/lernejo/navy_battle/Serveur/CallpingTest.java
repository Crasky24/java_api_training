package fr.lernejo.navy_battle.Serveur;

import com.sun.net.httpserver.HttpServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

public class CallpingTest {

    @Test
    public void handle() throws IOException, InterruptedException {

        HttpServer server = HttpServer.create(new InetSocketAddress(9876), 0);
        server.createContext("/ping", new Callping());

        server.setExecutor(Executors.newFixedThreadPool(1));
        server.start();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9876/ping"))
            .build();

        final HttpResponse<?> response = client.send(request,
            HttpResponse.BodyHandlers.ofString());

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        server.stop(1);

    }
}
