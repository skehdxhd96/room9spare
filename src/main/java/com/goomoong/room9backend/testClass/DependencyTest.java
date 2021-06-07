package com.goomoong.room9backend.testClass;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class DependencyTest {

    @Id
    private String data;
}
