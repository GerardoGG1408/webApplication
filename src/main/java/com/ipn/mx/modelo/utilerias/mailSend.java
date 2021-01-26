package com.ipn.mx.modelo.utilerias;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 *
 * @author gerardo
 */
public class mailSend {

    private static final String senderEmail = "webappdev.escom.gerardo@gmail.com";
    private static final String senderPassword = "Gerardo123";

    public static void sendAsHtml(String to, String title, int tipo) throws MessagingException {
        System.out.println("Sending email to " + to);

        Session session = createSession();

        //create message using session
        MimeMessage message = new MimeMessage(session);
        //prepareEmailMessage(message, to, title, html);

        if (tipo == 0) {
            //REGISTRO
            prepareEmailMessage(message, to, title,
                    "<div class=\"jumbotron text-center\">\n"
                    + "  <h1 class=\"display-3\">Registro completado con exito</h1>\n"
                    + "  <p class=\"lead\"><strong>Te agradecemos tu registro en nuestra pagina de intercambios ESCOM</p>\n"
                    + "  <p>\n"
                    + "    Tu cuenta fue registrada con el correo: " + to + "\n"
                    + "  </p>\n"
                    + "  <hr>\n"
                    + "  <br />\n"
                    + "  <p class=\"lead\">\n"
                    + "    <a class=\"btn btn-primary btn-sm\" href=\"\" role=\"button\">Ir a la pagina</a>\n"
                    + "  </p>\n"
                    + "</div>");
        } else if (tipo == 1) {
            //ELIMINAR CUENTA
        }

        //sending message
        Transport.send(message);
        System.out.println("Done");
    }

    private static void prepareEmailMessage(MimeMessage message, String to, String title, String html)
            throws MessagingException {
        message.setContent(html, "text/html; charset=utf-8");
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(title);
    }

    private static Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
        return session;
    }
}
