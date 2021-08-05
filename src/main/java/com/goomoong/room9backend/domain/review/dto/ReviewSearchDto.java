package com.goomoong.room9backend.domain.review.dto;

import com.goomoong.room9backend.domain.Room;
import com.goomoong.room9backend.domain.user.User;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ReviewSearchDto {
    private User user;
    private Room room;
}
