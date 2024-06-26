# Déploiement d'une API Spring Boot sur Heroku

Ce guide décrit les étapes nécessaires pour déployer une API Spring Boot sur Heroku, y compris la création de compte, la configuration et le déploiement.

## Prérequis

- Java JDK installé
- Maven ou Gradle installé
- Compte Heroku
- Heroku CLI installé

## 1. Création d'un compte Heroku

1. Rendez-vous sur [heroku.com](https://www.heroku.com/).
2. Cliquez sur "Sign Up" et remplissez le formulaire d'inscription.
3. Confirmez votre adresse email en cliquant sur le lien de validation envoyé à votre adresse.

## 2. Installation de Heroku CLI

Téléchargez et installez Heroku CLI à partir de [ce lien](https://devcenter.heroku.com/articles/heroku-cli).

Pour vérifier l'installation, exécutez :

```sh
heroku --version
```

## 3. Connexion à Heroku CLI

Connectez-vous à votre compte Heroku via le terminal :

```sh
heroku login
```

## 4. Configuration de l'API Spring Boot

### 4.1. Ajouter un fichier `Procfile`

Créez un fichier nommé `Procfile` à la racine de votre projet avec le contenu suivant :

```bash
web: java -jar target/your-api.jar
```

Ajoutez les configurations suivantes dans votre application.properties pour permettre le déploiement sur Heroku :

```sh
server.port=${PORT:9000}
```

Ajoutez les dépendances suivantes dans votre pom.xml (si vous utilisez Maven) ou build.gradle (si vous utilisez Gradle) pour gérer le JDK 17 sur Heroku.

```xml
<properties>
    <java.version>11</java.version>
</properties>
```

```groovy
sourceCompatibility = 17
targetCompatibility = 17
```

## 5. Déploiement sur Heroku

### 5.1. Initialisation d'un dépôt Git

Initialisez un dépôt Git dans votre projet s'il n'existe pas déjà 

```sh
git init
```

Ajout de tous les fichiers et commit initial

```sh
git add .
git commit -m "Initial commit"
```

### 5.3. Création d'une application Heroku

Créez une nouvelle application Heroku :
```sh
heroku create your-app-name
```

### 5.4. Déploiement de l'application

Déployez votre application sur Heroku en poussant votre dépôt Git :
```sh
git push heroku main
```

### 5.5. Vérification du déploiement

Après le déploiement, votre API sera accessible via l'URL fournie par Heroku, qui sera affichée dans le terminal après le déploiement réussi.

## 6. Utilisation de l'API

Accéder à l'API Utilisez l'URL fournie par Heroku pour accéder à votre API. Par exemple :
```arduino
https://your-app-name.herokuapp.com
```

### Configuration CORS

Si votre API doit être accessible depuis différentes origines, assurez-vous de configurer CORS correctement dans votre application Spring Boot :
```java
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
```

Remplacez http://localhost:3000 par les origines que vous souhaitez autoriser.

## Conclusion
En suivant ce guide, vous serez en mesure de déployer et de gérer votre API Spring Boot sur Heroku, permettant ainsi à vos utilisateurs d'y accéder facilement via le web.
