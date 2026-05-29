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
        <h3 class="text-center">Les Montants</h3>
        <hr>
        <div class="text-left">
            <a href="<%=request.getContextPath()%>/montant/nouveau" class="btn btn-primary btn-lg">
                <i class="bi bi-plus"></i> Ajouter un montant
            </a>
        </div>
        <br>
        <div class="table-responsive">
            <table class="table table-bordered table-striped">
                <thead>
                    <tr class="text-center" style="background-color: #45869;">
                        <th scope="col">ID niveau</th>
                        <th scope="col">Niveau</th>
                        <th scope="col">Montant</th>
                        <th scope="col">Équipement</th>
                        <th scope="col">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="bourse" items="${listMontant}">
                        <tr class="text-center">
                            <td><c:out value="${bourse.idniv}" /></td>
                            <td><c:out value="${bourse.niveau}" /></td>
                            <td><c:out value="${bourse.montant}" /></td>
                            <td><c:out value="${bourse.equipement}" /></td>
                            <td class="btn-container">
                                <a href="<%=request.getContextPath()%>/montant/editer?idniv=${bourse.idniv}" class="btn btn-info btn-action">
                                    <i class="bi bi-pencil"></i>
                                </a>
                                <a href="<%=request.getContextPath()%>/montant/supprimer?idniv=${bourse.idniv}" class="btn btn-danger btn-action">
                                    <i class="bi bi-trash"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa5mNEOFRPRm11J0TCM6XE6J6" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-pjaaA8dDz/6Ik5h1T+0s0kMHDQa5xlgk5aix0vBJ0l3xSxss3Uek/2azZkgxVygE" crossorigin="anonymous"></script>
</body>
</html>
