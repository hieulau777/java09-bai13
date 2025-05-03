package baitap13.model;

public abstract class PersonModel {
	private static int idCounter = 0;
	private int id;
	private String name;
	private String phone;
	private int daysOfWork;
	
	protected PersonModel(){
		id = ++idCounter;
	};
	
	protected PersonModel(String name, String phone, int daysOfWork){
		id = ++idCounter;
		this.name = name;
		this.phone = phone;
		this.daysOfWork = daysOfWork;
	};
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getDaysOfWork() {
		return daysOfWork;
	}
	public void setDaysOfWork(int daysOfWork) {
		this.daysOfWork = daysOfWork;
	}
	public abstract int getMonthlySalary();
}
