package model;

import output.Output;

import java.util.ArrayList;
import java.util.List;

public final class Company {
    private static volatile Company INSTANCE;
    // volatile makes so that these steps of creating an object are the same
    // 1. allocate memory
    // 2. run the constructor to initialise the fields
    // 3. assign the reference to INSTANCE.
    private final  List<Business> businesses = new ArrayList<>();

    private Company() {
    }

    public static Company getInstance() {
        if (INSTANCE == null) {
            synchronized (Company.class) { // lock; only 1 thread inside
                if (INSTANCE == null) {
                    INSTANCE = new Company();
                }
            }
        }
        return INSTANCE;
    }

    public synchronized void registerBusiness(Business business) {
        if (business == null) {
            throw new IllegalArgumentException("Business cannot be null.");
        }
        businesses.add(business);
    }

    public synchronized void printBusinesses(Output output) {
        output.println("Company businesses:");
        output.println("");
        for (int i = 0; i < businesses.size(); i++) {
            Business business = businesses.get(i);

            output.println("---- " + (i + 1) + " ----");
            business.printSummary(output);
            output.println("");
            business.performWork(output);
            output.println("");
        }
    }
}