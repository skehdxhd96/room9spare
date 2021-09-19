package com.goomoong.room9backend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("room9")
@Getter
@Setter
public class FolderConfig {

    private String room;
    private String user = "user";
}
