package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.OutputStream;

public class PostHandler implements HttpHandler {

    public int ValidationSchema(String request) {
        String schema = "{ \"$schema\": \"http://json-schema.org/schema#\", " +
            " \"type\":\"object\"," +
            " \"properties\": {\"id\": {\"type\": \"string\"}, " +
            "                  \"url\": {\"type\": \"string\"}, " +
            "                  \"message\": {\"type\": \"string\"}}, " +
            " \"required\": [\"id\", \"url\", \"message\"]}";

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

    public void handle(HttpExchange exchange) throws IOException {
        String request = "{\"id\":\"0c575465-21f6-43c9-8a2d-bc64c3ae6248\"," +
            " \"url\":\"http://localhost:8796\"," +
            " \"message\":\"Request\"} ";

        String response = "";

        if (ValidationSchema(request) == 1) {
            response = "{\"id\":\"0c575465-21f6-43c9-8a2d-bc64c3ae6241\", \"url\":\"http://localhost:8796\", " +
                "\"message\":\"I will crush you !\"}";
            exchange.sendResponseHeaders(202, response.length());
        } else {
            exchange.sendResponseHeaders(400, 1);
        }

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
