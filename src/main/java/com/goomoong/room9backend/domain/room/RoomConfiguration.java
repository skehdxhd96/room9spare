package com.goomoong.room9backend.domain.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goomoong.room9backend.domain.room.dto.confDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.*;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RoomConfiguration {

    private String confType;
    private int count;
}