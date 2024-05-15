package People.PatientData;

import People.Patient;
import People.Staff.Surgeon;

public class Operation {
    Patient patient;
    Surgeon surgeon;
    String operationType;
    String date;
    String notes;

    public Operation(Patient patient, Surgeon surgeon,String operationType, String date, String notes) {
        this.patient = patient;
        this.surgeon = surgeon;
        this.operationType = operationType;
        this.date = date;
        this.notes = notes;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Surgeon getSurgeon() {
        return surgeon;
    }

    public String getOperationType() {
        return operationType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }
}
