CREATE TABLE site (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),

    CONSTRAINT fk_site_customer
        FOREIGN KEY (customer_id)
        REFERENCES customer(id)
);