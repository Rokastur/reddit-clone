package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    @Query(value = "select * from file where id=:userId", nativeQuery = true)
    File getUserFile(Long userId);
}

