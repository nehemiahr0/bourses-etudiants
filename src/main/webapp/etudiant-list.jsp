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
    <div class="row-2">
        <div class="container">       
           <h3 class="text-center">Liste des étudiants</h3>
          <div class="text-right">
          <a href="<%=request.getContextPath()%>/mineurs" class="btn btn-warning btn-lg"><i class="far fa-user"></i> Les étudiants mineurs</a>
          </div>

    <hr>
   <div class="row">
    <!-- Colonne pour le bouton "Ajouter un étudiant" -->
    <div class="col-lg-3">
        <div class="text-left">
            <a href="<%=request.getContextPath()%>/new" class="btn btn-primary btn-lg">
                <i class="bi bi-person-plus"></i> Ajouter un étudiant
            </a>
        </div>
    </div> 


    <div class="col-lg-6 text-center">
    <form method="get" action="<%=request.getContextPath()%>/list" id="filterForm" class="form-inline justify-content-center">
        <div class="form-group ml-2">
            <select name="niveau" id="niveau" class="form-control">
                <option value="">Tous les niveaux</option>
                <c:forEach var="niveau" items="${niveaux}">
                    <option value="${niveau}" ${niveau == param.niveau ? 'selected' : ''}>${niveau}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group ml-2">
            <select name="institution" id="institution" class="form-control">
                <option value="">Tous les établissements</option>
                <c:forEach var="institution" items="${institutions}">
                    <option value="${institution}" ${institution == param.institution ? 'selected' : ''}>${institution}</option>
                </c:forEach>
            </select>
        </div>
        <!-- Optionnel : Ajoutez un élément invisible pour capturer les changements de sélection -->
        <input type="hidden" name="autoSubmit" id="autoSubmit" value="true">
    </form>
</div>


    <!-- Colonne pour le formulaire de recherche -->
    <div class="col-lg-3">
        <form method="get" action="<%=request.getContextPath()%>/list" class="form-inline float-right">
            <div class="form-group ml-auto">
                <input type="text" name="search" class="form-control" placeholder="Rechercher...">
            </div>
            <button type="submit" class="btn btn-secondary ml-1"> <i class="bi bi-search"></i></button>
        </form>
    </div>
</div>

            <br>
            <div class="table-responsive">
                <table class="table table-bordered table-striped">
                    <thead>
                        <tr class="text-center" style="background-color: #fffff;">
                            <th scope="col">Matricule</th>
                            <th scope="col">Nom</th>
                            <th scope="col">Sexe</th>
                            <th scope="col">Date de Naissance</th>
                            <th scope="col">Établissement</th>
                            <th scope="col">Niveau</th>
                            <th scope="col">Email</th>
                            <th scope="col">Année_univ</th>
                            <th scope="col">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="etudiant" items="${listEtudiant}">
                            <tr class="text-center">
                                <td><c:out value="${etudiant.matricule}" /></td>
                                <td><c:out value="${etudiant.nom}" /></td>
                                <td><c:out value="${etudiant.sexe}" /></td>
                                <td><c:out value="${etudiant.datenais}" /></td>
                                <td><c:out value="${etudiant.institution}" /></td>
                                <td><c:out value="${etudiant.niveau}" /></td>
                                <td><c:out value="${etudiant.mail}" /></td>
                                <td><c:out value="${etudiant.annee_univ}" /></td>
                                <td class="btn-container">
                                    <a href="edit?matricule=${etudiant.matricule}" class="btn btn-info btn-action">
                                        <i class="bi bi-pencil"></i>
                                    </a>
                                    <a href="delete?matricule=${etudiant.matricule}" class="btn btn-danger btn-action">
                                        <i class="bi bi-trash"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                 <c:if test="${empty listEtudiant}">
                       <div class="alert alert-info" role="alert">
                        Aucun résultat trouvé pour votre recherche.
                       </div>
                </c:if>
            </div>
   </div>
</body>
</html>
<!-- 
<script>
    function confirmDelete(matricule) {
        swal({
            title: "Êtes-vous sûr ?",
            text: "Une fois supprimé, vous ne pourrez pas récupérer cet étudiant !",
            icon: "warning",
            buttons: ["Annuler", "Confirmer"],
            dangerMode: true,
        })
        .then((willDelete) => {
            if (willDelete) {
                // Si l'utilisateur clique sur "Confirmer", rediriger vers la servlet de suppression
                window.location.href = <%--"<%=request.getContextPath()%>--%>/delete?matricule=" + matricule;
            } else {
                // Si l'utilisateur clique sur "Annuler", ne rien faire
                swal("L'étudiant n'a pas été supprimé.", {
                    icon: "info",
                });
            }
        });
    }
</script>
-->
<script>
    // Attend que le DOM soit chargé
    document.addEventListener('DOMContentLoaded', function() {
        // Sélectionnez les éléments de liste déroulante
        var niveauSelect = document.getElementById('niveau');
        var institutionSelect = document.getElementById('institution');

        // Ajoutez un gestionnaire d'événement onchange à chaque liste déroulante
        niveauSelect.addEventListener('change', function() {
            this.form.submit(); // Soumet automatiquement le formulaire lors du changement de sélection
        });

        institutionSelect.addEventListener('change', function() {
            this.form.submit(); // Soumet automatiquement le formulaire lors du changement de sélection
        });
    });
</script>
