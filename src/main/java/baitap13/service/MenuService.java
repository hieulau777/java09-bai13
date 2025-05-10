package baitap13.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import baitap13.Util;
import baitap13.helper.InputHelper;
import baitap13.helper.TablePrinterHelper;
import baitap13.model.CompanyInfo;
import baitap13.model.DirectorModel;
import baitap13.model.ManagerModel;
import baitap13.model.PersonModel;
import baitap13.model.StaffModel;
import baitap13.repo.PersonRepo;
import baitap13.repo.impl.PersonRepoImpl;
import de.vandermeer.asciitable.AsciiTable;

public class MenuService {

	private Scanner sc = new Scanner(System.in);

	private PersonRepo<PersonModel> personRepo = new PersonRepoImpl();
	private List<PersonModel> listPerson = personRepo.getAll();

	public void boot() {
		initData();
		boolean flag = true;
		while (flag) {
			Util.printMainMenu();
			int option = InputHelper.checkInteger("Nhập lựa chọn của bạn: ", sc);
			sc.nextLine();
			switch (option) {
				case 1:
					updateCompanyInfo();
					break;
				case 2:
					assignStaffToManager();
					break;
				case 3:
					addPerson();
					break;
				case 4:
					removePerson();
					break;
				case 5:
					printAllPerson();
					break;
				case 6:
					getCalculateTotalSalary();
					break;
				case 7:
					findStaffWithHighestSalary();
					break;
				case 8:
					findManagerWithHighestStaffNumber();
					break;
				case 9:
					sortByAbcPersons();
					break;
				case 10:
					sortByMonthlySalaryPersons();
					break;
				case 11:
					findDirectorWithHighestStock();
					break;
				case 12:
					getDirectorsIncome();
					break;
				case 13:
					flag = false;
					break;
				default:
					System.out.println("Lựa chọn không hợp lệ. Hãy nhập lại.");
					break;
				}
		}
	}

	private void updateCompanyInfo() {
		CompanyInfo company = CompanyInfo.getInstance();
		company.printCompanyInfo();
		System.out.println("Nhập tên công ty: ");
		String name = sc.nextLine();
		System.out.println("Nhập mã số thuế công ty: ");
		String taxCode = sc.nextLine();
		int income = InputHelper.checkInteger("Nhập doanh thu công ty: ", sc);
		company.setName(name);
		company.setTaxCode(taxCode);
		company.setIncome(income);
		company.printCompanyInfo();
	}

	private void assignStaffToManager() {
		int staffIdInput;
		int previousManagerId = 0;
		int managerIdInput;
		StaffModel staff;
		ManagerModel manager;
		while (true) {
			try {
				staffIdInput = InputHelper.checkInteger("Nhập id nhân viên: ", sc);
				staff = (StaffModel) personRepo.findById(staffIdInput);
				if (staff.getManagerId() != null) {
					previousManagerId = staff.getManagerId();
				}
				break;
			} catch (Exception e) {
				System.out.println("Id nhân viên không tồn tại!");
				sc.nextLine();
			}
		}
		while (true) {
			try {
				managerIdInput = InputHelper.checkInteger("Nhập id quản lý: ", sc);
				manager = (ManagerModel) personRepo.findById(managerIdInput);
				if (managerIdInput == previousManagerId) {
					System.out.println("Nhân viên '" + staff.getName() + "' đã được phân bổ cho quản lý '"
							+ manager.getName() + "'. Hãy nhập quản lý khác.");
					continue;
				}
				break;
			} catch (Exception e) {
				System.out.println("Id quản lý không tồn tại!");
				sc.nextLine();
			}
		}
		// Nếu nhân viên được quản lý trước đó, giảm số lượng nhân viên quản lý trước
		// khi set id quản lý mới
		if (previousManagerId != 0) {
			ManagerModel previousManager = (ManagerModel) personRepo.findById(previousManagerId);
			previousManager.setStaffNumber(previousManager.getStaffNumber() - 1);
		}
		staff.setManagerId(managerIdInput);
		manager.setStaffNumber(manager.getStaffNumber() + 1);

	}

