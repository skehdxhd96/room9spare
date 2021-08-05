package com.goomoong.room9backend.repository;

import com.goomoong.room9backend.domain.user.Role;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.repository.userRepository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void idStrategyTest() {
        User user1 = User.builder().accountId("1").name("user1").nickname("user1").role(Role.GUEST).thumbnailImgUrl("user1.jpg").build();
        User user2 = User.builder().accountId("2").name("user2").nickname("user2").role(Role.GUEST).thumbnailImgUrl("user2.jpg").build();
        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);

        Assertions.assertEquals(1, Math.abs(savedUser2.getId() - savedUser1.getId()));
    }

    @Test
    public void saveUserTest() {
        User user = User.builder().accountId("1").name("user").nickname("user").role(Role.GUEST).thumbnailImgUrl("user.jpg").build();
        User savedUser = userRepository.save(user);

        Assertions.assertEquals(user.getName(), savedUser.getName());
        Assertions.assertEquals(user.getNickname(), savedUser.getNickname());
    }

    @Test
    public void findByIdTest() {
        User user = User.builder().id(1L).accountId("1").name("user").nickname("user").role(Role.GUEST).thumbnailImgUrl("user.jpg").build();
        userRepository.save(user);

        Optional<User> userFindById = userRepository.findById(1L);
        userFindById.ifPresent(u -> Assertions.assertEquals(user.getName(), u.getNickname()));
    }

    @Test
    public void findByAccountIdTest() {
        User user = User.builder().accountId("1").name("user").nickname("user").role(Role.GUEST).thumbnailImgUrl("user.jpg").build();
        userRepository.save(user);

        Optional<User> userFindByAccountId = userRepository.findByAccountId("1");
        userFindByAccountId.ifPresent(u -> Assertions.assertEquals(user.getThumbnailImgUrl(), u.getThumbnailImgUrl()));
    }
}