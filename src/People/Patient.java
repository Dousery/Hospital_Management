package People;

import java.util.Comparator;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.PriorityQueue;

import People.PatientData.Appointment;
import People.PatientData.Medicine;
import People.PatientData.Operation;
import People.PatientData.Test;

public class Patient extends Person {
	private final ArrayList<Appointment> appointments;
	private final ArrayList<Test>tests;
	private final PriorityQueue<Medicine> prescribedMedicines;
	private final ArrayList<Operation> operations;

	public ArrayList<Operation> getOperations() {
		return operations;
	}

	public Patient(Person person) {
		super(person.getID(), person.getName(), person.getPhone(), person.getEmail());
		this.appointments = new ArrayList<>();
		this.tests = new ArrayList<>();
		this.prescribedMedicines = new PriorityQueue<>(new MedicineComparator());
		this.operations = new ArrayList<>();
	}
	
	private Date parseDateTime(String dateTimeStr) {
	    try {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
	        return dateFormat.parse(dateTimeStr);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	

	public ArrayList<Appointment> getAppointments() {
		return appointments;
	}

	public void archiveAppointment(Appointment appointment) {
		appointments.add(appointment);
	}

	public void addAppointment(Appointment appointment) {
		
	    String appointmentDateTime = appointment.getDate();
	    Date newDateTime = parseDateTime(appointmentDateTime);

	    int index = 0;
	    for (Appointment existingAppointment : appointments) {
	        String existingAppointmentDateTime = existingAppointment.getDate();
	        Date existingDateTime = parseDateTime(existingAppointmentDateTime);

			assert newDateTime != null;
			if (newDateTime.compareTo(existingDateTime) < 0) {
	            break;
	        } else if (newDateTime.compareTo(existingDateTime) == 0) {
	            String newTime = appointmentDateTime.split(" ")[1];
	            String existingTime = existingAppointmentDateTime.split(" ")[1];
	            if (newTime.compareTo(existingTime) < 0) {
	                break;
	            }
	        }
	        index++;
	    }

	    appointments.add(index, appointment);
	}

	public ArrayList<Test> getTests() {
		return tests;
	}

	public void addTest(Test test) {
		tests.add(test);
	}

	public PriorityQueue<Medicine> getPrescribedMedicines() {
		return prescribedMedicines;
	}

	public void addPrescribedMedicine(Medicine prescribedMedicine) {
		prescribedMedicines.add(prescribedMedicine);
	}
	
	public void removePrescribedMedicine(Medicine prescribedMedicine) {		
        boolean isMedicineFound = false;
        for (Medicine med : prescribedMedicines) {
            if (med.getBrand().equals(prescribedMedicine.getBrand()) && med.getName().equals(prescribedMedicine.getName())) {
                isMedicineFound = true;
                prescribedMedicines.remove(med);
                break;
            }
        }
        
        if (!isMedicineFound) {
            System.out.println("medicine is not found");
        }
	}
	
	public void viewMedicalPortfolio() {
        System.out.println("People.Patient information:");
        System.out.println("ID: " + super.getID());
        System.out.println("Name: " + super.getName());
        System.out.println("Phone: " + super.getPhone());
        System.out.println("Email: " + super.getEmail());

        System.out.println("\nPast Appointments:");
        for (Appointment appointment : appointments) {
            System.out.println("Appointment No : "+appointment.getAppointmentNo() + " -  Doctor to Appointment : " + appointment.getAssignedDoctor().getName());
			System.out.println("Notes: "+ appointment.getNotes());
        }

        System.out.println("\nPast Tests:");
        for (Test test : tests) {
            System.out.println("Test Type : " + test.getTestType() + " -  Test Date : " + test.getDate() + " -  Test Result : " + test.getTestResult());
        }

        System.out.println("\nPrescribed Medicines:");
        for (Medicine medicine : prescribedMedicines) {
            System.out.println("Medicine Name : "+ medicine.getName() + " -  Medicine Brand : " + medicine.getBrand());
        }

		System.out.println("\nOperations:");
		for (Operation operation : operations) {
			System.out.println("Operation Type :"+ operation.getOperationType()+" - Operation Date: "+operation.getDate()+"\nOperation Notes:"+operation.getNotes());
		}
    }


	public void addOperation(Operation newOperation) {
		operations.add(newOperation);
	}
}

class MedicineComparator implements Comparator<Medicine> {
	public int compare(Medicine m1, Medicine m2) {
		if (m1.getIntakeTime() < m2.getIntakeTime())
			return 1;
		else if (m1.getIntakeTime() > m2.getIntakeTime())
			return -1;
		return 0;
	}
}