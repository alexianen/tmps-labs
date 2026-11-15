package factory;
import model.Business;
import model.GameShop;

public class GameShopFactory extends BusinessFactory {
    @Override
    protected Business createBusinessInstance() {
        return new GameShop();
    }
}
