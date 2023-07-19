package application;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import DB.DBHelper;
import Models.UserInfo;
import Models.operationTableview;
import Models.tillTableview;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class StatisticsController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TableColumn<operationTableview, Float> buyAmount_clm;

	@FXML
	private TableColumn<operationTableview, String> buy_clm;

	@FXML
	private Button caseUpdate_button;

	@FXML
	private TableColumn<tillTableview, String> currencyCode_clm;

	@FXML
	private TableColumn<tillTableview, String> currency_clm;

	@FXML
	private TableColumn<tillTableview, String> currentMoney_clm;

	@FXML
	private TableColumn<operationTableview, String> mail_clm;

	@FXML
	private Label nameSurnameTitle_label;

	@FXML
	private Label nameSurname_label;

	@FXML
	private TableColumn<operationTableview, String> name_clm;

	@FXML
	private TableColumn<operationTableview, String> operationDate_clm;

	@FXML
	private Button operationUpdate_button;

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
	private TableColumn<operationTableview, String> surname_clm;

	@FXML
	private TableView<tillTableview> till_tableview;

	UserInfo user = new UserInfo();
	DBHelper db = new DBHelper();
	PreparedStatement statement;
	ResultSet resultSet;
	String query;
	String password;
	ObservableList<tillTableview> tblListTill = FXCollections.observableArrayList();
	ObservableList<operationTableview> tblListOperation = FXCollections.observableArrayList();

	// Kasadaki paraların tableview e aktarılması
	void tillGet() {
		try {
			tblListTill.clear();
			db.connectOpen();
			query = "SELECT * FROM till";
			statement = db.connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				tblListTill.add(new tillTableview(resultSet.getString("currency"), resultSet.getString("currencyCode"),
						resultSet.getFloat("currentMoney")));
				till_tableview.setItems(tblListTill);
			}
			db.connectClose();
			statement.close();
			resultSet.close();
			currency_clm.setCellValueFactory(new PropertyValueFactory<>("currency"));
			currencyCode_clm.setCellValueFactory(new PropertyValueFactory<>("currencyCode"));
			currentMoney_clm.setCellValueFactory(new PropertyValueFactory<>("currentMoney"));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	//Yapılan işlemlerin tableview e aktarılması
	void operationGet() {
		try {
			tblListOperation.clear();
			db.connectOpen();
			query = "SELECT * FROM operation WHERE userID=?";
			statement = db.connection.prepareStatement(query);
			statement.setString(1, user.getUserID());
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				tblListOperation.add(new operationTableview(resultSet.getString("customerName"),
						resultSet.getString("customerSurname"), resultSet.getString("customerTC"),
						resultSet.getString("customerPhone"), resultSet.getString("customerMail"),
						resultSet.getString("sellCode"), resultSet.getString("buyCode"),
						resultSet.getFloat("sellInstantPrice"), resultSet.getFloat("buyAmount"),
						resultSet.getFloat("sellAmount"), resultSet.getString("operationDate")));
				operation_tableview.setItems(tblListOperation);
			}
			db.connectClose();
			statement.close();
			resultSet.close();
			name_clm.setCellValueFactory(new PropertyValueFactory<>("customerName"));
			surname_clm.setCellValueFactory(new PropertyValueFactory<>("customerSurname"));
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
    
	// Buton ile fonksiyonun tetiklenmesi
	@FXML
	void caseUpdate_button_Click(ActionEvent event) {
		tillGet();
	}

	// Buton ile fonksiyonun tetiklenmesi
	@FXML
	void operationUpdate_button_Click(ActionEvent event) {
		operationGet();
	}

	@FXML
	void initialize() {
		user.profilGet();
		nameSurname_label.setText(user.getName() + " " + user.getSurname());
		tillGet();
		operationGet();

	}

}
