package web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.MontantDAO;
import model.Montant;

@WebServlet("/montant/*")
public class MontantServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MontantDAO bourseDAO ;

    public MontantServlet() {
        this.bourseDAO = new MontantDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        try {
            if (action != null) {
                switch (action) {
                    case "/nouveau":
                        showNewForm(request, response);
                        break;
                    case "/ajouter":
                        insertMontant(request, response);
                        break;
                    case "/supprimer":
                        deleteMontant(request, response);
                        break;
                    case "/editer":
                        showEditForm(request, response);
                        break;
                    case "/modifier":
                        updateMontant(request, response);
                        break;
                    default:
                        listMontant(request, response);
                        break;
                }
            } else {
                listMontant(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listMontant(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Montant> listMontants = bourseDAO.selectAllMontants();
        request.setAttribute("listMontant", listMontants);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/montant-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/montant-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String idniv = request.getParameter("idniv");
        Montant existingMontant = bourseDAO.selectMontant(idniv);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/montant-form.jsp");
        request.setAttribute("bourse", existingMontant);
        dispatcher.forward(request, response);
    }

    private void insertMontant(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String idniv = request.getParameter("idniv");
        String niveau = request.getParameter("niveau");
        int montant = Integer.parseInt(request.getParameter("montant")); 
        int equipement = Integer.parseInt(request.getParameter("equipement"));

        Montant bourse = new Montant(idniv, niveau, montant, equipement); 
        bourseDAO.insertMontant(bourse);
        response.sendRedirect(request.getContextPath() + "/montant");
    }

    private void updateMontant(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try {
            // L'ancien idniv peut être passé en tant que paramètre caché dans le formulaire
            String oldIdniv = request.getParameter("oldIdniv");
            
            // Récupération des nouveaux paramètres
            String newIdniv = request.getParameter("idniv");
            String niveau = request.getParameter("niveau");
            int montant = Integer.parseInt(request.getParameter("montant"));
            int equipement = Integer.parseInt(request.getParameter("equipement"));

            // Création de l'objet Montant avec les nouveaux paramètres
            Montant bourse = new Montant(newIdniv, niveau, montant, equipement);            
            // Mise à jour de l'objet dans la base de données
            bourseDAO.updateMontant(bourse, oldIdniv);
            
            // Redirection vers la liste des montants
            response.sendRedirect(request.getContextPath() + "/montant");
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input data");
        }
    }

    private void deleteMontant(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String idniv = request.getParameter("idniv");
        bourseDAO.deleteMontant(idniv);
        response.sendRedirect(request.getContextPath() + "/montant");
    }
}
