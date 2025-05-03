package baitap13;

import baitap13.service.MenuService;

public class Main {
	public static void main(String[] args) {
		
		// mvn clean install để tải về thư viện
		// Logic chính nằm ở menu service
		// Nhập lựa chọn 5 để xem dữ liệu mẫu
		// Dữ liệu được lưu trữ và các thao tác với dữ liệu (lấy model, lấy list model, add, xoá) nằm ở repo
		
		
		MenuService menuService = new MenuService();
		menuService.boot();
		
	}
}
