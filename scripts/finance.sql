-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: finance
-- ------------------------------------------------------
-- Server version	5.5.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `basic_stock_data`
--

DROP TABLE IF EXISTS `basic_stock_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `basic_stock_data` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `company_id` int(10) unsigned NOT NULL,
  `date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `close` double NOT NULL,
  `open` double NOT NULL,
  `high` double NOT NULL,
  `low` double NOT NULL,
  `volume` bigint(20) NOT NULL,
  `persistence_version` int(11) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `version_index` int(32) DEFAULT '1',
  `equity_data_source_id` int(12) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `company_id_version_idx_date_equity_ds_id` (`company_id`,`version_index`,`date`,`equity_data_source_id`),
  KEY `date_index` (`date`),
  KEY `company_id_2` (`company_id`,`date`,`version_index`,`equity_data_source_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3851157 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `symbol` varchar(16) DEFAULT NULL,
  `persistence_version` int(11) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` char(1) DEFAULT 'Y',
  PRIMARY KEY (`id`),
  UNIQUE KEY `symbol_index` (`symbol`)
) ENGINE=InnoDB AUTO_INCREMENT=209 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `company_strategy_analysis`
--

DROP TABLE IF EXISTS `company_strategy_analysis`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_strategy_analysis` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `company_id` int(11) DEFAULT NULL,
  `strategy_id` int(11) DEFAULT NULL,
  `enable` tinyint(1) DEFAULT NULL,
  `persistence_version` int(11) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `eds_ticker_timeinfo`
--

DROP TABLE IF EXISTS `eds_ticker_timeinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `eds_ticker_timeinfo` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ticker_id` int(10) unsigned NOT NULL,
  `equity_data_source_id` int(10) unsigned NOT NULL,
  `persistence_version` int(11) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` char(1) DEFAULT 'Y',
  `close_time_utc` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `equity_data_source`
--

DROP TABLE IF EXISTS `equity_data_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equity_data_source` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `persistence_version` int(11) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `active` char(1) DEFAULT 'Y',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `option_quote`
--

