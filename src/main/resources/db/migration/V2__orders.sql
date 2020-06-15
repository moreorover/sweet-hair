ALTER TABLE `orders`
    MODIFY COLUMN `purchased_at` date NOT NULL AFTER `id`;