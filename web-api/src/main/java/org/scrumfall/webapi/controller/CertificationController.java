package org.scrumfall.webapi.controller;

import lombok.RequiredArgsConstructor;
import org.scrumfall.webapi.controller.requests.CertificationPostRequest;
import org.scrumfall.webapi.controller.responses.Certification;
import org.scrumfall.webapi.controller.responses.CertificationPrice;
import org.scrumfall.webapi.service.CertificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Validated
@RequiredArgsConstructor
public class CertificationController {
    private final CertificationService service;

    @PostMapping(value = "/certifications", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Certification> certify(
            @RequestBody CertificationPostRequest body
    ) {
        try {
            final Optional<Certification> certification = service.certify(body.getUserId(), body.getName());
            return certification
                    .map(value -> new ResponseEntity<>(value, HttpStatus.CREATED))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/certifications/price", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public ResponseEntity<CertificationPrice> price() {
        try {
            final Optional<CertificationPrice> price = service.price();
            return price
                    .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
