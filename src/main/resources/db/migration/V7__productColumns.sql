ALTER TABLE `products`
    ADD `inStockCount` int NOT NULL default 0,
    ADD `size` int NOT NULL default 0,
    ADD `sizeUnit` varchar(3) NOT NULL;