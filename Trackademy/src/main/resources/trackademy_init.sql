-- =============================================================
--  TRACKADEMY — Script d'initialisation (données fictives)
--  Base     : trackademydb
--  Adapté à la structure RÉELLE des tables MySQL
--  Date     : 2026-03-04
-- =============================================================

USE trackademydb;

-- =============================================================
-- 1. NETTOYAGE (ordre inverse des FK)
-- =============================================================
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE note;
TRUNCATE TABLE devoir;
TRUNCATE TABLE cours;
TRUNCATE TABLE evenement;
TRUNCATE TABLE transaction_budget;
TRUNCATE TABLE etudiante;
SET FOREIGN_KEY_CHECKS = 1;

-- =============================================================
-- 2. TABLE : etudiante
--    Colonnes réelles : id | nom | prenom | programme | session | mot_passe | email
-- =============================================================
INSERT INTO etudiante (nom, prenom, programme, session, mot_passe, email) VALUES
('Elamba',     'Lashwana',   'Techniques de l''informatique',  'Hiver 2026', 'lashwana123',  'lashwana.elamba@etud.cegep.qc.ca'),
('Tremblay',   'Felix',      'Développement de jeux vidéo',    'Hiver 2026', 'felix456',     'felix.tremblay@etud.cegep.qc.ca'),
('Nguyen',     'Mia',        'Techniques de l''informatique',  'Hiver 2026', 'mia789',       'mia.nguyen@etud.cegep.qc.ca'),
('Beaulieu',   'Samuel',     'Administration des réseaux',     'Hiver 2026', 'sam2026',      'samuel.beaulieu@etud.cegep.qc.ca');

-- =============================================================
-- 3. TABLE : cours
--    Colonnes réelles :
--      id | nom_cours (NOT NULL) | code_cours (NOT NULL) | enseignant
--      etudiante_id | couleur | credits | horaire
--      nom (NOT NULL) | professeur | salle
--
--    → nom_cours / code_cours = ancienne structure
--    → nom / professeur = colonnes ajoutées par Hibernate
--    → On remplit les deux pour respecter les contraintes NOT NULL
-- =============================================================
INSERT INTO cours
    (nom_cours,                          code_cours, enseignant,       etudiante_id,
     nom,                                professeur,       horaire,             salle,  credits, couleur)
VALUES
('Prog. Web',                           '420-B52',  'M. Tremblay',    1,
 'Programmation Web (Spring Boot)',      'M. Tremblay',    'Lun & Mer  9h–12h', 'C-200',  3, '#FFD60A'),

('Base de données',                      '420-B41',  'Mme Cote',       1,
 'Base de données avancée',             'Mme Cote',        'Mar  13h–16h',      'A-110',  3, '#60A5FA'),

('Algorithmes',                          '420-B32',  'M. Lavoie',      1,
 'Algorithmes et structures de données', 'M. Lavoie',      'Mer & Ven  9h–12h', 'B-305',  3, '#34D399'),

('Développement mobile',                 '420-C14',  'M. Martin',      1,
 'Développement mobile (Kotlin/Android)','M. Martin',      'Jeu  13h–17h',      'C-115',  3, '#F472B6'),

('Modélisation UML',                     '420-B21',  'Mme Bergeron',   1,
 'Modélisation et analyse UML',         'Mme Bergeron',   'Ven  9h–12h',       'A-220',  2, '#A78BFA');

-- =============================================================
-- 4. TABLE : devoir
--    Colonnes réelles :
--      id | titre (NOT NULL) | description | date_remise | statut
--      priorite | ponderation | cours_id
-- =============================================================
INSERT INTO devoir (titre, description, date_remise, statut, priorite, ponderation, cours_id) VALUES
-- Cours 1 – Programmation Web
('Projet Spring Boot – Gestion etudiante',
 'Application web CRUD avec Spring Boot, Thymeleaf et MySQL.',
 '2026-03-15', 'EN_COURS', 'HAUTE', 30.00, 1),

('Lab #2 – API REST avec Postman',
 'Tester les endpoints de l''API et documenter les résultats.',
 '2026-02-28', 'TERMINE', 'MOYENNE', 15.00, 1),

-- Cours 2 – Base de données
('Examen mi-session – SQL avancé',
 'Révision : jointures, sous-requêtes, vues, procédures stockées.',
 '2026-03-20', 'PLANIFIE', 'HAUTE', 25.00, 2),

('TP #3 – Triggers et procédures MySQL',
 'Implémenter 3 triggers et 2 procédures stockées.',
 '2026-03-10', 'EN_COURS', 'MOYENNE', 20.00, 2),

-- Cours 3 – Algorithmes
('Lab Algo #3 – Arbres binaires',
 'Implémenter insertion, recherche et suppression dans un BST en Java.',
 '2026-03-07', 'TERMINE', 'MOYENNE', 15.00, 3),

('Devoir – Complexité algorithmique',
 'Analyser la complexité de 5 algorithmes de tri, rédiger un rapport.',
 '2026-03-28', 'PLANIFIE', 'BASSE', 10.00, 3),

-- Cours 4 – Mobile
('Maquette UI Figma – Appli budgetaire',
 'Créer les maquettes haute-fidélité de l''application Kotlin.',
 '2026-04-01', 'PLANIFIE', 'MOYENNE', 20.00, 4),

-- Cours 5 – UML
('Diagramme de classes – Projet intégrateur',
 'Modéliser le diagramme de classes complet (min. 8 classes).',
 '2026-03-25', 'PLANIFIE', 'HAUTE', 25.00, 5);

