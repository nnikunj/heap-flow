USE heapdb;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `heapdb.egress_ledgers` (
  `id` bigint NOT NULL,
  `creation` datetime(6) NOT NULL,
  `modified` datetime(6) NOT NULL,
  `version` int NOT NULL,
  `classification_category` varchar(255) DEFAULT NULL,
  `outgoing_quantity` double NOT NULL,
  `record_date` date NOT NULL,
  `machine_fk` bigint DEFAULT NULL,
  `inventory_type_fk` bigint DEFAULT NULL,
  `inventory_item_fk` bigint DEFAULT NULL,
  `issued_to` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs7iylwjuh7av0ocehj60dme04` (`machine_fk`),
  KEY `FK6y18cv81rof3qmmef8ot0np5t` (`inventory_type_fk`),
  KEY `FK2kr8s345pp1dy0y7rfxfsq909` (`inventory_item_fk`),
  CONSTRAINT `FK2kr8s345pp1dy0y7rfxfsq909` FOREIGN KEY (`inventory_item_fk`) REFERENCES `inventory_items` (`id`),
  CONSTRAINT `FK6y18cv81rof3qmmef8ot0np5t` FOREIGN KEY (`inventory_type_fk`) REFERENCES `inventory_types` (`id`),
  CONSTRAINT `FKs7iylwjuh7av0ocehj60dme04` FOREIGN KEY (`machine_fk`) REFERENCES `machines` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `heapdb.hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `heapdb.ingress_ledgers` (
  `id` bigint NOT NULL,
  `creation` datetime(6) NOT NULL,
  `modified` datetime(6) NOT NULL,
  `version` int NOT NULL,
  `classification_category` varchar(255) DEFAULT NULL,
  `grn_number` varchar(255) DEFAULT NULL,
  `incoming_quantity` double NOT NULL,
  `invoice_number` varchar(255) DEFAULT NULL,
  `price_per_unit` double NOT NULL,
  `record_date` date NOT NULL,
  `inventory_item_fk` bigint DEFAULT NULL,
  `inventory_type_fk` bigint DEFAULT NULL,
  `vendor_fk` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK91hgfl1f200r7g379ifoy2wd5` (`inventory_item_fk`),
  KEY `FKrsesy9wbn7v09g8mb25wuxbpc` (`inventory_type_fk`),
  KEY `FK5430d8ae4hw617mpybskgxrt6` (`vendor_fk`),
  CONSTRAINT `FK5430d8ae4hw617mpybskgxrt6` FOREIGN KEY (`vendor_fk`) REFERENCES `vendors` (`id`),
  CONSTRAINT `FK91hgfl1f200r7g379ifoy2wd5` FOREIGN KEY (`inventory_item_fk`) REFERENCES `inventory_items` (`id`),
  CONSTRAINT `FKrsesy9wbn7v09g8mb25wuxbpc` FOREIGN KEY (`inventory_type_fk`) REFERENCES `inventory_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `heapdb.inventories` (
  `id` bigint NOT NULL,
  `creation` datetime(6) NOT NULL,
  `modified` datetime(6) NOT NULL,
  `version` int NOT NULL,
  `average_unit_price` double NOT NULL,
  `quantity` double NOT NULL,
  `inventory_item_fk` bigint DEFAULT NULL,
  `inventory_type_fk` bigint DEFAULT NULL,
  `notes` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK13jf030vsc5spj0lgad34jnxt` (`inventory_item_fk`),
  KEY `FK9ildtna9cp9mbr9h9ijm8xp33` (`inventory_type_fk`),
  CONSTRAINT `FK13jf030vsc5spj0lgad34jnxt` FOREIGN KEY (`inventory_item_fk`) REFERENCES `inventory_items` (`id`),
  CONSTRAINT `FK9ildtna9cp9mbr9h9ijm8xp33` FOREIGN KEY (`inventory_type_fk`) REFERENCES `inventory_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 /*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `heapdb.inventory_items` (
  `id` bigint NOT NULL,
  `creation` datetime(6) NOT NULL,
  `modified` datetime(6) NOT NULL,
  `version` int NOT NULL,
  `inventory_item_code` varchar(32) NOT NULL,
  `base_unit_measure` varchar(32) DEFAULT NULL,
  `descriptions` longtext,
  `gen_product_posting_group` varchar(32) DEFAULT NULL,
  `gst_grp_code` varchar(32) DEFAULT NULL,
  `hsn_sac_code` varchar(10) DEFAULT NULL,
  `item_category_code` varchar(32) DEFAULT NULL,
  `product_group_code` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `heapdb.inventory_types` (
  `id` bigint NOT NULL,
  `creation` datetime(6) NOT NULL,
  `modified` datetime(6) NOT NULL,
  `version` int NOT NULL,
  `description` varchar(256) NOT NULL,
  `is_considered_for_valuation` bit(1) NOT NULL,
  `type_name` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `heapdb.machines` (
  `id` bigint NOT NULL,
  `creation` datetime(6) NOT NULL,
  `modified` datetime(6) NOT NULL,
  `version` int NOT NULL,
  `category` varchar(256) DEFAULT NULL,
  `code` varchar(256) DEFAULT NULL,
  `kw_kva` varchar(32) DEFAULT NULL,
  `make` varchar(256) DEFAULT NULL,
  `model` varchar(256) DEFAULT NULL,
  `name` varchar(256) NOT NULL,
  `serial_no` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE IF NOT EXISTS `heapdb.vendors` (
  `id` bigint NOT NULL,
  `creation` datetime(6) NOT NULL,
  `modified` datetime(6) NOT NULL,
  `version` int NOT NULL,
  `address` varchar(512) DEFAULT NULL,
  `address2` varchar(512) DEFAULT NULL,
  `city` varchar(128) DEFAULT NULL,
  `contact_person` varchar(128) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `gst_number` varchar(256) DEFAULT NULL,
  `name` varchar(256) NOT NULL,
  `pan_number` varchar(32) DEFAULT NULL,
  `phone` varchar(128) DEFAULT NULL,
  `search_name` varchar(256) DEFAULT NULL,
  `state_code` varchar(10) DEFAULT NULL,
  `vendor_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
