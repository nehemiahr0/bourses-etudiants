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
                <form action="${paiement != null ? 'modifier' : 'creer'}" method="post">
                    <h2>
                        <c:if test="${paiement  != null}">
                            Modifier le paiement
                        </c:if>
                        <c:if test="${paiement  == null}">
                            Ajouter un paiement
                        </c:if>
                    </h2>
                    <input type="hidden" value="${paiement != null ? paiement.idpaye : ''}" name="oldIdpaye">
                    <fieldset class="form-group">
                        <label>ID Paye</label> 
                        <input type="text" value="${paiement != null ? paiement.idpaye : ''}" class="form-control" name="idpaye" required="required">
                    </fieldset>
                    <fieldset class="form-group">
                        <label>Matricule</label> 
                        <input type="text" value="${paiement != null ? paiement.matricule : ''}" class="form-control" name="matricule" required="required">
                    </fieldset>
                    <fieldset class="form-group">
                        <label>Année-Universitaire</label> 
                        <input type="text" value="${paiement != null ? paiement.annee_univ : ''}" class="form-control" name="annee_univ" required="required">
                    </fieldset>
                    <fieldset class="form-group">
                         <label>Date</label> 
                           <input type="datetime-local" value="${paiement != null ? paiement.date : ''}" class="form-control" name="date" required="required">
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Nombres de mois</label> 
                        <input type="number" value="${paiement != null ? paiement.nbr_mois : ''}" class="form-control" name="nbr_mois" required="required">
                    </fieldset>
                    <button type="submit" class="btn btn-success">Enregistrer</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
