package fr.lernejo.navy_battle.Serveur;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

public class PostRequest {
    private final int myPort;

    public PostRequest(int port){ this.myPort = port; }

    public String sendPostRequest(String adversaryURL) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest postRequest = HttpRequest.newBuilder()
            .uri(URI.create(adversaryURL + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", " +
                "\"url\":\"http://localhost:" + myPort + "\"," +
                " \"message\":\"Hello\"}"))
            .build();
        HttpResponse<String> response = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
