-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : jeu. 25 avr. 2024 à 15:51
-- Version du serveur : 8.0.31
-- Version de PHP : 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS bdd_amphitryon;

USE bdd_amphitryon;

--
-- Base de données : `ylesgourgues_amphitryon`
--

-- --------------------------------------------------------

--
-- Structure de la table `categorieplat`
--

DROP TABLE IF EXISTS `categorieplat`;
CREATE TABLE IF NOT EXISTS `categorieplat` (
  `IDCATEG` int NOT NULL AUTO_INCREMENT,
  `NOMCATEG` char(32) DEFAULT NULL,
  PRIMARY KEY (`IDCATEG`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `categorieplat`
--

INSERT INTO `categorieplat` (`IDCATEG`, `NOMCATEG`) VALUES
(1, 'plat_principal'),
(2, 'entree'),
(3, 'dessert');

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

DROP TABLE IF EXISTS `commande`;
CREATE TABLE IF NOT EXISTS `commande` (
  `IDCOMMANDE` int NOT NULL AUTO_INCREMENT,
  `DATE_SERVICE` date NOT NULL,
  `NUMTABLE` int NOT NULL,
  `IDSERVICE` int NOT NULL,
  `HEURECOMMANDE` time DEFAULT NULL,
  `ETATCOMMANDE` char(32) DEFAULT NULL,
  PRIMARY KEY (`IDCOMMANDE`),
  KEY `FK_COMMANDE_TABLE_RESTO` (`IDSERVICE`,`DATE_SERVICE`,`NUMTABLE`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `commande`
--

INSERT INTO `commande` (`IDCOMMANDE`, `DATE_SERVICE`, `NUMTABLE`, `IDSERVICE`, `HEURECOMMANDE`, `ETATCOMMANDE`) VALUES
(1, CURRENT_DATE(), 1, 1, '02:28:43', 'non réglée'),
(2, CURRENT_DATE(), 2, 2, '16:14:11', 'non réglée'),
(3, CURRENT_DATE(), 1, 3, '03:08:13', 'non réglée');

-- --------------------------------------------------------

--
-- Structure de la table `commander`
--

DROP TABLE IF EXISTS `commander`;
CREATE TABLE IF NOT EXISTS `commander` (
  `IDPLAT` int NOT NULL,
  `IDCOMMANDE` int NOT NULL,
  `ETATPLAT` char(32) DEFAULT NULL,
  `INFOSCOMPLEMENTAIRES` char(32) DEFAULT NULL,
  `QUANTITE` int DEFAULT NULL,
  PRIMARY KEY (`IDPLAT`,`IDCOMMANDE`),
  KEY `FK_COMMANDER_COMMANDE` (`IDCOMMANDE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `commander`
--

INSERT INTO `commander` (`IDPLAT`, `IDCOMMANDE`, `ETATPLAT`, `INFOSCOMPLEMENTAIRES`, `QUANTITE`) VALUES
(1, 1, 'commandé', 'à point', 2),
(2, 2, 'desservi', 'cuit', 3),
(3, 3, 'servi', 'rien', 2);

-- --------------------------------------------------------

--
-- Structure de la table `date_service`
--

DROP TABLE IF EXISTS `date_service`;
CREATE TABLE IF NOT EXISTS `date_service` (
  `DATE_SERVICE` date NOT NULL,
  PRIMARY KEY (`DATE_SERVICE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `date_service`
--

INSERT INTO `date_service` (`DATE_SERVICE`) VALUES
('0000-00-00'),
('2024-01-26'),
('2024-02-14'),
('2024-04-19'),
('2024-04-21'),
('2024-04-22'),
(CURRENT_DATE());

-- --------------------------------------------------------

--
-- Structure de la table `plat`
--

DROP TABLE IF EXISTS `plat`;
CREATE TABLE IF NOT EXISTS `plat` (
  `IDPLAT` int NOT NULL AUTO_INCREMENT,
  `IDCATEG` int NOT NULL,
  `NOMPLAT` char(32) DEFAULT NULL,
  `DESCRIPTIF` char(32) DEFAULT NULL,
  PRIMARY KEY (`IDPLAT`),
  KEY `FK_PLAT_CATEGORIEPLAT` (`IDCATEG`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `plat`
--

INSERT INTO `plat` (`IDPLAT`, `IDCATEG`, `NOMPLAT`, `DESCRIPTIF`) VALUES
(1, 1, 'entrecôte', 'tendre'),
(2, 1, 'riz', 'bien cuit'),
(3, 1, 'saumon', 'frais'),
(4, 2, 'Soupe', 'Chaude'),
(5, 2, 'Salade', 'bien verte'),
(6, 2, 'Tomate', 'mûres'),
(7, 3, 'Flan', 'moelleux'),
(8, 3, 'Fondant', 'chocolaté'),
(9, 3, 'Crumble', 'aux pommes');

-- --------------------------------------------------------

--
-- Structure de la table `proposerplat`
--

DROP TABLE IF EXISTS `proposerplat`;
CREATE TABLE IF NOT EXISTS `proposerplat` (
  `IDSERVICE` int NOT NULL,
  `IDPLAT` int NOT NULL,
  `DATE_SERVICE` date NOT NULL,
  `QUANTITEPROPOSEE` int DEFAULT NULL,
  `PRIXVENTE` decimal(15,2) DEFAULT NULL,
  `QUANTITEVENDUE` int DEFAULT NULL,
  PRIMARY KEY (`IDSERVICE`,`IDPLAT`,`DATE_SERVICE`),
  KEY `FK_PROPOSERPLAT_PLAT` (`IDPLAT`),
  KEY `FK_PROPOSERPLAT_DATE_SERVICE` (`DATE_SERVICE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `proposerplat`
--

INSERT INTO `proposerplat` (`IDSERVICE`, `IDPLAT`, `DATE_SERVICE`, `QUANTITEPROPOSEE`, `PRIXVENTE`, `QUANTITEVENDUE`) VALUES
(1, 1, CURRENT_DATE(), 10, '1.00', 10),
(1, 4, CURRENT_DATE(), 10, '5.00', 10),
(1, 7, CURRENT_DATE(), 100, '50.00', 10),
(2, 2, CURRENT_DATE(), 10, '5.00', 10),
(2, 5, CURRENT_DATE(), 100, '7.00', 10),
(2, 8, CURRENT_DATE(), 100, '7.00', 10),
(3, 3, CURRENT_DATE(), 100, '5.00', 10),
(3, 6, CURRENT_DATE(), 100, '5.00', 10),
(3, 9, CURRENT_DATE(), 100, '5.00', 10);

-- --------------------------------------------------------

--
-- Structure de la table `service`
--

DROP TABLE IF EXISTS `service`;
CREATE TABLE IF NOT EXISTS `service` (
  `IDSERVICE` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`IDSERVICE`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `service`
--

INSERT INTO `service` (`IDSERVICE`) VALUES
(1),
(2),
(3);

-- --------------------------------------------------------

--
-- Structure de la table `statututilisateur`
--

DROP TABLE IF EXISTS `statututilisateur`;
CREATE TABLE IF NOT EXISTS `statututilisateur` (
  `IDSTATUT` int NOT NULL AUTO_INCREMENT,
  `NOMSTATUT` char(32) DEFAULT NULL,
  PRIMARY KEY (`IDSTATUT`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `statututilisateur`
--

INSERT INTO `statututilisateur` (`IDSTATUT`, `NOMSTATUT`) VALUES
(1, 'serveur'),
(2, 'chef_cuisinier'),
(3, 'chef_salle');

-- --------------------------------------------------------

--
-- Structure de la table `table_resto`
--

DROP TABLE IF EXISTS `table_resto`;
CREATE TABLE IF NOT EXISTS `table_resto` (
  `IDSERVICE` int NOT NULL,
  `DATE_SERVICE` date NOT NULL,
  `NUMTABLE` int NOT NULL,
  `IDUTILISATEUR` int NOT NULL,
  `NOMBREPLACE` int DEFAULT NULL,
  PRIMARY KEY (`IDSERVICE`,`DATE_SERVICE`,`NUMTABLE`),
  KEY `FK_TABLE_RESTO_DATE_SERVICE` (`DATE_SERVICE`),
  KEY `FK_TABLE_RESTO_UTILISATEUR` (`IDUTILISATEUR`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `table_resto`
--

INSERT INTO `table_resto` (`IDSERVICE`, `DATE_SERVICE`, `NUMTABLE`, `IDUTILISATEUR`, `NOMBREPLACE`) VALUES
(1, CURRENT_DATE(), 1, 1, 4),
(1, CURRENT_DATE(), 2, 1, 4),
(2, CURRENT_DATE(), 1, 1, 12),
(2, CURRENT_DATE(), 2, 1, 6),
(3, CURRENT_DATE(), 1, 1, 6),
(3, CURRENT_DATE(), 2, 1, 2);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `IDUTILISATEUR` int NOT NULL AUTO_INCREMENT,
  `IDSTATUT` int NOT NULL,
  `LOGIN` char(32) DEFAULT NULL,
  `MDP` char(32) DEFAULT NULL,
  `NOM` char(32) DEFAULT NULL,
  `PRENOM` char(32) DEFAULT NULL,
  PRIMARY KEY (`IDUTILISATEUR`),
  KEY `FK_UTILISATEUR_STATUTUTILISATEUR` (`IDSTATUT`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`IDUTILISATEUR`, `IDSTATUT`, `LOGIN`, `MDP`, `NOM`, `PRENOM`) VALUES
(1, 1, 'corentin', 'corentin', 'lartigue', 'coco'),
(2, 2, 'yoann', 'yoann', 'lesgourgues', 'yoyo');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `FK_COMMANDE_TABLE_RESTO` FOREIGN KEY (`IDSERVICE`,`DATE_SERVICE`,`NUMTABLE`) REFERENCES `table_resto` (`IDSERVICE`, `DATE_SERVICE`, `NUMTABLE`);

--
-- Contraintes pour la table `commander`
--
ALTER TABLE `commander`
  ADD CONSTRAINT `FK_COMMANDER_COMMANDE` FOREIGN KEY (`IDCOMMANDE`) REFERENCES `commande` (`IDCOMMANDE`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_COMMANDER_PLAT` FOREIGN KEY (`IDPLAT`) REFERENCES `plat` (`IDPLAT`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `plat`
--
ALTER TABLE `plat`
  ADD CONSTRAINT `FK_PLAT_CATEGORIEPLAT` FOREIGN KEY (`IDCATEG`) REFERENCES `categorieplat` (`IDCATEG`);

--
-- Contraintes pour la table `proposerplat`
--
ALTER TABLE `proposerplat`
  ADD CONSTRAINT `FK_PROPOSERPLAT_DATE_SERVICE` FOREIGN KEY (`DATE_SERVICE`) REFERENCES `date_service` (`DATE_SERVICE`),
  ADD CONSTRAINT `FK_PROPOSERPLAT_PLAT` FOREIGN KEY (`IDPLAT`) REFERENCES `plat` (`IDPLAT`),
  ADD CONSTRAINT `FK_PROPOSERPLAT_SERVICE` FOREIGN KEY (`IDSERVICE`) REFERENCES `service` (`IDSERVICE`);

--
-- Contraintes pour la table `table_resto`
--
ALTER TABLE `table_resto`
  ADD CONSTRAINT `FK_TABLE_RESTO_DATE_SERVICE` FOREIGN KEY (`DATE_SERVICE`) REFERENCES `date_service` (`DATE_SERVICE`),
  ADD CONSTRAINT `FK_TABLE_RESTO_SERVICE` FOREIGN KEY (`IDSERVICE`) REFERENCES `service` (`IDSERVICE`),
  ADD CONSTRAINT `FK_TABLE_RESTO_UTILISATEUR` FOREIGN KEY (`IDUTILISATEUR`) REFERENCES `utilisateur` (`IDUTILISATEUR`);

--
-- Contraintes pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `FK_UTILISATEUR_STATUTUTILISATEUR` FOREIGN KEY (`IDSTATUT`) REFERENCES `statututilisateur` (`IDSTATUT`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
