package org.scrumfall.webapi.controller;

import lombok.RequiredArgsConstructor;
import org.scrumfall.webapi.controller.requests.WalletPatchRequest;
import org.scrumfall.webapi.controller.responses.Wallet;
import org.scrumfall.webapi.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpClient;
import java.util.Optional;

@RestController
@Validated
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @GetMapping(value = "/wallets/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Wallet> getWalletByUserId(@PathVariable(name = "id") String id) {
        try {
            final Optional<Wallet> wallet = walletService.getByUserId(id);
            return wallet
                    .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/wallets/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Wallet> pay(
            @PathVariable(name = "id") String id,
            @RequestBody WalletPatchRequest body
    ) {
        try {
            final Optional<Wallet> wallet = walletService.pay(id, body.getAmount());
            return wallet
                    .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
