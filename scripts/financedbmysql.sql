DROP TABLE `company`;
CREATE TABLE `company` (
  `id` integer unsigned PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(32), 
  `symbol` varchar(16),
  `persistence_version` INTEGER, 
  `created` TIMESTAMP DEFAULT 0,
  `last_update` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE `strategy`;
CREATE TABLE `strategy` (
  `id` integer unsigned PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(32), 
  `classname` varchar(128),
  `persistence_version` INTEGER, 
  `created` TIMESTAMP DEFAULT 0,
  `last_update` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE `company_strategy_analysis`;
CREATE TABLE `company_strategy_analysis` (
  `id` integer unsigned PRIMARY KEY AUTO_INCREMENT,
  `company_id` INTEGER, 
  `strategy_id` INTEGER,
  `enable` boolean,
  `persistence_version` INTEGER, 
  `created` TIMESTAMP DEFAULT 0,
  `last_update` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



