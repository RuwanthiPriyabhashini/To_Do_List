package controller;

import com.mysql.cj.PreparedQuery;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class ToDoFormController {
    public Button btnLogOut;
    public Button btnAddToList;
    public Button btnAddNewToDo;
    public Button btnUpdate;
    public Button btnDelete;
    public Label lblWelcomeNote;
    public Label lblID;
    public Pane pane;
    public TextField txtNewTodo;
    public AnchorPane root;


    public void initialize() {
      lblID.setText(LoginFormController.enteredUserID);
       lblWelcomeNote.setText("Hi " + LoginFormController.enteredUserName + " Welcome to ToDo List!");

       pane.setVisible(false);
   }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do You Want Logout?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if(buttonType.get().equals(ButtonType.YES)){
            Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml "));
            Scene scene = new Scene(parent);

            Stage primaryStage = (Stage) this.root.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login Form");
            primaryStage.centerOnScreen();
            primaryStage.show();
        }


    }

    public void btnAddToListOnAction(ActionEvent actionEvent) {
        String id = autoGenerateID();


    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
    }

    public void btnAddNewToDoOnAction(ActionEvent actionEvent) {

        pane.setVisible(true);

        txtNewTodo.requestFocus();
    }

    public String autoGenerateID(){
        String newID = "";

        Connection connection = DBConnection.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select id from todos order by id desc limit 1");
            boolean isExisit = resultSet.next();

            if(isExisit){
                String oldID = resultSet.getString(1);

                int length = oldID.length();

                String id = oldID.substring(1, length);
                int intID = Integer.parseInt(id);

                if()

            }else{
                newID = "T001";
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return newID;
    }
}
