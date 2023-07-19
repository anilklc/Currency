package Models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import DB.DBHelper;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class UserInfo {

	DBHelper db = new DBHelper();
	PreparedStatement statement;
	ResultSet resultSet;
	String query;
	static String userID;
	static String userName;
	static String userAuthority;
	private String name;
	private String surname;
	private String currencyID;
	private float currentMoney;

	public float getCurrentMoney() {
		return currentMoney;
	}

	public void setCurrentMoney(float currentMoney) {
		this.currentMoney = currentMoney;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserAuthority() {
		return userAuthority;
	}

	public void setUserAuthority(String userAuthority) {
		UserInfo.userAuthority = userAuthority;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		UserInfo.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		UserInfo.userName = userName;
	}

	public String getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(String currencyID) {
		this.currencyID = currencyID;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	// Aktif olan kullanıcının yetki tipini , adını ve soyadını tutma
	public void profilGet() {

		try {
			db.connectOpen();
			query = "SELECT * FROM users WHERE userID=? ";
			statement = db.connection.prepareStatement(query);
			statement.setString(1, getUserID());
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				setUserName(resultSet.getString("username"));
				setUserAuthority(resultSet.getString("userAuthority"));
				setName(resultSet.getString("name"));
				setSurname(resultSet.getString("surname"));
			}
			db.connectClose();
			statement.close();
			resultSet.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Aktif olan kullanıcının aksiyonlarını log olarak ekleme
	public void userAction(String action) {
		try {
			db.connectOpen();
			query = "INSERT INTO log_users (userID,userAction,userLastEntry) VALUES (?,?,(SELECT CURRENT_TIMESTAMP()))";
			statement = db.connection.prepareStatement(query);
			statement.setString(1, getUserID());
			statement.setString(2, action);
			statement.execute();
			db.connectClose();
			statement.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
    
	// Para kontrolu yapma
	public void moneyControl(String currency) {
		try {
			db.connectOpen();
			query = "SELECT * FROM till WHERE currency=?";
			statement = db.connection.prepareStatement(query);
			statement.setString(1, currency);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				setCurrencyID(resultSet.getString("currencyID"));
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Hatalı İşlem");
				alert.setHeaderText("Hatalı İşlem");
				alert.setContentText(
						"Bu para birimi daha önce eklenmiş lütfen kontrol edip işlemlerinize devam ediniz.");
				alert.showAndWait();
			}
			db.connectClose();
			statement.close();
			resultSet.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Para birimi kontrol gelen değerlere göre yeterli miktarda para varmı kontrol etme
	public Boolean tillControl(String currency) {
		try {
			db.connectOpen();
			query = "SELECT * FROM till WHERE currencyCode=?";
			statement = db.connection.prepareStatement(query);
			statement.setString(1, currency);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				setCurrencyID(resultSet.getString("currencyID"));
				setCurrentMoney(resultSet.getFloat("currentMoney"));
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Hatalı İşlem");
				alert.setHeaderText("Hatalı İşlem");
				alert.setContentText(
						"Bu para birimi daha önce eklenmiş lütfen kontrol edip işlemlerinize devam ediniz.");
				alert.showAndWait();
			}
			db.connectClose();
			statement.close();
			resultSet.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return true;
	}

	// Yapılan alım satım işlemine göre o an satılan birim fiyat alınan ve verilen tutar log olarak tutma
	public void userActionMoney(String action, float oldMoney, float newMoney, float changingMoney) {
		try {
			db.connectOpen();
			query = "INSERT INTO log_till (userID,currencyID,userAction,oldMoney,newMoney,changingMoney,actionDate) VALUES (?,?,?,?,?,?,(SELECT CURRENT_TIMESTAMP()))";
			statement = db.connection.prepareStatement(query);
			statement.setString(1, getUserID());
			statement.setString(2, getCurrencyID());
			statement.setString(3, action);
			statement.setFloat(4, oldMoney);
			statement.setFloat(5, newMoney);
			statement.setFloat(6, changingMoney);
			statement.execute();
			db.connectClose();
			statement.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	// Yapılan alım satım işleminden sonra kasadaki para miktarını güncelleme
	public void updateMoney(float changingMoney, String currencyID) {
		try {
			db.connectOpen();
			query = "UPDATE till SET currentMoney=? , currencyUploadDate=(SELECT CURRENT_TIMESTAMP()) WHERE currencyID=?";
			statement = db.connection.prepareStatement(query);
			statement.setFloat(1, changingMoney);
			statement.setString(2, currencyID);
			statement.execute();
			db.connectClose();
			statement.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
