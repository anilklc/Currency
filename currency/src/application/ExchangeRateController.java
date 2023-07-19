package application;

import java.net.URL;
import java.util.ResourceBundle;
import DB.XMLHelper;
import Models.XMLView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ExchangeRateController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TableColumn<XMLView, String> currencyCode_clm;

	@FXML
	private TableColumn<XMLView, String> currency_clm;

	@FXML
	private TableView<XMLView> currency_tableview;

	@FXML
	private TableColumn<XMLView, String> forexBuying_clm;

	@FXML
	private TableColumn<XMLView, String> forexSelling_clm;

	@FXML
	private TableColumn<XMLView, String> unit_clm;

	@FXML
	private Button update_button;

	XMLHelper xml = new XMLHelper();

	// Xml de bulunan verilerin tableview eklenmesi
	@FXML
	void update_button_Click(ActionEvent event) {
		xml.XMLGet();
		currency_tableview.setItems(xml.currencyList);
	}

	@FXML
	void initialize() {
		xml.XMLGet();
		currency_tableview.setItems(xml.currencyList);
		unit_clm.setCellValueFactory(new PropertyValueFactory<>("unit"));
		currency_clm.setCellValueFactory(new PropertyValueFactory<>("currency"));
		forexBuying_clm.setCellValueFactory(new PropertyValueFactory<>("forexBuying"));
		forexSelling_clm.setCellValueFactory(new PropertyValueFactory<>("forexSelling"));
		currencyCode_clm.setCellValueFactory(new PropertyValueFactory<>("currencyCode"));
	}

}
