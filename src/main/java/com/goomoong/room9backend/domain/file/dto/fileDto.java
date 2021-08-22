package com.goomoong.room9backend.domain.file.dto;

import com.goomoong.room9backend.domain.file.RoomImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class fileDto {

    private String extension; // 확장자
    private String originalName;
    private String fileName;
    private String url; // url
}
