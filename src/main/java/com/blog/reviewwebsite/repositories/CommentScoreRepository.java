package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.Comment;
import com.blog.reviewwebsite.entities.CommentScore;
import com.blog.reviewwebsite.entities.Score;
import com.blog.reviewwebsite.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CommentScoreRepository extends JpaRepository<CommentScore, Long> {

    Set<CommentScore> findAllByCommentAndUpvotedTrue(Comment comment);

    Set<CommentScore> findAllByCommentAndDownvotedTrue(Comment comment);

    CommentScore findOneByCommentAndUser(Comment comment, User user);

    Boolean existsByCommentAndUserAndUpvotedTrue(Comment comment, User user);

    Boolean existsByCommentAndUserAndDownvotedTrue(Comment comment, User user);
}
