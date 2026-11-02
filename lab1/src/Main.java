import factory.*;
import model.*;
import output.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Output output = new ConsoleOutput();
        Company company = Company.getInstance();

        output.println("Welcome to the company manager.");

        while (true) {
            output.println("Do you want to open a new business? (yes/no)");
            String answer = scanner.nextLine();

            if (!"yes".equalsIgnoreCase(answer)) {
                break;
            }

            output.println("Choose business type:");
            output.println("1. Restaurant");
            output.println("2. Game Shop");
            output.println("3. IT Company");
            output.println("4. Other");

            String typeChoice = scanner.nextLine();
            BusinessFactory factory = switch (typeChoice) {
                case "1" -> new RestaurantFactory();
                case "2" -> new GameShopFactory();
                case "3" -> new ITCompanyFactory();
                default -> new GeneralBusinessFactory();
            };

            output.println("Enter business name:");
            String name = scanner.nextLine();

            output.println("Enter location:");
            String location = scanner.nextLine();

            output.println("Enter number of workers:");
            int workers = Integer.parseInt(scanner.nextLine());

            output.println("Enter revenue:");
            float revenue = Float.parseFloat(scanner.nextLine());

            Business business = factory.createBusiness(name, location, workers, revenue);

            company.registerBusiness(business);

            while (true) {
                output.println("Do you want to add a department? (yes/no)");
                String departmentAnswer = scanner.nextLine();

                if (!"yes".equalsIgnoreCase(departmentAnswer)) {
                    break;
                }

                output.println("Choose a department:");
                output.println("1. IT Department");
                output.println("2. Corporate Relations");
                output.println("3. Human Resources");
                output.println("4. Sales");
                output.println("5. Logistics");

                String departmentChoice = scanner.nextLine();

                String departmentName;
                switch (departmentChoice) {
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

                output.println("How many workers can work in this department?");
                int departmentWorkers = Integer.parseInt(scanner.nextLine());

                output.println("Enter department manager (press Enter to skip):");
                String manager = scanner.nextLine();

                output.println("Enter department budget (press Enter to skip):");
                String budgetInput = scanner.nextLine();
                double departmentBudget = budgetInput.trim().isEmpty() ? 0 : Double.parseDouble(budgetInput);

                output.println("Enter department social media (press Enter to skip):");
                String socialMedia = scanner.nextLine();

                try {
                    Department department = new Department.Builder()
                            .depName(departmentName)
                            .manager(manager)
                            .budget(departmentBudget)
                            .numWorkers(departmentWorkers)
                            .socialMedia(socialMedia)
                            .build();

                    business.addDepartment(department);
                    output.println("Department added successfully.");
                } catch (IllegalArgumentException e) {
                    output.println(e.getMessage());
                }
            }
        }
        company.printBusinesses(output);
    }
}