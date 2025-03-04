# Application de Gestion Hôtelière

Cette application Java permet de gérer le personnel et les finances d'un hôtel. Elle utilise JavaFX pour l'interface graphique et Oracle comme base de données.

## Prérequis

- Java JDK 17 ou supérieur
- Maven
- Oracle Database 11g ou supérieur
- IDE Java (VScode,Eclipse, IntelliJ IDEA, etc.)

## Configuration de la base de données

1. Connectez-vous à Oracle en tant qu'administrateur (SYSTEM)
2. Exécutez les commandes suivantes pour créer l'utilisateur de l'application :

```sql
CREATE USER hotel_admin IDENTIFIED BY your_password;
GRANT CONNECT, RESOURCE TO hotel_admin;
GRANT CREATE SESSION TO hotel_admin;
```

3. Connectez-vous avec le nouvel utilisateur et exécutez le script SQL d'initialisation situé dans `src/main/resources/sql/init.sql`

## Configuration de l'application

1. Ouvrez le fichier `src/main/java/com/hotel/util/DatabaseConnection.java`
2. Modifiez les constantes suivantes selon votre configuration :
   - URL : l'URL de connexion à votre base Oracle
   - USER : le nom d'utilisateur (hotel_admin)
   - PASSWORD : le mot de passe que vous avez défini

## Compilation et exécution

1. Compilez le projet avec Maven :
```bash
mvn clean package
```

2. Exécutez l'application :
```bash
mvn javafx:run
```

## Fonctionnalités

### Gestion du Personnel
- Ajouter, modifier et supprimer des employés
- Voir la liste des employés
- Gérer les informations personnelles et professionnelles

### Gestion Financière
- Enregistrer les revenus et les dépenses
- Catégoriser les transactions
- Suivre l'historique des transactions

## Opérations Possibles

### Ajouter des Employés
- Utiliser l'interface pour ajouter de nouveaux employés avec les informations nécessaires (nom, prénom, email, téléphone, poste, salaire, date d'embauche).

### Gérer les Transactions Financières
- Enregistrer les revenus et les dépenses via l'interface.
- Catégoriser les transactions (ex. : hébergement, marketing, fournitures).
- Visualiser l'historique des transactions.

### Interroger les Informations sur les Employés
- Rechercher des employés par nom ou par poste.
- Afficher les détails d'un employé spécifique.

### Générer des Rapports
- Produire des rapports sur les performances financières de l'hôtel.
- Analyser les données des employés et des transactions pour des prises de décision éclairées.

## Structure du projet

- `src/main/java/com/hotel/`
  - `model/` : Classes modèles (Employee, FinancialTransaction)
  - `dao/` : Classes d'accès aux données
  - `controller/` : Contrôleurs JavaFX
  - `util/` : Classes utilitaires
- `src/main/resources/`
  - `fxml/` : Fichiers de définition des interfaces
## Bonnes pratiques implémentées

- Architecture MVC
- Utilisation de DAO pour l'accès aux données
- Gestion des exceptions
- Code documenté
- Interface utilisateur intuitive
- Validation des données
- Utilisation de PreparedStatement pour prévenir les injections SQL
