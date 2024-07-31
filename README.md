# Documentation de l'API Spring Boot


## Introduction

Ce projet Spring Boot a pour objectif de gérer un système de location de voitures, d'évaluations des chauffeurs et des véhicules, ainsi que diverses entités associées telles que les agences, catégories, chauffeurs, clients, etc. L'architecture utilise les composants typiques de Spring Boot, notamment les modèles, DTOs, repositories, services et contrôleurs.

## Architecture du Projet

### Structure du Projet

Voici la structure du projet :
```css
src/
├── main/
│   ├── java/
│   │   └── org/
│   │       └── reseaux/
│   │           └── carLoc/
│   │               ├── controllers/
│   │               ├── dto/
│   │               ├── exceptions/
│   │               ├── models/
│   │               ├── repositories/
│   │               ├── services/
│   │               └── utils/
│   ├── resources/
│   └── webapp/
└── test/
```

### Modèles
- Agence
- Category
- Chauffeur
- Client
- ImageVehicule
- Location
- NoteChauffeur
- NoteVehicule
- Poste
- PosteImage
- PriceChauffeur
- PriceVehicule
- PrixCarburant
- Reservation
- Syndicat
- User
- Vehicule

### DTOs
- AgenceDTO
- CategoryDTO
- ChauffeurDTO
- ClientDTO
- ImageVehiculeDTO
- LocationDTO
- NoteChauffeurDTO
- NoteVehiculeDTO
- PosteDTO
- PosteImageDTO
- PriceChauffeurDTO
- PriceVehiculeDTO
- PrixCarburantDTO
- ReservationDTO
- SyndicatDTO
- UserDTO
- VehiculeDTO

### Repositories
Chaque entité possède son propre repository qui interagit avec la base de données.

### Services
Les services contiennent la logique métier et appellent les repositories pour accéder aux données et impleemente des operations qui seront possible d'executer par le model

### Contrôleurs
Les contrôleurs exposent des endpoints pour chaque entité, permettant d'effectuer des opérations de requette.


## Routes de Requêtes

### Agence

Méthode | HTTP URL | Description
------- | --------- | -----------
GET | /agences/{id}/ | Récupère la liste des points de service d'une agence
GET | /agences | Récupère toutes les agences
GET | /agences/{id} | Récupère une agence par ID
POST | /agences | Crée une nouvelle agence
PATCH | /agences/{id} | Met à jour une agence existante
DELETE | /agences/{id} | Supprime une agence par ID

### Category

Méthode | HTTP URL | Description
------- | --------- | -----------
GET | /categories | Récupère toutes les catégories
GET | /categories/{id} | Récupère une catégorie par ID
GET | /categories/{id}/vehicules | Récupère les véhicules d'une catégorie
POST | /categories | Crée une nouvelle catégorie
PATCH | /categories/{id} | Met à jour une catégorie existante
DELETE | /categories/{id} | Supprime une catégorie par ID

### Chauffeur

Méthode | HTTP URL | Description
------- | --------- | -----------
GET | /chauffeurs | Récupère tous les chauffeurs
GET | /chauffeurs/{id} | Récupère un chauffeur par ID
GET | /chauffeurs/{id}/reservations | Récupère les réservations du chauffeur pour ID
GET | /chauffeurs/{id}/locations | Récupère les locations du chauffeur pour ID
GET | /chauffeurs/{id}/notes | Récupère les évaluations du chauffeur pour ID
GET | /chauffeurs/{id}/prices | Récupère les prix de location du chauffeur pour ID
POST | /chauffeurs | Crée un nouveau chauffeur
PATCH | /chauffeurs/{id} | Met à jour un chauffeur existant
DELETE | /chauffeurs/{id} | Supprime un chauffeur par ID

### Client

