ALTER TABLE buy RENAME COLUMN reciever TO receiver;
-- @BLOCK
ALTER TABLE buy MODIFY COLUMN id INT NOT NULL AUTO_INCREMENT;
-- @BLOCK
ALTER TABLE sell MODIFY COLUMN id INT NOT NULL AUTO_INCREMENT;
-- @BLOCK
INSERT INTO buy VALUES(1,'hatim',10,69);
INSERT INTO buy VALUES(2,'kaif',15,80);

-- @BLOCK
INSERT INTO sell VALUES(1,'kaif',10,69);
INSERT INTO sell VALUES(2,'hatim',15,80);
-- @BLOCK
TRUNCATE TABLE buy;
TRUNCATE TABLE sell;
-- @BLOCK
UPDATE users SET balance = 0;
-- @BLOCK
UPDATE users SET phone = '+919022775595' WHERE id = 3;
-- @BLOCK
INSERT INTO users VALUES(3,'Kaif Sayyad','kaifalisayyad@gmail.com',9022775595,'Mumbai, India',0,'password');
-- @BLOCK
INSERT INTO users VALUES(4,'Tathagat Sen','tathagatsen@gmail.com','+914838636479','Kabul, Afghanistan',0,'noobmaster69');
-- @BLOCK
UPDATE users SET balance = 5000 WHERE id>0;
-- @BLOCK
DELETE FROM users WHERE id>4;
