package application;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import DB.DBHelper;
import DB.XMLHelper;
import Models.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class MoneyAddController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label aMoneyAdd_label;

	@FXML
	private Label aMoneyAdd_label2;

	@FXML
	private TextField aMoneyAdd_text;

	@FXML
	private ComboBox<String> aMoneyUnit_cBox;

	@FXML
	private Label aMoney_label;

	@FXML
	private TextField aMoney_text;

	@FXML
	private Label confirmation;

	@FXML
	private Label confirmation2;

	@FXML
	private Label confirmation2_label;

	@FXML
	private TextField confirmation2_text;

	@FXML
	private Label confirmation_label;

	@FXML
	private TextField confirmation_text;

	@FXML
	private Label dConfirmation;

	@FXML
	private Label dConfirmation2_label;

	@FXML
	private TextField dConfirmation2_text;

	@FXML
	private Label dConfirmation_label;

	@FXML
	private TextField dConfirmation_text;
	@FXML
	private Label dConfirmation2;

	@FXML
	private ComboBox<String> dMoneyUnit2_cBox;

	@FXML
	private ComboBox<String> dMoneyUnit_cBox;

	@FXML
	private Label dMoney_label;

	@FXML
	private TextField dMoney_text;

	@FXML
	private Button moneyAdd_button;

	@FXML
	private Label moneyAdd_label;

	@FXML
	private TextField moneyAdd_text;

	@FXML
	private Label moneyCode_label;

	@FXML
	private ComboBox<String> moneyCode_cBox;

	@FXML
	private Button moneyDel_button;

	@FXML
	private TextField moneyDel_text;

	@FXML
	private Label moneyUnit2_label;

	@FXML
	private Label moneyUnit3_label;

	@FXML
	private Button moneyUnitAdd_button;

	@FXML
	private Button moneyUnitDel_button;

	@FXML
	private ComboBox<String> moneyUnit_cBox;

	@FXML
	private Label moneyUnit_label;

	@FXML
	private Label moneyUnit_label11;

	XMLHelper xml = new XMLHelper();
	UserInfo user = new UserInfo();
	DBHelper db = new DBHelper();
	PreparedStatement statement;
	ResultSet resultSet;
	String query;

	// Combobox a istenilen verileri ekleme xml den
	void comboBoxLoadXML(ComboBox<String> cmb, ComboBox<String> cmb2) {
		xml.XMLGet();

		try {
			cmb.getItems().addAll(xml.currencyStringList);
			cmb2.getItems().addAll(xml.currencyCodeList);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
    
	// Combobox a istenilen verileri ekleme
	void comboBoxLoad(ComboBox<String> cmb) {
		try {
			db.connectOpen();
			query = "SELECT * FROM till ";
			statement = db.connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				cmb.getItems().addAll(resultSet.getString("currency"));
			}
			db.connectClose();
			statement.close();
			resultSet.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Combobox temizleyip yükleme yapma
	void comboBoxClearAndUpload() {
		aMoneyUnit_cBox.getItems().clear();
		dMoneyUnit_cBox.getItems().clear();
		dMoneyUnit2_cBox.getItems().clear();
		moneyUnit_cBox.getItems().clear();
		moneyCode_cBox.getItems().clear();
		aMoney_text.setText("");
		aMoneyAdd_text.setText("");
		moneyAdd_text.setText("");
		moneyDel_text.setText("");
		dMoney_text.setText("");
		confirmation_text.setText("");
		confirmation2_text.setText("");
		dConfirmation2_text.setText("");
		dConfirmation_text.setText("");
		comboBoxLoad(aMoneyUnit_cBox);
		comboBoxLoad(dMoneyUnit_cBox);
		comboBoxLoad(dMoneyUnit2_cBox);
		comboBoxLoadXML(moneyUnit_cBox, moneyCode_cBox);
	}

	// Para birimi dana önceden eklenmiş kontrol etme eklenmemiş ise ekleme
	void tillControl() {
		try {
			db.connectOpen();
			query = "SELECT * FROM till WHERE currency=?";
			statement = db.connection.prepareStatement(query);
			statement.setString(1, moneyUnit_cBox.getSelectionModel().getSelectedItem());
			resultSet = statement.executeQuery();
			if (!resultSet.next()) {
				moneyUnitAdd();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Hatalı İşlem");
				alert.setHeaderText("Hatalı İşlem");
				alert.setContentText("Bu para birimi daha önce eklenmiş lütfen kontrol edip işlemlerinize devam ediniz.");
				alert.showAndWait();
			}
			db.connectClose();
			statement.close();
			resultSet.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	String currencyID;

	// Seçilen para biriminin kasada ne kadar olduğunu gösterme
	void moneyControl(ComboBox<String> cmb, TextField txt) {
		try {
			db.connectOpen();
			query = "SELECT * FROM till WHERE currency=?";
			statement = db.connection.prepareStatement(query);
			statement.setString(1, cmb.getSelectionModel().getSelectedItem());
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				txt.setText(resultSet.getString("currentMoney"));
				currencyID = resultSet.getString("currencyID");
			}

			db.connectClose();
			statement.close();
			resultSet.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Para birimi ekleme
	void moneyUnitAdd() {
		try {
			db.connectOpen();
			query = "INSERT INTO till(currency,currencyCode,currentMoney,currencyUploadDate) VALUES (?,?,?,(SELECT CURRENT_TIMESTAMP()))";
			statement = db.connection.prepareStatement(query);
			statement.setString(1, moneyUnit_cBox.getSelectionModel().getSelectedItem());
			statement.setString(2, moneyCode_cBox.getSelectionModel().getSelectedItem());
			statement.setFloat(3, Float.parseFloat(moneyAdd_text.getText()));
			statement.executeUpdate();
			db.connectClose();
			statement.close();
			user.moneyControl(moneyUnit_cBox.getSelectionModel().getSelectedItem());
			user.userActionMoney("Para Birimi Eklendi", 0, Float.parseFloat(moneyAdd_text.getText()),
					Float.parseFloat(moneyAdd_text.getText()));
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Para Birimi Eklendi");
			alert.setHeaderText("Para Birimi Ekleme");
			alert.setContentText("Para birimi ekleme işlemi başarılı.");
			alert.showAndWait();
			comboBoxClearAndUpload();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Para birimi silme
	void moneyUnitDelete() {
		try {
			db.connectOpen();
			query = "DELETE FROM till WHERE currency=?";
			statement = db.connection.prepareStatement(query);
			statement.setString(1, dMoneyUnit2_cBox.getSelectionModel().getSelectedItem());
			statement.executeUpdate();
			db.connectClose();
			statement.close();
			user.userActionMoney(dMoneyUnit2_cBox.getSelectionModel().getSelectedItem() + " Para Birimi Silindi", 0, 0,
					0);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Para Birimi Silindi");
			alert.setHeaderText("Para Birimi Silindi");
			alert.setContentText("Para birimi silme işlemi başarılı.");
			alert.showAndWait();
			comboBoxClearAndUpload();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Para ekleme
	void moneyAdd(float money) {
		try {
			db.connectOpen();
			query = "UPDATE till SET currentMoney=? WHERE currency=?";
			statement = db.connection.prepareStatement(query);
			statement.setFloat(1, money);
			statement.setString(2, aMoneyUnit_cBox.getSelectionModel().getSelectedItem());
			statement.executeUpdate();
			db.connectClose();
			statement.close();
			user.moneyControl(aMoneyUnit_cBox.getSelectionModel().getSelectedItem());
			user.userActionMoney("Para Eklendi", Float.parseFloat(aMoney_text.getText()), money,
					Float.parseFloat(aMoneyAdd_text.getText()));
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Para Eklendi");
			alert.setHeaderText("Para Ekleme");
			alert.setContentText("Para ekleme işlemi başarılı.");
			alert.showAndWait();
			comboBoxClearAndUpload();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Para silme
	void moneyDelete(float money) {
		try {
			db.connectOpen();
			query = "UPDATE till SET currentMoney=? WHERE currency=?";
			statement = db.connection.prepareStatement(query);
			statement.setFloat(1, money);
			statement.setString(2, dMoneyUnit_cBox.getSelectionModel().getSelectedItem());
			statement.executeUpdate();
			db.connectClose();
			statement.close();
			user.moneyControl(dMoneyUnit_cBox.getSelectionModel().getSelectedItem());
			user.userActionMoney("Para Silindi", Float.parseFloat(dMoney_text.getText()), money,
					Float.parseFloat(moneyDel_text.getText()));
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Para  Silindi");
			alert.setHeaderText("Para  Silme");
			alert.setContentText("Para silme işlemi başarılı.");
			alert.showAndWait();
			comboBoxClearAndUpload();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	String cMoneyAdd, cMoneyDelete, cUnitAdd, cUnitDelete;

	//Doğrulama kodlarını işlemlerden sonra değiştirme
	void confrim() {
		db.confrim();
		confirmation.setText(Integer.toString(db.getConfrimNumber()));
		cMoneyAdd = Integer.toString(db.getConfrimNumber());

		db.confrim();
		dConfirmation.setText(Integer.toString(db.getConfrimNumber()));
		cMoneyDelete = Integer.toString(db.getConfrimNumber());

		db.confrim();
		confirmation2.setText(Integer.toString(db.getConfrimNumber()));
		cUnitAdd = Integer.toString(db.getConfrimNumber());

		db.confrim();
		dConfirmation2.setText(Integer.toString(db.getConfrimNumber()));
		cUnitDelete = Integer.toString(db.getConfrimNumber());
	}
	
	// Kullanilan textfield e yazılcak değerlerin sadece rakam veya harf olup olmuycağını ve kaç karakter olacağını kontrol etme
	@FXML
	public void textControl(KeyEvent event) {
	    TextField text = (TextField) event.getSource();
	    String value = text.getText();
		if (!value.matches("\\d*")) {
	        text.setText(value.replaceAll("[^\\d]", ""));
	    }
	}

	// Eklenilcek para biriminin döviz kodunun ve döviz adının hangi comboboxda seçilirse otomatik olarak seçilmesi
	@FXML
	void moneyUnit_cBox_Click(ActionEvent event) {
		if (moneyUnit_cBox.getSelectionModel().getSelectedIndex() != moneyCode_cBox.getSelectionModel()
				.getSelectedIndex()) {
			moneyCode_cBox.getSelectionModel().select(moneyUnit_cBox.getSelectionModel().getSelectedIndex());
		}
	}

	@FXML
	void moneyCode_cBox_Click(ActionEvent event) {
		if (moneyCode_cBox.getSelectionModel().getSelectedIndex() != moneyUnit_cBox.getSelectionModel()
				.getSelectedIndex()) {
			moneyUnit_cBox.getSelectionModel().select(moneyCode_cBox.getSelectionModel().getSelectedIndex());
		}
	}

	// Comboboxda seçilen para birimine göre kasadaki miktarının ekrana gelmesi
	@FXML
	void aMoneyUnit_cBox_Click(ActionEvent event) {
		if (aMoneyUnit_cBox.getSelectionModel().getSelectedItem() != null) {
			moneyControl(aMoneyUnit_cBox, aMoney_text);
		}
	}

	@FXML
	void dMoneyUnit_cBox_Click(ActionEvent event) {
		if (dMoneyUnit_cBox.getSelectionModel().getSelectedItem() != null) {
			moneyControl(dMoneyUnit_cBox, dMoney_text);
		}
	}

	Float money;

	// Buton ile para ekleme fonksiyonunun tetiklenmesi
	@FXML
	void moneyAdd_button_Click(ActionEvent event) {
		if (confirmation_text.getText().equals(cMoneyAdd) & aMoney_text.getText() != "" & aMoneyAdd_text.getText() != ""
				& aMoneyUnit_cBox.getSelectionModel().getSelectedItem() != null) {
			money = Float.parseFloat(aMoneyAdd_text.getText()) + Float.parseFloat(aMoney_text.getText());
			moneyAdd(money);
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Eksik Alan");
			alert.setHeaderText("Eksik alan");
			alert.setContentText("Lütfen eksik alanları kontrol edip tekrar deneyiniz.");
			alert.showAndWait();
		}
		confrim();
	}

	// Buton ile para silme fonksiyonunun tetiklenmesi
	@FXML
	void moneyDel_button_Click(ActionEvent event) {
		if (dConfirmation_text.getText().equals(cMoneyDelete) & dMoney_text.getText() != ""
				& moneyDel_text.getText() != "" & dMoneyUnit_cBox.getSelectionModel().getSelectedItem() != null) {
			if (Float.parseFloat(dMoney_text.getText()) >= Float.parseFloat(moneyDel_text.getText())) {
				money = Float.parseFloat(dMoney_text.getText()) - Float.parseFloat(moneyDel_text.getText());
				moneyDelete(money);
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Para Çıkarma");
				alert.setHeaderText("Para Çıkarma Hatası");
				alert.setContentText("Kasada olan miktardan daha fazla para çıkarılamaz lütfen kontrol ediniz.");
				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Eksik Alan");
			alert.setHeaderText("Eksik alan");
			alert.setContentText("Lütfen eksik alanları kontrol edip tekrar deneyiniz.");
			alert.showAndWait();
		}
		confrim();
	}

	// Buton ile para biriminin eklenmesi
	@FXML
	void moneyUnitAdd_button_Click(ActionEvent event) {
		if (confirmation2_text.getText().equals(cUnitAdd)
				& moneyUnit_cBox.getSelectionModel().getSelectedItem() != null) {
			tillControl();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Eksik Alan");
			alert.setHeaderText("Eksik alan");
			alert.setContentText("Lütfen eksik alanları kontrol edip tekrar deneyiniz.");
			alert.showAndWait();
		}
		confrim();
	}

	// Buton ile para biriminin silinmesi
	@FXML
	void moneyUnitDel_button_Click(ActionEvent event) {
		if (dConfirmation2_text.getText().equals(cUnitDelete)
				& dMoneyUnit2_cBox.getSelectionModel().getSelectedItem() != null) {
			user.moneyControl(dMoneyUnit2_cBox.getSelectionModel().getSelectedItem());
			moneyUnitDelete();
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Eksik Alan");
			alert.setHeaderText("Eksik alan");
			alert.setContentText("Lütfen eksik alanları kontrol edip tekrar deneyiniz.");
			alert.showAndWait();
		}
		confrim();
	}

	@FXML
	void initialize() {
		user.profilGet();
		confrim();
		comboBoxClearAndUpload();
	}

}
