-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-07-2018 a las 19:54:53
-- Versión del servidor: 10.1.32-MariaDB
-- Versión de PHP: 7.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyecto`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categories`
--

CREATE TABLE `categories` (
  `CategoryID` tinyint(3) UNSIGNED NOT NULL,
  `CategoryName` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `categories`
--

INSERT INTO `categories` (`CategoryID`, `CategoryName`) VALUES
(2, 'Cellphones'),
(5, 'Computer'),
(3, 'Laptops'),
(4, 'Tablet'),
(1, 'Technology ');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `customers`
--

CREATE TABLE `customers` (
  `CustomerID` varchar(10) NOT NULL,
  `Name` varchar(30) DEFAULT NULL,
  `Address` varchar(60) DEFAULT NULL,
  `City` varchar(15) DEFAULT NULL,
  `Country` varchar(15) DEFAULT NULL,
  `Phone` varchar(24) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `customers`
--

INSERT INTO `customers` (`CustomerID`, `Name`, `Address`, `City`, `Country`, `Phone`) VALUES
('1102166921', 'Santos Nilda', '18 de Noviembre, 18 de Noviembre y Celín Arrobo', 'Orellana', 'Ecuador', '0991156785'),
('1104001597', 'Jorge Luis', '18 de Noviembre, 18 de Noviembre y Celín Arrobo', 'Azuay', 'Ecuador', '0991156789'),
('1104113889', 'Bruno Esparza', 'Pradera, Alisos y Arupos', 'Loja', 'Ecuador', '0011223344'),
('1104621857', 'Irvin Dario', 'La banda, Manuela Saenz y Taquil', 'Pastaza', 'Ecuador', '0991156709'),
('1104686728', 'Junior Bladimir', 'La banda, Manuela Saenz y Taquil', 'Loja', 'Ecuador', '0991356709'),
('1104872964', 'Juan Magan', 'Calle 7 y Manuel Surita', 'Loja', 'Ecuador', '1122334455'),
('1204001597', 'Juan Pablo', 'Cdla Zamora, Prolongación de 24 de Mayo y Segundo Cueva Celi', 'Loja', 'Ecuador', '0981156789'),
('1244686728', 'Jacinto Juan', 'Echeverría y Valcázar ', 'Loja', 'Ecuador', '1222334456'),
('1254686728', 'Juan Carlos', 'Zaruma y Jaramijó ', 'Puyo', 'Ecuador', '1322334457'),
('1264686728', 'Jorge Lurgo', 'Lombardi y Espinel ', 'San Francisco d', 'Ecuador', '1422334458'),
('1274686728', 'Juan Peritles', 'Bartolomé y Juan de Dios', 'Cuenca', 'Ecuador', '1522334458'),
('1284113889', 'Ronald Adrian', 'Robalino y Eguiguren ', 'Loja', 'Ecuador', '1622334558'),
('1294113889', 'Jessica Anabel', 'Martínez y Toral', 'Azoguez', 'Ecuador', '1722334559'),
('1304001597', 'Juan Diego', 'Cdla Zamora, Prolongación de 24 de Mayo y Segundo Cueva Celi', 'Cañar', 'Ecuador', '0971156789'),
('1304113889', 'Juan Diego', 'Diego y Leiva ', 'Riobamba', 'Ecuador', '1822334569'),
('1314113889', 'Diego Francisco', 'Francisco y 18 de Noviembre', 'Guarandda', 'Ecuador', '1922334569'),
('1324113889', 'Luis Adrian', 'Machala y Carlos Tola', 'Loja', 'Ecuador', '1822634569'),
('1334113889', 'Luis Andres ', 'Guayas y Juan Boscoso ', 'Ibarra', 'Ecuador', '1942634569'),
('1344113889', 'Bruno Andres', 'Camila de Florencia y Manuela Cañizares ', 'Machala', 'Ecuador', '1944634569'),
('1354113889', 'Matias de Dios', 'Malvinas y Arupos', 'Nueva Loja', 'Ecuador', '1944534579'),
('1364113889', 'Pedro Fernando', 'Juan Pablo y Polo Artis', 'Guayaquil', 'Ecuador', '1845534569'),
('1374113889', 'Fulvia María', 'María y San Juan', 'Loja', 'Ecuador', '1645534569'),
('1384113889', 'Jhairo Matias ', 'Marcabelí y Simón Bolivar', 'Quito', 'Ecuador', '1947524569'),
('1404001598', 'Daniel Pedro', 'San Sebastian, 18 de Noviembre y Lourdes', 'Chimborazo', 'Ecuador', '0871356789'),
('1504001698', 'Luis Miguel', 'San Sebastian, 18 de Noviembre y Azuay', 'Bolivar', 'Ecuador', '0971356589'),
('1504101778', 'Volanio Fabricio', 'El Valle, Juan de Salinas y Vilcabamba', 'Loja', 'Ecuador', '0968357689'),
('1604001778', 'Matias Fabricio', 'La banda, Manuela Saenz y Taxiche', 'El Oro', 'Ecuador', '0968357689'),
('1604001798', 'Luis Guillermo', 'San Pedro, 18 de Noviembre y Rocafuerte', 'Loja', 'Ecuador', '0972357589'),
('1704001768', 'Carlos José', 'El Valle, Avenida Universitaria y Guayaquil', 'Loja', 'Ecuador', '0967377689'),
('1704001798', 'Marco Fabricio', 'La banda, Manuela Saenz y Vilcabamba', 'Imbabura', 'Ecuador', '0976357689'),
('1706101778', 'Valdp Franco', 'La Padera, Juan Bosco y Jaramijó', 'Pichincha', 'Ecuador', '0978757629'),
('1904101798', 'Matias Fabricio', 'La banda, Manuela Saenz y Taxiche', 'Guayas', 'Ecuador', '0968357689');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `order details`
--

CREATE TABLE `order details` (
  `OrderID` int(10) UNSIGNED NOT NULL,
  `ProductID` smallint(5) UNSIGNED NOT NULL,
  `UnitPrice` decimal(8,2) UNSIGNED NOT NULL DEFAULT '999999.99',
  `Quantity` smallint(2) UNSIGNED NOT NULL DEFAULT '1',
  `Discount` double(8,0) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orders`
--

CREATE TABLE `orders` (
  `OrderID` int(10) UNSIGNED NOT NULL,
  `CustomerID` varchar(10) DEFAULT NULL,
  `OrderDate` date DEFAULT NULL,
  `RequiredDate` date DEFAULT NULL,
  `ShippedDate` date DEFAULT NULL,
  `ShipName` varchar(40) DEFAULT NULL,
  `ShipAddress` varchar(60) DEFAULT NULL,
  `ShipCity` varchar(15) DEFAULT NULL,
  `ShipCountry` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `products`
--

CREATE TABLE `products` (
  `ProductID` smallint(5) UNSIGNED NOT NULL,
  `ProductName` varchar(40) NOT NULL,
  `SupplierID` smallint(5) UNSIGNED NOT NULL,
  `CategoryID` tinyint(3) UNSIGNED NOT NULL,
  `QuantityPerUnit` varchar(20) DEFAULT NULL,
  `UnitPrice` decimal(10,2) UNSIGNED DEFAULT '0.00',
  `UnitsInStock` smallint(6) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `products`
--

INSERT INTO `products` (`ProductID`, `ProductName`, `SupplierID`, `CategoryID`, `QuantityPerUnit`, `UnitPrice`, `UnitsInStock`) VALUES
(1, 'Iphone X', 1, 2, '1', '1200.00', 90),
(2, 'Iphone 7 Plus', 2, 2, '2', '1150.00', 74),
(3, 'MacBook Pro', 1, 3, '1', '6999.00', 98),
(4, 'Cable HDMI', 2, 1, '1', '10.00', 75),
(5, 'Iphone 8 Plus', 3, 2, '1', '800.00', 76),
(6, 'AirPods', 1, 1, '1', '100.00', 77),
(7, 'Apple Watch', 1, 1, '3', '300.00', 78),
(8, 'iPod Classic', 1, 1, '2', '200.00', 79),
(9, 'Mac Pro', 1, 5, '1', '700.00', 98),
(10, 'MacBook Air', 1, 2, '1', '3000.00', 99),
(11, 'Iphone 4S', 1, 2, '1', '200.00', 100),
(12, 'IMac', 1, 5, '2', '800.00', 101),
(13, 'PowerMAc G4 Cube', 3, 1, '1', '2000.00', 102),
(14, 'Apple Tv', 1, 1, '2', '2000.00', 103),
(15, 'Apple Care', 1, 1, '5', '500.00', 104),
(16, 'Iphone 6 Plus', 1, 2, '2', '6999.00', 105),
(17, 'IMac 21.5\" quad-core i5 2.7GHz/8GB/1TB/I', 1, 5, '1', '1098.35', 40),
(18, 'iMac 21.5\" quad-core i5 2.9GHz/8GB/1TB/G', 1, 5, '1', '1263.64', 430),
(19, 'Mac mini dual-core i5 2.5GHz/4GB/500GB/H', 1, 5, '1', '1663.67', 40),
(20, 'MacBook Air 11-inch dual-core i5 1.4GHz/', 1, 3, '2', '767.67', 20),
(21, 'MacBook Pro 13-inch dual-core i5 2.5GHz/', 1, 3, '1', '1015.70', 25),
(22, 'Huawei P20', 6, 2, '3', '800.70', 22),
(23, 'Alcatel A5 LED', 9, 2, '2', '400.70', 23),
(24, 'Samsung', 8, 2, '2', '1015.70', 27),
(25, 'Lenovo Vibe K6', 12, 1, '1', '800.50', 30),
(26, 'LG G6', 10, 2, '2', '500.70', 25),
(27, 'Motorola z2 Force', 11, 2, '1', '900.70', 28),
(28, 'Nokia XL', 7, 2, '1', '700.70', 21);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `suppliers`
--

CREATE TABLE `suppliers` (
  `SupplierID` smallint(5) UNSIGNED NOT NULL,
  `Name` varchar(40) NOT NULL,
  `Address` varchar(60) DEFAULT NULL,
  `City` varchar(15) DEFAULT NULL,
  `Country` varchar(15) DEFAULT NULL,
  `Phone` varchar(24) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `suppliers`
--

INSERT INTO `suppliers` (`SupplierID`, `Name`, `Address`, `City`, `Country`, `Phone`) VALUES
(1, 'Apple', 'Apple Campus', 'Cupertino', 'Estados Unidos', '12345'),
(2, 'Amazon', '20450 Stevens Creek Blvd.', 'Cupertino', 'Estados Unidos', '(408) 790-6400'),
(3, 'Ebay', 'San José', 'California', 'Estados Unidos', '(834) 7690-6670'),
(4, 'IBM', 'Armonk', 'Nueva York', 'Estados Unidos', '(834) 890-6670'),
(5, 'Toshiba', 'Campus Minato', 'Minato', 'Tokio', '(534) 890-370'),
(6, 'Huawei', 'Campus Huawei', 'Shenzhen', 'Republica Popul', '(234) 890-6670'),
(7, 'Nokia', 'Campus Nokia', 'Espoo', 'Finlandia', '(134) 690-5670'),
(8, 'Samsung', 'Campus Samsung', 'Seúl', 'Corea del Sur', '(224) 490-2670'),
(9, 'Alcatel', 'Campus Alcatel', 'Boulogne Billan', 'Francia', '(234) 890-6670'),
(10, 'Lg Electronics', 'Campus Lg Electronics', 'Seúl', 'Corea del Sur', '(124) 860-6672'),
(11, 'Motorola', 'Campus Motorola', 'Chicago Illinoi', 'Estados Unidos', '(534) 190-4670'),
(12, 'Lenovo', 'Campus Lenovo', 'Quarry Bay', 'Hong Kong', '(343) 290-3470');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`CategoryID`),
  ADD UNIQUE KEY `CategoryName` (`CategoryName`);

--
-- Indices de la tabla `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`CustomerID`),
  ADD KEY `City` (`City`);

--
-- Indices de la tabla `order details`
--
ALTER TABLE `order details`
  ADD PRIMARY KEY (`OrderID`,`ProductID`),
  ADD KEY `ProductID` (`ProductID`);

--
-- Indices de la tabla `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`OrderID`),
  ADD KEY `OrderDate` (`OrderDate`),
  ADD KEY `ShippedDate` (`ShippedDate`);

--
-- Indices de la tabla `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`ProductID`),
  ADD KEY `ProductName` (`ProductName`),
  ADD KEY `CategoryID` (`CategoryID`),
  ADD KEY `SupplierID` (`SupplierID`);

--
-- Indices de la tabla `suppliers`
--
ALTER TABLE `suppliers`
  ADD PRIMARY KEY (`SupplierID`),
  ADD KEY `CompanyName` (`Name`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categories`
--
ALTER TABLE `categories`
  MODIFY `CategoryID` tinyint(3) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `orders`
--
ALTER TABLE `orders`
  MODIFY `OrderID` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `products`
--
ALTER TABLE `products`
  MODIFY `ProductID` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT de la tabla `suppliers`
--
ALTER TABLE `suppliers`
  MODIFY `SupplierID` smallint(5) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `order details`
--
ALTER TABLE `order details`
  ADD CONSTRAINT `order details_ibfk_1` FOREIGN KEY (`OrderID`) REFERENCES `orders` (`OrderID`),
  ADD CONSTRAINT `order details_ibfk_2` FOREIGN KEY (`ProductID`) REFERENCES `products` (`ProductID`);

--
-- Filtros para la tabla `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `products_ibfk_1` FOREIGN KEY (`CategoryID`) REFERENCES `categories` (`CategoryID`),
  ADD CONSTRAINT `products_ibfk_2` FOREIGN KEY (`SupplierID`) REFERENCES `suppliers` (`SupplierID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