Méthode | HTTP URL | Description
------- | --------- | -----------
GET | /clients | Récupère tous les clients
GET | /clients/{id} | Récupère un client par ID
GET | /clients/{id}/reservations | Récupère les réservations du client pour ID
GET | /clients/{id}/locations | Récupère les locations du client pour ID
POST | /clients | Crée un nouveau client
PATCH | /clients/{id} | Met à jour un client existant
DELETE | /clients/{id} | Supprime un client par ID


### ImageVehicule

Méthode | HTTP URL | Description
------- | --------- | -----------
GET | /imageVehicules/{id} | Récupère une image de véhicule par ID
PATCH | /imageVehicules/{id} | Met à jour une image de véhicule existante
DELETE | /imageVehicules/{id} | Supprime une image de véhicule par ID

### Location

Méthode | HTTP URL | Description
------- | --------- | -----------
GET | /locations | Récupère toutes les locations
GET | /locations/{id} | Récupère une location par ID
POST | /locations | Crée une nouvelle location
PATCH | /locations/{id} | Met à jour une location existante
DELETE | /locations/{id} | Supprime une location par ID

### NoteChauffeur

Méthode | HTTP URL | Description
------- | --------- | -----------
GET | /noteChauffeurs | Récupère toutes les notes des chauffeurs
GET | /noteChauffeurs/{id} | Récupère une note de chauffeur par ID
POST | /noteChauffeurs | Crée une nouvelle note pour un chauffeur
PATCH | /noteChauffeurs/{id} | Met à jour une note de chauffeur existante
DELETE | /noteChauffeurs/{id} | Supprime une note de chauffeur par ID

### NoteVehicule

Méthode | HTTP URL | Description
------- | --------- | -----------
GET | /noteVehicules | Récupère toutes les notes des véhicules
GET | /noteVehicules/{id} | Récupère une note de véhicule par ID
POST | /noteVehicules | Crée une nouvelle note pour un véhicule
PATCH | /noteVehicules/{id} | Met à jour une note de véhicule existante
DELETE | /noteVehicules/{id} | Supprime une note de véhicule par ID

### Poste

Méthode | HTTP URL | Description
------- | --------- | -----------
GET | /postes | Récupère tous les postes
GET | /postes/{id} | Récupère un poste par ID
GET | /postes/{id}/vehicules | Récupère des véhicules du poste pour ID
GET | /postes/{id}/images | Récupère des images du poste pour ID
POST | /postes/{id}/images | Enregistre des images du poste pour ID
POST | /postes | Crée un nouveau poste
PATCH | /postes/{id} | Met à jour un poste existant
DELETE | /postes/{id} | Supprime un poste par ID

### PosteImage

Méthode | HTTP URL | Description
------- | --------- | -----------
GET | /posteImages/{id} | Récupère une image de poste par ID
POST | /posteImages | Crée une nouvelle image de poste
PATCH | /posteImages/{id} | Met à jour une image de poste existante
DELETE | /posteImages/{id} | Supprime une image de poste par ID

### PriceChauffeur

Méthode | HTTP URL | Description
------- | --------- | -----------
GET | /priceChauffeurs | Récupère tous les prix des chauffeurs
GET | /priceChauffeurs/{id} | Récupère un prix de chauffeur par ID
POST | /priceChauffeurs | Crée un nouveau prix pour un chauffeur
PATCH | /priceChauffeurs/{id} | Met à jour un prix de chauffeur existant
DELETE | /priceChauffeurs/{id} | Supprime un prix de chauffeur par ID

### PriceVehicule

Méthode | HTTP URL | Description
------- | --------- | -----------
GET | /priceVehicules | Récupère tous les prix des véhicules
GET | /priceVehicules/{id} | Récupère un prix de véhicule par ID
POST | /priceVehicules | Crée un nouveau prix pour un véhicule
PATCH | /priceVehicules/{id} | Met à jour un prix de véhicule existant
DELETE | /priceVehicules/{id} | Supprime un prix de véhicule par ID

### PrixCarburant

