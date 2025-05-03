package baitap13.model;

public class DirectorModel extends PersonModel {
	final private int dailySalary = 200;
	private int stock;
	
	public DirectorModel(){
		super();
	}
	
	public DirectorModel(String name, String phone, int daysOfWork, int stock){
		super(name, phone, daysOfWork);
		this.stock = stock;
	}
	
	public int getMonthlySalary() {
		return calculateMonthlySalary();
	}
	
	private int calculateMonthlySalary() {
		return super.getDaysOfWork() * dailySalary;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
}
