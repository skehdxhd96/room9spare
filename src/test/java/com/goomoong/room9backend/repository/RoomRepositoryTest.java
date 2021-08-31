package com.goomoong.room9backend.repository;

import com.goomoong.room9backend.config.QuerydslConfig;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.room.dto.OrderDto;
import com.goomoong.room9backend.domain.room.dto.searchDto;
import com.goomoong.room9backend.domain.user.Role;
import com.goomoong.room9backend.domain.user.User;
import com.goomoong.room9backend.repository.room.RoomRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
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
    @Rollback(value = false)
    public void setUpData() {

        //given

        user = User.builder()
                .accountId("testAccountId")
                .nickname("testNickname")
                .thumbnailImgUrl("testurl")
                .name("testName")
                .role(Role.GUEST)
                .build();
        testEntityManager.persist(user);

        final var rooms = List.of(
                Room.builder().title("테").price(1000).detailLocation("h").limited(10).users(user).liked(10).build(),
                Room.builder().title("테스").price(2000).detailLocation("he").limited(20).users(user).liked(12).build(),
                Room.builder().title("트").price(3000).detailLocation("hell").limited(30).users(user).liked(6).build(),
                Room.builder().title("테스트").price(4000).detailLocation("hello").limited(40).users(user).liked(11).build(),
                Room.builder().title("테스트으").price(5000).detailLocation("hellow").limited(50).users(user).liked(4).build(),
                Room.builder().title("te").price(6000).detailLocation("hellowo").limited(60).users(user).liked(10).build(),
                Room.builder().title("tes").price(7000).detailLocation("hellowor").limited(70).users(user).liked(9).build(),
                Room.builder().title("test").price(8000).detailLocation("helloworl").limited(80).users(user).liked(3).build(),
                Room.builder().title("testing").price(9000).detailLocation("helloworld").limited(90).users(user).liked(9).build());
        rooms.forEach(r -> testEntityManager.persist(r));
    }

    @Test
    @DisplayName("title : 포함 / limitPrice : 이하 / detailLocation : 포함 / limitPeople : 이하 / orderStandard : 정렬기준")
    @Rollback(value = false)
    public void 방_필터조회_테스트() {

        //given
        ArrayList<Room> actual_likedby = new ArrayList<>();
        actual_likedby.add(roomRepository.findById(2L).orElse(null));
        actual_likedby.add(roomRepository.findById(1L).orElse(null));

        //when
        searchDto s1 = new searchDto("테", 4999, "h", 29, OrderDto.LIKEDDESC);
        var result_likedby = roomRepository.findRoomWithFilter(s1);

        searchDto s2= new searchDto("테", 4999, "h", 29, OrderDto.LIKEDASC);
        var result_likedby2 = roomRepository.findRoomWithFilter(s2);

        //then
        Assertions.assertThat(actual_likedby).containsExactly(result_likedby.get(0), result_likedby.get(1));
        Assertions.assertThat(actual_likedby).containsExactly(result_likedby2.get(1), result_likedby2.get(0));
    }
}
