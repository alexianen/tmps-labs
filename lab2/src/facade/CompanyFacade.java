package facade;

import factory.BusinessFactory;
import factory.GameShopFactory;
import factory.GeneralBusinessFactory;
import factory.ITCompanyFactory;
import factory.RestaurantFactory;
import model.Business;
import model.Company;
import model.Department;
import output.Output;

/**
 * Facade pattern: one simple entry point to the whole subsystem.
 * The client (Main) no longer needs to know about the factories,
 * the Department builder or the Company singleton.
 */

public class CompanyFacade {
    private final Company company = Company.getInstance();
    private final Output output;

    public CompanyFacade(Output output) {
        if (output == null) {
            throw new IllegalArgumentException("Output cannot be null.");
        }
        this.output = output;
    }

    /** Picks the right factory, creates the business and registers it with the company. */
    public Business openBusiness(BusinessType type, String name, String location,
                                 int numWorkers, float revenue) {
        if (type == null) {
            throw new IllegalArgumentException("Business type is required.");
        }

        BusinessFactory businessFactory = switch (type) {
            case RESTAURANT -> new RestaurantFactory();
            case GAME_SHOP -> new GameShopFactory();
            case IT_COMPANY -> new ITCompanyFactory();
            case OTHER -> new GeneralBusinessFactory();
        };

        Business business = businessFactory.createBusiness(name, location, numWorkers, revenue);
        company.registerBusiness(business);
        return business;
    }

    /** Builds a department with the Builder and adds it to the business. */
    public void addDepartment(Business business, String departmentName, int numWorkers,
                              String manager, double budget, String socialMedia) {
        Department department = new Department.Builder()
                .depName(departmentName)
                .numWorkers(numWorkers)
                .manager(manager)
                .budget(budget)
                .socialMedia(socialMedia)
                .build();

        business.addDepartment(department);
    }

    public void printReport() {
        company.printBusinesses(output);
    }
}