package com.goomoong.room9backend.testClass;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DependencyTestRepositoryTest {

    @Autowired
    DBConnectionRepository dbConnectionRepository;

    @Test
    public void db연동테스트() {

        //given
        String text = "test";

        dbConnectionRepository.save(DBConnection.builder().text(text).build());

        //when
        List<DBConnection> testObjectList = dbConnectionRepository.findAll();

        //then
        DBConnection testObject = testObjectList.get(0);
        assertThat(testObject.getText()).isEqualTo(text);
    }
}
