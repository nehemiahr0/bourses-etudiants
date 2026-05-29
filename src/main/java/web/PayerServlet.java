package web;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dao.MontantDAO;
import dao.PayerDAO;
import model.Montant;
import model.Payer;

/**
 * Servlet implementation class PayerServlet
 */
@WebServlet("/paiement/*")
public class PayerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private PayerDAO paiementDAO;
       private MontantDAO montantDao;
  
    public PayerServlet() {
        super();
       this.paiementDAO = new PayerDAO();
       this.montantDao = new MontantDAO();
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
		
		String action = request.getPathInfo();
		 try {
	            if (action != null) {
	                switch (action) {
	                    case "/autre":
	                        showNewForm(request, response);
	                        break;
	                    case "/creer":
	                        insertPayer(request, response);
	                        break;
	                    case "/effacer":
	                        deletePayer(request, response);
	                        break;
	                    case "/editer":
	                        showEditForm(request, response);
	                        break;
	                    case "/modifier":
	                        updatePayer(request, response);
	                        break;
	                    case "/pdf":
	                        generatePDF(request, response);
	                        break;
	                    
	                    default:
	                        listPayer(request, response);
	                        break;
	                }
	            } else {
	                listPayer(request, response);
	            }
	        } catch (SQLException ex) {
	            throw new ServletException(ex);
	        }
	}
