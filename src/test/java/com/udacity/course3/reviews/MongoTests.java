package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.CommentDocument;
import com.udacity.course3.reviews.entity.ReviewDocument;
import com.udacity.course3.reviews.repositories.Mongo.MongoReviewRepository;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class MongoTests {

	@Autowired MongoReviewRepository mongoReviewRepository;

	@AfterEach
	public void tearDown() {
		mongoReviewRepository.deleteAll();
	}

	@Test
	public void testReviewDocument() {
		CommentDocument comment = new CommentDocument();
		comment.setCommentContent("some comment");
		comment.setCommentId(123);

		ReviewDocument review = new ReviewDocument();
		review.setReviewId(123);
		review.setProductId(456);
		review.setReviewContent("some review");
		review.add(comment);

		mongoReviewRepository.save(review);

		ReviewDocument savedReview = mongoReviewRepository.findAll().get(0);
		CommentDocument savedComment = savedReview.getCommentDocuments().get(0);
		assertEquals(123, savedReview.getReviewId().intValue());
		assertEquals(456, savedReview.getProductId().intValue());
		assertEquals("some review", savedReview.getReviewContent());
		assertEquals(123, comment.getCommentId().intValue());
		assertEquals("some comment", comment.getCommentContent());
	}

}