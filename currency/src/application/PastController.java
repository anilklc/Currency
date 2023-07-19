package application;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import DB.DBHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import Models.UserInfo;
import Models.operationTableview;

public class PastController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TableColumn<operationTableview, Float> buyAmount_clm;

	@FXML
	private TableColumn<operationTableview, String> buy_clm;

	@FXML
	private Button filterList_button;

	@FXML
	private ComboBox<String> filterType_cBox;

	@FXML
	private Label filterType_label;

	@FXML
	private Label finishDate_label;

	@FXML
	private DatePicker finishDate_picker;

	@FXML
	private Button listAll_button;

	@FXML
	private TableColumn<operationTableview, String> mail_clm;

	@FXML
	private ComboBox<String> nameSurname_cBox;

	@FXML
	private Label nameSurname_label;

	@FXML
	private TableColumn<operationTableview, String> name_clm;

	@FXML
	private TableColumn<operationTableview, String> operationDate_clm;

	@FXML
	private TableView<operationTableview> operation_tableview;

	@FXML
	private TableColumn<operationTableview, String> phone_clm;

	@FXML
	private TableColumn<operationTableview, Float> sellAmount_clm;

	@FXML
	private TableColumn<operationTableview, Float> sellPrice_clm;

	@FXML
	private TableColumn<operationTableview, String> sell_clm;

	@FXML
	private Label startDate_label;

	@FXML
	private DatePicker startDate_picker;

	@FXML
	private TableColumn<operationTableview, String> surname_clm;

	@FXML
	private TableColumn<operationTableview, String> TC_clm;

	@FXML
	private TableColumn<operationTableview, String> uName_clm;

	@FXML
	private TableColumn<operationTableview, String> uSurname_clm;

	@FXML
	private Label customerName_label;

	@FXML
	private TextField customerName_text;

	UserInfo user = new UserInfo();
	DBHelper db = new DBHelper();
	PreparedStatement statement;
	ResultSet resultSet;
	String query;
	String password;
	ObservableList<operationTableview> tblListOperation = FXCollections.observableArrayList();

	// Combobox a istenilen verileri ekleme
	void comboBoxLoad(ComboBox<String> cmb) {
		try {
			db.connectOpen();
			query = "SELECT * FROM users";
			statement = db.connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				cmb.getItems().addAll(resultSet.getString("name") + " " + resultSet.getString("surname"));

			}
			db.connectClose();
			statement.close();
			resultSet.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// tableview e verileri aktarma
	void operationGet() {
		try {
			tblListOperation.clear();
			db.connectOpen();
			statement = db.connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				tblListOperation.add(new operationTableview(resultSet.getString("name"), resultSet.getString("surname"),
						resultSet.getString("customerName"), resultSet.getString("customerSurname"),
						resultSet.getString("customerTC"), resultSet.getString("customerPhone"),
						resultSet.getString("customerMail"), resultSet.getString("sellCode"),
						resultSet.getString("buyCode"), resultSet.getFloat("sellInstantPrice"),
						resultSet.getFloat("buyAmount"), resultSet.getFloat("sellAmount"),
						resultSet.getString("operationDate")));
				operation_tableview.setItems(tblListOperation);
			}
			db.connectClose();
			statement.close();
			resultSet.close();
			uName_clm.setCellValueFactory(new PropertyValueFactory<>("name"));
			uSurname_clm.setCellValueFactory(new PropertyValueFactory<>("surname"));
			name_clm.setCellValueFactory(new PropertyValueFactory<>("customerName"));
			surname_clm.setCellValueFactory(new PropertyValueFactory<>("customerSurname"));
			TC_clm.setCellValueFactory(new PropertyValueFactory<>("customerTC"));
			phone_clm.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
			mail_clm.setCellValueFactory(new PropertyValueFactory<>("customerMail"));
			sell_clm.setCellValueFactory(new PropertyValueFactory<>("sellCode"));
			buy_clm.setCellValueFactory(new PropertyValueFactory<>("buyCode"));
			sellPrice_clm.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
			buyAmount_clm.setCellValueFactory(new PropertyValueFactory<>("buyAmount"));
			sellAmount_clm.setCellValueFactory(new PropertyValueFactory<>("sellAmount"));
			operationDate_clm.setCellValueFactory(new PropertyValueFactory<>("operationDate"));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Combobox da seçilen filtre tipine göre gerekli alanları aktif etme
	@FXML
	void filterType_cBox_Click(ActionEvent event) {
		if (filterType_cBox.getSelectionModel().getSelectedIndex() == 0) {
			startDate_picker.setVisible(true);
			finishDate_picker.setVisible(true);
			startDate_label.setVisible(true);
			finishDate_label.setVisible(true);
			customerName_text.setVisible(false);
			customerName_label.setVisible(false);

		}

		else if (filterType_cBox.getSelectionModel().getSelectedIndex() == 1) {
			customerName_text.setVisible(true);
			customerName_label.setVisible(true);
			startDate_picker.setVisible(false);
			finishDate_picker.setVisible(false);
			startDate_label.setVisible(false);
			finishDate_label.setVisible(false);

		} else {
			startDate_picker.setVisible(false);
			finishDate_picker.setVisible(false);
			startDate_label.setVisible(false);
			finishDate_label.setVisible(false);
			customerName_text.setVisible(false);
			customerName_label.setVisible(false);
		}
	}

	// Buton ile seçilen filtre tipine göre tableview a verilerin aktarılması
	@FXML
	void filterList_button_Click(ActionEvent event) {
		if (!startDate_picker.getValue().equals(null) & !finishDate_picker.getValue().equals(null)
				& filterType_cBox.getSelectionModel().getSelectedIndex() == 0
				& nameSurname_cBox.getSelectionModel().getSelectedItem() != null) {
			query = "SELECT * FROM operation INNER JOIN users ON operation.userID=users.userID WHERE operationDate>='"
					+ startDate_picker.getValue().toString() + " 00:00:00' AND operationDate<='"
					+ finishDate_picker.getValue().toString() + " 23:59:59' AND users.username='" + value[0]
					+ "' AND users.surname='" + value[1] + "'";
			operationGet();
		} else if (!startDate_picker.getValue().equals(null) & !finishDate_picker.getValue().equals(null)
				& filterType_cBox.getSelectionModel().getSelectedIndex() == 0
				& nameSurname_cBox.getSelectionModel().getSelectedItem() == null) {
			query = "SELECT * FROM operation INNER JOIN users ON operation.userID=users.userID WHERE operationDate>='"
					+ startDate_picker.getValue().toString() + " 00:00:00' AND operationDate<='"
					+ finishDate_picker.getValue().toString() + " 23:59:59'";
			operationGet();
		}
	}

	// Müşteri adı girilirken verilerin aynı zamanda tableview e aktarılması
	@FXML
	void customerName_text_Pressed(KeyEvent event) {
		if (!customerName_text.getText().equals(null) & filterType_cBox.getSelectionModel().getSelectedIndex() == 1
				& nameSurname_cBox.getSelectionModel().getSelectedItem() != null) {
			query = "SELECT * FROM operation INNER JOIN users ON operation.userID=users.userID WHERE operation.customerName LIKE '%"
					+ customerName_text.getText().trim() + "%' AND users.username='" + value[0]
					+ "' AND users.surname='" + value[1] + "'";
			operationGet();
		} else if (customerName_text.getText().equals(null)
				& filterType_cBox.getSelectionModel().getSelectedIndex() == 1
				& nameSurname_cBox.getSelectionModel().getSelectedItem() == null) {
			query = "SELECT * FROM operation INNER JOIN users ON operation.userID=users.userID WHERE operation.customerName LIKE '%"
					+ customerName_text.getText().trim() + "%'";
			operationGet();
		} else if (!customerName_text.getText().equals(null)
				& filterType_cBox.getSelectionModel().getSelectedIndex() == 1
				& nameSurname_cBox.getSelectionModel().getSelectedItem() == null) {
			query = "SELECT * FROM operation INNER JOIN users ON operation.userID=users.userID WHERE operation.customerName LIKE '%"
					+ customerName_text.getText().trim() + "%'";
			operationGet();
		}
	}

	// Buton ile tüm verilerin tableview e aktarılması
	@FXML
	void listAll_button_Click(ActionEvent event) {
		query = "SELECT * FROM operation INNER JOIN users ON operation.userID=users.userID";
		operationGet();
		nameSurname_cBox.getItems().clear();
		comboBoxLoad(nameSurname_cBox);
	}

	String[] value = new String[2];

	// Comboboxdan seçilen kişinin verilerini tableview e aktarma
	@FXML
	void nameSurname_cBox_Click(ActionEvent event) {
		if (nameSurname_cBox.getSelectionModel().getSelectedItem() != null) {
			int i = 0;
			String[] nameSurname = nameSurname_cBox.getSelectionModel().getSelectedItem().split(" ");
			for (String type : nameSurname) {
				value[i] = type;
				i++;
			}
			if (!value.equals(null)) {
				query = "SELECT * FROM operation INNER JOIN users ON operation.userID=users.userID WHERE users.name='"
						+ value[0] + "' AND users.surname='" + value[1] + "'";
				operationGet();
			}
		}
	}

	@FXML
	void initialize() {
		query = "SELECT * FROM operation INNER JOIN users ON operation.userID=users.userID";
		operationGet();
		comboBoxLoad(nameSurname_cBox);
		filterType_cBox.getItems().add("Tarih Aralığı");
		filterType_cBox.getItems().add("Müşteri Adı");
	}

}
