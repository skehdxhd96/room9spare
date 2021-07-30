package com.goomoong.room9backend.domain.user.dto;

import com.goomoong.room9backend.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RoleResponseDto {
    private Role role;
}
