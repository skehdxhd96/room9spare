package com.goomoong.room9backend.repository;

import com.goomoong.room9backend.config.QuerydslConfig;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.room.dto.searchDto;
import com.goomoong.room9backend.domain.user.Role;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.repository.room.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@Import({QuerydslConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class RoomRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private User user;

    @BeforeEach
    public void setUpData() {

        user = User.builder()
                .accountId("testAccountId")
                .nickname("testNickname")
                .thumbnailImgUrl("testurl")
                .name("testName")
                .role(Role.GUEST)
                .build();
        testEntityManager.persist(user);

        final var rooms = List.of(
                Room.builder().title("스프링").price(1000).detailLocation("아메리카노").limited(10).users(user).build(),
                Room.builder().title("스프링부트").price(2000).detailLocation("카페라떼").limited(20).users(user).build(),
                Room.builder().title("스프링MVC").price(3000).detailLocation("오렌지스무디").limited(30).users(user).build(),
                Room.builder().title("커피").price(4000).detailLocation("아이스라떼").limited(40).users(user).build(),
                Room.builder().title("커피앤도넛").price(5000).detailLocation("요거트스무디").limited(50).users(user).build(),
                Room.builder().title("커피와디저트").price(6000).detailLocation("케잌1").limited(60).users(user).build(),
                Room.builder().title("위키").price(7000).detailLocation("케잌2").limited(70).users(user).build(),
                Room.builder().title("위키북스").price(8000).detailLocation("아포가토").limited(80).users(user).build(),
                Room.builder().title("위키피디아").price(9000).detailLocation("아이스크림").limited(90).users(user).build());
        rooms.forEach(r -> testEntityManager.persist(r));
    }

    @Test
    @DisplayName("title : 포함 / limitPrice : 이하 / detailLocation : 포함 / limitPeople : 이하")
    public void test1() {

        var expected = List.of("오렌지스무디", "요거트스무디");

        searchDto s1 = new searchDto(null, 5500, "스무디", 55);
        var result = roomRepository.findRoomWithFilter(s1)
                .stream().map(Room::getDetailLocation).collect(Collectors.toList());

        Assertions.assertIterableEquals(expected, result);
    }
}
