package HospitalAccountSystem;

import People.PatientData.Appointment;

public interface StaffUserInterface extends HospitalInterface{
    int getWorkerID(int PersonID);
    void displayStaff(int WorkerID);
    void listPatients();
    void displayPatientData(int PersonID);
    void viewPendingAppointments();
    Appointment getPendingAppointment(int AppointmentNo);
    void approveAppointment(int AppointmentNo);
    void cancelAppointment(int AppointmentNo);
    void recordAppointment(int appNo, int workerID);
    void addTest(int patientID, int workerID);
    void viewTests();
    void concludeTest(int testNo);
    void addOperation(int patientID, int workerID);
    void prescribeMedicine(int patientID);
}
