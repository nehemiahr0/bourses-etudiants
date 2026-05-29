<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Bourses des Étudiants</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
    integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">
    
    <style>
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
                <form action="${bourse != null ? 'modifier' : 'ajouter'}" method="post">
                    <h2>
                        <c:if test="${bourse != null}">
                            Modifier le montant
                        </c:if>
                        <c:if test="${bourse == null}">
                            Ajouter un montant
                        </c:if>
                    </h2>
                    <input type="hidden" value="${bourse != null ? bourse.idniv : ''}" name="oldIdniv">
                    <fieldset class="form-group">
                        <label>ID Niveau</label> 
                        <input type="text" value="${bourse != null ? bourse.idniv : ''}" class="form-control" name="idniv" required="required">
                    </fieldset>
                    <fieldset class="form-group">
                        <label>Niveau</label> 
                        <input type="text" value="${bourse != null ? bourse.niveau : ''}" class="form-control" name="niveau" required="required">
                    </fieldset>
                    <fieldset class="form-group">
                        <label>Montant</label> 
                        <input type="number" value="${bourse != null ? bourse.montant : ''}" class="form-control" name="montant" required="required">
                    </fieldset>
                    <fieldset class="form-group">
                        <label>Equipement</label> 
                        <input type="number" value="${bourse != null ? bourse.equipement : ''}" class="form-control" name="equipement" required="required">
                    </fieldset>
                    <button type="submit" class="btn btn-success">Enregistrer</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
