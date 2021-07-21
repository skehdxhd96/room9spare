package com.goomoong.room9backend.controller;

import com.goomoong.room9backend.domain.user.Role;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @GetMapping("/api/v1/users")
    public List<User> getAllUsers() {
        List<User> allUsers = userService.findAllUsers();
        return allUsers;
    }

    @GetMapping("/api/v1/users/{id}")
    public UserResponseDto getUserInfo(@PathVariable Long id) {
        User findUser = userService.findById(id);

        return UserResponseDto.builder()
                .id(findUser.getId())
                .thumbnailUrl(findUser.getThumbnailUrl())
                .name(findUser.getName())
                .intro(findUser.getIntro())
                .build();
    }

    @PostMapping("/api/v1/users/{id}/role")
    public RoleResponseDto changeRole(@PathVariable Long id) {
        User findUser = userService.findById(id);
        findUser.changeRole();

        return RoleResponseDto.builder().role(findUser.getRole()).build();
    }

    @PostMapping("/api/v1/users/{id}")
    public UserResponseDto update(@PathVariable Long id, @RequestBody UpdateRequestDto requestDto) {
        User updateUser = userService.update(id, requestDto.getThumbnailUrl(), requestDto.getIntro());

        return UserResponseDto.builder()
                .id(updateUser.getId())
                .thumbnailUrl(updateUser.getThumbnailUrl())
                .name(updateUser.getName())
                .intro(updateUser.getIntro())
                .build();
    }

    @Getter
    @AllArgsConstructor
    @Builder
    static class UpdateRequestDto {
        private String thumbnailUrl;
        private String intro;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    static class UserResponseDto {
        private Long id;
        private String thumbnailUrl;
        private String name;
        private String intro;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    static class RoleResponseDto {
        private Role role;
    }

    @Getter
    @AllArgsConstructor
    static class AllUsersResponseDto<T> {
        private T data;
    }
}
