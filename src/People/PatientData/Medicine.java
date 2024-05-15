package People.PatientData;

public class Medicine {
	
	private final String brand;
	private final String name;
	private final float intakeTime;

	public Medicine(String brand, String name, float inTakePriority) {
		  this.brand = brand;
		  this.name = name;
		  
		  if (inTakePriority < 0.0f || inTakePriority >= 24.0f) {
		    throw new IllegalArgumentException("please valid time (00.00-23.59).");
		  }
		  
		  this.intakeTime = inTakePriority;
	}
	
	public String getBrand() {
		return brand;
	}

	public String getName() {
		return name;
	}

	public float getIntakeTime() { return intakeTime; }
	
	
}
