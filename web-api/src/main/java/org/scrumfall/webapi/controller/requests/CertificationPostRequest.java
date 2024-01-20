package org.scrumfall.webapi.controller.requests;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class CertificationPostRequest {
    private final String userId;
    private final String name;

    public CertificationPostRequest(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}
