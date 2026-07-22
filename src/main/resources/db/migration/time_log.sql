CREATE TABLE time_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    work_order_id BIGINT NOT NULL,
    technician_id BIGINT NOT NULL,

    minutes INT NOT NULL,
    note TEXT,

    CONSTRAINT fk_timelog_workorder
        FOREIGN KEY (work_order_id)
        REFERENCES work_order(id),

    CONSTRAINT fk_timelog_user
        FOREIGN KEY (technician_id)
        REFERENCES users(id)
);