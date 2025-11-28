-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: dbcafeteria
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `auditoria`
--

DROP TABLE IF EXISTS `auditoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auditoria` (
  `id_auditoria` int(11) NOT NULL AUTO_INCREMENT,
  `tabla` varchar(50) NOT NULL,
  `operacion` enum('INSERT','UPDATE','DELETE') NOT NULL,
  `id_registro` int(11) NOT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `datos_anteriores` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`datos_anteriores`)),
  `datos_nuevos` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`datos_nuevos`)),
  `ip_address` varchar(45) DEFAULT NULL,
  `fecha_hora` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_auditoria`),
  KEY `idx_auditoria_tabla` (`tabla`),
  KEY `idx_auditoria_fecha` (`fecha_hora`),
  KEY `idx_auditoria_usuario` (`id_usuario`),
  CONSTRAINT `auditoria_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditoria`
--

LOCK TABLES `auditoria` WRITE;
/*!40000 ALTER TABLE `auditoria` DISABLE KEYS */;
/*!40000 ALTER TABLE `auditoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `caja`
--

DROP TABLE IF EXISTS `caja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `caja` (
  `id_caja` int(11) NOT NULL AUTO_INCREMENT,
  `id_empleado` int(11) NOT NULL,
  `fecha_apertura` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_cierre` timestamp NULL DEFAULT NULL,
  `saldo_inicial` decimal(10,2) NOT NULL DEFAULT 0.00,
  `total_efectivo` decimal(10,2) DEFAULT 0.00,
  `total_tarjeta` decimal(10,2) DEFAULT 0.00,
  `total_transferencia` decimal(10,2) DEFAULT 0.00,
  `total_qr` decimal(10,2) DEFAULT 0.00,
  `total_ingresos` decimal(10,2) DEFAULT 0.00,
  `total_egresos` decimal(10,2) DEFAULT 0.00,
  `saldo_sistema` decimal(10,2) DEFAULT 0.00,
  `saldo_fisico` decimal(10,2) DEFAULT NULL,
  `diferencia` decimal(10,2) GENERATED ALWAYS AS (`saldo_fisico` - `saldo_sistema`) STORED,
  `cantidad_facturas` int(11) DEFAULT 0,
  `estado` enum('Abierta','Cerrada','Cuadrada','ConDiferencia') DEFAULT 'Abierta',
  `notas` text DEFAULT NULL,
  PRIMARY KEY (`id_caja`),
  KEY `idx_caja_empleado` (`id_empleado`),
  KEY `idx_caja_fecha` (`fecha_apertura`),
  KEY `idx_caja_estado` (`estado`),
  CONSTRAINT `caja_ibfk_1` FOREIGN KEY (`id_empleado`) REFERENCES `empleado` (`id_empleado`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `caja`
--

LOCK TABLES `caja` WRITE;
/*!40000 ALTER TABLE `caja` DISABLE KEYS */;
INSERT INTO `caja` (`id_caja`, `id_empleado`, `fecha_apertura`, `fecha_cierre`, `saldo_inicial`, `total_efectivo`, `total_tarjeta`, `total_transferencia`, `total_qr`, `total_ingresos`, `total_egresos`, `saldo_sistema`, `saldo_fisico`, `cantidad_facturas`, `estado`, `notas`) VALUES (1,2,'2025-11-05 12:00:00','2025-11-05 22:00:00',500.00,424.37,67.15,0.00,83.62,575.14,0.00,1075.14,1075.00,3,'Cuadrada','Día normal'),(2,2,'2025-11-06 12:00:00','2025-11-06 22:00:00',500.00,247.46,0.00,178.48,0.00,425.94,0.00,925.94,926.00,3,'ConDiferencia','Diferencia mínima');
/*!40000 ALTER TABLE `caja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `id_categoria` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `icono` varchar(50) DEFAULT '?️',
  `color_hex` varchar(7) DEFAULT '#3498db',
  `orden_display` int(11) DEFAULT 0,
  `mostrar_en_menu` tinyint(1) DEFAULT 1,
  `activo` tinyint(1) DEFAULT 1,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_actualizacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id_categoria`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `idx_categoria_nombre` (`nombre`),
  KEY `idx_categoria_orden` (`orden_display`),
  KEY `idx_categoria_activo` (`activo`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Bebidas Calientes','Café, té, chocolate caliente y bebidas reconfortantes','☕','#D35400',1,1,1,'2025-11-07 23:37:23','2025-11-07 23:37:23'),(2,'Bebidas Frías','Jugos naturales, smoothies, frappes y refrescos','🧃','#3498DB',2,1,1,'2025-11-07 23:37:23','2025-11-07 23:37:23'),(3,'Desayunos','Opciones nutritivas para empezar el día con energía','🍳','#F39C12',3,1,1,'2025-11-07 23:37:23','2025-11-07 23:37:23'),(4,'Postres','Dulces tentaciones, pasteles, helados y más','🍰','#E74C3C',4,1,1,'2025-11-07 23:37:23','2025-11-07 23:37:23'),(5,'Snacks','Bocadillos rápidos, sandwiches y entradas ligeras','🥪','#16A085',5,1,1,'2025-11-07 23:37:23','2025-11-07 23:37:23'),(6,'Comidas Ligeras','Sopas, wraps, ensaladas saludables y balanceadas','🥗','#27AE60',6,1,0,'2025-11-07 23:37:23','2025-11-10 02:50:17'),(7,'Especialidades','Platos especiales de la casa, únicos e irresistibles','⭐','#8E44AD',7,1,1,'2025-11-07 23:37:23','2025-11-07 23:37:23'),(8,'Combos y Promociones','Ofertas especiales y combos con descuento','🎁','#E67E22',8,1,1,'2025-11-07 23:37:23','2025-11-07 23:37:23');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `id_cliente` int(11) NOT NULL AUTO_INCREMENT,
  `id_persona` int(11) NOT NULL,
  `puntos_fidelidad` int(11) DEFAULT 0,
  `nivel_fidelidad` enum('Básico','Silver','Gold','VIP') DEFAULT 'Básico',
  `fecha_primera_compra` timestamp NULL DEFAULT NULL,
  `total_gastado` decimal(12,2) DEFAULT 0.00,
  `cantidad_visitas` int(11) DEFAULT 0,
  `preferencias` text DEFAULT NULL,
  `fecha_registro` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_cliente`),
  UNIQUE KEY `id_persona` (`id_persona`),
  KEY `idx_cliente_persona` (`id_persona`),
  KEY `idx_cliente_nivel` (`nivel_fidelidad`),
  KEY `idx_cliente_puntos` (`puntos_fidelidad`),
  CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id_persona`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,1,250,'VIP','2024-01-15 14:30:00',2500.00,25,NULL,'2025-11-07 23:36:55'),(2,2,120,'Gold','2024-02-20 18:15:00',1200.00,15,NULL,'2025-11-07 23:36:55'),(3,6,350,'VIP','2023-12-05 13:00:00',3500.00,42,NULL,'2025-11-07 23:36:55'),(4,9,65,'Silver','2024-05-10 20:45:00',650.00,8,NULL,'2025-11-07 23:36:55'),(5,10,15,'Básico','2024-10-01 16:00:00',150.00,2,NULL,'2025-11-07 23:36:55'),(6,11,180,'Gold','2024-03-12 15:20:00',1800.00,18,NULL,'2025-11-07 23:36:55'),(7,14,95,'Silver','2024-04-08 17:30:00',950.00,10,NULL,'2025-11-07 23:36:55'),(8,18,220,'VIP','2023-11-20 19:00:00',2200.00,28,NULL,'2025-11-07 23:36:55'),(9,20,45,'Básico','2024-07-15 14:15:00',450.00,5,NULL,'2025-11-07 23:36:55'),(10,22,160,'Gold','2024-01-30 18:45:00',1600.00,16,NULL,'2025-11-07 23:36:55'),(11,24,80,'Silver','2024-06-05 16:30:00',800.00,9,NULL,'2025-11-07 23:36:55'),(12,26,290,'VIP','2024-02-10 13:30:00',2900.00,35,NULL,'2025-11-07 23:36:55'),(13,28,35,'Básico','2024-08-20 20:00:00',350.00,4,NULL,'2025-11-07 23:36:55'),(14,30,125,'Gold','2024-03-25 15:00:00',1250.00,13,NULL,'2025-11-07 23:36:55'),(15,12,50,'Básico','2024-09-10 17:45:00',500.00,6,NULL,'2025-11-07 23:36:55'),(16,31,0,'Básico',NULL,0.00,0,NULL,'2025-11-24 15:07:48');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_pedido`
--

DROP TABLE IF EXISTS `detalle_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_pedido` (
  `id_detalle` int(11) NOT NULL AUTO_INCREMENT,
  `id_pedido` int(11) NOT NULL,
  `id_plato` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL CHECK (`cantidad` > 0),
  `precio_unitario` decimal(8,2) NOT NULL,
  `subtotal` decimal(10,2) GENERATED ALWAYS AS (`cantidad` * `precio_unitario`) STORED,
  `descuento_aplicado` decimal(10,2) DEFAULT 0.00,
  `notas_especiales` text DEFAULT NULL,
  `estado` enum('Pendiente','EnPreparacion','Listo','Servido','Cancelado') DEFAULT 'Pendiente',
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_detalle`),
  KEY `idx_detalle_pedido` (`id_pedido`),
  KEY `idx_detalle_plato` (`id_plato`),
  KEY `idx_detalle_estado` (`estado`),
  KEY `idx_detalle_pedido_estado` (`id_pedido`,`estado`),
  CONSTRAINT `detalle_pedido_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id_pedido`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `detalle_pedido_ibfk_2` FOREIGN KEY (`id_plato`) REFERENCES `plato` (`id_plato`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_pedido`
--

LOCK TABLES `detalle_pedido` WRITE;
/*!40000 ALTER TABLE `detalle_pedido` DISABLE KEYS */;
INSERT INTO `detalle_pedido` (`id_detalle`, `id_pedido`, `id_plato`, `cantidad`, `precio_unitario`, `descuento_aplicado`, `notas_especiales`, `estado`, `fecha_creacion`) VALUES (28,1,3,2,10.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(29,1,23,1,35.00,0.00,'Sin cebolla','Servido','2025-11-07 23:45:03'),(30,1,33,1,28.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(31,1,12,1,18.00,0.00,'Extra frutilla','Servido','2025-11-07 23:45:03'),(32,2,4,1,15.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(33,2,48,1,18.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(34,2,36,1,15.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(35,3,16,2,10.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(36,3,50,2,26.00,0.00,'Sin papas','Servido','2025-11-07 23:45:03'),(37,3,39,1,28.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(38,4,51,1,45.00,0.00,'Término medio','Servido','2025-11-07 23:45:03'),(39,4,56,1,35.00,0.00,'Extra queso','Servido','2025-11-07 23:45:03'),(40,4,21,2,23.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(41,4,31,1,27.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(42,5,42,1,20.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(43,5,17,1,12.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(44,5,3,1,10.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(45,6,57,1,42.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(46,6,43,2,15.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(47,6,14,1,20.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(48,6,32,1,20.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(49,6,4,2,15.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(50,7,51,1,45.00,0.00,'Término medio','Listo','2025-11-07 23:45:03'),(51,7,56,1,35.00,0.00,'Extra queso','Listo','2025-11-07 23:45:03'),(52,7,20,2,22.00,0.00,NULL,'Listo','2025-11-07 23:45:03'),(53,7,30,1,25.00,0.00,NULL,'EnPreparacion','2025-11-07 23:45:03'),(54,7,17,3,12.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(55,8,57,2,42.00,0.00,'Sin cebolla','Listo','2025-11-07 23:45:03'),(56,8,53,1,42.00,0.00,NULL,'Listo','2025-11-07 23:45:03'),(57,8,52,1,38.00,0.00,'Sin ajo','Listo','2025-11-07 23:45:03'),(58,8,34,1,22.00,0.00,NULL,'Listo','2025-11-07 23:45:03'),(59,8,9,2,8.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(60,9,26,1,32.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(61,9,18,1,12.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(62,9,25,1,28.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(63,10,54,1,40.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(64,10,45,1,30.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(65,10,13,1,18.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(66,11,55,1,32.00,0.00,NULL,'EnPreparacion','2025-11-07 23:45:03'),(67,11,5,2,17.00,0.00,NULL,'Listo','2025-11-07 23:45:03'),(68,11,35,1,32.00,0.00,NULL,'Pendiente','2025-11-07 23:45:03'),(69,12,58,1,40.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(70,12,43,1,15.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(71,12,46,1,28.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(72,12,6,2,16.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(73,13,28,1,25.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(74,13,3,1,10.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(75,14,52,2,38.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(76,14,49,2,22.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(77,14,36,2,15.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(78,15,40,2,24.00,0.00,NULL,'Listo','2025-11-07 23:45:03'),(79,15,15,2,19.00,0.00,NULL,'Listo','2025-11-07 23:45:03'),(80,16,24,1,28.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(81,16,10,1,7.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(82,17,38,1,22.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(83,17,41,1,18.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(84,17,20,1,22.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(85,17,32,1,20.00,0.00,NULL,'Servido','2025-11-07 23:45:03'),(86,18,57,2,42.00,0.00,'Sin cebolla','EnPreparacion','2025-11-07 23:45:03'),(87,18,53,1,42.00,0.00,NULL,'EnPreparacion','2025-11-07 23:45:03'),(88,18,52,1,38.00,0.00,'Sin ajo','EnPreparacion','2025-11-07 23:45:03'),(89,18,31,1,27.00,0.00,NULL,'Pendiente','2025-11-07 23:45:03'),(90,18,9,4,8.00,0.00,NULL,'Listo','2025-11-07 23:45:03'),(91,19,26,1,32.00,0.00,NULL,'Pendiente','2025-11-07 23:45:03'),(92,19,14,1,20.00,0.00,NULL,'Pendiente','2025-11-07 23:45:03'),(93,19,3,1,10.00,0.00,NULL,'Pendiente','2025-11-07 23:45:03'),(94,20,25,1,28.00,0.00,'Con extra miel','Pendiente','2025-11-07 23:45:03'),(95,20,4,2,15.00,0.00,NULL,'Pendiente','2025-11-07 23:45:03'),(96,20,47,1,32.00,0.00,NULL,'Pendiente','2025-11-07 23:45:03'),(97,20,32,1,20.00,0.00,NULL,'Pendiente','2025-11-07 23:45:03');
/*!40000 ALTER TABLE `detalle_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleado`
--

DROP TABLE IF EXISTS `empleado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleado` (
  `id_empleado` int(11) NOT NULL AUTO_INCREMENT,
  `id_persona` int(11) NOT NULL,
  `cargo` enum('Administrador','Mesero','Cajero','Chef','Gerente','Barista') NOT NULL,
  `codigo_trabajador` varchar(20) NOT NULL,
  `fecha_contratacion` date NOT NULL,
  `salario_base` decimal(10,2) DEFAULT NULL CHECK (`salario_base` >= 0),
  `comision_porcentaje` decimal(5,2) DEFAULT 0.00,
  `turno_preferido` enum('Mañana','Tarde','Noche','Rotativo') DEFAULT 'Rotativo',
  `estado_laboral` enum('Activo','Vacaciones','Permiso','Suspendido','Retirado') DEFAULT 'Activo',
  `activo` tinyint(1) DEFAULT 1,
  `fecha_registro` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_empleado`),
  UNIQUE KEY `id_persona` (`id_persona`),
  UNIQUE KEY `codigo_trabajador` (`codigo_trabajador`),
  KEY `idx_empleado_codigo` (`codigo_trabajador`),
  KEY `idx_empleado_cargo` (`cargo`),
  KEY `idx_empleado_estado` (`estado_laboral`),
  CONSTRAINT `empleado_ibfk_1` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id_persona`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleado`
--

LOCK TABLES `empleado` WRITE;
/*!40000 ALTER TABLE `empleado` DISABLE KEYS */;
INSERT INTO `empleado` VALUES (1,3,'Mesero','MES001','2024-01-15',2500.00,3.00,'Tarde','Activo',1,'2025-11-07 23:36:59'),(2,4,'Cajero','CAJ001','2024-02-01',2800.00,2.50,'Mañana','Activo',1,'2025-11-07 23:36:59'),(3,5,'Administrador','ADM001','2023-06-01',4500.00,5.00,'Rotativo','Activo',1,'2025-11-07 23:36:59'),(4,7,'Chef','CHF001','2023-08-10',3500.00,0.00,'Mañana','Activo',1,'2025-11-07 23:36:59'),(5,8,'Mesero','MES002','2024-03-20',2500.00,3.00,'Noche','Activo',1,'2025-11-07 23:36:59'),(6,13,'Gerente','GER001','2023-07-15',5000.00,8.00,'Rotativo','Activo',1,'2025-11-07 23:36:59'),(7,15,'Cajero','CAJ002','2024-04-10',2800.00,2.50,'Tarde','Activo',1,'2025-11-07 23:36:59'),(8,17,'Barista','BAR001','2024-01-20',2600.00,2.00,'Mañana','Activo',1,'2025-11-07 23:36:59'),(9,19,'Chef','CHF002','2024-05-05',3500.00,0.00,'Tarde','Activo',1,'2025-11-07 23:36:59'),(10,21,'Mesero','MES003','2024-06-15',2500.00,3.00,'Rotativo','Activo',1,'2025-11-07 23:36:59');
/*!40000 ALTER TABLE `empleado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `factura`
--

DROP TABLE IF EXISTS `factura`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `factura` (
  `id_factura` int(11) NOT NULL AUTO_INCREMENT,
  `id_pedido` int(11) NOT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  `numero_factura` varchar(20) NOT NULL,
  `razon_social` varchar(200) DEFAULT NULL,
  `nit` varchar(20) DEFAULT NULL,
  `fecha_hora` timestamp NOT NULL DEFAULT current_timestamp(),
  `subtotal` decimal(10,2) NOT NULL,
  `impuesto` decimal(10,2) DEFAULT 0.00,
  `descuento` decimal(10,2) DEFAULT 0.00,
  `propina` decimal(10,2) DEFAULT 0.00,
  `total` decimal(10,2) NOT NULL,
  `metodo_pago` enum('Efectivo','Tarjeta','Transferencia','QR','Mixto') NOT NULL,
  `monto_pagado` decimal(10,2) DEFAULT NULL,
  `cambio` decimal(10,2) DEFAULT NULL,
  `estado` enum('Pendiente','Pagada','Anulada','Reembolsada') DEFAULT 'Pendiente',
  `motivo_anulacion` text DEFAULT NULL,
  `codigo_autorizacion` varchar(50) DEFAULT NULL,
  `factura_electronica_url` varchar(255) DEFAULT NULL,
  `fecha_anulacion` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id_factura`),
  UNIQUE KEY `id_pedido` (`id_pedido`),
  UNIQUE KEY `numero_factura` (`numero_factura`),
  KEY `idx_factura_pedido` (`id_pedido`),
  KEY `idx_factura_numero` (`numero_factura`),
  KEY `idx_factura_fecha` (`fecha_hora`),
  KEY `idx_factura_estado` (`estado`),
  KEY `idx_factura_cliente` (`id_cliente`),
  CONSTRAINT `factura_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedido` (`id_pedido`) ON UPDATE CASCADE,
  CONSTRAINT `factura_ibfk_2` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `factura`
--

LOCK TABLES `factura` WRITE;
/*!40000 ALTER TABLE `factura` DISABLE KEYS */;
INSERT INTO `factura` VALUES (1,1,1,'F-000001','Juan Carlos Pérez Soto','1234567LP','2025-11-05 16:45:00',87.00,11.31,0.00,10.00,108.31,'Efectivo',120.00,11.69,'Pagada',NULL,NULL,NULL,NULL),(2,2,2,'F-000002','María Elena López García','2345678CB','2025-11-05 18:25:00',55.00,7.15,0.00,5.00,67.15,'Tarjeta',67.15,0.00,'Pagada',NULL,NULL,NULL,NULL),(3,3,3,'F-000003','Paola Andrea Mendoza Flores','6789012CB','2025-11-05 20:20:00',74.00,9.62,0.00,0.00,83.62,'QR',83.62,0.00,'Pagada',NULL,NULL,NULL,NULL),(4,4,4,'F-000004','Ana Lucía Torrez Mamani','4567890OR','2025-11-06 17:40:00',128.00,16.64,0.00,15.00,159.64,'Efectivo',200.00,40.36,'Pagada',NULL,NULL,NULL,NULL),(5,5,5,'F-000005','Carmen Rosa Flores Quispe','0123456LP','2025-11-06 21:50:00',42.00,5.46,0.00,0.00,47.46,'Efectivo',50.00,2.54,'Pagada',NULL,NULL,NULL,NULL),(6,6,6,'F-000006','Paola Andrea Mendoza Flores','6789012CB','2025-11-07 16:30:00',165.00,18.23,24.75,20.00,178.48,'Transferencia',178.48,0.00,'Pagada',NULL,NULL,NULL,NULL),(7,9,9,'F-000009','Roberto Miguel Sánchez Cordero','9012345SC','2025-11-08 16:05:00',68.00,8.84,0.00,0.00,76.84,'Efectivo',80.00,3.16,'Pagada',NULL,NULL,NULL,NULL),(8,10,10,'F-000010','Carmen Rosa Flores Quispe','0123456LP','2025-11-08 17:55:00',95.00,12.35,0.00,10.00,117.35,'Tarjeta',117.35,0.00,'Pagada',NULL,NULL,NULL,NULL),(9,13,13,'F-000013','Valeria Beatriz Herrera Pinto','2233445CB','2025-11-09 14:50:00',38.00,4.94,0.00,0.00,42.94,'QR',42.94,0.00,'Pagada',NULL,NULL,NULL,NULL),(10,14,14,'F-000014','Gabriela Patricia Vera Soliz','4455667OR','2025-11-09 17:15:00',156.00,20.28,0.00,18.00,194.28,'Efectivo',200.00,5.72,'Pagada',NULL,NULL,NULL,NULL),(11,16,1,'F-000016','Juan Carlos Pérez Soto','1234567LP','2025-11-10 12:45:00',35.00,4.55,0.00,0.00,39.55,'Efectivo',40.00,0.45,'Pagada',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `factura` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingrediente`
--

DROP TABLE IF EXISTS `ingrediente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingrediente` (
  `id_ingrediente` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `id_proveedor` int(11) DEFAULT NULL,
  `unidad_medida` enum('kg','g','l','ml','unidad','docena','paquete') NOT NULL,
  `stock_actual` decimal(10,2) DEFAULT 0.00,
  `stock_minimo` decimal(10,2) NOT NULL,
  `stock_maximo` decimal(10,2) DEFAULT NULL,
  `precio_unitario` decimal(8,2) NOT NULL,
  `fecha_ultima_compra` date DEFAULT NULL,
  `fecha_vencimiento` date DEFAULT NULL,
  `categoria_ingrediente` enum('Lácteos','Carnes','Vegetales','Frutas','Granos','Bebidas','Condimentos','Otro') DEFAULT 'Otro',
  `activo` tinyint(1) DEFAULT 1,
  `fecha_registro` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_ingrediente`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `idx_ingrediente_nombre` (`nombre`),
  KEY `idx_ingrediente_stock` (`stock_actual`),
  KEY `idx_ingrediente_proveedor` (`id_proveedor`),
  KEY `idx_ingrediente_stock_minimo` (`stock_actual`,`stock_minimo`),
  CONSTRAINT `ingrediente_ibfk_1` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedor` (`id_proveedor`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingrediente`
--

LOCK TABLES `ingrediente` WRITE;
/*!40000 ALTER TABLE `ingrediente` DISABLE KEYS */;
INSERT INTO `ingrediente` VALUES (1,'Café en grano premium','Café arábica de origen colombiano',4,'kg',25.00,10.00,50.00,85.00,NULL,NULL,'Bebidas',1,'2025-11-07 23:37:44'),(2,'Leche entera','Leche fresca pasteurizada',1,'l',80.00,30.00,150.00,8.50,NULL,NULL,'Lácteos',1,'2025-11-07 23:37:44'),(3,'Azúcar blanca','Azúcar refinada de caña',NULL,'kg',40.00,15.00,80.00,6.20,NULL,NULL,'Condimentos',1,'2025-11-07 23:37:44'),(4,'Chocolate en polvo','Cacao puro sin azúcar añadida',NULL,'kg',15.00,5.00,30.00,45.00,NULL,NULL,'Condimentos',1,'2025-11-07 23:37:44'),(5,'Harina de trigo','Harina todo uso',5,'kg',60.00,20.00,100.00,5.80,NULL,NULL,'Granos',1,'2025-11-07 23:37:44'),(6,'Huevos','Huevos frescos de granja',NULL,'docena',30.00,10.00,60.00,22.00,NULL,NULL,'Otro',1,'2025-11-07 23:37:44'),(7,'Queso mozzarella','Queso mozzarella rallado',1,'kg',18.00,8.00,35.00,48.00,NULL,NULL,'Lácteos',1,'2025-11-07 23:37:44'),(8,'Jamón de pierna','Jamón cocido premium',2,'kg',12.00,5.00,25.00,62.00,NULL,NULL,'Carnes',1,'2025-11-07 23:37:44'),(9,'Pechuga de pollo','Pechuga de pollo fresca',2,'kg',35.00,15.00,60.00,28.00,NULL,NULL,'Carnes',1,'2025-11-07 23:37:44'),(10,'Carne de res (lomo)','Lomo fino de res',2,'kg',20.00,8.00,40.00,72.00,NULL,NULL,'Carnes',1,'2025-11-07 23:37:44'),(11,'Tocino ahumado','Tocino en tiras',2,'kg',10.00,4.00,20.00,55.00,NULL,NULL,'Carnes',1,'2025-11-07 23:37:44'),(12,'Lechuga romana','Lechuga fresca hidropónica',3,'unidad',25.00,10.00,50.00,4.50,NULL,NULL,'Vegetales',1,'2025-11-07 23:37:44'),(13,'Tomate fresco','Tomate de mesa',3,'kg',30.00,12.00,60.00,8.50,NULL,NULL,'Vegetales',1,'2025-11-07 23:37:44'),(14,'Cebolla blanca','Cebolla nacional',3,'kg',28.00,10.00,50.00,6.80,NULL,NULL,'Vegetales',1,'2025-11-07 23:37:44'),(15,'Pimiento morrón','Pimiento de colores variados',3,'kg',15.00,6.00,30.00,12.00,NULL,NULL,'Vegetales',1,'2025-11-07 23:37:44'),(16,'Papa blanca','Papa para freír',3,'kg',50.00,20.00,100.00,5.20,NULL,NULL,'Vegetales',1,'2025-11-07 23:37:44'),(17,'Fresa fresca','Fresa de temporada',3,'kg',8.00,3.00,15.00,35.00,NULL,NULL,'Frutas',1,'2025-11-07 23:37:44'),(18,'Mango maduro','Mango dulce',3,'kg',12.00,5.00,25.00,18.00,NULL,NULL,'Frutas',1,'2025-11-07 23:37:44'),(19,'Naranja para jugo','Naranja valencia',3,'kg',40.00,15.00,80.00,7.50,NULL,NULL,'Frutas',1,'2025-11-07 23:37:44'),(20,'Limón','Limón criollo',3,'kg',20.00,8.00,40.00,9.20,NULL,NULL,'Frutas',1,'2025-11-07 23:37:44'),(21,'Pan integral','Pan de molde integral',5,'unidad',35.00,15.00,70.00,8.50,NULL,NULL,'Granos',1,'2025-11-07 23:37:44'),(22,'Croissant','Croissant francés mantecoso',5,'unidad',24.00,10.00,50.00,4.80,NULL,NULL,'Granos',1,'2025-11-07 23:37:44'),(23,'Yogurt natural','Yogurt griego sin azúcar',1,'l',15.00,6.00,30.00,18.00,NULL,NULL,'Lácteos',1,'2025-11-07 23:37:44'),(24,'Crema de leche','Crema para batir 35% grasa',1,'l',12.00,5.00,25.00,22.00,NULL,NULL,'Lácteos',1,'2025-11-07 23:37:44'),(25,'Queso crema','Queso crema tipo philadelphia',1,'kg',8.00,3.00,15.00,42.00,NULL,NULL,'Lácteos',1,'2025-11-07 23:37:44'),(26,'Aceite vegetal','Aceite de soya',NULL,'l',25.00,10.00,50.00,12.50,NULL,NULL,'Condimentos',1,'2025-11-07 23:37:44'),(27,'Sal fina','Sal de mesa yodada',NULL,'kg',30.00,10.00,60.00,3.20,NULL,NULL,'Condimentos',1,'2025-11-07 23:37:44'),(28,'Pimienta negra','Pimienta molida',NULL,'kg',5.00,2.00,10.00,85.00,NULL,NULL,'Condimentos',1,'2025-11-07 23:37:44'),(29,'Pasta seca (spaghetti)','Pasta italiana de trigo durum',NULL,'kg',40.00,15.00,80.00,14.50,NULL,NULL,'Granos',1,'2025-11-07 23:37:44'),(30,'Helado de vainilla','Helado artesanal',NULL,'l',20.00,8.00,40.00,38.00,NULL,NULL,'Otro',1,'2025-11-07 23:37:44');
/*!40000 ALTER TABLE `ingrediente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingrediente_plato`
--

DROP TABLE IF EXISTS `ingrediente_plato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingrediente_plato` (
  `id_ingrediente` int(11) NOT NULL,
  `id_plato` int(11) NOT NULL,
  `cantidad` decimal(10,2) NOT NULL CHECK (`cantidad` > 0),
  `unidad` enum('kg','g','l','ml','unidad','docena','paquete') NOT NULL,
  PRIMARY KEY (`id_ingrediente`,`id_plato`),
  KEY `idx_ingrediente_plato` (`id_plato`),
  CONSTRAINT `ingrediente_plato_ibfk_1` FOREIGN KEY (`id_ingrediente`) REFERENCES `ingrediente` (`id_ingrediente`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ingrediente_plato_ibfk_2` FOREIGN KEY (`id_plato`) REFERENCES `plato` (`id_plato`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingrediente_plato`
--

LOCK TABLES `ingrediente_plato` WRITE;
/*!40000 ALTER TABLE `ingrediente_plato` DISABLE KEYS */;
INSERT INTO `ingrediente_plato` VALUES (1,4,0.02,'kg'),(2,4,0.12,'l'),(2,11,0.25,'l'),(2,25,0.10,'l'),(2,56,0.08,'kg'),(3,4,0.01,'kg'),(3,11,0.02,'kg'),(3,12,0.02,'kg'),(3,13,0.02,'kg'),(3,25,0.03,'kg'),(3,30,0.05,'kg'),(4,11,0.03,'kg'),(4,32,0.08,'kg'),(5,32,0.06,'kg'),(5,41,0.15,'kg'),(5,42,0.15,'kg'),(5,55,0.25,'kg'),(5,56,0.25,'kg'),(5,58,0.05,'kg'),(6,23,2.00,'unidad'),(6,25,2.00,'unidad'),(6,26,3.00,'unidad'),(6,27,3.00,'unidad'),(6,39,1.00,'unidad'),(6,54,2.00,'unidad'),(7,23,0.03,'kg'),(7,26,0.03,'kg'),(7,27,0.05,'kg'),(7,41,0.15,'kg'),(7,45,0.03,'kg'),(7,53,0.05,'kg'),(7,54,0.05,'kg'),(7,55,0.15,'kg'),(7,56,0.15,'kg'),(7,57,0.05,'kg'),(7,58,0.08,'kg'),(8,23,0.05,'kg'),(8,27,0.08,'kg'),(8,58,0.05,'kg'),(9,38,0.12,'kg'),(9,39,0.10,'kg'),(9,45,0.15,'kg'),(9,52,0.25,'kg'),(9,53,0.15,'kg'),(10,42,0.18,'kg'),(10,51,0.25,'kg'),(10,57,0.20,'kg'),(10,58,0.22,'kg'),(11,39,0.05,'kg'),(11,54,0.10,'kg'),(11,57,0.03,'kg'),(12,38,2.00,'unidad'),(12,45,4.00,'unidad'),(12,52,3.00,'unidad'),(13,26,0.05,'kg'),(13,38,0.05,'kg'),(13,39,0.05,'kg'),(13,48,0.10,'kg'),(13,51,0.08,'kg'),(13,52,0.10,'kg'),(13,55,0.10,'kg'),(13,57,0.05,'kg'),(13,58,0.10,'kg'),(14,26,0.04,'kg'),(14,42,0.03,'kg'),(14,48,0.08,'kg'),(14,51,0.05,'kg'),(15,26,0.05,'kg'),(15,48,0.06,'kg'),(15,51,0.04,'kg'),(16,43,0.40,'kg'),(16,48,0.10,'kg'),(16,51,0.20,'kg'),(16,57,0.20,'kg'),(17,12,0.15,'kg'),(17,30,0.08,'kg'),(18,13,0.18,'kg'),(19,13,0.10,'l'),(21,23,2.00,'unidad'),(21,25,3.00,'unidad'),(21,38,2.00,'unidad'),(21,39,3.00,'unidad'),(23,12,0.10,'l'),(24,53,0.10,'l'),(24,54,0.10,'l'),(25,30,0.15,'kg'),(26,43,0.05,'l'),(27,43,0.01,'kg'),(29,53,0.20,'kg'),(29,54,0.20,'kg'),(30,32,0.10,'l');
/*!40000 ALTER TABLE `ingrediente_plato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesa`
--

DROP TABLE IF EXISTS `mesa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mesa` (
  `id_mesa` int(11) NOT NULL AUTO_INCREMENT,
  `numero_mesa` int(11) NOT NULL,
  `capacidad` int(11) NOT NULL CHECK (`capacidad` > 0),
  `ubicacion` enum('Interior','Terraza','VIP','Barra','Privado') DEFAULT 'Interior',
  `estado` enum('Libre','Ocupada','Reservada','Mantenimiento','Inactiva') DEFAULT 'Libre',
  `forma` enum('Cuadrada','Rectangular','Redonda','Alta') DEFAULT 'Cuadrada',
  `tiene_toma_corriente` tinyint(1) DEFAULT 0,
  `es_fumadores` tinyint(1) DEFAULT 0,
  `activo` tinyint(1) DEFAULT 1,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_mesa`),
  UNIQUE KEY `numero_mesa` (`numero_mesa`),
  KEY `idx_mesa_numero` (`numero_mesa`),
  KEY `idx_mesa_estado` (`estado`),
  KEY `idx_mesa_ubicacion` (`ubicacion`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesa`
--

LOCK TABLES `mesa` WRITE;
/*!40000 ALTER TABLE `mesa` DISABLE KEYS */;
INSERT INTO `mesa` VALUES (1,1,2,'Interior','Libre','Cuadrada',1,0,1,'2025-11-07 23:37:19'),(2,2,4,'Interior','Libre','Rectangular',1,0,1,'2025-11-07 23:37:19'),(3,3,4,'Terraza','Libre','Redonda',0,1,1,'2025-11-07 23:37:19'),(4,4,6,'Interior','Libre','Rectangular',1,0,1,'2025-11-07 23:37:19'),(5,5,8,'VIP','Libre','Rectangular',1,0,1,'2025-11-07 23:37:19'),(6,6,2,'Terraza','Libre','Cuadrada',0,1,1,'2025-11-07 23:37:19'),(7,7,4,'Interior','Libre','Redonda',1,0,1,'2025-11-07 23:37:19'),(8,8,6,'VIP','Libre','Rectangular',1,0,1,'2025-11-07 23:37:19'),(9,9,2,'Barra','Libre','Alta',1,0,1,'2025-11-07 23:37:19'),(10,10,2,'Barra','Libre','Alta',1,0,1,'2025-11-07 23:37:19'),(11,11,10,'Privado','Libre','Rectangular',1,0,1,'2025-11-07 23:37:19'),(12,12,4,'Terraza','Libre','Redonda',0,1,1,'2025-11-07 23:37:19');
/*!40000 ALTER TABLE `mesa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movimiento_caja`
--

DROP TABLE IF EXISTS `movimiento_caja`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimiento_caja` (
  `id_movimiento` int(11) NOT NULL AUTO_INCREMENT,
  `id_caja` int(11) NOT NULL,
  `tipo` enum('Ingreso','Egreso','Retiro','Deposito') NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `concepto` varchar(200) NOT NULL,
  `id_factura` int(11) DEFAULT NULL,
  `metodo` enum('Efectivo','Tarjeta','Transferencia','QR') NOT NULL,
  `usuario_registro` varchar(50) DEFAULT NULL,
  `fecha_hora` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_movimiento`),
  KEY `id_factura` (`id_factura`),
  KEY `idx_movimiento_caja` (`id_caja`),
  KEY `idx_movimiento_tipo` (`tipo`),
  KEY `idx_movimiento_fecha` (`fecha_hora`),
  CONSTRAINT `movimiento_caja_ibfk_1` FOREIGN KEY (`id_caja`) REFERENCES `caja` (`id_caja`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `movimiento_caja_ibfk_2` FOREIGN KEY (`id_factura`) REFERENCES `factura` (`id_factura`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimiento_caja`
--

LOCK TABLES `movimiento_caja` WRITE;
/*!40000 ALTER TABLE `movimiento_caja` DISABLE KEYS */;
INSERT INTO `movimiento_caja` VALUES (1,1,'Ingreso',108.31,'Venta de alimentos',1,'Efectivo','cajero1','2025-11-05 16:45:00'),(2,1,'Ingreso',67.15,'Venta de alimentos',2,'Tarjeta','cajero1','2025-11-05 18:25:00'),(3,1,'Ingreso',83.62,'Venta de alimentos',3,'QR','cajero1','2025-11-05 20:20:00'),(4,1,'Egreso',50.00,'Compra de ingredientes',NULL,'Efectivo','cajero1','2025-11-05 19:00:00'),(5,2,'Ingreso',159.64,'Venta de alimentos',4,'Efectivo','cajero1','2025-11-06 17:40:00'),(6,2,'Ingreso',47.46,'Venta de alimentos',5,'Efectivo','cajero1','2025-11-06 21:50:00'),(7,2,'Ingreso',178.48,'Venta de alimentos',6,'Transferencia','cajero1','2025-11-07 16:30:00');
/*!40000 ALTER TABLE `movimiento_caja` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oferta`
--

DROP TABLE IF EXISTS `oferta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oferta` (
  `id_oferta` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(80) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_fin` date NOT NULL,
  `tipo` enum('Descuento','Combo','Fidelidad','Festivo','2x1','HappyHour') NOT NULL,
  `porcentaje_descuento` decimal(5,2) DEFAULT 0.00 CHECK (`porcentaje_descuento` >= 0 and `porcentaje_descuento` <= 100),
  `monto_fijo` decimal(8,2) DEFAULT 0.00,
  `dias_aplicables` set('Lunes','Martes','Miércoles','Jueves','Viernes','Sábado','Domingo') DEFAULT NULL,
  `hora_inicio` time DEFAULT NULL,
  `hora_fin` time DEFAULT NULL,
  `cantidad_minima` int(11) DEFAULT 1,
  `limite_uso_por_cliente` int(11) DEFAULT NULL,
  `activa` tinyint(1) DEFAULT 1,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_oferta`),
  KEY `idx_oferta_tipo` (`tipo`),
  KEY `idx_oferta_fechas` (`fecha_inicio`,`fecha_fin`),
  KEY `idx_oferta_activa` (`activa`),
  CONSTRAINT `chk_fechas_oferta` CHECK (`fecha_inicio` <= `fecha_fin`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oferta`
--

LOCK TABLES `oferta` WRITE;
/*!40000 ALTER TABLE `oferta` DISABLE KEYS */;
INSERT INTO `oferta` VALUES (1,'Happy Hour Café','Todas las bebidas calientes 30% OFF','2025-01-01','2025-12-31','HappyHour',30.00,0.00,'Lunes,Martes,Miércoles,Jueves,Viernes','15:00:00','17:00:00',1,NULL,1,'2025-11-07 23:41:35'),(2,'2x1 en Postres','Lleva 2 postres y paga solo 1','2025-11-01','2025-11-30','2x1',50.00,0.00,'Lunes,Martes,Miércoles,Jueves','14:00:00','18:00:00',2,1,1,'2025-11-07 23:41:35'),(3,'Combo Desayuno Completo','Desayuno + bebida caliente con descuento','2025-01-01','2025-12-31','Combo',20.00,0.00,'Lunes,Martes,Miércoles,Jueves,Viernes,Sábado,Domingo','07:00:00','11:00:00',2,NULL,1,'2025-11-07 23:41:35'),(4,'Descuento Cliente VIP','Clientes VIP obtienen 15% en todo','2025-01-01','2025-12-31','Fidelidad',15.00,0.00,'Lunes,Martes,Miércoles,Jueves,Viernes,Sábado,Domingo',NULL,NULL,1,NULL,1,'2025-11-07 23:41:35'),(5,'Día del Amigo','20% de descuento en toda la carta','2025-07-20','2025-07-20','Festivo',20.00,0.00,'Sábado',NULL,NULL,1,NULL,1,'2025-11-07 23:41:35'),(6,'Black Friday','30% OFF en todo el menú','2025-11-29','2025-11-29','Festivo',30.00,0.00,'Viernes',NULL,NULL,1,NULL,1,'2025-11-07 23:41:35'),(7,'Navidad Especial','25% en especialidades y postres','2025-12-24','2025-12-26','Festivo',25.00,0.00,'Martes,Miércoles,Jueves',NULL,NULL,1,NULL,1,'2025-11-07 23:41:35'),(8,'Año Nuevo','15% de descuento general','2025-12-31','2026-01-01','Festivo',15.00,0.00,'Martes,Miércoles',NULL,NULL,1,NULL,1,'2025-11-07 23:41:35'),(9,'Combo Almuerzo Ejecutivo','Plato principal + bebida + postre','2025-01-01','2025-12-31','Combo',25.00,0.00,'Lunes,Martes,Miércoles,Jueves,Viernes','12:00:00','15:00:00',3,NULL,1,'2025-11-07 23:41:35'),(10,'Fin de Semana Familiar','10% OFF en consumo total','2025-01-01','2025-12-31','Descuento',10.00,0.00,'Sábado,Domingo',NULL,NULL,1,NULL,1,'2025-11-07 23:41:35');
/*!40000 ALTER TABLE `oferta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oferta_plato`
--

DROP TABLE IF EXISTS `oferta_plato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oferta_plato` (
  `id_oferta` int(11) NOT NULL,
  `id_plato` int(11) NOT NULL,
  `precio_oferta` decimal(8,2) DEFAULT NULL,
  PRIMARY KEY (`id_oferta`,`id_plato`),
  KEY `id_plato` (`id_plato`),
  CONSTRAINT `oferta_plato_ibfk_1` FOREIGN KEY (`id_oferta`) REFERENCES `oferta` (`id_oferta`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `oferta_plato_ibfk_2` FOREIGN KEY (`id_plato`) REFERENCES `plato` (`id_plato`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oferta_plato`
--

LOCK TABLES `oferta_plato` WRITE;
/*!40000 ALTER TABLE `oferta_plato` DISABLE KEYS */;
INSERT INTO `oferta_plato` VALUES (1,1,8.40),(1,3,7.00),(1,4,10.50),(1,6,11.20),(2,33,12.50),(2,35,10.00),(2,36,14.00),(2,37,11.00),(3,23,28.00),(3,24,22.40),(3,25,22.40);
/*!40000 ALTER TABLE `oferta_plato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido` (
  `id_pedido` int(11) NOT NULL AUTO_INCREMENT,
  `numero_pedido` varchar(20) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `id_empleado` int(11) DEFAULT NULL,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `subtotal` decimal(10,2) DEFAULT 0.00,
  `descuento` decimal(10,2) DEFAULT 0.00,
  `impuesto` decimal(10,2) DEFAULT 0.00,
  `propina` decimal(10,2) DEFAULT 0.00,
  `total` decimal(10,2) DEFAULT 0.00,
  `estado` enum('Pendiente','EnPreparacion','Listo','Servido','Pagado','Cancelado') DEFAULT 'Pendiente',
  `tipo` enum('Local','ParaLlevar','Delivery') DEFAULT 'Local',
  `tiempo_estimado_min` int(11) DEFAULT NULL,
  `notas` text DEFAULT NULL,
  `id_oferta_aplicada` int(11) DEFAULT NULL,
  `puntos_ganados` int(11) DEFAULT 0,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_actualizacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id_pedido`),
  UNIQUE KEY `numero_pedido` (`numero_pedido`),
  KEY `id_oferta_aplicada` (`id_oferta_aplicada`),
  KEY `idx_pedido_numero` (`numero_pedido`),
  KEY `idx_pedido_cliente` (`id_cliente`),
  KEY `idx_pedido_empleado` (`id_empleado`),
  KEY `idx_pedido_fecha` (`fecha`,`hora`),
  KEY `idx_pedido_estado` (`estado`),
  KEY `idx_pedido_numero_fecha` (`numero_pedido`,`fecha`),
  CONSTRAINT `pedido_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`) ON UPDATE CASCADE,
  CONSTRAINT `pedido_ibfk_2` FOREIGN KEY (`id_empleado`) REFERENCES `empleado` (`id_empleado`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `pedido_ibfk_4` FOREIGN KEY (`id_oferta_aplicada`) REFERENCES `oferta` (`id_oferta`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` VALUES (1,'PED-000001',1,1,'2025-11-05','12:30:00',101.00,0.00,13.13,10.00,114.13,'Pagado','Local',30,NULL,NULL,10,'2025-11-07 23:41:50','2025-11-07 23:45:27'),(2,'PED-000002',2,5,'2025-11-05','14:00:00',48.00,0.00,6.24,5.00,54.24,'Pagado','Local',25,NULL,NULL,6,'2025-11-07 23:41:50','2025-11-07 23:45:27'),(3,'PED-000003',3,1,'2025-11-05','16:00:00',100.00,0.00,13.00,0.00,113.00,'Pagado','ParaLlevar',20,NULL,NULL,8,'2025-11-07 23:41:50','2025-11-07 23:45:27'),(4,'PED-000004',4,10,'2025-11-06','13:15:00',153.00,0.00,19.89,15.00,172.89,'Pagado','Local',35,NULL,NULL,15,'2025-11-07 23:41:50','2025-11-07 23:45:27'),(5,'PED-000005',5,1,'2025-11-06','17:30:00',42.00,0.00,5.46,0.00,47.46,'Pagado','ParaLlevar',15,NULL,NULL,4,'2025-11-07 23:41:50','2025-11-07 23:41:50'),(6,'PED-000006',6,5,'2025-11-07','12:00:00',142.00,24.75,18.46,20.00,160.46,'Pagado','Local',40,NULL,NULL,17,'2025-11-07 23:41:50','2025-11-07 23:45:27'),(7,'PED-000007',7,1,'2025-11-07','19:00:00',185.00,0.00,24.05,25.00,209.05,'Servido','Local',45,NULL,NULL,0,'2025-11-07 23:41:50','2025-11-07 23:45:27'),(8,'PED-000008',8,10,'2025-11-07','20:30:00',202.00,0.00,26.26,20.00,228.26,'Listo','Local',35,NULL,NULL,0,'2025-11-07 23:41:50','2025-11-07 23:45:27'),(9,'PED-000009',9,1,'2025-11-08','11:45:00',72.00,0.00,9.36,0.00,81.36,'Pagado','ParaLlevar',20,NULL,NULL,7,'2025-11-07 23:41:50','2025-11-07 23:45:27'),(10,'PED-000010',10,5,'2025-11-08','13:30:00',88.00,0.00,11.44,10.00,99.44,'Pagado','Local',30,NULL,NULL,11,'2025-11-07 23:41:50','2025-11-07 23:45:27'),(11,'PED-000011',11,1,'2025-11-08','14:15:00',98.00,0.00,12.74,8.00,110.74,'EnPreparacion','Local',25,NULL,NULL,0,'2025-11-07 23:41:50','2025-11-07 23:45:27'),(12,'PED-000012',12,10,'2025-11-08','18:00:00',115.00,20.10,14.95,15.00,129.95,'Servido','Local',35,NULL,NULL,0,'2025-11-07 23:41:50','2025-11-07 23:45:27'),(13,'PED-000013',13,1,'2025-11-09','10:30:00',35.00,0.00,4.55,0.00,39.55,'Pagado','ParaLlevar',15,NULL,NULL,4,'2025-11-07 23:41:50','2025-11-07 23:45:27'),(14,'PED-000014',14,5,'2025-11-09','12:45:00',150.00,0.00,19.50,18.00,169.50,'Pagado','Local',40,NULL,NULL,19,'2025-11-07 23:41:50','2025-11-07 23:45:27'),(15,'PED-000015',15,1,'2025-11-09','19:30:00',86.00,0.00,11.18,10.00,97.18,'Listo','Local',30,NULL,NULL,0,'2025-11-07 23:41:50','2025-11-07 23:45:27'),(16,'PED-000016',1,10,'2025-11-10','08:30:00',35.00,0.00,4.55,0.00,39.55,'Pagado','ParaLlevar',12,NULL,NULL,3,'2025-11-07 23:41:50','2025-11-07 23:41:50'),(17,'PED-000017',2,1,'2025-11-10','14:00:00',82.00,0.00,10.66,9.00,92.66,'Servido','Local',28,NULL,NULL,0,'2025-11-07 23:41:50','2025-11-07 23:45:27'),(18,'PED-000018',3,5,'2025-11-10','20:00:00',223.00,0.00,28.99,30.00,251.99,'EnPreparacion','Local',50,NULL,NULL,0,'2025-11-07 23:41:50','2025-11-07 23:45:27'),(19,'PED-000019',4,1,'2025-11-11','11:00:00',62.00,0.00,8.06,0.00,70.06,'Pendiente','ParaLlevar',18,NULL,NULL,0,'2025-11-07 23:41:50','2025-11-07 23:45:27'),(20,'PED-000020',5,10,'2025-11-11','13:15:00',110.00,0.00,14.30,12.00,124.30,'Pendiente','Local',32,NULL,NULL,0,'2025-11-07 23:41:50','2025-11-07 23:45:27');
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persona`
--

DROP TABLE IF EXISTS `persona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persona` (
  `id_persona` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `ci` varchar(20) NOT NULL,
  `edad` int(11) DEFAULT NULL CHECK (`edad` >= 0 and `edad` <= 120),
  `telefono` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `genero` enum('M','F','Otro','Prefiero no decir') DEFAULT 'Prefiero no decir',
  `fecha_registro` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_actualizacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `activo` tinyint(1) DEFAULT 1,
  PRIMARY KEY (`id_persona`),
  UNIQUE KEY `ci` (`ci`),
  KEY `idx_persona_ci` (`ci`),
  KEY `idx_persona_nombre` (`nombre`,`apellidos`),
  KEY `idx_persona_activo` (`activo`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persona`
--

LOCK TABLES `persona` WRITE;
/*!40000 ALTER TABLE `persona` DISABLE KEYS */;
INSERT INTO `persona` VALUES (1,'Juan Carlos','Pérez Soto','1234567LP',30,'71234567','juan.perez@email.com','Av. 6 de Agosto #1234','1994-03-15','M','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(2,'María Elena','López García','2345678CB',25,'72345678','maria.lopez@email.com','Calle Ayacucho #567','1999-07-22','F','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(3,'Carlos Alberto','Rojas Vargas','3456789LP',28,'73456789','carlos.rojas@email.com','Av. Arce #890','1996-11-08','M','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(4,'Ana Lucía','Torrez Mamani','4567890OR',32,'74567890','ana.torrez@email.com','Zona Sur Calle 21 #456','1992-05-12','F','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(5,'Luis Fernando','Gutiérrez Fernández','5678901LP',40,'75678901','luis.gutierrez@email.com','Av. Ballivián #234','1984-09-28','M','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(6,'Paola Andrea','Mendoza Flores','6789012CB',27,'76789012','paola.mendoza@email.com','Calle Comercio #789','1997-02-14','F','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(7,'Jorge Ernesto','Alarcón Pérez','7890123LP',35,'77890123','jorge.alarcon@email.com','Av. del Maestro #123','1989-12-03','M','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(8,'Sofía Isabel','Villalobos Salinas','8901234LP',29,'78901234','sofia.villalobos@email.com','Calle 16 de Julio #456','1995-08-19','F','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(9,'Roberto Miguel','Sánchez Cordero','9012345SC',33,'79012345','roberto.sanchez@email.com','Av. Busch #678','1991-04-27','M','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(10,'Carmen Rosa','Flores Quispe','0123456LP',26,'70123456','carmen.flores@email.com','Zona Norte Calle 8 #901','1998-10-05','F','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(11,'Diego Armando','Morales Castro','1122334LP',31,'71122334','diego.morales@email.com','Av. América #234','1993-06-18','M','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(12,'Valeria Beatriz','Herrera Pinto','2233445CB',24,'72233445','valeria.herrera@email.com','Calle Sagárnaga #567','2000-01-30','F','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(13,'Fernando José','Quiroga Ramírez','3344556LP',38,'73344556','fernando.quiroga@email.com','Av. Camacho #890','1986-07-11','M','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(14,'Gabriela Patricia','Vera Soliz','4455667OR',29,'74455667','gabriela.vera@email.com','Zona Sopocachi Calle 15 #123','1995-03-25','F','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(15,'Andrés Felipe','Carvajal Toro','5566778LP',34,'75566778','andres.carvajal@email.com','Av. Simón Bolívar #456','1990-09-14','M','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(16,'Daniela Cristina','Ortiz Mamani','6677889CB',27,'76677889','daniela.ortiz@email.com','Calle México #789','1997-11-22','F','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(17,'Ricardo Antonio','Paz Gonzales','7788990LP',36,'77788990','ricardo.paz@email.com','Av. Montenegro #234','1988-05-07','M','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(18,'Lorena Isabel','Campos Rivera','8899001LP',23,'78899001','lorena.campos@email.com','Zona Miraflores Calle 16 #567','2001-12-16','F','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(19,'Mauricio Javier','Silva Quisbert','9900112SC',41,'79900112','mauricio.silva@email.com','Av. Tejada Sorzano #890','1983-08-29','M','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(20,'Alejandra Sofía','Luna Paredes','0011223LP',28,'70011223','alejandra.luna@email.com','Calle Potosí #123','1996-04-13','F','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(21,'Pablo César','Romero Vásquez','1234111LP',37,'71234111','pablo.romero@email.com','Av. Saavedra #456','1987-10-21','M','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(22,'Natalia Andrea','Castro Delgado','2345222CB',26,'72345222','natalia.castro@email.com','Calle Illampu #789','1998-02-08','F','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(23,'Rodrigo Daniel','Espinoza Tapia','3456333LP',39,'73456333','rodrigo.espinoza@email.com','Av. Mariscal Santa Cruz #234','1985-06-15','M','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(24,'Carolina Mónica','Ramos Choque','4567444OR',25,'74567444','carolina.ramos@email.com','Zona Calacoto Calle 12 #567','1999-09-03','F','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(25,'Esteban Miguel','Fuentes Gallardo','5678555LP',32,'75678555','esteban.fuentes@email.com','Av. Kantutani #890','1992-12-19','M','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(26,'Fernanda Paola','Aguilar Rocha','6789666CB',30,'76789666','fernanda.aguilar@email.com','Calle Landaeta #123','1994-03-07','F','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(27,'Martín Eduardo','Cordero Zárate','7890777LP',35,'77890777','martin.cordero@email.com','Av. 14 de Septiembre #456','1989-07-24','M','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(28,'Verónica Isabel','Salazar Paniagua','8901888LP',28,'78901888','veronica.salazar@email.com','Zona San Miguel Calle 18 #789','1996-11-12','F','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(29,'Sebastián Andrés','Montaño Calle','9012999SC',33,'79012999','sebastian.montano@email.com','Av. Juan Pablo II #234','1991-05-28','M','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(30,'Victoria Alejandra','Navarro Condori','0123000LP',27,'70123000','victoria.navarro@email.com','Calle Junín #567','1997-08-16','F','2025-11-07 23:36:46','2025-11-07 23:36:46',1),(31,'Gonzalo','Apaza','8307262',0,NULL,NULL,NULL,NULL,'Prefiero no decir','2025-11-24 15:07:48','2025-11-24 15:07:48',1);
/*!40000 ALTER TABLE `persona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plato`
--

DROP TABLE IF EXISTS `plato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plato` (
  `id_plato` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `id_categoria` int(11) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `precio` decimal(8,2) NOT NULL CHECK (`precio` >= 0),
  `costo` decimal(8,2) DEFAULT 0.00,
  `margen_ganancia` decimal(8,2) GENERATED ALWAYS AS (`precio` - `costo`) STORED,
  `disponible` tinyint(1) DEFAULT 1,
  `es_promocion` tinyint(1) DEFAULT 0,
  `imagen_url` varchar(255) DEFAULT NULL,
  `tiempo_preparacion_min` int(11) DEFAULT 15,
  `popularidad_score` int(11) DEFAULT 0,
  `veces_vendido` int(11) DEFAULT 0,
  `activo` tinyint(1) DEFAULT 1,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_actualizacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id_plato`),
  KEY `idx_plato_nombre` (`nombre`),
  KEY `idx_plato_categoria` (`id_categoria`),
  KEY `idx_plato_disponible` (`disponible`),
  KEY `idx_plato_precio` (`precio`),
  KEY `idx_plato_popularidad` (`popularidad_score`),
  CONSTRAINT `plato_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plato`
--

LOCK TABLES `plato` WRITE;
/*!40000 ALTER TABLE `plato` DISABLE KEYS */;
INSERT INTO `plato` (`id_plato`, `nombre`, `id_categoria`, `descripcion`, `precio`, `costo`, `disponible`, `es_promocion`, `imagen_url`, `tiempo_preparacion_min`, `popularidad_score`, `veces_vendido`, `activo`, `fecha_creacion`, `fecha_actualizacion`) VALUES (1,'Espresso Simple',1,'Café expreso italiano tradicional, intenso y aromático',12.00,4.00,1,0,NULL,5,85,142,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(2,'Espresso Doble',1,'Doble shot de espresso para los amantes del café fuerte',18.00,6.00,1,0,NULL,5,78,98,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(3,'Café Americano',1,'Espresso diluido con agua caliente, suave y aromático',10.00,3.50,1,0,NULL,5,92,187,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(4,'Cappuccino Clásico',1,'Espresso con leche vaporizada y espuma cremosa',15.00,5.00,1,0,NULL,8,88,156,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(5,'Cappuccino Vainilla',1,'Cappuccino con toque de vainilla natural',17.00,5.50,1,0,NULL,8,73,89,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(6,'Latte Clásico',1,'Espresso con generosa cantidad de leche vaporizada',16.00,5.50,1,0,NULL,8,90,178,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(7,'Latte Caramelo',1,'Latte con delicioso jarabe de caramelo casero',18.00,6.00,1,0,NULL,8,82,124,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(8,'Mocha',1,'Espresso con chocolate y leche, coronado con crema',19.00,6.50,1,0,NULL,10,76,96,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(9,'Té Verde Premium',1,'Té verde japonés de alta calidad, antioxidante',8.00,2.50,1,0,NULL,5,68,72,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(10,'Té de Manzanilla',1,'Infusión relajante de manzanilla natural',7.00,2.00,1,0,NULL,5,65,58,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(11,'Chocolate Caliente',1,'Chocolate belga con leche cremosa y chantilly',18.00,6.00,1,0,NULL,10,87,145,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(12,'Smoothie de Fresa',2,'Fresa fresca con yogurt natural y miel',18.00,7.00,1,0,NULL,8,84,132,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(13,'Smoothie de Mango',2,'Mango maduro con naranja y un toque de miel',18.00,7.00,1,0,NULL,8,79,108,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(14,'Smoothie Tropical',2,'Mix de piña, mango y coco, refrescante',20.00,8.00,1,0,NULL,8,75,87,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(15,'Smoothie Verde Detox',2,'Espinaca, manzana verde, pepino y limón',19.00,7.50,1,0,NULL,10,71,64,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(16,'Limonada Natural',2,'Limones frescos con azúcar de caña y hierbabuena',10.00,3.00,1,0,NULL,5,89,165,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(17,'Limonada de Frutilla',2,'Limonada con frutillas frescas machacadas',12.00,4.00,1,0,NULL,5,81,118,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(18,'Jugo de Naranja Natural',2,'Naranja recién exprimida, 100% natural',12.00,4.00,1,0,NULL,5,88,154,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(19,'Jugo Multivitamínico',2,'Mezcla de naranja, zanahoria y jengibre',15.00,5.50,1,0,NULL,8,74,91,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(20,'Frappé de Café',2,'Café helado con hielo frappe y crema batida',22.00,8.00,1,0,NULL,10,83,126,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(21,'Frappé de Chocolate',2,'Chocolate helado con chispas de chocolate',23.00,8.50,1,0,NULL,10,80,112,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(22,'Té Helado con Limón',2,'Té negro frío con rodajas de limón y hierbabuena',10.00,3.00,1,0,NULL,5,72,85,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(23,'Desayuno Completo',3,'Huevos revueltos, pan tostado, mermelada, café y jugo',35.00,15.00,1,0,NULL,20,93,201,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(24,'Desayuno Light',3,'Yogurt griego con granola, frutas frescas y miel',28.00,12.00,1,0,NULL,12,78,104,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(25,'Tostadas Francesas',3,'Pan francés caramelizado con frutas y miel de maple',28.00,12.00,1,0,NULL,15,86,142,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(26,'Omelette de Verduras',3,'3 huevos con pimiento, cebolla, tomate y queso',32.00,14.00,1,0,NULL,18,81,115,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(27,'Omelette de Jamón y Queso',3,'3 huevos con jamón premium y queso fundido',34.00,15.00,1,0,NULL,18,85,132,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(28,'Croissant con Jamón',3,'Croissant mantecoso relleno con jamón y queso suizo',25.00,10.00,1,0,NULL,12,79,108,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(29,'Panqueques Americanos',3,'Stack de 3 panqueques con miel de maple y mantequilla',26.00,11.00,1,0,NULL,18,88,156,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(30,'Cheesecake de Frutos Rojos',4,'Cremoso pastel de queso con coulis de frutos rojos',25.00,10.00,1,0,NULL,5,91,183,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(31,'Cheesecake de Oreo',4,'Cheesecake con base y topping de galletas Oreo',27.00,11.00,1,0,NULL,5,87,147,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(32,'Brownie con Helado',4,'Brownie tibio de chocolate con helado de vainilla',20.00,8.00,1,0,NULL,8,89,165,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(33,'Tiramisú Italiano',4,'Postre italiano clásico con café y mascarpone',28.00,12.00,1,0,NULL,5,84,128,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(34,'Torta de Chocolate',4,'Torta húmeda de chocolate con ganache',22.00,9.00,1,0,NULL,5,90,176,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(35,'Volcán de Chocolate',4,'Volcán de chocolate con centro líquido y helado',32.00,14.00,1,0,NULL,12,92,195,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(36,'Helado Artesanal (2 bolas)',4,'Helado artesanal en sabores variados',15.00,5.00,1,0,NULL,3,86,143,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(37,'Copa Helada Especial',4,'Copa con 3 bolas de helado, frutas y salsas',25.00,10.00,1,0,NULL,8,82,119,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(38,'Sándwich de Pollo',5,'Pan integral con pechuga de pollo grillada y vegetales frescos',22.00,9.00,1,0,NULL,15,87,148,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(39,'Sándwich Club Triple',5,'Triple decker con pollo, tocino, huevo y vegetales',28.00,12.00,1,0,NULL,18,85,136,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(40,'Wrap Vegetal',5,'Tortilla integral con vegetales asados y hummus',24.00,10.00,1,0,NULL,12,76,94,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(41,'Empanadas de Queso (3 un.)',5,'Empanadas horneadas rellenas de queso fundido',18.00,6.00,1,0,NULL,12,83,125,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(42,'Empanadas de Carne (3 un.)',5,'Empanadas con carne mechada y aceitunas',20.00,7.00,1,0,NULL,12,88,159,1,'2025-11-07 23:37:37','2025-11-07 23:37:37'),(43,'Papas Fritas Grandes',5,'Porción generosa de papas fritas con salsas',15.00,5.00,0,0,NULL,10,89,167,1,'2025-11-07 23:37:37','2025-11-24 15:02:53'),(44,'Nachos con Queso',5,'Nachos crujientes con queso cheddar fundido y jalapeños',22.00,8.00,0,0,NULL,12,81,114,1,'2025-11-07 23:37:37','2025-11-24 15:02:57'),(45,'Ensalada César',6,'Lechuga romana, pollo grillado, crutones y parmesano',30.00,12.00,0,0,NULL,15,84,130,1,'2025-11-07 23:37:37','2025-11-24 15:03:01'),(46,'Ensalada Caprese',6,'Tomate, mozzarella fresca, albahaca y aceite de oliva',28.00,11.00,0,0,NULL,12,77,98,1,'2025-11-07 23:37:37','2025-11-24 15:03:08'),(47,'Ensalada Mediterránea',6,'Mix de lechugas con atún, aceitunas, tomate y feta',32.00,13.00,0,0,NULL,15,79,106,1,'2025-11-07 23:37:37','2025-11-24 15:03:14'),(48,'Sopa de Verduras',6,'Sopa casera con vegetales frescos de temporada',18.00,6.00,0,0,NULL,20,73,86,1,'2025-11-07 23:37:37','2025-11-24 15:03:17'),(49,'Sopa de Pollo Casera',6,'Sopa reconfortante con pollo, fideos y vegetales',22.00,8.00,0,0,NULL,25,80,112,1,'2025-11-07 23:37:37','2025-11-24 15:03:21'),(50,'Wrap de Pollo César',6,'Tortilla con pollo, lechuga romana y aderezo César',26.00,10.00,0,0,NULL,15,82,121,1,'2025-11-07 23:37:37','2025-11-24 15:03:25'),(51,'Lomo Saltado',7,'Carne de res salteada con papas fritas al estilo peruano',45.00,20.00,0,0,NULL,25,90,175,1,'2025-11-07 23:37:37','2025-11-24 15:03:29'),(52,'Pollo a la Plancha',7,'Pechuga de pollo jugosa con ensalada mixta',38.00,16.00,0,0,NULL,20,86,141,1,'2025-11-07 23:37:37','2025-11-24 15:02:35'),(53,'Pasta Alfredo con Pollo',7,'Fettuccine en cremosa salsa alfredo con pollo',42.00,18.00,0,0,NULL,22,88,158,1,'2025-11-07 23:37:37','2025-11-24 15:02:11'),(54,'Pasta Carbonara',7,'Spaghetti con tocino, huevo, parmesano y crema',40.00,17.00,0,0,NULL,20,83,126,1,'2025-11-07 23:37:37','2025-11-24 15:02:03'),(55,'Pizza Margarita Personal',7,'Pizza individual con tomate, mozzarella y albahaca',32.00,12.00,0,0,NULL,20,89,164,1,'2025-11-07 23:37:37','2025-11-24 15:02:15'),(56,'Pizza Pepperoni Personal',7,'Pizza individual con generoso pepperoni y queso',35.00,14.00,0,0,NULL,20,91,187,1,'2025-11-07 23:37:37','2025-11-24 15:02:19'),(57,'Hamburguesa Premium',7,'Carne angus 200g con queso, tocino y papas fritas',42.00,18.00,0,0,NULL,22,94,208,1,'2025-11-07 23:37:37','2025-11-24 15:02:23'),(58,'Milanesa Napolitana',7,'Milanesa de res con jamón, queso y salsa de tomate',40.00,17.00,0,0,NULL,25,87,149,1,'2025-11-07 23:37:37','2025-11-24 15:02:29');
/*!40000 ALTER TABLE `plato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proveedor` (
  `id_proveedor` int(11) NOT NULL AUTO_INCREMENT,
  `id_persona` int(11) NOT NULL,
  `empresa` varchar(100) NOT NULL,
  `ruc` varchar(20) DEFAULT NULL,
  `sitio_web` varchar(100) DEFAULT NULL,
  `categoria_productos` varchar(100) DEFAULT NULL,
  `calificacion` decimal(3,2) DEFAULT NULL CHECK (`calificacion` >= 0 and `calificacion` <= 5),
  `activo` tinyint(1) DEFAULT 1,
  `fecha_registro` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id_proveedor`),
  UNIQUE KEY `ruc` (`ruc`),
  KEY `id_persona` (`id_persona`),
  KEY `idx_proveedor_empresa` (`empresa`),
  KEY `idx_proveedor_ruc` (`ruc`),
  CONSTRAINT `proveedor_ibfk_1` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id_persona`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedor`
--

LOCK TABLES `proveedor` WRITE;
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
INSERT INTO `proveedor` VALUES (1,16,'Lácteos La Vaca Feliz','1234567890001','www.lavacafeliz.com.bo','Lácteos',4.80,1,'2025-11-07 23:37:11'),(2,23,'Carnes Premium S.A.','2345678901002','www.carnespremium.bo','Carnes',4.50,1,'2025-11-07 23:37:11'),(3,25,'Verduras Frescas del Valle','3456789012003','www.verdurasfrescas.bo','Vegetales y Frutas',4.70,1,'2025-11-07 23:37:11'),(4,27,'Distribuidora Café Oro','4567890123004','www.cafeoro.com','Bebidas',4.90,1,'2025-11-07 23:37:11'),(5,29,'Panadería San José','5678901234005','www.panaderiasjose.bo','Granos y Panadería',4.60,1,'2025-11-07 23:37:11');
/*!40000 ALTER TABLE `proveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reserva`
--

DROP TABLE IF EXISTS `reserva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reserva` (
  `id_reserva` int(11) NOT NULL AUTO_INCREMENT,
  `id_cliente` int(11) NOT NULL,
  `id_empleado` int(11) DEFAULT NULL,
  `id_mesa` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `personas` int(11) NOT NULL CHECK (`personas` > 0),
  `estado` enum('Pendiente','Confirmada','EnCurso','Completada','Cancelada','NoShow') DEFAULT 'Pendiente',
  `motivo` varchar(100) DEFAULT NULL,
  `notas` text DEFAULT NULL,
  `telefono_contacto` varchar(20) DEFAULT NULL,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_actualizacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id_reserva`),
  KEY `id_empleado` (`id_empleado`),
  KEY `idx_reserva_cliente` (`id_cliente`),
  KEY `idx_reserva_mesa` (`id_mesa`),
  KEY `idx_reserva_fecha` (`fecha`,`hora`),
  KEY `idx_reserva_estado` (`estado`),
  CONSTRAINT `reserva_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`) ON UPDATE CASCADE,
  CONSTRAINT `reserva_ibfk_2` FOREIGN KEY (`id_empleado`) REFERENCES `empleado` (`id_empleado`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `reserva_ibfk_3` FOREIGN KEY (`id_mesa`) REFERENCES `mesa` (`id_mesa`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reserva`
--

LOCK TABLES `reserva` WRITE;
/*!40000 ALTER TABLE `reserva` DISABLE KEYS */;
INSERT INTO `reserva` VALUES (1,1,1,2,'2025-11-10','19:00:00',4,'Confirmada','Cumpleaños',NULL,'71234567','2025-11-07 23:41:45','2025-11-07 23:41:45'),(2,2,5,1,'2025-11-10','13:00:00',2,'Confirmada',NULL,NULL,'72345678','2025-11-07 23:41:45','2025-11-07 23:41:45'),(3,3,1,5,'2025-11-11','20:00:00',8,'Confirmada','Aniversario',NULL,'76789012','2025-11-07 23:41:45','2025-11-07 23:41:45'),(4,4,10,3,'2025-11-11','14:30:00',4,'Confirmada',NULL,NULL,'79012345','2025-11-07 23:41:45','2025-11-07 23:41:45'),(5,5,1,4,'2025-11-12','19:30:00',6,'Pendiente','Reunión de trabajo',NULL,'70123456','2025-11-07 23:41:45','2025-11-07 23:41:45'),(6,6,5,7,'2025-11-12','12:00:00',4,'Confirmada',NULL,NULL,'71122334','2025-11-07 23:41:45','2025-11-07 23:41:45'),(7,7,1,8,'2025-11-13','21:00:00',6,'Pendiente','Cena romántica',NULL,'74455667','2025-11-07 23:41:45','2025-11-07 23:41:45'),(8,8,10,11,'2025-11-13','18:00:00',10,'Confirmada','Evento corporativo',NULL,'78899001','2025-11-07 23:41:45','2025-11-07 23:41:45'),(9,9,1,2,'2025-11-14','13:30:00',4,'Confirmada',NULL,NULL,'79012345','2025-11-07 23:41:45','2025-11-07 23:41:45'),(10,10,5,6,'2025-11-14','15:00:00',2,'Pendiente',NULL,NULL,'71234111','2025-11-07 23:41:45','2025-11-07 23:41:45'),(11,11,1,3,'2025-11-15','19:00:00',4,'Confirmada',NULL,NULL,'72233445','2025-11-07 23:41:45','2025-11-07 23:41:45'),(12,12,10,1,'2025-11-15','12:30:00',2,'Confirmada',NULL,NULL,'76677889','2025-11-07 23:41:45','2025-11-07 23:41:45'),(13,13,1,12,'2025-11-16','20:00:00',4,'Pendiente',NULL,NULL,'70123000','2025-11-07 23:41:45','2025-11-07 23:41:45'),(14,14,5,4,'2025-11-16','13:00:00',6,'Confirmada','Almuerzo familiar',NULL,'72345222','2025-11-07 23:41:45','2025-11-07 23:41:45'),(15,15,1,7,'2025-11-17','18:30:00',4,'Pendiente',NULL,NULL,'76789666','2025-11-07 23:41:45','2025-11-07 23:41:45'),(16,16,NULL,2,'2025-11-24','11:08:00',12,'Pendiente',NULL,NULL,NULL,'2025-11-24 15:08:44','2025-11-24 15:08:44');
/*!40000 ALTER TABLE `reserva` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sesion_log`
--

DROP TABLE IF EXISTS `sesion_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sesion_log` (
  `id_log` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) NOT NULL,
  `session_id` varchar(36) NOT NULL,
  `inicio_sesion` timestamp NOT NULL DEFAULT current_timestamp(),
  `fin_sesion` timestamp NULL DEFAULT NULL,
  `ip_address` varchar(45) DEFAULT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  `accion` varchar(100) DEFAULT NULL,
  `detalles` text DEFAULT NULL,
  PRIMARY KEY (`id_log`),
  KEY `idx_sesion_usuario` (`id_usuario`),
  KEY `idx_sesion_fecha` (`inicio_sesion`),
  KEY `idx_sesion_id` (`session_id`),
  CONSTRAINT `sesion_log_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sesion_log`
--

LOCK TABLES `sesion_log` WRITE;
/*!40000 ALTER TABLE `sesion_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sesion_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `salt` varchar(64) NOT NULL,
  `id_empleado` int(11) DEFAULT NULL,
  `rol` enum('Administrador','Cajero','Mesero','Chef','Gerente') NOT NULL,
  `ultimo_acceso` timestamp NULL DEFAULT NULL,
  `intentos_fallidos` int(11) DEFAULT 0,
  `bloqueado` tinyint(1) DEFAULT 0,
  `fecha_expiracion` timestamp NULL DEFAULT NULL,
  `requiere_cambio_password` tinyint(1) DEFAULT 0,
  `activo` tinyint(1) DEFAULT 1,
  `fecha_creacion` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_actualizacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `username` (`username`),
  KEY `id_empleado` (`id_empleado`),
  KEY `idx_usuario_username` (`username`),
  KEY `idx_usuario_rol` (`rol`),
  KEY `idx_usuario_activo` (`activo`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`id_empleado`) REFERENCES `empleado` (`id_empleado`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'admin','admin123','salt_admin',3,'Administrador','2025-11-24 15:10:02',0,0,NULL,0,1,'2025-11-07 23:37:15','2025-11-24 15:10:02'),(2,'mesero1','mesero123','salt_mes1',1,'Mesero',NULL,0,0,NULL,0,1,'2025-11-07 23:37:15','2025-11-08 00:14:22'),(3,'cajero1','cajero123','salt_caj1',2,'Cajero','2025-11-24 15:06:06',0,0,NULL,0,1,'2025-11-07 23:37:15','2025-11-24 15:06:06'),(4,'chef1','chef123','salt_chf1',4,'Chef','2025-11-24 14:34:59',0,0,NULL,0,1,'2025-11-07 23:37:15','2025-11-24 14:34:59'),(5,'mesero2','mesero123','salt_mes2',5,'Mesero',NULL,0,0,NULL,0,1,'2025-11-07 23:37:15','2025-11-08 00:14:31'),(6,'gerente1','gerente123','salt_ger1',6,'Gerente',NULL,0,0,NULL,0,1,'2025-11-07 23:37:15','2025-11-08 00:14:34'),(7,'cajero2','cajero123','salt_caj2',7,'Cajero',NULL,0,0,NULL,0,1,'2025-11-07 23:37:15','2025-11-08 00:14:38'),(8,'barista1','barista123','salt_bar1',8,'Mesero',NULL,0,0,NULL,0,1,'2025-11-07 23:37:15','2025-11-08 00:14:41'),(9,'chef2','chef123','salt_chf2',9,'Chef',NULL,0,0,NULL,0,1,'2025-11-07 23:37:15','2025-11-08 00:14:44'),(10,'mesero3','mesero123','salt_mes3',10,'Mesero',NULL,0,0,NULL,0,1,'2025-11-07 23:37:15','2025-11-08 00:14:47');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `v_clientes_vip`
--

DROP TABLE IF EXISTS `v_clientes_vip`;
/*!50001 DROP VIEW IF EXISTS `v_clientes_vip`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_clientes_vip` AS SELECT 
 1 AS `id_cliente`,
 1 AS `nombre_completo`,
 1 AS `ci`,
 1 AS `telefono`,
 1 AS `email`,
 1 AS `puntos_fidelidad`,
 1 AS `nivel_fidelidad`,
 1 AS `total_gastado`,
 1 AS `cantidad_visitas`,
 1 AS `fecha_primera_compra`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_inventario_alertas`
--

DROP TABLE IF EXISTS `v_inventario_alertas`;
/*!50001 DROP VIEW IF EXISTS `v_inventario_alertas`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_inventario_alertas` AS SELECT 
 1 AS `id_ingrediente`,
 1 AS `nombre`,
 1 AS `stock_actual`,
 1 AS `stock_minimo`,
 1 AS `stock_maximo`,
 1 AS `unidad_medida`,
 1 AS `precio_unitario`,
 1 AS `proveedor`,
 1 AS `estado_stock`,
 1 AS `porcentaje_sobre_minimo`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_mesas_disponibles`
--

DROP TABLE IF EXISTS `v_mesas_disponibles`;
/*!50001 DROP VIEW IF EXISTS `v_mesas_disponibles`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_mesas_disponibles` AS SELECT 
 1 AS `id_mesa`,
 1 AS `numero_mesa`,
 1 AS `capacidad`,
 1 AS `ubicacion`,
 1 AS `forma`,
 1 AS `tiene_toma_corriente`,
 1 AS `es_fumadores`,
 1 AS `estado`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_productos_mas_vendidos`
--

DROP TABLE IF EXISTS `v_productos_mas_vendidos`;
/*!50001 DROP VIEW IF EXISTS `v_productos_mas_vendidos`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_productos_mas_vendidos` AS SELECT 
 1 AS `id_plato`,
 1 AS `nombre`,
 1 AS `categoria`,
 1 AS `veces_pedido`,
 1 AS `cantidad_total`,
 1 AS `ingresos_totales`,
 1 AS `precio_actual`,
 1 AS `veces_vendido`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_recibo_detalle`
--

DROP TABLE IF EXISTS `v_recibo_detalle`;
/*!50001 DROP VIEW IF EXISTS `v_recibo_detalle`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_recibo_detalle` AS SELECT 
 1 AS `cliente`,
 1 AS `monto_total`,
 1 AS `empleado`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_ventas_dia`
--

DROP TABLE IF EXISTS `v_ventas_dia`;
/*!50001 DROP VIEW IF EXISTS `v_ventas_dia`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_ventas_dia` AS SELECT 
 1 AS `fecha`,
 1 AS `total_facturas`,
 1 AS `subtotal_total`,
 1 AS `descuentos_total`,
 1 AS `impuestos_total`,
 1 AS `propinas_total`,
 1 AS `total_ventas`,
 1 AS `ticket_promedio`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_clientes_top`
--

DROP TABLE IF EXISTS `vista_clientes_top`;
/*!50001 DROP VIEW IF EXISTS `vista_clientes_top`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_clientes_top` AS SELECT 
 1 AS `cliente`,
 1 AS `ci`,
 1 AS `total_pedidos`,
 1 AS `total_gastado`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_mejores_clientes`
--

DROP TABLE IF EXISTS `vista_mejores_clientes`;
/*!50001 DROP VIEW IF EXISTS `vista_mejores_clientes`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_mejores_clientes` AS SELECT 
 1 AS `cliente`,
 1 AS `puntos_fidelidad`,
 1 AS `pedidos`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_pedidos_por_fecha`
--

DROP TABLE IF EXISTS `vista_pedidos_por_fecha`;
/*!50001 DROP VIEW IF EXISTS `vista_pedidos_por_fecha`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_pedidos_por_fecha` AS SELECT 
 1 AS `fecha`,
 1 AS `total_pedidos`,
 1 AS `monto_total`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_platos_categorias`
--

DROP TABLE IF EXISTS `vista_platos_categorias`;
/*!50001 DROP VIEW IF EXISTS `vista_platos_categorias`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_platos_categorias` AS SELECT 
 1 AS `categoria`,
 1 AS `plato`,
 1 AS `precio`,
 1 AS `disponible`,
 1 AS `tiempo_preparacion_min`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_reservas_mes_actual`
--

DROP TABLE IF EXISTS `vista_reservas_mes_actual`;
/*!50001 DROP VIEW IF EXISTS `vista_reservas_mes_actual`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_reservas_mes_actual` AS SELECT 
 1 AS `id_reserva`,
 1 AS `cliente`,
 1 AS `numero_mesa`,
 1 AS `fecha`,
 1 AS `hora`,
 1 AS `personas`,
 1 AS `estado`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `vista_top_platos`
--

DROP TABLE IF EXISTS `vista_top_platos`;
/*!50001 DROP VIEW IF EXISTS `vista_top_platos`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vista_top_platos` AS SELECT 
 1 AS `nombre`,
 1 AS `veces`,
 1 AS `ingresos`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping routines for database 'dbcafeteria'
--
/*!50003 DROP PROCEDURE IF EXISTS `p_VentasCliente` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `p_VentasCliente`(IN nombre VARCHAR(100))
BEGIN
    SELECT
        f.fecha_hora AS fecha,
        CONCAT(pc.nombre, ' ', pc.apellidos) AS Cliente,
        f.total AS monto_total,
        CONCAT(pe.nombre, ' ', pe.apellidos) AS Atendio
    FROM factura f
    INNER JOIN pedido p ON f.id_pedido = p.id_pedido
    INNER JOIN cliente c ON p.id_cliente = c.id_cliente
    INNER JOIN persona pc ON c.id_persona = pc.id_persona
    INNER JOIN empleado e ON p.id_empleado = e.id_empleado
    INNER JOIN persona pe ON e.id_persona = pe.id_persona
    WHERE pc.nombre LIKE CONCAT('%', nombre, '%');
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_facturas_con_descuento` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_facturas_con_descuento`(
    IN p_fecha_inicio DATE,
    IN p_fecha_fin DATE
)
BEGIN
    SELECT 
        f.numero_factura, 
        f.fecha_hora, 
        CONCAT(per.nombre, ' ', per.apellidos) AS cliente, 
        f.subtotal, 
        f.descuento, 
        f.total
    FROM factura f
    INNER JOIN pedido p ON f.id_pedido = p.id_pedido
    INNER JOIN cliente c ON p.id_cliente = c.id_cliente
    INNER JOIN persona per ON c.id_persona = per.id_persona
    WHERE f.descuento > 0 
      AND DATE(f.fecha_hora) BETWEEN p_fecha_inicio AND p_fecha_fin
    ORDER BY f.fecha_hora DESC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `v_clientes_vip`
--

/*!50001 DROP VIEW IF EXISTS `v_clientes_vip`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_clientes_vip` AS select `c`.`id_cliente` AS `id_cliente`,concat(`p`.`nombre`,' ',`p`.`apellidos`) AS `nombre_completo`,`p`.`ci` AS `ci`,`p`.`telefono` AS `telefono`,`p`.`email` AS `email`,`c`.`puntos_fidelidad` AS `puntos_fidelidad`,`c`.`nivel_fidelidad` AS `nivel_fidelidad`,`c`.`total_gastado` AS `total_gastado`,`c`.`cantidad_visitas` AS `cantidad_visitas`,`c`.`fecha_primera_compra` AS `fecha_primera_compra` from (`cliente` `c` join `persona` `p` on(`c`.`id_persona` = `p`.`id_persona`)) where `c`.`puntos_fidelidad` >= 100 order by `c`.`puntos_fidelidad` desc,`c`.`total_gastado` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_inventario_alertas`
--

/*!50001 DROP VIEW IF EXISTS `v_inventario_alertas`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_inventario_alertas` AS select `i`.`id_ingrediente` AS `id_ingrediente`,`i`.`nombre` AS `nombre`,`i`.`stock_actual` AS `stock_actual`,`i`.`stock_minimo` AS `stock_minimo`,`i`.`stock_maximo` AS `stock_maximo`,`i`.`unidad_medida` AS `unidad_medida`,`i`.`precio_unitario` AS `precio_unitario`,`pr`.`empresa` AS `proveedor`,case when `i`.`stock_actual` <= `i`.`stock_minimo` then 'CRÍTICO' when `i`.`stock_actual` <= `i`.`stock_minimo` * 1.5 then 'BAJO' when `i`.`stock_actual` >= `i`.`stock_maximo` then 'EXCESO' else 'NORMAL' end AS `estado_stock`,round((`i`.`stock_actual` - `i`.`stock_minimo`) / `i`.`stock_minimo` * 100,2) AS `porcentaje_sobre_minimo` from (`ingrediente` `i` left join `proveedor` `pr` on(`i`.`id_proveedor` = `pr`.`id_proveedor`)) where `i`.`activo` = 1 order by case when `i`.`stock_actual` <= `i`.`stock_minimo` then 1 when `i`.`stock_actual` <= `i`.`stock_minimo` * 1.5 then 2 else 3 end,`i`.`stock_actual` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_mesas_disponibles`
--

/*!50001 DROP VIEW IF EXISTS `v_mesas_disponibles`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_mesas_disponibles` AS select `mesa`.`id_mesa` AS `id_mesa`,`mesa`.`numero_mesa` AS `numero_mesa`,`mesa`.`capacidad` AS `capacidad`,`mesa`.`ubicacion` AS `ubicacion`,`mesa`.`forma` AS `forma`,`mesa`.`tiene_toma_corriente` AS `tiene_toma_corriente`,`mesa`.`es_fumadores` AS `es_fumadores`,`mesa`.`estado` AS `estado` from `mesa` where `mesa`.`activo` = 1 and `mesa`.`estado` = 'Libre' order by `mesa`.`ubicacion`,`mesa`.`capacidad`,`mesa`.`numero_mesa` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_productos_mas_vendidos`
--

/*!50001 DROP VIEW IF EXISTS `v_productos_mas_vendidos`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_productos_mas_vendidos` AS select `p`.`id_plato` AS `id_plato`,`p`.`nombre` AS `nombre`,`c`.`nombre` AS `categoria`,count(`dp`.`id_detalle`) AS `veces_pedido`,sum(`dp`.`cantidad`) AS `cantidad_total`,sum(`dp`.`subtotal`) AS `ingresos_totales`,`p`.`precio` AS `precio_actual`,`p`.`veces_vendido` AS `veces_vendido` from (((`detalle_pedido` `dp` join `plato` `p` on(`dp`.`id_plato` = `p`.`id_plato`)) join `categoria` `c` on(`p`.`id_categoria` = `c`.`id_categoria`)) join `pedido` `ped` on(`dp`.`id_pedido` = `ped`.`id_pedido`)) where `ped`.`estado` in ('Pagado','Servido') group by `p`.`id_plato`,`p`.`nombre`,`c`.`nombre`,`p`.`precio`,`p`.`veces_vendido` order by sum(`dp`.`cantidad`) desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_recibo_detalle`
--

/*!50001 DROP VIEW IF EXISTS `v_recibo_detalle`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_recibo_detalle` AS select concat(`pc`.`nombre`,' ',`pc`.`apellidos`) AS `cliente`,`f`.`total` AS `monto_total`,concat(`pe`.`nombre`,' ',`pe`.`apellidos`) AS `empleado` from (((((`factura` `f` join `pedido` `p` on(`f`.`id_pedido` = `p`.`id_pedido`)) join `cliente` `c` on(`p`.`id_cliente` = `c`.`id_cliente`)) join `persona` `pc` on(`c`.`id_persona` = `pc`.`id_persona`)) join `empleado` `e` on(`p`.`id_empleado` = `e`.`id_empleado`)) join `persona` `pe` on(`e`.`id_persona` = `pe`.`id_persona`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_ventas_dia`
--

/*!50001 DROP VIEW IF EXISTS `v_ventas_dia`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_ventas_dia` AS select cast(`f`.`fecha_hora` as date) AS `fecha`,count(distinct `f`.`id_factura`) AS `total_facturas`,sum(`f`.`subtotal`) AS `subtotal_total`,sum(`f`.`descuento`) AS `descuentos_total`,sum(`f`.`impuesto`) AS `impuestos_total`,sum(`f`.`propina`) AS `propinas_total`,sum(`f`.`total`) AS `total_ventas`,avg(`f`.`total`) AS `ticket_promedio` from `factura` `f` where `f`.`estado` = 'Pagada' group by cast(`f`.`fecha_hora` as date) order by cast(`f`.`fecha_hora` as date) desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_clientes_top`
--

/*!50001 DROP VIEW IF EXISTS `vista_clientes_top`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_clientes_top` AS select concat(`per`.`nombre`,' ',`per`.`apellidos`) AS `cliente`,`per`.`ci` AS `ci`,count(`p`.`id_pedido`) AS `total_pedidos`,sum(`p`.`total`) AS `total_gastado` from ((`cliente` `c` join `persona` `per` on(`c`.`id_persona` = `per`.`id_persona`)) left join `pedido` `p` on(`c`.`id_cliente` = `p`.`id_cliente`)) group by `c`.`id_cliente`,`per`.`nombre`,`per`.`apellidos`,`per`.`ci` having `total_pedidos` > 0 order by count(`p`.`id_pedido`) desc limit 20 */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_mejores_clientes`
--

/*!50001 DROP VIEW IF EXISTS `vista_mejores_clientes`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_mejores_clientes` AS select concat(`per`.`nombre`,' ',`per`.`apellidos`) AS `cliente`,`c`.`puntos_fidelidad` AS `puntos_fidelidad`,count(`p`.`id_pedido`) AS `pedidos` from ((`cliente` `c` join `persona` `per` on(`c`.`id_persona` = `per`.`id_persona`)) left join `pedido` `p` on(`c`.`id_cliente` = `p`.`id_cliente`)) where `c`.`puntos_fidelidad` >= 50 group by `c`.`id_cliente`,`per`.`nombre`,`per`.`apellidos`,`c`.`puntos_fidelidad` order by `c`.`puntos_fidelidad` desc limit 5 */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_pedidos_por_fecha`
--

/*!50001 DROP VIEW IF EXISTS `vista_pedidos_por_fecha`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_pedidos_por_fecha` AS select cast(`p`.`fecha` as date) AS `fecha`,count(0) AS `total_pedidos`,sum(`p`.`total`) AS `monto_total` from `pedido` `p` where `p`.`estado` in ('Pagado','Servido') group by cast(`p`.`fecha` as date) order by cast(`p`.`fecha` as date) desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_platos_categorias`
--

/*!50001 DROP VIEW IF EXISTS `vista_platos_categorias`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_platos_categorias` AS select `c`.`nombre` AS `categoria`,`p`.`nombre` AS `plato`,`p`.`precio` AS `precio`,`p`.`disponible` AS `disponible`,`p`.`tiempo_preparacion_min` AS `tiempo_preparacion_min` from (`plato` `p` join `categoria` `c` on(`p`.`id_categoria` = `c`.`id_categoria`)) order by `c`.`nombre`,`p`.`nombre` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_reservas_mes_actual`
--

/*!50001 DROP VIEW IF EXISTS `vista_reservas_mes_actual`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_reservas_mes_actual` AS select `r`.`id_reserva` AS `id_reserva`,concat(`per`.`nombre`,' ',`per`.`apellidos`) AS `cliente`,`m`.`numero_mesa` AS `numero_mesa`,`r`.`fecha` AS `fecha`,`r`.`hora` AS `hora`,`r`.`personas` AS `personas`,`r`.`estado` AS `estado` from (((`reserva` `r` join `cliente` `c` on(`r`.`id_cliente` = `c`.`id_cliente`)) join `persona` `per` on(`c`.`id_persona` = `per`.`id_persona`)) join `mesa` `m` on(`r`.`id_mesa` = `m`.`id_mesa`)) where month(`r`.`fecha`) = month(curdate()) and year(`r`.`fecha`) = year(curdate()) order by `r`.`fecha` desc,`r`.`hora` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `vista_top_platos`
--

/*!50001 DROP VIEW IF EXISTS `vista_top_platos`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vista_top_platos` AS select `p`.`nombre` AS `nombre`,count(`dp`.`id_detalle`) AS `veces`,sum(`dp`.`subtotal`) AS `ingresos` from (`detalle_pedido` `dp` join `plato` `p` on(`dp`.`id_plato` = `p`.`id_plato`)) group by `p`.`id_plato`,`p`.`nombre` order by count(`dp`.`id_detalle`) desc limit 5 */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-28 10:57:11
