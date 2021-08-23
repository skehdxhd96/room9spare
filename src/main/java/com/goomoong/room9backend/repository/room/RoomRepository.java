package com.goomoong.room9backend.repository.room;

import com.goomoong.room9backend.domain.room.Room;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long>, RoomRepositoryCustom {

    @Override
    @EntityGraph(attributePaths = {"users"})
    List<Room> findAll();

    @Override
    @EntityGraph(attributePaths = {"users"})
    Optional<Room> findById(Long id);

    List<Room> findTop5ByOrderByLikedDesc();

    List<Room> findTop5ByOrderByCreatedDateDesc();
}
