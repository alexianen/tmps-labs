package factory;
import model.Business;
import model.Restaurant;

public class RestaurantFactory extends BusinessFactory {
    @Override
    protected Business createBusinessInstance() {
        return new Restaurant();
    }
}