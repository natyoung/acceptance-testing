package org.scrumfall.webapi.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Setter;
import org.scrumfall.webapi.controller.responses.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Service
public class WalletService {
    private static final Gson GSON = new Gson();

    private final HttpClient httpClient;

    @Setter
    private String host = System.getenv("HOST_WALLET");

    @Autowired
    public WalletService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Optional<Wallet> getByUserId(String id) {
        try {
            final URI uri = new URI(host + "/wallets/" + URLEncoder.encode(id, StandardCharsets.UTF_8));
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .build();
            final HttpResponse<String> response = httpClient
                    .send(request, HttpResponse.BodyHandlers.ofString());
            final Wallet wallet = GSON.fromJson(response.body(), Wallet.class);
            return Optional.ofNullable(wallet);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Wallet> pay(String id, int amount) {
        try {
            final URI uri = new URI(host + "/wallets/" + URLEncoder.encode(id, StandardCharsets.UTF_8));

            final JsonObject requestBody = new JsonObject();
            requestBody.addProperty("amount", amount);

            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .build();
            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            final Wallet wallet = GSON.fromJson(response.body(), Wallet.class);
            return Optional.ofNullable(wallet);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
