package com.goomoong.room9backend.service;

import com.goomoong.room9backend.config.FolderConfig;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.repository.user.UserRepository;
import com.goomoong.room9backend.exception.DuplicateNicknameException;
import com.goomoong.room9backend.exception.NoSuchUserException;
import com.goomoong.room9backend.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final FileService fileService;
    private final FolderConfig folderConfig;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchUserException("존재하지 않는 회원입니다."));
    }

    @Transactional
    public User update(Long id, String nickname, MultipartFile file, String email, String birthday, String gender, String intro) {
        User findUser = userRepository.findById(id).orElseThrow(() -> new NoSuchUserException("존재하지 않는 회원입니다."));
        //닉네임이 현재 닉네임과 같거나 빈 칸일 경우
        if (nickname.equals(findUser.getNickname()) || nickname == "") {
            nickname = null;
        }
        //닉네임 중복검사
        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new DuplicateNicknameException("중복된 닉네임입니다.");
        }

        String thumbnailImgUrl = file.isEmpty() ? null : fileService.uploadThumbnailImage(folderConfig.getUser(), file);
        findUser.update(nickname, thumbnailImgUrl, email, birthday, gender, intro);
        return findUser;
    }

    @Transactional
    public User changeRole(Long id) {
        User foundUser = userRepository.findById(id).orElseThrow(() -> new NoSuchUserException("존재하지 않는 회원입니다."));
        foundUser.changeRole();

        return foundUser;
    }
}