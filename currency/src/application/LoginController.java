package application;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import DB.DBHelper;
import Models.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.AnchorPane;

public class LoginController {

	@FXML
	private AnchorPane scenePane;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private ImageView close_image;

	@FXML
	private Button login_button;

	@FXML
	private PasswordField password_text;

	@FXML
	private TextField username_text;

	UserInfo user = new UserInfo();
	DBHelper db = new DBHelper();
	PreparedStatement statement;
	ResultSet resultSet;
	String query;
	String passwordMd5;
	String userID;

	// Giriş başarılıysa kullanıcının ana sayfasının açılması
	public void stage(String stageName) throws Exception {
		Stage window = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource(stageName));
		Image icon = new Image(getClass().getResourceAsStream("/image/icon/logo.png"));
		window.getIcons().add(icon);
		window.setTitle("Currency");
		window.initStyle(StageStyle.TRANSPARENT);
		window.setScene(new Scene(root));
		window.show();
		user.setUserID(userID);
		Stage stage = (Stage) scenePane.getScene().getWindow();
		stage.close();
	}

	// Kulanıcının giriş bilgilerinin kontrol edilip yetkisine göre kendisine ait ana sayfanın açılması
	public void login() {
		if (username_text.getText() != "" & password_text.getText() != "") {

			try {
				passwordMd5 = db.MD5Generator(password_text.getText().trim());
				db.connectOpen();
				query = "SELECT * FROM users where username=? and userPassword=?";
				statement = db.connection.prepareStatement(query);
				statement.setString(1, username_text.getText().trim());
				statement.setString(2, passwordMd5);
				resultSet = statement.executeQuery();
				resultSet.next();
				userID = resultSet.getString("userID");
				if (username_text.getText().equals(resultSet.getString("username"))
						&& passwordMd5.equals(resultSet.getString("userPassword"))
						&& "0".equals(resultSet.getString("userAuthority"))) {
					stage("MainPageAdmin.fxml");
				} else if (username_text.getText().equals(resultSet.getString("username"))
						&& passwordMd5.equals(resultSet.getString("userPassword"))
						&& "1".equals(resultSet.getString("userAuthority"))) {
					stage("MainPage.fxml");
				}
				user.userAction("Giriş Yapıldı");
				db.connectClose();
				statement.close();
				resultSet.close();
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Giriş Bilgileri Hatalı");
				alert.setHeaderText("Kullanıcı Adı veya Şifre Hatalı");
				alert.setContentText(
						"Lütfen bilgilerinizi kontrol edip tekrar deneyiniz veya yöneticiniz ile iletişime geçiniz.");
				username_text.setText("");
				password_text.setText("");
				alert.showAndWait();
				System.out.println(e.toString());
			}
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Giriş Bilgileri Eksik");
			alert.setHeaderText("Giriş Bilgileri Eksik");
			alert.setContentText(
					"Lütfen bilgilerinizi kontrol edip boş alan bırakmadığınıza emin olup tekrar deneyiniz.");
			username_text.setText("");
			password_text.setText("");
			alert.showAndWait();
		}
	}

	//Uygulamanın kapatılması
	@FXML
	void close_image_Click(MouseEvent event) {
		System.exit(0);
	}

	// Buton ile fonksiyonun tetiklenmesi
	@FXML
	void login_button_Click(ActionEvent event) {
		login();
	}

	@FXML
	void initialize() {

	}

}
