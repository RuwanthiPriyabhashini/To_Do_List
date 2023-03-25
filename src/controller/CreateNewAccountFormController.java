package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CreateNewAccountFormController {
    public Button btnRegister;
    public Button btnAddNewUser;
    public PasswordField txtNewPassword;
    public Label lblUserName;
    public TextField txtUserName;
    public Label lblEmail;
    public TextField txtEmail;
    public Label lblNewPassword;
    public Label lblConfirmPassword;
    public PasswordField txtConfirmPassword;
    public AnchorPane lblUserId;

    public void initialize(){
        txtUserName.setDisable(true);
        txtEmail.setDisable(true);
        txtNewPassword.setDisable(true);
        txtConfirmPassword.setDisable(true);
        btnRegister.setDisable(true);
    }
    public void btnRegisterOnAction(ActionEvent actionEvent) {
    }

    public void btnAddNewUserOnAction(ActionEvent actionEvent) {
        txtUserName.setDisable(false);
        txtEmail.setDisable(false);
        txtNewPassword.setDisable(false);
        txtConfirmPassword.setDisable(false);
        btnRegister.setDisable(false);

        txtUserName.requestFocus();
    }
}