-- =============================================================
-- 5. TABLE : note
--    Colonnes réelles :
--      id | valeur | commentaire | date_evaluation | devoir_id
--    → On note uniquement les devoirs TERMINÉ (id 2 et 5)
-- =============================================================
INSERT INTO note (valeur, commentaire, date_evaluation, devoir_id) VALUES
(88.50, 'Bonne maîtrise de l''API REST, documentation claire.',            '2026-03-01', 2),
(92.00, 'Excellent travail sur les arbres binaires, code bien structuré.', '2026-03-09', 5);

-- =============================================================
-- 6. TABLE : evenement
--    Colonnes réelles :
--      id | date_evenement | description | titre (NOT NULL) | type
-- =============================================================
INSERT INTO evenement (titre, date_evenement, type, description) VALUES
-- Mars 2026
('Remise Lab #2 – API REST',             '2026-02-28', 'DEVOIR',  'Dernier délai pour soumettre le lab Postman.'),
('Lab Algo #3 – Date limite',            '2026-03-07', 'DEVOIR',  'Arbres binaires, depot sur Omnivox.'),
('Semaine de relache',                   '2026-03-02', 'AUTRE',   'Pas de cours cette semaine.'),
('TP #3 BD – Date limite',               '2026-03-10', 'DEVOIR',  'Triggers et procédures MySQL.'),
('Rattrapage – Algorithmes',             '2026-03-12', 'COURS',   'Séance de rattrapage : graphes, BFS et DFS.'),
('Remise Projet Spring Boot',            '2026-03-15', 'DEVOIR',  'Depot final de l''application Spring Boot.'),
('Conférence – Génie logiciel',          '2026-03-19', 'AUTRE',   'Conférence invitée sur les bonnes pratiques DevOps.'),
('Examen mi-session – Base de données',  '2026-03-20', 'EXAMEN',  'Salle A-110, 13h a 16h. Notes de cours autorisées.'),
('Remise Diagramme UML',                 '2026-03-25', 'DEVOIR',  'Diagramme de classes sur Moodle.'),
('Devoir Complexité algorithmique',      '2026-03-28', 'DEVOIR',  'Rapport PDF a déposer sur Omnivox.'),
-- Avril 2026
('Maquette Figma – Kotlin App',          '2026-04-01', 'DEVOIR',  'Maquettes UI de l''application mobile.'),
('Examen final – Programmation Web',     '2026-04-08', 'EXAMEN',  'Examen synthese Spring Boot. Salle C-200, 9h a 12h.'),
('Examen final – Algorithmes',           '2026-04-14', 'EXAMEN',  'Examen final. Salle B-305, 9h a 12h.');

-- =============================================================
-- 7. TABLE : transaction_budget
--    Colonnes réelles :
--      id | categorie | date_transaction | description (NOT NULL) | montant | type
-- =============================================================
INSERT INTO transaction_budget (description, montant, type, categorie, date_transaction) VALUES
-- ---- REVENUS ------------------------------------------------
('Bourse d''études Hiver 2026',           1500.00, 'REVENU',  'Bourse',      '2026-02-01'),
('Salaire emploi a temps partiel',         425.50, 'REVENU',  'Emploi',      '2026-02-14'),
('Allocation familiale',                   200.00, 'REVENU',  'Autre',       '2026-03-01'),
('Salaire emploi a temps partiel',         425.50, 'REVENU',  'Emploi',      '2026-03-14'),
('Remboursement coloc – Internet',          28.00, 'REVENU',  'Autre',       '2026-03-05'),

-- ---- DÉPENSES -----------------------------------------------
('Loyer mars 2026',                        650.00, 'DEPENSE', 'Loyer',       '2026-03-01'),
('Epicerie IGA',                           118.47, 'DEPENSE', 'Nourriture',  '2026-03-02'),
('Transport STM – passe mensuelle',         97.00, 'DEPENSE', 'Transport',   '2026-03-01'),
('Manuel Effective Java 3e ed.',            58.99, 'DEPENSE', 'Etudes',      '2026-02-20'),
('Abonnement Netflix',                      17.99, 'DEPENSE', 'Loisirs',     '2026-03-05'),
('Pharmacie Jean-Coutu',                    35.50, 'DEPENSE', 'Sante',       '2026-03-07'),
('Restaurant – Sortie entre amis',          42.00, 'DEPENSE', 'Loisirs',     '2026-03-08'),
('Epicerie Costco',                         96.20, 'DEPENSE', 'Nourriture',  '2026-03-15'),
('Cle USB 64 Go',                           12.49, 'DEPENSE', 'Etudes',      '2026-02-25'),
('Cafe etudiant – Cafeteria',               18.75, 'DEPENSE', 'Nourriture',  '2026-03-03'),
('Spotify Premium',                         11.99, 'DEPENSE', 'Loisirs',     '2026-03-06'),
('Loyer fevrier 2026',                     650.00, 'DEPENSE', 'Loyer',       '2026-02-01'),
('Transport STM – passe mensuelle fevr.',   97.00, 'DEPENSE', 'Transport',   '2026-02-01'),
('Epicerie IGA fevrier',                   105.30, 'DEPENSE', 'Nourriture',  '2026-02-10');

-- =============================================================
-- 8. Vérification rapide
-- =============================================================
SELECT 'etudiante'          AS table_name, COUNT(*) AS nb_lignes FROM etudiante
UNION ALL SELECT 'cours',            COUNT(*) FROM cours
UNION ALL SELECT 'devoir',           COUNT(*) FROM devoir
UNION ALL SELECT 'note',             COUNT(*) FROM note
UNION ALL SELECT 'evenement',        COUNT(*) FROM evenement
UNION ALL SELECT 'transaction_budget', COUNT(*) FROM transaction_budget;
