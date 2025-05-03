package baitap13.model;


public class CompanyInfo {
	private static final CompanyInfo INSTANCE_COMPANY_INFO = new CompanyInfo();
	private String name;
	private String taxCode;
	private int income;
	
	private CompanyInfo(){};
	
    public static CompanyInfo getInstance() {
        return INSTANCE_COMPANY_INFO;
    }
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTaxCode() {
		return taxCode;
	}
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	public int getIncome() {
		return income;
	}
	public void setIncome(int income) {
		this.income = income;
	}
	
	public void printCompanyInfo() {
		System.out.println(this.getName());
		System.out.println(this.getTaxCode());
		System.out.println(this.getIncome());
	}
}
