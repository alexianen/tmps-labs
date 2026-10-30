import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Output output = new ConsoleOutput();
        Company company = Company.getInstance();
        BusinessFactory businessMaker = Business.createBusiness() ;

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

            Business business = businessMaker.createBusiness(name, location, workers, revenue);

            business.addWorker(new Cashier());
            business.addWorker(new Cook());
            business.addWorker(new Itist());
            business.addWorker(new Designer());

            System.out.println("A business needs departments: ");
            System.out.println("Which department do you want to add?");
            System.out.println("1. Human resources");
            System.out.println("2. Information technology department");
            String dpName = scanner.nextLine();


            company.registerBusiness(business);
            output.println("Business opened successfully.");
        }

        output.println("Company businesses:");
        company.printBusinesses(output);
        company.manageAllBusinesses();

        Department department = new Department.Builder()
                .depName("Hello")
                .numWorkers(5)
                .build();

        System.out.println(department);
        System.out.println(department.getName());
        System.out.println(department.getNumWorkers());
    }
}