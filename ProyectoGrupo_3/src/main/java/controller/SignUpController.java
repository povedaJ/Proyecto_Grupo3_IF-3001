package controller;

import domain.CircularLinkedList;
import domain.Customer;
import domain.ListException;
import domain.SinglyLinkedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import ucr.proyecto.HelloApplication;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

public class SignUpController {
    @FXML
    private TextField addressTextField;

    @FXML
    private BorderPane bp;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField idTextField;

    @FXML
    private ImageView logoImagen;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneNumberTextField;
    private SinglyLinkedList customerList;
    Alert alert;
    private static String emailFrom = "vivipoveda15@gmail.com";
    private static String passwordFrom = "exgehhmbahbxeeyi";
    private String emailTo;
    private String subject;
    private String content;

    private Properties mProperties;
    private Session mSession;
    private MimeMessage mCorreo;

    public void initialize() {
        mProperties = new Properties();
        //carga la lista de clientes
         this.customerList = util.Utility.getCustomerList();
        this.alert = util.FXUtility.alert("Sign up", "Add new customer...");
        Image image = new Image(util.Utility.getRouteImagen()); // Cambia la ruta por la ubicación de tu imagen
        logoImagen.setImage(image);
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void numericOnly(KeyEvent event) {
        String input = event.getCharacter();
        if (!input.matches("\\d")) {
            event.consume();
        }
    }

    @FXML
    void retunrOnAction(ActionEvent event) {
        loadPage("initial_view.fxml");
    }

    @FXML
    void signUpOnAction(ActionEvent event) {
        try {
            if (isValid()) {
                int id = Integer.parseInt(this.idTextField.getText());
                Customer newCustomer = new Customer(
                        id,
                        this.nameTextField.textProperty().getValue(),
                        this.phoneNumberTextField.textProperty().getValue(),
                        this.emailTextField.textProperty().getValue(),
                        this.addressTextField.textProperty().getValue());
                if (customerList.isEmpty()|| !customerList.contains(newCustomer)){
                    customerList.add(newCustomer);


                    createEmail(newCustomer.getEmail(),newCustomer.getId());
                    sendEmail();
                    //settear lista de utiliti, la global
                    btnClean(); //llama al boton clean
                    util.Utility.setCustomerList(customerList);
                }else{
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("The employee already exists in the list");
                    btnClean(); //llama al boton clean
                }
            } else {//alerta de complete los campos
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Complete the form with \nthe information, please");
            }
            alert.showAndWait();

        } catch (ListException ex) {
            System.out.println(ex.getMessage());
        }
    }//end

    void btnClean() {
        this.idTextField.clear();
        this.nameTextField.clear();
        this.phoneNumberTextField.clear();
        this.emailTextField.clear();
        this.addressTextField.clear();
    }

    private boolean isValid() {

        return !idTextField.getText().isEmpty() && !nameTextField.getText().isEmpty() && !phoneNumberTextField.getText().isEmpty()
                && !emailTextField.getText().isEmpty() && !addressTextField.getText().isEmpty();
    }
    private void createEmail(String email,int user) {
        emailTo = email;
        subject = "Acceso a ferreteria ";
        String password= generateUniquePassword();
        content = "Este mensaje es enviado por ferreterias 3 hermanos\n\n"+"User: "+user+"\n Password: "+password;

        // Simple mail transfer protocol
        mProperties.put("mail.smtp.host", "smtp.gmail.com");
        mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mProperties.setProperty("mail.smtp.starttls.enable", "true");
        mProperties.setProperty("mail.smtp.port", "587");
        mProperties.setProperty("mail.smtp.user",emailFrom);
        mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        mProperties.setProperty("mail.smtp.auth", "true");

        mSession = Session.getDefaultInstance(mProperties);


        try {
            mCorreo = new MimeMessage(mSession);
            mCorreo.setFrom(new InternetAddress(emailFrom));
            mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            mCorreo.setSubject(subject);
            mCorreo.setText(content, "ISO-8859-1", "html");


        } catch (AddressException ex) {
            throw new RuntimeException(ex);
        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
    }
    public static String generateUniquePassword() {
        int length=8;
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[length];
        secureRandom.nextBytes(randomBytes);
        String password = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        return password.substring(0, length);
    }
    private void sendEmail() {
        try {
            Transport mTransport = mSession.getTransport("smtp");
            mTransport.connect(emailFrom, passwordFrom);
            mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
            mTransport.close();
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("The login information has been sent, please check your email");

        } catch (NoSuchProviderException ex) {
            throw new RuntimeException(ex);
        } catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
    }

}
