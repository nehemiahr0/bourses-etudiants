<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Bourses des Étudiants</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        body {
            font-family: -apple-system, BlinkMacSystemFont, "Helvetica Neue", Arial, sans-serif;
            font-size: 18px; /* Taille de la police de base */
            line-height: 1.6; /* Espacement des lignes */
            background-color: #f8f9fa; /* Couleur de fond */
        }
        .form-group {
            margin-bottom: 10px; /* Espacement entre les groupes de formulaire */
        }
        .form-control {
            padding: 8px 12px; /* Espacement interne des champs de formulaire */
            font-size: 14px; /* Taille de la police des champs de formulaire */
        }
        .btn {
            font-size: 14px; /* Taille de la police des boutons */
        }
        .navbar-brand {
            font-size: 24px; /* Taille de la police du logo de la navbar */
            font-weight: bold; /* Police en gras pour le logo */
        }
        .navbar-nav .nav-link {
            font-size: 18px; /* Taille de la police des liens de la navbar */
        }
        .table th, .table td {
            font-size: 14px; /* Taille de la police des cellules de tableau */
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
<div class="row-2">
    <div class="container">
        <h3 class="text-center">Liste des retardataires pour un mois donné</h3>
        <hr>
        <div class="col text-center">
            <form action="${pageContext.request.contextPath}/retardataires" method="GET" class="form-inline justify-content-center">
                <label for="mois">Sélectionnez un mois :</label>
                <div class="form-group ml-2">
                    <select id="mois" name="mois" class="form-control">
                        <c:forEach items="${moisDisponibles}" var="mois">
                            <option value="${mois}" ${mois eq moisSelectionne ? 'selected' : ''}>${mois}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group ml-2">
                    <input type="submit" value="Afficher" class="btn btn-info btn-action">
                </div>
            </form>
        </div>
        <div class="table-responsive">
            <table class="table table-bordered table-striped">
                <thead>
                    <tr class="text-center" style="background-color: #fffff;">
                        <th scope="col">Matricule</th>
                        <th scope="col">Nom</th>
                        <th scope="col">Mail</th>
                        <th scope="col">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="etudiant" items="${retardataires}">
                        <tr class="text-center">
                            <td scope="col">${etudiant.matricule}</td>
                            <td scope="col">${etudiant.nom}</td>
                            <td scope="col">${etudiant.mail}</td>
                            <td scope="col">
                                  <form action="${pageContext.request.contextPath}/envoyerEmail" method="post">
                                  <input type="hidden" name="mail" value="${etudiant.mail}">
                                  <input type="hidden" name="nom" value="${etudiant.nom}">
                                  <button type="submit" class="btn btn-warning">Envoyer un email</button>
                                  </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
