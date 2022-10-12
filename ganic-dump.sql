-- MariaDB dump 10.19  Distrib 10.9.3-MariaDB, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: ganic
-- ------------------------------------------------------
-- Server version	10.9.3-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Ingrediente`
--

DROP TABLE IF EXISTS `Ingrediente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Ingrediente` (
  `idIngrediente` bigint(20) NOT NULL,
  `detalle` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `paso` int(11) DEFAULT NULL,
  `precio` float DEFAULT NULL,
  `esApto` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`idIngrediente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Ingrediente`
--

LOCK TABLES `Ingrediente` WRITE;
/*!40000 ALTER TABLE `Ingrediente` DISABLE KEYS */;
INSERT INTO `Ingrediente` VALUES
(5,'Pan lactal blanco','Pan clasico',1,150,'SinRestriccion'),
(6,'Pan de mesa blanco','Pan flauta',1,120,'SinRestriccion'),
(7,'Pan de campo blanco','Pan de campo',1,250,'SinRestriccion'),
(8,'Pan lactal integral','Pan integral',1,280,'SinRestriccion'),
(9,'Carne de ternera a la parrilla','Medallo de carne',2,450,'SinRestriccion'),
(10,'Bondiola de cerdo a la parrilla','Cerdo',2,450,'SinRestriccion'),
(11,'Milanesa de pollo','Mila-pollo',2,450,'SinRestriccion'),
(12,'Milanesa de soja','Vegetalizima',2,400,'SinRestriccion'),
(13,'Salteado de verduras','Mix verde',2,380,'SinRestriccion'),
(14,'Salsa de barbacoa','Barbacoa',3,150,'SinRestriccion'),
(15,'Pure de papas','Pure',3,200,'SinRestriccion'),
(16,'Mix mostaza y mayonesa','Mostayesa',3,150,'SinRestriccion'),
(17,'Guacamole y mayonesa','La casa',3,250,'SinRestriccion'),
(18,'Pan integral vegano','Pan integral vegano',1,350,'Vegano'),
(19,'Pan de hamburguesa','Pan de hamburguesa',1,400,'Vegano'),
(20,'Pan de hamburguesa','Pan de hamburguesa',1,400,'sin_TACC'),
(21,'Pan de miga blanco','Pan de Miga',1,400,'sin_TACC'),
(22,'Milanesa de ternera','Milanesa de carne',2,500,'sin_TACC'),
(23,'Hamburguesa de carne','Hamburguesa',2,500,'sin_TACC'),
(24,'Hamburguesa de pollo','Milanesa de pollo',2,500,'sin_TACC'),
(25,'Hamburguesa de Lenteja','MIlanesa de lenteja',2,500,'Vegano'),
(26,'GArbanzos cocidos con aguacate','Garbanzo',2,500,'Vegano'),
(27,'Salsa de barbacoa','Barbacoa',3,150,'sin_TACC'),
(28,'Salsa al pesto','Salsa al pesto',3,150,'Vegano'),
(29,'Mayonesa vegana','Veganesa',3,180,'Vegano');
/*!40000 ALTER TABLE `Ingrediente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Pedido`
--

DROP TABLE IF EXISTS `Pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Pedido` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pedido`
--

LOCK TABLES `Pedido` WRITE;
/*!40000 ALTER TABLE `Pedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `Pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Sandwich`
--

DROP TABLE IF EXISTS `Sandwich`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Sandwich` (
  `idSandwich` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`idSandwich`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sandwich`
--

LOCK TABLES `Sandwich` WRITE;
/*!40000 ALTER TABLE `Sandwich` DISABLE KEYS */;
/*!40000 ALTER TABLE `Sandwich` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Usuario`
--

DROP TABLE IF EXISTS `Usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Usuario` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `apellido` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `direccion` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `preferencia` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Usuario`
--

LOCK TABLES `Usuario` WRITE;
/*!40000 ALTER TABLE `Usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `Usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compuesto_por`
--

DROP TABLE IF EXISTS `compuesto_por`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compuesto_por` (
  `idSandwich` bigint(20) NOT NULL,
  `idIngrediente` bigint(20) NOT NULL,
  PRIMARY KEY (`idSandwich`,`idIngrediente`),
  KEY `FKh0fp8hwcwr091ii0cc99i2vhn` (`idIngrediente`),
  CONSTRAINT `FK4wdawm4jec9dattj0fdkhhetf` FOREIGN KEY (`idSandwich`) REFERENCES `Sandwich` (`idSandwich`),
  CONSTRAINT `FKh0fp8hwcwr091ii0cc99i2vhn` FOREIGN KEY (`idIngrediente`) REFERENCES `Ingrediente` (`idIngrediente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compuesto_por`
--

LOCK TABLES `compuesto_por` WRITE;
/*!40000 ALTER TABLE `compuesto_por` DISABLE KEYS */;
/*!40000 ALTER TABLE `compuesto_por` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES
(139);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-12  1:01:22