Méthode | HTTP URL | Description
------- | --------- | -----------
GET | /prixCarburants | Récupère tous les prix des carburants
GET | /prixCarburants/{carburant} | Récupère tous les prix des carburants dont le nom est carburant
GET | /prixCarburants/{id} | Récupère un prix de carburant par ID
POST | /prixCarburants | Crée un nouveau prix pour un carburant
PATCH | /prixCarburants/{id} | Met à jour un prix de carburant existant
DELETE | /prixCarburants/{id} | Supprime un prix de carburant par ID

### Reservation

Méthode | HTTP URL | Description
------- | --------- | -----------
GET | /reservations | Récupère toutes les réservations
GET | /reservations/{id} | Récupère une réservation par ID
POST | /reservations | Crée une nouvelle réservation
PATCH | /reservations/{id} | Met à jour une réservation existante
DELETE | /reservations/{id} | Supprime une réservation par ID

### Syndicat

Méthode | HTTP URL | Description
------- | --------- | -----------
GET | /syndicats | Récupère tous les syndicats
GET | /syndicats/{id} | Récupère un syndicat par ID
GET | /syndicats/{id}/vehicules | Récupère les véhicules du syndicat pour ID
POST | /syndicats | Crée un nouveau syndicat
PATCH | /syndicats/{id} | Met à jour un syndicat existant
DELETE | /syndicats/{id} | Supprime un syndicat par ID

### User

Méthode | HTTP URL | Description
------- | --------- | -----------
GET | /users | Récupère tous les utilisateurs
GET | /users/{id} | Récupère un utilisateur par ID
POST | /users | Crée un nouveau utilisateur
POST | /users/login | Authentification de l'utilisateur
PATCH | /users/{id} | Met à jour un utilisateur existant
DELETE | /users/{id} | Supprime un utilisateur par ID

### Véhicule

Méthode | HTTP URL | Description
------- | --------- | -----------
GET | /vehicules | Récupère tous les véhicules
GET | /vehicules/{id} | Récupère un véhicule par ID
GET | /vehicules/{id}/images | Récupère les images du véhicule pour ID
GET | /vehicules/{id}/reservations | Récupère les réservations du véhicule pour ID
GET | /vehicules/{id}/locations | Récupère les locations du véhicule pour ID
GET | /vehicules/{id}/notes | Récupère les évaluations du véhicule pour ID
GET | /vehicules/{id}/prices | Récupère les prix de location du véhicule pour ID
POST | /vehicules | Crée un nouveau véhicule
PATCH | /vehicules/{id} | Met à jour un véhicule existant
DELETE | /vehicules/{id} | Supprime un véhicule par ID

## Processus d'Exécution du Projet

### Prérequis
- Java 11+
- Maven
- Cassandra


### Étapes d'Exécution

Clonez le projet :
```bash
git clone https://github.com/kBRAINX/carLoc.git
cd carLoc
```

### Configurer la base de données :

S'AssureR que Cassandra est en cours d'exécution.
Mettez à jour application.properties avec les détails de connexion à Cassandra.
```sh
# Configuration de Heroku pour la base de données Cassandra
spring.cassandra.contact-points=${SCYLLADB_CONTACT_POINTS}
spring.cassandra.port=${SCYLLADB_PORT}
spring.cassandra.keyspace-name=${SCYLLADB_KEYSPACE}
spring.cassandra.local-datacenter=${SCYLLADB_LOCAL_DATACENTER}
spring.cassandra.schema-action=CREATE_IF_NOT_EXISTS
```

Construire le projet :
```bash
mvn clean install
```

### Exécuter le projet :
```bash
mvn spring-boot:run
```


## Conclusion
Ce document décrit l'implémentation d'un projet Spring Boot pour gérer les locations de voitures ainsi que les évaluations des chauffeurs et des véhicules, et diverses entités associées telles que les agences, catégories, chauffeurs, clients, etc. Il détaille l'architecture du projet, les routes de requêtes et le processus d'exécution. Assurez-vous de configurer correctement la base de données Cassandra et de respecter les étapes d'exécution pour lancer le projet avec succès.
