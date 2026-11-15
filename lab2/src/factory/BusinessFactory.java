package factory;
import model.Business;

public abstract class BusinessFactory {
   // Factory Method means: the parent class defines the steps of creating an object,
   // but lets each child class decide which object gets created.

    public final Business createBusiness(String name, String location, int numWorkers, float revenue) {
        Business business = createBusinessInstance();
        business.setName(name);
        business.setLocation(location);
        business.setNumWorkers(numWorkers);
        business.setRevenue(revenue);

        return business;
    }

    protected abstract Business createBusinessInstance();
}