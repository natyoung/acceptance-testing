package org.scrumfall.webapi.controller;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import org.scrumfall.webapi.controller.responses.Wallet;
import org.scrumfall.webapi.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = WalletController.class)
public class WalletControllerTest {
    private static final String WALLET_PATH = "/wallets";
    private static final Gson GSON = new Gson();
    private static final Faker faker = new Faker();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @Test
    void shouldReturnWalletById() throws Exception {
        final Wallet wallet = new Wallet("1", "1", 0);
        when(walletService.getByUserId("1")).thenReturn(Optional.of(wallet));

        String response = mockMvc
                .perform(get(WALLET_PATH + "/1"))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(GSON.toJson(wallet), response);
    }

    @Test
    void shouldReturn404WhenNoWalletFound() throws Exception {
        when(walletService.getByUserId("1")).thenReturn(Optional.empty());
        mockMvc.perform(get(WALLET_PATH + "/1")).andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateWalletById() throws Exception {
        final String userId = "1";
        String walletId = faker.number().digit();
        final int amount = new Random().nextInt(Integer.MAX_VALUE);
        final Wallet wallet = new Wallet(walletId, userId, amount);
        when(walletService.pay(userId, amount)).thenReturn(Optional.of(wallet));
        final JsonObject body = new JsonObject();
        body.addProperty("amount", String.valueOf(amount));

        String response = mockMvc
                .perform(patch(WALLET_PATH + "/" + userId)
                        .content(body.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(GSON.toJson(wallet), response);
    }

    @Test
    void shouldReturn404WhenNoWalletFoundForUpdate() throws Exception {
        final int amount = new Random().nextInt(Integer.MAX_VALUE);
        when(walletService.pay("1", amount)).thenReturn(Optional.empty());
        final JsonObject body = new JsonObject();
        body.addProperty("amount", String.valueOf(amount));

        mockMvc.perform(patch(WALLET_PATH + "/1")
                        .content(body.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }
}
