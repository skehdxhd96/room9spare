package com.goomoong.room9backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.config.location=" +
        "classpath:application.yml," +
        "classpath:aws.yml")
class Room9BackendApplicationTests {

//	@Test
//	void contextLoads() {
//	}

}
