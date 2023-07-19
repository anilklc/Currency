package application;

import java.net.URL;
import java.util.ResourceBundle;
import Models.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainPageAdminController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button accountSetting_button;

	@FXML
	private Button announcement_button;

	@FXML
	private Button buyAndSell_button;

	@FXML
	private ImageView close_image;

	@FXML
	private Button movements_button;

	@FXML
	private Button logout_button;

	@FXML
	private ImageView minimize_image;

	@FXML
	private Button money_button;

	@FXML
	private Button past_button;

	@FXML
	private Button rate_button;

	@FXML
	private AnchorPane stagePane;

	@FXML
	private Button statistics_button;

	@FXML
	private Button worker_button;

	UserInfo user = new UserInfo();

	// Kullanıcın buton ile seçtiği stage in açılması
	void childrenStage(String stageName) {
		try {
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(getClass().getResource(stageName));
			stagePane.getChildren().setAll(pane1);

		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	@FXML
	void close_image_Click(MouseEvent event) {
		user.userAction("Çıkış Yapıldı");
		System.exit(0);
	}

	@FXML
	void minimize_image_Click(MouseEvent event) {
		Stage stage = (Stage) minimize_image.getScene().getWindow();
		stage.setIconified(true);
	}

	@FXML
	void accountSetting_button_Click(ActionEvent event) {
		childrenStage("AccountSetting.fxml");
	}

	@FXML
	void announcement_button_Click(ActionEvent event) {
		childrenStage("Announcement.fxml");
	}

	@FXML
	void buyAndSell_button_Click(ActionEvent event) {
		childrenStage("BuyAndSell.fxml");
	}

	@FXML
	void movements_button_Click(ActionEvent event) {
		childrenStage("Movements.fxml");
	}

	@FXML
	void logout_button_Click(ActionEvent event) {
		user.userAction("Çıkış Yapıldı");
		System.exit(0);
	}

	@FXML
	void money_button_Click(ActionEvent event) {
		childrenStage("MoneyAdd.fxml");
	}

	@FXML
	void past_button_Click(ActionEvent event) {
		childrenStage("Past.fxml");
	}

	@FXML
	void rate_button_Click(ActionEvent event) {
		childrenStage("ExchangeRate.fxml");
	}

	@FXML
	void statistics_button_Click(ActionEvent event) {
		childrenStage("Statistics.fxml");
	}

	@FXML
	void worker_button_Click(ActionEvent event) {
		childrenStage("WorkerAdd.fxml");
	}

	@FXML
	void initialize() {
		user.profilGet();
	}

}
