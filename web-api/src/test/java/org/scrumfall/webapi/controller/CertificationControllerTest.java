package org.scrumfall.webapi.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;
import org.scrumfall.webapi.controller.responses.Certification;
import org.scrumfall.webapi.controller.responses.CertificationPrice;
import org.scrumfall.webapi.service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = CertificationController.class)
public class CertificationControllerTest {
    private static final String CERT_PATH = "/certifications";
    private static final String CERT_PRICE_PATH = "/certifications/price";
    private static final Gson GSON = new Gson();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CertificationService certificationService;

    @Test
    void shouldAcceptACertificationRequest() throws Exception {
        final Certification certification = new Certification(
                "1", "1", "date", "name"
        );

        when(certificationService.certify("1", "name"))
                .thenReturn(Optional.of(certification));

        final JsonObject body = new JsonObject();
        body.addProperty("userId", "1");
        body.addProperty("name", "name");

        String response = mockMvc
                .perform(post(CERT_PATH)
                        .content(body.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(GSON.toJson(certification), response);
    }

    @Test
    void shoudReturnErrorWhenCertifyFails() throws Exception {
        final JsonObject body = new JsonObject();
        when(certificationService.certify("1", "name"))
                .thenReturn(Optional.empty());
        mockMvc.perform(post(CERT_PATH)
                        .content(body.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldHandlePriceRequest() throws Exception {
        final CertificationPrice price = new CertificationPrice(100);

        when(certificationService.price()).thenReturn(Optional.of(price));

        String response = mockMvc
                .perform(get(CERT_PRICE_PATH)
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(GSON.toJson(price), response);
    }

    @Test
    void shouldHandlePriceRequestError() throws Exception {
        when(certificationService.price()).thenReturn(Optional.empty());
        mockMvc.perform(get(CERT_PRICE_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
