package DB;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;
import java.util.Random;

public class DBHelper {
	private String url = "jdbc:mysql://localhost:3306/202503077";
	private String userName = "root";
	private String password = "12345";
	public Connection connection = null;

	// Veritabanı bağlantısı oluşturma
	public Connection connectOpen() {
		try {
			connection = DriverManager.getConnection(url, userName, password);
		} catch (Exception expection) {

			System.out.println(expection.getMessage());
		}
		return connection;
	}
   
	// Veritabanı bağlantısı kapatma
	public void connectClose() {

		try {
			connection.close();

		} catch (Exception expection) {

			System.out.println(expection.getMessage());
		}
	}

	// Şifrenin MD5 e dönüştürülerek eklenmesi
	public String MD5Generator(String s) throws Exception {
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(s.getBytes(), 0, s.length());
		return new BigInteger(1, m.digest()).toString(16);
	}

	// Random oalrak doğrulama kodu oluşturma
	private int confrimNumber;

	public int getConfrimNumber() {
		return confrimNumber;
	}

	public void setConfrimNumber(int confrimNumber) {
		this.confrimNumber = confrimNumber;
	}

	public void confrim() {
		Random random = new Random();
		setConfrimNumber(random.nextInt(90000) + 10000);
	}
}
