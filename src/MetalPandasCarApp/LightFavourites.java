package MetalPandasCarApp;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LightFavourites implements Initializable {
  @FXML public SplitPane favouritesBackground;
  @FXML public ToolBar toolBar;
  @FXML public Button menuButton;
  @FXML public VBox drawer;
  @FXML public HBox hBox;
  @FXML public ImageView profileImage;
  @FXML public Button profileButton;
  @FXML public Button homeButton;
  @FXML public Button logoutButton;
  @FXML public TableView<Users> favouritesTable;
  @FXML public TableColumn<?, ?> driverLastName;
  @FXML public TableColumn<?, ?> driverFirstName;
  @FXML public TableColumn<?, ?> driverRating;
  @FXML public TableColumn<?, ?> previousDestination;
  @FXML public Button schedules;

  private void setFavouritesTable(ObservableList<Users> userProfilesGlobal) {
    try {
      Connection conn = DatabaseDriver.initializeDB();

      String sql = "SELECT * FROM USER";

      Statement stmt = conn.createStatement();

      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        String firstName = rs.getString(2);
        String lastName = rs.getString(3);

        Users items = new Users(firstName, lastName);
        userProfilesGlobal.add(items);
      }

      favouritesTable.setItems(userProfilesGlobal);

      driverFirstName.setCellValueFactory(new PropertyValueFactory("firstName"));
      driverLastName.setCellValueFactory(new PropertyValueFactory("lastName"));

      conn.close();
      stmt.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  public void handleMenuAction(ActionEvent actionEvent) {

    hBox.setVisible(true);
    TranslateTransition openNav = new TranslateTransition(new Duration(350), drawer);
    openNav.setToX(0);
    TranslateTransition closeNav = new TranslateTransition(new Duration(350), drawer);
    menuButton.setOnAction(
        (ActionEvent evt) -> {
          if (drawer.getTranslateX() != 0) {
            openNav.play();
          } else {
            closeNav.setToX(-(drawer.getWidth()));
            closeNav.play();
          }
        });
  }

  public void handleLogoutAction(ActionEvent actionEvent) throws IOException {
    Parent homePageParent = FXMLLoader.load(getClass().getResource("lightLogin.fxml"));
    Scene homePageScene = new Scene(homePageParent);
    Stage homeStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    homeStage.setScene(homePageScene);
    homeStage.show();
  }

  public void handleProfileAction(ActionEvent actionEvent) throws IOException {
    Parent homePageParent = FXMLLoader.load(getClass().getResource("lightProfile.fxml"));
    Scene homePageScene = new Scene(homePageParent);
    Stage homeStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    homeStage.setScene(homePageScene);
    homeStage.show();
  }

  public void handleHomeAction(ActionEvent actionEvent) throws IOException {
    Parent homePageParent = FXMLLoader.load(getClass().getResource("lightHome.fxml"));
    Scene homePageScene = new Scene(homePageParent);
    Stage homeStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    homeStage.setScene(homePageScene);
    homeStage.show();
  }

  public void handleSchedulesAction(ActionEvent actionEvent) throws IOException {
    Parent homePageParent = FXMLLoader.load(getClass().getResource("lightAppointments.fxml"));
    Scene homePageScene = new Scene(homePageParent);
    Stage homeStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    homeStage.setScene(homePageScene);
    homeStage.show();

  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

    setFavouritesTable(UsersInfo.userProfilesGlobal);
  }
}
