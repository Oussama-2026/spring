# 🎓 TP Spring Boot - Gestion Étudiants & Cours
**SUPNUM Mauritanie**

## 📋 Description
Application Spring Boot exposant une API REST complète pour la gestion des étudiants et leurs cours, avec persistance en base de données H2.

---

## 🛠️ Technologies utilisées

| Technologie | Version |
|---|---|
| Java | 17 |
| Spring Boot | 3.2.5 |
| Spring Data JPA | - |
| H2 Database | En mémoire |
| Lombok | - |
| Maven | - |

---

## 🚀 Lancer le projet

### Prérequis
- Java 17+
- Maven 3.8+

### Commandes
```bash
# Cloner le projet
git clone <url-du-repo>
cd tp-springboot

# Lancer l'application
./mvnw spring-boot:run

# Ou avec Maven
mvn spring-boot:run
```

L'application démarre sur **http://localhost:8080**

### Console H2 (base de données)
Accessible sur : **http://localhost:8080/h2-console**
- JDBC URL : `jdbc:h2:mem:supnumdb`
- Username : `sa`
- Password : *(vide)*

---

## 📡 Endpoints API

### 🎓 Étudiants (`/students`)

| Méthode | URL | Description |
|---|---|---|
| GET | `/students` | Liste tous les étudiants |
| GET | `/students/{id}` | Récupérer un étudiant par ID |
| POST | `/students` | Créer un étudiant |
| PUT | `/students/{id}` | Modifier un étudiant ⭐ |
| DELETE | `/students/{id}` | Supprimer un étudiant |
| GET | `/students/search?name=Ali` | Recherche par nom ⭐ |
| GET | `/students/paginated?page=0&size=5` | Liste paginée ⭐ |

### 📚 Cours (`/courses`)

| Méthode | URL | Description |
|---|---|---|
| GET | `/courses` | Liste tous les cours |
| GET | `/courses/{id}` | Récupérer un cours par ID |
| POST | `/courses` | Créer un cours |
| PUT | `/courses/{id}` | Modifier un cours |
| DELETE | `/courses/{id}` | Supprimer un cours |
| POST | `/courses/students/{sId}/courses/{cId}` | Assigner un cours à un étudiant |
| GET | `/courses/students/{sId}/courses` | Cours d'un étudiant |

---

## 📝 Exemples Postman

### Créer un étudiant
```json
POST /students
Content-Type: application/json

{
  "name": "Ali Ould Mohamed",
  "email": "ali@supnum.mr"
}
```

### Modifier un étudiant
```json
PUT /students/1
Content-Type: application/json

{
  "name": "Ali Ould Mohamed Updated",
  "email": "ali.new@supnum.mr"
}
```

### Rechercher par nom
```
GET /students/search?name=Ali
```

### Liste paginée
```
GET /students/paginated?page=0&size=5&sortBy=name
```

---

## 🏗️ Structure du projet

```
src/
├── main/
│   ├── java/com/supnum/tp/
│   │   ├── TpApplication.java          # Point d'entrée
│   │   ├── entity/
│   │   │   ├── Student.java            # Entité Étudiant
│   │   │   └── Course.java             # Entité Cours
│   │   ├── dto/
│   │   │   └── StudentDTO.java         # DTO Étudiant
│   │   ├── repository/
│   │   │   ├── StudentRepository.java
│   │   │   └── CourseRepository.java
│   │   ├── controller/
│   │   │   ├── StudentController.java
│   │   │   └── CourseController.java
│   │   └── exception/
│   │       ├── GlobalExceptionHandler.java
│   │       └── ResourceNotFoundException.java
│   └── resources/
│       ├── application.properties
│       └── data.sql                    # Données initiales
└── test/
    └── java/com/supnum/tp/
        └── TpApplicationTests.java
```

---

## ❓ Réponses aux Questions de Réflexion (Partie 8)

### 1. Différence entre `@RestController` et `@Controller`
- **`@Controller`** : retourne des **vues** (pages HTML, templates Thymeleaf). Utilisé pour les applications web classiques.
- **`@RestController`** : retourne directement des **données JSON/XML**. C'est un raccourci de `@Controller + @ResponseBody`. Utilisé pour les APIs REST.

### 2. Pourquoi utiliser un DTO ?
- **Séparer** la couche API de la couche base de données
- **Ne pas exposer** les champs sensibles de l'entité (ex: mot de passe)
- **Contrôler** exactement ce que l'API reçoit et retourne
- **Faciliter** la validation des données entrantes
- **Flexibilité** : modifier la BDD sans casser l'API

### 3. Rôle de `@Transactional`
- Garantit que **toutes les opérations** d'une méthode s'exécutent dans une même transaction
- Si une opération échoue → **rollback** automatique (tout est annulé)
- Si tout réussit → **commit** (tout est sauvegardé)
- Évite les états incohérents dans la base de données

### 4. Différence entre `findById()` et `getById()`
- **`findById(id)`** : retourne un `Optional<T>` → force à gérer le cas "non trouvé" proprement
- **`getById(id)`** (ou `getReferenceById`) : retourne directement l'entité mais lance une exception `EntityNotFoundException` si non trouvé. Utilise le lazy loading.
- ✅ **Recommandé** : `findById()` car il est plus sûr et explicite.

### 5. Pourquoi utiliser la validation (`@Valid`) ?
- Vérifie les données **avant** qu'elles atteignent la logique métier
- Évite de sauvegarder des données incorrectes en base
- Retourne des messages d'erreur clairs à l'utilisateur
- Respecte le principe **"Fail Fast"** (échouer tôt)

---

## ⭐ Améliorations réalisées
- [x] `@PutMapping` pour modifier un étudiant
- [x] DTO `StudentDTO` avec validation
- [x] Pagination avec `Page<Student>`
- [x] Recherche `GET /students/search?name=Ali`
- [x] Gestion des exceptions personnalisée avec messages clairs
- [x] Controller pour les Cours avec CRUD complet
- [x] Association Étudiant ↔ Cours
