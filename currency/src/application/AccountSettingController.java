package application;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import DB.DBHelper;
import Models.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class AccountSettingController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label TCNo_label;

	@FXML
	private TextField TCno_text;

	@FXML
	private RadioButton admin_rbtn;

	@FXML
	private Label birthday_label;

	@FXML
	private DatePicker birthday_picker;

	@FXML
	private Label confirmation;

	@FXML
	private Label confirmation_label;

	@FXML
	private TextField confirmation_text;

	@FXML
	private Label mail_label;

	@FXML
	private TextField mail_text;

	@FXML
	private Label mission_label;

	@FXML
	private Label name_label;

	@FXML
	private TextField name_text;

	@FXML
	private Label password_label;

	@FXML
	private RadioButton password_rbtn;

	@FXML
	private PasswordField password_text;

	@FXML
	private Label phone_label;

	@FXML
	private TextField phone_text;

	@FXML
	private Button save_button;

	@FXML
	private Label surname_label;

	@FXML
	private TextField surname_text;

	@FXML
	private Button userFind_button;

	@FXML
	private Label username_label;

	@FXML
	private TextField username_text;

	@FXML
	private ToggleGroup worker_group;

	@FXML
	private RadioButton worker_rbtn;

	UserInfo user = new UserInfo();
	DBHelper db = new DBHelper();
	PreparedStatement statement;
	ResultSet resultSet;
	String query;
	String password;

	// Istenilen kullanicinin verilerini ekrana getirme
	void userGet(String value) {
		try {
			db.connectOpen();
			statement = db.connection.prepareStatement(query);
			statement.setString(1, value);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				name_text.setText(resultSet.getString("name"));
				surname_text.setText(resultSet.getString("surname"));
				TCno_text.setText(resultSet.getString("userTC"));
				username_text.setText(resultSet.getString("username"));
				phone_text.setText(resultSet.getString("userPhone"));
				mail_text.setText(resultSet.getString("userMail"));
				Date d = Date.valueOf(resultSet.getString("userBirthDate"));
				birthday_picker.setValue(d.toLocalDate());
				password = resultSet.getString("userPassword");
				if (resultSet.getString("userAuthority").equals("0")) {
					admin_rbtn.setSelected(true);
					worker_rbtn.setSelected(false);
				} else {
					worker_rbtn.setSelected(true);
					admin_rbtn.setSelected(false);
				}
			}
			db.connectClose();
			statement.close();
			resultSet.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Kullanicinin yetkisini belirleme
	int userAuthority;

	void Authority() {
		if (worker_rbtn.isSelected()) {
			userAuthority = 1;
		} else {
			userAuthority = 0;
		}
	}

	// Istenilen kullanicinin verilerini guncelleme
	void userUpdate() {
		try {
			db.connectOpen();
			query = "UPDATE users SET name=?,surname=?,userTC=?,userPassword=?,userPhone=?,userMail=?,userBirthDate=?,userAuthority=? WHERE username=?";
			statement = db.connection.prepareStatement(query);
			statement.setString(1, name_text.getText().trim());
			statement.setString(2, surname_text.getText().trim());
			statement.setString(3, TCno_text.getText().trim());
			statement.setString(4, password);
			statement.setString(5, phone_text.getText().trim());
			statement.setString(6, mail_text.getText().trim());
			statement.setString(7, birthday_picker.getValue().toString());
			statement.setInt(8, userAuthority);
			statement.setString(9, username_text.getText().trim());
			statement.executeUpdate();
			db.connectClose();
			statement.close();
			db.confrim();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Kullanıcı Güncellendi");
			alert.setHeaderText("Güncelleme");
			alert.setContentText("Kullanıcı bilgileri başarıyla güncellendi.");
			alert.showAndWait();
			confirmation.setText(Integer.toString(db.getConfrimNumber()));
			user.userAction(
					name_text.getText().trim() + " " + surname_text.getText().trim() + " nın Bilgileri Güncellendi");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	// Kullanilan textfield e yazılcak değerlerin sadece rakam veya harf olup
	// olmuycağını ve kaç karakter olacağını kontrol etme
	@FXML
	public void textControl(KeyEvent event) {
		TextField text = (TextField) event.getSource();
		int length = 11;
		String value = text.getText();
		if (text.getText().length() > length) {
			event.consume();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Hatalı Giriş");
			alert.setHeaderText("Hatalı Bilgi Girişi");
			alert.setContentText("TC ve telefon numarası en fazla 11 karakterli olmalıdır.");
			alert.showAndWait();
			text.setText("");
		}
		if (!value.matches("\\d*")) {
			text.setText(value.replaceAll("[^\\d]", ""));
		}
	}

	@FXML
	public void textControlCharacter(KeyEvent event) {
		TextField text = (TextField) event.getSource();
		String value = text.getText();
		if (!value.matches("[a-zA-Z]*")) {
			text.setText(value.replaceAll("[^a-zA-Z]", ""));
		}
	}

	// Istenilen kullanicinin verilerini ekrana getirmek için buton ile fonksiyonu
	// tetikleme
	@FXML
	void userFind_button_Click(ActionEvent event) {
		query = "SELECT * FROM users where userTC=?";
		userGet(TCno_text.getText());
	}

	// Kullanıcı şifresini değiştirmek istiyorsa şifre textfield ın aktif olup
	// olmayacağına karar verme
	@FXML
	void password_rbtn_Click(MouseEvent event) {
		if (password_rbtn.isSelected()) {
			password_text.setDisable(false);

		} else {
			password_text.setDisable(true);
		}
	}

	// Kullanıcı bilgilerini güncelleme butonu ile doğrulama doğru ise fonksiyon
	// tetikleme
	@FXML
	void save_button_Click(ActionEvent event) throws Exception {
		if (birthday_picker.getValue() != null & TCno_text.getText() != "" & mail_text.getText() != ""
				& name_text.getText() != "" & password_text.getText() != "" & phone_text.getText() != ""
				& surname_text.getText() != "" & username_text.getText() != "") {
			if (confirmation_text.getText().equals(Integer.toString(db.getConfrimNumber()))) {
				password = db.MD5Generator(password_text.getText().trim());
				Authority();
				userUpdate();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Hatalı Doğrulama");
				alert.setHeaderText("Hatalı Doğrulama Kodu");
				alert.setContentText("Lütfen doğrulama kodunuzun doğruluğundan emin olduktan sonra tekrar deneyiniz.");
				alert.showAndWait();
			}
			db.confrim();
			confirmation.setText(Integer.toString(db.getConfrimNumber()));
		} else if (birthday_picker.getValue() != null & TCno_text.getText() != "" & mail_text.getText() != ""
				& name_text.getText() != "" & phone_text.getText() != "" & surname_text.getText() != ""
				& username_text.getText() != "") {
			if (confirmation_text.getText().equals(Integer.toString(db.getConfrimNumber()))) {
				Authority();
				userUpdate();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Hatalı Doğrulama");
				alert.setHeaderText("Hatalı Doğrulama Kodu");
				alert.setContentText("Lütfen doğrulama kodunuzun doğruluğundan emin olduktan sonra tekrar deneyiniz.");
				alert.showAndWait();
			}
			db.confrim();
			confirmation.setText(Integer.toString(db.getConfrimNumber()));

		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Eksik Bilgi");
			alert.setHeaderText("Lütfen Tüm Boş Alanları Doldurunuz");
			alert.setContentText(
					"Lütfen tüm alanların dolu olduğundan ve doğrulama kodunuzun doğruluğundan emin olduktan sonra tekrar deneyiniz.");
			alert.showAndWait();
			db.confrim();
			confirmation.setText(Integer.toString(db.getConfrimNumber()));
		}
	}

	@FXML
	void initialize() {
		userFind_button.setTooltip(new Tooltip("TC No girdikten sonra butona basarak\nverileri ekrana getirebilirsiniz."));
		query = "SELECT * FROM users WHERE userID=?";
		userGet(user.getUserID());
		user.profilGet();
		if (user.getUserAuthority().equals("0")) {
			userFind_button.setVisible(true);
		} else {
			userFind_button.setVisible(false);
			name_text.setDisable(true);
			surname_text.setDisable(true);
			TCno_text.setDisable(true);
			username_text.setDisable(true);
			birthday_picker.setDisable(true);
			admin_rbtn.setDisable(true);
			worker_rbtn.setDisable(true);
		}
		db.confrim();
		confirmation.setText(Integer.toString(db.getConfrimNumber()));
	}
}
