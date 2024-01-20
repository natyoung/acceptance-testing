package org.scrumfall.webapi.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Setter;
import org.scrumfall.webapi.controller.responses.Certification;
import org.scrumfall.webapi.controller.responses.CertificationPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Service
public class CertificationService {
    private static final Gson GSON = new Gson();
    private final HttpClient httpClient;
    @Setter
    private String host = "http://localhost:8082";

    @Autowired
    public CertificationService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Optional<Certification> certify(String userId, String name) {
        try {
            final URI uri = new URI(host + "/certifications");
            final JsonObject requestBody = new JsonObject();
            requestBody.addProperty("userId", userId);
            requestBody.addProperty("name", name);
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .method("POST", HttpRequest.BodyPublishers.ofString(
                            requestBody.toString())
                    )
                    .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .build();
            final HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString()
            );
            final Certification certification = GSON.fromJson(response.body(), Certification.class);
            return Optional.ofNullable(certification);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<CertificationPrice> price() {
        try {
            final URI uri = new URI(host + "/certifications/price");
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .build();
            final HttpResponse<String> response = httpClient.send(
                    request, HttpResponse.BodyHandlers.ofString()
            );
            final CertificationPrice price = GSON.fromJson(response.body(), CertificationPrice.class);
            return Optional.ofNullable(price);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
