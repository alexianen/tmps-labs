import java.util.ArrayList;
import java.util.List;

public class Company {
    private final List<Business> businesses = new ArrayList<>();

    public void registerBusiness(Business business) {
        businesses.add(business);
    }

    public void printBusinesses(Output output) {
        for (Business business : businesses) {
            business.printSummary(output);
        }
    }

    public void manageAllBusinesses() {
        for (Business business : businesses) {
            business.open();
            business.performWork();
        }
    }
}