package web;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.EtudiantDAO;
import model.Etudiant;
/**
 * Servlet implementation class RetardataireServlet
 */
@WebServlet("/retardataires")
public class RetardataireServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RetardataireServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Liste des mois disponibles
        List<String> moisDisponibles = Arrays.asList("Janvier", "Février", "Mars", "Avril", "Mai", "Juin", 
                                                     "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre");
        
        // Récupérer le mois sélectionné depuis les paramètres de requête
        String moisSelectionne = request.getParameter("mois");
        
        // Si le mois sélectionné n'est pas spécifié, utiliser le premier mois par défaut
        if (moisSelectionne == null || moisSelectionne.isEmpty()) {
            moisSelectionne = moisDisponibles.get(0);
        }
        
        // Récupérer les étudiants retardataires pour le mois sélectionné
        EtudiantDAO etudiantDAO = new EtudiantDAO();
        List<Etudiant> retardataires = etudiantDAO.getRetardataires(moisSelectionne);
        
        // Mettre les résultats dans les attributs de requête
        request.setAttribute("moisDisponibles", moisDisponibles);
        request.setAttribute("moisSelectionne", moisSelectionne);
        request.setAttribute("retardataires", retardataires);
    
        // Rediriger vers la JSP pour afficher les résultats
        request.getRequestDispatcher("/retardataires-list.jsp").forward(request, response);
    }

protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
	doGet(request, response);
}
}
