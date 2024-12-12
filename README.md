# GraphQL avec Spring Boot

Ce projet est une application d'exemple pour apprendre et explorer l'utilisation de GraphQL avec Spring Boot. Il offre une implémentation basique de GraphQL et montre comment configurer, développer et tester des API GraphQL avec Spring Boot.

## Prérequis

- [Java 17+](https://adoptopenjdk.net/)
- [Maven 3.8+](https://maven.apache.org/)
- [Postman](https://www.postman.com/) ou un client GraphQL

## Fonctionnalités

- Configuration de base de GraphQL dans Spring Boot.
- Modèle d'exemple (e.g., `Book` et `Author`).
- Opérations GraphQL : `query`, `mutation` et `subscription`.
- Tests unitaires pour les requêtes et mutations GraphQL.

## Installation

1. Clonez le projet :

   ```bash
   git clone https://github.com/votre-utilisateur/graphql-springboot-demo.git
   cd graphql-springboot-demo
   ```

2. Compilez et lancez l'application :

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. Accédez à l'interface GraphQL via :

- http://localhost:8080/graphiql pour une interface utilisateur.

## Structure du Projet

- `src/main/java` : Contient le code source principal.
   - `entity` : Les entités de données.
   - `repository` : Interfaces pour accès aux données (en mémoire ou base de données).
   - `controller` : Les implémentations des queries, mutations et subscriptions.

- `src/main/resources` :
   - `schema.graphqls` : Schéma GraphQL contenant les types, queries, mutations et subscriptions.