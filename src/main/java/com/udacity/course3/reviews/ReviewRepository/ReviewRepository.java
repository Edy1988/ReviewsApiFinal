package com.udacity.course3.reviews.ReviewRepository;

import com.udacity.course3.reviews.entity.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, Integer> {
	List<Review> findAllByProductId(Integer productId);
}