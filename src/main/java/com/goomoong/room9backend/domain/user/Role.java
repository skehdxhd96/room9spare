package com.goomoong.room9backend.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    CUSTOMER("ROLE_CUSTOMER", "구매자"),
    SELLER("ROLE_SELLER", "판매자");

    private final String key;
    private final String value;
}
