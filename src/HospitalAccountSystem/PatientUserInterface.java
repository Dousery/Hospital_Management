package HospitalAccountSystem;

import People.Patient;

public interface PatientUserInterface extends HospitalInterface{
    void displayPatientData(int ID);
    void bookAppointment(Patient patient);
    Patient getPatient(int ID);
    void displayDoctors();
    void viewPatientAppointments(int ID);
    void cancelAppointment(int ID);
}
