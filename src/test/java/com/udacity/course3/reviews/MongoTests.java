package com.udacity.course3.reviews;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.ReviewMongo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class MongoTests {

	@Test
	public void testReviewEntity(@Autowired MongoTemplate mongoTemplate) {
		DBObject review = BasicDBObjectBuilder.start()
				.add("reviewContent", "some review")
				.add("productId", 123)
				.get();

		mongoTemplate.save(review, "reviews");

		ReviewMongo savedReview = mongoTemplate.findAll(ReviewMongo.class, "reviews").get(0);
		assertEquals("some review", savedReview.getReviewContent());
		assertEquals(123, savedReview.getProductId().intValue());	}

}