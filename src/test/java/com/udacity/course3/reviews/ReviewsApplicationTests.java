package com.udacity.course3.reviews;

import com.udacity.course3.reviews.CommentRepository.CommentRepository;
import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.productRepository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ReviewsApplicationTests {

	@Autowired ProductRepository productRepository;
	@Autowired CommentRepository commentRepository;

	private final Integer reviewId = 123;

	@BeforeEach
	public void setUp() {
		Product product = new Product();
		product.setProductName("new");
		productRepository.save(product);

		Comment comment = new Comment();
		comment.setReviewId(reviewId);
		comment.setCommentContent("some comment");
		commentRepository.save(comment);
	}

	@Test
	public void testProductEntity() {
		Product savedProduct = productRepository.findAll().get(0);
		assertEquals("new", savedProduct.getProductName());
	}

	@Test
	public void testCommentEntity() {
		Comment savedComment = commentRepository.findAll().get(0);
		assertEquals("some comment", savedComment.getCommentContent());
		assertEquals(reviewId, savedComment.getReviewId());
	}

	@Test
	public void testCommentRepository() {
		List<Comment> comments = commentRepository.findAllByReviewId(reviewId);

		assertNotNull(comments);
		assertThat(comments.get(0).getCommentContent(), equalTo("some comment"));
	}

}

