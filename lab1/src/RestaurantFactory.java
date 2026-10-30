public class RestaurantFactory extends BusinessFactory {
    @Override
    protected Business createBusinessInstance() {
        return new Business();
    }
}