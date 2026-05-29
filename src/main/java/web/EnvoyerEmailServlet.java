package web;

import java.io.IOException;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/envoyerEmail")
public class EnvoyerEmailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String host;
    private String port;
    private String user;
    private String pass;

    public void init() throws ServletException {
        // Initialisation des paramètres SMTP depuis web.xml
        ServletContext context = getServletContext();
        host = context.getInitParameter("host");
        port = context.getInitParameter("port");
        user = context.getInitParameter("user");
        pass = context.getInitParameter("pass");

        if (host == null || port == null || user == null || pass == null) {
            throw new ServletException("Les paramètres SMTP ne sont pas configurés correctement.");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupération des paramètres d'e-mail et de nom depuis le formulaire
        String mail = request.getParameter("mail");
        String nom = request.getParameter("nom");

        String subject = "Notification de retard de paiement";
        String content = "Cher " + nom + ",\n\nNous vous rappelons que vous avez un retard sur votre bourse. Veuillez régulariser votre situation au plus vite.\n\nCordialement,\nAdministration";

        try {
            sendEmail(host, port, user, pass, mail, subject, content);
            String message = "Email envoyé avec succès à l'étudiant : " + nom + " (" + mail + ")";
            request.setAttribute("Message", message);
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("Message", "Erreur lors de l'envoi de l'e-mail à l'étudiant : " + nom + " (" + mail + ")");
        }

        // Redirection vers la page des étudiants retardataires après l'envoi de l'e-mail
        request.getRequestDispatcher("/retardataires").forward(request, response);
    }

    private void sendEmail(String host, String port, final String userName, final String password,
                           String toAddress, String subject, String message) throws AddressException, MessagingException {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setText(message);

        Transport.send(msg);
    }
}
