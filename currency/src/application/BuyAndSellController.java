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
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;

public class BuyAndSellController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label TCNo_label;

	@FXML
	private TextField TCno_text;

	@FXML
	private ComboBox<String> aMoneyUnit_cBox;

	@FXML
	private Label bAmount_label;

	@FXML
	private ComboBox<String> bMoneyUnit_cBox;

	@FXML
	private Label buyAmount_label;

	@FXML
	private Button calculate_button;

	@FXML
	private Button clear_button;

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
	private Label moneyAmount_label;

	@FXML
	private TextField moneyAmount_text;

	@FXML
	private Label moneyUnit_label;

	@FXML
	private Label money_label;

	@FXML
	private Label name_label;

	@FXML
	private TextField name_text;

	@FXML
	private Label phone_label;

	@FXML
	private TextField phone_text;

	@FXML
	private Button process_button;

	@FXML
	private Label sAmount_label;

	@FXML
	private Label sMoneyUnit_label;

	@FXML
	private Label sMoney_label;

	@FXML
	private Label sellAmount_label;

	@FXML
	private Label surname_label;

	@FXML
	private TextField surname_text;

	XMLHelper xml = new XMLHelper();
	UserInfo user = new UserInfo();
	DBHelper db = new DBHelper();
	PreparedStatement statement;
	ResultSet resultSet;
	String query;
	float sellAmount, buyAmount, money;

	// Combobox a istenilen verileri ekleme
	void comboBoxLoad(ComboBox<String> cmb, ComboBox<String> cmb2) {
		try {
			db.connectOpen();
			query = "SELECT * FROM till";
			statement = db.connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				cmb.getItems().addAll(resultSet.getString("currencyCode"));
				cmb2.getItems().addAll(resultSet.getString("currencyCode"));
			}
			db.connectClose();
			statement.close();
			resultSet.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
   
	// Yapılan alım satım işlemi için alıncak ve verilcek tutarı hesaplama
	void calculate() {
		money = (buyAmount * Float.parseFloat(moneyAmount_text.getText())) / sellAmount;
		buyAmount_label.setText(moneyAmount_text.getText() + " " + bMoneyUnit_cBox.getSelectionModel().getSelectedItem());
		sellAmount_label.setText(Float.toString(money) + " " + aMoneyUnit_cBox.getSelectionModel().getSelectedItem());

	}

	String sellID, buyID;
	float bCurrentMoney, sCurrentMoney;

	// Alım satım işlemini gerçekleştirme
	public void process() {
		try {
			db.connectOpen();
			query = "INSERT INTO operation(userID, customerName, customerSurname, customerTC, customerPhone, customerMail, sellCode, buyCode, sellInstantPrice, buyAmount, sellAmount, operationDate) VALUES (?,?,?,?,?,?,?,?,?,?,?,(SELECT CURRENT_TIMESTAMP()))";
			statement = db.connection.prepareStatement(query);
			statement.setString(1, user.getUserID());
			statement.setString(2, name_text.getText().trim());
			statement.setString(3, surname_text.getText().trim());
			statement.setString(4, TCno_text.getText().trim());
			statement.setString(5, phone_text.getText().trim());
			statement.setString(6, mail_text.getText().trim());
			statement.setString(7, aMoneyUnit_cBox.getSelectionModel().getSelectedItem());
			statement.setString(8, bMoneyUnit_cBox.getSelectionModel().getSelectedItem());
			statement.setFloat(9, Float.parseFloat(moneyAmount_text.getText()));
			statement.setFloat(10, buyAmount);
			statement.setFloat(11, money);
			statement.executeUpdate();
			db.connectClose();
			statement.close();
			user.updateMoney(bCurrentMoney + Float.parseFloat(moneyAmount_text.getText()), buyID);
			user.updateMoney((sCurrentMoney - money), sellID);
			user.userAction(bMoneyUnit_cBox.getSelectionModel().getSelectedItem() + " alınıp "
					+ aMoneyUnit_cBox.getSelectionModel().getSelectedItem() + " satılarak. "
					+ "Alım Satım İşlemi Yapıldı");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Alım Satım");
			alert.setHeaderText("Alım Satım İşlemi");
			alert.setContentText("alım satım işlemi başarılı.");
			alert.showAndWait();
			clear();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// Kullanilan textfield e yazılcak değerlerin sadece rakam veya harf olup olmuycağını ve kaç karakter olacağını kontrol etme 
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
	public void textControl2(KeyEvent event) {
	    TextField text = (TextField) event.getSource();
	    String value = text.getText();
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

	// Kullanıcı comboboxlardan seçtiği birimleri kontrol edip aynı birimleri seçmesini engelleme 
	@FXML
	void bMoneyUnit_cBox_Click(ActionEvent event) {
		if (bMoneyUnit_cBox.getSelectionModel().getSelectedIndex() == aMoneyUnit_cBox.getSelectionModel()
				.getSelectedIndex()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Hatalı İşlem");
			alert.setHeaderText("Hatalı İşlem");
			alert.setContentText("Aynı para biriminde alım satım yapılamaz.");
			alert.showAndWait();
		} else {
			if (bMoneyUnit_cBox.getSelectionModel().getSelectedItem().equals("TRY")) {
				buyAmount = (float) 1.0000;
			} else {
				xml.XMLBuyAndSell(bMoneyUnit_cBox.getSelectionModel().getSelectedItem());
				buyAmount = Float.parseFloat(xml.getForexBuy());
			}
			user.tillControl(bMoneyUnit_cBox.getSelectionModel().getSelectedItem());
			buyID = user.getCurrencyID();
			bCurrentMoney = user.getCurrentMoney();
		}

	}

	@FXML
	void aMoneyUnit_cBox_Click(ActionEvent event) {
		if (aMoneyUnit_cBox.getSelectionModel().getSelectedIndex() == bMoneyUnit_cBox.getSelectionModel()
				.getSelectedIndex()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Hatalı İşlem");
			alert.setHeaderText("Hatalı İşlem");
			alert.setContentText("Aynı para biriminde alım satım yapılamaz.");
			alert.showAndWait();
		}

		else {
			if (aMoneyUnit_cBox.getSelectionModel().getSelectedItem().equals("TRY")) {
				money_label.setText("1.000");
				sellAmount = (float) 1.0000;

			} else {
				xml.XMLBuyAndSell(aMoneyUnit_cBox.getSelectionModel().getSelectedItem());
				money_label.setText(xml.getForexSell());
				sellAmount = Float.parseFloat(xml.getForexSell());
			}
			user.tillControl(aMoneyUnit_cBox.getSelectionModel().getSelectedItem());
			sellID = user.getCurrencyID();
			sCurrentMoney = user.getCurrentMoney();

		}
	}

	// Eğer eksik alan yok ise alıncak ve verilcek tutarın hesaplanması için buton ile fonksiyonun tetiklenmesi
	@FXML
	void calculate_button_Click(ActionEvent event) {
		if (aMoneyUnit_cBox.getSelectionModel().getSelectedItem() != null
				& bMoneyUnit_cBox.getSelectionModel().getSelectedItem() != null & moneyAmount_text.getText() != "") {
			calculate();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Eksik Alan");
			alert.setHeaderText("Eksik alan");
			alert.setContentText("Lütfen eksik alanları kontrol edip tekrar deneyiniz.");
			alert.showAndWait();
		}

	}
    
	// Ekrandakii textfieldların temizlenmesi
	@FXML
	void clear_button_Click(ActionEvent event) {
		clear();
	}

	void clear() {
		name_text.setText("");
		surname_text.setText("");
		TCno_text.setText("");
		phone_text.setText("");
		mail_text.setText("");
		confirmation_text.setText("");
		moneyAmount_text.setText("");
		money_label.setText("0.000");
		buyAmount_label.setText("0.000");
		sellAmount_label.setText("0.000");

	}

	// Yapılan işlemin tamamlanması için buton ile fonksiyonun tetiklenmesi
	@FXML
	void process_button_Click(ActionEvent event) {
		if (confirmation_text.getText().equals(Integer.toString(db.getConfrimNumber())) & name_text.getText() != ""
				& surname_text.getText() != "" & TCno_text.getText() != "" & phone_text.getText() != ""
				& mail_text.getText() != "" & moneyAmount_text.getText() != "") {
			user.tillControl(aMoneyUnit_cBox.getSelectionModel().getSelectedItem());
			if (user.getCurrentMoney() >= money) {
				process();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Yetersiz Para");
				alert.setHeaderText("Yetersiz Para Sorunu");
				alert.setContentText("Kasada yeterli miktarda para bulunmamaktadır lütfen daha sonra tekrar deneyiniz.");
				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Eksik Alan");
			alert.setHeaderText("Eksik alan");
			alert.setContentText("Lütfen eksik alanları kontrol edip tekrar deneyiniz.");
			alert.showAndWait();
		}
		db.confrim();
		confirmation.setText(Integer.toString(db.getConfrimNumber()));
	}

	@FXML
	void initialize() {
		calculate_button.setTooltip(new Tooltip("Girilen bilgilere göre tutarları hesaplama."));
		clear_button.setTooltip(new Tooltip("Ekrandaki verileri temizle."));
		user.profilGet();
		db.confrim();
		confirmation.setText(Integer.toString(db.getConfrimNumber()));
		comboBoxLoad(bMoneyUnit_cBox, aMoneyUnit_cBox);
	}

}
