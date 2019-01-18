DELIMITER $$
DROP EVENT IF EXISTS e_AutoTableEvent;
DELIMITER $$

DELIMITER $$
CREATE EVENT e_AutoTableEvent
  ON SCHEDULE EVERY 1 DAY
  ON COMPLETION PRESERVE
DO
  BEGIN
    DECLARE rightNow DATETIME;
    SET rightNow = NOW();
    IF(DATE(rightNow) = LAST_DAY(DATE(rightNow))) THEN
      CALL autoCreateOpenTable();
    END IF;
  END $$

DELIMITER $$

SET GLOBAL event_scheduler = ON;
