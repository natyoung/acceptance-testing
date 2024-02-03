package org.scrumfall.webapi.contract.providers;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.MockServerConfig;
import au.com.dius.pact.consumer.junit5.PactConsumerTest;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactDirectory;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scrumfall.webapi.WebApiApplication;
import org.scrumfall.webapi.controller.responses.Certification;
import org.scrumfall.webapi.controller.responses.CertificationPrice;
import org.scrumfall.webapi.service.CertificationService;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@PactConsumerTest
@PactTestFor(providerName = "certifier", pactVersion = PactSpecVersion.V2)
@MockServerConfig(hostInterface = "localhost", port = "9090")
public class CertifierConsumerTest {
    private static final String userIdWithBalance100 = "ea4ceefe-cfe6-49e8-a36d-1a889c780bb4";

    private static final Map<String, String> headers = Map.of(
            HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE
    );

    @Pact(provider = "certifier", consumer = "web-api")
    public RequestResponsePact certify(PactDslWithProvider builder) throws JSONException {
        return builder
                .given("There are certifications")
                .uponReceiving("a certification request")
                .path("/certifications")
                .method("POST")
                .headers(headers)
                .body(
                        new PactDslJsonBody()
                                .stringType("user_id", userIdWithBalance100)
                                .stringType("name", "someone")
                )
                .willRespondWith()
                .headers(Map.of(
                        HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE + "; charset=utf-8"
                ))
                .status(200)
                .body(new PactDslJsonBody()
                        .stringType("id")
                        .stringType("name")
                        .stringType("date")
                )
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "certify")
    void testCertify(MockServer mockServer) throws IOException {
        final CertificationService service = new CertificationService(
                HttpClient.newBuilder().build()
        );
        service.setHost(mockServer.getUrl());
        Optional<Certification> certification = service.certify(userIdWithBalance100, "name");
        assertTrue(certification.isPresent());
    }

    @Pact(provider = "certifier", consumer = "web-api")
    public RequestResponsePact price(PactDslWithProvider builder) throws JSONException {
        return builder
                .given("There is a certification price")
                .uponReceiving("a query for the price")
                .path("/certifications/price")
                .method("GET")
                .headers(headers)
                .willRespondWith()
                .headers(Map.of(
                        HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE + "; charset=utf-8"
                ))
                .status(200)
                .body(new PactDslJsonBody()
                        .integerType("price")
                )
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "price")
    void testPrice(MockServer mockServer) throws IOException {
        final CertificationService service = new CertificationService(
                HttpClient.newBuilder().build()
        );
        service.setHost(mockServer.getUrl());
        Optional<CertificationPrice> price = service.price();
        assertTrue(price.isPresent());
    }
}
