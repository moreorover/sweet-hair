CREATE TABLE `transaction_type` (
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `name` varchar(255) NOT NULL
);

CREATE TABLE `transactions` (
    `id` int PRIMARY KEY AUTO_INCREMENT,
    `paid` boolean NOT NULL default FALSE,
    `amount` decimal(8, 2) default 0,
    `note` varchar(8000) default NULL,
    `operation` int NOT NULL,
    `operation_type` int NOT NULL
);

ALTER TABLE `transactions` ADD FOREIGN KEY (`operation_type`) REFERENCES `transaction_type` (`id`);
ALTER TABLE `transactions` ADD FOREIGN KEY (`operation`) REFERENCES `operations` (`id`);

INSERT INTO `transaction_type` (id, name) VALUES
(1, 'Deposit'),
(2, 'Payment'),
(3, 'Final Payment');