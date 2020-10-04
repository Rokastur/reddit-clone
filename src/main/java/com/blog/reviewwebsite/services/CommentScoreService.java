package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.entities.*;
import com.blog.reviewwebsite.repositories.CommentRepository;
import com.blog.reviewwebsite.repositories.CommentScoreRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentScoreService {

    private CommentScoreRepository commentScoreRepository;
    private CommentRepository commentRepository;

    public CommentScoreService(CommentScoreRepository commentScoreRepository, CommentRepository commentRepository) {
        this.commentScoreRepository = commentScoreRepository;
        this.commentRepository = commentRepository;
    }

    public void upvoteComment(Long commentId, User user, CommentScore score) {
        Comment comment = commentRepository.getOne(commentId);

        if (userAlreadyUpvotedComment(comment, user)) {
            CommentScore existingVote = commentScoreRepository.findOneByCommentAndUser(comment, user);
            commentScoreRepository.delete(existingVote);
            updateAllCommentVotes(comment);
            commentRepository.save(comment);
            return;
        }
        if (userAlreadyDownvotedComment(comment, user)) {
            CommentScore existingVote = commentScoreRepository.findOneByCommentAndUser(comment, user);
            commentScoreRepository.delete(existingVote);
        }
        score.setComment(comment);
        score.setUser(user);
        score.setUpvoted(true);
        commentScoreRepository.save(score);
        updateAllCommentVotes(comment);
        commentRepository.save(comment);
    }

    public void downvoteComment(Long commentId, User user, CommentScore score) {
        Comment comment = commentRepository.getOne(commentId);

        if (userAlreadyDownvotedComment(comment, user)) {
            CommentScore existingVote = commentScoreRepository.findOneByCommentAndUser(comment, user);
            commentScoreRepository.delete(existingVote);
            updateAllCommentVotes(comment);
            commentRepository.save(comment);
            return;
        }
        if (userAlreadyUpvotedComment(comment, user)) {
            CommentScore existingVote = commentScoreRepository.findOneByCommentAndUser(comment, user);
            commentScoreRepository.delete(existingVote);
        }
        score.setComment(comment);
        score.setUser(user);
        score.setDownvoted(true);
        commentScoreRepository.save(score);
        updateAllCommentVotes(comment);
        commentRepository.save(comment);
    }

    private void updateAllCommentVotes(Comment comment) {
        comment.setTotalUpvotes(commentScoreRepository.findAllByCommentAndUpvotedTrue(comment).size());
        comment.setTotalDownvotes(commentScoreRepository.findAllByCommentAndDownvotedTrue(comment).size());
        comment.setTotalScore(comment.getTotalUpvotes() - comment.getTotalDownvotes());
    }

    public Boolean userAlreadyUpvotedComment(Comment comment, User user) {
        return commentScoreRepository.existsByCommentAndUserAndUpvotedTrue(comment, user);
    }

    public Boolean userAlreadyDownvotedComment(Comment comment, User user) {
        return commentScoreRepository.existsByCommentAndUserAndDownvotedTrue(comment, user);
    }


}
