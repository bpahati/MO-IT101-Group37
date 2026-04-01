/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 * MotorPH Payroll System
 *
 * Usage Instructions:
 * 1. Run the program.
 * 2. Enter Username:
 *      - "employee" OR "payroll_staff"
 * 3. Enter Password:
 *      - "12345"
 * 4. If you log in as Employee:
 *      - You will see the Employee Menu:
 *          1. Enter your employee number
 *          2. Exit the program
 *      - Example:
 *          Enter Number: 1
 *          Enter Employee Number: 10016
 *          Employee Number: 10016
 *          Employee Name: Christian Mata
 *          Birthday: 10/21/1987
 * 5. Follow the prompts to view employee info or exit the menu.
 *
 * Note:
 * - Employee and attendance data are read from CSV files
 * - Employee number is required to display payroll or personal info
 */

package com.mycompany.motorph;

import java.io.*;
import java.util.*;

public class MotorPH {

    static Scanner sc = new Scanner(System.in);
    static String BASE_DIR = "c:\\Users\\ASUS\\Desktop\\motorph";

    public static void main(String[] args) {

        System.out.print("Enter Username: ");
        String username = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        if (!(username.equals("employee") || username.equals("payroll_staff")) || !password.equals("12345")) {
            System.out.println("Incorrect username and/or password.");
            return;
        }

        if (username.equals("employee")) {
            employeeMenu();
        } else {
            payrollStaffMenu();
        }
    }

