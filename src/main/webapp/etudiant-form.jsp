<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
     <title>Bourses des Étudiants</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
    integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    
    <style>
    body {
       font-family: -apple-system, BlinkMacSystemFont, Arial, sans-serif;
        font-size: 16px; /* Taille de la police de base */
        line-height: 1.6; /* Espacement des lignes */
    }

    .form-group {
        margin-bottom: 10px; /* Espacement entre les groupes de formulaire */
    }

    .form-control {
        padding: 8px 12px; /* Espacement interne des champs de formulaire */
        font-size: 14px; /* Taille de la police des champs de formulaire */
    }

    .btn {
        font-size: 16px; /* Taille de la police des boutons */
    }

    .navbar-brand {
        font-size: 24px; /* Taille de la police du logo de la navbar */
        font-weight: bold; /* Police en gras pour le logo */
    }

    .navbar-nav .nav-link {
        font-size: 18px; /* Taille de la police des liens de la navbar */
    }

    .table th,
    .table td {
        font-size: 14px; /* Taille de la police des cellules de tableau */
    }
     .navbar-brand img {
            margin-right: 10px;
        }
        .card {
            margin: 0 auto; /* Centrer le formulaire */
            max-width: 500px; /* Limiter la largeur du formulaire */
        }
</style>

</head>
<body>
      <header>
        <nav class="navbar navbar-expand-md navbar-dark" style="background-color: #4682b4;">
            <div class="container">
                <a class="navbar-brand" href="#">
                    <img src="https://img.icons8.com/material-rounded/24/000000/money.png" style="margin-right: 10px;">
                    Bourses
                </a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav ml-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="<%=request.getContextPath()%>/list">Étudiants</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<%=request.getContextPath()%>/montant">Montant</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<%=request.getContextPath()%>/paiement">Paiement</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </header>
    <br>
    <div class="container">
        <div class="card">
            <div class="card-body">
                <form action="${etudiant != null ? 'update' : 'insert'}" method="post">
                    <h2>
                        <c:if test="${etudiant != null}">
                            Modifier Étudiant
                        </c:if>
                        <c:if test="${etudiant == null}">
                            Ajouter un étudiant
                        </c:if>
                    </h2>
                    <input type="hidden" value="${etudiant != null ? etudiant.matricule : ''}" name="oldMatricule">
                    <fieldset class="form-group">
                        <label>Matricule</label> 
                        <input type="text" value="${etudiant != null ? etudiant.matricule : ''}" class="form-control" name="matricule" required="required">
                    </fieldset>
                    <fieldset class="form-group">
                        <label>Nom de l'étudiant</label> 
                        <input type="text" value="${etudiant != null ? etudiant.nom : ''}" class="form-control" name="nom" required="required">
                    </fieldset>
                    <fieldset class="form-group">
                        <label>Sexe</label> 
                        <input type="text" value="${etudiant != null ? etudiant.sexe : ''}" class="form-control" name="sexe">
                    </fieldset>
                    <fieldset class="form-group">
                        <label>Date de Naissance</label> 
                        <input type="date" value="${etudiant != null ? etudiant.datenais : ''}" class="form-control" name="datenais" placeholder="yyyy-MM-dd">
                    </fieldset>
                    <fieldset class="form-group">
                        <label>Institution</label> 
                        <input type="text" value="${etudiant != null ? etudiant.institution : ''}" class="form-control" name="institution" required="required">
                    </fieldset>
                    <fieldset class="form-group">
                        <label>Niveau</label> 
                        <input type="text" value="${etudiant != null ? etudiant.niveau : ''}" class="form-control" name="niveau" required="required">
                    </fieldset>
                    <fieldset class="form-group">
                        <label>Adresse email</label> 
                        <input type="email" value="${etudiant != null ? etudiant.mail : ''}" class="form-control" name="mail" required="required">
                    </fieldset>
                    <fieldset class="form-group">
                        <label>Année Universitaire</label> 
                        <input type="text" value="${etudiant != null ? etudiant.annee_univ : ''}" class="form-control" name="annee_univ" required="required">
                    </fieldset>
                    <button type="submit" class="btn btn-success">Enregistrer</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
