# Bourses des Etudiants

Application web Java EE de gestion des bourses étudiantes — CRUD complet avec notifications email.

## Stack technique

- Java EE (Servlet / JSP)
- MySQL
- Apache Tomcat 9
- JavaMail (SMTP Gmail)
- iTextPDF

## Installation

### Prérequis
- JDK 11/17
- Apache Tomcat 9
- MySQL
- Eclipse IDE (Java EE)

### 1. Cloner le projet
```bash
git clone https://github.com/nehemiahr0/bourses-etudiants.git
```

### 2. Créer la base de données
```sql
CREATE DATABASE bourses;

USE bourses;

CREATE TABLE etudiant (
    matricule VARCHAR(50) PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    sexe VARCHAR(10),
    datenais DATE,
    institution VARCHAR(100),
    niveau VARCHAR(50),
    mail VARCHAR(100),
    annee_univ VARCHAR(20)
);

CREATE TABLE montant (
    idniv VARCHAR(50) PRIMARY KEY,
    niveau VARCHAR(50) NOT NULL,
    montant INT NOT NULL,
    equipement INT NOT NULL
);

CREATE TABLE payer (
    idpaye VARCHAR(50) PRIMARY KEY,
    matricule VARCHAR(50),
    annee_univ VARCHAR(20),
    date DATETIME,
    nbr_mois INT,
    FOREIGN KEY (matricule) REFERENCES etudiant(matricule)
);
```

### 3. Configurer `web.xml`
```bash
cp src/main/webapp/WEB-INF/web.xml.example src/main/webapp/WEB-INF/web.xml
```
Remplis les valeurs dans `web.xml` :
```xml
<context-param>
    <param-name>user</param-name>
    <param-value>ton_email@gmail.com</param-value>
</context-param>
<context-param>
    <param-name>pass</param-name>
    <param-value>ton_app_password</param-value>
</context-param>
```

### 4. Variables d'environnement
| Variable | Description |
|----------|-------------|
| `GMAIL_USER` | Adresse Gmail pour l'envoi des notifications |
| `GMAIL_PASS` | App Password Gmail |

### 5. Configurer la base de données
Dans `src/main/java/dao/EtudiantDAO.java` :
```java
private String jdbcURL = "jdbc:mysql://localhost:3306/bourses?useSSL=false";
private String jdbcUsername = "root";
private String jdbcPassword = "ton_mot_de_passe";
```

### 6. Déployer sur Tomcat
- Importe le projet dans Eclipse
- Ajoute **Apache Tomcat 9** comme Server Runtime
- Clic droit sur le projet → **Run As** → **Run on Server**

##  Accès
http://localhost:8080/jsp-servlet-jdbc-mysql-crud-example/

## Fonctionnalités
- Gestion des étudiants (CRUD)
- Gestion des montants de bourses par niveau
- Suivi des paiements
- Détection des retardataires
- Envoi d'emails aux retardataires
- Export PDF
- Filtrage et recherche dynamique
