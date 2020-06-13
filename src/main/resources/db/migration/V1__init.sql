CREATE TABLE `suppliers` (
                             `id` int PRIMARY KEY AUTO_INCREMENT,
                             `name` varchar(250) NOT NULL,
                             `url` varchar(250) UNIQUE NOT NULL,
                             `logo` varchar(250)
);

CREATE TABLE `products` (
                            `id` int PRIMARY KEY AUTO_INCREMENT,
                            `name` varchar(250) UNIQUE NOT NULL
);

CREATE TABLE `orders` (
                          `id` int PRIMARY KEY AUTO_INCREMENT,
                          `purchased_at` datetime NOT NULL,
                          `supplier_id` int NOT NULL,
                          `total` double NOT NULL DEFAULT 0,
                          `items_count` int NOT NULL DEFAULT 0,
                          `currency` varchar(3) NOT NULL
);

CREATE TABLE `order_items` (
                               `id` int PRIMARY KEY AUTO_INCREMENT,
                               `product_id` int NOT NULL,
                               `order_id` int NOT NULL
);

CREATE TABLE `customers` (
                             `id` int PRIMARY KEY AUTO_INCREMENT,
                             `name` varchar(50) NOT NULL
);

CREATE TABLE `sales` (
                         `id` int PRIMARY KEY AUTO_INCREMENT,
                         `sold_at` datetime NOT NULL,
                         `customer_id` int NOT NULL,
                         `total` double NOT NULL DEFAULT 0,
                         `items_count` int NOT NULL DEFAULT 0
);

CREATE TABLE `sale_items` (
                              `id` int PRIMARY KEY AUTO_INCREMENT,
                              `product_id` int NOT NULL,
                              `sale_id` int NOT NULL
);

ALTER TABLE `orders` ADD FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`);

ALTER TABLE `order_items` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `order_items` ADD FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

ALTER TABLE `sales` ADD FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`);

ALTER TABLE `sale_items` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `sale_items` ADD FOREIGN KEY (`sale_id`) REFERENCES `sales` (`id`);
