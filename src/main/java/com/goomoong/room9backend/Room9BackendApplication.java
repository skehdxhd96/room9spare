package com.goomoong.room9backend;

import com.goomoong.room9backend.testClass.DependencyTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Room9BackendApplication {

	public static void main(String[] args) {

		//롬복 테스트
		DependencyTest dependencyTest = new DependencyTest();
		dependencyTest.setData("lombok");
		System.out.println(dependencyTest.getData());

		SpringApplication.run(Room9BackendApplication.class, args);
	}
}
