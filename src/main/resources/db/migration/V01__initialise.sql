create table products(
    product_id int auto_increment,
    product_name varchar(100),
    constraint product_pk primary key (product_id)
);

create table reviews(
    review_id int auto_increment,
    review_content varchar(1000),
    product_id int,
    constraint review_pk primary key (review_id),
    constraint review_fk foreign key (product_id) references products (product_id)
);

create table comments(
    comment_id int auto_increment,
    comment_content varchar(1000),
    review_id int,
    constraint comment_pk primary key (comment_id),
    constraint comment_fk foreign key (review_id) references reviews (review_id)
);