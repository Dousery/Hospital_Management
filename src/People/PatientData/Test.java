package People.PatientData;

import People.Patient;
import People.Staff.Scientist;

public class Test {
	int testNo;
	private final String testType;
	private String result;
	private String date;
	private Patient patient;
	private final Scientist scientist;
	
	public Test(int testNo,String testType, String result, String date, Patient patient, Scientist scientist) {
		super();
		this.testNo = testNo;
		this.testType = testType;
		this.result = result;
		this.date = date;
		this.patient = patient;
		this.scientist = scientist;
	}

	public void display() {
		System.out.println("Test No: "+testNo);
		System.out.println("Test Type: "+testType);
		System.out.println("Date: "+date);
		System.out.println("Patient: "+patient.getName());
		System.out.println("Scientist "+scientist.getName());
		System.out.println("Result: "+result);
	}

	public int getTestNo() {
		return testNo;
	}

	public String getTestType() {
		return testType;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}


    public String getTestResult() {
		return result;
    }

	public Scientist getScientist() {
		return scientist;
	}
}
