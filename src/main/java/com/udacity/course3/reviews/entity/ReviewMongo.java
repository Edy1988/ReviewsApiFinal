package com.udacity.course3.reviews.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;


@Document("reviews")

public class ReviewMongo {

		@Id
		private Integer reviewId;
		private String reviewContent;
		private Product product;
		private Integer productId;

		public Integer getReviewId() {
			return reviewId;
		}

		public void setReviewId(Integer reviewId) {
			this.reviewId = reviewId;
		}

		public String getReviewContent() {
			return reviewContent;
		}

		public void setReviewContent(String reviewContent) {
			this.reviewContent = reviewContent;
		}

		public void setProductId(Product product) {
			this.productId = productId;
		}

		public Integer getProductId() {
			return productId;
		}
	}


