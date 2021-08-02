package com.goomoong.room9backend.domain.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.goomoong.room9backend.domain.room.dto.confDto;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Amenity {

    private String facility; // 부대시설
}
