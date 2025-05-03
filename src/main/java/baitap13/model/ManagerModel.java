package baitap13.model;

public class ManagerModel extends PersonModel {
	final private int dailySalary = 200;
	private int staffNumber = 0;
	
	public void setStaffNumber(int staffNumber) {
		this.staffNumber = staffNumber;
	}

	public ManagerModel(){
		super();
	}
	
	public ManagerModel(String name, String phone, int daysOfWork){
		super(name, phone, daysOfWork);
	}
	
	public int getMonthlySalary() {
		return calculateMonthlySalary();
	}
	
	private int calculateMonthlySalary() {
		return super.getDaysOfWork() * dailySalary;
	}

	public int getStaffNumber() {
		return staffNumber;
	}

	public int getDailySalary() {
		return dailySalary;
	}
	
}
