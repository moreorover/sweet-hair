ALTER TABLE `transactions`
    ADD COLUMN `transaction_type` int NOT NULL;

ALTER TABLE `transactions` ADD FOREIGN KEY (`transaction_type`) REFERENCES `transaction_type` (`id`);