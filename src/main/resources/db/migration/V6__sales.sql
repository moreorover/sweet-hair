ALTER TABLE `sales`
    MODIFY COLUMN `sold_at` date NOT NULL AFTER `id`;

ALTER TABLE `sales`
    ADD `currency` varchar(3) NOT NULL;