-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Anamakine: 127.0.0.1
-- Üretim Zamanı: 19 Tem 2023, 20:30:46
-- Sunucu sürümü: 10.4.25-MariaDB
-- PHP Sürümü: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `202503077`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `announcement`
--

CREATE TABLE `announcement` (
  `announcementID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `announcementName` varchar(25) NOT NULL,
  `announcementText` varchar(250) NOT NULL,
  `announcementDate` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `announcement`
--

INSERT INTO `announcement` (`announcementID`, `userID`, `announcementName`, `announcementText`, `announcementDate`) VALUES
(10, 1, 'tettg4', 'kuhkhukh', '2023-06-07 23:24:48'),
(18, 2, 'testUser', 'test duyurusu user', '2023-06-22 19:13:10'),
(20, 1, 'test duyrusu', 'test duyurusu', '2023-06-23 17:26:13');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `log_till`
--

CREATE TABLE `log_till` (
  `logID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `currencyID` int(11) NOT NULL,
  `userAction` varchar(100) NOT NULL,
  `oldMoney` decimal(14,4) NOT NULL,
  `newMoney` decimal(14,4) NOT NULL,
  `changingMoney` decimal(14,4) NOT NULL,
  `actionDate` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `log_till`
--

INSERT INTO `log_till` (`logID`, `userID`, `currencyID`, `userAction`, `oldMoney`, `newMoney`, `changingMoney`, `actionDate`) VALUES
(1, 1, 8, 'Para Eklendi', '140.0000', '160.0000', '20.0000', '2023-06-10 14:39:54'),
(2, 1, 8, 'Para Silindi', '160.0000', '100.0000', '60.0000', '2023-06-10 14:40:03'),
(3, 1, 25, 'Para Birimi Eklendi', '0.0000', '100.0000', '100.0000', '2023-06-10 15:47:49'),
(4, 1, 25, 'Para Eklendi', '100.0000', '120.0000', '120.0000', '2023-06-10 15:48:00'),
(5, 1, 25, 'Para Silindi', '120.0000', '90.0000', '90.0000', '2023-06-10 15:48:07'),
(6, 1, 25, 'POUND STERLING Para Birimi Silindi', '0.0000', '0.0000', '0.0000', '2023-06-10 15:48:39'),
(7, 1, 3, 'Para Eklendi', '100.0000', '110.0000', '10.0000', '2023-06-10 15:49:58'),
(8, 1, 3, 'Para Silindi', '110.0000', '90.0000', '20.0000', '2023-06-10 15:50:06'),
(9, 1, 2, 'Para Eklendi', '150.0000', '200.0000', '50.0000', '2023-06-13 15:34:43'),
(10, 1, 3, 'Para Eklendi', '47.0024', '207.0024', '160.0000', '2023-06-13 15:34:57'),
(11, 1, 1, 'Para Eklendi', '100.0000', '200.0000', '100.0000', '2023-06-13 15:35:09'),
(12, 1, 26, 'Para Birimi Eklendi', '0.0000', '200.0000', '200.0000', '2023-06-13 15:35:35'),
(13, 1, 26, 'Para Silindi', '200.0000', '100.0000', '100.0000', '2023-06-13 15:35:47'),
(14, 1, 26, 'POUND STERLING Para Birimi Silindi', '0.0000', '0.0000', '0.0000', '2023-06-13 15:35:55'),
(15, 1, 3, 'Para Eklendi', '207.0024', '210.0024', '3.0000', '2023-06-13 15:38:00'),
(16, 1, 1, 'Para Eklendi', '148.9396', '248.9396', '100.0000', '2023-06-22 19:16:44'),
(17, 1, 1, 'Para Silindi', '248.9396', '200.9396', '48.0000', '2023-06-22 19:18:01'),
(18, 1, 1, 'Para Eklendi', '200.9396', '250.9396', '50.0000', '2023-06-22 19:31:07'),
(19, 1, 27, 'Para Birimi Eklendi', '0.0000', '100.0000', '100.0000', '2023-06-23 17:43:15'),
(20, 1, 28, 'Para Birimi Eklendi', '0.0000', '100.0000', '100.0000', '2023-06-23 17:43:26'),
(21, 1, 27, 'Para Eklendi', '100.0000', '200.0000', '100.0000', '2023-06-23 17:43:56'),
(22, 1, 27, 'Para Silindi', '200.0000', '100.0000', '100.0000', '2023-06-23 17:44:13'),
(23, 1, 28, 'Para Silindi', '100.0000', '50.0000', '50.0000', '2023-06-23 17:44:25'),
(24, 1, 28, 'KUWAITI DINAR Para Birimi Silindi', '0.0000', '0.0000', '0.0000', '2023-06-23 17:44:36');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `log_users`
--

CREATE TABLE `log_users` (
  `logID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `userAction` varchar(100) NOT NULL,
  `userLastEntry` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `log_users`
--

INSERT INTO `log_users` (`logID`, `userID`, `userAction`, `userLastEntry`) VALUES
(1, 1, 'Giriş Yapıldı', '2023-06-13 12:29:35'),
(2, 1, 'admin admin nın Bilgileri Güncellendi', '2023-06-13 12:29:53'),
(3, 1, 'Giriş Yapıldı', '2023-06-13 12:33:29'),
(6, 1, 'EUR alınıp USD satılarak. Alım Satım İşlemi Yapıldı', '2023-06-13 12:42:06'),
(7, 1, 'USD alınıp EUR satılarak. Alım Satım İşlemi Yapıldı', '2023-06-13 12:42:58'),
(8, 1, 'Çıkış Yapıldı', '2023-06-13 12:47:15'),
(9, 2, 'Giriş Yapıldı', '2023-06-13 12:51:55'),
(10, 2, 'Duyuru Eklendi', '2023-06-13 12:52:10'),
(11, 2, 'Duyuru Silindi', '2023-06-13 12:52:15'),
(12, 2, 'test testt nın Bilgileri Güncellendi', '2023-06-13 12:53:36'),
(13, 2, 'test testt nın Bilgileri Güncellendi', '2023-06-13 12:53:49'),
(14, 2, 'Çıkış Yapıldı', '2023-06-13 12:53:52'),
(15, 2, 'Giriş Yapıldı', '2023-06-13 12:54:08'),
(16, 2, 'EUR alınıp TRY satılarak. Alım Satım İşlemi Yapıldı', '2023-06-13 12:54:50'),
(17, 2, 'Çıkış Yapıldı', '2023-06-13 12:55:46'),
(18, 1, 'Giriş Yapıldı', '2023-06-13 12:55:56'),
(19, 1, 'Duyuru Eklendi', '2023-06-13 12:56:02'),
(20, 1, 'Duyuru Silindi', '2023-06-13 12:56:05'),
(21, 1, 'Duyuru Eklendi', '2023-06-13 12:56:15'),
(22, 1, 'Çıkış Yapıldı', '2023-06-13 12:56:43'),
(23, 1, 'Giriş Yapıldı', '2023-06-16 11:24:13'),
(24, 1, 'Giriş Yapıldı', '2023-06-16 11:25:35'),
(25, 1, 'Çıkış Yapıldı', '2023-06-16 11:25:53'),
(26, 1, 'Çıkış Yapıldı', '2023-06-16 11:32:36'),
(27, 1, 'Giriş Yapıldı', '2023-06-16 11:41:44'),
(78, 2, 'Duyuru Eklendi', '2023-06-22 16:13:16'),
(79, 2, 'Duyuru Silindi', '2023-06-22 16:13:18'),
(80, 1, 'admin admin nın Bilgileri Güncellendi', '2023-06-22 16:13:57'),
(81, 2, 'test testt nın Bilgileri Güncellendi', '2023-06-22 16:14:38'),
(82, 2, 'test testt nın Bilgileri Güncellendi', '2023-06-22 16:15:01'),
(83, 1, 'testdd testgf üye olarak eklendi.', '2023-06-22 16:22:00'),
(84, 1, '  kullanıcısı silindi.', '2023-06-22 16:22:23'),
(85, 1, 'EUR alınıp USD satılarak. Alım Satım İşlemi Yapıldı', '2023-06-22 16:23:31'),
(86, 1, 'EUR alınıp USD satılarak. Alım Satım İşlemi Yapıldı', '2023-06-22 16:24:10'),
(87, 1, 'Çıkış Yapıldı', '2023-06-22 16:25:57'),
(88, 2, 'Çıkış Yapıldı', '2023-06-22 16:25:59'),
(89, 1, 'Giriş Yapıldı', '2023-06-22 16:29:41'),
(90, 1, 'testfe fesfsg üye olarak eklendi.', '2023-06-22 16:30:18'),
(91, 1, '12345678902 TC nolu kullanıcı silindi.', '2023-06-22 16:30:36'),
(92, 1, 'Çıkış Yapıldı', '2023-06-22 16:31:25'),
(93, 1, 'Giriş Yapıldı', '2023-06-23 12:48:39'),
(94, 1, 'Çıkış Yapıldı', '2023-06-23 12:50:37'),
(95, 1, 'Giriş Yapıldı', '2023-06-23 14:24:58'),
(96, 1, 'Çıkış Yapıldı', '2023-06-23 14:25:03'),
(97, 1, 'Giriş Yapıldı', '2023-06-23 14:25:19'),
(98, 1, 'Duyuru Eklendi', '2023-06-23 14:26:13'),
(99, 1, 'Duyuru Silindi', '2023-06-23 14:26:37'),
(100, 1, 'Çıkış Yapıldı', '2023-06-23 14:27:52'),
(101, 1, 'Giriş Yapıldı', '2023-06-23 14:29:00'),
(102, 1, 'Duyuru Eklendi', '2023-06-23 14:30:15'),
(103, 1, 'Duyuru Silindi', '2023-06-23 14:30:25'),
(104, 1, 'test test üye olarak eklendi.', '2023-06-23 14:33:37'),
(105, 1, '12345678905 TC nolu kullanıcı silindi.', '2023-06-23 14:34:17'),
(106, 1, '45678912301 TC nolu kullanıcı silindi.', '2023-06-23 14:34:31'),
(107, 1, '12345678905 TC nolu kullanıcı silindi.', '2023-06-23 14:34:46'),
(108, 1, 'Çıkış Yapıldı', '2023-06-23 14:35:50'),
(109, 1, 'Giriş Yapıldı', '2023-06-23 14:36:54'),
(110, 1, 'Duyuru Eklendi', '2023-06-23 14:38:08'),
(111, 1, 'Duyuru Silindi', '2023-06-23 14:38:19'),
(112, 1, 'mehmet kaya üye olarak eklendi.', '2023-06-23 14:40:29'),
(113, 1, 'testtt testdt üye olarak eklendi.', '2023-06-23 14:41:24'),
(114, 1, '01234567892 TC nolu kullanıcı silindi.', '2023-06-23 14:41:54'),
(115, 1, '01234567891 TC nolu kullanıcı silindi.', '2023-06-23 14:42:30'),
(116, 1, 'admin admin nın Bilgileri Güncellendi', '2023-06-23 14:46:24'),
(117, 1, 'admin admin nın Bilgileri Güncellendi', '2023-06-23 14:46:47'),
(118, 1, 'EUR alınıp USD satılarak. Alım Satım İşlemi Yapıldı', '2023-06-23 14:48:24'),
(119, 1, 'Çıkış Yapıldı', '2023-06-23 14:53:07'),
(120, 2, 'Giriş Yapıldı', '2023-06-23 14:53:16'),
(121, 2, 'test testt nın Bilgileri Güncellendi', '2023-06-23 14:54:16'),
(122, 2, 'test testt nın Bilgileri Güncellendi', '2023-06-23 14:54:31');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `operation`
--

CREATE TABLE `operation` (
  `operationID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `customerName` varchar(25) NOT NULL,
  `customerSurname` varchar(25) NOT NULL,
  `customerTC` char(11) NOT NULL,
  `customerPhone` char(11) NOT NULL,
  `customerMail` varchar(25) NOT NULL,
  `sellCode` varchar(3) NOT NULL,
  `buyCode` varchar(3) NOT NULL,
  `sellInstantPrice` decimal(14,4) NOT NULL,
  `buyAmount` decimal(14,4) NOT NULL,
  `sellAmount` decimal(14,4) NOT NULL,
  `operationDate` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `operation`
--

INSERT INTO `operation` (`operationID`, `userID`, `customerName`, `customerSurname`, `customerTC`, `customerPhone`, `customerMail`, `sellCode`, `buyCode`, `sellInstantPrice`, `buyAmount`, `sellAmount`, `operationDate`) VALUES
(13, 1, 'testA', 'testA', '00000111112', '12332112332', 'tesmaill', 'USD', 'EUR', '20.0000', '25.5302', '21.5615', '2023-06-13 15:42:06'),
(14, 1, 'testk', 'testkk', '45645645656', '47474747474', 'juku', 'EUR', 'USD', '20.0000', '25.5302', '19.9640', '2023-06-13 15:42:58'),
(15, 2, 'testu', 'testu', '12312365498', '12332145656', 'hthth', 'TRY', 'EUR', '2.0000', '25.5302', '51.0604', '2023-06-13 15:54:50'),
(16, 1, 'ahmet', 'kartal', '54687912301', '12345600123', 'ahmet@gmail.com', 'USD', 'EUR', '10.0000', '25.9732', '10.9747', '2023-06-22 19:23:31'),
(18, 1, 'mehmet', 'kaya', '01234569877', '05424324359', 'mehmet@mail.com', 'USD', 'EUR', '100.0000', '27.4579', '108.5464', '2023-06-23 17:48:24');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `till`
--

CREATE TABLE `till` (
  `currencyID` int(11) NOT NULL,
  `currency` varchar(30) NOT NULL,
  `currencyCode` varchar(3) NOT NULL,
  `currentMoney` decimal(14,4) NOT NULL,
  `currencyUploadDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `till`
--

INSERT INTO `till` (`currencyID`, `currency`, `currencyCode`, `currentMoney`, `currencyUploadDate`) VALUES
(1, 'TÜRK LİRASI', 'TRY', '250.9396', '2023-06-13'),
(2, 'EURO', 'EUR', '300.0360', '2023-06-23'),
(3, 'US DOLLAR', 'USD', '104.2998', '2023-06-23'),
(27, 'SWISS FRANK', 'CHF', '100.0000', '2023-06-23');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `users`
--

CREATE TABLE `users` (
  `userID` int(11) NOT NULL,
  `name` varchar(25) NOT NULL,
  `surname` varchar(25) NOT NULL,
  `userTC` char(11) DEFAULT NULL,
  `username` varchar(25) NOT NULL,
  `userPassword` char(32) NOT NULL,
  `userPhone` varchar(15) NOT NULL,
  `userMail` varchar(30) NOT NULL,
  `userBirthDate` date NOT NULL,
  `userAuthority` bit(1) NOT NULL,
  `userCreationDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Tablo döküm verisi `users`
--

INSERT INTO `users` (`userID`, `name`, `surname`, `userTC`, `username`, `userPassword`, `userPhone`, `userMail`, `userBirthDate`, `userAuthority`, `userCreationDate`) VALUES
(1, 'admin', 'admin', '12345678901', 'admin', '827ccb0eea8a706c4c34a16891f84e7b', '00000456789', 'test@gmail.com', '2000-12-22', b'0', '2023-05-22'),
(2, 'test', 'testt', '00000000002', 'user', 'e10adc3949ba59abbe56e057f20f883e', '00000000003', 'test@gmail.com', '2000-12-22', b'1', '2023-05-24');

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `announcement`
--
ALTER TABLE `announcement`
  ADD PRIMARY KEY (`announcementID`);

--
-- Tablo için indeksler `log_till`
--
ALTER TABLE `log_till`
  ADD PRIMARY KEY (`logID`);

--
-- Tablo için indeksler `log_users`
--
ALTER TABLE `log_users`
  ADD PRIMARY KEY (`logID`);

--
-- Tablo için indeksler `operation`
--
ALTER TABLE `operation`
  ADD PRIMARY KEY (`operationID`);

--
-- Tablo için indeksler `till`
--
ALTER TABLE `till`
  ADD PRIMARY KEY (`currencyID`);

--
-- Tablo için indeksler `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userID`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `announcement`
--
ALTER TABLE `announcement`
  MODIFY `announcementID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- Tablo için AUTO_INCREMENT değeri `log_till`
--
ALTER TABLE `log_till`
  MODIFY `logID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- Tablo için AUTO_INCREMENT değeri `log_users`
--
ALTER TABLE `log_users`
  MODIFY `logID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=123;

--
-- Tablo için AUTO_INCREMENT değeri `operation`
--
ALTER TABLE `operation`
  MODIFY `operationID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Tablo için AUTO_INCREMENT değeri `till`
--
ALTER TABLE `till`
  MODIFY `currencyID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- Tablo için AUTO_INCREMENT değeri `users`
--
ALTER TABLE `users`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
