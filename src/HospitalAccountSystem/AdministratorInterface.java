package HospitalAccountSystem;

import People.Person;

import java.io.IOException;

public interface AdministratorInterface extends HospitalInterface {
    void addStaff(Person person);
    void listStaff();
    void displayStaff(int WorkerID);
    int removeStaff(int WorkerID) throws IOException;
}
