import java.util.Scanner;

public class Main {
    private final static Scanner scn = new Scanner(System.in);

    public static void main(String[] args) {

        BusinessMaker maker = new BusinessMaker();


        System.out.println("Welcome! Let's open a business based on an existing franchise!");
        System.out.println("Let's say... we should open a new DAVIDAN in Ciocana. Or any other restaurant, really." +
                "Those make good money ;)");
        System.out.println("Let's create a new business!");
        System.out.println("What do you want to open?");
        System.out.println("> ");
        String name = scn.nextLine().trim();
        System.out.println("Where do you want to open it?");
        System.out.println("> ");
        String location = scn.nextLine().trim();
        System.out.println("How many employees?");
        System.out.println("> ");
        String numE = scn.nextLine().trim();
        int numEmployees = Integer.parseInt(numE);

        Business b = maker.createBusiness(name, location, numEmployees);

    }
}