	private void addPerson() {
		boolean flag = true;
		while (flag) {
		    System.out.println("1. Nhân viên");
		    System.out.println("2. Quản lý");
		    System.out.println("3. Giám đốc");
		    System.out.println("4. Thoát");

		    int option = InputHelper.checkInteger("Nhập lựa chọn của bạn: ", sc);
		    sc.nextLine();

		    switch (option) {
		        case 1: {
		            System.out.println("Nhập họ tên: ");
		            String name = sc.nextLine();
		            System.out.println("Nhập số điện thoại: ");
		            String phone = sc.nextLine();
		            int daysOfWork = InputHelper.checkInteger("Nhập số ngày làm việc: ", sc);
		            personRepo.add(new StaffModel(name, phone, daysOfWork));
		            break;
		        }

		        case 2: {
		            System.out.println("Nhập họ tên: ");
		            String name = sc.nextLine();
		            System.out.println("Nhập số điện thoại: ");
		            String phone = sc.nextLine();
		            int daysOfWork = InputHelper.checkInteger("Nhập số ngày làm việc: ", sc);
		            personRepo.add(new ManagerModel(name, phone, daysOfWork));
		            break;
		        }

		        case 3: {
		            System.out.println("Nhập họ tên: ");
		            String name = sc.nextLine();
		            System.out.println("Nhập số điện thoại: ");
		            String phone = sc.nextLine();
		            int daysOfWork = InputHelper.checkInteger("Nhập số ngày làm việc: ", sc);
		            int stock = InputHelper.checkStock("Nhập cổ phần: ", sc);
		            personRepo.add(new DirectorModel(name, phone, daysOfWork, stock));
		            break;
		        }
		        
		        case 4: {
		            flag = false;
		            break;
		        }

		        default:
		            System.out.println("Lựa chọn không hợp lệ. Hãy nhập lại.");
		            break;
		    }
		}

	}

	private void removePerson() {

		int personId = InputHelper.checkInteger("Nhập id nhân sự: ", sc);
		for (PersonModel personModel : listPerson) {
			// Nếu người được xoá là Staff thì giảm số lượng staff của manager
			if (personModel instanceof StaffModel) {
				StaffModel staff = (StaffModel) personModel;
				if (staff.getManagerId() != null) {
					ManagerModel manager = (ManagerModel) personRepo.findById(staff.getManagerId());
					manager.setStaffNumber(manager.getStaffNumber() - 1);
				}
			}
			// Nếu người được xoá là manager thì reset manager id của staff
			if (personModel instanceof ManagerModel) {
				ManagerModel manager = (ManagerModel) personModel;
				if (manager.getStaffNumber() > 0) {
					List<StaffModel> listStaffByManagerId = findStaffsByManagerId(personId);
					for (StaffModel staff : listStaffByManagerId) {
						staff.setManagerId(null);
					}
				}
			}
		}

		personRepo.remove(personId);
	}

	public List<StaffModel> findStaffsByManagerId(int managerId) {
		List<StaffModel> allStaff = personRepo.getAllStaff();
		List<StaffModel> result = new ArrayList<>();

		for (StaffModel staff : allStaff) {
			if (staff.getManagerId() != null && staff.getManagerId() == managerId) {
				result.add(staff);
			}
		}
		return result;
	}

	private void printAllPerson() {
		List<StaffModel> listStaff = personRepo.getAllStaff();
		List<ManagerModel> listManager = personRepo.getAllManager();
		List<DirectorModel> listDirector = personRepo.getAllDirector();

		String[] staffHeaders = { "ID", "Tên", "Số điện thoại", "Id quản lý" };
		String[] staffGetters = { "id", "name", "phone", "managerId" };
		String[] managerHeaders = { "ID", "Tên", "Số điện thoại", "Số lượng nhân viên quản lý" };
		String[] managerGetters = { "id", "name", "phone", "staffNumber" };
		String[] directorHeaders = { "ID", "Tên", "Số điện thoại", "Cổ phần" };
		String[] directorGetters = { "id", "name", "phone", "stock" };
		System.out.println("Nhân viên: ");
		TablePrinterHelper.printTable(listStaff, staffHeaders, staffGetters);
		System.out.println("Quản lý: ");
		TablePrinterHelper.printTable(listManager, managerHeaders, managerGetters);
		System.out.println("Giám đốc: ");
		TablePrinterHelper.printTable(listDirector, directorHeaders, directorGetters);

	}

	private void getCalculateTotalSalary() {
		System.out.println("Tổng lương toàn công ty: " + calculateTotalSalary());
	}

	private int calculateTotalSalary() {
		int totalSalary = 0;
		for (PersonModel person : listPerson) {
			totalSalary += person.getMonthlySalary();
		}
		return totalSalary;
	}

	private void findStaffWithHighestSalary() {
		List<StaffModel> listStaff = personRepo.getAllStaff();
		StaffModel highestSalaryStaff = listStaff.get(0);
		for (int i = 1; i < listStaff.size(); i++) {
			if (listStaff.get(i).getMonthlySalary() > highestSalaryStaff.getMonthlySalary()) {
				highestSalaryStaff = listStaff.get(i);
			}
		}
		String[] staffHeaders = { "ID", "Tên", "Số điện thoại", "Lương tháng" };
		String[] staffGetters = { "id", "name", "phone", "monthlySalary" };
		TablePrinterHelper.printTableSingle(highestSalaryStaff, staffHeaders, staffGetters);
	}

