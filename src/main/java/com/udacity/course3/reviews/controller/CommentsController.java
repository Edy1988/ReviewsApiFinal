package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.CommentDocument;
import com.udacity.course3.reviews.entity.ReviewDocument;
import com.udacity.course3.reviews.repositories.Mongo.MongoReviewRepository;
import com.udacity.course3.reviews.repositories.MySQL.CommentRepository.CommentRepository;
import com.udacity.course3.reviews.repositories.MySQL.ReviewRepository.ReviewRepository;
import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired private CommentRepository commentRepository;
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private MongoReviewRepository mongoReviewRepository;

    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<?> createCommentForReview(@PathVariable("reviewId") Integer reviewId, @Valid @RequestBody Comment comment) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isPresent()) {
            comment.setReviewId(review.get());
            commentRepository.save(comment);

            Optional<ReviewDocument> optionalMongoReview = mongoReviewRepository.findById(reviewId);
            if (optionalMongoReview.isPresent()) {
                CommentDocument mongoComment = new CommentDocument();
                mongoComment.setCommentId(comment.getCommentId());
                mongoComment.setCommentContent(comment.getCommentContent());

                ReviewDocument mongoReview = optionalMongoReview.get();
                mongoReview.add(mongoComment);
                mongoReviewRepository.save(mongoReview);
            }

            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public List<?> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        return commentRepository.findAllByReviewId(reviewId);
    }
}