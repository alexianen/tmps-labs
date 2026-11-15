package factory;
import model.Business;
import model.ITCompany;

public class ITCompanyFactory extends BusinessFactory {
    @Override
    protected Business createBusinessInstance() {
        return new ITCompany();
    }
}
