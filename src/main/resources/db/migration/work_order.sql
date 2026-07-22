CREATE TABLE work_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    title VARCHAR(200) NOT NULL,
    priority VARCHAR(20),
    status VARCHAR(30),
    sla_due_at TIMESTAMP,

    customer_id BIGINT NOT NULL,
    site_id BIGINT NOT NULL,
    assigned_to BIGINT,

    CONSTRAINT fk_workorder_customer
        FOREIGN KEY (customer_id)
        REFERENCES customer(id),

    CONSTRAINT fk_workorder_site
        FOREIGN KEY (site_id)
        REFERENCES site(id),

    CONSTRAINT fk_workorder_user
        FOREIGN KEY (assigned_to)
        REFERENCES users(id)
);