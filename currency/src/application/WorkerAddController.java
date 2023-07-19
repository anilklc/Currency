package application;

import java.net.URL;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class WorkerAddController {

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
	private Label confirmation;

	@FXML
	private Label confirmation_label;

	@FXML
	private TextField confirmation_text;

	@FXML
	private TextField delTCNo_text;

	@FXML
	private Button delete_button;

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
	private Label username_label;

	@FXML
	private TextField username_text;

	@FXML
	private ToggleGroup worker_group;

	@FXML
	private RadioButton worker_rbtn;

	@FXML
	private DatePicker birthday_picker;

	UserInfo user = new UserInfo();
	DBHelper db = new DBHelper();
	PreparedStatement statement;
	ResultSet resultSet;
	String query;
	String password;

	// Ekrandakii textfieldların temizlenmesi
	void textClear() {
		name_text.setText("");
		surname_text.setText("");
		TCno_text.setText("");
		username_text.setText("");
		password_text.setText("");
		phone_text.setText("");
		mail_text.setText("");
		birthday_picker.setValue(null);
		delTCNo_text.setText("");
		mail_text.setText("");
		confirmation_text.setText("");
	}

	// Kullanıcı daha önceden eklenmiş mi eklenmemiş mi kontrol ve eklenmemiş ise
	// ekleme
	void userControl() {
		try {
			db.connectOpen();
			query = "SELECT * FROM users WHERE username=? OR userTC=?";
			statement = db.connection.prepareStatement(query);
			statement.setString(1, username_text.getText().trim());
			statement.setString(2, TCno_text.getText().trim());
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Kullanıcı Eklenemedi");
				alert.setHeaderText("Kullanılmış bilgi");
				alert.setContentText(
						"Daha önceden kullanılmış kullanıcı adı ve TC kimlik no lütfen kontrol edip tekrar deneyiniz.");
				alert.showAndWait();
			}

			else {
				userAdd();
			}
			db.connectClose();
			statement.close();
			resultSet.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Eklenen kullanıcının yetkisini belirleme
	int userAuthority = 0;

	void Authority() {
		if (worker_rbtn.isSelected()) {
			userAuthority = 1;
		} else {
			userAuthority = 0;
		}
	}

	// Kullanıcının eklenmesi
	void userAdd() {
		try {
			Authority();
			db.connectOpen();
			query = "INSERT INTO users(name, surname, userTC, username, userPassword, userPhone, userMail, userBirthDate, userAuthority, userCreationDate) VALUES (?,?,?,?,?,?,?,?,?,CURRENT_DATE())";
			statement = db.connection.prepareStatement(query);
			statement.setString(1, name_text.getText().trim());
			statement.setString(2, surname_text.getText().trim());
			statement.setString(3, TCno_text.getText().trim());
			statement.setString(4, username_text.getText().trim());
			statement.setString(5, db.MD5Generator(password_text.getText().trim()));
			statement.setString(6, phone_text.getText().trim());
			statement.setString(7, mail_text.getText().trim());
			statement.setString(8, birthday_picker.getValue().toString());
			statement.setInt(9, userAuthority);
			statement.executeUpdate();
			db.connectClose();
			statement.close();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Kullanıcı Eklendi");
			alert.setHeaderText("Eklendi");
			alert.setContentText("Kullanıcı başarıyla eklendi.");
			alert.showAndWait();
			user.userAction(name_text.getText().trim() + " " + surname_text.getText().trim() + " üye olarak eklendi.");
			textClear();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Kullanıcının silinmesi
	void userDelete() {
		try {
			db.connectOpen();
			query = "DELETE FROM users WHERE userTC=?;";
			statement = db.connection.prepareStatement(query);
			statement.setString(1, delTCNo_text.getText().trim());
			statement.executeUpdate();
			db.connectClose();
			statement.close();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Kullanıcı Silindi");
			alert.setHeaderText("Kullanıcı Silme");
			alert.setContentText("Aranılan kullanıcı başarıyla silindi.");
			alert.showAndWait();
			user.userAction(delTCNo_text.getText().trim() + " TC nolu kullanıcı silindi.");
			textClear();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Kullanıcı Silinemdi");
			alert.setHeaderText("Kullanıcı Silme Hatası");
			alert.setContentText("Aranılan kullanıcı bulunamadı lütfen girilen bilginin doğruluğunu kontrol edip tekrar deneyiniz.");
			alert.showAndWait();
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

	// Buton ile doğrulama kodu doğru ise kullanıcının silinmesi
	@FXML
	void delete_button_Click(ActionEvent event) {
		if (confirmation_text.getText().equals(Integer.toString(db.getConfrimNumber()))) {
			Authority();
			userDelete();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Hatalı Doğrulama");
			alert.setHeaderText("Hatalı Doğrulama Kodu");
			alert.setContentText("Lütfen doğrulama kodunuzun doğruluğundan emin olduktan sonra tekrar deneyiniz.");
			alert.showAndWait();
		}
		db.confrim();
		confirmation.setText(Integer.toString(db.getConfrimNumber()));
	}

	// Buton ile kullanıcı önceden eklenip eklenmediği bakılıp sonra eklenmemiş ise
	// ekleme fonksiyonunu tetikleme
	@FXML
	void save_button_Click(ActionEvent event) {

		userControl();
	}

	@FXML
	void initialize() {
		db.confrim();
		confirmation.setText(Integer.toString(db.getConfrimNumber()));
	}

}
