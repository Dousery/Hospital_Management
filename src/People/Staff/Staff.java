package People.Staff;

import People.Person;

public abstract class Staff extends Person {
	int ID;
	private String name,phone,email;

	public int getWorkerID() {
		return workerID;
	}

	private final int workerID;

	public void viewMedicalHistory() {}

	public Staff(Person person,int workerID) {
		super(person.getID(), person.getName(), person.getPhone(), person.getEmail());
		this.workerID=workerID;
	}

	public void displayStaffData() {
		System.out.println("Name: "+name);
		System.out.println("Email: "+email);
		System.out.println("Phone:" +phone);
		System.out.println("WorkerID: "+workerID);
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