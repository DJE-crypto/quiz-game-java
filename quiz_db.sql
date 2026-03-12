-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3307
-- Généré le : mer. 17 déc. 2025 à 23:15
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `quiz_db`
--

-- --------------------------------------------------------

--
-- Structure de la table `difficulte`
--

CREATE TABLE `difficulte` (
  `id_difficulte` int(11) NOT NULL,
  `niveau` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `difficulte`
--

INSERT INTO `difficulte` (`id_difficulte`, `niveau`) VALUES
(1, 'Facile'),
(2, 'Moyen'),
(3, 'Difficile');

-- --------------------------------------------------------

--
-- Structure de la table `question`
--

CREATE TABLE `question` (
  `id_question` int(11) NOT NULL,
  `libelle` text NOT NULL,
  `choix1` varchar(255) NOT NULL,
  `choix2` varchar(255) NOT NULL,
  `choix3` varchar(255) NOT NULL,
  `choix4` varchar(255) NOT NULL,
  `reponse` tinyint(4) NOT NULL,
  `quiz_id` int(11) NOT NULL,
  `difficulte_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `question`
--

INSERT INTO `question` (`id_question`, `libelle`, `choix1`, `choix2`, `choix3`, `choix4`, `reponse`, `quiz_id`, `difficulte_id`) VALUES
(285, 'crffrcflk3fl3', 'rfrf', 'ff', '4r4r4', 'zzz', 2, 45, 1),
(286, 'eeeee', 'a', 'ee', 'gg', 'tt', 1, 53, 1),
(287, 'le ballon', 'ronaldo', 'messi', 'amine', 'neymar', 1, 55, 1);

-- --------------------------------------------------------

--
-- Structure de la table `quiz`
--

CREATE TABLE `quiz` (
  `id_quiz` int(11) NOT NULL,
  `titre` varchar(255) NOT NULL,
  `date_creation` date NOT NULL,
  `utilisateur_id` int(11) NOT NULL,
  `theme_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `quiz`
--

INSERT INTO `quiz` (`id_quiz`, `titre`, `date_creation`, `utilisateur_id`, `theme_id`) VALUES
(43, 'dddddd', '2025-12-08', 13, 1),
(45, 'wertzui', '2025-12-08', 13, 1),
(46, 'rrrrrrrrrrrr', '2025-12-08', 13, 1),
(48, 'ttttttttttttttttttttttttttttttttttttttttttttttttttttt', '2025-12-08', 13, 1),
(49, '00000000000000000000', '2025-12-09', 13, 1),
(51, '88888888888888888888', '2025-12-09', 13, 1),
(52, '98200000000000000000000', '2025-12-09', 13, 3),
(53, 'qqqqwwweertzesdcrdddhjkjdstasdfrtzurewsertzrewsetz', '2025-12-09', 13, 1),
(54, 'qqqqqqqqppppeeoeoeoeoeo', '2025-12-09', 13, 1),
(55, 'quiz de football', '2025-12-14', 13, 4);

-- --------------------------------------------------------

--
-- Structure de la table `role`
--

CREATE TABLE `role` (
  `id_role` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `role`
--

INSERT INTO `role` (`id_role`, `nom`) VALUES
(1, 'Admin'),
(2, 'Joueur');

-- --------------------------------------------------------

--
-- Structure de la table `score`
--

CREATE TABLE `score` (
  `id_score` int(11) NOT NULL,
  `valeur` int(11) NOT NULL,
  `date_score` date NOT NULL,
  `utilisateur_id` int(11) NOT NULL,
  `quiz_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `theme`
--

CREATE TABLE `theme` (
  `id_theme` int(11) NOT NULL,
  `nom_theme` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `theme`
--

INSERT INTO `theme` (`id_theme`, `nom_theme`) VALUES
(1, 'Histoire'),
(2, 'Géographie'),
(3, 'Science'),
(4, 'Sport'),
(5, 'Cinéma'),
(6, 'Manga'),
(7, 'Musique');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id_user` int(11) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id_user`, `nom`, `email`, `password`, `role_id`) VALUES
(13, 'Admin', 'admine@mail.com', '$2a$10$2k0epPKRAK4Wmln7B3nqWucxEc8xlzUnSlXoeXBrgsAdhAENfVb2W', 1),
(21, 'aminebkr', 'aminebkr@mail.com', '$2a$10$VdmQwmeGawiBNfyJNmpwZueD2osJpEUcpOxpJnY7LNntuKUxCcCaO', 2);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `difficulte`
--
ALTER TABLE `difficulte`
  ADD PRIMARY KEY (`id_difficulte`);

--
-- Index pour la table `question`
--
ALTER TABLE `question`
  ADD PRIMARY KEY (`id_question`),
  ADD KEY `quiz_id` (`quiz_id`),
  ADD KEY `difficulte_id` (`difficulte_id`);

--
-- Index pour la table `quiz`
--
ALTER TABLE `quiz`
  ADD PRIMARY KEY (`id_quiz`),
  ADD KEY `utilisateur_id` (`utilisateur_id`),
  ADD KEY `theme_id` (`theme_id`);

--
-- Index pour la table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id_role`);

--
-- Index pour la table `score`
--
ALTER TABLE `score`
  ADD PRIMARY KEY (`id_score`),
  ADD KEY `utilisateur_id` (`utilisateur_id`),
  ADD KEY `quiz_id` (`quiz_id`);

--
-- Index pour la table `theme`
--
ALTER TABLE `theme`
  ADD PRIMARY KEY (`id_theme`);

--
-- Index pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `role_id` (`role_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `difficulte`
--
ALTER TABLE `difficulte`
  MODIFY `id_difficulte` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `question`
--
ALTER TABLE `question`
  MODIFY `id_question` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=288;

--
-- AUTO_INCREMENT pour la table `quiz`
--
ALTER TABLE `quiz`
  MODIFY `id_quiz` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;

--
-- AUTO_INCREMENT pour la table `role`
--
ALTER TABLE `role`
  MODIFY `id_role` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `score`
--
ALTER TABLE `score`
  MODIFY `id_score` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT pour la table `theme`
--
ALTER TABLE `theme`
  MODIFY `id_theme` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `question`
--
ALTER TABLE `question`
  ADD CONSTRAINT `question_ibfk_1` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`id_quiz`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `question_ibfk_2` FOREIGN KEY (`difficulte_id`) REFERENCES `difficulte` (`id_difficulte`) ON UPDATE CASCADE;

--
-- Contraintes pour la table `quiz`
--
ALTER TABLE `quiz`
  ADD CONSTRAINT `quiz_ibfk_1` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `quiz_ibfk_2` FOREIGN KEY (`theme_id`) REFERENCES `theme` (`id_theme`) ON UPDATE CASCADE;

--
-- Contraintes pour la table `score`
--
ALTER TABLE `score`
  ADD CONSTRAINT `score_ibfk_1` FOREIGN KEY (`utilisateur_id`) REFERENCES `utilisateur` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `score_ibfk_2` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`id_quiz`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD CONSTRAINT `utilisateur_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id_role`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
