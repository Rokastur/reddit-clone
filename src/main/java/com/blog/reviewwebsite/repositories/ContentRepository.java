package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.Content;
import com.blog.reviewwebsite.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ContentRepository<T extends Content> extends JpaRepository<T, Long> {

    Page<T> findAllByUser(User user, Pageable pageable);

}
