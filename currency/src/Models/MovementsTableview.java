package Models;

public class MovementsTableview {
	private String name;
	private String surname;
	private String userAction;
	private String userLastEntry;

	public MovementsTableview(String name, String surname, String userAction, String userLastEntry) {
		this.name = name;
		this.surname = surname;
		this.userAction = userAction;
		this.userLastEntry = userLastEntry;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUserAction() {
		return userAction;
	}

	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}

	public String getUserLastEntry() {
		return userLastEntry;
	}

	public void setUserLastEntry(String userLastEntry) {
		this.userLastEntry = userLastEntry;
	}
}
