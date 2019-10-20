package com.udacity.course3.reviews;

import com.udacity.course3.reviews.ReviewRepository.ReviewRepository;
import com.udacity.course3.reviews.entity.Review;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.*;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class ReviewRepositoryIntegrationTest {

	@Autowired ReviewRepository reviewRepository;

	private final Integer productId = 123;
	private final String content = "some content";

	@BeforeEach
	public void setUp() {
		Review review = new Review();
		review.setReviewContent(content);
		review.setProductId(productId);
		reviewRepository.save(review);
	}

	@AfterEach
	public void tearDown() {
		reviewRepository.deleteAll();
	}

	@Test
	public void testReviewEntity() {
		Review savedReview = reviewRepository.findAllByProductId(productId).get(0);

		assertEquals(content, savedReview.getReviewContent());
		assertEquals(productId, savedReview.getProductId());
	}

}
