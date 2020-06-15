ALTER TABLE `order_items`
    ADD COLUMN `quantity` int NOT NULL AFTER `order_id`;

ALTER TABLE `order_items`
    ADD COLUMN `unit_price` float NOT NULL AFTER `quantity`;