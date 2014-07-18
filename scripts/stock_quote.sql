DROP TABLE `stock_quote`;
CREATE TABLE `stock_quote` (
  `id` integer unsigned PRIMARY KEY AUTO_INCREMENT,
  `adp_100` double DEFAULT NULL,
  `adp_200` double DEFAULT NULL,
  `adp_50` double DEFAULT NULL,
  `adv_21` integer DEFAULT NULL,
  `adv_30` integer DEFAULT NULL,
  `adv_90` integer DEFAULT NULL,
  `ask` double DEFAULT NULL,
  `ask_time` varchar(16) DEFAULT NULL,
  `asksz` integer DEFAULT NULL,
  `basis` integer DEFAULT NULL,
  `beta` double DEFAULT NULL,
  `bid` double DEFAULT NULL,
  `bid_time` varchar(16) DEFAULT NULL,
  `bidsz` integer DEFAULT NULL,
  `bidtick` integer DEFAULT NULL,
  `chg` double DEFAULT NULL,
  `chg_sign` char(1) DEFAULT NULL,
  `chg_t` double DEFAULT NULL,
  `cl` double DEFAULT NULL,
  `cusip` varchar(32) DEFAULT NULL,
  `date` TIMESTAMP NULL,
  `datetime` TIMESTAMP NULL,
  `div` double DEFAULT NULL,
  `divexdate`  TIMESTAMP NULL,
  `divfreq` char(1) DEFAULT NULL,
  `divpaydt`  TIMESTAMP NULL,
  `dollar_value` double DEFAULT NULL,
  `eps` double DEFAULT NULL,
  `exch` varchar(16) DEFAULT NULL,
  `exch_desc` varchar(64) DEFAULT NULL,
  `hi` double DEFAULT NULL,
  `iad` double DEFAULT NULL,
  `incr_vl` double DEFAULT NULL,
  `last` double DEFAULT NULL,
  `lo` double DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `op_flag` char(1) DEFAULT NULL,
  `opn` double DEFAULT NULL,
  `pchg` varchar(8) DEFAULT NULL,
  `pchg_sign` char(1) DEFAULT NULL,
  `pcls` double DEFAULT NULL,
  `pe` double DEFAULT NULL,
  `phi` double DEFAULT NULL,
  `plo` double DEFAULT NULL,
  `popn` double DEFAULT NULL,
  `pr_adp_100` double DEFAULT NULL,
  `pr_adp_200` double DEFAULT NULL,
  `pr_adp_50` double DEFAULT NULL,
  `pr_date` TIMESTAMP NULL,
  `prbook` double DEFAULT NULL,
  `prchg` double DEFAULT NULL,
  `prime_exch` varchar(16) DEFAULT NULL,
  `pvol` integer DEFAULT NULL,
  `qcond` integer DEFAULT NULL,
  `secclass` integer DEFAULT NULL,
  `sesn` varchar(64) DEFAULT NULL,
  `sesn_vl` integer DEFAULT NULL,
  `sho` bigint(20) DEFAULT NULL,
  `symbol` varchar(16) DEFAULT NULL,
  `tcond` char(1) DEFAULT NULL,
  `timestamp` TIMESTAMP NULL,
  `tr_num` integer DEFAULT NULL,
  `tradetick` char(1) DEFAULT NULL,
  `trend` varchar(16) DEFAULT NULL,
  `vl` bigint(20) DEFAULT NULL,
  `volatility12` double DEFAULT NULL,
  `vwap` double DEFAULT NULL,
  `wk52hi` double DEFAULT NULL,
  `wk52hidate` TIMESTAMP NULL,
  `wk52lo` double DEFAULT NULL,
  `wk52lodate` TIMESTAMP NULL,
  `yield` double DEFAULT NULL,
  `persistence_version` INTEGER, 
  `created` TIMESTAMP DEFAULT 0,
  `last_update` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
