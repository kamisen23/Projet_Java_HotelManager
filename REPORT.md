# Rapport Detaillé du Projet de Gestion Hôtelière

## 1. Aperçu du Projet
Cette application de gestion hôtelière permet de gérer efficacement le personnel et les finances d'un hôtel. Elle facilite l'ajout, la modification et la suppression des employés, ainsi que la gestion des transactions financières. Le projet vise à simplifier les opérations quotidiennes des hôtels et à améliorer l'efficacité des processus de gestion.

## 2. Technologies Utilisées
- **Langage de Programmation**: Java - Choisi pour sa robustesse et sa portabilité.
- **Framework**: JavaFX pour l'interface utilisateur - Permet de créer des interfaces graphiques modernes et réactives.
- **Base de Données**: Oracle Database - Utilisé pour sa fiabilité et ses fonctionnalités avancées de gestion des données.
- **Outils de Développement**: Maven pour la gestion des dépendances et la compilation, IDE Java (VScode) pour le développement.

## 3. Fonctionnalités
- **Gestion des employés**: Ajouter, modifier et supprimer des employés. Par exemple, un gestionnaire peut facilement ajouter un nouvel employé via l'interface.
- **Gestion des transactions financières**: Enregistrer les revenus et les dépenses, permettant une vue d'ensemble des finances de l'hôtel.
- **Visualisation des rapports financiers**: Générer des rapports pour analyser les performances financières.
- **Recherche d'employés**: Trouver rapidement des employés par nom ou poste, facilitant la gestion des ressources humaines.

## 4. Conception de la Base de Données
La base de données est structurée autour de deux tables principales : `employees` et `financial_transactions`. Chaque table contient des champs spécifiques pour stocker les informations nécessaires.

### Table des Employés (`employees`)
- **id**: Identifiant unique de l'employé (clé primaire).
- **first_name**: Prénom de l'employé.
- **last_name**: Nom de famille de l'employé.
- **email**: Adresse email de l'employé.
- **phone**: Numéro de téléphone de l'employé.
- **position**: Poste occupé par l'employé dans l'hôtel.
- **salary**: Salaire de l'employé.
- **hire_date**: Date d'embauche de l'employé.

### Table des Transactions Financières (`financial_transactions`)
- **transaction_id**: Identifiant unique de la transaction (clé primaire).
- **employee_id**: Identifiant de l'employé associé à la transaction (clé étrangère).
- **amount**: Montant de la transaction.
- **description**: Description de la transaction.
- **transaction_date**: Date de la transaction.
- **category**: Catégorie de la transaction (ex. : revenu, dépense).

## 5. Interface Utilisateur
L'interface utilisateur est conçue pour être intuitive et facile à naviguer, permettant aux utilisateurs d'accéder rapidement aux fonctionnalités principales. Des composants comme des boutons, des champs de texte et des tableaux sont utilisés pour améliorer l'expérience utilisateur.

## 6. Installation et Configuration
1. Cloner le dépôt du projet.
2. Configurer la base de données comme décrit dans le fichier `README.md`.
3. Compiler le projet avec Maven et exécuter l'application. En cas de problèmes, vérifier les configurations de connexion à la base de données.

## 7. Instructions d'Utilisation
Les utilisateurs peuvent interagir avec l'application via l'interface graphique pour ajouter des employés, enregistrer des transactions et générer des rapports. Par exemple, pour ajouter un employé, l'utilisateur doit remplir un formulaire avec les détails requis.

## 8. Améliorations Futures
- Intégration d'une fonctionnalité de gestion des réservations.
- Ajout d'une interface de reporting plus avancée pour des analyses plus détaillées.
- Amélioration de la sécurité des données avec des mécanismes de cryptage.

## 9. Conclusion
Ce projet vise à fournir une solution complète pour la gestion des opérations hôtelières, facilitant ainsi la gestion quotidienne des employés et des finances. Les améliorations futures envisagées permettront d'enrichir encore davantage l'application et d'optimiser l'expérience utilisateur.
