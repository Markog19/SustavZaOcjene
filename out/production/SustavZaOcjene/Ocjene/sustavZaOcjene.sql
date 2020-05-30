-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Mar 08, 2020 at 07:19 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `puj`
--

-- --------------------------------------------------------

--
-- Table structure for table `korisnik`
--

CREATE TABLE `korisnik` (
  `ID` int(11) NOT NULL,
  `korisnicko_ime` varchar(50) NOT NULL,
  `lozinka` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `isAdmin` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `korisnik`
--

INSERT INTO `korisnik` (`ID`, `korisnicko_ime`, `lozinka`, `email`, `isAdmin`) VALUES
(1, 'Marko', 'Marko', 'saddas@gmail.com', 1),
(2, 'Ucenik', 'Ucenik', 'fsdsa', 0),
(6, 'Ivan', 'fafas', '1234567', 0),
(7, '', 'Email', 'Lozinka', 0);

-- --------------------------------------------------------

--
-- Table structure for table `ocjene`
--

CREATE TABLE `ocjene` (
  `ID` int(11) NOT NULL,
  `Datum` varchar(50) NOT NULL,
  `Ime` varchar(50) NOT NULL,
  `Profesor` varchar(50) NOT NULL,
  `Predmet` varchar(50) NOT NULL,
  `Ocjena` varchar(50) NOT NULL,
  `IDKorisnik` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `ocjene`
--

INSERT INTO `ocjene` (`ID`, `Datum`, `Ime`, `Profesor`, `Predmet`, `Ocjena`, `IDKorisnik`) VALUES
(38, '21.1.2020.', 'Ucenik', 'Marko', 'Matematika', '4', 2),
(40, '21.1.2020.', 'Ucenik', 'Marko', 'Povijest', '1', 2),
(42, '22.1.2020', 'Ucenik', 'Marko', 'Povijest', '3', 2),
(43, 'fasf', 'fasfa', 'fasfa', 'dasda', '3', 6),
(44, '22.1.2', 'Ivan', 'Marko', 'Matematika', '3', 6),
(45, '22.1.2', 'Ivan', 'Marko', 'Engleski', '3', 6),
(46, '23.1.2020', 'Ivan', 'Marko', 'Matematika', '3', 6),
(47, '23.1.2020', 'Ivan', 'Marko', 'Hrvatski', '3', 6),
(48, '23.1.2020', 'Ivan', 'Marko', 'Fizika', '3', 6);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `korisnik`
--
ALTER TABLE `korisnik`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `ocjene`
--
ALTER TABLE `ocjene`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `korisnik`
--
ALTER TABLE `korisnik`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `ocjene`
--
ALTER TABLE `ocjene`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
