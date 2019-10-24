package com.udacity.course3.reviews;

import com.udacity.course3.reviews.repositories.MySQL.CommentRepository.CommentRepository;
import com.udacity.course3.reviews.repositories.MySQL.ReviewRepository.ReviewRepository;
import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repositories.MySQL.ProductRepository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewsApplicationTests {

	@Autowired ProductRepository productRepository;
	@Autowired ReviewRepository reviewRepository;
	@Autowired CommentRepository commentRepository;

	@Before
	public void setUp() {
		Product product = new Product();
		product.setProductName("new");
		productRepository.save(product);

		Review review = new Review();
		review.setProductId(product);
		review.setReviewContent("some review");
		reviewRepository.save(review);

		Comment comment = new Comment();
		comment.setReviewId(review);
		comment.setCommentContent("some comment");
		commentRepository.save(comment);
	}

	@Test
	public void testProductEntity() {
		Product savedProduct = productRepository.findAll().get(0);
		assertEquals("new", savedProduct.getProductName());
	}

	@Test
	public void testReviewEntity() {
		Review savedReview = reviewRepository.findAll().get(0);
		assertEquals("some review", savedReview.getReviewContent());
	}

	@Test
	public void testCommentEntity() {
		Comment savedComment = commentRepository.findAll().get(0);
		assertEquals("some comment", (savedComment).getCommentContent());
	}

	@Test
	public void testFindProductFromReview() {
		Product savedProduct = productRepository.findAll().get(0);
		Review savedReview = reviewRepository.findAll().get(0);

		Product productFromReview = savedReview.getProductId();

		assertEquals(savedProduct, productRepository.findById(productFromReview.getProductId()).get());
	}

	@Test
	public void testFindReviewByComment() {
		Review savedReview = reviewRepository.findAll().get(0);
		Comment savedComment = commentRepository.findAll().get(0);

		Review reviewFromComment = savedComment.getReviewId();

		assertEquals(savedReview, reviewRepository.findById(reviewFromComment.getReviewId()).get());
	}

	@Test
	public void testReviewRepository() {
		Product savedProduct = productRepository.findAll().get(0);
		List<Review> reviews = reviewRepository.findAllByProductId(savedProduct.getProductId());

		assertNotNull(reviews);
		assertThat(reviews.get(0).getReviewContent(), equalTo("some review"));
	}

	@Test
	public void testCommentRepository() {
		Review savedReview = reviewRepository.findAll().get(0);
		List<Comment> comments = commentRepository.findAllByReviewId(savedReview.getReviewId());

		assertNotNull(comments);
		assertThat(comments.get(0).getCommentContent(), equalTo("some comment"));
	}
}

