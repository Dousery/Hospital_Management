package People;

public class Person {
	private int ID;
	private  String name,phone,email;
	
	public Person(int iD, String name, String phone, String email) {
		ID = iD;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
