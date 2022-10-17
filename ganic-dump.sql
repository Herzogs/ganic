-- MariaDB dump 10.19  Distrib 10.9.3-MariaDB, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: 
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
-- Current Database: `ganic`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `ganic` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;

USE `ganic`;

--
-- Table structure for table `Ingrediente`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Ingrediente` (
  `idIngrediente` bigint(20) NOT NULL,
  `detalle` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `esApto` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `paso` int(11) DEFAULT NULL,
  `precio` float DEFAULT NULL,
  PRIMARY KEY (`idIngrediente`)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Ingrediente`
--

INSERT INTO `Ingrediente` VALUES
(1,'Pan lactal blanco','SinRestriccion','Pan clasico',1,150),
(2,'Pan de mesa blanco','SinRestriccion','Pan flauta',1,120),
(3,'Pan de campo blanco','SinRestriccion','Pan de campo',1,250),
(4,'Pan lactal integral','SinRestriccion','Pan integral',1,280),
(5,'Carne de ternera a la parrilla','SinRestriccion','Medallo de carne',2,450),
(6,'Bondiola de cerdo a la parrilla','SinRestriccion','Cerdo',2,450),
(7,'Milanesa de pollo','SinRestriccion','Mila-pollo',2,450),
(8,'Milanesa de soja','SinRestriccion','Vegetalizima',2,400),
(9,'Salteado de verduras','SinRestriccion','Mix verde',2,380),
(10,'Salsa de barbacoa','SinRestriccion','Barbacoa',3,150),
(11,'Pure de papas','SinRestriccion','Pure',3,200),
(12,'Mix mostaza y mayonesa','SinRestriccion','Mostayesa',3,150),
(13,'Guacamole y mayonesa','SinRestriccion','La casa',3,250),
(14,'Pan integral vegano','Vegano','Pan integral vegano',1,350),
(15,'Pan de hamburguesa','Vegano','Pan de hamburguesa',1,400),
(16,'Pan de hamburguesa','sin_TACC','Pan de hamburguesa',1,400),
(17,'Pan de miga blanco','sin_TACC','Pan de Miga',1,400),
(18,'Milanesa de ternera','sin_TACC','Milanesa de carne',2,500),
(19,'Hamburguesa de carne','sin_TACC','Hamburguesa',2,500),
(20,'Hamburguesa de pollo','sin_TACC','Milanesa de pollo',2,500),
(21,'Hamburguesa de Lenteja','Vegano','MIlanesa de lenteja',2,500),
(22,'GArbanzos cocidos con aguacate','Vegano','Garbanzo',2,500),
(23,'Salsa de barbacoa','sin_TACC','Barbacoa',3,150),
(24,'Salsa al pesto','Vegano','Salsa al pesto',3,150),
(25,'Mayonesa vegana','Vegano','Veganesa',3,180);

--
-- Table structure for table `Pedido`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Pedido` (
  `id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pedido`
--


--
-- Table structure for table `Sandwich`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Sandwich` (
  `idSandwich` bigint(20) NOT NULL,
  `descripcion` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `enPromocion` int(11) DEFAULT NULL,
  `esApto` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`idSandwich`)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sandwich`
--

INSERT INTO `Sandwich` VALUES
(1,'Sandwich de pan integral con garbanzo cocido con aguacate y mayonesa',1,'Vegano','Sandwich de garbanzo'),
(2,'Hamburguesa vegana de lenteja',1,'Vegano','Hamburguesa vegana de lenteja'),
(3,'Hamburguesa de carne con salsa barbacoa',1,'sin_TACC','Hamburguesa de carne'),
(4,'Sandwich de milanesa de carne con salsa barbacoa',1,'sin_TACC','Sandwich de milanesa'),
(5,'Sandwich hecho con pan de campo, bondiola y con mix de mostaza con mayonesa',0,'SinRestriccion','Bondiola'),
(6,'Sandwich hecho con pan blanco, carne de ternera a la parrilla y salsa barbacoa',1,'SinRestriccion','A la parri');

--
-- Table structure for table `Usuario`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Usuario` (
  `id` bigint(20) NOT NULL,
  `apellido` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `direccion` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nombre` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `preferencia` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Usuario`
--


--
-- Table structure for table `compuesto_por`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `compuesto_por` (
  `idSandwich` bigint(20) NOT NULL,
  `idIngrediente` bigint(20) NOT NULL,
  PRIMARY KEY (`idSandwich`,`idIngrediente`),
  KEY `FKh0fp8hwcwr091ii0cc99i2vhn` (`idIngrediente`),
  CONSTRAINT `FK4wdawm4jec9dattj0fdkhhetf` FOREIGN KEY (`idSandwich`) REFERENCES `Sandwich` (`idSandwich`),
  CONSTRAINT `FKh0fp8hwcwr091ii0cc99i2vhn` FOREIGN KEY (`idIngrediente`) REFERENCES `Ingrediente` (`idIngrediente`)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compuesto_por`
--

INSERT INTO `compuesto_por` VALUES
(1,14),
(1,22),
(1,25),
(2,19),
(2,21),
(2,25),
(3,16),
(3,19),
(3,23),
(4,17),
(4,18),
(4,23),
(5,3),
(5,6),
(5,12),
(6,2),
(6,5),
(6,10);

--
-- Table structure for table `hibernate_sequence`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` VALUES
(25);
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-10-17 18:02:34