	private void findManagerWithHighestStaffNumber() {
		List<ManagerModel> listManager = personRepo.getAllManager();
		ManagerModel highestStaffNumberManager = listManager.get(0);
		for (int i = 1; i < listManager.size(); i++) {
			if (listManager.get(i).getStaffNumber() > highestStaffNumberManager.getStaffNumber()) {
				highestStaffNumberManager = listManager.get(i);
			}
		}
		String[] managerHeaders = { "ID", "Tên", "Số điện thoại", "Số lượng nhân viên dưới quyền" };
		String[] managerGetters = { "id", "name", "phone", "staffNumber" };
		TablePrinterHelper.printTableSingle(highestStaffNumberManager, managerHeaders, managerGetters);
	}

	private void sortByAbcPersons() {
		List<PersonModel> sortedList = new ArrayList<>(listPerson);
		Collections.sort(sortedList, (p1, p2) -> p1.getName().compareTo(p2.getName()));
		String[] personHeaders = { "ID", "Tên", "Số điện thoại" };
		String[] personGetters = { "id", "name", "phone" };
		TablePrinterHelper.printTable(sortedList, personHeaders, personGetters);
	}

	private void sortByMonthlySalaryPersons() {
		List<PersonModel> sortedList = new ArrayList<>(listPerson);
		Collections.sort(sortedList, (p1, p2) -> Integer.compare(p2.getMonthlySalary(), p1.getMonthlySalary()));
		String[] personHeaders = { "ID", "Tên", "Số điện thoại", "Lương tháng" };
		String[] personGetters = { "id", "name", "phone", "monthlySalary" };
		TablePrinterHelper.printTable(sortedList, personHeaders, personGetters);
	}

	private void findDirectorWithHighestStock() {
		List<DirectorModel> listDirector = personRepo.getAllDirector();
		DirectorModel highestStaffNumberManager = listDirector.get(0);
		for (int i = 1; i < listDirector.size(); i++) {
			if (listDirector.get(i).getStock() > highestStaffNumberManager.getStock()) {
				highestStaffNumberManager = listDirector.get(i);
			}
		}
		String[] directorHeaders = { "ID", "Tên", "Số điện thoại", "Số cổ phần" };
		String[] directorGetters = { "id", "name", "phone", "stock" };
		TablePrinterHelper.printTableSingle(highestStaffNumberManager, directorHeaders, directorGetters);
	}

	private void getDirectorsIncome() {
		List<DirectorModel> listDirector = personRepo.getAllDirector();
		AsciiTable at = new AsciiTable();
		String[] headers = { "STT", "ID", "Tên", "Số điện thoại", "Thu nhập" };
		at.addRule();
		at.addRow(headers);
		at.addRule();
		for (int i = 0; i < listDirector.size(); i++) {
			DirectorModel director = listDirector.get(i);
			int income = director.getMonthlySalary() + director.getStock() * calculateTotalSalary();
			String[] rowData = { String.valueOf(i + 1), String.valueOf(director.getId()), director.getName(),
					director.getPhone(), String.valueOf(income) };
			at.addRow(rowData);
		}

		at.addRule();
		System.out.println(at.render());
	}

	private void initData() {
		CompanyInfo defaultCompany = CompanyInfo.getInstance();
		defaultCompany.setName("ABC Corp");
		defaultCompany.setTaxCode("123456789");
		defaultCompany.setIncome(1000000);

		List<StaffModel> defaultListStaff = new ArrayList<StaffModel>();
		defaultListStaff.add(new StaffModel("Ngọc Kem", "090", 200));
		defaultListStaff.add(new StaffModel("Pháo", "091", 30));
		defaultListStaff.add(new StaffModel("Ngân Sát Thủ", "092", 1000));
		defaultListStaff.add(new StaffModel("Chị Phiến", "093", 1));

		List<ManagerModel> defaultListManager = new ArrayList<ManagerModel>();
		defaultListManager.add(new ManagerModel("Cô trợ lý", "094", 200));
		defaultListManager.add(new ManagerModel("Peter", "095", 30));

		List<DirectorModel> defaultListDirector = new ArrayList<DirectorModel>();
		defaultListDirector.add(new DirectorModel("Viruss", "096", 200, 90));
		defaultListDirector.add(new DirectorModel("Thầy giáo Ba", "097", 30, 10));

		for (StaffModel p : defaultListStaff) {
			personRepo.add(p);
		}

		for (ManagerModel p : defaultListManager) {
			personRepo.add(p);
		}

		for (DirectorModel p : defaultListDirector) {
			personRepo.add(p);
		}
	}
}
