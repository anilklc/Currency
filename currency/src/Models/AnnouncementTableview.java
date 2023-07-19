package Models;

public class AnnouncementTableview {
	private String announcementID;
	private String announcementName;
	private String announcementText;
	private String announcementDate;
	private String name;
	private String surname;
	private String username;
    
	public AnnouncementTableview(String announcementID, String announcementName, String announcementText,
			String announcementDate, String name, String surname, String username) {
		this.announcementID = announcementID;
		this.announcementName = announcementName;
		this.announcementText = announcementText;
		this.announcementDate = announcementDate;
		this.name = name;
		this.surname = surname;
		this.username = username;
	}

	public String getAnnouncementID() {
		return announcementID;
	}

	public void setAnnouncementID(String announcementID) {
		this.announcementID = announcementID;
	}

	public String getAnnouncementName() {
		return announcementName;
	}

	public void setAnnouncementName(String announcementName) {
		this.announcementName = announcementName;
	}

	public String getAnnouncementText() {
		return announcementText;
	}

	public void setAnnouncementText(String announcementText) {
		this.announcementText = announcementText;
	}

	public String getAnnouncementDate() {
		return announcementDate;
	}

	public void setAnnouncementDate(String announcementDate) {
		this.announcementDate = announcementDate;
	}

	public String getName() {
		return name;
	}

	public void setAnnouncementUserN(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
