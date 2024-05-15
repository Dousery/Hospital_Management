package People.Staff;

import java.util.ArrayList;

import People.Person;
import People.PatientData.Appointment;

public class Doctor extends Staff {
	private final ArrayList<Appointment> scheduledAppointments;

	public Doctor(Person person, int workerID) {
		super(person,workerID);
		this.setID(person.getID());
		this.setName(person.getName());
		this.setEmail(person.getEmail());
		this.setPhone(person.getPhone());
		this.scheduledAppointments = new ArrayList<>();
	}

	public ArrayList<Appointment> getScheduledAppointments() {
		return scheduledAppointments;
	}

	public void addAppointment(Appointment appointment) {
		scheduledAppointments.add(appointment);
	}

	public void viewMedicalHistory() {
		System.out.println("Medical History of Doctor " + getName()+" : ");
	    for (Appointment appointment : scheduledAppointments) {
	        System.out.println("Appointment No: " + appointment.getAppointmentNo());
	        System.out.println("Date: " + appointment.getDate());
	        System.out.println("Patient: " + appointment.getPatient().getName());
	        System.out.println("Status: " + appointment.getAppointmentStatus());
	        System.out.println("----------------------------------------");
	    }
	
	}
}
