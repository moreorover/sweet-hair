ALTER TABLE `sale_items`
    ADD COLUMN `quantity` int NOT NULL AFTER `sale_id`;

ALTER TABLE `sale_items`
    ADD COLUMN `unit_price` float NOT NULL AFTER `quantity`;