/*	 private void listRetardataires(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
	        List<Payer> listPaiements = paiementDAO.selectAllPaiements();
	        List<String> retardataires = new ArrayList<>();
	        String moisFiltre = request.getParameter("mois");

	        if (moisFiltre == null || moisFiltre.isEmpty()) {
	            moisFiltre = LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, Locale.FRENCH);
	        }

	        for (Payer payer : listPaiements) {
	            LocalDateTime datePaiement = payer.getDate();
	            int moisPayes = payer.getNbr_mois();
	            LocalDate moisCourant = datePaiement.toLocalDate();
	            List<String> moisPayesList = new ArrayList<>();

	            // Calculer les mois payés par l'utilisateur
	            for (int i = 0; i < moisPayes; i++) {
	                moisPayesList.add(moisCourant.format(DateTimeFormatter.ofPattern("MMMM", Locale.FRENCH)));
	                moisCourant = moisCourant.plusMonths(1);
	            }

	            // Vérifier si le mois filtré n'est pas payé
	            if (!moisPayesList.contains(moisFiltre)) {
	                retardataires.add(payer.getNom());
	            }
	        }

	        // Préparer la liste des mois pour le filtre
	        List<String> moisList = new ArrayList<>();
	        for (int mois = 1; mois <= 12; mois++) {
	            String nomMois = Month.of(mois).getDisplayName(TextStyle.FULL, Locale.FRENCH);
	            moisList.add(nomMois);
	        }

	        // Ajouter les attributs pour la requête
	        request.setAttribute("moisList", moisList);
	        request.setAttribute("listRetardataires", retardataires);
	        request.setAttribute("moisFiltre", moisFiltre);

	        // Transférer la requête et la réponse à la JSP
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/retardataires-list.jsp");
	        dispatcher.forward(request, response);
	    }
	

*/

	 private void generatePDF(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
	        response.setContentType("application/pdf");
	        OutputStream out = response.getOutputStream();
	        try {
	            Document doc = new Document();
	            PdfWriter.getInstance(doc, out);
	            doc.open();
	            
	            // Obtenir la date actuelle et formater
	            LocalDate currentDate = LocalDate.now();
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Aujourd''hui, le' dd MMMM yyyy", Locale.FRENCH);
	            String dateString = currentDate.format(formatter);

	            // Récupérer le paiement depuis la base de données
	            String idpaye = request.getParameter("idpaye");
	            Payer payer = paiementDAO.selectPayerWithDetails(idpaye);

	            // Ajouter la date
	            Paragraph para1 = new Paragraph();
	            Font fontDate = new Font(Font.FontFamily.HELVETICA, 16, Font.NORMAL, BaseColor.DARK_GRAY);
	            para1.add(new Phrase(dateString, fontDate));
	            para1.setAlignment(Element.ALIGN_CENTER);
	            doc.add(para1);

	            // Ajouter un espace entre les paragraphes
	            doc.add(new Paragraph("\n"));

	            // Ajouter les détails du paiement
	            if (payer != null) {
	            	Paragraph para2 = new Paragraph();
	                Font fontParaTitle = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL, BaseColor.BLACK);
	                Font fontParaContent = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL, BaseColor.DARK_GRAY);
	                para2.add(new Phrase("\n Matricule : ", fontParaTitle));
	                para2.add(new Phrase(payer.getMatricule(), fontParaContent));
	                para2.add(new Phrase("\n\n Nom : ", fontParaTitle));
	                para2.add(new Phrase(payer.getNom(), fontParaContent));
	                para2.add(new Phrase("\n\n Né(e) le ", fontParaTitle));
	                // Conversion de la date de naissance en LocalDate
	                LocalDate dateNaissance = payer.getDatenais().toLocalDate();
	                
	                // Formatter la date de naissance
	                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.FRENCH);
	                String dateNaissanceFormatted = dateNaissance.format(dateFormatter);
	                para2.add(new Phrase(dateNaissanceFormatted, fontParaContent));
	                para2.add(new Phrase("\n\n Sexe : ", fontParaTitle));
	                para2.add(new Phrase(payer.getSexe(), fontParaContent));
	                para2.add(new Phrase("\n\n Institution : ", fontParaTitle));
	                para2.add(new Phrase(payer.getInstitution(), fontParaContent));
	                para2.add(new Phrase("\n\n Niveau : ", fontParaTitle));
	                para2.add(new Phrase(payer.getNiveau(), fontParaContent));
	                para2.setAlignment(Element.ALIGN_LEFT);
	                doc.add(para2);

	                // Ajouter un espace entre les paragraphes
	                doc.add(new Paragraph("\n"));

	               // Initialisation du tableau PDF
	                PdfPTable table = new PdfPTable(2);
	                table.setWidthPercentage(50);
	                table.setWidths(new float[]{1, 2});

	                // Cellules pour les titres
	                PdfPCell cell = new PdfPCell(new Phrase("Mois"));
	                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                table.addCell(cell);

	                cell = new PdfPCell(new Phrase("Montant"));
	                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                table.addCell(cell);

	                LocalDateTime datePaiement = payer.getDate();
	                List<Montant> bourses = montantDao.getMontantsByNiveau(payer.getNiveau());
	                int moisPayes = payer.getNbr_mois();
	                LocalDate moisCourant = datePaiement.toLocalDate();
	                DateTimeFormatter moisFormatter = DateTimeFormatter.ofPattern("MMMM", Locale.FRENCH);
	                
	                table.addCell("Équipement");	                
	                int equipment = 0 ;
	                		for(Montant bourse : bourses){
	                			equipment = bourse.getEquipement();
	                		}
	                		cell = new PdfPCell(new Phrase(String.valueOf(equipment)));
	 	                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	 	                    table.addCell(cell);

	             // Initialisation de la somme totale des montants payés
	                int totalMontants = equipment;

	                for (int i = 0; i < moisPayes; i++) {
	                	// Cellule pour le mois (alignement à gauche)
	                    cell = new PdfPCell(new Phrase(moisCourant.format(moisFormatter)));
	                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	                    table.addCell(cell);

	                    int montantMois = 0;
	                   
	                    // Parcourir les bourses pour trouver le montant correspondant au mois courant
	                    for (Montant bourse : bourses) {
	                            montantMois = bourse.getMontant(); // Utiliser le montant trouvé pour ce mois
	                            
	                    }
	                    
	                    // Ajouter le montant correspondant pour ce mois au tableau (à adapter selon votre implémentation)
	                    // Cellule pour le montant (alignement à droite)
	                    cell = new PdfPCell(new Phrase(String.valueOf(montantMois)));
	                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                    table.addCell(cell);

	                    // Ajouter le montant à la somme totale
	                    totalMontants += montantMois;
	                    

	                    // Passer au mois suivant
	                    moisCourant = moisCourant.plusMonths(1);
	                }


	             // Ajouter la ligne pour le total des montants payés
	                PdfPCell totalCell = new PdfPCell(new Phrase("Total", fontParaTitle));
	                totalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                table.addCell(totalCell);

	                PdfPCell totalMontantCell = new PdfPCell(new Phrase(String.valueOf(totalMontants), fontParaTitle));
	                totalMontantCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                table.addCell(totalMontantCell);
	                
	                // Ajouter le tableau au document PDF	                
	                doc.add(table);
	                
	                Paragraph para3 = new Paragraph();
	                para3.add(new Phrase(" \n \nTotal payé: ", fontParaTitle));
	                para3.add(new Phrase(String.valueOf(totalMontants), fontParaContent));
	                para3.add(new Phrase(" Ariary", fontParaContent));
	                para3.setAlignment(Element.ALIGN_LEFT);
	                doc.add(para3);

	            } else {
	                Paragraph para2 = new Paragraph("Utilisateur non trouvé.", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED));
	                para2.setAlignment(Element.ALIGN_CENTER);
	                doc.add(para2);
	            }
	            doc.close();
	            
	        } catch (DocumentException ex) {
	            ex.printStackTrace(); // Utiliser printStackTrace pour voir l'erreur en détail
	        } finally {
	            if (out != null) {
	                out.close();
	            }
	        }
	    }
	private void listPayer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Payer> listPaiements = paiementDAO.selectAllPaiements();
        request.setAttribute("listPaiement", listPaiements);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/paiement-list.jsp");
        dispatcher.forward(request, response);
    }
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/paiement-form.jsp");
        dispatcher.forward(request, response);
    }
	 private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
	        String idpaye = request.getParameter("idpaye");
	        Payer existingPaiement = paiementDAO.selectPayer(idpaye);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/paiement-form.jsp");
	        request.setAttribute("paiement", existingPaiement);
	        dispatcher.forward(request, response);
	    }
	 private void insertPayer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
	        String idpaye = request.getParameter("idpaye");
	        String matricule = request.getParameter("matricule");
	        String annee_univ = request.getParameter("annee_univ");
	        LocalDateTime date = LocalDateTime.parse(request.getParameter("date"));
	        int nbr_mois = Integer.parseInt(request.getParameter("nbr_mois"));

	        Payer paiement = new Payer(idpaye, matricule, annee_univ, date, nbr_mois); 
	        paiementDAO.insertPayer(paiement);
	        response.sendRedirect(request.getContextPath() + "/paiement");
	    }
	 private void updatePayer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		    try {
		    	String oldIdpaye = request.getParameter("oldIdpaye");
		        String idpaye = request.getParameter("idpaye");
		        String matricule = request.getParameter("matricule");
		        String annee_univ = request.getParameter("annee_univ");

		        // Conversion de la date en LocalDateTime
		        LocalDateTime date = LocalDateTime.parse(request.getParameter("date"));
		        
		        // Conversion du nombre de mois en entier
		        int nbr_mois = Integer.parseInt(request.getParameter("nbr_mois"));

		        // Création de l'objet Payer
		        Payer paiement = new Payer(idpaye, matricule, annee_univ, date, nbr_mois); 
		        
		        // Mise à jour de l'objet dans la base de données
		        paiementDAO.updatePayer(paiement,oldIdpaye);
		        
		        // Redirection vers la liste des paiements
		        response.sendRedirect(request.getContextPath() + "/paiement");
		    } catch (NumberFormatException | SQLException e) {
		        e.printStackTrace();
		        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input data");
		    }
		}
	 private void deletePayer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
	        String idpaye = request.getParameter("idpaye");
	        paiementDAO.deletePayer(idpaye);
	        response.sendRedirect(request.getContextPath() + "/paiement");
	    }
}
