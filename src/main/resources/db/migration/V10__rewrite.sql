CREATE TABLE `client_type` (
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(255) NOT NULL
);

CREATE TABLE `clients` (
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `client_type` int NOT NULL
);

ALTER TABLE `clients` ADD FOREIGN KEY (`client_type`) REFERENCES `client_type` (`id`);

CREATE TABLE `operation_type` (
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(255) NOT NULL
);

CREATE TABLE `operations` (
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `date` date NOT NULL,
    `total` decimal(6, 2) NOT NULL,
    `profit` decimal(10, 2) default 0,
    `note` varchar(8000) default NULL,
    `operation_type` int NOT NULL,
    `client` int NOT NULL
);

ALTER TABLE `operations` ADD FOREIGN KEY (`operation_type`) REFERENCES `operation_type` (`id`);
ALTER TABLE `operations` ADD FOREIGN KEY (`client`) REFERENCES `clients` (`id`);

CREATE TABLE `operation_products` (
    `operation_id` int NOT NULL,
    `product_id` int NOT NULL
);

ALTER TABLE `operation_products` ADD FOREIGN KEY (`operation_id`) REFERENCES `operations` (`id`);
ALTER TABLE `operation_products` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `products` ADD `price` decimal(6, 2) default 0;
ALTER TABLE `products` ADD `total_spent` decimal(10, 2) default 0;
ALTER TABLE `products` ADD `total_received` decimal(10, 2) default 0;
ALTER TABLE `products` ADD `profit` decimal(10, 2) default 0;