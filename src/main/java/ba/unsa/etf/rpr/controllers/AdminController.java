package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.AdminManager;
import ba.unsa.etf.rpr.business.MovieManager;
import ba.unsa.etf.rpr.business.UserManager;
import ba.unsa.etf.rpr.domain.Administrator;
import ba.unsa.etf.rpr.domain.Movie;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.MovieException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class AdminController {

    public ListView<String> listView;
    AdminManager adminManager = new AdminManager();
    MovieManager movieManager = new MovieManager();
    UserManager userManager = new UserManager();
    public Button loginBtn;
    public Button cancelBtn;
    public TextField fieldUsername;
    public Label adminNameLabel;
    public PasswordField fieldPassword;

    public void cancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage)cancelBtn.getScene().getWindow();
        stage.close();
    }

    public void loginClick(ActionEvent actionEvent) throws IOException, MovieException {
        List<Administrator> allAdmins = FXCollections.observableList(adminManager.getAll());
        boolean valid = false;

        for(int i = 0; i < allAdmins.size(); i++) {
            System.out.println(allAdmins.get(i).getUsername() + " " + allAdmins.get(i).getPassword());
            if(allAdmins.get(i).getUsername().equals(fieldUsername.getText()) && allAdmins.get(i).getPassword().equals(fieldPassword.getText())) {
                valid = true;
                break;
            }
        }

        Stage stage1 = (Stage)loginBtn.getScene().getWindow();
        stage1.close();

        if(valid) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/admin_login.fxml"));
            Parent root = loader.load();

            AdminController adminController = loader.getController();
            adminController.adminNameLabel.setText(fieldUsername.getText());

            stage.setTitle("Admin");
            stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            stage.setResizable(false);
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error alert");
            alert.setHeaderText("Invalid information!");
            alert.setContentText("You don't have administrator access.");

            alert.showAndWait();
        }
    }

    public void moviesBtnClick(ActionEvent actionEvent) throws MovieException {
        listView.getItems().clear();
        List<Movie> allMovies = FXCollections.observableList(movieManager.getAll());
        List<String> namesOfAllMovies = new ArrayList<>();

        for(int i = 0; i < allMovies.size(); i++) {
            namesOfAllMovies.add(allMovies.get(i).getId() + ". " + allMovies.get(i).getName());
        }
        listView.getItems().addAll(namesOfAllMovies);
    }

    public void usersBtnClick(ActionEvent actionEvent) throws MovieException {
        listView.getItems().clear();
        List<User> allUsers = FXCollections.observableList(userManager.getAll());
        List<String> namesOfAllUsers = new ArrayList<>();

        for(int i = 0; i < allUsers.size(); i++) {
            namesOfAllUsers.add(allUsers.get(i).getId() + ". " + allUsers.get(i).getName() + " " + allUsers.get(i).getLastName());
        }

        listView.getItems().addAll(namesOfAllUsers);
    }

    public void deleteUser(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/delete_user.fxml"));
        Parent root = loader.load();
        stage.setTitle("Delete user");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }

    public void deleteMovie(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/delete_movie.fxml"));
        Parent root = loader.load();
        stage.setTitle("Delete movie");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
    }

    public void addMovie(ActionEvent actionEvent) {
    }

    public void deleteUserOKClick(ActionEvent actionEvent) {
    }
}
