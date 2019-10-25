package com.udacity.course3.reviews.controller;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.ReviewDocument;
import com.udacity.course3.reviews.repositories.Mongo.MongoReviewRepository;
import com.udacity.course3.reviews.repositories.MySQL.ProductRepository.ProductRepository;
import com.udacity.course3.reviews.repositories.MySQL.ReviewRepository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
public class ReviewsController {

    @Autowired ReviewRepository reviewRepository;
    @Autowired ProductRepository productRepository;
    @Autowired MongoReviewRepository reviewMongoRepository;

    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<?> createReviewForProduct(@PathVariable("productId") Integer productId, @Valid @RequestBody Review review) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            review.setProductId(optionalProduct.get());
            @Valid Review savedReview = reviewRepository.save(review);

            ReviewDocument reviewDocument = new ReviewDocument();
            reviewDocument.setReviewId(savedReview.getReviewId());
            reviewDocument.setReviewContent(savedReview.getReviewContent());
            reviewDocument.setProductId(productId);

            reviewMongoRepository.save(reviewDocument);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        return new ResponseEntity<>(reviewRepository.findAllByProductId(productId), HttpStatus.OK);
    }
}