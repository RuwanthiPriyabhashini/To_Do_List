package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.*;

public class CreateNewAccountFormController {
    public Button btnRegister;
    public Button btnAddNewUser;
    public PasswordField txtNewPassword;
    public TextField txtUserName;
    public TextField txtEmail;
    public Label lblNewPassword;
    public Label lblConfirmPassword;
    public PasswordField txtConfirmPassword;
    public Label lblUserId;
    public AnchorPane root;


    public void initialize(){
        txtUserName.setDisable(true);
        txtEmail.setDisable(true);
        txtNewPassword.setDisable(true);
        txtConfirmPassword.setDisable(true);
        btnRegister.setDisable(true);
        lblNewPassword.setVisible(false);
        lblConfirmPassword.setVisible(false);
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) {
        String password = txtNewPassword.getText();
        String confPassword = txtConfirmPassword.getText();

        boolean isEqauls = password.equals(confPassword);

        if(isEqauls){
            txtNewPassword.setStyle("-fx-border-color: transparent");
            txtConfirmPassword.setStyle("-fx-border-color: transparent");

            lblNewPassword.setVisible(false);
            lblConfirmPassword.setVisible(false);

            register();

        }else{
            txtNewPassword.setStyle("-fx-border-color: red");
            txtConfirmPassword.setStyle("-fx-border-color: red");

            lblNewPassword.setVisible(true);
            lblConfirmPassword.setVisible(true);
            txtNewPassword.requestFocus();
        }


    }

    public void btnAddNewUserOnAction(ActionEvent actionEvent) {
        txtUserName.setDisable(false);
        txtEmail.setDisable(false);
        txtNewPassword.setDisable(false);
        txtConfirmPassword.setDisable(false);
        btnRegister.setDisable(false);

        txtUserName.requestFocus();

        autoGenerateId();

    }

    public void autoGenerateId(){
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select id from user order by id desc limit 1");

            boolean isExist = resultSet.next();

            if(isExist){

                String oldId = resultSet.getString(1);

                int length = oldId.length();

                String id = oldId.substring(1, length);

                int intId = Integer.parseInt(id);

                intId = intId +1;

                if(intId < 10){
                    lblUserId.setText("U00" + intId);
                }
                else if(intId < 100){
                    lblUserId.setText("U0" + intId);
                }
                else{
                    lblUserId.setText("U" + intId);
                }
            }
            else{
                lblUserId.setText("U001");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void register(){

        String userID = lblUserId.getText();
        String userName = txtUserName.getText();
        String password = txtNewPassword.getText();
        String email = txtEmail.getText();

        Connection connection = DBConnection.getInstance().getConnection();



        try {

            PreparedStatement preparedStatement = connection.prepareStatement("insert into user values(?,?,?,?)");

            preparedStatement.setObject(1,userID);
            preparedStatement.setObject(2,userName);
            preparedStatement.setObject(3,password);
            preparedStatement.setObject(4,email);

            int i = preparedStatement.executeUpdate();

            if(i !=0){

                new Alert(Alert.AlertType.CONFIRMATION,"Record added Successfully...").showAndWait();

                Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
                Scene scene = new Scene(parent);

                Stage primaryStage = (Stage) this.root.getScene().getWindow();

                primaryStage.setScene(scene);
                primaryStage.setTitle("Login Form");
                primaryStage.centerOnScreen();
                primaryStage.show();
            }
            else{
                new Alert(Alert.AlertType.ERROR,"Record added Filed... try again!").showAndWait();

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
