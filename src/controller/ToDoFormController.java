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
import tm.ToDoTM;

import java.io.IOException;
import java.sql.*;
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
    public ListView<ToDoTM> lstToDoList;


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
        String description = txtNewTodo.getText();
        String userID = lblID.getText();


        Connection connection = DBConnection.getInstance().getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into todos values(?,?,?)");

           preparedStatement.setObject(1,id);
           preparedStatement.setObject(2,description);
           preparedStatement.setObject(3,userID);

            int i = preparedStatement.executeUpdate();

            if(i !=0){
                new Alert(Alert.AlertType.CONFIRMATION,"ToDos Added Successfully...!").showAndWait();

                

            }else{
                new Alert(Alert.AlertType.ERROR,"Something Wrong.. Try Again!").showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


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

                intID = intID +1;

                if(intID < 10){
                    newID = "T00" + intID;
                }
                else if(intID <100){
                    newID = "T0" + intID;
                }
                else {
                    newID = "T" + intID;
                }

            }else{
                newID = "T001";
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return newID;
    }
}
