package org.scrumfall.webapi.controller.requests;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class WalletPatchRequest {
    private final int amount;

    public WalletPatchRequest(int amount) {
        this.amount = amount;
    }
}