DROP TABLE IF EXISTS `option_quote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `option_quote` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `xday` int(11) DEFAULT NULL,
  `xmonth` int(11) DEFAULT NULL,
  `xyear` varchar(4) DEFAULT NULL,
  `ask` double DEFAULT NULL,
  `ask_time` varchar(16) DEFAULT NULL,
  `asksz` bigint(20) DEFAULT NULL,
  `basis` double DEFAULT NULL,
  `bid` double DEFAULT NULL,
  `bid_time` varchar(16) DEFAULT NULL,
  `bidsz` bigint(20) DEFAULT NULL,
  `chg` double DEFAULT NULL,
  `chg_sign` char(1) DEFAULT NULL,
  `chg_t` double DEFAULT NULL,
  `cl` double DEFAULT NULL,
  `contract_size` bigint(20) DEFAULT NULL,
  `date` timestamp NULL DEFAULT NULL,
  `datetime` timestamp NULL DEFAULT NULL,
  `days_to_expiration` bigint(20) DEFAULT NULL,
  `exch` varchar(32) DEFAULT NULL,
  `exch_desc` varchar(32) DEFAULT NULL,
  `hi` double DEFAULT NULL,
  `idelta` double DEFAULT NULL,
  `igamma` double DEFAULT NULL,
  `imp_volatility` double DEFAULT NULL,
  `incr_vl` int(11) DEFAULT NULL,
  `irho` double DEFAULT NULL,
  `issue_desc` varchar(255) DEFAULT NULL,
  `itheta` double DEFAULT NULL,
  `ivega` double DEFAULT NULL,
  `last` double DEFAULT NULL,
  `lo` double DEFAULT NULL,
  `op_delivery` varchar(16) DEFAULT NULL,
  `op_flag` int(11) DEFAULT NULL,
  `op_style` varchar(16) DEFAULT NULL,
  `op_subclass` int(11) DEFAULT NULL,
  `openinterest` int(11) DEFAULT NULL,
  `opn` double DEFAULT NULL,
  `opt_val` double DEFAULT NULL,
  `pchg` varchar(16) DEFAULT NULL,
  `pchg_sign` varchar(16) DEFAULT NULL,
  `pcls` double DEFAULT NULL,
  `phi` double DEFAULT NULL,
  `plo` double DEFAULT NULL,
  `popn` varchar(16) DEFAULT NULL,
  `pr_date` timestamp NULL DEFAULT NULL,
  `pr_openinterest` int(11) DEFAULT NULL,
  `prchag` varchar(16) DEFAULT NULL,
  `prchg` double DEFAULT NULL,
  `prem_mult` bigint(20) DEFAULT NULL,
  `put_call` varchar(16) DEFAULT NULL,
  `pvol` bigint(20) DEFAULT NULL,
  `qcond` bigint(20) DEFAULT NULL,
  `rootsymbol` varchar(16) DEFAULT NULL,
  `secclass` varchar(16) DEFAULT NULL,
  `sesn` varchar(16) DEFAULT NULL,
  `strikeprice` double DEFAULT NULL,
  `symbol` varchar(32) DEFAULT NULL,
  `tcond` varchar(16) DEFAULT NULL,
  `timestamp` timestamp NULL DEFAULT NULL,
  `tr_num` bigint(20) DEFAULT NULL,
  `tradetick` varchar(32) DEFAULT NULL,
  `trend` varchar(16) DEFAULT NULL,
  `under_cusip` varchar(16) DEFAULT NULL,
  `undersymbol` varchar(16) DEFAULT NULL,
  `vl` bigint(20) DEFAULT NULL,
  `vwap` double DEFAULT NULL,
  `wk52hi` double DEFAULT NULL,
  `wk52hidate` timestamp NULL DEFAULT NULL,
  `wk52lo` double DEFAULT NULL,
  `wk52lodate` timestamp NULL DEFAULT NULL,
  `xdate` timestamp NULL DEFAULT NULL,
  `persistence_version` int(11) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `stock_quote_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `stock_quote_id_index` (`stock_quote_id`),
  KEY `created` (`created`)
) ENGINE=InnoDB AUTO_INCREMENT=6099630 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stock_quote`
--

DROP TABLE IF EXISTS `stock_quote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stock_quote` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `adp_100` double DEFAULT NULL,
  `adp_200` double DEFAULT NULL,
  `adp_50` double DEFAULT NULL,
  `adv_21` int(11) DEFAULT NULL,
  `adv_30` int(11) DEFAULT NULL,
  `adv_90` int(11) DEFAULT NULL,
  `ask` double DEFAULT NULL,
  `ask_time` varchar(16) DEFAULT NULL,
  `asksz` int(11) DEFAULT NULL,
  `basis` int(11) DEFAULT NULL,
  `beta` double DEFAULT NULL,
  `bid` double DEFAULT NULL,
  `bid_time` varchar(16) DEFAULT NULL,
  `bidsz` int(11) DEFAULT NULL,
  `bidtick` int(11) DEFAULT NULL,
  `chg` double DEFAULT NULL,
  `chg_sign` char(1) DEFAULT NULL,
  `chg_t` double DEFAULT NULL,
  `cl` double DEFAULT NULL,
  `cusip` varchar(32) DEFAULT NULL,
  `date` timestamp NULL DEFAULT NULL,
  `datetime` timestamp NULL DEFAULT NULL,
  `div` double DEFAULT NULL,
  `divexdate` timestamp NULL DEFAULT NULL,
  `divfreq` char(1) DEFAULT NULL,
  `divpaydt` timestamp NULL DEFAULT NULL,
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
  `pr_date` timestamp NULL DEFAULT NULL,
  `prbook` double DEFAULT NULL,
  `prchg` double DEFAULT NULL,
  `prime_exch` varchar(16) DEFAULT NULL,
  `pvol` int(11) DEFAULT NULL,
  `qcond` int(11) DEFAULT NULL,
  `secclass` int(11) DEFAULT NULL,
  `sesn` varchar(64) DEFAULT NULL,
  `sesn_vl` int(11) DEFAULT NULL,
  `sho` bigint(20) DEFAULT NULL,
  `symbol` varchar(16) DEFAULT NULL,
  `tcond` char(1) DEFAULT NULL,
  `timestamp` timestamp NULL DEFAULT NULL,
  `tr_num` int(11) DEFAULT NULL,
  `tradetick` char(1) DEFAULT NULL,
  `trend` varchar(16) DEFAULT NULL,
  `vl` bigint(20) DEFAULT NULL,
  `volatility12` double DEFAULT NULL,
  `vwap` double DEFAULT NULL,
  `wk52hi` double DEFAULT NULL,
  `wk52hidate` timestamp NULL DEFAULT NULL,
  `wk52lo` double DEFAULT NULL,
  `wk52lodate` timestamp NULL DEFAULT NULL,
  `yield` double DEFAULT NULL,
  `persistence_version` int(11) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `stock_quote_symbol_index` (`symbol`)
) ENGINE=InnoDB AUTO_INCREMENT=7809 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `strategy`
--

DROP TABLE IF EXISTS `strategy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `strategy` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  `classname` varchar(128) DEFAULT NULL,
  `persistence_version` int(11) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-07-27 15:04:30
