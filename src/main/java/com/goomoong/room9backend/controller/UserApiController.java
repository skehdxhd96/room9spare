package com.goomoong.room9backend.controller;

import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.domain.user.dto.RoleResponseDto;
import com.goomoong.room9backend.domain.user.dto.UpdateRequestDto;
import com.goomoong.room9backend.domain.user.dto.UserResponseDto;
import com.goomoong.room9backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @GetMapping("/api/v1/users")
    public List<UserResponseDto> getAllUsers() {
        List<UserResponseDto> allUsers =  userService.findAllUsers().stream().map(user ->
            UserResponseDto.builder().id(user.getId()).nickname(user.getNickname()).thumbnailImgUrl(user.getThumbnailImgUrl()).email(user.getEmail()).birthday(user.getBirthday()).gender(user.getGender()).intro(user.getIntro()).build()
                ).collect(Collectors.toList());
        return allUsers;
    }

    @GetMapping("/api/v1/users/{id}")
    public UserResponseDto getUserInfo(@PathVariable Long id) {
        User findUser = userService.findById(id);

        return UserResponseDto.builder()
                .id(findUser.getId())
                .nickname(findUser.getNickname())
                .thumbnailImgUrl(findUser.getThumbnailImgUrl())
                .email(findUser.getEmail())
                .birthday(findUser.getBirthday())
                .gender(findUser.getGender())
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
        User updateUser = userService.update(id, requestDto.getNickname(), requestDto.getThumbnailImgUrl(), requestDto.getEmail(),
                requestDto.getBirthday(), requestDto.getGender(), requestDto.getIntro());

        return UserResponseDto.builder()
                .id(updateUser.getId())
                .nickname(updateUser.getNickname())
                .thumbnailImgUrl(updateUser.getThumbnailImgUrl())
                .email(updateUser.getEmail())
                .birthday(updateUser.getBirthday())
                .gender(updateUser.getGender())
                .intro(updateUser.getIntro())
                .build();
    }
}
