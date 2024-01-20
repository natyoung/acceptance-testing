package org.scrumfall.webapi.contract.providers;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.MockServerConfig;
import au.com.dius.pact.consumer.junit5.PactConsumerTest;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.scrumfall.webapi.controller.responses.Wallet;
import org.scrumfall.webapi.service.WalletService;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@PactConsumerTest
@PactTestFor(providerName = "wallet", pactVersion = PactSpecVersion.V2)
@MockServerConfig(hostInterface = "localhost", port = "9091")
public class WalletConsumerTest {
    private static final String userIdWithBalance100 = "ea4ceefe-cfe6-49e8-a36d-1a889c780bb4";
    private static final String nonExistentUserId = "0";

    private static final Map<String, String> headers = Map.of(
            HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE
    );

    @Pact(provider = "wallet", consumer = "web-api")
    public RequestResponsePact getWallet(PactDslWithProvider builder) throws JSONException {
        return builder
                .given("I have a user_id and wallet")
                .uponReceiving("a query by user_id")
                .path("/wallets/" + userIdWithBalance100)
                .method("GET")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(expectedWalletResponseBody())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getWallet")
    void testWallet(MockServer mockServer) throws IOException {
        final WalletService service = createWalletService(mockServer);
        final Optional<Wallet> wallet = service.getByUserId(userIdWithBalance100);
        assertTrue(wallet.isPresent());
    }

    @Pact(provider = "wallet", consumer = "web-api")
    public RequestResponsePact pay(PactDslWithProvider builder) throws JSONException {
        return builder
                .given("I have a user_id and wallet")
                .uponReceiving("a balance update")
                .path("/wallets/" + userIdWithBalance100)
                .method("PATCH")
                .headers(headers)
                .body(
                        new PactDslJsonBody()
                                .integerType("amount", 900)
                )
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(expectedWalletResponseBody())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "pay")
    void testPay(MockServer mockServer) throws IOException {
        final WalletService service = new WalletService(HttpClient.newBuilder().build());
        service.setHost(mockServer.getUrl());
        Optional<Wallet> wallet = service.pay(userIdWithBalance100, 900);
        assertTrue(wallet.isPresent());
    }

    @Pact(provider = "wallet", consumer = "web-api")
    public RequestResponsePact payNonExistingWallet(PactDslWithProvider builder) throws JSONException {
        return builder
                .given("I have an non-existent user_id")
                .uponReceiving("a payment request")
                .path("/wallets/" + nonExistentUserId)
                .method("PATCH")
                .headers(headers)
                .body(
                        new PactDslJsonBody()
                                .integerType("amount", 900)
                )
                .willRespondWith()
                .status(404)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "payNonExistingWallet")
    void testPayNonExistingWallet(MockServer mockServer) throws IOException {
        final WalletService service = createWalletService(mockServer);
        final Optional<Wallet> wallet = service.pay(nonExistentUserId, 900);
        assertTrue(wallet.isEmpty());
    }

    @NotNull
    private static DslPart expectedWalletResponseBody() {
        return new PactDslJsonBody()
                .stringType("id")
                .stringType("userId")
                .integerType("balance", 1000); // TODO: this is coupled to UI assertion
    }

    @NotNull
    private static WalletService createWalletService(MockServer mockServer) {
        final WalletService service = new WalletService(
                HttpClient.newBuilder().build()
        );
        service.setHost(mockServer.getUrl());
        return service;
    }
}
