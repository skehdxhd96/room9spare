package com.goomoong.room9backend.Repository;

import com.goomoong.room9backend.domain.room.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {

    @Modifying
    @Query("delete from Amenity a where a.room.id = :id")
    void DeleteAmenityWithRoom(@Param("id") Long id);
}
