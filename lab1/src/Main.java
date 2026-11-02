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
            int workers = Integer.parseInt(scanner.nextLine());

            output.println("Enter revenue:");
            float revenue = Float.parseFloat(scanner.nextLine());

            Business business = businessFactory.createBusiness(name, location, workers, revenue);

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

                output.println("Choose a department to add:");
                output.println("1. Human Resources");
                output.println("2. Information Technology");
                output.println("3. Sales");
                String departmentChoice = scanner.nextLine();

                Department department;
                switch (departmentChoice) {
                    case "1":
                        department = new Department.Builder()
                                .depName("Human Resources")
                                .numWorkers(3)
                                .build();
                        break;
                    case "2":
                        department = new Department.Builder()
                                .depName("Information Technology")
                                .numWorkers(5)
                                .build();
                        break;
                    case "3":
                        department = new Department.Builder()
                                .depName("Sales")
                                .numWorkers(4)
                                .build();
                        break;
                    default:
                        department = new Department.Builder()
                                .depName("General")
                                .numWorkers(1)
                                .build();
                        break;
                }

                business.addDepartment(department);
            }

            company.registerBusiness(business);
            output.println("Business opened successfully.");
        }

        output.println("Company businesses:");
        System.out.println();
        company.printBusinesses(output);
        System.out.println();
        company.manageAllBusinesses();
        System.out.println();
    }
}
