package People.Staff;

import People.PatientData.Operation;
import People.Person;

import java.util.ArrayList;

public class Surgeon extends Staff{

    ArrayList<Operation> operations;
    public Surgeon(Person person, int workerID) {
        super(person,workerID);
        this.setID(person.getID());
        this.setName(person.getName());
        this.setEmail(person.getEmail());
        this.setPhone(person.getPhone());
        this.operations = new ArrayList<>();
    }

    public void addOperations(Operation operation) { operations.add(operation); }

    public void viewMedicalHistory() {
        System.out.println("Medical History of Doctor " + getName()+" : ");
        for (Operation operation : operations) {
            System.out.println("Operation Type: " + operation.getOperationType());
            System.out.println("Date: " + operation.getDate());
            System.out.println("Patient: " + operation.getPatient().getName());
            System.out.println("Scientist: " + operation.getSurgeon().getName());
            System.out.println("Notes:" + operation.getNotes());
            System.out.println("----------------------------------------");
        }

    }
}
