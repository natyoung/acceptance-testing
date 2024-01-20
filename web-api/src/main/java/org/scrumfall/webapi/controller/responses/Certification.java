package org.scrumfall.webapi.controller.responses;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Certification {
    private final String id;
    private final String userId;
    private final String date;
    private final String name;

    public Certification(String id, String userId, String date, String name) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.name = name;
    }
}
