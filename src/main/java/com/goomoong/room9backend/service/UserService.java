package com.goomoong.room9backend.service;

import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.domain.user.UserRepository;
import com.goomoong.room9backend.exception.NoSuchUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchUserException("존재하지 않는 회원입니다."));
    }

    @Transactional
    public User update(Long id, String thumbnailUrl, String intro) {
        User findUser = userRepository.findById(id).orElseThrow(() -> new NoSuchUserException("존재하지 않는 회원입니다."));
        findUser.update(thumbnailUrl, intro);
        return findUser;
    }

    @Transactional
    public void changeRole(Long id) {
        User findUser = userRepository.findById(id).orElseThrow(() -> new NoSuchUserException("존재하지 않는 회원입니다."));
        findUser.changeRole();
    }
}
