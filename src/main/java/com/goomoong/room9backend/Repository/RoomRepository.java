package com.goomoong.room9backend.Repository;

import com.goomoong.room9backend.domain.room.Room;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Entity;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Override
    @EntityGraph(attributePaths = {"users"})
    List<Room> findAll();

    @Modifying()
    @Query("delete from Room r where r.id = :id")
    void DeleteRoomByIdWithQuery(@Param("id") Long id);
}
