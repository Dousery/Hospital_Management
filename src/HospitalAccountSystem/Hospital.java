package HospitalAccountSystem;

import People.Patient;
import People.PatientData.*;
import People.Person;
import People.Staff.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Hospital implements PatientUserInterface,StaffUserInterface,AdministratorInterface {
    String name;
    private final ArrayList<Patient> patients;
    private final ArrayList<Staff> staff;
    private int totalAppointments;
    private int totalTests;
    private final ArrayList<Appointment> pendingAppointments;

    private final Scanner sc = new Scanner(System.in);

    public Hospital() throws FileNotFoundException {
        patients = new ArrayList<>();
        staff = new ArrayList<>();
        pendingAppointments = new ArrayList<>();
        totalAppointments = 0;
        totalTests = 0;

        loadPatients();
        loadStaff();
        loadAppointments();
        loadTests();
        loadMedicine();
        loadOperations();
    }

    private void loadOperations() throws FileNotFoundException {
        Scanner fileSc = new Scanner(new File("Operations.dat"));
        fileSc.nextLine();
        fileSc.nextLine();
        while (fileSc.hasNextLine()) {
            String[] parsed = fileSc.nextLine().split(",");
            Patient patient = null;
            int patientID = Integer.parseInt(parsed[0]);
            for (Patient patientIteration : patients) {
                if (patientID == patientIteration.getID()) {
                    patient = patientIteration;
                }
            }

            Surgeon surgeon = null;
            int SurgeonID = Integer.parseInt(parsed[3]);
            for (Staff surgeonIteration : staff) {
                if (surgeonIteration.getWorkerID() == SurgeonID) {
                    surgeon = (Surgeon) surgeonIteration;
                }
            }

            String notes = fileSc.nextLine();
            assert patient != null : "Patient ID not found";
            assert surgeon != null : "Scientist ID not found";
            Operation newOperation = new Operation(patient,surgeon,parsed[2],parsed[1],notes);
            surgeon.addOperations(newOperation);
            patient.addOperation(newOperation);
        }
    }

    public void addOperation(int PatientID,int WorkerID) {
        for (Patient patient : patients) {
            if (patient.getID() == PatientID) {
                for (Staff surgeon : staff) {
                    if (surgeon.getWorkerID() == WorkerID) {
                        System.out.println("Operation Type: ");
                        String operationType = sc.nextLine();
                        System.out.println("Date: ");
                        String date = sc.nextLine();
                        System.out.println("Notes: ");
                        String notes = sc.nextLine();
                        patient.addOperation(new Operation(patient, (Surgeon) surgeon,operationType,date,notes));
                    }
                }
                System.out.println("Surgeon not found");
                return;
            }
        }
        System.out.println("Patient not found");
    }

    private void loadMedicine() throws FileNotFoundException {
        Scanner fileSc = new Scanner(new File("Medicine.dat"));
        fileSc.nextLine();
        while(fileSc.hasNext()) {
            String[] parsed = fileSc.nextLine().split(",");
            int patientID = Integer.parseInt(parsed[0]);
            for (Patient patient : patients) {
                if (patient.getID() == patientID) {
                    for (int i = 1; i < parsed.length; i+=3)
                    patient.addPrescribedMedicine(new Medicine(parsed[i+1],parsed[i],Float.parseFloat(parsed[i+2])));
                }
            }
        }
    }

    private void loadTests() throws FileNotFoundException {
        Scanner fileSc = new Scanner(new File("Tests.dat"));
        fileSc.nextLine();
        fileSc.nextLine();
        while (fileSc.hasNextLine()) {
            String[] parsed = fileSc.nextLine().split(",");
            Patient patient = null;
            int patientID = Integer.parseInt(parsed[1]);
            for (Patient patientIteration : patients) {
                if (patientID == patientIteration.getID()) {
                    patient = patientIteration;
                }
            }

            Scientist scientist = null;
            int ScientistID = Integer.parseInt(parsed[4]);
            for (Staff scientistIteration : staff) {
                if (scientistIteration.getWorkerID() == ScientistID) {
                    scientist = (Scientist) scientistIteration;
                }
            }

            String result = fileSc.nextLine();
            assert patient != null : "Patient ID not found";
            assert scientist != null : "Scientist ID not found";
            totalTests++;
            Test newTest = new Test(Integer.parseInt(parsed[0]),parsed[3],result,parsed[2],patient,scientist);
            scientist.addTest(newTest);
            patient.addTest(newTest);
        }
    }

    public void addTest(int patientID,int WorkerID) {
        for (Patient patient : patients) {
            if (patient.getID() == patientID) {
                for (Staff scientist : staff) {
                    if (scientist instanceof Scientist && scientist.getWorkerID() == WorkerID) {
                        System.out.println("Test type:");
                        String testType = sc.nextLine();
                        System.out.println("Date:");
                        String date = sc.nextLine();
                        totalTests++;
                        Test test = new Test(totalTests,testType,"None",date,patient, (Scientist) scientist);
                        patient.addTest(test);
                        return;
                    }
                }
                return;
            }
        }
    }

    public void viewTests() {
        for (Patient patient : patients) {
            for (Test test : patient.getTests()) {
                test.display();
            }
        }
    }

    public void concludeTest(int testNo) {
        for (Patient patient : patients) {
            for (Test test : patient.getTests()) {
                if (test.getTestNo() == testNo) {
                    System.out.println("Result:");
                    test.setResult(sc.nextLine());
                    return;
                }
            }
        }
        System.out.println("Test not found");
    }

    private void loadAppointments() throws FileNotFoundException {
        Scanner fileSc = new Scanner(new File("Appointments.dat"));
        fileSc.nextLine();
        fileSc.nextLine();
        while (fileSc.hasNextLine()) {
            String[] parsed = fileSc.nextLine().split(",");

            Doctor doctor = null;
            int doctorID = Integer.parseInt(parsed[1]);
            for (Staff doctorIteration : staff) {
                if (doctorIteration.getWorkerID() == doctorID) {
                    doctor = (Doctor) doctorIteration;
                }
            }

            Patient patient = null;
            int patientID = Integer.parseInt(parsed[2]);
            for (Patient patientIteration : patients) {
                if (patientIteration.getID() == patientID) {
                    patient = patientIteration;
                }
            }

            Appointment appointmentToAdd = new Appointment(Integer.parseInt(parsed[0]),parsed[3],patient,doctor);
            appointmentToAdd.setNotes(fileSc.nextLine());

            switch (parsed[4]) {
                case "Pending" -> {
                    pendingAppointments.add(appointmentToAdd);
                    totalAppointments++;
                }
                case "Scheduled" -> {
                    appointmentToAdd.setAppointmentStatus(AppointmentStatus.Scheduled);
                    assert doctor != null : "Worker ID not found";
                    doctor.addAppointment(appointmentToAdd);
                    totalAppointments++;
                }
                case "Done" -> {
                    appointmentToAdd.setAppointmentStatus(AppointmentStatus.Done);
                    assert patient != null : "Patient ID not found";
                    patient.addAppointment(appointmentToAdd);
                    totalAppointments++;
                }
                case "Cancelled" -> {
                    appointmentToAdd.setAppointmentStatus(AppointmentStatus.Cancelled);
                    assert patient != null : "Patient ID not found";
                    patient.addAppointment(appointmentToAdd);
                    totalAppointments++;
                }
            }
        }
    }

    private void loadStaff() throws FileNotFoundException {
        Scanner fileSc = new Scanner(new File("Staff.csv"));
        fileSc.nextLine();
        while (fileSc.hasNextLine()) {
            String[] parsed = fileSc.nextLine().split(",");
            staff.add(StaffFactory.newStaff(new Person(Integer.parseInt(parsed[0]), parsed[1], parsed[2], parsed[3]), Integer.parseInt(parsed[4]),parsed[5]));
        }
    }

    public void loadPatients() throws FileNotFoundException {
        Scanner fileSc = new Scanner(new File("Patients.csv"));
        fileSc.nextLine();
        while (fileSc.hasNextLine()) {
            String[] parsed = fileSc.nextLine().split(",");
            patients.add(new Patient(new Person(Integer.parseInt(parsed[0]),parsed[1],parsed[2],parsed[3])));
        }
    }

    public void bookAppointment(Patient patient) {
        displayDoctors();
        System.out.println("Enter doctor ID");
        int selectedID = Integer.parseInt(sc.nextLine());
        for (Staff doctor : staff) {
            if (doctor instanceof Doctor) {
                if (doctor.getWorkerID() == selectedID) {
                    System.out.print("Date: ");
                    String Date = sc.nextLine();
                    totalAppointments++;
                    pendingAppointments.add(new Appointment(totalAppointments, Date, patient, (Doctor) doctor));
                    return;
                }
            }
        }
        System.out.println("Doctor ID not found. Cancelled booking.");
    }

    public void viewPendingAppointments() {
        for (Appointment appointment : pendingAppointments) {
            appointment.display();
        }
    }

    public void viewPatientAppointments(int ID) {
        for (Appointment appointment : pendingAppointments) {
            if (appointment.getPatient().getID() == ID) {
                appointment.display();
            }
        }
        for (Staff doctor : staff) {
            if (doctor instanceof Doctor) {
                for (Appointment appointment : ((Doctor) doctor).getScheduledAppointments()) {
                    if (appointment.getPatient().getID() == ID) {
                        appointment.display();
                    }
                }
            }
        }
    }

    public void approveAppointment(int appointmentID) {
        for (Appointment appointment : pendingAppointments) {
            if (appointment.getAppointmentNo() == appointmentID) {
                appointment.setAppointmentStatus(AppointmentStatus.Scheduled);
                pendingAppointments.remove(appointment);
                appointment.getAssignedDoctor().addAppointment(appointment);
                return;
            }
        }
        System.out.println("Appointment not found.");
    }

    public void cancelAppointment(int appointmentID) {
        for (Appointment appointment : pendingAppointments) {
            if (appointment.getAppointmentNo() == appointmentID) {
                appointment.setAppointmentStatus(AppointmentStatus.Cancelled);
                pendingAppointments.remove(appointment);
                appointment.getPatient().addAppointment(appointment);
                return;
            }
        }
        for (Staff doctor : staff) {
            if (doctor instanceof Doctor) {
                for (Appointment appointment : ((Doctor) doctor).getScheduledAppointments()) {
                    if (appointment.getAppointmentNo() == appointmentID) {
                        appointment.setAppointmentStatus(AppointmentStatus.Cancelled);
                        ((Doctor) doctor).getScheduledAppointments().remove(appointment);
                        appointment.getPatient().addAppointment(appointment);
                        return;
                    }
                }
            }
        }
        System.out.println("Appointment not found.");
    }

    public void recordAppointment(int appointmentID,int DoctorID) {
        for (Staff doctor : staff) {
            if (doctor.getWorkerID() == DoctorID) {
                for (Appointment appointment : ((Doctor) doctor).getScheduledAppointments()) {
                    if (appointment.getAppointmentNo() == appointmentID) {
                        appointment.setAppointmentStatus(AppointmentStatus.Done);
                        ((Doctor) doctor).getScheduledAppointments().remove(appointment);
                        System.out.println("Notes >");
                        appointment.setNotes(sc.nextLine());
                        appointment.getPatient().archiveAppointment(appointment);
                        return;
                    }
                }
            }
        }
        System.out.println("Appointment not found.");
    }

    public Appointment getPendingAppointment(int appointmentID) {
        for (Appointment appointment : pendingAppointments) {
            if (appointment.getAppointmentNo() == appointmentID && appointment.getAppointmentStatus() == AppointmentStatus.Pending) {
                return appointment;
            }
        }
        System.out.println("Appointment not found");
        return null;
    }

    public void displayPatientData(int ID) {
        for (Patient patient : patients) {
            if (patient.getID() == ID) {
                patient.viewMedicalPortfolio();
            }
        }
    }

    public Patient getPatient(int id) {
        for (Patient patient : patients) {
            if (patient.getID() == id) {
                return patient;
            }
        }
        System.out.println("Patient not found!");
        return null;
    }

    public void displayDoctors() {
        for (Staff doctor : staff) {
            if (doctor instanceof Doctor) {
                System.out.println(doctor.getName()+" - "+doctor.getWorkerID());
            }
        }
    }

    public int getWorkerID(int PersonID) {
        for (Staff iteration : staff)
            if (iteration.getID() == PersonID)
                return iteration.getWorkerID();
        return 0;
    }

    public void displayStaff(int WorkerID) {
        for (Staff iteration : staff) {
            if (iteration.getWorkerID() == WorkerID) {
                iteration.displayStaffData();
                iteration.viewMedicalHistory();
            }
        }
    }

    public void listPatients() {
        for (Patient patient : patients) {
            System.out.println(patient.getID()+" - "+patient.getName());
        }
    }

    public void prescribeMedicine(int patientID) {
        for (Patient patient : patients) {
            if (patient.getID() == patientID) {
                System.out.println("Brand: ");
                String brand = sc.nextLine();
                System.out.println("Name: ");
                String name = sc.nextLine();
                System.out.println("Intake Time: ");
                float time = Float.parseFloat(sc.nextLine());
                Medicine medicine = new Medicine(brand,name,time);
                patient.addPrescribedMedicine(medicine);
            }
        }
        System.out.println("Patient not found");
    }

    public void addStaff(Person newPerson) {
        int workerID;
        while (true) {
            System.out.println("WorkerID:");
            workerID = Integer.parseInt(sc.nextLine());
            boolean identical = false;
            for (Staff iteration : staff) {
                if (workerID == iteration.getWorkerID()) {
                    identical = true;
                    break;
                }
            }
            if (!identical) {
                break;
            }
            else System.out.println("ID taken");
        }
        while (true) {
            System.out.println("1. Doctor");
            System.out.println("2. Surgeon");
            System.out.println("3. Scientist");
            String input = sc.nextLine();
            if (input.equals("1")) {
                staff.add(StaffFactory.newStaff(newPerson,workerID,"Doctor"));
                break;
            }
            if (input.equals("2")) {
                staff.add(StaffFactory.newStaff(newPerson,workerID,"Surgeon"));
                break;
            }
            if (input.equals("3")) {
                staff.add(StaffFactory.newStaff(newPerson,workerID,"Scientist"));
                break;
            }
        }
    }

    public void listStaff() {
        for (Staff iteration : staff) {
            if (iteration instanceof Doctor) {
                System.out.println("Doctor "+iteration.getName());
            }
            else if (iteration instanceof Scientist) {
                System.out.println("Scientist "+iteration.getName());
            }
            else if (iteration instanceof Surgeon) {
                System.out.println("Surgeon " + iteration.getName());
            }
            System.out.println(iteration.getWorkerID()+" - "+iteration.getPhone()+" - "+iteration.getEmail());
        }
    }

    public int removeStaff(int workerID) throws IOException {
        for (Staff iteration : staff) {
            if (iteration.getWorkerID() == workerID) {
                int out = iteration.getID();
                staff.remove(iteration);
                save();
                return out;
            }
        }
        System.out.println("Staff not found");
        return -1;
    }

    public void save() throws IOException {
        FileWriter fileWriter = new FileWriter("Patients.csv");
        fileWriter.write("ID,Name,Phone,Email");
        for (Patient patient : patients) {
            fileWriter.write("\n"+patient.getID()+","+patient.getName()+","+patient.getPhone()+","+patient.getEmail());
        }
        fileWriter.close();

        fileWriter = new FileWriter("Appointments.dat");
        fileWriter.write("AppointmentNo,DoctorID,PatientID,Date,Status");
        fileWriter.write("\nNotes");
        for (Appointment appointment : pendingAppointments) {
            fileWriter.write("\n"+appointment.getAppointmentNo()+","+appointment.getAssignedDoctor().getWorkerID()+","+appointment.getPatient().getID()+","+appointment.getDate()+","+appointment.getStatusString());
            fileWriter.write("\n"+appointment.getNotes());
        }
        for (Staff doctor : staff) {
            if (doctor instanceof Doctor) {
                for (Appointment appointment : ((Doctor) doctor).getScheduledAppointments()) {
                    fileWriter.write("\n"+appointment.getAppointmentNo()+","+appointment.getAssignedDoctor().getWorkerID()+","+appointment.getPatient().getID()+","+appointment.getDate()+","+appointment.getStatusString());
                    fileWriter.write("\n"+appointment.getNotes());
                }
            }
        }
        for (Patient patient : patients) {
            for (Appointment appointment : patient.getAppointments()) {
                fileWriter.write("\n"+appointment.getAppointmentNo()+","+appointment.getAssignedDoctor().getWorkerID()+","+appointment.getPatient().getID()+","+appointment.getDate()+","+appointment.getStatusString());
                fileWriter.write("\n"+appointment.getNotes());
            }
        }
        fileWriter.close();

        fileWriter = new FileWriter("Staff.csv");
        fileWriter.write("ID,Name,Phone,Email,WorkerID,Occupation");
        for (Staff iteration : staff) {
            fileWriter.write("\n"+iteration.getID()+","+iteration.getName()+","+iteration.getPhone()+","+iteration.getEmail()+","+iteration.getWorkerID()+",");
            if (iteration instanceof Doctor) fileWriter.write("Doctor");
            else if (iteration instanceof Surgeon) fileWriter.write("Surgeon");
            else if (iteration instanceof Scientist) fileWriter.write("Scientist");
        }
        fileWriter.close();

        fileWriter = new FileWriter("Operations.dat");
        fileWriter.write("PatientID,Date,OperationType,SurgeonID\n" +
                "Notes");
        for (Patient patient : patients) {
            for (Operation operation : patient.getOperations()) {
                fileWriter.write("\n"+operation.getPatient().getID()+","+operation.getDate()+","+operation.getOperationType()+","+operation.getSurgeon().getWorkerID());
                fileWriter.write("\n"+operation.getNotes());
            }
        }
        fileWriter.close();

        fileWriter = new FileWriter("Tests.dat");
        fileWriter.write("TestNo,PatientID,Date,TestType,ScientistID\n" +
                "Result");
        for (Patient patient : patients) {
            for (Test test : patient.getTests()) {
                fileWriter.write("\n"+test.getTestNo()+","+test.getPatient().getID()+","+test.getDate()+","+test.getTestType()+","+test.getScientist().getWorkerID());
                fileWriter.write("\n"+test.getResult());
            }
        }
        fileWriter.close();

        fileWriter = new FileWriter("Medicine.dat");
        fileWriter.write("PatientID,MedicineName,MedicineBrand,intakeTime,...");
        for (Patient patient : patients) {
            fileWriter.write("\n"+patient.getID());
            for (Medicine medicine : patient.getPrescribedMedicines()) {
                fileWriter.write(","+medicine.getName()+","+medicine.getBrand()+","+medicine.getIntakeTime());
            }
        }
        fileWriter.close();
    }


}
