# Trackademy

Application web qui réunit la vie étudiante au même endroit : cours, devoirs, calendrier et budget, dans un tableau de bord personnel.

![Tableau de bord de Trackademy](docs/screenshots/dashboard.png)

## Fonctionnalités

- **Tableau de bord** : prochains devoirs, nombre de cours actifs et solde du budget en un coup d'œil.
- **Cours** : nom, professeur, horaire, salle, crédits et couleur pour les repérer.
- **Devoirs** : date de remise, cours associé et statut (à faire, en cours, terminé), avec filtres par statut.
- **Calendrier** : examens, remises, cours et autres événements, par mois.
- **Budget** : revenus et dépenses par catégorie (bourse, loyer, nourriture, transport…), total entrant, total sortant et solde.
- **Comptes** : inscription et connexion par courriel et mot de passe (Spring Security, mots de passe chiffrés avec BCrypt).
- **Données isolées par utilisateur** : chaque étudiant ne voit que ses propres cours, devoirs, événements et transactions.
- **Profil** : informations de l'étudiant et galerie d'avatars.

| Cours | Devoirs | Budget |
|---|---|---|
| ![Mes cours](docs/screenshots/cours.png) | ![Devoirs par statut](docs/screenshots/devoirs.png) | ![Gestion du budget](docs/screenshots/budget.png) |

## Stack

- **Java 21**, **Spring Boot 4**
- **Spring MVC** et **Thymeleaf** pour les pages
- **Spring Security** pour l'authentification
- **Spring Data JPA** / Hibernate avec **MySQL**
- **Docker** (build multi-étapes) et configuration **Render**

## Lancer le projet en local

Prérequis : Java 21 et un serveur MySQL (ou MariaDB).

1. Créez la base de données :

   ```sql
   CREATE DATABASE trackademydb;
   ```

2. Dans `Trackademy/src/main/resources/application.properties`, renseignez l'utilisateur et le mot de passe de votre base (`spring.datasource.username`, `spring.datasource.password`).

3. Démarrez l'application :

   ```bash
   cd Trackademy
   ./mvnw spring-boot:run
   ```

   Sous Windows : `mvnw.cmd spring-boot:run`.

4. Ouvrez http://localhost:8080 et créez un compte depuis la page d'inscription.

Les tables sont créées et mises à jour automatiquement au démarrage (`spring.jpa.hibernate.ddl-auto=update`).

> Avec MariaDB, si les tables ne sont pas créées, utilisez le dialecte MariaDB :
> `spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect`.

## Docker

```bash
cd Trackademy
docker build -t trackademy .
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3306/trackademydb" \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=motdepasse \
  trackademy
```

Spring Boot lit les variables `SPRING_DATASOURCE_*`, qui remplacent les valeurs de `application.properties`.

## Déploiement sur Render

`Trackademy/render.yaml` décrit un service web Java et une base de données. Sur Render : **New + → Blueprint**, choisissez ce dépôt, puis indiquez `Trackademy` comme répertoire racine si Render le demande. Les identifiants de la base sont injectés par `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` et `SPRING_DATASOURCE_PASSWORD`.

## Structure

```
Trackademy/
  src/main/java/com/elpzang/trackademy/
    config/        sécurité (Spring Security)
    controller/    pages : tableau de bord, cours, devoirs, calendrier, budget, profil, connexion
    entite/        Etudiant, Cours, Devoir, Evenement, Transaction
    repository/    accès aux données (Spring Data JPA)
    service/       logique métier
  src/main/resources/templates/   vues Thymeleaf
  Dockerfile
  render.yaml
```

## En cours de développement

- Connexion avec Google (OAuth2)
- API REST protégée par jetons JWT
- Changement et réinitialisation du mot de passe par courriel
- Interface en français et en anglais

## Auteur

Erwin Lashwana Prammo Zang — [GitHub](https://github.com/lashwanap)
