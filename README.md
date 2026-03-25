# MO-IT101-Group37 | MotorPH Payroll System
## Computer Programming 1 | Term 2 SY 2025 - 2026
## MotorPH Payroll System | H1101

## Team Details: 
 - Catherine Alcantara
 - Bianca Secreto
 - Benedict Pahati
 - Mary Mesinas

## Contribution of Members:
| Task contribution | Members |
|---|---|
| Analyze the MotorPH project requirements and the company background| Bianca Secreto Catherine Alcantara Benedict Pahati Mary Mesinas |
| Discuss the flow of the basic payroll system | Bianca Secreto Catherine Alcantara Benedict Pahati Mary Mesinas |
| Design the payroll system logic and workflow | Benedict Pahati |
| Created the effort estimation & project planning | Bianca Secreto |
| Created the Use-case diagram | Catherine Alcantara |
| Created the Wireframe | Catherine Alcantara |
| Calculated the total hours worked | Bianca Secreto |
| Calculated the payroll system computation of employee (June-December) | Benedict Pahati |
| Made the accuracy of SSS, PhilHealth, Pag-IBIG, and Tax computations| Mary Mesinas|
| Revised the Use-case diagram | Bianca Secreto Mary Mesinas |
| Revised Wireframe | Catherine Alcantara |
| Create GitHub accounts | Bianca Secreto Catherine Alcantara Benedict Pahati Mary Mesinas | 
| Create the GitHub Repository | Benedict Pahati |
| Added other members and mentors as collaborators in GitHub | Benedict Pahati |
|Tested and checked the payroll program | Bianca Secreto Catherine Alcantara Mary Mesinas 
| Fixed the errors in payroll system |Benedict Pahati|
| Commit, edited and updated the README.file | Bianca Secreto |
| Revise and finalize the payroll system | Benedict Pahati |
| Review and finalize all the requirements, information, and payroll system | Bianca Secreto Benedict Pahati Catherine Alcantara Mary Mesinas |
| Submitted the final project of MotorPH Payroll System | Bianca Secreto Catherine Alcantara Benedict Pahati Mary Mesinas |

## Program Details 

### Program Structure 
The system is implemented in a single main class (MotorPH) that contains multiple methods respnsible for specific tasks such as user authentication, data processing, salary computation, and output display. 

### Main Components of the Code 
#### 1. User Authentication 
The program begins by asking the user to enter a username and password.
There are two types of users:
> "Employee"
> "Payroll Staff"
Based on the user type, the system redirects to the appropriate menu.

#### 2. Menu System 
The program uses a menu-driven interface:
> "Employee Menu"
    > "View basic employee information"
> "Payroll Staff Menu"
    > "Process payroll for one employee"
    > "Process payroll for all employees"
This is implemented using loops and conditional statements to allow continuous interaction

#### 3. File Handling (CSV Processing)
The system reads data from two CSV files:
"employeedata.csv" contains employee details and salary rate
"employeeattendance.csv" contains attendance records
A custom method (parseCSV) is used to properly read and separate data, including values with commas. 

#### 4. Employee Information Display
The method "displayBasicInfo() retrieves and displays:
> "Employee number"
> "Employee name"
> "Birthday"
It searches the employee file and matches the entered employee number

#### 5. Hours Worked Calculation 
The method "computeHours():
> Reads attendance records
> Filters data by employee number and month
> Divides records into two cutoff periods (1-15 and 16-31)
> Calculates total working hours per cutoff
It also ensures:
  > Work hours are only between 8:00 AM and 5:00 PM
  > Maximum of 8 hours per day

#### 6. Salary Computation 
The system calculates:
> Gross Salary = Hours Worked x Hourly Rate
This is done separately for each cutoff period

#### 7. Deduction Processing 
The program automatically computes government deductions:
> SSS - based on salary range
> PhilHealth - percentage-based computation
> Pag-IBIG - fixed percentage with a maximum limit
> Tax - based on taxable income using tax brackets
These are handled using dedicated methods:
> "computeSSS()"
> "computePhilHealth()"
> "computePagIbig()"
> computeTax()"

#### 8. Net Salary Calculation 
The system computes:
> Total Deductions = SSS + PhilHealth + Pag-IBIG + Tax
> Net Salary = Gross Salary - Deductions
The final salary is displayed for each cutoff period

#### 9. Payroll Display 
The method "displayPayroll()" presents:
> Employee details
> Cutoff dates
> Hours worked
> Gross salary
> Individual deductions
> Net salary
This ensures the output is clear and organized for the  employee user



