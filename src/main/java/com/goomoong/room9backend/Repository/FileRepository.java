package com.goomoong.room9backend.Repository;

import com.goomoong.room9backend.domain.file.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
