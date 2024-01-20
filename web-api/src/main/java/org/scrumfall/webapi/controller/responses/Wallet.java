package org.scrumfall.webapi.controller.responses;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Wallet {
    private final String id;
    private final String userId;
    private final int balance;

    public Wallet(String id, String userId, int balance) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
    }
}
