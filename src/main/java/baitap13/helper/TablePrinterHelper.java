package baitap13.helper;

import java.util.List;

import de.vandermeer.asciitable.AsciiTable;

public class TablePrinterHelper {

    public static <T> void printTable(List<T> list, String[] headers, String[] getters) {
        if (list == null || list.isEmpty()) {
            System.out.println("Danh sách trống.");
            return;
        }

        AsciiTable at = new AsciiTable();

        String[] newHeaders = new String[headers.length + 1];
        newHeaders[0] = "STT";
        System.arraycopy(headers, 0, newHeaders, 1, headers.length);

        at.addRule();
        at.addRow(newHeaders);
        at.addRule();

        for (int i = 0; i < list.size(); i++) {
            T obj = list.get(i);
            String[] rowData = extractData(obj, getters);

            String[] rowWithIndex = new String[rowData.length + 1];
            rowWithIndex[0] = String.valueOf(i + 1);
            System.arraycopy(rowData, 0, rowWithIndex, 1, rowData.length);

            at.addRow(rowWithIndex);
        }

        at.addRule();
        System.out.println(at.render());
    }

    public static <T> void printTableSingle(T obj, String[] headers, String[] getters) {
        if (obj == null) {
            System.out.println("Đối tượng trống.");
            return;
        }

        AsciiTable at = new AsciiTable();

        at.addRule();
        at.addRow(headers);
        at.addRule();

        String[] rowData = extractData(obj, getters);
        at.addRow(rowData);

        at.addRule();
        System.out.println(at.render());
    }

    private static <T> String[] extractData(T obj, String[] getters) {
        String[] rowData = new String[getters.length];

        try {
            for (int i = 0; i < getters.length; i++) {
                String getterMethod = "get" + capitalize(getters[i]);
                rowData[i] = String.valueOf(obj.getClass().getMethod(getterMethod).invoke(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rowData;
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty())
            return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
