package factory;
import model.Business;
import model.GeneralBusiness;

public class GeneralBusinessFactory extends BusinessFactory {
    @Override
    protected Business createBusinessInstance() {
        return new GeneralBusiness();
    }
}
