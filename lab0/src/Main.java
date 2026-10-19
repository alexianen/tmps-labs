import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Output output = new ConsoleOutput();
        Company company = new Company();
        BusinessMaker maker = new BusinessMaker();

        output.println("Welcome to the company manager.");
        output.println("Would you like to open a new business? (yes/no)");

        String answer = scanner.nextLine();

        while ("yes".equalsIgnoreCase(answer)) {
            output.println("Enter business name:");
            String name = scanner.nextLine();

            output.println("Enter location:");
            String location = scanner.nextLine();

            output.println("Enter number of workers:");
            int workers = Integer.parseInt(scanner.nextLine());

            output.println("Enter revenue:");
            float revenue = Float.parseFloat(scanner.nextLine());
            try {
                Business business = maker.createBusiness(name, location, workers, revenue);

                output.println("Choose workers to add. Enter numbers separated by commas (1=Designer, 2=Cashier, 3=Cook). Or type 'all'.");
                String choice = scanner.nextLine();

                if ("all".equalsIgnoreCase(choice)) {
                    business.addWorker(new Designer());
                    business.addWorker(new Cashier());
                    business.addWorker(new Cook());
                } else {
                    String[] values = choice.split(",");
                    for (String value : values) {
                        switch (value.trim()) {
                            case "1":
                                business.addWorker(new Designer());
                                break;
                            case "2":
                                business.addWorker(new Cashier());
                                break;
                            case "3":
                                business.addWorker(new Cook());
                                break;
                            default:
                                output.println("Invalid worker option: " + value);
                                break;
                        }
                    }
                }

                company.registerBusiness(business);
                output.println("Business opened successfully.");
            } catch (IllegalArgumentException e) {
                output.println("Error: " + e.getMessage());
            }

            output.println("Do you want to open another business? (yes/no)");
            answer = scanner.nextLine();
        }

        output.println("Current businesses:");
        company.printBusinesses(output);
        company.manageAllBusinesses();
    }
}