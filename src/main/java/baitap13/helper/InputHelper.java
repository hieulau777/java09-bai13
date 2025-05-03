package baitap13.helper;

import java.util.Scanner;

public class InputHelper {

    public static int checkInteger(String message, Scanner scanner) {
        while (true) {
            System.out.print(message);

            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                return value;
            } else {
                System.out.println("Giá trị nhập vào không phải là số nguyên. Vui lòng nhập lại.");
                scanner.next();
            }
        }
    }

    public static int checkStock(String message, Scanner scanner) {
        while (true) {
            System.out.print(message);

            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                if (value < 100) {
                    return value;
                } else {
                    System.out.println("Số lượng phải nhỏ hơn 100. Vui lòng nhập lại.");
                }
            } else {
                System.out.println("Giá trị nhập vào không phải là số nguyên. Vui lòng nhập lại.");
                scanner.next();
            }
        }
    }
}

