package com.goomoong.room9backend.domain.reservation;

import com.goomoong.room9backend.domain.base.BaseEntity;
import com.goomoong.room9backend.domain.room.Room;
import com.goomoong.room9backend.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class roomReservation extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Reservation_Id")
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Room_Id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User users;

    private ReserveStatus reserveStatus;
    private RefundStatus refundStatus;
    private LocalDateTime checkinDate;
    private LocalDateTime checkoutDate;
    private LocalDateTime startDate;
    private LocalDateTime finalDate;
}
