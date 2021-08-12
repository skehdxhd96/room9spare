package com.goomoong.room9backend.Repository;

import com.goomoong.room9backend.domain.file.RoomImg;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomImgRepository extends JpaRepository<RoomImg, Long> { }
