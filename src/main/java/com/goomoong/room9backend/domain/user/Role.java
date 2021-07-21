package com.goomoong.room9backend.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "게스트"),
    HOST("ROLE_HOST", "호스트");

    private final String key;
    private final String value;
}
