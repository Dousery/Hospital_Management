package People.Staff;

import People.Person;

public abstract class StaffFactory {

    public static Staff newStaff(Person person, int WorkerID, String type) {
        if (type.equals("Doctor")) {
            return new Doctor(person,WorkerID);
        }
        else if (type.equals("Surgeon")) {
            return new Surgeon(person,WorkerID);
        }
        else if (type.equals("Scientist")) {
            return new Scientist(person,WorkerID);
        }
        return null;
    }

}
