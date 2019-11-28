package com.app.service.dataUtility;

import com.app.exception.AppException;

import java.nio.file.LinkOption;
import java.util.Scanner;

public class DataManager {

    private final static Scanner sc = new Scanner(System.in);

    public static Integer getInt(String message)throws AppException {
        System.out.println(message);
        String line = sc.nextLine();
        if (line == null || !line.matches("\\d+")) {
            throw new AppException("WRONG DATA TRY AGIAN");
        }
        return Integer.parseInt(line);
    }

    public static Long getLong(String message)throws AppException {
        System.out.println(message);
        String line = sc.nextLine();
        if (line == null || !line.matches("\\d+")) {
            throw new AppException("WRONG DATA TRY AGIAN");
        }
        return Long.parseLong(line);
    }

    public static Integer getInt2(String message) {
        boolean err = true;
        String line = "";
        do {
            System.out.println(message);
            line = sc.nextLine();
            err = false;
            if (line == null || !line.matches("\\d+")) {
                System.out.println(" pass wrong data, try again");
                err = true;
            }
        }
        while (err);
        return Integer.parseInt(line);
    }


    public static Double getDouble(String message) {
        System.out.println(message);
        String line = sc.nextLine();
        if (line == null || !line.matches("(\\d+\\.)*\\d+")) {
            return null;
        }
        return Double.parseDouble(line);
    }

    public static String getLine(String message) {
        System.out.println(message);
        return sc.nextLine();
    }

    public  void close() {
        if (sc != null) {
            sc.close();
        }
    }

}
