package People.PatientData;

import People.Patient;
import People.Staff.Doctor;

public class Appointment {

	private AppointmentStatus appointmentStatus;
	private String date;
	private final int appointmentNo;
	private final Doctor assignedDoctor;
	private Patient patient;
	private String notes;
	
	public Appointment(int appointmentNo, String date, Patient patient,
			Doctor assignedDoctor) {
		this.appointmentStatus = AppointmentStatus.Pending;
		this.appointmentNo = appointmentNo;
		this.date = date;
		this.patient = patient;
		this.assignedDoctor = assignedDoctor;
		this.notes = "None";
	}

	public String getStatusString() {
		if (appointmentStatus == AppointmentStatus.Pending) return "Pending";
		if (appointmentStatus == AppointmentStatus.Cancelled) return "Cancelled";
		if (appointmentStatus == AppointmentStatus.Scheduled) return "Scheduled";
		if (appointmentStatus == AppointmentStatus.Done) return "Done";
		else return "Error";
	}

	public void display() {
		System.out.println("Appointment no: "+appointmentNo);
		System.out.println("Date: "+date);
		System.out.println("Doctor: "+assignedDoctor.getName());
		System.out.println("Patient: "+patient.getName());
		System.out.println("Patient ID: "+patient.getID());
		System.out.println("Status: "+getStatusString());
	}

	public AppointmentStatus getAppointmentStatus() {
		return appointmentStatus;
	}

	public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getAppointmentNo() {
		return appointmentNo;
	}

	public Doctor getAssignedDoctor() {
		return assignedDoctor;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}


	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotes() {
		return notes;
	}
}

