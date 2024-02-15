CREATE TABLE vault (
    `id` BINARY(16) PRIMARY KEY,
    `name` TEXT NOT NULL,
    `created_at` INT(32) NOT NULL,
    `last_modified` INT(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

DELIMITER //

CREATE TRIGGER create_timestamp BEFORE INSERT ON vault
FOR EACH ROW SET NEW.created_at = UNIX_TIMESTAMP(NOW()), NEW.last_modified = UNIX_TIMESTAMP(NOW());
//

CREATE TRIGGER update_last_modified BEFORE UPDATE ON vault
FOR EACH ROW SET NEW.last_modified = UNIX_TIMESTAMP(NOW());
//

DELIMITER ;