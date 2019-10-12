package com.udacity.course3.reviews.entity;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Integer productId;
}
