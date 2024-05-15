package People.Staff;

import People.PatientData.Test;
import People.Person;

import java.util.ArrayList;

public class Scientist extends Staff {

    ArrayList<Test> tests;

    public Scientist(Person person, int workerID) {
        super(person,workerID);
        this.setID(person.getID());
        this.setName(person.getName());
        this.setEmail(person.getEmail());
        this.setPhone(person.getPhone());
        this.tests = new ArrayList<>();
    }

    public void addTest(Test test) { tests.add(test); }

    public void viewMedicalHistory() {
        System.out.println("Medical History of Doctor " + getName()+" : ");
        for (Test test : tests) {
            System.out.println("Test Type: " + test.getTestType());
            System.out.println("Date: " + test.getDate());
            System.out.println("Patient: " + test.getPatient().getName());
            System.out.println("Scientist: " + test.getScientist().getName());
            System.out.println("Result:" + test.getResult());
            System.out.println("----------------------------------------");
        }

    }
}
