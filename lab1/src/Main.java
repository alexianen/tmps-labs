import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Output output = new ConsoleOutput();
        Company company = Company.getInstance();
        BusinessFactory businessFactory = new RestaurantFactory();

        output.println("Welcome to the company manager.");

        while (true) {
            output.println("Do you want to open a new business? (yes/no)");
            String answer = scanner.nextLine();

            if (!"yes".equalsIgnoreCase(answer)) {
                break;
            }

            output.println("Enter business name:");
            String name = scanner.nextLine();

            output.println("Enter location:");
            String location = scanner.nextLine();

            output.println("Enter number of workers:");
            int totalBusinessWorkers = Integer.parseInt(scanner.nextLine());

            output.println("Enter revenue:");
            float revenue = Float.parseFloat(scanner.nextLine());

            Business business = businessFactory.createBusiness(name, location, totalBusinessWorkers, revenue);

            business.addWorker(new Cashier());
            business.addWorker(new Cook());
            business.addWorker(new Itist());
            business.addWorker(new Designer());

            while (true) {
                output.println("Do you want to add a department? (yes/no)");
                String addDepartmentAnswer = scanner.nextLine();

                if (!"yes".equalsIgnoreCase(addDepartmentAnswer)) {
                    break;
                }

                output.println("Choose a department:");
                output.println("1. IT Department");
                output.println("2. Corporate Relations");
                output.println("3. Human Resources");
                output.println("4. Sales");
                output.println("5. Logistics");

                String choice = scanner.nextLine();

                String departmentName;
                int departmentWorkers;

                while (true) {
                    output.println("How many workers can work in this department?");
                    departmentWorkers = Integer.parseInt(scanner.nextLine());

                    int currentDepartmentsTotal = business.getDepartmentWorkersTotal();

                    if (currentDepartmentsTotal + departmentWorkers <= totalBusinessWorkers) {
                        break;
                    }

                    output.println("The total workers in departments cannot exceed the business worker count.");
                    output.println("Current used workers: " + currentDepartmentsTotal);
                    output.println("Remaining workers: " + (totalBusinessWorkers - currentDepartmentsTotal));
                    output.println("Please enter a smaller value.");
                }

                switch (choice) {
                    case "1":
                        departmentName = "IT Department";
                        break;
                    case "2":
                        departmentName = "Corporate Relations";
                        break;
                    case "3":
                        departmentName = "Human Resources";
                        break;
                    case "4":
                        departmentName = "Sales";
                        break;
                    case "5":
                        departmentName = "Logistics";
                        break;
                    default:
                        departmentName = "General Department";
                        break;
                }

                Department department = new Department.Builder()
                        .depName(departmentName)
                        .numWorkers(departmentWorkers)
                        .build();

                business.addDepartment(department);

                output.println("Department added successfully: " + departmentName
                        + " (" + departmentWorkers + " workers)");
            }

            company.registerBusiness(business);
            output.println("Business opened successfully.");
        }

        company.printBusinesses(output);
    }
}