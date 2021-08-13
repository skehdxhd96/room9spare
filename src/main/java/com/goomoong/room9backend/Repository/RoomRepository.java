package com.goomoong.room9backend.Repository;

import com.goomoong.room9backend.domain.room.Room;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Override @EntityGraph(attributePaths = {"users"})
    List<Room> findAll();

    @Override @EntityGraph(attributePaths = {"users"})
    Optional<Room> findById(Long aLong);

    List<Room> findTop5ByOrderByLikedDesc();
}
