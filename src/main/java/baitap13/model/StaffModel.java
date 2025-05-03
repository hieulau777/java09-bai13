package baitap13.model;

public class StaffModel extends PersonModel {
	final private int dailySalary = 100;
	private Integer managerId;
	
	public StaffModel(){
		super();
	}
	
	public StaffModel(String name, String phone, int daysOfWork){
		super(name, phone, daysOfWork);
	}
	
	public int getMonthlySalary() {
		return calculateMonthlySalary();
	}
	
	private int calculateMonthlySalary() {
		return super.getDaysOfWork() * dailySalary;
	}

	public int getDailySalary() {
		return dailySalary;
	}

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
}
