package com.goomoong.room9backend.Repository;

import com.goomoong.room9backend.domain.room.RoomConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomConfRepository extends JpaRepository<RoomConfiguration, Long> {

    @Modifying
    @Query("delete from RoomConfiguration rc where rc.room.id = :id")
    void DeleteRoomConfWithRoom(@Param("id") Long id);
}
