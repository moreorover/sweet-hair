ALTER TABLE `orders`
    RENAME COLUMN `purchased_at` TO `operation_date`;

ALTER TABLE `sales`
    RENAME COLUMN `sold_at` TO `operation_date`;