    // ================= EMPLOYEE MENU =================
    static void employeeMenu() {
        while (true) {
            System.out.println("\n======= EMPLOYEE MENU =======");
            System.out.println("1. Enter your employee number");
            System.out.println("2. Exit the program");
            System.out.print("Enter Number: ");
            String choice = sc.nextLine();

            if (choice.equals("1")) {
                System.out.print("Enter Employee Number: ");
                String empNo = sc.nextLine();
                displayBasicInfo(empNo);
            } else if (choice.equals("2")) {
                System.out.println("Exiting program.");
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    // ================= PAYROLL STAFF MENU =================
    static void payrollStaffMenu() {
        while (true) {
            System.out.println("\n======= PAYROLL STAFF MENU =======");
            System.out.println("1. Process Payroll");
            System.out.println("2. Exit the program");
            System.out.print("Enter Number: ");
            String choice = sc.nextLine();

            if (choice.equals("1")) {
                processPayrollMenu();
            } else if (choice.equals("2")) {
                System.out.println("Exiting program.");
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    // ================= PROCESS PAYROLL SUBMENU =================
    static void processPayrollMenu() {
        while (true) {
            System.out.println("\n======= PROCESS PAYROLL =======");
            System.out.println("1. One employee");
            System.out.println("2. All employees");
            System.out.println("3. Exit the program");
            System.out.print("Enter Number: ");
            String choice = sc.nextLine();

            if (choice.equals("1")) {
                System.out.print("Enter the employee number: ");
                String empNo = sc.nextLine();
                if (!displayPayrollForEmployee(empNo)) {
                    System.out.println("• Employee number does not exist.");
                }
            } else if (choice.equals("2")) {
                processAllEmployees();
            } else if (choice.equals("3")) {
                System.out.println("Exiting program.");
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    // ================= CSV PARSER =================
    static String[] parseCSV(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString().trim().replaceAll("^\"|\"$", ""));
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        result.add(current.toString().trim().replaceAll("^\"|\"$", ""));

        return result.toArray(new String[0]);
    }

    // ================= EMPLOYEE INFO =================
    static void displayBasicInfo(String empNo) {

        try (BufferedReader br = new BufferedReader(new FileReader("employeedata.csv"))) {

            String line;
            br.readLine(); 

            while ((line = br.readLine()) != null) {

                String[] data = parseCSV(line);

                if (data[0].equals(empNo)) {
                    System.out.println("Employee Number: " + data[0]);
                    System.out.println("Employee Name: " + data[2] + " " + data[1]);
                    System.out.println("Birthday: " + data[3]);
                    return;
                }
            }

            System.out.println("Employee number does not exist.");

        } catch (Exception e) {
            System.out.println("Error reading employee file.");
        }
    }

    // ================= PROCESS ALL EMPLOYEES =================
    static void processAllEmployees() {

        try (BufferedReader br = new BufferedReader(new FileReader("employeedata.csv"))) {

            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {

                String[] data = parseCSV(line);

                String empNo = data[0];
                String rateStr = data[18].replaceAll(",", ""); 
                double rate = Double.parseDouble(rateStr);

                // Process all months from June (6) to December (12)
                for (int month = 6; month <= 12; month++) {
                    double h1 = computeHours(empNo, month, "1-15");
                    double h2 = computeHours(empNo, month, "16-31");
                    displayPayroll(data, rate, h1, h2, month);
                }
            }

        } catch (Exception e) {
            System.out.println("Error processing employees.");
        }
    }

    // ================= DISPLAY PAYROLL FOR SINGLE EMPLOYEE =================
    static boolean displayPayrollForEmployee(String empNo) {

        try (BufferedReader br = new BufferedReader(new FileReader("employeedata.csv"))) {

            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {

                String[] data = parseCSV(line);

                if (data[0].equals(empNo)) {
                    String rateStr = data[18].replaceAll(",", "");
                    double rate = Double.parseDouble(rateStr);

                    // Process all months from June (6) to December (12)
                    for (int month = 6; month <= 12; month++) {
                        double h1 = computeHours(empNo, month, "1-15");
                        double h2 = computeHours(empNo, month, "16-31");
                        displayPayroll(data, rate, h1, h2, month);
                    }
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println("Error processing employee.");
        }

        return false;
    }

    // ================= COMPUTE HOURS =================
    static double computeHours(String empNo, int month, String cutoff) {

        double totalHours = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("employeeattendance.csv"))) {

            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                if (!data[0].equals(empNo)) continue;

                // Parse date: M/D/YYYY
                String[] dateParts = data[3].split("/");
                int empMonth = Integer.parseInt(dateParts[0]);
                int day = Integer.parseInt(dateParts[1]);

                // Skip if not the target month
                if (empMonth != month) continue;

                if (cutoff.equals("1-15") && day > 15) continue;
                if (cutoff.equals("16-31") && day <= 15) continue;

                double timeIn = convertTime(data[4]);
                double timeOut = convertTime(data[5]);

                double start = Math.max(timeIn, 8.0);
                double end = Math.min(timeOut, 17.0);

                double hours = end - start;

                if (hours > 8) hours = 8;
                if (hours < 0) hours = 0;

                totalHours += hours;
            }

        } catch (Exception e) {
            System.out.println("Error reading attendance.");
        }

        return totalHours;
    }

    // ================= TIME CONVERTER =================
    static double convertTime(String time) {

        String[] t = time.split(":");
        double hour = Double.parseDouble(t[0]);
        double min = Double.parseDouble(t[1]) / 60;

        return hour + min;
    }

    // ================= GET MONTH NAME =================
    static String getMonthName(int month) {
        String[] months = {"", "January", "February", "March", "April", "May", "June", 
                           "July", "August", "September", "October", "November", "December"};
        return months[month];
    }

    // ================= DISPLAY PAYROLL =================
    static void displayPayroll(String[] data, double rate, double h1, double h2, int month) {

        double gross1 = h1 * rate;
        double gross2 = h2 * rate;

        // Deductions are calculated based on combined gross (for the month)
        double combinedGross = gross1 + gross2;
        double sss = computeSSS(combinedGross);
        double phil = computePhilHealth(combinedGross);
        double pagibig = computePagIbig(combinedGross);

        double totalDed = sss + phil + pagibig;
        double taxable = combinedGross - totalDed;
        double tax = computeTax(taxable);

        double net1 = gross1;
        double net2 = gross2 - (totalDed + tax);

        System.out.println("\n==============================");
        System.out.println("Employee #: " + data[0]);
        System.out.println("• Employee Name: " + data[2] + " " + data[1]);
        System.out.println("• Birthday: " + data[3]);

        System.out.println("• Cutoff Date: " + getMonthName(month) + " 1 to " + getMonthName(month) + " 15");
        System.out.println("Total Hours Worked: " + h1);
        System.out.println("Gross Salary: " + gross1);
        System.out.println("Net Salary: " + net1);

        System.out.println("• Cutoff Date: " + getMonthName(month) + " 16 to " + getMonthName(month) + " 31 ");
        System.out.println("Total Hours Worked: " + h2);
        System.out.println("Gross Salary: " + gross2);
        System.out.println("Each Deduction:");
        System.out.println("SSS: " + sss);
        System.out.println("PhilHealth: " + phil);
        System.out.println("Pag-IBIG: " + pagibig);
        System.out.println("Tax: " + tax);
        System.out.println("Total Deductions: " + (totalDed + tax));
        System.out.println("Net Salary: " + net2);
    }

    // ================= SSS =================
    static double computeSSS(double salary) {

        if (salary < 3250) return 135.00;
        else if (salary <= 3750) return 157.50;
        else if (salary <= 4250) return 180.00;
        else if (salary <= 4750) return 202.50;
        else if (salary <= 5250) return 225.00;
        else if (salary <= 5750) return 247.50;
        else if (salary <= 6250) return 270.00;
        else if (salary <= 6750) return 292.50;
        else if (salary <= 7250) return 315.00;
        else if (salary <= 7750) return 337.50;
        else if (salary <= 8250) return 360.00;
        else if (salary <= 8750) return 382.50;
        else if (salary <= 9250) return 405.00;
        else if (salary <= 9750) return 427.50;
        else if (salary <= 10250) return 450.00;
        else if (salary <= 10750) return 472.50;
        else if (salary <= 11250) return 495.00;
        else if (salary <= 11750) return 517.50;
        else if (salary <= 12250) return 540.00;
        else if (salary <= 12750) return 562.50;
        else if (salary <= 13250) return 585.00;
        else if (salary <= 13750) return 607.50;
        else if (salary <= 14250) return 630.00;
        else if (salary <= 14750) return 652.50;
        else if (salary <= 15250) return 675.00;
        else if (salary <= 15750) return 697.50;
        else if (salary <= 16250) return 720.00;
        else if (salary <= 16750) return 742.50;
        else if (salary <= 17250) return 765.00;
        else if (salary <= 17750) return 787.50;
        else if (salary <= 18250) return 810.00;
        else if (salary <= 18750) return 832.50;
        else if (salary <= 19250) return 855.00;
        else if (salary <= 19750) return 877.50;
        else if (salary <= 20250) return 900.00;
        else if (salary <= 20750) return 922.50;
        else if (salary <= 21250) return 945.00;
        else if (salary <= 21750) return 967.50;
        else if (salary <= 22250) return 990.00;
        else if (salary <= 22750) return 1012.50;
        else if (salary <= 23250) return 1035.00;
        else if (salary <= 23750) return 1057.50;
        else if (salary <= 24250) return 1080.00;
        else if (salary <= 24750) return 1102.50;
        else return 1125.00;
    }

    // ================= PHILHEALTH =================
    static double computePhilHealth(double salary) {

        double premium;

        if (salary <= 10000) premium = 300;
        else if (salary < 60000) premium = salary * 0.03;
        else premium = 1800;

        return premium / 2;
    }

    // ================= PAG-IBIG =================
    static double computePagIbig(double salary) {

        double contribution;

        if (salary >= 1000 && salary <= 1500)
            contribution = salary * 0.01;
        else if (salary > 1500)
            contribution = salary * 0.02;
        else
            contribution = 0;

        if (contribution > 100) contribution = 100;

        return contribution;
    }

    // ================= INCOME TAX =================
    static double computeTax(double taxableIncome) {

        if (taxableIncome <= 20832) return 0;
        else if (taxableIncome < 33333)
            return (taxableIncome - 20833) * 0.20;
        else if (taxableIncome < 66667)
            return 2500 + (taxableIncome - 33333) * 0.25;
        else if (taxableIncome < 166667)
            return 10833 + (taxableIncome - 66667) * 0.30;
        else if (taxableIncome < 666667)
            return 40833.33 + (taxableIncome - 166667) * 0.32;
        else
            return 200833.33 + (taxableIncome - 666667) * 0.35;
    }
}
