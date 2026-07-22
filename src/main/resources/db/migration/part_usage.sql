CREATE TABLE part_usage (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    work_order_id BIGINT NOT NULL,
    part_id BIGINT NOT NULL,

    qty_used INT NOT NULL,

    CONSTRAINT fk_partusage_workorder
        FOREIGN KEY (work_order_id)
        REFERENCES work_order(id),

    CONSTRAINT fk_partusage_part
        FOREIGN KEY (part_id)
        REFERENCES part(id)
);