package application;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import DB.DBHelper;
import Models.AnnouncementTableview;
import Models.UserInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class AnnouncementController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button add_button;

	@FXML
	private TableColumn<AnnouncementTableview, String> announcementDate_clm;

	@FXML
	private TableColumn<AnnouncementTableview, String> announcementName_clm;

	@FXML
	private TableColumn<AnnouncementTableview, String> announcementText_clm;

	@FXML
	private Label announcementTitle_label;

	@FXML
	private TextField announcementTitle_text;

	@FXML
	private TableColumn<AnnouncementTableview, String> name_clm;

	@FXML
	private TableColumn<AnnouncementTableview, String> surname_clm;

	@FXML
	private TableView<AnnouncementTableview> announcement_tableview;

	@FXML
	private TextArea announcement_textArea;

	@FXML
	private Button clear_button;

	@FXML
	private Button delete_button;

	@FXML
	private Label senderInfo_label;

	@FXML
	private Label sender_label;

	@FXML
	private Button update_button;

	UserInfo user = new UserInfo();
	DBHelper db = new DBHelper();
	PreparedStatement statement;
	ResultSet resultSet;
	String query;
	AnnouncementTableview tbl = null;
	ObservableList<AnnouncementTableview> tblList = FXCollections.observableArrayList();
	
	// Duyuru tableview e verileri yükleme
	void announcementUpdate() {
		try {
			db.connectOpen();
			query = "SELECT * FROM announcement INNER JOIN users ON announcement.userID=users.userID";
			statement = db.connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			tblList.clear();
			while (resultSet.next()) {

				tblList.add(new AnnouncementTableview(resultSet.getString("announcementID"),
						resultSet.getString("announcementName"), resultSet.getString("announcementText"),
						resultSet.getString("announcementDate"), resultSet.getString("name"),
						resultSet.getString("surname"), resultSet.getString("username")));
				announcement_tableview.setItems(tblList);
			}
			db.connectClose();
			statement.close();
			resultSet.close();
			name_clm.setCellValueFactory(new PropertyValueFactory<>("name"));
			surname_clm.setCellValueFactory(new PropertyValueFactory<>("surname"));
			announcementName_clm.setCellValueFactory(new PropertyValueFactory<>("announcementName"));
			announcementText_clm.setCellValueFactory(new PropertyValueFactory<>("announcementText"));
			announcementDate_clm.setCellValueFactory(new PropertyValueFactory<>("announcementDate"));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}
	
	// Duyuru ekleme
	void announcementAdd() {
		try {
			db.connectOpen();
			query = "INSERT INTO announcement(userID, announcementName, announcementText, announcementDate) VALUES (?,?,?,(SELECT CURRENT_TIMESTAMP()))";
			statement = db.connection.prepareStatement(query);
			statement.setString(1, user.getUserID());
			statement.setString(2, announcementTitle_text.getText().trim());
			statement.setString(3, announcement_textArea.getText().trim());
			statement.executeUpdate();
			db.connectClose();
			statement.close();
			user.userAction("Duyuru Eklendi");
			textClear();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
    
	// Duyuru silme
	void announcementDelete() {
		try {
			db.connectOpen();
			statement = db.connection.prepareStatement(query);
			statement.setString(1, tbl.getAnnouncementID());
			statement.executeUpdate();
			db.connectClose();
			statement.close();
			user.userAction("Duyuru Silindi");
			textClear();
			announcementUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
    
	//Ekrandaki dolu textleri temizleme
	void textClear() {
		announcementTitle_text.setText("");
		announcement_textArea.setText("");
		sender_label.setText("");
	}
    
	// Kontrol yapıldıktan sonra duyuru ekleme fonksiyonu tetikleme
	@FXML
	void add_button_Click(ActionEvent event) {
		if (announcementTitle_text.getText() != "" & announcement_textArea.getText() != "") {
			announcementAdd();
			announcementUpdate();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Eksik Bilgi");
			alert.setHeaderText("Eksik Bilgi");
			alert.setContentText("Lütfen tüm alanları doldurunuz.");
			alert.showAndWait();
		}
	}

	// Tableview de seçilen duyuruyu ekrana getirme
	@FXML
	void announcement_tableview_Click(MouseEvent event) {
		tbl = announcement_tableview.getSelectionModel().getSelectedItem();
		announcementTitle_text.setText(tbl.getAnnouncementName());
		announcement_textArea.setText(tbl.getAnnouncementText());
		sender_label.setText(tbl.getName() + " " + tbl.getSurname());
	}

	// Clear butonuyla ekrandaki textleri temizleme
	@FXML
	void clear_button_Click(ActionEvent event) {
		textClear();
	}
    
	// Seçilen duyuruyu silme fonksiyonu tetikleme
	@FXML
	void delete_button_Click(ActionEvent event) {
		user.profilGet();
		if (tbl.getAnnouncementID() != "" & user.getUserAuthority().equals("0")) {
			query = "DELETE FROM announcement WHERE announcementID=?";
			announcementDelete();
		} else if (tbl.getAnnouncementID() != "" & user.getUserAuthority().equals("1")
				& tbl.getUsername().equals(user.getUserName())) {

			query = "DELETE FROM announcement WHERE announcementID=? AND userID=" + user.getUserID();
			announcementDelete();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Duyuru Silme Hatası");
			alert.setHeaderText("Eksik Bilgi veya Yetkisiz İşlem");
			alert.setContentText("Lütfen silinicek duyuru seçip daha sonra deneyiniz veya \nseçtiğiniz duyurunun yönetici değilseniz kendinize ait \nolduğundan emin olunuz.");
			alert.showAndWait();
		}
	}
    
	// Duyuru tableview deki verileri güncellemek için buton ile fonksiyonu tetikleme
	@FXML
	void update_button_Click(ActionEvent event) {
		announcementUpdate();
	}

	@FXML
	void initialize() {
		clear_button.setTooltip(new Tooltip("Ekranda bulunan duyuruyu temizle."));
		announcementUpdate();
	}

}
