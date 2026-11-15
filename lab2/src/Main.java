import facade.BusinessType;
import facade.CompanyFacade;
import model.Business;
import output.ConsoleOutput;
import output.Output;
import output.TimestampOutput;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Output console = new ConsoleOutput();

        /*
         Decorator: the final report goes through a timestamping wrapper around the console.
         Decorators can be stacked, e.g. new TimestampOutput(new PrefixOutput(console, "[LOG] ")).
         Facade: Main only talks to the facade, never to factories, builders or Company directly.
         */
        CompanyFacade company = new CompanyFacade(new TimestampOutput(console));

        console.println("Welcome to the company manager.");

        while (askYesNo(scanner, console, "Do you want to open a new business? (yes/no)")) {
            console.println("Choose business type:");
            console.println("1. Restaurant");
            console.println("2. Game Shop");
            console.println("3. IT Company");
            console.println("4. Other");

            BusinessType type = switch (scanner.nextLine().trim()) {
                case "1" -> BusinessType.RESTAURANT;
                case "2" -> BusinessType.GAME_SHOP;
                case "3" -> BusinessType.IT_COMPANY;
                default -> BusinessType.OTHER;
            };

            console.println("Enter business name:");
            String name = scanner.nextLine();

            console.println("Enter location:");
            String location = scanner.nextLine();

            int workers = readInt(scanner, console, "Enter number of workers:");
            float revenue = (float) readDouble(scanner, console, "Enter revenue:");

            Business business;
            try {
                business = company.openBusiness(type, name, location, workers, revenue);
            } catch (IllegalArgumentException e) {
                console.println(e.getMessage());
                continue;
            }

            while (askYesNo(scanner, console, "Do you want to add a department? (yes/no)")) {
                console.println("Choose a department:");
                console.println("1. IT Department");
                console.println("2. Corporate Relations");
                console.println("3. Human Resources");
                console.println("4. Sales");
                console.println("5. Logistics");

                String departmentName = switch (scanner.nextLine().trim()) {
                    case "1" -> "IT Department";
                    case "2" -> "Corporate Relations";
                    case "3" -> "Human Resources";
                    case "4" -> "Sales";
                    case "5" -> "Logistics";
                    default -> "General Department";
                };

                int departmentWorkers = readInt(scanner, console,
                        "How many workers can work in this department?");

                console.println("Enter department manager (press Enter to skip):");
                String manager = scanner.nextLine();

                console.println("Enter department budget (press Enter to skip):");
                String budgetInput = scanner.nextLine().trim();
                double budget = budgetInput.isEmpty() ? 0 : parseOrZero(budgetInput, console);

                console.println("Enter department social media (press Enter to skip):");
                String socialMedia = scanner.nextLine();

                try {
                    company.addDepartment(business, departmentName, departmentWorkers,
                            manager, budget, socialMedia);
                    console.println("Department added successfully.");
                } catch (IllegalArgumentException e) {
                    console.println(e.getMessage());
                }
            }
        }

        company.printReport();
    }

    private static boolean askYesNo(Scanner scanner, Output console, String prompt) {
        console.println(prompt);
        return "yes".equalsIgnoreCase(scanner.nextLine().trim());
    }

    private static int readInt(Scanner scanner, Output console, String prompt) {
        while (true) {
            console.println(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                console.println("Please enter a whole number.");
            }
        }
    }

    private static double readDouble(Scanner scanner, Output console, String prompt) {
        while (true) {
            console.println(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                console.println("Please enter a number.");
            }
        }
    }

    private static double parseOrZero(String text, Output console) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            console.println("Invalid budget, using 0.");
            return 0;
        }
    }
}
