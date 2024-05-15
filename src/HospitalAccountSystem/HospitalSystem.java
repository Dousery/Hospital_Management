package HospitalAccountSystem;

import People.PatientData.Appointment;
import People.Person;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class HospitalSystem {

    static ArrayList<Account> accounts = new ArrayList<>();
    static String csvParams;

    public static void main(String[] args) throws IOException, invalidAccessLevel {

        Scanner scanner = new Scanner(new File("Accounts.csv"));
        csvParams = scanner.nextLine();

        while (scanner.hasNextLine()) {
            String[] parsed = scanner.nextLine().split(",");
            try {
                accounts.add(new Account(Integer.parseInt(parsed[0]),parsed[1],parsed[2],parsed[3]));
            } catch (invalidAccessLevel e) {
                System.out.println("invalid access level, skipping line.");
            }
        }

        scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.equals("1")) {
                System.out.println("Username:");
                String user = scanner.nextLine();
                System.out.println("Password:");
                String password = scanner.nextLine();
                login(user,password);
                continue;
            }
            if (input.equals("2")) {
                System.out.println("Enter ID:");
                int id = Integer.parseInt(scanner.nextLine());
                System.out.println("Username:");
                String user = scanner.nextLine();
                System.out.println("Password:");
                String password = scanner.nextLine();
                System.out.println("Password again:");
                String passwordAgain = scanner.nextLine();

                if (password.equals(passwordAgain)) {
                    addAccount(new Account(id,user,password,"patient"));
                    FileWriter fileWriter = new FileWriter("Patients.csv",true);
                    System.out.println("Phone:");
                    String phone = scanner.nextLine();
                    System.out.println("Email:");
                    String email = scanner.nextLine();
                    fileWriter.write("\n"+id+","+user+","+phone+","+email);
                    fileWriter.close();
                    fileWriter = new FileWriter("Accounts.csv",true);
                    fileWriter.write("\n"+id+","+user+","+password+",patient");
                    fileWriter.close();
                }
                else
                    System.out.println("Password doesn't match. Try again.");
                continue;
            }
            if (input.equals("0")) {
                break;
            }

        }


    }

    public static void login(String user, String password) throws IOException {
        for (Account account : accounts)
            if (account.getName().equals(user) && account.getPassword().equals(password)) {
                System.out.println("Successfully logged in!");
                accessLevel accessLevel = account.getAccessLevel();
                Hospital hospital = new Hospital();

                if (accessLevel == HospitalAccountSystem.accessLevel.Patient) {
                    patientAccess(hospital,account);
                }
                if (accessLevel == HospitalAccountSystem.accessLevel.Staff) {
                    staffAccess(hospital,account);
                }
                if (accessLevel == HospitalAccountSystem.accessLevel.Administrator) {
                    adminAccess(hospital,account);
                }
                hospital.save();
                break;
            }
        exit();
    }

    public static void patientAccess(Hospital hospital, Account account) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome "+account.getName());
            System.out.println("1. View your medical data.");
            System.out.println("2. Book an appointment.");
            System.out.println("3. View Doctors");
            System.out.println("4. Cancel appointment");
            System.out.println("0. Exit");
            System.out.print(account.getName()+" > ");
            String input = scanner.nextLine();
            if (input.equals("0"))
                break;
            if (input.equals("1")) {
                hospital.displayPatientData(account.getId());
                continue;
            }
            if (input.equals("2")) {
                hospital.bookAppointment(hospital.getPatient(account.getId()));
            }
            if (input.equals("3")) {
                hospital.displayDoctors();
            }
            if (input.equals("4")) {
                hospital.viewPatientAppointments(account.getId());
                System.out.println("Enter appointment number, 0 to cancel:");
                int appNo = Integer.parseInt(scanner.nextLine());
                if (appNo == 0) continue;
                hospital.cancelAppointment(appNo);
            }
        }
    }

    public static void staffAccess(Hospital hospital,Account account) {
        Scanner scanner = new Scanner(System.in);
        int workerID = hospital.getWorkerID(account.getId());
        while (true) {
            System.out.println("Welcome "+account.getName());
            System.out.println("1. View Medical Portfolio");
            System.out.println("2. List Patients");
            System.out.println("3. View Patient Data");
            System.out.println("4. Approve/Deny Pending Appointments");
            System.out.println("5. Finish Appointment");
            System.out.println("6. Add Test");
            System.out.println("7. Finish Test");
            System.out.println("8. Add Operation");
            System.out.println("9. Prescribe Medicine");
            System.out.println("0. Exit");
            System.out.print(account.getName()+" > ");
            String input = scanner.nextLine();
            if (input.equals("0"))
                break;
            if (input.equals("1")) {
                hospital.displayStaff(workerID);
                continue;
            }
            if (input.equals("2")) {
                hospital.listPatients();
                continue;
            }
            if (input.equals("3")) {
                System.out.println("Patient ID:");
                int PatientID = Integer.parseInt(scanner.nextLine());
                hospital.displayPatientData(PatientID);
                continue;
            }
            if (input.equals("4")) {
                hospital.viewPendingAppointments();
                int appNo = Integer.parseInt(scanner.nextLine());
                System.out.println("Select appointment, 0 to select none.");
                if (appNo == 0) continue;
                Appointment appointment = hospital.getPendingAppointment(appNo);
                if (appointment == null) continue;
                while (true) {
                    System.out.println("1. Approve");
                    System.out.println("2. Deny");
                    System.out.println("0. Exit");
                    input = scanner.nextLine();
                    if (input.equals("0")) break;
                    if (input.equals("1")) {
                        hospital.approveAppointment(appNo);
                        break;
                    }
                    if (input.equals("2")) {
                        System.out.println("Add note or leave blank:");
                        String note = scanner.nextLine();
                        appointment.setNotes(note);
                        hospital.cancelAppointment(appNo);
                        break;
                    }
                }
                continue;
            }
            if (input.equals("5")) {
                hospital.displayStaff(workerID);
                System.out.println("Appointment No (0 to cancel):");
                int appNo = Integer.parseInt(scanner.nextLine());
                if (appNo == 0) continue;
                hospital.recordAppointment(appNo,workerID);
                continue;
            }
            if (input.equals("6")) {
                System.out.println("Patient ID:");
                hospital.addTest(Integer.parseInt(scanner.nextLine()),workerID);
                continue;
            }
            if (input.equals("7")) {
                hospital.viewTests();
                System.out.println("Test No (0 to cancel):");
                int testNo = Integer.parseInt(scanner.nextLine());
                if (testNo == 0) continue;
                hospital.concludeTest(testNo);
                continue;
            }
            if (input.equals("8")) {
                System.out.println("Patient ID:");
                hospital.addOperation(Integer.parseInt(scanner.nextLine()),workerID);
                continue;
            }
            if (input.equals("9")) {
                System.out.println("Patient ID:");
                hospital.prescribeMedicine(Integer.parseInt(scanner.nextLine()));
            }
        }
    }

    public static void adminAccess(Hospital hospital, Account account) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome "+account.getName());
            System.out.println("1. Add Staff");
            System.out.println("2. List Staff");
            System.out.println("3. View Staff");
            System.out.println("4. Remove Staff");
            System.out.println("0. Exit");
            System.out.print(account.getName()+" > ");
            String input = scanner.nextLine();
            if (input.equals("0")) break;
            if (input.equals("1")) {
                FileWriter fileWriter = new FileWriter("Accounts.csv",true);
                System.out.println("Enter ID:");
                int id = Integer.parseInt(scanner.nextLine());
                System.out.println("Username:");
                String staffUser = scanner.nextLine();
                System.out.println("Password:");
                String staffPassword = scanner.nextLine();
                System.out.println("Password again:");
                String passwordAgain = scanner.nextLine();

                if (staffPassword.equals(passwordAgain)) {
                    boolean success = false;
                    try {
                        success = addAccount(new Account(id, staffUser, staffPassword, "staff"));
                        fileWriter.write("\n"+id+","+staffUser+","+staffPassword+",staff");
                        fileWriter.close();
                    } catch (invalidAccessLevel e) {
                        System.out.println("invalid access level");
                    }
                    if (success) {
                        System.out.println("Phone:");
                        String phone = scanner.nextLine();
                        System.out.println("Email");
                        String email = scanner.nextLine();
                        hospital.addStaff(new Person(id,staffUser,phone,email));
                    }
                }
                continue;
            }
            if (input.equals("2")) {
                hospital.listStaff();
                continue;
            }
            if (input.equals("3")) {
                System.out.println("WorkerID:");
                int workerID = Integer.parseInt(scanner.nextLine());
                hospital.displayStaff(workerID);
                continue;
            }
            if (input.equals("4")) {
                System.out.println("WorkerID:");
                int workerID = Integer.parseInt(scanner.nextLine());
                int personID = hospital.removeStaff(workerID);
                if (personID != -1) {
                    for (Account accountIteration : accounts) {
                        if (accountIteration.getId() == personID) {
                            accounts.remove(accountIteration);
                            break;
                        }
                    }
                }
            }
        }
    }

    public static boolean addAccount(Account newAccount) {
        boolean occupiedId = false;
        for (Account account : accounts)
            if (account.getId() == newAccount.getId()) occupiedId = true;
        if (!occupiedId) {
            accounts.add(newAccount);
            return true;
        }
        System.out.println("ID is taken.");
        return false;
    }

    public static void exit() throws IOException {
        FileWriter writer = new FileWriter("Accounts.csv");
        writer.write(csvParams);
        for (Account account : accounts)
            writer.write("\n"+account.returnCSVRecord());
        writer.close();
    }

}
