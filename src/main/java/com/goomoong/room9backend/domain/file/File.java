package com.goomoong.room9backend.domain.file;

import com.goomoong.room9backend.domain.base.BaseEntity;
import com.goomoong.room9backend.domain.file.dto.fileDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class File extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "File_Id")
    private Long id;

    @OneToOne(mappedBy = "file", cascade = CascadeType.ALL)
    private RoomImg roomImg;

    private String extension; // 확장자
    private String originalName;
    private String fileName;
    private String url; // url

    public static File create(fileDto dto) {
        return File.builder()
                .fileName(dto.getFileName())
                .originalName(dto.getOriginalName())
                .url(dto.getUrl())
                .extension(dto.getExtension())
                .build();
    }
}
