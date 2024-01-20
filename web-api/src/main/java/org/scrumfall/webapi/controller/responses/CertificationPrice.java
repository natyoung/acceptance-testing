package org.scrumfall.webapi.controller.responses;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class CertificationPrice {
    private final int price;

    public CertificationPrice(int price) {
        this.price = price;
    }
}
