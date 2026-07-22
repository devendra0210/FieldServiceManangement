CREATE TABLE work_order_status_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    work_order_id BIGINT NOT NULL,

    from_status VARCHAR(30),
    to_status VARCHAR(30),

    changed_by BIGINT,
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_history_workorder
        FOREIGN KEY (work_order_id)
        REFERENCES work_order(id),

    CONSTRAINT fk_history_user
        FOREIGN KEY (changed_by)
        REFERENCES users(id)
);