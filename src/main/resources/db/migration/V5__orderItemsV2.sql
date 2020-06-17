ALTER TABLE `order_items`
    DROP COLUMN `id`,
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (`product_id`, `order_id`) USING BTREE;