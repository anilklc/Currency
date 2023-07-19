package application;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import DB.DBHelper;
import Models.MovementsTableview;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class MovementsController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label TCNo_label;

	@FXML
	private TextField TCno_text;

	@FXML
	private Label finishDate_label;

	@FXML
	private DatePicker finishDate_picker;

	@FXML
	private TableView<MovementsTableview> movements_tableview;

	@FXML
	private Button pastUpdate_button;

	@FXML
	private Button past_button;

	@FXML
	private Label startDate_label;

	@FXML
	private DatePicker startDate_picker;

	@FXML
	private TableColumn<MovementsTableview, String> surname_clm;

	@FXML
	private TableColumn<MovementsTableview, String> userAction_clm;

	@FXML
	private TableColumn<MovementsTableview, String> name_clm;

	@FXML
	private TableColumn<MovementsTableview, String> userLastEntry_clm;

	@FXML

	DBHelper db = new DBHelper();
	PreparedStatement statement;
	ResultSet resultSet;
	String query;
	ObservableList<MovementsTableview> tblList = FXCollections.observableArrayList();

	// Kullanıcının geçmişte yaptığı işlemleri tableview e aktarma
	void movementsGet() {
		try {
			tblList.clear();
			db.connectOpen();
			statement = db.connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				tblList.add(new MovementsTableview(resultSet.getString("name"), resultSet.getString("surname"),
						resultSet.getString("userAction"), resultSet.getString("userLastEntry")));
				movements_tableview.setItems(tblList);
			}
			db.connectClose();
			statement.close();
			resultSet.close();
			name_clm.setCellValueFactory(new PropertyValueFactory<>("name"));
			surname_clm.setCellValueFactory(new PropertyValueFactory<>("surname"));
			userAction_clm.setCellValueFactory(new PropertyValueFactory<>("userAction"));
			userLastEntry_clm.setCellValueFactory(new PropertyValueFactory<>("userLastEntry"));

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Eksik veya Hatalı Bilgi");
			alert.setHeaderText("Eksik veya Hatalı Bilgi");
			alert.setContentText("Lütfen girdiğiniz bilgileri kontrol ederek tekrar deneyiniz.");
			alert.showAndWait();
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

	// Buton ile tableview e aktarılan verilerin güncellenmesi için fonksiyon tetikleme
	@FXML
	void pastUpdate_button_Click(ActionEvent event) {
		query = "SELECT * FROM log_users INNER JOIN users ON log_users.userID=users.userID";
		movementsGet();
		TCno_text.setText("");
		startDate_picker.setValue(null);
		finishDate_picker.setValue(null);

	}

	// Buton ile yapılan arama tipine göre tableview e aranılan değerlere göre verilerin gelmesi
	@FXML
	void past_button_Click(ActionEvent event) {
		if (TCno_text.getText() != "") {
			if (startDate_picker.getValue() != null & finishDate_picker.getValue() != null) {

				query = "SELECT * FROM log_users INNER JOIN users ON log_users.userID=users.userID WHERE userLastEntry>='"
						+ startDate_picker.getValue().toString() + "' AND " + " userLastEntry<='"
						+ finishDate_picker.getValue().toString() + " 23:59:59' AND users.userTC='"
						+ TCno_text.getText().trim() + "'";
			} else {
				query = "SELECT * FROM log_users INNER JOIN users ON log_users.userID=users.userID WHERE users.userTC="
						+ TCno_text.getText().trim();
			}

		} else {
			if (startDate_picker.getValue() != null & finishDate_picker.getValue() != null) {
				query = "SELECT * FROM log_users INNER JOIN users ON log_users.userID=users.userID WHERE userLastEntry>='"
						+ startDate_picker.getValue().toString() + "' AND " + " userLastEntry<='"
						+ finishDate_picker.getValue().toString() + " 23:59:59'";
			} else {
				query = "SELECT * FROM log_users INNER JOIN users ON log_users.userID=users.userID";
			}
		}
		movementsGet();
	}

	@FXML
	void initialize() {
		query = "SELECT * FROM log_users INNER JOIN users ON log_users.userID=users.userID";
		movementsGet();
	}

}
