/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//Bianca Ysobelle M. Secreto - name
//lrbysecreto - GitHub username
package com.mycompany.motorph;

/**
 *
 * @author pahati
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MotorPH {

    public static void main(String[] args) {

        // File Location
        String filePath = "src/main/java/com/mycompany/MotorPH/employee_data.txt";

        File file = new File(filePath);

        // Validate FIle
        if (!file.exists() || !file.canRead()) {
            System.out.println("Error: File not found or cannot be read.");
            return;
        }

        // Reader Close
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            // Read each line from the text file
            while ((line = reader.readLine()) != null) {

                // Split the record into name and salary
                String[] data = line.split(",");

                // Validate correct format
                if (data.length != 2) {
                    System.out.println("Invalid record format: " + line);
                    continue;
                }

                String employeeName = data[0].trim();
                double grossSalary;

                // Convert salary text to number
                try {
                    grossSalary = Double.parseDouble(data[1].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid salary value for: " + employeeName);
                    continue;
                }

                // Ensure salary is positive
                if (grossSalary <= 0) {
                    System.out.println("Salary must be positive for: " + employeeName);
                    continue;
                }

                // Compute government deductions
                double sss = computeSSS(grossSalary);
                double philhealth = computePhilHealth(grossSalary);
                double pagibig = computePagIbig(grossSalary);

                double totalDeductions = sss + philhealth + pagibig;

                // Compute taxable income
                double taxableIncome = grossSalary - totalDeductions;

                // Compute withholding tax
                double incomeTax = computeIncomeTax(taxableIncome);

                // Compute net pay
                double netPay = grossSalary - (totalDeductions + incomeTax);

                // Display payroll summary
                System.out.println("\n==============================");
                System.out.println("Employee Name: " + employeeName);
                System.out.println("Gross Salary: PHP " + grossSalary);
                System.out.println("SSS: PHP " + sss);
                System.out.println("PhilHealth: PHP " + philhealth);
                System.out.println("Pag-IBIG: PHP " + pagibig);
                System.out.println("Income Tax: PHP " + incomeTax);
                System.out.println("Net Pay: PHP " + netPay);
                System.out.println("==============================");
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // SSS Deduction
    public static double computeSSS(double salary) {

        if (salary < 3250) return 135;
        else if (salary <= 3750) return 157.5;
        else if (salary <= 4250) return 180;
        else if (salary <= 4750) return 202.5;
        else if (salary <= 5250) return 225;
        else if (salary <= 5750) return 247.5;
        else if (salary <= 6250) return 270;
        else if (salary <= 6750) return 292.5;
        else if (salary <= 7250) return 315;
        else if (salary <= 7750) return 337.5;
        else if (salary <= 8250) return 360;
        else if (salary <= 8750) return 382.5;
        else if (salary <= 9250) return 405;
        else if (salary <= 9750) return 427.5;
        else if (salary <= 10250) return 450;
        else if (salary <= 10750) return 472.5;
        else if (salary <= 11250) return 495;
        else if (salary <= 11750) return 517.5;
        else if (salary <= 12250) return 540;
        else if (salary <= 12750) return 562.5;
        else if (salary <= 13250) return 585;
        else if (salary <= 13750) return 607.5;
        else if (salary <= 14250) return 630;
        else if (salary <= 14750) return 652.5;
        else if (salary <= 15250) return 675;
        else if (salary <= 15750) return 697.5;
        else if (salary <= 16250) return 720;
        else if (salary <= 16750) return 742.5;
        else if (salary <= 17250) return 765;
        else if (salary <= 17750) return 787.5;
        else if (salary <= 18250) return 810;
        else if (salary <= 18750) return 832.5;
        else if (salary <= 19250) return 855;
        else if (salary <= 19750) return 877.5;
        else if (salary <= 20250) return 900;
        else if (salary <= 20750) return 922.5;
        else if (salary <= 21250) return 945;
        else if (salary <= 21750) return 967.5;
        else if (salary <= 22250) return 990;
        else if (salary <= 22750) return 1012.5;
        else if (salary <= 23250) return 1035;
        else if (salary <= 23750) return 1057.5;
        else if (salary <= 24250) return 1080;
        else if (salary <= 24750) return 1102.5;
        else return 1125;
    }

    // Philhealth Deduction
    public static double computePhilHealth(double salary) {

        double premium;

        if (salary <= 10000)
            premium = 300;
        else if (salary < 60000)
            premium = salary * 0.03;
        else
            premium = 1800;

        return premium / 2;
    }

    // PagIbig Deduction
    public static double computePagIbig(double salary) {

        double contribution;

        if (salary <= 1500)
            contribution = salary * 0.01;
        else
            contribution = salary * 0.02;

        if (contribution > 100)
            contribution = 100;

        return contribution;
    }

    // Income Tax Deduction
    public static double computeIncomeTax(double taxableIncome) {

        if (taxableIncome <= 20832)
            return 0;
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