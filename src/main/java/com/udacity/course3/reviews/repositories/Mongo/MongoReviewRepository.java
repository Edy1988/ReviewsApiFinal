package com.udacity.course3.reviews.repositories;

import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.ReviewMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
interface MongoReviewRepository extends MongoRepository<ReviewMongo, Integer> {
	 List<Review> findAllByProductId(Integer productId);
 }

