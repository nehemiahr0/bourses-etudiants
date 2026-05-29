package web;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EtudiantDAO;
import model.Etudiant;

/**
 * Servlet implementation class EtudiantServlet
 */
@WebServlet("/")
public class EtudiantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EtudiantDAO etudiantDAO;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EtudiantServlet() {
        this.etudiantDAO = new EtudiantDAO();
        
    }
    
    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String action = request.getServletPath();
	        try {
	            switch (action) {
	                case "/new":
	                    showNewForm(request, response);
	                    break;
	                case "/insert":
	                    insertEtudiant(request, response);
	                    break;
	                case "/delete":
	                    deleteEtudiant(request, response);
	                    break;
	                case "/edit":
	                    showEditForm(request, response);
	                    break;
	                case "/update":
	                    updateEtudiant(request, response);
	                    break;
	                case "/mineurs":
	                    listEtudiantsMineurs(request, response);
	                    break;
	                case "/list":
	                    listEtudiant(request, response);
	                    break;
	                default:
	                    listEtudiant(request, response);
	                    break;
	            }
	        }catch (SQLException ex) {
	                throw new ServletException(ex);
	            }
	        }
	private void listEtudiantsMineurs(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, ServletException, IOException {
		 List<Etudiant> etudiantsMineurs = etudiantDAO.getEtudiantsMineurs();
		// l'attribut pour JSP
		request.setAttribute("etudiantsMineurs", etudiantsMineurs);
		// Transmission JSP 
		request.getRequestDispatcher("etudiant-mineurs.jsp").forward(request, response);
	    }

	private void listEtudiant(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String searchTerm = request.getParameter("search");
        String niveau = request.getParameter("niveau");
        String institution = request.getParameter("institution");

        // Log the selected niveau and institution for debugging
        System.out.println("Niveau sélectionné : " + niveau);
        System.out.println("Institution sélectionnée : " + institution);

        List<Etudiant> listEtudiants;

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            // Utilisation de la méthode searchEtudiants si un terme de recherche est fourni
            listEtudiants = etudiantDAO.searchEtudiants(searchTerm);
        } else if ((niveau != null && !niveau.isEmpty()) || (institution != null && !institution.isEmpty())) {
            // Utilisation de la méthode filterEtudiants si niveau ou institution est fourni
            listEtudiants = etudiantDAO.filterEtudiants(niveau, institution);
        } else {
            // Sinon, sélectionner tous les étudiants
            listEtudiants = etudiantDAO.selectAllEtudiants();
        }

        // Récupération de tous les niveaux et établissements pour les options de sélection
        List<String> niveaux = etudiantDAO.getAllNiveaux();
        List<String> institutions = etudiantDAO.getAllInstitutions();

        // Définition des attributs pour le rendu JSP
        request.setAttribute("listEtudiant", listEtudiants);
        request.setAttribute("niveaux", niveaux);
        request.setAttribute("institutions", institutions);

        // Transmission à la JSP pour le rendu
        RequestDispatcher dispatcher = request.getRequestDispatcher("etudiant-list.jsp");
        dispatcher.forward(request, response);
    }



	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		        RequestDispatcher dispatcher = request.getRequestDispatcher("etudiant-form.jsp");
		        dispatcher.forward(request, response);
		    }
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, ServletException, IOException {
	    String matricule = request.getParameter("matricule");
	    Etudiant existingEtudiant = etudiantDAO.selectEtudiant(matricule);
	    RequestDispatcher dispatcher = request.getRequestDispatcher("etudiant-form.jsp");
	    request.setAttribute("etudiant", existingEtudiant);
	    dispatcher.forward(request, response);
	}


	
    private void insertEtudiant(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String matricule = request.getParameter("matricule");
        String nom = request.getParameter("nom");
        String sexe = request.getParameter("sexe");
        String dateString = request.getParameter("datenais");
        String institution = request.getParameter("institution");
        String niveau = request.getParameter("niveau");
        String mail = request.getParameter("mail");
        String annee_univ = request.getParameter("annee_univ");

        Date datenais = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = formatter.parse(dateString);
            datenais = new Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format");
            return;
        }

        Etudiant newEtudiant = new Etudiant(matricule, nom, sexe, datenais, institution, niveau, mail, annee_univ);
        etudiantDAO.insertEtudiant(newEtudiant);
        response.sendRedirect("list");
    }
    private void updateEtudiant(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
    	
    	String oldMatricule = request.getParameter("oldMatricule");
        String nouveauMatricule = request.getParameter("matricule"); // Nouveau matricule
        String nom = request.getParameter("nom");
        String sexe = request.getParameter("sexe");
        String dateString = request.getParameter("datenais");
        String institution = request.getParameter("institution");
        String niveau = request.getParameter("niveau");
        String mail = request.getParameter("mail");
        String annee_univ = request.getParameter("annee_univ");

        Date datenais = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = formatter.parse(dateString);
            datenais = new Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format");
            return;
        }

        Etudiant etudiant = new Etudiant(nouveauMatricule, nom, sexe, datenais, institution, niveau, mail, annee_univ);
        etudiantDAO.updateEtudiant(etudiant, oldMatricule); // Utiliser le nouveau matricule pour identifier l'étudiant à mettre à jour
        response.sendRedirect("list");
    }



    private void deleteEtudiant(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String matricule = request.getParameter("matricule");
        etudiantDAO.deleteEtudiant(matricule);
        response.sendRedirect("list");
    }
    }
