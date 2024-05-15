package HospitalAccountSystem;

public class Account {

    private final int id;
    private final String user;
    private final String password;
    private final accessLevel accessLevel;

    public Account(int id, String user, String password, String accessLevel) throws invalidAccessLevel {

        this.id = id;
        this.user = user;
        this.password = password;

        switch (accessLevel) {
            case "administrator" -> this.accessLevel = HospitalAccountSystem.accessLevel.Administrator;
            case "patient" -> this.accessLevel = HospitalAccountSystem.accessLevel.Patient;
            case "staff" -> this.accessLevel = HospitalAccountSystem.accessLevel.Staff;
            default -> throw new invalidAccessLevel();
        }

    }

    public String returnCSVRecord() {
        String accessLevelString = null;
        if (accessLevel == HospitalAccountSystem.accessLevel.Patient)
            accessLevelString = "patient";
        else if (accessLevel == HospitalAccountSystem.accessLevel.Administrator)
            accessLevelString = "administrator";
        else if (accessLevel == HospitalAccountSystem.accessLevel.Staff)
            accessLevelString = "staff";
        return id +","+user+","+password+","+accessLevelString;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public accessLevel getAccessLevel() {
        return this.accessLevel;
    }
}

enum accessLevel {
    Patient,
    Staff,
    Administrator
}
